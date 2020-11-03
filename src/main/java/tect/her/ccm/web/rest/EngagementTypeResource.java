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
import tect.her.ccm.service.EngagementTypeService;
import tect.her.ccm.service.dto.EngagementTypeDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.EngagementType}.
 */
@RestController
@RequestMapping("/api")
public class EngagementTypeResource {
    private final Logger log = LoggerFactory.getLogger(EngagementTypeResource.class);

    private static final String ENTITY_NAME = "engagementType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EngagementTypeService engagementTypeService;

    public EngagementTypeResource(EngagementTypeService engagementTypeService) {
        this.engagementTypeService = engagementTypeService;
    }

    /**
     * {@code POST  /engagement-types} : Create a new engagementType.
     *
     * @param engagementTypeDTO the engagementTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new engagementTypeDTO, or with status {@code 400 (Bad Request)} if the engagementType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/engagement-types")
    public ResponseEntity<EngagementTypeDTO> createEngagementType(@Valid @RequestBody EngagementTypeDTO engagementTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save EngagementType : {}", engagementTypeDTO);
        if (engagementTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new engagementType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EngagementTypeDTO result = engagementTypeService.save(engagementTypeDTO);
        return ResponseEntity
            .created(new URI("/api/engagement-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /engagement-types} : Updates an existing engagementType.
     *
     * @param engagementTypeDTO the engagementTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated engagementTypeDTO,
     * or with status {@code 400 (Bad Request)} if the engagementTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the engagementTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/engagement-types")
    public ResponseEntity<EngagementTypeDTO> updateEngagementType(@Valid @RequestBody EngagementTypeDTO engagementTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to update EngagementType : {}", engagementTypeDTO);
        if (engagementTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EngagementTypeDTO result = engagementTypeService.save(engagementTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, engagementTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /engagement-types} : get all the engagementTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of engagementTypes in body.
     */
    @GetMapping("/engagement-types")
    public ResponseEntity<List<EngagementTypeDTO>> getAllEngagementTypes(Pageable pageable) {
        log.debug("REST request to get a page of EngagementTypes");
        Page<EngagementTypeDTO> page = engagementTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /engagement-types/:id} : get the "id" engagementType.
     *
     * @param id the id of the engagementTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the engagementTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/engagement-types/{id}")
    public ResponseEntity<EngagementTypeDTO> getEngagementType(@PathVariable Long id) {
        log.debug("REST request to get EngagementType : {}", id);
        Optional<EngagementTypeDTO> engagementTypeDTO = engagementTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(engagementTypeDTO);
    }

    /**
     * {@code DELETE  /engagement-types/:id} : delete the "id" engagementType.
     *
     * @param id the id of the engagementTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/engagement-types/{id}")
    public ResponseEntity<Void> deleteEngagementType(@PathVariable Long id) {
        log.debug("REST request to delete EngagementType : {}", id);
        engagementTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
