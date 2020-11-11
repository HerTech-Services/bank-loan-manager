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
import tect.her.ccm.service.EntiteService;
import tect.her.ccm.service.dto.EntiteDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Entite}.
 */
@RestController
@RequestMapping("/api")
public class EntiteResource {
    private final Logger log = LoggerFactory.getLogger(EntiteResource.class);

    private static final String ENTITY_NAME = "entite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntiteService entiteService;

    public EntiteResource(EntiteService entiteService) {
        this.entiteService = entiteService;
    }

    /**
     * {@code POST  /entites} : Create a new entite.
     *
     * @param entiteDTO the entiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entiteDTO, or with status {@code 400 (Bad Request)} if the entite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entites")
    public ResponseEntity<EntiteDTO> createEntite(@Valid @RequestBody EntiteDTO entiteDTO) throws URISyntaxException {
        log.debug("REST request to save Entite : {}", entiteDTO);
        if (entiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new entite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntiteDTO result = entiteService.save(entiteDTO);
        return ResponseEntity
            .created(new URI("/api/entites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entites} : Updates an existing entite.
     *
     * @param entiteDTO the entiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entiteDTO,
     * or with status {@code 400 (Bad Request)} if the entiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entites")
    public ResponseEntity<EntiteDTO> updateEntite(@Valid @RequestBody EntiteDTO entiteDTO) throws URISyntaxException {
        log.debug("REST request to update Entite : {}", entiteDTO);
        if (entiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntiteDTO result = entiteService.save(entiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entites} : get all the entites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entites in body.
     */
    @GetMapping("/entites")
    public ResponseEntity<List<EntiteDTO>> getAllEntites(Pageable pageable) {
        log.debug("REST request to get a page of Entites");
        Page<EntiteDTO> page = entiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entites/:id} : get the "id" entite.
     *
     * @param id the id of the entiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entites/{id}")
    public ResponseEntity<EntiteDTO> getEntite(@PathVariable Long id) {
        log.debug("REST request to get Entite : {}", id);
        Optional<EntiteDTO> entiteDTO = entiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entiteDTO);
    }

    /**
     * {@code DELETE  /entites/:id} : delete the "id" entite.
     *
     * @param id the id of the entiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entites/{id}")
    public ResponseEntity<Void> deleteEntite(@PathVariable Long id) {
        log.debug("REST request to delete Entite : {}", id);
        entiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
