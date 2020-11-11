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
import tect.her.ccm.service.EngagementQueryService;
import tect.her.ccm.service.EngagementService;
import tect.her.ccm.service.dto.EngagementCriteria;
import tect.her.ccm.service.dto.EngagementDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Engagement}.
 */
@RestController
@RequestMapping("/api")
public class EngagementResource {
    private final Logger log = LoggerFactory.getLogger(EngagementResource.class);

    private static final String ENTITY_NAME = "engagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EngagementService engagementService;

    private final EngagementQueryService engagementQueryService;

    public EngagementResource(EngagementService engagementService, EngagementQueryService engagementQueryService) {
        this.engagementService = engagementService;
        this.engagementQueryService = engagementQueryService;
    }

    /**
     * {@code POST  /engagements} : Create a new engagement.
     *
     * @param engagementDTO the engagementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new engagementDTO, or with status {@code 400 (Bad Request)} if the engagement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/engagements")
    public ResponseEntity<EngagementDTO> createEngagement(@Valid @RequestBody EngagementDTO engagementDTO) throws URISyntaxException {
        log.debug("REST request to save Engagement : {}", engagementDTO);
        if (engagementDTO.getId() != null) {
            throw new BadRequestAlertException("A new engagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EngagementDTO result = engagementService.save(engagementDTO);
        return ResponseEntity
            .created(new URI("/api/engagements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /engagements} : Updates an existing engagement.
     *
     * @param engagementDTO the engagementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated engagementDTO,
     * or with status {@code 400 (Bad Request)} if the engagementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the engagementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/engagements")
    public ResponseEntity<EngagementDTO> updateEngagement(@Valid @RequestBody EngagementDTO engagementDTO) throws URISyntaxException {
        log.debug("REST request to update Engagement : {}", engagementDTO);
        if (engagementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EngagementDTO result = engagementService.save(engagementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, engagementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /engagements} : get all the engagements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of engagements in body.
     */
    @GetMapping("/engagements")
    public ResponseEntity<List<EngagementDTO>> getAllEngagements(EngagementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Engagements by criteria: {}", criteria);
        Page<EngagementDTO> page = engagementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /engagements/count} : count all the engagements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/engagements/count")
    public ResponseEntity<Long> countEngagements(EngagementCriteria criteria) {
        log.debug("REST request to count Engagements by criteria: {}", criteria);
        return ResponseEntity.ok().body(engagementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /engagements/:id} : get the "id" engagement.
     *
     * @param id the id of the engagementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the engagementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/engagements/{id}")
    public ResponseEntity<EngagementDTO> getEngagement(@PathVariable Long id) {
        log.debug("REST request to get Engagement : {}", id);
        Optional<EngagementDTO> engagementDTO = engagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(engagementDTO);
    }

    /**
     * {@code DELETE  /engagements/:id} : delete the "id" engagement.
     *
     * @param id the id of the engagementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/engagements/{id}")
    public ResponseEntity<Void> deleteEngagement(@PathVariable Long id) {
        log.debug("REST request to delete Engagement : {}", id);
        engagementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
