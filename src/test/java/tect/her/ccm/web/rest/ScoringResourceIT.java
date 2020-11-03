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
import tect.her.ccm.domain.Scoring;
import tect.her.ccm.repository.ScoringRepository;
import tect.her.ccm.service.ScoringService;
import tect.her.ccm.service.dto.ScoringDTO;
import tect.her.ccm.service.mapper.ScoringMapper;

/**
 * Integration tests for the {@link ScoringResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScoringResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer UPDATED_SCORE = 1;

    @Autowired
    private ScoringRepository scoringRepository;

    @Autowired
    private ScoringMapper scoringMapper;

    @Autowired
    private ScoringService scoringService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoringMockMvc;

    private Scoring scoring;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scoring createEntity(EntityManager em) {
        Scoring scoring = new Scoring().label(DEFAULT_LABEL).score(DEFAULT_SCORE);
        return scoring;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scoring createUpdatedEntity(EntityManager em) {
        Scoring scoring = new Scoring().label(UPDATED_LABEL).score(UPDATED_SCORE);
        return scoring;
    }

    @BeforeEach
    public void initTest() {
        scoring = createEntity(em);
    }

    @Test
    @Transactional
    public void createScoring() throws Exception {
        int databaseSizeBeforeCreate = scoringRepository.findAll().size();
        // Create the Scoring
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);
        restScoringMockMvc
            .perform(post("/api/scorings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isCreated());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeCreate + 1);
        Scoring testScoring = scoringList.get(scoringList.size() - 1);
        assertThat(testScoring.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testScoring.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createScoringWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoringRepository.findAll().size();

        // Create the Scoring with an existing ID
        scoring.setId(1L);
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoringMockMvc
            .perform(post("/api/scorings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoringRepository.findAll().size();
        // set the field null
        scoring.setLabel(null);

        // Create the Scoring, which fails.
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);

        restScoringMockMvc
            .perform(post("/api/scorings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isBadRequest());

        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoringRepository.findAll().size();
        // set the field null
        scoring.setScore(null);

        // Create the Scoring, which fails.
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);

        restScoringMockMvc
            .perform(post("/api/scorings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isBadRequest());

        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScorings() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        // Get all the scoringList
        restScoringMockMvc
            .perform(get("/api/scorings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scoring.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    @Test
    @Transactional
    public void getScoring() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        // Get the scoring
        restScoringMockMvc
            .perform(get("/api/scorings/{id}", scoring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scoring.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingScoring() throws Exception {
        // Get the scoring
        restScoringMockMvc.perform(get("/api/scorings/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScoring() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        int databaseSizeBeforeUpdate = scoringRepository.findAll().size();

        // Update the scoring
        Scoring updatedScoring = scoringRepository.findById(scoring.getId()).get();
        // Disconnect from session so that the updates on updatedScoring are not directly saved in db
        em.detach(updatedScoring);
        updatedScoring.label(UPDATED_LABEL).score(UPDATED_SCORE);
        ScoringDTO scoringDTO = scoringMapper.toDto(updatedScoring);

        restScoringMockMvc
            .perform(put("/api/scorings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isOk());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeUpdate);
        Scoring testScoring = scoringList.get(scoringList.size() - 1);
        assertThat(testScoring.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testScoring.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingScoring() throws Exception {
        int databaseSizeBeforeUpdate = scoringRepository.findAll().size();

        // Create the Scoring
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoringMockMvc
            .perform(put("/api/scorings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScoring() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        int databaseSizeBeforeDelete = scoringRepository.findAll().size();

        // Delete the scoring
        restScoringMockMvc
            .perform(delete("/api/scorings/{id}", scoring.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
