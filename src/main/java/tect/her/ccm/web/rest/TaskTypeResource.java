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
import tect.her.ccm.service.TaskTypeService;
import tect.her.ccm.service.dto.TaskTypeDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.TaskType}.
 */
@RestController
@RequestMapping("/api")
public class TaskTypeResource {
    private final Logger log = LoggerFactory.getLogger(TaskTypeResource.class);

    private static final String ENTITY_NAME = "taskType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskTypeService taskTypeService;

    public TaskTypeResource(TaskTypeService taskTypeService) {
        this.taskTypeService = taskTypeService;
    }

    /**
     * {@code POST  /task-types} : Create a new taskType.
     *
     * @param taskTypeDTO the taskTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskTypeDTO, or with status {@code 400 (Bad Request)} if the taskType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-types")
    public ResponseEntity<TaskTypeDTO> createTaskType(@Valid @RequestBody TaskTypeDTO taskTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TaskType : {}", taskTypeDTO);
        if (taskTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskTypeDTO result = taskTypeService.save(taskTypeDTO);
        return ResponseEntity
            .created(new URI("/api/task-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-types} : Updates an existing taskType.
     *
     * @param taskTypeDTO the taskTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskTypeDTO,
     * or with status {@code 400 (Bad Request)} if the taskTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-types")
    public ResponseEntity<TaskTypeDTO> updateTaskType(@Valid @RequestBody TaskTypeDTO taskTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TaskType : {}", taskTypeDTO);
        if (taskTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskTypeDTO result = taskTypeService.save(taskTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-types} : get all the taskTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskTypes in body.
     */
    @GetMapping("/task-types")
    public ResponseEntity<List<TaskTypeDTO>> getAllTaskTypes(Pageable pageable) {
        log.debug("REST request to get a page of TaskTypes");
        Page<TaskTypeDTO> page = taskTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-types/:id} : get the "id" taskType.
     *
     * @param id the id of the taskTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-types/{id}")
    public ResponseEntity<TaskTypeDTO> getTaskType(@PathVariable Long id) {
        log.debug("REST request to get TaskType : {}", id);
        Optional<TaskTypeDTO> taskTypeDTO = taskTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskTypeDTO);
    }

    /**
     * {@code DELETE  /task-types/:id} : delete the "id" taskType.
     *
     * @param id the id of the taskTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-types/{id}")
    public ResponseEntity<Void> deleteTaskType(@PathVariable Long id) {
        log.debug("REST request to delete TaskType : {}", id);
        taskTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
