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
import tect.her.ccm.service.RejectQueryService;
import tect.her.ccm.service.RejectService;
import tect.her.ccm.service.dto.RejectCriteria;
import tect.her.ccm.service.dto.RejectDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Reject}.
 */
@RestController
@RequestMapping("/api")
public class RejectResource {
    private final Logger log = LoggerFactory.getLogger(RejectResource.class);

    private static final String ENTITY_NAME = "reject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RejectService rejectService;

    private final RejectQueryService rejectQueryService;

    public RejectResource(RejectService rejectService, RejectQueryService rejectQueryService) {
        this.rejectService = rejectService;
        this.rejectQueryService = rejectQueryService;
    }

    /**
     * {@code POST  /rejects} : Create a new reject.
     *
     * @param rejectDTO the rejectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rejectDTO, or with status {@code 400 (Bad Request)} if the reject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rejects")
    public ResponseEntity<RejectDTO> createReject(@Valid @RequestBody RejectDTO rejectDTO) throws URISyntaxException {
        log.debug("REST request to save Reject : {}", rejectDTO);
        if (rejectDTO.getId() != null) {
            throw new BadRequestAlertException("A new reject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RejectDTO result = rejectService.save(rejectDTO);
        return ResponseEntity
            .created(new URI("/api/rejects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rejects} : Updates an existing reject.
     *
     * @param rejectDTO the rejectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rejectDTO,
     * or with status {@code 400 (Bad Request)} if the rejectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rejectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rejects")
    public ResponseEntity<RejectDTO> updateReject(@Valid @RequestBody RejectDTO rejectDTO) throws URISyntaxException {
        log.debug("REST request to update Reject : {}", rejectDTO);
        if (rejectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RejectDTO result = rejectService.save(rejectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rejectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rejects} : get all the rejects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rejects in body.
     */
    @GetMapping("/rejects")
    public ResponseEntity<List<RejectDTO>> getAllRejects(RejectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rejects by criteria: {}", criteria);
        Page<RejectDTO> page = rejectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rejects/count} : count all the rejects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rejects/count")
    public ResponseEntity<Long> countRejects(RejectCriteria criteria) {
        log.debug("REST request to count Rejects by criteria: {}", criteria);
        return ResponseEntity.ok().body(rejectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rejects/:id} : get the "id" reject.
     *
     * @param id the id of the rejectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rejectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rejects/{id}")
    public ResponseEntity<RejectDTO> getReject(@PathVariable Long id) {
        log.debug("REST request to get Reject : {}", id);
        Optional<RejectDTO> rejectDTO = rejectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rejectDTO);
    }

    /**
     * {@code DELETE  /rejects/:id} : delete the "id" reject.
     *
     * @param id the id of the rejectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rejects/{id}")
    public ResponseEntity<Void> deleteReject(@PathVariable Long id) {
        log.debug("REST request to delete Reject : {}", id);
        rejectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
