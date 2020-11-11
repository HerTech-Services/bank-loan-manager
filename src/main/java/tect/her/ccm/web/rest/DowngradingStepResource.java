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
import tect.her.ccm.service.DowngradingStepService;
import tect.her.ccm.service.dto.DowngradingStepDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.DowngradingStep}.
 */
@RestController
@RequestMapping("/api")
public class DowngradingStepResource {
    private final Logger log = LoggerFactory.getLogger(DowngradingStepResource.class);

    private static final String ENTITY_NAME = "downgradingStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DowngradingStepService downgradingStepService;

    public DowngradingStepResource(DowngradingStepService downgradingStepService) {
        this.downgradingStepService = downgradingStepService;
    }

    /**
     * {@code POST  /downgrading-steps} : Create a new downgradingStep.
     *
     * @param downgradingStepDTO the downgradingStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new downgradingStepDTO, or with status {@code 400 (Bad Request)} if the downgradingStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/downgrading-steps")
    public ResponseEntity<DowngradingStepDTO> createDowngradingStep(@Valid @RequestBody DowngradingStepDTO downgradingStepDTO)
        throws URISyntaxException {
        log.debug("REST request to save DowngradingStep : {}", downgradingStepDTO);
        if (downgradingStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new downgradingStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DowngradingStepDTO result = downgradingStepService.save(downgradingStepDTO);
        return ResponseEntity
            .created(new URI("/api/downgrading-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /downgrading-steps} : Updates an existing downgradingStep.
     *
     * @param downgradingStepDTO the downgradingStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated downgradingStepDTO,
     * or with status {@code 400 (Bad Request)} if the downgradingStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the downgradingStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/downgrading-steps")
    public ResponseEntity<DowngradingStepDTO> updateDowngradingStep(@Valid @RequestBody DowngradingStepDTO downgradingStepDTO)
        throws URISyntaxException {
        log.debug("REST request to update DowngradingStep : {}", downgradingStepDTO);
        if (downgradingStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DowngradingStepDTO result = downgradingStepService.save(downgradingStepDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, downgradingStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /downgrading-steps} : get all the downgradingSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of downgradingSteps in body.
     */
    @GetMapping("/downgrading-steps")
    public ResponseEntity<List<DowngradingStepDTO>> getAllDowngradingSteps(Pageable pageable) {
        log.debug("REST request to get a page of DowngradingSteps");
        Page<DowngradingStepDTO> page = downgradingStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /downgrading-steps/:id} : get the "id" downgradingStep.
     *
     * @param id the id of the downgradingStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the downgradingStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/downgrading-steps/{id}")
    public ResponseEntity<DowngradingStepDTO> getDowngradingStep(@PathVariable Long id) {
        log.debug("REST request to get DowngradingStep : {}", id);
        Optional<DowngradingStepDTO> downgradingStepDTO = downgradingStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(downgradingStepDTO);
    }

    /**
     * {@code DELETE  /downgrading-steps/:id} : delete the "id" downgradingStep.
     *
     * @param id the id of the downgradingStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/downgrading-steps/{id}")
    public ResponseEntity<Void> deleteDowngradingStep(@PathVariable Long id) {
        log.debug("REST request to delete DowngradingStep : {}", id);
        downgradingStepService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
