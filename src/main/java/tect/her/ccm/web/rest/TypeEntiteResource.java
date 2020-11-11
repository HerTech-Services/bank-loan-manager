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
import tect.her.ccm.service.TypeEntiteService;
import tect.her.ccm.service.dto.TypeEntiteDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.TypeEntite}.
 */
@RestController
@RequestMapping("/api")
public class TypeEntiteResource {
    private final Logger log = LoggerFactory.getLogger(TypeEntiteResource.class);

    private static final String ENTITY_NAME = "typeEntite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeEntiteService typeEntiteService;

    public TypeEntiteResource(TypeEntiteService typeEntiteService) {
        this.typeEntiteService = typeEntiteService;
    }

    /**
     * {@code POST  /type-entites} : Create a new typeEntite.
     *
     * @param typeEntiteDTO the typeEntiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeEntiteDTO, or with status {@code 400 (Bad Request)} if the typeEntite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-entites")
    public ResponseEntity<TypeEntiteDTO> createTypeEntite(@Valid @RequestBody TypeEntiteDTO typeEntiteDTO) throws URISyntaxException {
        log.debug("REST request to save TypeEntite : {}", typeEntiteDTO);
        if (typeEntiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeEntite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeEntiteDTO result = typeEntiteService.save(typeEntiteDTO);
        return ResponseEntity
            .created(new URI("/api/type-entites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-entites} : Updates an existing typeEntite.
     *
     * @param typeEntiteDTO the typeEntiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeEntiteDTO,
     * or with status {@code 400 (Bad Request)} if the typeEntiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeEntiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-entites")
    public ResponseEntity<TypeEntiteDTO> updateTypeEntite(@Valid @RequestBody TypeEntiteDTO typeEntiteDTO) throws URISyntaxException {
        log.debug("REST request to update TypeEntite : {}", typeEntiteDTO);
        if (typeEntiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeEntiteDTO result = typeEntiteService.save(typeEntiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeEntiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-entites} : get all the typeEntites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeEntites in body.
     */
    @GetMapping("/type-entites")
    public ResponseEntity<List<TypeEntiteDTO>> getAllTypeEntites(Pageable pageable) {
        log.debug("REST request to get a page of TypeEntites");
        Page<TypeEntiteDTO> page = typeEntiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-entites/:id} : get the "id" typeEntite.
     *
     * @param id the id of the typeEntiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeEntiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-entites/{id}")
    public ResponseEntity<TypeEntiteDTO> getTypeEntite(@PathVariable Long id) {
        log.debug("REST request to get TypeEntite : {}", id);
        Optional<TypeEntiteDTO> typeEntiteDTO = typeEntiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeEntiteDTO);
    }

    /**
     * {@code DELETE  /type-entites/:id} : delete the "id" typeEntite.
     *
     * @param id the id of the typeEntiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-entites/{id}")
    public ResponseEntity<Void> deleteTypeEntite(@PathVariable Long id) {
        log.debug("REST request to delete TypeEntite : {}", id);
        typeEntiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
