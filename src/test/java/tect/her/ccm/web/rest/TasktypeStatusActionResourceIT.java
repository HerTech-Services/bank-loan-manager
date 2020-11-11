package tect.her.ccm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.BankLoanManagerApp;
import tect.her.ccm.domain.TasktypeStatusAction;
import tect.her.ccm.repository.TasktypeStatusActionRepository;
import tect.her.ccm.service.TasktypeStatusActionService;
import tect.her.ccm.service.dto.TasktypeStatusActionDTO;
import tect.her.ccm.service.mapper.TasktypeStatusActionMapper;

/**
 * Integration tests for the {@link TasktypeStatusActionResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TasktypeStatusActionResourceIT {
    @Autowired
    private TasktypeStatusActionRepository tasktypeStatusActionRepository;

    @Autowired
    private TasktypeStatusActionMapper tasktypeStatusActionMapper;

    @Autowired
    private TasktypeStatusActionService tasktypeStatusActionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTasktypeStatusActionMockMvc;

    private TasktypeStatusAction tasktypeStatusAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TasktypeStatusAction createEntity(EntityManager em) {
        TasktypeStatusAction tasktypeStatusAction = new TasktypeStatusAction();
        return tasktypeStatusAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TasktypeStatusAction createUpdatedEntity(EntityManager em) {
        TasktypeStatusAction tasktypeStatusAction = new TasktypeStatusAction();
        return tasktypeStatusAction;
    }

    @BeforeEach
    public void initTest() {
        tasktypeStatusAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTasktypeStatusAction() throws Exception {
        int databaseSizeBeforeCreate = tasktypeStatusActionRepository.findAll().size();
        // Create the TasktypeStatusAction
        TasktypeStatusActionDTO tasktypeStatusActionDTO = tasktypeStatusActionMapper.toDto(tasktypeStatusAction);
        restTasktypeStatusActionMockMvc
            .perform(
                post("/api/tasktype-status-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tasktypeStatusActionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TasktypeStatusAction in the database
        List<TasktypeStatusAction> tasktypeStatusActionList = tasktypeStatusActionRepository.findAll();
        assertThat(tasktypeStatusActionList).hasSize(databaseSizeBeforeCreate + 1);
        TasktypeStatusAction testTasktypeStatusAction = tasktypeStatusActionList.get(tasktypeStatusActionList.size() - 1);
    }

    @Test
    @Transactional
    public void createTasktypeStatusActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tasktypeStatusActionRepository.findAll().size();

        // Create the TasktypeStatusAction with an existing ID
        tasktypeStatusAction.setId(1L);
        TasktypeStatusActionDTO tasktypeStatusActionDTO = tasktypeStatusActionMapper.toDto(tasktypeStatusAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTasktypeStatusActionMockMvc
            .perform(
                post("/api/tasktype-status-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tasktypeStatusActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TasktypeStatusAction in the database
        List<TasktypeStatusAction> tasktypeStatusActionList = tasktypeStatusActionRepository.findAll();
        assertThat(tasktypeStatusActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTasktypeStatusActions() throws Exception {
        // Initialize the database
        tasktypeStatusActionRepository.saveAndFlush(tasktypeStatusAction);

        // Get all the tasktypeStatusActionList
        restTasktypeStatusActionMockMvc
            .perform(get("/api/tasktype-status-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasktypeStatusAction.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTasktypeStatusAction() throws Exception {
        // Initialize the database
        tasktypeStatusActionRepository.saveAndFlush(tasktypeStatusAction);

        // Get the tasktypeStatusAction
        restTasktypeStatusActionMockMvc
            .perform(get("/api/tasktype-status-actions/{id}", tasktypeStatusAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tasktypeStatusAction.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTasktypeStatusAction() throws Exception {
        // Get the tasktypeStatusAction
        restTasktypeStatusActionMockMvc.perform(get("/api/tasktype-status-actions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTasktypeStatusAction() throws Exception {
        // Initialize the database
        tasktypeStatusActionRepository.saveAndFlush(tasktypeStatusAction);

        int databaseSizeBeforeUpdate = tasktypeStatusActionRepository.findAll().size();

        // Update the tasktypeStatusAction
        TasktypeStatusAction updatedTasktypeStatusAction = tasktypeStatusActionRepository.findById(tasktypeStatusAction.getId()).get();
        // Disconnect from session so that the updates on updatedTasktypeStatusAction are not directly saved in db
        em.detach(updatedTasktypeStatusAction);
        TasktypeStatusActionDTO tasktypeStatusActionDTO = tasktypeStatusActionMapper.toDto(updatedTasktypeStatusAction);

        restTasktypeStatusActionMockMvc
            .perform(
                put("/api/tasktype-status-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tasktypeStatusActionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TasktypeStatusAction in the database
        List<TasktypeStatusAction> tasktypeStatusActionList = tasktypeStatusActionRepository.findAll();
        assertThat(tasktypeStatusActionList).hasSize(databaseSizeBeforeUpdate);
        TasktypeStatusAction testTasktypeStatusAction = tasktypeStatusActionList.get(tasktypeStatusActionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTasktypeStatusAction() throws Exception {
        int databaseSizeBeforeUpdate = tasktypeStatusActionRepository.findAll().size();

        // Create the TasktypeStatusAction
        TasktypeStatusActionDTO tasktypeStatusActionDTO = tasktypeStatusActionMapper.toDto(tasktypeStatusAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTasktypeStatusActionMockMvc
            .perform(
                put("/api/tasktype-status-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tasktypeStatusActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TasktypeStatusAction in the database
        List<TasktypeStatusAction> tasktypeStatusActionList = tasktypeStatusActionRepository.findAll();
        assertThat(tasktypeStatusActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTasktypeStatusAction() throws Exception {
        // Initialize the database
        tasktypeStatusActionRepository.saveAndFlush(tasktypeStatusAction);

        int databaseSizeBeforeDelete = tasktypeStatusActionRepository.findAll().size();

        // Delete the tasktypeStatusAction
        restTasktypeStatusActionMockMvc
            .perform(delete("/api/tasktype-status-actions/{id}", tasktypeStatusAction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TasktypeStatusAction> tasktypeStatusActionList = tasktypeStatusActionRepository.findAll();
        assertThat(tasktypeStatusActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
