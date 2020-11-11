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
import tect.her.ccm.service.DowngradingQueryService;
import tect.her.ccm.service.DowngradingService;
import tect.her.ccm.service.dto.DowngradingCriteria;
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
    private static final BigDecimal SMALLER_CAPITAL = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_INTERESTS = new BigDecimal(0);
    private static final BigDecimal UPDATED_INTERESTS = new BigDecimal(1);
    private static final BigDecimal SMALLER_INTERESTS = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_PENALTIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_PENALTIES = new BigDecimal(1);
    private static final BigDecimal SMALLER_PENALTIES = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_ACCESSORIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_ACCESSORIES = new BigDecimal(1);
    private static final BigDecimal SMALLER_ACCESSORIES = new BigDecimal(0 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DowngradingRepository downgradingRepository;

    @Autowired
    private DowngradingMapper downgradingMapper;

    @Autowired
    private DowngradingService downgradingService;

    @Autowired
    private DowngradingQueryService downgradingQueryService;

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
    public void getDowngradingsByIdFiltering() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        Long id = downgrading.getId();

        defaultDowngradingShouldBeFound("id.equals=" + id);
        defaultDowngradingShouldNotBeFound("id.notEquals=" + id);

        defaultDowngradingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDowngradingShouldNotBeFound("id.greaterThan=" + id);

        defaultDowngradingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDowngradingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where createdDate equals to DEFAULT_CREATED_DATE
        defaultDowngradingShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the downgradingList where createdDate equals to UPDATED_CREATED_DATE
        defaultDowngradingShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultDowngradingShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the downgradingList where createdDate not equals to UPDATED_CREATED_DATE
        defaultDowngradingShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultDowngradingShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the downgradingList where createdDate equals to UPDATED_CREATED_DATE
        defaultDowngradingShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where createdDate is not null
        defaultDowngradingShouldBeFound("createdDate.specified=true");

        // Get all the downgradingList where createdDate is null
        defaultDowngradingShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital equals to DEFAULT_CAPITAL
        defaultDowngradingShouldBeFound("capital.equals=" + DEFAULT_CAPITAL);

        // Get all the downgradingList where capital equals to UPDATED_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.equals=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital not equals to DEFAULT_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.notEquals=" + DEFAULT_CAPITAL);

        // Get all the downgradingList where capital not equals to UPDATED_CAPITAL
        defaultDowngradingShouldBeFound("capital.notEquals=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsInShouldWork() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital in DEFAULT_CAPITAL or UPDATED_CAPITAL
        defaultDowngradingShouldBeFound("capital.in=" + DEFAULT_CAPITAL + "," + UPDATED_CAPITAL);

        // Get all the downgradingList where capital equals to UPDATED_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.in=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital is not null
        defaultDowngradingShouldBeFound("capital.specified=true");

        // Get all the downgradingList where capital is null
        defaultDowngradingShouldNotBeFound("capital.specified=false");
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital is greater than or equal to DEFAULT_CAPITAL
        defaultDowngradingShouldBeFound("capital.greaterThanOrEqual=" + DEFAULT_CAPITAL);

        // Get all the downgradingList where capital is greater than or equal to UPDATED_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.greaterThanOrEqual=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital is less than or equal to DEFAULT_CAPITAL
        defaultDowngradingShouldBeFound("capital.lessThanOrEqual=" + DEFAULT_CAPITAL);

        // Get all the downgradingList where capital is less than or equal to SMALLER_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.lessThanOrEqual=" + SMALLER_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsLessThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital is less than DEFAULT_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.lessThan=" + DEFAULT_CAPITAL);

        // Get all the downgradingList where capital is less than UPDATED_CAPITAL
        defaultDowngradingShouldBeFound("capital.lessThan=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByCapitalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where capital is greater than DEFAULT_CAPITAL
        defaultDowngradingShouldNotBeFound("capital.greaterThan=" + DEFAULT_CAPITAL);

        // Get all the downgradingList where capital is greater than SMALLER_CAPITAL
        defaultDowngradingShouldBeFound("capital.greaterThan=" + SMALLER_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests equals to DEFAULT_INTERESTS
        defaultDowngradingShouldBeFound("interests.equals=" + DEFAULT_INTERESTS);

        // Get all the downgradingList where interests equals to UPDATED_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.equals=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests not equals to DEFAULT_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.notEquals=" + DEFAULT_INTERESTS);

        // Get all the downgradingList where interests not equals to UPDATED_INTERESTS
        defaultDowngradingShouldBeFound("interests.notEquals=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsInShouldWork() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests in DEFAULT_INTERESTS or UPDATED_INTERESTS
        defaultDowngradingShouldBeFound("interests.in=" + DEFAULT_INTERESTS + "," + UPDATED_INTERESTS);

        // Get all the downgradingList where interests equals to UPDATED_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.in=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsNullOrNotNull() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests is not null
        defaultDowngradingShouldBeFound("interests.specified=true");

        // Get all the downgradingList where interests is null
        defaultDowngradingShouldNotBeFound("interests.specified=false");
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests is greater than or equal to DEFAULT_INTERESTS
        defaultDowngradingShouldBeFound("interests.greaterThanOrEqual=" + DEFAULT_INTERESTS);

        // Get all the downgradingList where interests is greater than or equal to UPDATED_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.greaterThanOrEqual=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests is less than or equal to DEFAULT_INTERESTS
        defaultDowngradingShouldBeFound("interests.lessThanOrEqual=" + DEFAULT_INTERESTS);

        // Get all the downgradingList where interests is less than or equal to SMALLER_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.lessThanOrEqual=" + SMALLER_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsLessThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests is less than DEFAULT_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.lessThan=" + DEFAULT_INTERESTS);

        // Get all the downgradingList where interests is less than UPDATED_INTERESTS
        defaultDowngradingShouldBeFound("interests.lessThan=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByInterestsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where interests is greater than DEFAULT_INTERESTS
        defaultDowngradingShouldNotBeFound("interests.greaterThan=" + DEFAULT_INTERESTS);

        // Get all the downgradingList where interests is greater than SMALLER_INTERESTS
        defaultDowngradingShouldBeFound("interests.greaterThan=" + SMALLER_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties equals to DEFAULT_PENALTIES
        defaultDowngradingShouldBeFound("penalties.equals=" + DEFAULT_PENALTIES);

        // Get all the downgradingList where penalties equals to UPDATED_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.equals=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties not equals to DEFAULT_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.notEquals=" + DEFAULT_PENALTIES);

        // Get all the downgradingList where penalties not equals to UPDATED_PENALTIES
        defaultDowngradingShouldBeFound("penalties.notEquals=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsInShouldWork() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties in DEFAULT_PENALTIES or UPDATED_PENALTIES
        defaultDowngradingShouldBeFound("penalties.in=" + DEFAULT_PENALTIES + "," + UPDATED_PENALTIES);

        // Get all the downgradingList where penalties equals to UPDATED_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.in=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties is not null
        defaultDowngradingShouldBeFound("penalties.specified=true");

        // Get all the downgradingList where penalties is null
        defaultDowngradingShouldNotBeFound("penalties.specified=false");
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties is greater than or equal to DEFAULT_PENALTIES
        defaultDowngradingShouldBeFound("penalties.greaterThanOrEqual=" + DEFAULT_PENALTIES);

        // Get all the downgradingList where penalties is greater than or equal to UPDATED_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.greaterThanOrEqual=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties is less than or equal to DEFAULT_PENALTIES
        defaultDowngradingShouldBeFound("penalties.lessThanOrEqual=" + DEFAULT_PENALTIES);

        // Get all the downgradingList where penalties is less than or equal to SMALLER_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.lessThanOrEqual=" + SMALLER_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsLessThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties is less than DEFAULT_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.lessThan=" + DEFAULT_PENALTIES);

        // Get all the downgradingList where penalties is less than UPDATED_PENALTIES
        defaultDowngradingShouldBeFound("penalties.lessThan=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByPenaltiesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where penalties is greater than DEFAULT_PENALTIES
        defaultDowngradingShouldNotBeFound("penalties.greaterThan=" + DEFAULT_PENALTIES);

        // Get all the downgradingList where penalties is greater than SMALLER_PENALTIES
        defaultDowngradingShouldBeFound("penalties.greaterThan=" + SMALLER_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories equals to DEFAULT_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.equals=" + DEFAULT_ACCESSORIES);

        // Get all the downgradingList where accessories equals to UPDATED_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.equals=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories not equals to DEFAULT_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.notEquals=" + DEFAULT_ACCESSORIES);

        // Get all the downgradingList where accessories not equals to UPDATED_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.notEquals=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsInShouldWork() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories in DEFAULT_ACCESSORIES or UPDATED_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.in=" + DEFAULT_ACCESSORIES + "," + UPDATED_ACCESSORIES);

        // Get all the downgradingList where accessories equals to UPDATED_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.in=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories is not null
        defaultDowngradingShouldBeFound("accessories.specified=true");

        // Get all the downgradingList where accessories is null
        defaultDowngradingShouldNotBeFound("accessories.specified=false");
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories is greater than or equal to DEFAULT_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.greaterThanOrEqual=" + DEFAULT_ACCESSORIES);

        // Get all the downgradingList where accessories is greater than or equal to UPDATED_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.greaterThanOrEqual=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories is less than or equal to DEFAULT_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.lessThanOrEqual=" + DEFAULT_ACCESSORIES);

        // Get all the downgradingList where accessories is less than or equal to SMALLER_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.lessThanOrEqual=" + SMALLER_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsLessThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories is less than DEFAULT_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.lessThan=" + DEFAULT_ACCESSORIES);

        // Get all the downgradingList where accessories is less than UPDATED_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.lessThan=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByAccessoriesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        downgradingRepository.saveAndFlush(downgrading);

        // Get all the downgradingList where accessories is greater than DEFAULT_ACCESSORIES
        defaultDowngradingShouldNotBeFound("accessories.greaterThan=" + DEFAULT_ACCESSORIES);

        // Get all the downgradingList where accessories is greater than SMALLER_ACCESSORIES
        defaultDowngradingShouldBeFound("accessories.greaterThan=" + SMALLER_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllDowngradingsByStepIsEqualToSomething() throws Exception {
        // Get already existing entity
        DowngradingStep step = downgrading.getStep();
        downgradingRepository.saveAndFlush(downgrading);
        Long stepId = step.getId();

        // Get all the downgradingList where step equals to stepId
        defaultDowngradingShouldBeFound("stepId.equals=" + stepId);

        // Get all the downgradingList where step equals to stepId + 1
        defaultDowngradingShouldNotBeFound("stepId.equals=" + (stepId + 1));
    }

    @Test
    @Transactional
    public void getAllDowngradingsByEngagementIsEqualToSomething() throws Exception {
        // Get already existing entity
        Engagement engagement = downgrading.getEngagement();
        downgradingRepository.saveAndFlush(downgrading);
        Long engagementId = engagement.getId();

        // Get all the downgradingList where engagement equals to engagementId
        defaultDowngradingShouldBeFound("engagementId.equals=" + engagementId);

        // Get all the downgradingList where engagement equals to engagementId + 1
        defaultDowngradingShouldNotBeFound("engagementId.equals=" + (engagementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDowngradingShouldBeFound(String filter) throws Exception {
        restDowngradingMockMvc
            .perform(get("/api/downgradings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(downgrading.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].interests").value(hasItem(DEFAULT_INTERESTS.intValue())))
            .andExpect(jsonPath("$.[*].penalties").value(hasItem(DEFAULT_PENALTIES.intValue())))
            .andExpect(jsonPath("$.[*].accessories").value(hasItem(DEFAULT_ACCESSORIES.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restDowngradingMockMvc
            .perform(get("/api/downgradings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDowngradingShouldNotBeFound(String filter) throws Exception {
        restDowngradingMockMvc
            .perform(get("/api/downgradings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDowngradingMockMvc
            .perform(get("/api/downgradings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
