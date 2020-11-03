package tect.her.ccm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
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
import tect.her.ccm.domain.Downgrading;
import tect.her.ccm.domain.DowngradingStep;
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.repository.DowngradingRepository;
import tect.her.ccm.service.DowngradingService;
import tect.her.ccm.service.dto.DowngradingDTO;
import tect.her.ccm.service.mapper.DowngradingMapper;

/**
 * Integration tests for the {@link DowngradingResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DowngradingResourceIT {
    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_CAPITAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_CAPITAL = new BigDecimal(1);

    private static final BigDecimal DEFAULT_INTERESTS = new BigDecimal(0);
    private static final BigDecimal UPDATED_INTERESTS = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PENALTIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_PENALTIES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_ACCESSORIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_ACCESSORIES = new BigDecimal(1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DowngradingRepository downgradingRepository;

    @Autowired
    private DowngradingMapper downgradingMapper;

    @Autowired
    private DowngradingService downgradingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDowngradingMockMvc;

    private Downgrading downgrading;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Downgrading createEntity(EntityManager em) {
        Downgrading downgrading = new Downgrading()
            .createdDate(DEFAULT_CREATED_DATE)
            .capital(DEFAULT_CAPITAL)
            .interests(DEFAULT_INTERESTS)
            .penalties(DEFAULT_PENALTIES)
            .accessories(DEFAULT_ACCESSORIES)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        DowngradingStep downgradingStep;
        if (TestUtil.findAll(em, DowngradingStep.class).isEmpty()) {
            downgradingStep = DowngradingStepResourceIT.createEntity(em);
            em.persist(downgradingStep);
            em.flush();
        } else {
            downgradingStep = TestUtil.findAll(em, DowngradingStep.class).get(0);
        }
        downgrading.setStep(downgradingStep);
        // Add required entity
        Engagement engagement;
        if (TestUtil.findAll(em, Engagement.class).isEmpty()) {
            engagement = EngagementResourceIT.createEntity(em);
            em.persist(engagement);
            em.flush();
        } else {
            engagement = TestUtil.findAll(em, Engagement.class).get(0);
        }
        downgrading.setEngagement(engagement);
        return downgrading;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Downgrading createUpdatedEntity(EntityManager em) {
        Downgrading downgrading = new Downgrading()
            .createdDate(UPDATED_CREATED_DATE)
            .capital(UPDATED_CAPITAL)
            .interests(UPDATED_INTERESTS)
            .penalties(UPDATED_PENALTIES)
            .accessories(UPDATED_ACCESSORIES)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        DowngradingStep downgradingStep;
        if (TestUtil.findAll(em, DowngradingStep.class).isEmpty()) {
            downgradingStep = DowngradingStepResourceIT.createUpdatedEntity(em);
            em.persist(downgradingStep);
            em.flush();
        } else {
            downgradingStep = TestUtil.findAll(em, DowngradingStep.class).get(0);
        }
        downgrading.setStep(downgradingStep);
        // Add required entity
        Engagement engagement;
        if (TestUtil.findAll(em, Engagement.class).isEmpty()) {
            engagement = EngagementResourceIT.createUpdatedEntity(em);
            em.persist(engagement);
            em.flush();
        } else {
            engagement = TestUtil.findAll(em, Engagement.class).get(0);
        }
        downgrading.setEngagement(engagement);
        return downgrading;
    }

    @BeforeEach
    public void initTest() {
        downgrading = createEntity(em);
    }

    @Test
    @Transactional
    public void createDowngrading() throws Exception {
        int databaseSizeBeforeCreate = downgradingRepository.findAll().size();
        // Create the Downgrading
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);
        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Downgrading in the database
        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeCreate + 1);
        Downgrading testDowngrading = downgradingList.get(downgradingList.size() - 1);
        assertThat(testDowngrading.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDowngrading.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testDowngrading.getInterests()).isEqualTo(DEFAULT_INTERESTS);
        assertThat(testDowngrading.getPenalties()).isEqualTo(DEFAULT_PENALTIES);
        assertThat(testDowngrading.getAccessories()).isEqualTo(DEFAULT_ACCESSORIES);
        assertThat(testDowngrading.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDowngradingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = downgradingRepository.findAll().size();

        // Create the Downgrading with an existing ID
        downgrading.setId(1L);
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Downgrading in the database
        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = downgradingRepository.findAll().size();
        // set the field null
        downgrading.setCreatedDate(null);

        // Create the Downgrading, which fails.
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = downgradingRepository.findAll().size();
        // set the field null
        downgrading.setCapital(null);

        // Create the Downgrading, which fails.
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = downgradingRepository.findAll().size();
        // set the field null
        downgrading.setInterests(null);

        // Create the Downgrading, which fails.
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPenaltiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = downgradingRepository.findAll().size();
        // set the field null
        downgrading.setPenalties(null);

        // Create the Downgrading, which fails.
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccessoriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = downgradingRepository.findAll().size();
        // set the field null
        downgrading.setAccessories(null);

        // Create the Downgrading, which fails.
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        restDowngradingMockMvc
            .perform(
                post("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDowngradings() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList
        restDowngradingMockMvc
            .perform(get("/api/downgradings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(downgrading.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].interests").value(hasItem(DEFAULT_INTERESTS.intValue())))
            .andExpect(jsonPath("$.[*].penalties").value(hasItem(DEFAULT_PENALTIES.intValue())))
            .andExpect(jsonPath("$.[*].accessories").value(hasItem(DEFAULT_ACCESSORIES.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDowngrading() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get the downgrading
        restDowngradingMockMvc
            .perform(get("/api/downgradings/{id}", downgrading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(downgrading.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.intValue()))
            .andExpect(jsonPath("$.interests").value(DEFAULT_INTERESTS.intValue()))
            .andExpect(jsonPath("$.penalties").value(DEFAULT_PENALTIES.intValue()))
            .andExpect(jsonPath("$.accessories").value(DEFAULT_ACCESSORIES.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDowngrading() throws Exception {
        // Get the downgrading
        restDowngradingMockMvc.perform(get("/api/downgradings/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDowngrading() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        int databaseSizeBeforeUpdate = downgradingRepository.findAll().size();

        // Update the downgrading
        Downgrading updatedDowngrading = downgradingRepository.findById(downgrading.getId()).get();
        // Disconnect from session so that the updates on updatedDowngrading are not directly saved in db
        em.detach(updatedDowngrading);
        updatedDowngrading
            .createdDate(UPDATED_CREATED_DATE)
            .capital(UPDATED_CAPITAL)
            .interests(UPDATED_INTERESTS)
            .penalties(UPDATED_PENALTIES)
            .accessories(UPDATED_ACCESSORIES)
            .description(UPDATED_DESCRIPTION);
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(updatedDowngrading);

        restDowngradingMockMvc
            .perform(
                put("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Downgrading in the database
        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeUpdate);
        Downgrading testDowngrading = downgradingList.get(downgradingList.size() - 1);
        assertThat(testDowngrading.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDowngrading.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testDowngrading.getInterests()).isEqualTo(UPDATED_INTERESTS);
        assertThat(testDowngrading.getPenalties()).isEqualTo(UPDATED_PENALTIES);
        assertThat(testDowngrading.getAccessories()).isEqualTo(UPDATED_ACCESSORIES);
        assertThat(testDowngrading.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDowngrading() throws Exception {
        int databaseSizeBeforeUpdate = downgradingRepository.findAll().size();

        // Create the Downgrading
        DowngradingDTO downgradingDTO = downgradingMapper.toDto(downgrading);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDowngradingMockMvc
            .perform(
                put("/api/downgradings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(downgradingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Downgrading in the database
        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDowngrading() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        int databaseSizeBeforeDelete = downgradingRepository.findAll().size();

        // Delete the downgrading
        restDowngradingMockMvc
            .perform(delete("/api/downgradings/{id}", downgrading.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Downgrading> downgradingList = downgradingRepository.findAll();
        assertThat(downgradingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
