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
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.domain.Reject;
import tect.her.ccm.domain.RejectType;
import tect.her.ccm.repository.RejectRepository;
import tect.her.ccm.service.RejectQueryService;
import tect.her.ccm.service.RejectService;
import tect.her.ccm.service.dto.RejectCriteria;
import tect.her.ccm.service.dto.RejectDTO;
import tect.her.ccm.service.mapper.RejectMapper;

/**
 * Integration tests for the {@link RejectResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RejectResourceIT {
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
    private RejectRepository rejectRepository;

    @Autowired
    private RejectMapper rejectMapper;

    @Autowired
    private RejectService rejectService;

    @Autowired
    private RejectQueryService rejectQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRejectMockMvc;

    private Reject reject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reject createEntity(EntityManager em) {
        Reject reject = new Reject()
            .createdDate(DEFAULT_CREATED_DATE)
            .capital(DEFAULT_CAPITAL)
            .interests(DEFAULT_INTERESTS)
            .penalties(DEFAULT_PENALTIES)
            .accessories(DEFAULT_ACCESSORIES)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        RejectType rejectType;
        if (TestUtil.findAll(em, RejectType.class).isEmpty()) {
            rejectType = RejectTypeResourceIT.createEntity(em);
            em.persist(rejectType);
            em.flush();
        } else {
            rejectType = TestUtil.findAll(em, RejectType.class).get(0);
        }
        reject.setType(rejectType);
        // Add required entity
        Engagement engagement;
        if (TestUtil.findAll(em, Engagement.class).isEmpty()) {
            engagement = EngagementResourceIT.createEntity(em);
            em.persist(engagement);
            em.flush();
        } else {
            engagement = TestUtil.findAll(em, Engagement.class).get(0);
        }
        reject.setEngagement(engagement);
        return reject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reject createUpdatedEntity(EntityManager em) {
        Reject reject = new Reject()
            .createdDate(UPDATED_CREATED_DATE)
            .capital(UPDATED_CAPITAL)
            .interests(UPDATED_INTERESTS)
            .penalties(UPDATED_PENALTIES)
            .accessories(UPDATED_ACCESSORIES)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        RejectType rejectType;
        if (TestUtil.findAll(em, RejectType.class).isEmpty()) {
            rejectType = RejectTypeResourceIT.createUpdatedEntity(em);
            em.persist(rejectType);
            em.flush();
        } else {
            rejectType = TestUtil.findAll(em, RejectType.class).get(0);
        }
        reject.setType(rejectType);
        // Add required entity
        Engagement engagement;
        if (TestUtil.findAll(em, Engagement.class).isEmpty()) {
            engagement = EngagementResourceIT.createUpdatedEntity(em);
            em.persist(engagement);
            em.flush();
        } else {
            engagement = TestUtil.findAll(em, Engagement.class).get(0);
        }
        reject.setEngagement(engagement);
        return reject;
    }

    @BeforeEach
    public void initTest() {
        reject = createEntity(em);
    }

    @Test
    @Transactional
    public void createReject() throws Exception {
        int databaseSizeBeforeCreate = rejectRepository.findAll().size();
        // Create the Reject
        RejectDTO rejectDTO = rejectMapper.toDto(reject);
        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isCreated());

        // Validate the Reject in the database
        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeCreate + 1);
        Reject testReject = rejectList.get(rejectList.size() - 1);
        assertThat(testReject.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReject.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testReject.getInterests()).isEqualTo(DEFAULT_INTERESTS);
        assertThat(testReject.getPenalties()).isEqualTo(DEFAULT_PENALTIES);
        assertThat(testReject.getAccessories()).isEqualTo(DEFAULT_ACCESSORIES);
        assertThat(testReject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRejectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rejectRepository.findAll().size();

        // Create the Reject with an existing ID
        reject.setId(1L);
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reject in the database
        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectRepository.findAll().size();
        // set the field null
        reject.setCreatedDate(null);

        // Create the Reject, which fails.
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectRepository.findAll().size();
        // set the field null
        reject.setCapital(null);

        // Create the Reject, which fails.
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectRepository.findAll().size();
        // set the field null
        reject.setInterests(null);

        // Create the Reject, which fails.
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPenaltiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectRepository.findAll().size();
        // set the field null
        reject.setPenalties(null);

        // Create the Reject, which fails.
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccessoriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectRepository.findAll().size();
        // set the field null
        reject.setAccessories(null);

        // Create the Reject, which fails.
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        restRejectMockMvc
            .perform(post("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRejects() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList
        restRejectMockMvc
            .perform(get("/api/rejects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reject.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].interests").value(hasItem(DEFAULT_INTERESTS.intValue())))
            .andExpect(jsonPath("$.[*].penalties").value(hasItem(DEFAULT_PENALTIES.intValue())))
            .andExpect(jsonPath("$.[*].accessories").value(hasItem(DEFAULT_ACCESSORIES.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReject() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get the reject
        restRejectMockMvc
            .perform(get("/api/rejects/{id}", reject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reject.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.intValue()))
            .andExpect(jsonPath("$.interests").value(DEFAULT_INTERESTS.intValue()))
            .andExpect(jsonPath("$.penalties").value(DEFAULT_PENALTIES.intValue()))
            .andExpect(jsonPath("$.accessories").value(DEFAULT_ACCESSORIES.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getRejectsByIdFiltering() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        Long id = reject.getId();

        defaultRejectShouldBeFound("id.equals=" + id);
        defaultRejectShouldNotBeFound("id.notEquals=" + id);

        defaultRejectShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRejectShouldNotBeFound("id.greaterThan=" + id);

        defaultRejectShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRejectShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllRejectsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where createdDate equals to DEFAULT_CREATED_DATE
        defaultRejectShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the rejectList where createdDate equals to UPDATED_CREATED_DATE
        defaultRejectShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllRejectsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultRejectShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the rejectList where createdDate not equals to UPDATED_CREATED_DATE
        defaultRejectShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllRejectsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultRejectShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the rejectList where createdDate equals to UPDATED_CREATED_DATE
        defaultRejectShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllRejectsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where createdDate is not null
        defaultRejectShouldBeFound("createdDate.specified=true");

        // Get all the rejectList where createdDate is null
        defaultRejectShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital equals to DEFAULT_CAPITAL
        defaultRejectShouldBeFound("capital.equals=" + DEFAULT_CAPITAL);

        // Get all the rejectList where capital equals to UPDATED_CAPITAL
        defaultRejectShouldNotBeFound("capital.equals=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital not equals to DEFAULT_CAPITAL
        defaultRejectShouldNotBeFound("capital.notEquals=" + DEFAULT_CAPITAL);

        // Get all the rejectList where capital not equals to UPDATED_CAPITAL
        defaultRejectShouldBeFound("capital.notEquals=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsInShouldWork() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital in DEFAULT_CAPITAL or UPDATED_CAPITAL
        defaultRejectShouldBeFound("capital.in=" + DEFAULT_CAPITAL + "," + UPDATED_CAPITAL);

        // Get all the rejectList where capital equals to UPDATED_CAPITAL
        defaultRejectShouldNotBeFound("capital.in=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital is not null
        defaultRejectShouldBeFound("capital.specified=true");

        // Get all the rejectList where capital is null
        defaultRejectShouldNotBeFound("capital.specified=false");
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital is greater than or equal to DEFAULT_CAPITAL
        defaultRejectShouldBeFound("capital.greaterThanOrEqual=" + DEFAULT_CAPITAL);

        // Get all the rejectList where capital is greater than or equal to UPDATED_CAPITAL
        defaultRejectShouldNotBeFound("capital.greaterThanOrEqual=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital is less than or equal to DEFAULT_CAPITAL
        defaultRejectShouldBeFound("capital.lessThanOrEqual=" + DEFAULT_CAPITAL);

        // Get all the rejectList where capital is less than or equal to SMALLER_CAPITAL
        defaultRejectShouldNotBeFound("capital.lessThanOrEqual=" + SMALLER_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsLessThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital is less than DEFAULT_CAPITAL
        defaultRejectShouldNotBeFound("capital.lessThan=" + DEFAULT_CAPITAL);

        // Get all the rejectList where capital is less than UPDATED_CAPITAL
        defaultRejectShouldBeFound("capital.lessThan=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByCapitalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where capital is greater than DEFAULT_CAPITAL
        defaultRejectShouldNotBeFound("capital.greaterThan=" + DEFAULT_CAPITAL);

        // Get all the rejectList where capital is greater than SMALLER_CAPITAL
        defaultRejectShouldBeFound("capital.greaterThan=" + SMALLER_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests equals to DEFAULT_INTERESTS
        defaultRejectShouldBeFound("interests.equals=" + DEFAULT_INTERESTS);

        // Get all the rejectList where interests equals to UPDATED_INTERESTS
        defaultRejectShouldNotBeFound("interests.equals=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests not equals to DEFAULT_INTERESTS
        defaultRejectShouldNotBeFound("interests.notEquals=" + DEFAULT_INTERESTS);

        // Get all the rejectList where interests not equals to UPDATED_INTERESTS
        defaultRejectShouldBeFound("interests.notEquals=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsInShouldWork() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests in DEFAULT_INTERESTS or UPDATED_INTERESTS
        defaultRejectShouldBeFound("interests.in=" + DEFAULT_INTERESTS + "," + UPDATED_INTERESTS);

        // Get all the rejectList where interests equals to UPDATED_INTERESTS
        defaultRejectShouldNotBeFound("interests.in=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsNullOrNotNull() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests is not null
        defaultRejectShouldBeFound("interests.specified=true");

        // Get all the rejectList where interests is null
        defaultRejectShouldNotBeFound("interests.specified=false");
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests is greater than or equal to DEFAULT_INTERESTS
        defaultRejectShouldBeFound("interests.greaterThanOrEqual=" + DEFAULT_INTERESTS);

        // Get all the rejectList where interests is greater than or equal to UPDATED_INTERESTS
        defaultRejectShouldNotBeFound("interests.greaterThanOrEqual=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests is less than or equal to DEFAULT_INTERESTS
        defaultRejectShouldBeFound("interests.lessThanOrEqual=" + DEFAULT_INTERESTS);

        // Get all the rejectList where interests is less than or equal to SMALLER_INTERESTS
        defaultRejectShouldNotBeFound("interests.lessThanOrEqual=" + SMALLER_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsLessThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests is less than DEFAULT_INTERESTS
        defaultRejectShouldNotBeFound("interests.lessThan=" + DEFAULT_INTERESTS);

        // Get all the rejectList where interests is less than UPDATED_INTERESTS
        defaultRejectShouldBeFound("interests.lessThan=" + UPDATED_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByInterestsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where interests is greater than DEFAULT_INTERESTS
        defaultRejectShouldNotBeFound("interests.greaterThan=" + DEFAULT_INTERESTS);

        // Get all the rejectList where interests is greater than SMALLER_INTERESTS
        defaultRejectShouldBeFound("interests.greaterThan=" + SMALLER_INTERESTS);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties equals to DEFAULT_PENALTIES
        defaultRejectShouldBeFound("penalties.equals=" + DEFAULT_PENALTIES);

        // Get all the rejectList where penalties equals to UPDATED_PENALTIES
        defaultRejectShouldNotBeFound("penalties.equals=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties not equals to DEFAULT_PENALTIES
        defaultRejectShouldNotBeFound("penalties.notEquals=" + DEFAULT_PENALTIES);

        // Get all the rejectList where penalties not equals to UPDATED_PENALTIES
        defaultRejectShouldBeFound("penalties.notEquals=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsInShouldWork() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties in DEFAULT_PENALTIES or UPDATED_PENALTIES
        defaultRejectShouldBeFound("penalties.in=" + DEFAULT_PENALTIES + "," + UPDATED_PENALTIES);

        // Get all the rejectList where penalties equals to UPDATED_PENALTIES
        defaultRejectShouldNotBeFound("penalties.in=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties is not null
        defaultRejectShouldBeFound("penalties.specified=true");

        // Get all the rejectList where penalties is null
        defaultRejectShouldNotBeFound("penalties.specified=false");
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties is greater than or equal to DEFAULT_PENALTIES
        defaultRejectShouldBeFound("penalties.greaterThanOrEqual=" + DEFAULT_PENALTIES);

        // Get all the rejectList where penalties is greater than or equal to UPDATED_PENALTIES
        defaultRejectShouldNotBeFound("penalties.greaterThanOrEqual=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties is less than or equal to DEFAULT_PENALTIES
        defaultRejectShouldBeFound("penalties.lessThanOrEqual=" + DEFAULT_PENALTIES);

        // Get all the rejectList where penalties is less than or equal to SMALLER_PENALTIES
        defaultRejectShouldNotBeFound("penalties.lessThanOrEqual=" + SMALLER_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsLessThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties is less than DEFAULT_PENALTIES
        defaultRejectShouldNotBeFound("penalties.lessThan=" + DEFAULT_PENALTIES);

        // Get all the rejectList where penalties is less than UPDATED_PENALTIES
        defaultRejectShouldBeFound("penalties.lessThan=" + UPDATED_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByPenaltiesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where penalties is greater than DEFAULT_PENALTIES
        defaultRejectShouldNotBeFound("penalties.greaterThan=" + DEFAULT_PENALTIES);

        // Get all the rejectList where penalties is greater than SMALLER_PENALTIES
        defaultRejectShouldBeFound("penalties.greaterThan=" + SMALLER_PENALTIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories equals to DEFAULT_ACCESSORIES
        defaultRejectShouldBeFound("accessories.equals=" + DEFAULT_ACCESSORIES);

        // Get all the rejectList where accessories equals to UPDATED_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.equals=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories not equals to DEFAULT_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.notEquals=" + DEFAULT_ACCESSORIES);

        // Get all the rejectList where accessories not equals to UPDATED_ACCESSORIES
        defaultRejectShouldBeFound("accessories.notEquals=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsInShouldWork() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories in DEFAULT_ACCESSORIES or UPDATED_ACCESSORIES
        defaultRejectShouldBeFound("accessories.in=" + DEFAULT_ACCESSORIES + "," + UPDATED_ACCESSORIES);

        // Get all the rejectList where accessories equals to UPDATED_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.in=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories is not null
        defaultRejectShouldBeFound("accessories.specified=true");

        // Get all the rejectList where accessories is null
        defaultRejectShouldNotBeFound("accessories.specified=false");
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories is greater than or equal to DEFAULT_ACCESSORIES
        defaultRejectShouldBeFound("accessories.greaterThanOrEqual=" + DEFAULT_ACCESSORIES);

        // Get all the rejectList where accessories is greater than or equal to UPDATED_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.greaterThanOrEqual=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories is less than or equal to DEFAULT_ACCESSORIES
        defaultRejectShouldBeFound("accessories.lessThanOrEqual=" + DEFAULT_ACCESSORIES);

        // Get all the rejectList where accessories is less than or equal to SMALLER_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.lessThanOrEqual=" + SMALLER_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsLessThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories is less than DEFAULT_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.lessThan=" + DEFAULT_ACCESSORIES);

        // Get all the rejectList where accessories is less than UPDATED_ACCESSORIES
        defaultRejectShouldBeFound("accessories.lessThan=" + UPDATED_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByAccessoriesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        // Get all the rejectList where accessories is greater than DEFAULT_ACCESSORIES
        defaultRejectShouldNotBeFound("accessories.greaterThan=" + DEFAULT_ACCESSORIES);

        // Get all the rejectList where accessories is greater than SMALLER_ACCESSORIES
        defaultRejectShouldBeFound("accessories.greaterThan=" + SMALLER_ACCESSORIES);
    }

    @Test
    @Transactional
    public void getAllRejectsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        RejectType type = reject.getType();
        rejectRepository.saveAndFlush(reject);
        Long typeId = type.getId();

        // Get all the rejectList where type equals to typeId
        defaultRejectShouldBeFound("typeId.equals=" + typeId);

        // Get all the rejectList where type equals to typeId + 1
        defaultRejectShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    @Test
    @Transactional
    public void getAllRejectsByEngagementIsEqualToSomething() throws Exception {
        // Get already existing entity
        Engagement engagement = reject.getEngagement();
        rejectRepository.saveAndFlush(reject);
        Long engagementId = engagement.getId();

        // Get all the rejectList where engagement equals to engagementId
        defaultRejectShouldBeFound("engagementId.equals=" + engagementId);

        // Get all the rejectList where engagement equals to engagementId + 1
        defaultRejectShouldNotBeFound("engagementId.equals=" + (engagementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRejectShouldBeFound(String filter) throws Exception {
        restRejectMockMvc
            .perform(get("/api/rejects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reject.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].interests").value(hasItem(DEFAULT_INTERESTS.intValue())))
            .andExpect(jsonPath("$.[*].penalties").value(hasItem(DEFAULT_PENALTIES.intValue())))
            .andExpect(jsonPath("$.[*].accessories").value(hasItem(DEFAULT_ACCESSORIES.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restRejectMockMvc
            .perform(get("/api/rejects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRejectShouldNotBeFound(String filter) throws Exception {
        restRejectMockMvc
            .perform(get("/api/rejects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRejectMockMvc
            .perform(get("/api/rejects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReject() throws Exception {
        // Get the reject
        restRejectMockMvc.perform(get("/api/rejects/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReject() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        int databaseSizeBeforeUpdate = rejectRepository.findAll().size();

        // Update the reject
        Reject updatedReject = rejectRepository.findById(reject.getId()).get();
        // Disconnect from session so that the updates on updatedReject are not directly saved in db
        em.detach(updatedReject);
        updatedReject
            .createdDate(UPDATED_CREATED_DATE)
            .capital(UPDATED_CAPITAL)
            .interests(UPDATED_INTERESTS)
            .penalties(UPDATED_PENALTIES)
            .accessories(UPDATED_ACCESSORIES)
            .description(UPDATED_DESCRIPTION);
        RejectDTO rejectDTO = rejectMapper.toDto(updatedReject);

        restRejectMockMvc
            .perform(put("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isOk());

        // Validate the Reject in the database
        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeUpdate);
        Reject testReject = rejectList.get(rejectList.size() - 1);
        assertThat(testReject.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReject.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testReject.getInterests()).isEqualTo(UPDATED_INTERESTS);
        assertThat(testReject.getPenalties()).isEqualTo(UPDATED_PENALTIES);
        assertThat(testReject.getAccessories()).isEqualTo(UPDATED_ACCESSORIES);
        assertThat(testReject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReject() throws Exception {
        int databaseSizeBeforeUpdate = rejectRepository.findAll().size();

        // Create the Reject
        RejectDTO rejectDTO = rejectMapper.toDto(reject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRejectMockMvc
            .perform(put("/api/rejects").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reject in the database
        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReject() throws Exception {
        // Initialize the database
        rejectRepository.saveAndFlush(reject);

        int databaseSizeBeforeDelete = rejectRepository.findAll().size();

        // Delete the reject
        restRejectMockMvc
            .perform(delete("/api/rejects/{id}", reject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reject> rejectList = rejectRepository.findAll();
        assertThat(rejectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
