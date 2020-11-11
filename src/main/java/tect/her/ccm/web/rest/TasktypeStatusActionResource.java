package tect.her.ccm.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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
import tect.her.ccm.service.TasktypeStatusActionService;
import tect.her.ccm.service.dto.TasktypeStatusActionDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.TasktypeStatusAction}.
 */
@RestController
@RequestMapping("/api")
public class TasktypeStatusActionResource {
    private final Logger log = LoggerFactory.getLogger(TasktypeStatusActionResource.class);

    private static final String ENTITY_NAME = "tasktypeStatusAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TasktypeStatusActionService tasktypeStatusActionService;

    public TasktypeStatusActionResource(TasktypeStatusActionService tasktypeStatusActionService) {
        this.tasktypeStatusActionService = tasktypeStatusActionService;
    }

    /**
     * {@code POST  /tasktype-status-actions} : Create a new tasktypeStatusAction.
     *
     * @param tasktypeStatusActionDTO the tasktypeStatusActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tasktypeStatusActionDTO, or with status {@code 400 (Bad Request)} if the tasktypeStatusAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasktype-status-actions")
    public ResponseEntity<TasktypeStatusActionDTO> createTasktypeStatusAction(@RequestBody TasktypeStatusActionDTO tasktypeStatusActionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TasktypeStatusAction : {}", tasktypeStatusActionDTO);
        if (tasktypeStatusActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new tasktypeStatusAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TasktypeStatusActionDTO result = tasktypeStatusActionService.save(tasktypeStatusActionDTO);
        return ResponseEntity
            .created(new URI("/api/tasktype-status-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasktype-status-actions} : Updates an existing tasktypeStatusAction.
     *
     * @param tasktypeStatusActionDTO the tasktypeStatusActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tasktypeStatusActionDTO,
     * or with status {@code 400 (Bad Request)} if the tasktypeStatusActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tasktypeStatusActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasktype-status-actions")
    public ResponseEntity<TasktypeStatusActionDTO> updateTasktypeStatusAction(@RequestBody TasktypeStatusActionDTO tasktypeStatusActionDTO)
        throws URISyntaxException {
        log.debug("REST request to update TasktypeStatusAction : {}", tasktypeStatusActionDTO);
        if (tasktypeStatusActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TasktypeStatusActionDTO result = tasktypeStatusActionService.save(tasktypeStatusActionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tasktypeStatusActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasktype-status-actions} : get all the tasktypeStatusActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasktypeStatusActions in body.
     */
    @GetMapping("/tasktype-status-actions")
    public ResponseEntity<List<TasktypeStatusActionDTO>> getAllTasktypeStatusActions(Pageable pageable) {
        log.debug("REST request to get a page of TasktypeStatusActions");
        Page<TasktypeStatusActionDTO> page = tasktypeStatusActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasktype-status-actions/:id} : get the "id" tasktypeStatusAction.
     *
     * @param id the id of the tasktypeStatusActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tasktypeStatusActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasktype-status-actions/{id}")
    public ResponseEntity<TasktypeStatusActionDTO> getTasktypeStatusAction(@PathVariable Long id) {
        log.debug("REST request to get TasktypeStatusAction : {}", id);
        Optional<TasktypeStatusActionDTO> tasktypeStatusActionDTO = tasktypeStatusActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tasktypeStatusActionDTO);
    }

    /**
     * {@code DELETE  /tasktype-status-actions/:id} : delete the "id" tasktypeStatusAction.
     *
     * @param id the id of the tasktypeStatusActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasktype-status-actions/{id}")
    public ResponseEntity<Void> deleteTasktypeStatusAction(@PathVariable Long id) {
        log.debug("REST request to delete TasktypeStatusAction : {}", id);
        tasktypeStatusActionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
