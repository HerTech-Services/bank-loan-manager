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
import tect.her.ccm.domain.DowngradingStep;
import tect.her.ccm.domain.Scoring;
import tect.her.ccm.repository.DowngradingStepRepository;
import tect.her.ccm.service.DowngradingStepService;
import tect.her.ccm.service.dto.DowngradingStepDTO;
import tect.her.ccm.service.mapper.DowngradingStepMapper;

/**
 * Integration tests for the {@link DowngradingStepResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DowngradingStepResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private DowngradingStepRepository downgradingStepRepository;

    @Autowired
    private DowngradingStepMapper downgradingStepMapper;

    @Autowired
    private DowngradingStepService downgradingStepService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDowngradingStepMockMvc;

    private DowngradingStep downgradingStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DowngradingStep createEntity(EntityManager em) {
        DowngradingStep downgradingStep = new DowngradingStep().label(DEFAULT_LABEL).identifier(DEFAULT_IDENTIFIER);
        // Add required entity
        Scoring scoring;
        if (TestUtil.findAll(em, Scoring.class).isEmpty()) {
            scoring = ScoringResourceIT.createEntity(em);
            em.persist(scoring);
            em.flush();
        } else {
            scoring = TestUtil.findAll(em, Scoring.class).get(0);
        }
        downgradingStep.setScoring(scoring);
        return downgradingStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DowngradingStep createUpdatedEntity(EntityManager em) {
        DowngradingStep downgradingStep = new DowngradingStep().label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER);
        // Add required entity
        Scoring scoring;
        if (TestUtil.findAll(em, Scoring.class).isEmpty()) {
            scoring = ScoringResourceIT.createUpdatedEntity(em);
            em.persist(scoring);
            em.flush();
        } else {
            scoring = TestUtil.findAll(em, Scoring.class).get(0);
        }
        downgradingStep.setScoring(scoring);
        return downgradingStep;
    }

    @BeforeEach
    public void initTest() {
        downgradingStep = createEntity(em);
    }

    @Test
    @Transactional
    public void createDowngradingStep() throws Exception {
        int databaseSizeBeforeCreate = downgradingStepRepository.findAll().size();
        // Create the DowngradingStep
        DowngradingStepDTO downgradingStepDTO = downgradingStepMapper.toDto(downgradingStep);
        restDowngradingStepMockMvc
            .perform(
                post("/api/downgrading-steps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(downgradingStepDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DowngradingStep in the database
        List<DowngradingStep> downgradingStepList = downgradingStepRepository.findAll();
        assertThat(downgradingStepList).hasSize(databaseSizeBeforeCreate + 1);
        DowngradingStep testDowngradingStep = downgradingStepList.get(downgradingStepList.size() - 1);
        assertThat(testDowngradingStep.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testDowngradingStep.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createDowngradingStepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = downgradingStepRepository.findAll().size();

        // Create the DowngradingStep with an existing ID
        downgradingStep.setId(1L);
        DowngradingStepDTO downgradingStepDTO = downgradingStepMapper.toDto(downgradingStep);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDowngradingStepMockMvc
            .perform(
                post("/api/downgrading-steps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(downgradingStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DowngradingStep in the database
        List<DowngradingStep> downgradingStepList = downgradingStepRepository.findAll();
        assertThat(downgradingStepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = downgradingStepRepository.findAll().size();
        // set the field null
        downgradingStep.setLabel(null);

        // Create the DowngradingStep, which fails.
        DowngradingStepDTO downgradingStepDTO = downgradingStepMapper.toDto(downgradingStep);

        restDowngradingStepMockMvc
            .perform(
                post("/api/downgrading-steps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(downgradingStepDTO))
            )
            .andExpect(status().isBadRequest());

        List<DowngradingStep> downgradingStepList = downgradingStepRepository.findAll();
        assertThat(downgradingStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDowngradingSteps() throws Exception {
        // Initialize the database
        downgradingStepRepository.saveAndFlush(downgradingStep);

        // Get all the downgradingStepList
        restDowngradingStepMockMvc
            .perform(get("/api/downgrading-steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(downgradingStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)));
    }

    @Test
    @Transactional
    public void getDowngradingStep() throws Exception {
        // Initialize the database
        downgradingStepRepository.saveAndFlush(downgradingStep);

        // Get the downgradingStep
        restDowngradingStepMockMvc
            .perform(get("/api/downgrading-steps/{id}", downgradingStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(downgradingStep.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER));
    }

    @Test
    @Transactional
    public void getNonExistingDowngradingStep() throws Exception {
        // Get the downgradingStep
        restDowngradingStepMockMvc.perform(get("/api/downgrading-steps/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDowngradingStep() throws Exception {
        // Initialize the database
        downgradingStepRepository.saveAndFlush(downgradingStep);

        int databaseSizeBeforeUpdate = downgradingStepRepository.findAll().size();

        // Update the downgradingStep
        DowngradingStep updatedDowngradingStep = downgradingStepRepository.findById(downgradingStep.getId()).get();
        // Disconnect from session so that the updates on updatedDowngradingStep are not directly saved in db
        em.detach(updatedDowngradingStep);
        updatedDowngradingStep.label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER);
        DowngradingStepDTO downgradingStepDTO = downgradingStepMapper.toDto(updatedDowngradingStep);

        restDowngradingStepMockMvc
            .perform(
                put("/api/downgrading-steps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(downgradingStepDTO))
            )
            .andExpect(status().isOk());

        // Validate the DowngradingStep in the database
        List<DowngradingStep> downgradingStepList = downgradingStepRepository.findAll();
        assertThat(downgradingStepList).hasSize(databaseSizeBeforeUpdate);
        DowngradingStep testDowngradingStep = downgradingStepList.get(downgradingStepList.size() - 1);
        assertThat(testDowngradingStep.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testDowngradingStep.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingDowngradingStep() throws Exception {
        int databaseSizeBeforeUpdate = downgradingStepRepository.findAll().size();

        // Create the DowngradingStep
        DowngradingStepDTO downgradingStepDTO = downgradingStepMapper.toDto(downgradingStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDowngradingStepMockMvc
            .perform(
                put("/api/downgrading-steps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(downgradingStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DowngradingStep in the database
        List<DowngradingStep> downgradingStepList = downgradingStepRepository.findAll();
        assertThat(downgradingStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDowngradingStep() throws Exception {
        // Initialize the database
        downgradingStepRepository.saveAndFlush(downgradingStep);

        int databaseSizeBeforeDelete = downgradingStepRepository.findAll().size();

        // Delete the downgradingStep
        restDowngradingStepMockMvc
            .perform(delete("/api/downgrading-steps/{id}", downgradingStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DowngradingStep> downgradingStepList = downgradingStepRepository.findAll();
        assertThat(downgradingStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
