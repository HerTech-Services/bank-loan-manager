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
import tect.her.ccm.service.EmployeEntiteService;
import tect.her.ccm.service.dto.EmployeEntiteDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.EmployeEntite}.
 */
@RestController
@RequestMapping("/api")
public class EmployeEntiteResource {
    private final Logger log = LoggerFactory.getLogger(EmployeEntiteResource.class);

    private static final String ENTITY_NAME = "employeEntite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeEntiteService employeEntiteService;

    public EmployeEntiteResource(EmployeEntiteService employeEntiteService) {
        this.employeEntiteService = employeEntiteService;
    }

    /**
     * {@code POST  /employe-entites} : Create a new employeEntite.
     *
     * @param employeEntiteDTO the employeEntiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeEntiteDTO, or with status {@code 400 (Bad Request)} if the employeEntite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employe-entites")
    public ResponseEntity<EmployeEntiteDTO> createEmployeEntite(@Valid @RequestBody EmployeEntiteDTO employeEntiteDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeEntite : {}", employeEntiteDTO);
        if (employeEntiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeEntite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeEntiteDTO result = employeEntiteService.save(employeEntiteDTO);
        return ResponseEntity
            .created(new URI("/api/employe-entites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employe-entites} : Updates an existing employeEntite.
     *
     * @param employeEntiteDTO the employeEntiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeEntiteDTO,
     * or with status {@code 400 (Bad Request)} if the employeEntiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeEntiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employe-entites")
    public ResponseEntity<EmployeEntiteDTO> updateEmployeEntite(@Valid @RequestBody EmployeEntiteDTO employeEntiteDTO)
        throws URISyntaxException {
        log.debug("REST request to update EmployeEntite : {}", employeEntiteDTO);
        if (employeEntiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeEntiteDTO result = employeEntiteService.save(employeEntiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeEntiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employe-entites} : get all the employeEntites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeEntites in body.
     */
    @GetMapping("/employe-entites")
    public ResponseEntity<List<EmployeEntiteDTO>> getAllEmployeEntites(Pageable pageable) {
        log.debug("REST request to get a page of EmployeEntites");
        Page<EmployeEntiteDTO> page = employeEntiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employe-entites/:id} : get the "id" employeEntite.
     *
     * @param id the id of the employeEntiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeEntiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employe-entites/{id}")
    public ResponseEntity<EmployeEntiteDTO> getEmployeEntite(@PathVariable Long id) {
        log.debug("REST request to get EmployeEntite : {}", id);
        Optional<EmployeEntiteDTO> employeEntiteDTO = employeEntiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeEntiteDTO);
    }

    /**
     * {@code DELETE  /employe-entites/:id} : delete the "id" employeEntite.
     *
     * @param id the id of the employeEntiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employe-entites/{id}")
    public ResponseEntity<Void> deleteEmployeEntite(@PathVariable Long id) {
        log.debug("REST request to delete EmployeEntite : {}", id);
        employeEntiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
