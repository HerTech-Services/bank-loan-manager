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
import tect.her.ccm.service.RejectTypeService;
import tect.her.ccm.service.dto.RejectTypeDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.RejectType}.
 */
@RestController
@RequestMapping("/api")
public class RejectTypeResource {
    private final Logger log = LoggerFactory.getLogger(RejectTypeResource.class);

    private static final String ENTITY_NAME = "rejectType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RejectTypeService rejectTypeService;

    public RejectTypeResource(RejectTypeService rejectTypeService) {
        this.rejectTypeService = rejectTypeService;
    }

    /**
     * {@code POST  /reject-types} : Create a new rejectType.
     *
     * @param rejectTypeDTO the rejectTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rejectTypeDTO, or with status {@code 400 (Bad Request)} if the rejectType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reject-types")
    public ResponseEntity<RejectTypeDTO> createRejectType(@Valid @RequestBody RejectTypeDTO rejectTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RejectType : {}", rejectTypeDTO);
        if (rejectTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new rejectType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RejectTypeDTO result = rejectTypeService.save(rejectTypeDTO);
        return ResponseEntity
            .created(new URI("/api/reject-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reject-types} : Updates an existing rejectType.
     *
     * @param rejectTypeDTO the rejectTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rejectTypeDTO,
     * or with status {@code 400 (Bad Request)} if the rejectTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rejectTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reject-types")
    public ResponseEntity<RejectTypeDTO> updateRejectType(@Valid @RequestBody RejectTypeDTO rejectTypeDTO) throws URISyntaxException {
        log.debug("REST request to update RejectType : {}", rejectTypeDTO);
        if (rejectTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RejectTypeDTO result = rejectTypeService.save(rejectTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rejectTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reject-types} : get all the rejectTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rejectTypes in body.
     */
    @GetMapping("/reject-types")
    public ResponseEntity<List<RejectTypeDTO>> getAllRejectTypes(Pageable pageable) {
        log.debug("REST request to get a page of RejectTypes");
        Page<RejectTypeDTO> page = rejectTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reject-types/:id} : get the "id" rejectType.
     *
     * @param id the id of the rejectTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rejectTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reject-types/{id}")
    public ResponseEntity<RejectTypeDTO> getRejectType(@PathVariable Long id) {
        log.debug("REST request to get RejectType : {}", id);
        Optional<RejectTypeDTO> rejectTypeDTO = rejectTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rejectTypeDTO);
    }

    /**
     * {@code DELETE  /reject-types/:id} : delete the "id" rejectType.
     *
     * @param id the id of the rejectTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reject-types/{id}")
    public ResponseEntity<Void> deleteRejectType(@PathVariable Long id) {
        log.debug("REST request to delete RejectType : {}", id);
        rejectTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
