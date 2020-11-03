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
import tect.her.ccm.service.ScoringService;
import tect.her.ccm.service.dto.ScoringDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Scoring}.
 */
@RestController
@RequestMapping("/api")
public class ScoringResource {
    private final Logger log = LoggerFactory.getLogger(ScoringResource.class);

    private static final String ENTITY_NAME = "scoring";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoringService scoringService;

    public ScoringResource(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    /**
     * {@code POST  /scorings} : Create a new scoring.
     *
     * @param scoringDTO the scoringDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scoringDTO, or with status {@code 400 (Bad Request)} if the scoring has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scorings")
    public ResponseEntity<ScoringDTO> createScoring(@Valid @RequestBody ScoringDTO scoringDTO) throws URISyntaxException {
        log.debug("REST request to save Scoring : {}", scoringDTO);
        if (scoringDTO.getId() != null) {
            throw new BadRequestAlertException("A new scoring cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScoringDTO result = scoringService.save(scoringDTO);
        return ResponseEntity
            .created(new URI("/api/scorings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scorings} : Updates an existing scoring.
     *
     * @param scoringDTO the scoringDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scoringDTO,
     * or with status {@code 400 (Bad Request)} if the scoringDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scoringDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scorings")
    public ResponseEntity<ScoringDTO> updateScoring(@Valid @RequestBody ScoringDTO scoringDTO) throws URISyntaxException {
        log.debug("REST request to update Scoring : {}", scoringDTO);
        if (scoringDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScoringDTO result = scoringService.save(scoringDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scoringDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scorings} : get all the scorings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scorings in body.
     */
    @GetMapping("/scorings")
    public ResponseEntity<List<ScoringDTO>> getAllScorings(Pageable pageable) {
        log.debug("REST request to get a page of Scorings");
        Page<ScoringDTO> page = scoringService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scorings/:id} : get the "id" scoring.
     *
     * @param id the id of the scoringDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scoringDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scorings/{id}")
    public ResponseEntity<ScoringDTO> getScoring(@PathVariable Long id) {
        log.debug("REST request to get Scoring : {}", id);
        Optional<ScoringDTO> scoringDTO = scoringService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scoringDTO);
    }

    /**
     * {@code DELETE  /scorings/:id} : delete the "id" scoring.
     *
     * @param id the id of the scoringDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scorings/{id}")
    public ResponseEntity<Void> deleteScoring(@PathVariable Long id) {
        log.debug("REST request to delete Scoring : {}", id);
        scoringService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
