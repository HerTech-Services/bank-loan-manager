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
import tect.her.ccm.service.ParametersService;
import tect.her.ccm.service.dto.ParametersDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Parameters}.
 */
@RestController
@RequestMapping("/api")
public class ParametersResource {
    private final Logger log = LoggerFactory.getLogger(ParametersResource.class);

    private static final String ENTITY_NAME = "parameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametersService parametersService;

    public ParametersResource(ParametersService parametersService) {
        this.parametersService = parametersService;
    }

    /**
     * {@code POST  /parameters} : Create a new parameters.
     *
     * @param parametersDTO the parametersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametersDTO, or with status {@code 400 (Bad Request)} if the parameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameters")
    public ResponseEntity<ParametersDTO> createParameters(@Valid @RequestBody ParametersDTO parametersDTO) throws URISyntaxException {
        log.debug("REST request to save Parameters : {}", parametersDTO);
        if (parametersDTO.getId() != null) {
            throw new BadRequestAlertException("A new parameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametersDTO result = parametersService.save(parametersDTO);
        return ResponseEntity
            .created(new URI("/api/parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parameters} : Updates an existing parameters.
     *
     * @param parametersDTO the parametersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametersDTO,
     * or with status {@code 400 (Bad Request)} if the parametersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameters")
    public ResponseEntity<ParametersDTO> updateParameters(@Valid @RequestBody ParametersDTO parametersDTO) throws URISyntaxException {
        log.debug("REST request to update Parameters : {}", parametersDTO);
        if (parametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametersDTO result = parametersService.save(parametersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parameters} : get all the parameters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameters in body.
     */
    @GetMapping("/parameters")
    public ResponseEntity<List<ParametersDTO>> getAllParameters(Pageable pageable) {
        log.debug("REST request to get a page of Parameters");
        Page<ParametersDTO> page = parametersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parameters/:id} : get the "id" parameters.
     *
     * @param id the id of the parametersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameters/{id}")
    public ResponseEntity<ParametersDTO> getParameters(@PathVariable Long id) {
        log.debug("REST request to get Parameters : {}", id);
        Optional<ParametersDTO> parametersDTO = parametersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametersDTO);
    }

    /**
     * {@code DELETE  /parameters/:id} : delete the "id" parameters.
     *
     * @param id the id of the parametersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameters/{id}")
    public ResponseEntity<Void> deleteParameters(@PathVariable Long id) {
        log.debug("REST request to delete Parameters : {}", id);
        parametersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
