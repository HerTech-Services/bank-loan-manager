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
import tect.her.ccm.domain.TaskType;
import tect.her.ccm.repository.TaskTypeRepository;
import tect.her.ccm.service.TaskTypeService;
import tect.her.ccm.service.dto.TaskTypeDTO;
import tect.her.ccm.service.mapper.TaskTypeMapper;

/**
 * Integration tests for the {@link TaskTypeResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskTypeResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Autowired
    private TaskTypeRepository taskTypeRepository;

    @Autowired
    private TaskTypeMapper taskTypeMapper;

    @Autowired
    private TaskTypeService taskTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskTypeMockMvc;

    private TaskType taskType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskType createEntity(EntityManager em) {
        TaskType taskType = new TaskType().label(DEFAULT_LABEL).identifier(DEFAULT_IDENTIFIER).isEnabled(DEFAULT_IS_ENABLED);
        return taskType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskType createUpdatedEntity(EntityManager em) {
        TaskType taskType = new TaskType().label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER).isEnabled(UPDATED_IS_ENABLED);
        return taskType;
    }

    @BeforeEach
    public void initTest() {
        taskType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskType() throws Exception {
        int databaseSizeBeforeCreate = taskTypeRepository.findAll().size();
        // Create the TaskType
        TaskTypeDTO taskTypeDTO = taskTypeMapper.toDto(taskType);
        restTaskTypeMockMvc
            .perform(
                post("/api/task-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaskType in the database
        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TaskType testTaskType = taskTypeList.get(taskTypeList.size() - 1);
        assertThat(testTaskType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTaskType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testTaskType.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createTaskTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskTypeRepository.findAll().size();

        // Create the TaskType with an existing ID
        taskType.setId(1L);
        TaskTypeDTO taskTypeDTO = taskTypeMapper.toDto(taskType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskTypeMockMvc
            .perform(
                post("/api/task-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskType in the database
        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskTypeRepository.findAll().size();
        // set the field null
        taskType.setLabel(null);

        // Create the TaskType, which fails.
        TaskTypeDTO taskTypeDTO = taskTypeMapper.toDto(taskType);

        restTaskTypeMockMvc
            .perform(
                post("/api/task-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskTypeRepository.findAll().size();
        // set the field null
        taskType.setIdentifier(null);

        // Create the TaskType, which fails.
        TaskTypeDTO taskTypeDTO = taskTypeMapper.toDto(taskType);

        restTaskTypeMockMvc
            .perform(
                post("/api/task-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskTypes() throws Exception {
        // Initialize the database
        taskTypeRepository.saveAndFlush(taskType);

        // Get all the taskTypeList
        restTaskTypeMockMvc
            .perform(get("/api/task-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTaskType() throws Exception {
        // Initialize the database
        taskTypeRepository.saveAndFlush(taskType);

        // Get the taskType
        restTaskTypeMockMvc
            .perform(get("/api/task-types/{id}", taskType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaskType() throws Exception {
        // Get the taskType
        restTaskTypeMockMvc.perform(get("/api/task-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskType() throws Exception {
        // Initialize the database
        taskTypeRepository.saveAndFlush(taskType);

        int databaseSizeBeforeUpdate = taskTypeRepository.findAll().size();

        // Update the taskType
        TaskType updatedTaskType = taskTypeRepository.findById(taskType.getId()).get();
        // Disconnect from session so that the updates on updatedTaskType are not directly saved in db
        em.detach(updatedTaskType);
        updatedTaskType.label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER).isEnabled(UPDATED_IS_ENABLED);
        TaskTypeDTO taskTypeDTO = taskTypeMapper.toDto(updatedTaskType);

        restTaskTypeMockMvc
            .perform(put("/api/task-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TaskType in the database
        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeUpdate);
        TaskType testTaskType = taskTypeList.get(taskTypeList.size() - 1);
        assertThat(testTaskType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTaskType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testTaskType.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskType() throws Exception {
        int databaseSizeBeforeUpdate = taskTypeRepository.findAll().size();

        // Create the TaskType
        TaskTypeDTO taskTypeDTO = taskTypeMapper.toDto(taskType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskTypeMockMvc
            .perform(put("/api/task-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskType in the database
        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskType() throws Exception {
        // Initialize the database
        taskTypeRepository.saveAndFlush(taskType);

        int databaseSizeBeforeDelete = taskTypeRepository.findAll().size();

        // Delete the taskType
        restTaskTypeMockMvc
            .perform(delete("/api/task-types/{id}", taskType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskType> taskTypeList = taskTypeRepository.findAll();
        assertThat(taskTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
