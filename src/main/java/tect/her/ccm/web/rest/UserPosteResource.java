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
import tect.her.ccm.service.UserPosteService;
import tect.her.ccm.service.dto.UserPosteDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.UserPoste}.
 */
@RestController
@RequestMapping("/api")
public class UserPosteResource {
    private final Logger log = LoggerFactory.getLogger(UserPosteResource.class);

    private static final String ENTITY_NAME = "userPoste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPosteService userPosteService;

    public UserPosteResource(UserPosteService userPosteService) {
        this.userPosteService = userPosteService;
    }

    /**
     * {@code POST  /user-postes} : Create a new userPoste.
     *
     * @param userPosteDTO the userPosteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPosteDTO, or with status {@code 400 (Bad Request)} if the userPoste has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-postes")
    public ResponseEntity<UserPosteDTO> createUserPoste(@Valid @RequestBody UserPosteDTO userPosteDTO) throws URISyntaxException {
        log.debug("REST request to save UserPoste : {}", userPosteDTO);
        if (userPosteDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPoste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPosteDTO result = userPosteService.save(userPosteDTO);
        return ResponseEntity
            .created(new URI("/api/user-postes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-postes} : Updates an existing userPoste.
     *
     * @param userPosteDTO the userPosteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPosteDTO,
     * or with status {@code 400 (Bad Request)} if the userPosteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPosteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-postes")
    public ResponseEntity<UserPosteDTO> updateUserPoste(@Valid @RequestBody UserPosteDTO userPosteDTO) throws URISyntaxException {
        log.debug("REST request to update UserPoste : {}", userPosteDTO);
        if (userPosteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPosteDTO result = userPosteService.save(userPosteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPosteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-postes} : get all the userPostes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPostes in body.
     */
    @GetMapping("/user-postes")
    public ResponseEntity<List<UserPosteDTO>> getAllUserPostes(Pageable pageable) {
        log.debug("REST request to get a page of UserPostes");
        Page<UserPosteDTO> page = userPosteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-postes/:id} : get the "id" userPoste.
     *
     * @param id the id of the userPosteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPosteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-postes/{id}")
    public ResponseEntity<UserPosteDTO> getUserPoste(@PathVariable Long id) {
        log.debug("REST request to get UserPoste : {}", id);
        Optional<UserPosteDTO> userPosteDTO = userPosteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPosteDTO);
    }

    /**
     * {@code DELETE  /user-postes/:id} : delete the "id" userPoste.
     *
     * @param id the id of the userPosteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-postes/{id}")
    public ResponseEntity<Void> deleteUserPoste(@PathVariable Long id) {
        log.debug("REST request to delete UserPoste : {}", id);
        userPosteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
