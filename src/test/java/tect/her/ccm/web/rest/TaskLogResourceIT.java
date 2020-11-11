package tect.her.ccm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.util.Base64Utils;
import tect.her.ccm.BankLoanManagerApp;
import tect.her.ccm.domain.TaskLog;
import tect.her.ccm.repository.TaskLogRepository;
import tect.her.ccm.service.TaskLogService;
import tect.her.ccm.service.dto.TaskLogDTO;
import tect.her.ccm.service.mapper.TaskLogMapper;

/**
 * Integration tests for the {@link TaskLogResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskLogResourceIT {
    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TASK_PROPERTIES = "AAAAAAAAAA";
    private static final String UPDATED_TASK_PROPERTIES = "BBBBBBBBBB";

    private static final String DEFAULT_ENGAGEMENT_PROPERTIES = "AAAAAAAAAA";
    private static final String UPDATED_ENGAGEMENT_PROPERTIES = "BBBBBBBBBB";

    @Autowired
    private TaskLogRepository taskLogRepository;

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Autowired
    private TaskLogService taskLogService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskLogMockMvc;

    private TaskLog taskLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskLog createEntity(EntityManager em) {
        TaskLog taskLog = new TaskLog()
            .comment(DEFAULT_COMMENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .taskProperties(DEFAULT_TASK_PROPERTIES)
            .engagementProperties(DEFAULT_ENGAGEMENT_PROPERTIES);
        return taskLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskLog createUpdatedEntity(EntityManager em) {
        TaskLog taskLog = new TaskLog()
            .comment(UPDATED_COMMENT)
            .createdDate(UPDATED_CREATED_DATE)
            .taskProperties(UPDATED_TASK_PROPERTIES)
            .engagementProperties(UPDATED_ENGAGEMENT_PROPERTIES);
        return taskLog;
    }

    @BeforeEach
    public void initTest() {
        taskLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskLog() throws Exception {
        int databaseSizeBeforeCreate = taskLogRepository.findAll().size();
        // Create the TaskLog
        TaskLogDTO taskLogDTO = taskLogMapper.toDto(taskLog);
        restTaskLogMockMvc
            .perform(post("/api/task-logs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskLogDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskLog in the database
        List<TaskLog> taskLogList = taskLogRepository.findAll();
        assertThat(taskLogList).hasSize(databaseSizeBeforeCreate + 1);
        TaskLog testTaskLog = taskLogList.get(taskLogList.size() - 1);
        assertThat(testTaskLog.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTaskLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTaskLog.getTaskProperties()).isEqualTo(DEFAULT_TASK_PROPERTIES);
        assertThat(testTaskLog.getEngagementProperties()).isEqualTo(DEFAULT_ENGAGEMENT_PROPERTIES);
    }

    @Test
    @Transactional
    public void createTaskLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskLogRepository.findAll().size();

        // Create the TaskLog with an existing ID
        taskLog.setId(1L);
        TaskLogDTO taskLogDTO = taskLogMapper.toDto(taskLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskLogMockMvc
            .perform(post("/api/task-logs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskLog in the database
        List<TaskLog> taskLogList = taskLogRepository.findAll();
        assertThat(taskLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaskLogs() throws Exception {
        // Initialize the database
        taskLogRepository.saveAndFlush(taskLog);

        // Get all the taskLogList
        restTaskLogMockMvc
            .perform(get("/api/task-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].taskProperties").value(hasItem(DEFAULT_TASK_PROPERTIES.toString())))
            .andExpect(jsonPath("$.[*].engagementProperties").value(hasItem(DEFAULT_ENGAGEMENT_PROPERTIES.toString())));
    }

    @Test
    @Transactional
    public void getTaskLog() throws Exception {
        // Initialize the database
        taskLogRepository.saveAndFlush(taskLog);

        // Get the taskLog
        restTaskLogMockMvc
            .perform(get("/api/task-logs/{id}", taskLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskLog.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.taskProperties").value(DEFAULT_TASK_PROPERTIES.toString()))
            .andExpect(jsonPath("$.engagementProperties").value(DEFAULT_ENGAGEMENT_PROPERTIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaskLog() throws Exception {
        // Get the taskLog
        restTaskLogMockMvc.perform(get("/api/task-logs/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskLog() throws Exception {
        // Initialize the database
        taskLogRepository.saveAndFlush(taskLog);

        int databaseSizeBeforeUpdate = taskLogRepository.findAll().size();

        // Update the taskLog
        TaskLog updatedTaskLog = taskLogRepository.findById(taskLog.getId()).get();
        // Disconnect from session so that the updates on updatedTaskLog are not directly saved in db
        em.detach(updatedTaskLog);
        updatedTaskLog
            .comment(UPDATED_COMMENT)
            .createdDate(UPDATED_CREATED_DATE)
            .taskProperties(UPDATED_TASK_PROPERTIES)
            .engagementProperties(UPDATED_ENGAGEMENT_PROPERTIES);
        TaskLogDTO taskLogDTO = taskLogMapper.toDto(updatedTaskLog);

        restTaskLogMockMvc
            .perform(put("/api/task-logs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskLogDTO)))
            .andExpect(status().isOk());

        // Validate the TaskLog in the database
        List<TaskLog> taskLogList = taskLogRepository.findAll();
        assertThat(taskLogList).hasSize(databaseSizeBeforeUpdate);
        TaskLog testTaskLog = taskLogList.get(taskLogList.size() - 1);
        assertThat(testTaskLog.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTaskLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTaskLog.getTaskProperties()).isEqualTo(UPDATED_TASK_PROPERTIES);
        assertThat(testTaskLog.getEngagementProperties()).isEqualTo(UPDATED_ENGAGEMENT_PROPERTIES);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskLog() throws Exception {
        int databaseSizeBeforeUpdate = taskLogRepository.findAll().size();

        // Create the TaskLog
        TaskLogDTO taskLogDTO = taskLogMapper.toDto(taskLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskLogMockMvc
            .perform(put("/api/task-logs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskLog in the database
        List<TaskLog> taskLogList = taskLogRepository.findAll();
        assertThat(taskLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskLog() throws Exception {
        // Initialize the database
        taskLogRepository.saveAndFlush(taskLog);

        int databaseSizeBeforeDelete = taskLogRepository.findAll().size();

        // Delete the taskLog
        restTaskLogMockMvc
            .perform(delete("/api/task-logs/{id}", taskLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskLog> taskLogList = taskLogRepository.findAll();
        assertThat(taskLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
