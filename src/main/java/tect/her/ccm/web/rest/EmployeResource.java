package tect.her.ccm.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tect.her.ccm.domain.Employe;
import tect.her.ccm.domain.User;
import tect.her.ccm.repository.UserRepository;
import tect.her.ccm.service.EmployeQueryService;
import tect.her.ccm.service.EmployeService;
import tect.her.ccm.service.UserService;
import tect.her.ccm.service.dto.EmployeCriteria;
import tect.her.ccm.service.dto.EmployeDTO;
import tect.her.ccm.service.dto.UserDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;
import tect.her.ccm.web.rest.errors.EmailAlreadyUsedException;
import tect.her.ccm.web.rest.errors.LoginAlreadyUsedException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Employe}.
 */
@RestController
@RequestMapping("/api")
public class EmployeResource {
    private final Logger log = LoggerFactory.getLogger(EmployeResource.class);

    private static final String ENTITY_NAME = "employe";
    private static final String ENTITY_USER_NAME = "employe.utilisateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${application.default-password}")
    private String defaultPassword;

    private final EmployeService employeService;

    private final EmployeQueryService employeQueryService;

    private final UserRepository userRepository;

    private final UserService userService;

    public EmployeResource(
        EmployeService employeService,
        EmployeQueryService employeQueryService,
        UserRepository userRepository,
        UserService userService
    ) {
        this.employeService = employeService;
        this.employeQueryService = employeQueryService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /employes} : Create a new employe.
     *
     * @param employeDTO the employeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeDTO, or with status {@code 400 (Bad Request)} if the employe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employes")
    public ResponseEntity<EmployeDTO> createEmploye(@Valid @RequestBody EmployeDTO employeDTO) throws URISyntaxException {
        log.debug("REST request to save Employe : {}", employeDTO);
        if (employeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeDTO result = employeService.save(employeDTO);
        return ResponseEntity
            .created(new URI("/api/employes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employes} : Updates an existing employe.
     *
     * @param employeDTO the employeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeDTO,
     * or with status {@code 400 (Bad Request)} if the employeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employes")
    public ResponseEntity<EmployeDTO> updateEmploye(@Valid @RequestBody EmployeDTO employeDTO) throws URISyntaxException {
        log.debug("REST request to update Employe : {}", employeDTO);
        if (employeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeDTO result = employeService.save(employeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employes} : get all the employes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employes in body.
     */
    @GetMapping("/employes")
    public ResponseEntity<List<EmployeDTO>> getAllEmployes(EmployeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Employes by criteria: {}", criteria);
        Page<EmployeDTO> page = employeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employes/count} : count all the employes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employes/count")
    public ResponseEntity<Long> countEmployes(EmployeCriteria criteria) {
        log.debug("REST request to count Employes by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employes/:id} : get the "id" employe.
     *
     * @param id the id of the employeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employes/{id}")
    public ResponseEntity<EmployeDTO> getEmploye(@PathVariable Long id) {
        log.debug("REST request to get Employe : {}", id);
        Optional<EmployeDTO> employeDTO = employeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeDTO);
    }

    /**
     * {@code DELETE  /employes/:id} : delete the "id" employe.
     *
     * @param id the id of the employeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employes/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        log.debug("REST request to delete Employe : {}", id);
        employeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /employes/{id}/user} : Create a user employe.
     *
     * @param userDTO the userDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the employeDTO with userId, or with status {@code 400 (Bad Request)} if the user already exist
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employes/{id}/user")
    public ResponseEntity<EmployeDTO> createEmployeUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO)
        throws URISyntaxException {
        log.debug("REST request to save Employe Id and User : {} {}", id, userDTO);
        Optional<EmployeDTO> employeDTO = employeService.findOne(id);

        if (!employeDTO.isPresent()) {
            return ResponseUtil.wrapOrNotFound(employeDTO);
        }
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            //Transaction: create user and assign it to employe
            EmployeDTO employe = employeService.saveEmployeUser(employeDTO.get(), userDTO, this.defaultPassword);
            return ResponseEntity
                .created(new URI("/api/employes/" + employe.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_USER_NAME, employe.getId().toString()))
                .body(employe);
        }
    }

    /**
     * {@code PUT  /employes/{id}/user} : Update a user employe.
     *
     * @param userDTO the userDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the employeDTO with userId, or with status {@code 400 (Bad Request)} if the user already exist
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employes/{id}/user")
    public ResponseEntity<EmployeDTO> updateEmployeUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO)
        throws URISyntaxException {
        log.debug("REST request to update Employe Id and User : {} {}", id, userDTO);
        //Optional<EmployeDTO> employeDTO = employeService.findOne(id);

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_USER_NAME, id.toString()))
            .body(null);
    }
}
