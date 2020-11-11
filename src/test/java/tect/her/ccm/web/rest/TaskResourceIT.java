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
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.domain.Task;
import tect.her.ccm.domain.TaskType;
import tect.her.ccm.domain.User;
import tect.her.ccm.repository.TaskRepository;
import tect.her.ccm.service.TaskService;
import tect.her.ccm.service.dto.TaskDTO;
import tect.her.ccm.service.mapper.TaskMapper;

/**
 * Integration tests for the {@link TaskResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskResourceIT {
    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final Boolean DEFAULT_IS_SYSTEM = false;
    private static final Boolean UPDATED_IS_SYSTEM = true;

    private static final Integer DEFAULT_PROCESS_DELAY = 1;
    private static final Integer UPDATED_PROCESS_DELAY = 2;

    private static final Integer DEFAULT_DELAY_1 = 1;
    private static final Integer UPDATED_DELAY_1 = 2;

    private static final Integer DEFAULT_DELAY_2 = 1;
    private static final Integer UPDATED_DELAY_2 = 2;

    private static final Integer DEFAULT_VIEWED = 1;
    private static final Integer UPDATED_VIEWED = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PROCESS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PROCESS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROCESS_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_COMMENT = "BBBBBBBBBB";

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .sequence(DEFAULT_SEQUENCE)
            .isSystem(DEFAULT_IS_SYSTEM)
            .processDelay(DEFAULT_PROCESS_DELAY)
            .delay1(DEFAULT_DELAY_1)
            .delay2(DEFAULT_DELAY_2)
            .viewed(DEFAULT_VIEWED)
            .createdDate(DEFAULT_CREATED_DATE)
            .processDate(DEFAULT_PROCESS_DATE)
            .processComment(DEFAULT_PROCESS_COMMENT);
        // Add required entity
        TaskType taskType;
        if (TestUtil.findAll(em, TaskType.class).isEmpty()) {
            taskType = TaskTypeResourceIT.createEntity(em);
            em.persist(taskType);
            em.flush();
        } else {
            taskType = TestUtil.findAll(em, TaskType.class).get(0);
        }
        task.setType(taskType);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        task.setAssignee(user);
        // Add required entity
        Engagement engagement;
        if (TestUtil.findAll(em, Engagement.class).isEmpty()) {
            engagement = EngagementResourceIT.createEntity(em);
            em.persist(engagement);
            em.flush();
        } else {
            engagement = TestUtil.findAll(em, Engagement.class).get(0);
        }
        task.setEngagement(engagement);
        return task;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .sequence(UPDATED_SEQUENCE)
            .isSystem(UPDATED_IS_SYSTEM)
            .processDelay(UPDATED_PROCESS_DELAY)
            .delay1(UPDATED_DELAY_1)
            .delay2(UPDATED_DELAY_2)
            .viewed(UPDATED_VIEWED)
            .createdDate(UPDATED_CREATED_DATE)
            .processDate(UPDATED_PROCESS_DATE)
            .processComment(UPDATED_PROCESS_COMMENT);
        // Add required entity
        TaskType taskType;
        if (TestUtil.findAll(em, TaskType.class).isEmpty()) {
            taskType = TaskTypeResourceIT.createUpdatedEntity(em);
            em.persist(taskType);
            em.flush();
        } else {
            taskType = TestUtil.findAll(em, TaskType.class).get(0);
        }
        task.setType(taskType);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        task.setAssignee(user);
        // Add required entity
        Engagement engagement;
        if (TestUtil.findAll(em, Engagement.class).isEmpty()) {
            engagement = EngagementResourceIT.createUpdatedEntity(em);
            em.persist(engagement);
            em.flush();
        } else {
            engagement = TestUtil.findAll(em, Engagement.class).get(0);
        }
        task.setEngagement(engagement);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);
        restTaskMockMvc
            .perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testTask.isIsSystem()).isEqualTo(DEFAULT_IS_SYSTEM);
        assertThat(testTask.getProcessDelay()).isEqualTo(DEFAULT_PROCESS_DELAY);
        assertThat(testTask.getDelay1()).isEqualTo(DEFAULT_DELAY_1);
        assertThat(testTask.getDelay2()).isEqualTo(DEFAULT_DELAY_2);
        assertThat(testTask.getViewed()).isEqualTo(DEFAULT_VIEWED);
        assertThat(testTask.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTask.getProcessDate()).isEqualTo(DEFAULT_PROCESS_DATE);
        assertThat(testTask.getProcessComment()).isEqualTo(DEFAULT_PROCESS_COMMENT);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);
        TaskDTO taskDTO = taskMapper.toDto(task);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc
            .perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setSequence(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);

        restTaskMockMvc
            .perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSystemIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setIsSystem(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);

        restTaskMockMvc
            .perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcessDelayIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setProcessDelay(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);

        restTaskMockMvc
            .perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc
            .perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].isSystem").value(hasItem(DEFAULT_IS_SYSTEM.booleanValue())))
            .andExpect(jsonPath("$.[*].processDelay").value(hasItem(DEFAULT_PROCESS_DELAY)))
            .andExpect(jsonPath("$.[*].delay1").value(hasItem(DEFAULT_DELAY_1)))
            .andExpect(jsonPath("$.[*].delay2").value(hasItem(DEFAULT_DELAY_2)))
            .andExpect(jsonPath("$.[*].viewed").value(hasItem(DEFAULT_VIEWED)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].processDate").value(hasItem(DEFAULT_PROCESS_DATE.toString())))
            .andExpect(jsonPath("$.[*].processComment").value(hasItem(DEFAULT_PROCESS_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc
            .perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.isSystem").value(DEFAULT_IS_SYSTEM.booleanValue()))
            .andExpect(jsonPath("$.processDelay").value(DEFAULT_PROCESS_DELAY))
            .andExpect(jsonPath("$.delay1").value(DEFAULT_DELAY_1))
            .andExpect(jsonPath("$.delay2").value(DEFAULT_DELAY_2))
            .andExpect(jsonPath("$.viewed").value(DEFAULT_VIEWED))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.processDate").value(DEFAULT_PROCESS_DATE.toString()))
            .andExpect(jsonPath("$.processComment").value(DEFAULT_PROCESS_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .sequence(UPDATED_SEQUENCE)
            .isSystem(UPDATED_IS_SYSTEM)
            .processDelay(UPDATED_PROCESS_DELAY)
            .delay1(UPDATED_DELAY_1)
            .delay2(UPDATED_DELAY_2)
            .viewed(UPDATED_VIEWED)
            .createdDate(UPDATED_CREATED_DATE)
            .processDate(UPDATED_PROCESS_DATE)
            .processComment(UPDATED_PROCESS_COMMENT);
        TaskDTO taskDTO = taskMapper.toDto(updatedTask);

        restTaskMockMvc
            .perform(put("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testTask.isIsSystem()).isEqualTo(UPDATED_IS_SYSTEM);
        assertThat(testTask.getProcessDelay()).isEqualTo(UPDATED_PROCESS_DELAY);
        assertThat(testTask.getDelay1()).isEqualTo(UPDATED_DELAY_1);
        assertThat(testTask.getDelay2()).isEqualTo(UPDATED_DELAY_2);
        assertThat(testTask.getViewed()).isEqualTo(UPDATED_VIEWED);
        assertThat(testTask.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTask.getProcessDate()).isEqualTo(UPDATED_PROCESS_DATE);
        assertThat(testTask.getProcessComment()).isEqualTo(UPDATED_PROCESS_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(put("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc
            .perform(delete("/api/tasks/{id}", task.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
