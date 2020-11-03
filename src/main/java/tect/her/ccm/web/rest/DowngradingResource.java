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
import tect.her.ccm.service.DowngradingService;
import tect.her.ccm.service.dto.DowngradingDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Downgrading}.
 */
@RestController
@RequestMapping("/api")
public class DowngradingResource {
    private final Logger log = LoggerFactory.getLogger(DowngradingResource.class);

    private static final String ENTITY_NAME = "downgrading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DowngradingService downgradingService;

    public DowngradingResource(DowngradingService downgradingService) {
        this.downgradingService = downgradingService;
    }

    /**
     * {@code POST  /downgradings} : Create a new downgrading.
     *
     * @param downgradingDTO the downgradingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new downgradingDTO, or with status {@code 400 (Bad Request)} if the downgrading has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/downgradings")
    public ResponseEntity<DowngradingDTO> createDowngrading(@Valid @RequestBody DowngradingDTO downgradingDTO) throws URISyntaxException {
        log.debug("REST request to save Downgrading : {}", downgradingDTO);
        if (downgradingDTO.getId() != null) {
            throw new BadRequestAlertException("A new downgrading cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DowngradingDTO result = downgradingService.save(downgradingDTO);
        return ResponseEntity
            .created(new URI("/api/downgradings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /downgradings} : Updates an existing downgrading.
     *
     * @param downgradingDTO the downgradingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated downgradingDTO,
     * or with status {@code 400 (Bad Request)} if the downgradingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the downgradingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/downgradings")
    public ResponseEntity<DowngradingDTO> updateDowngrading(@Valid @RequestBody DowngradingDTO downgradingDTO) throws URISyntaxException {
        log.debug("REST request to update Downgrading : {}", downgradingDTO);
        if (downgradingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DowngradingDTO result = downgradingService.save(downgradingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, downgradingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /downgradings} : get all the downgradings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of downgradings in body.
     */
    @GetMapping("/downgradings")
    public ResponseEntity<List<DowngradingDTO>> getAllDowngradings(Pageable pageable) {
        log.debug("REST request to get a page of Downgradings");
        Page<DowngradingDTO> page = downgradingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /downgradings/:id} : get the "id" downgrading.
     *
     * @param id the id of the downgradingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the downgradingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/downgradings/{id}")
    public ResponseEntity<DowngradingDTO> getDowngrading(@PathVariable Long id) {
        log.debug("REST request to get Downgrading : {}", id);
        Optional<DowngradingDTO> downgradingDTO = downgradingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(downgradingDTO);
    }

    /**
     * {@code DELETE  /downgradings/:id} : delete the "id" downgrading.
     *
     * @param id the id of the downgradingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/downgradings/{id}")
    public ResponseEntity<Void> deleteDowngrading(@PathVariable Long id) {
        log.debug("REST request to delete Downgrading : {}", id);
        downgradingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
