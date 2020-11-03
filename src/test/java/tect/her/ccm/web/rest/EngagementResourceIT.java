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
import tect.her.ccm.domain.Client;
import tect.her.ccm.domain.Compte;
import tect.her.ccm.domain.Downgrading;
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.domain.EngagementType;
import tect.her.ccm.domain.Notes;
import tect.her.ccm.domain.Reject;
import tect.her.ccm.domain.Status;
import tect.her.ccm.domain.Task;
import tect.her.ccm.domain.User;
import tect.her.ccm.domain.enumeration.Echeance;
import tect.her.ccm.domain.enumeration.ModeRembourssement;
import tect.her.ccm.repository.EngagementRepository;
import tect.her.ccm.service.EngagementQueryService;
import tect.her.ccm.service.EngagementService;
import tect.her.ccm.service.dto.EngagementCriteria;
import tect.her.ccm.service.dto.EngagementDTO;
import tect.her.ccm.service.mapper.EngagementMapper;

/**
 * Integration tests for the {@link EngagementResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EngagementResourceIT {
    private static final Integer DEFAULT_SCORING = 0;
    private static final Integer UPDATED_SCORING = 1;
    private static final Integer SMALLER_SCORING = 0 - 1;

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_DELAY = 1;
    private static final Integer UPDATED_DELAY = 2;
    private static final Integer SMALLER_DELAY = 1 - 1;

    private static final Echeance DEFAULT_PAYMENT = Echeance.MENSUELLE;
    private static final Echeance UPDATED_PAYMENT = Echeance.TRIMESTRIELLE;

    private static final Integer DEFAULT_DELAYED = 0;
    private static final Integer UPDATED_DELAYED = 1;
    private static final Integer SMALLER_DELAYED = 0 - 1;

    private static final ModeRembourssement DEFAULT_REIMBURSEMENT = ModeRembourssement.CONSTANT;
    private static final ModeRembourssement UPDATED_REIMBURSEMENT = ModeRembourssement.DEGRESSIF;

    private static final String DEFAULT_ANALYSE = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_STOP = false;
    private static final Boolean UPDATED_IS_STOP = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_STOPED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STOPED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EngagementRepository engagementRepository;

    @Autowired
    private EngagementMapper engagementMapper;

    @Autowired
    private EngagementService engagementService;

    @Autowired
    private EngagementQueryService engagementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEngagementMockMvc;

    private Engagement engagement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Engagement createEntity(EntityManager em) {
        Engagement engagement = new Engagement()
            .scoring(DEFAULT_SCORING)
            .subject(DEFAULT_SUBJECT)
            .amount(DEFAULT_AMOUNT)
            .delay(DEFAULT_DELAY)
            .payment(DEFAULT_PAYMENT)
            .delayed(DEFAULT_DELAYED)
            .reimbursement(DEFAULT_REIMBURSEMENT)
            .analyse(DEFAULT_ANALYSE)
            .isStop(DEFAULT_IS_STOP)
            .isDeleted(DEFAULT_IS_DELETED)
            .startDate(DEFAULT_START_DATE)
            .enDate(DEFAULT_EN_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .deletedDate(DEFAULT_DELETED_DATE)
            .stopedDate(DEFAULT_STOPED_DATE);
        // Add required entity
        EngagementType engagementType;
        if (TestUtil.findAll(em, EngagementType.class).isEmpty()) {
            engagementType = EngagementTypeResourceIT.createEntity(em);
            em.persist(engagementType);
            em.flush();
        } else {
            engagementType = TestUtil.findAll(em, EngagementType.class).get(0);
        }
        engagement.setType(engagementType);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        engagement.setStatus(status);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        engagement.setClient(client);
        // Add required entity
        Compte compte;
        if (TestUtil.findAll(em, Compte.class).isEmpty()) {
            compte = CompteResourceIT.createEntity(em);
            em.persist(compte);
            em.flush();
        } else {
            compte = TestUtil.findAll(em, Compte.class).get(0);
        }
        engagement.setCompte(compte);
        return engagement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Engagement createUpdatedEntity(EntityManager em) {
        Engagement engagement = new Engagement()
            .scoring(UPDATED_SCORING)
            .subject(UPDATED_SUBJECT)
            .amount(UPDATED_AMOUNT)
            .delay(UPDATED_DELAY)
            .payment(UPDATED_PAYMENT)
            .delayed(UPDATED_DELAYED)
            .reimbursement(UPDATED_REIMBURSEMENT)
            .analyse(UPDATED_ANALYSE)
            .isStop(UPDATED_IS_STOP)
            .isDeleted(UPDATED_IS_DELETED)
            .startDate(UPDATED_START_DATE)
            .enDate(UPDATED_EN_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .stopedDate(UPDATED_STOPED_DATE);
        // Add required entity
        EngagementType engagementType;
        if (TestUtil.findAll(em, EngagementType.class).isEmpty()) {
            engagementType = EngagementTypeResourceIT.createUpdatedEntity(em);
            em.persist(engagementType);
            em.flush();
        } else {
            engagementType = TestUtil.findAll(em, EngagementType.class).get(0);
        }
        engagement.setType(engagementType);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createUpdatedEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        engagement.setStatus(status);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        engagement.setClient(client);
        // Add required entity
        Compte compte;
        if (TestUtil.findAll(em, Compte.class).isEmpty()) {
            compte = CompteResourceIT.createUpdatedEntity(em);
            em.persist(compte);
            em.flush();
        } else {
            compte = TestUtil.findAll(em, Compte.class).get(0);
        }
        engagement.setCompte(compte);
        return engagement;
    }

    @BeforeEach
    public void initTest() {
        engagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEngagement() throws Exception {
        int databaseSizeBeforeCreate = engagementRepository.findAll().size();
        // Create the Engagement
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);
        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Engagement in the database
        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeCreate + 1);
        Engagement testEngagement = engagementList.get(engagementList.size() - 1);
        assertThat(testEngagement.getScoring()).isEqualTo(DEFAULT_SCORING);
        assertThat(testEngagement.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEngagement.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEngagement.getDelay()).isEqualTo(DEFAULT_DELAY);
        assertThat(testEngagement.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testEngagement.getDelayed()).isEqualTo(DEFAULT_DELAYED);
        assertThat(testEngagement.getReimbursement()).isEqualTo(DEFAULT_REIMBURSEMENT);
        assertThat(testEngagement.getAnalyse()).isEqualTo(DEFAULT_ANALYSE);
        assertThat(testEngagement.isIsStop()).isEqualTo(DEFAULT_IS_STOP);
        assertThat(testEngagement.isIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEngagement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEngagement.getEnDate()).isEqualTo(DEFAULT_EN_DATE);
        assertThat(testEngagement.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEngagement.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testEngagement.getDeletedDate()).isEqualTo(DEFAULT_DELETED_DATE);
        assertThat(testEngagement.getStopedDate()).isEqualTo(DEFAULT_STOPED_DATE);
    }

    @Test
    @Transactional
    public void createEngagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = engagementRepository.findAll().size();

        // Create the Engagement with an existing ID
        engagement.setId(1L);
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Engagement in the database
        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkScoringIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setScoring(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setSubject(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setAmount(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelayIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setDelay(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setPayment(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelayedIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setDelayed(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReimbursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementRepository.findAll().size();
        // set the field null
        engagement.setReimbursement(null);

        // Create the Engagement, which fails.
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        restEngagementMockMvc
            .perform(
                post("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEngagements() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList
        restEngagementMockMvc
            .perform(get("/api/engagements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(engagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].scoring").value(hasItem(DEFAULT_SCORING)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].delay").value(hasItem(DEFAULT_DELAY)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT.toString())))
            .andExpect(jsonPath("$.[*].delayed").value(hasItem(DEFAULT_DELAYED)))
            .andExpect(jsonPath("$.[*].reimbursement").value(hasItem(DEFAULT_REIMBURSEMENT.toString())))
            .andExpect(jsonPath("$.[*].analyse").value(hasItem(DEFAULT_ANALYSE.toString())))
            .andExpect(jsonPath("$.[*].isStop").value(hasItem(DEFAULT_IS_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].enDate").value(hasItem(DEFAULT_EN_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].stopedDate").value(hasItem(DEFAULT_STOPED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getEngagement() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get the engagement
        restEngagementMockMvc
            .perform(get("/api/engagements/{id}", engagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(engagement.getId().intValue()))
            .andExpect(jsonPath("$.scoring").value(DEFAULT_SCORING))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.delay").value(DEFAULT_DELAY))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT.toString()))
            .andExpect(jsonPath("$.delayed").value(DEFAULT_DELAYED))
            .andExpect(jsonPath("$.reimbursement").value(DEFAULT_REIMBURSEMENT.toString()))
            .andExpect(jsonPath("$.analyse").value(DEFAULT_ANALYSE.toString()))
            .andExpect(jsonPath("$.isStop").value(DEFAULT_IS_STOP.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.enDate").value(DEFAULT_EN_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.deletedDate").value(DEFAULT_DELETED_DATE.toString()))
            .andExpect(jsonPath("$.stopedDate").value(DEFAULT_STOPED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getEngagementsByIdFiltering() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        Long id = engagement.getId();

        defaultEngagementShouldBeFound("id.equals=" + id);
        defaultEngagementShouldNotBeFound("id.notEquals=" + id);

        defaultEngagementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEngagementShouldNotBeFound("id.greaterThan=" + id);

        defaultEngagementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEngagementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring equals to DEFAULT_SCORING
        defaultEngagementShouldBeFound("scoring.equals=" + DEFAULT_SCORING);

        // Get all the engagementList where scoring equals to UPDATED_SCORING
        defaultEngagementShouldNotBeFound("scoring.equals=" + UPDATED_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring not equals to DEFAULT_SCORING
        defaultEngagementShouldNotBeFound("scoring.notEquals=" + DEFAULT_SCORING);

        // Get all the engagementList where scoring not equals to UPDATED_SCORING
        defaultEngagementShouldBeFound("scoring.notEquals=" + UPDATED_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring in DEFAULT_SCORING or UPDATED_SCORING
        defaultEngagementShouldBeFound("scoring.in=" + DEFAULT_SCORING + "," + UPDATED_SCORING);

        // Get all the engagementList where scoring equals to UPDATED_SCORING
        defaultEngagementShouldNotBeFound("scoring.in=" + UPDATED_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring is not null
        defaultEngagementShouldBeFound("scoring.specified=true");

        // Get all the engagementList where scoring is null
        defaultEngagementShouldNotBeFound("scoring.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring is greater than or equal to DEFAULT_SCORING
        defaultEngagementShouldBeFound("scoring.greaterThanOrEqual=" + DEFAULT_SCORING);

        // Get all the engagementList where scoring is greater than or equal to UPDATED_SCORING
        defaultEngagementShouldNotBeFound("scoring.greaterThanOrEqual=" + UPDATED_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring is less than or equal to DEFAULT_SCORING
        defaultEngagementShouldBeFound("scoring.lessThanOrEqual=" + DEFAULT_SCORING);

        // Get all the engagementList where scoring is less than or equal to SMALLER_SCORING
        defaultEngagementShouldNotBeFound("scoring.lessThanOrEqual=" + SMALLER_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsLessThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring is less than DEFAULT_SCORING
        defaultEngagementShouldNotBeFound("scoring.lessThan=" + DEFAULT_SCORING);

        // Get all the engagementList where scoring is less than UPDATED_SCORING
        defaultEngagementShouldBeFound("scoring.lessThan=" + UPDATED_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsByScoringIsGreaterThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where scoring is greater than DEFAULT_SCORING
        defaultEngagementShouldNotBeFound("scoring.greaterThan=" + DEFAULT_SCORING);

        // Get all the engagementList where scoring is greater than SMALLER_SCORING
        defaultEngagementShouldBeFound("scoring.greaterThan=" + SMALLER_SCORING);
    }

    @Test
    @Transactional
    public void getAllEngagementsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where subject equals to DEFAULT_SUBJECT
        defaultEngagementShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the engagementList where subject equals to UPDATED_SUBJECT
        defaultEngagementShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEngagementsBySubjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where subject not equals to DEFAULT_SUBJECT
        defaultEngagementShouldNotBeFound("subject.notEquals=" + DEFAULT_SUBJECT);

        // Get all the engagementList where subject not equals to UPDATED_SUBJECT
        defaultEngagementShouldBeFound("subject.notEquals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEngagementsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultEngagementShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the engagementList where subject equals to UPDATED_SUBJECT
        defaultEngagementShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEngagementsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where subject is not null
        defaultEngagementShouldBeFound("subject.specified=true");

        // Get all the engagementList where subject is null
        defaultEngagementShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsBySubjectContainsSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where subject contains DEFAULT_SUBJECT
        defaultEngagementShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the engagementList where subject contains UPDATED_SUBJECT
        defaultEngagementShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEngagementsBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where subject does not contain DEFAULT_SUBJECT
        defaultEngagementShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the engagementList where subject does not contain UPDATED_SUBJECT
        defaultEngagementShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount equals to DEFAULT_AMOUNT
        defaultEngagementShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the engagementList where amount equals to UPDATED_AMOUNT
        defaultEngagementShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount not equals to DEFAULT_AMOUNT
        defaultEngagementShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the engagementList where amount not equals to UPDATED_AMOUNT
        defaultEngagementShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultEngagementShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the engagementList where amount equals to UPDATED_AMOUNT
        defaultEngagementShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount is not null
        defaultEngagementShouldBeFound("amount.specified=true");

        // Get all the engagementList where amount is null
        defaultEngagementShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultEngagementShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the engagementList where amount is greater than or equal to UPDATED_AMOUNT
        defaultEngagementShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount is less than or equal to DEFAULT_AMOUNT
        defaultEngagementShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the engagementList where amount is less than or equal to SMALLER_AMOUNT
        defaultEngagementShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount is less than DEFAULT_AMOUNT
        defaultEngagementShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the engagementList where amount is less than UPDATED_AMOUNT
        defaultEngagementShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where amount is greater than DEFAULT_AMOUNT
        defaultEngagementShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the engagementList where amount is greater than SMALLER_AMOUNT
        defaultEngagementShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay equals to DEFAULT_DELAY
        defaultEngagementShouldBeFound("delay.equals=" + DEFAULT_DELAY);

        // Get all the engagementList where delay equals to UPDATED_DELAY
        defaultEngagementShouldNotBeFound("delay.equals=" + UPDATED_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay not equals to DEFAULT_DELAY
        defaultEngagementShouldNotBeFound("delay.notEquals=" + DEFAULT_DELAY);

        // Get all the engagementList where delay not equals to UPDATED_DELAY
        defaultEngagementShouldBeFound("delay.notEquals=" + UPDATED_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay in DEFAULT_DELAY or UPDATED_DELAY
        defaultEngagementShouldBeFound("delay.in=" + DEFAULT_DELAY + "," + UPDATED_DELAY);

        // Get all the engagementList where delay equals to UPDATED_DELAY
        defaultEngagementShouldNotBeFound("delay.in=" + UPDATED_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay is not null
        defaultEngagementShouldBeFound("delay.specified=true");

        // Get all the engagementList where delay is null
        defaultEngagementShouldNotBeFound("delay.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay is greater than or equal to DEFAULT_DELAY
        defaultEngagementShouldBeFound("delay.greaterThanOrEqual=" + DEFAULT_DELAY);

        // Get all the engagementList where delay is greater than or equal to UPDATED_DELAY
        defaultEngagementShouldNotBeFound("delay.greaterThanOrEqual=" + UPDATED_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay is less than or equal to DEFAULT_DELAY
        defaultEngagementShouldBeFound("delay.lessThanOrEqual=" + DEFAULT_DELAY);

        // Get all the engagementList where delay is less than or equal to SMALLER_DELAY
        defaultEngagementShouldNotBeFound("delay.lessThanOrEqual=" + SMALLER_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsLessThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay is less than DEFAULT_DELAY
        defaultEngagementShouldNotBeFound("delay.lessThan=" + DEFAULT_DELAY);

        // Get all the engagementList where delay is less than UPDATED_DELAY
        defaultEngagementShouldBeFound("delay.lessThan=" + UPDATED_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delay is greater than DEFAULT_DELAY
        defaultEngagementShouldNotBeFound("delay.greaterThan=" + DEFAULT_DELAY);

        // Get all the engagementList where delay is greater than SMALLER_DELAY
        defaultEngagementShouldBeFound("delay.greaterThan=" + SMALLER_DELAY);
    }

    @Test
    @Transactional
    public void getAllEngagementsByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where payment equals to DEFAULT_PAYMENT
        defaultEngagementShouldBeFound("payment.equals=" + DEFAULT_PAYMENT);

        // Get all the engagementList where payment equals to UPDATED_PAYMENT
        defaultEngagementShouldNotBeFound("payment.equals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByPaymentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where payment not equals to DEFAULT_PAYMENT
        defaultEngagementShouldNotBeFound("payment.notEquals=" + DEFAULT_PAYMENT);

        // Get all the engagementList where payment not equals to UPDATED_PAYMENT
        defaultEngagementShouldBeFound("payment.notEquals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where payment in DEFAULT_PAYMENT or UPDATED_PAYMENT
        defaultEngagementShouldBeFound("payment.in=" + DEFAULT_PAYMENT + "," + UPDATED_PAYMENT);

        // Get all the engagementList where payment equals to UPDATED_PAYMENT
        defaultEngagementShouldNotBeFound("payment.in=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where payment is not null
        defaultEngagementShouldBeFound("payment.specified=true");

        // Get all the engagementList where payment is null
        defaultEngagementShouldNotBeFound("payment.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed equals to DEFAULT_DELAYED
        defaultEngagementShouldBeFound("delayed.equals=" + DEFAULT_DELAYED);

        // Get all the engagementList where delayed equals to UPDATED_DELAYED
        defaultEngagementShouldNotBeFound("delayed.equals=" + UPDATED_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed not equals to DEFAULT_DELAYED
        defaultEngagementShouldNotBeFound("delayed.notEquals=" + DEFAULT_DELAYED);

        // Get all the engagementList where delayed not equals to UPDATED_DELAYED
        defaultEngagementShouldBeFound("delayed.notEquals=" + UPDATED_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed in DEFAULT_DELAYED or UPDATED_DELAYED
        defaultEngagementShouldBeFound("delayed.in=" + DEFAULT_DELAYED + "," + UPDATED_DELAYED);

        // Get all the engagementList where delayed equals to UPDATED_DELAYED
        defaultEngagementShouldNotBeFound("delayed.in=" + UPDATED_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed is not null
        defaultEngagementShouldBeFound("delayed.specified=true");

        // Get all the engagementList where delayed is null
        defaultEngagementShouldNotBeFound("delayed.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed is greater than or equal to DEFAULT_DELAYED
        defaultEngagementShouldBeFound("delayed.greaterThanOrEqual=" + DEFAULT_DELAYED);

        // Get all the engagementList where delayed is greater than or equal to UPDATED_DELAYED
        defaultEngagementShouldNotBeFound("delayed.greaterThanOrEqual=" + UPDATED_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed is less than or equal to DEFAULT_DELAYED
        defaultEngagementShouldBeFound("delayed.lessThanOrEqual=" + DEFAULT_DELAYED);

        // Get all the engagementList where delayed is less than or equal to SMALLER_DELAYED
        defaultEngagementShouldNotBeFound("delayed.lessThanOrEqual=" + SMALLER_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsLessThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed is less than DEFAULT_DELAYED
        defaultEngagementShouldNotBeFound("delayed.lessThan=" + DEFAULT_DELAYED);

        // Get all the engagementList where delayed is less than UPDATED_DELAYED
        defaultEngagementShouldBeFound("delayed.lessThan=" + UPDATED_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDelayedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where delayed is greater than DEFAULT_DELAYED
        defaultEngagementShouldNotBeFound("delayed.greaterThan=" + DEFAULT_DELAYED);

        // Get all the engagementList where delayed is greater than SMALLER_DELAYED
        defaultEngagementShouldBeFound("delayed.greaterThan=" + SMALLER_DELAYED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByReimbursementIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where reimbursement equals to DEFAULT_REIMBURSEMENT
        defaultEngagementShouldBeFound("reimbursement.equals=" + DEFAULT_REIMBURSEMENT);

        // Get all the engagementList where reimbursement equals to UPDATED_REIMBURSEMENT
        defaultEngagementShouldNotBeFound("reimbursement.equals=" + UPDATED_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByReimbursementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where reimbursement not equals to DEFAULT_REIMBURSEMENT
        defaultEngagementShouldNotBeFound("reimbursement.notEquals=" + DEFAULT_REIMBURSEMENT);

        // Get all the engagementList where reimbursement not equals to UPDATED_REIMBURSEMENT
        defaultEngagementShouldBeFound("reimbursement.notEquals=" + UPDATED_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByReimbursementIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where reimbursement in DEFAULT_REIMBURSEMENT or UPDATED_REIMBURSEMENT
        defaultEngagementShouldBeFound("reimbursement.in=" + DEFAULT_REIMBURSEMENT + "," + UPDATED_REIMBURSEMENT);

        // Get all the engagementList where reimbursement equals to UPDATED_REIMBURSEMENT
        defaultEngagementShouldNotBeFound("reimbursement.in=" + UPDATED_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void getAllEngagementsByReimbursementIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where reimbursement is not null
        defaultEngagementShouldBeFound("reimbursement.specified=true");

        // Get all the engagementList where reimbursement is null
        defaultEngagementShouldNotBeFound("reimbursement.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsStopIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isStop equals to DEFAULT_IS_STOP
        defaultEngagementShouldBeFound("isStop.equals=" + DEFAULT_IS_STOP);

        // Get all the engagementList where isStop equals to UPDATED_IS_STOP
        defaultEngagementShouldNotBeFound("isStop.equals=" + UPDATED_IS_STOP);
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsStopIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isStop not equals to DEFAULT_IS_STOP
        defaultEngagementShouldNotBeFound("isStop.notEquals=" + DEFAULT_IS_STOP);

        // Get all the engagementList where isStop not equals to UPDATED_IS_STOP
        defaultEngagementShouldBeFound("isStop.notEquals=" + UPDATED_IS_STOP);
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsStopIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isStop in DEFAULT_IS_STOP or UPDATED_IS_STOP
        defaultEngagementShouldBeFound("isStop.in=" + DEFAULT_IS_STOP + "," + UPDATED_IS_STOP);

        // Get all the engagementList where isStop equals to UPDATED_IS_STOP
        defaultEngagementShouldNotBeFound("isStop.in=" + UPDATED_IS_STOP);
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsStopIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isStop is not null
        defaultEngagementShouldBeFound("isStop.specified=true");

        // Get all the engagementList where isStop is null
        defaultEngagementShouldNotBeFound("isStop.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isDeleted equals to DEFAULT_IS_DELETED
        defaultEngagementShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the engagementList where isDeleted equals to UPDATED_IS_DELETED
        defaultEngagementShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultEngagementShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the engagementList where isDeleted not equals to UPDATED_IS_DELETED
        defaultEngagementShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultEngagementShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the engagementList where isDeleted equals to UPDATED_IS_DELETED
        defaultEngagementShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    public void getAllEngagementsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where isDeleted is not null
        defaultEngagementShouldBeFound("isDeleted.specified=true");

        // Get all the engagementList where isDeleted is null
        defaultEngagementShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where startDate equals to DEFAULT_START_DATE
        defaultEngagementShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the engagementList where startDate equals to UPDATED_START_DATE
        defaultEngagementShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where startDate not equals to DEFAULT_START_DATE
        defaultEngagementShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the engagementList where startDate not equals to UPDATED_START_DATE
        defaultEngagementShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultEngagementShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the engagementList where startDate equals to UPDATED_START_DATE
        defaultEngagementShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where startDate is not null
        defaultEngagementShouldBeFound("startDate.specified=true");

        // Get all the engagementList where startDate is null
        defaultEngagementShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByEnDateIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where enDate equals to DEFAULT_EN_DATE
        defaultEngagementShouldBeFound("enDate.equals=" + DEFAULT_EN_DATE);

        // Get all the engagementList where enDate equals to UPDATED_EN_DATE
        defaultEngagementShouldNotBeFound("enDate.equals=" + UPDATED_EN_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByEnDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where enDate not equals to DEFAULT_EN_DATE
        defaultEngagementShouldNotBeFound("enDate.notEquals=" + DEFAULT_EN_DATE);

        // Get all the engagementList where enDate not equals to UPDATED_EN_DATE
        defaultEngagementShouldBeFound("enDate.notEquals=" + UPDATED_EN_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByEnDateIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where enDate in DEFAULT_EN_DATE or UPDATED_EN_DATE
        defaultEngagementShouldBeFound("enDate.in=" + DEFAULT_EN_DATE + "," + UPDATED_EN_DATE);

        // Get all the engagementList where enDate equals to UPDATED_EN_DATE
        defaultEngagementShouldNotBeFound("enDate.in=" + UPDATED_EN_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByEnDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where enDate is not null
        defaultEngagementShouldBeFound("enDate.specified=true");

        // Get all the engagementList where enDate is null
        defaultEngagementShouldNotBeFound("enDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEngagementShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the engagementList where createdDate equals to UPDATED_CREATED_DATE
        defaultEngagementShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEngagementShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the engagementList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEngagementShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEngagementShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the engagementList where createdDate equals to UPDATED_CREATED_DATE
        defaultEngagementShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where createdDate is not null
        defaultEngagementShouldBeFound("createdDate.specified=true");

        // Get all the engagementList where createdDate is null
        defaultEngagementShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultEngagementShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the engagementList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultEngagementShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultEngagementShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the engagementList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultEngagementShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultEngagementShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the engagementList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultEngagementShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where updatedDate is not null
        defaultEngagementShouldBeFound("updatedDate.specified=true");

        // Get all the engagementList where updatedDate is null
        defaultEngagementShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByDeletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where deletedDate equals to DEFAULT_DELETED_DATE
        defaultEngagementShouldBeFound("deletedDate.equals=" + DEFAULT_DELETED_DATE);

        // Get all the engagementList where deletedDate equals to UPDATED_DELETED_DATE
        defaultEngagementShouldNotBeFound("deletedDate.equals=" + UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDeletedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where deletedDate not equals to DEFAULT_DELETED_DATE
        defaultEngagementShouldNotBeFound("deletedDate.notEquals=" + DEFAULT_DELETED_DATE);

        // Get all the engagementList where deletedDate not equals to UPDATED_DELETED_DATE
        defaultEngagementShouldBeFound("deletedDate.notEquals=" + UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDeletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where deletedDate in DEFAULT_DELETED_DATE or UPDATED_DELETED_DATE
        defaultEngagementShouldBeFound("deletedDate.in=" + DEFAULT_DELETED_DATE + "," + UPDATED_DELETED_DATE);

        // Get all the engagementList where deletedDate equals to UPDATED_DELETED_DATE
        defaultEngagementShouldNotBeFound("deletedDate.in=" + UPDATED_DELETED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByDeletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where deletedDate is not null
        defaultEngagementShouldBeFound("deletedDate.specified=true");

        // Get all the engagementList where deletedDate is null
        defaultEngagementShouldNotBeFound("deletedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByStopedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where stopedDate equals to DEFAULT_STOPED_DATE
        defaultEngagementShouldBeFound("stopedDate.equals=" + DEFAULT_STOPED_DATE);

        // Get all the engagementList where stopedDate equals to UPDATED_STOPED_DATE
        defaultEngagementShouldNotBeFound("stopedDate.equals=" + UPDATED_STOPED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByStopedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where stopedDate not equals to DEFAULT_STOPED_DATE
        defaultEngagementShouldNotBeFound("stopedDate.notEquals=" + DEFAULT_STOPED_DATE);

        // Get all the engagementList where stopedDate not equals to UPDATED_STOPED_DATE
        defaultEngagementShouldBeFound("stopedDate.notEquals=" + UPDATED_STOPED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByStopedDateIsInShouldWork() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where stopedDate in DEFAULT_STOPED_DATE or UPDATED_STOPED_DATE
        defaultEngagementShouldBeFound("stopedDate.in=" + DEFAULT_STOPED_DATE + "," + UPDATED_STOPED_DATE);

        // Get all the engagementList where stopedDate equals to UPDATED_STOPED_DATE
        defaultEngagementShouldNotBeFound("stopedDate.in=" + UPDATED_STOPED_DATE);
    }

    @Test
    @Transactional
    public void getAllEngagementsByStopedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        // Get all the engagementList where stopedDate is not null
        defaultEngagementShouldBeFound("stopedDate.specified=true");

        // Get all the engagementList where stopedDate is null
        defaultEngagementShouldNotBeFound("stopedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEngagementsByCurrentTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        Task currentTask = TaskResourceIT.createEntity(em);
        em.persist(currentTask);
        em.flush();
        engagement.setCurrentTask(currentTask);
        engagementRepository.saveAndFlush(engagement);
        Long currentTaskId = currentTask.getId();

        // Get all the engagementList where currentTask equals to currentTaskId
        defaultEngagementShouldBeFound("currentTaskId.equals=" + currentTaskId);

        // Get all the engagementList where currentTask equals to currentTaskId + 1
        defaultEngagementShouldNotBeFound("currentTaskId.equals=" + (currentTaskId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByDowngradingIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        Downgrading downgrading = DowngradingResourceIT.createEntity(em);
        em.persist(downgrading);
        em.flush();
        engagement.addDowngrading(downgrading);
        engagementRepository.saveAndFlush(engagement);
        Long downgradingId = downgrading.getId();

        // Get all the engagementList where downgrading equals to downgradingId
        defaultEngagementShouldBeFound("downgradingId.equals=" + downgradingId);

        // Get all the engagementList where downgrading equals to downgradingId + 1
        defaultEngagementShouldNotBeFound("downgradingId.equals=" + (downgradingId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByRejectIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        Reject reject = RejectResourceIT.createEntity(em);
        em.persist(reject);
        em.flush();
        engagement.addReject(reject);
        engagementRepository.saveAndFlush(engagement);
        Long rejectId = reject.getId();

        // Get all the engagementList where reject equals to rejectId
        defaultEngagementShouldBeFound("rejectId.equals=" + rejectId);

        // Get all the engagementList where reject equals to rejectId + 1
        defaultEngagementShouldNotBeFound("rejectId.equals=" + (rejectId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        Task task = TaskResourceIT.createEntity(em);
        em.persist(task);
        em.flush();
        engagement.addTask(task);
        engagementRepository.saveAndFlush(engagement);
        Long taskId = task.getId();

        // Get all the engagementList where task equals to taskId
        defaultEngagementShouldBeFound("taskId.equals=" + taskId);

        // Get all the engagementList where task equals to taskId + 1
        defaultEngagementShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        Notes note = NotesResourceIT.createEntity(em);
        em.persist(note);
        em.flush();
        engagement.addNote(note);
        engagementRepository.saveAndFlush(engagement);
        Long noteId = note.getId();

        // Get all the engagementList where note equals to noteId
        defaultEngagementShouldBeFound("noteId.equals=" + noteId);

        // Get all the engagementList where note equals to noteId + 1
        defaultEngagementShouldNotBeFound("noteId.equals=" + (noteId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        EngagementType type = engagement.getType();
        engagementRepository.saveAndFlush(engagement);
        Long typeId = type.getId();

        // Get all the engagementList where type equals to typeId
        defaultEngagementShouldBeFound("typeId.equals=" + typeId);

        // Get all the engagementList where type equals to typeId + 1
        defaultEngagementShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByStatusIsEqualToSomething() throws Exception {
        // Get already existing entity
        Status status = engagement.getStatus();
        engagementRepository.saveAndFlush(engagement);
        Long statusId = status.getId();

        // Get all the engagementList where status equals to statusId
        defaultEngagementShouldBeFound("statusId.equals=" + statusId);

        // Get all the engagementList where status equals to statusId + 1
        defaultEngagementShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByDecisionIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        Status decision = StatusResourceIT.createEntity(em);
        em.persist(decision);
        em.flush();
        engagement.setDecision(decision);
        engagementRepository.saveAndFlush(engagement);
        Long decisionId = decision.getId();

        // Get all the engagementList where decision equals to decisionId
        defaultEngagementShouldBeFound("decisionId.equals=" + decisionId);

        // Get all the engagementList where decision equals to decisionId + 1
        defaultEngagementShouldNotBeFound("decisionId.equals=" + (decisionId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);
        User createdBy = UserResourceIT.createEntity(em);
        em.persist(createdBy);
        em.flush();
        engagement.setCreatedBy(createdBy);
        engagementRepository.saveAndFlush(engagement);
        Long createdById = createdBy.getId();

        // Get all the engagementList where createdBy equals to createdById
        defaultEngagementShouldBeFound("createdById.equals=" + createdById);

        // Get all the engagementList where createdBy equals to createdById + 1
        defaultEngagementShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByClientIsEqualToSomething() throws Exception {
        // Get already existing entity
        Client client = engagement.getClient();
        engagementRepository.saveAndFlush(engagement);
        Long clientId = client.getId();

        // Get all the engagementList where client equals to clientId
        defaultEngagementShouldBeFound("clientId.equals=" + clientId);

        // Get all the engagementList where client equals to clientId + 1
        defaultEngagementShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    @Test
    @Transactional
    public void getAllEngagementsByCompteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Compte compte = engagement.getCompte();
        engagementRepository.saveAndFlush(engagement);
        Long compteId = compte.getId();

        // Get all the engagementList where compte equals to compteId
        defaultEngagementShouldBeFound("compteId.equals=" + compteId);

        // Get all the engagementList where compte equals to compteId + 1
        defaultEngagementShouldNotBeFound("compteId.equals=" + (compteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEngagementShouldBeFound(String filter) throws Exception {
        restEngagementMockMvc
            .perform(get("/api/engagements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(engagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].scoring").value(hasItem(DEFAULT_SCORING)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].delay").value(hasItem(DEFAULT_DELAY)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT.toString())))
            .andExpect(jsonPath("$.[*].delayed").value(hasItem(DEFAULT_DELAYED)))
            .andExpect(jsonPath("$.[*].reimbursement").value(hasItem(DEFAULT_REIMBURSEMENT.toString())))
            .andExpect(jsonPath("$.[*].analyse").value(hasItem(DEFAULT_ANALYSE.toString())))
            .andExpect(jsonPath("$.[*].isStop").value(hasItem(DEFAULT_IS_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].enDate").value(hasItem(DEFAULT_EN_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deletedDate").value(hasItem(DEFAULT_DELETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].stopedDate").value(hasItem(DEFAULT_STOPED_DATE.toString())));

        // Check, that the count call also returns 1
        restEngagementMockMvc
            .perform(get("/api/engagements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEngagementShouldNotBeFound(String filter) throws Exception {
        restEngagementMockMvc
            .perform(get("/api/engagements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEngagementMockMvc
            .perform(get("/api/engagements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEngagement() throws Exception {
        // Get the engagement
        restEngagementMockMvc.perform(get("/api/engagements/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEngagement() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        int databaseSizeBeforeUpdate = engagementRepository.findAll().size();

        // Update the engagement
        Engagement updatedEngagement = engagementRepository.findById(engagement.getId()).get();
        // Disconnect from session so that the updates on updatedEngagement are not directly saved in db
        em.detach(updatedEngagement);
        updatedEngagement
            .scoring(UPDATED_SCORING)
            .subject(UPDATED_SUBJECT)
            .amount(UPDATED_AMOUNT)
            .delay(UPDATED_DELAY)
            .payment(UPDATED_PAYMENT)
            .delayed(UPDATED_DELAYED)
            .reimbursement(UPDATED_REIMBURSEMENT)
            .analyse(UPDATED_ANALYSE)
            .isStop(UPDATED_IS_STOP)
            .isDeleted(UPDATED_IS_DELETED)
            .startDate(UPDATED_START_DATE)
            .enDate(UPDATED_EN_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .deletedDate(UPDATED_DELETED_DATE)
            .stopedDate(UPDATED_STOPED_DATE);
        EngagementDTO engagementDTO = engagementMapper.toDto(updatedEngagement);

        restEngagementMockMvc
            .perform(
                put("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Engagement in the database
        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeUpdate);
        Engagement testEngagement = engagementList.get(engagementList.size() - 1);
        assertThat(testEngagement.getScoring()).isEqualTo(UPDATED_SCORING);
        assertThat(testEngagement.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEngagement.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEngagement.getDelay()).isEqualTo(UPDATED_DELAY);
        assertThat(testEngagement.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testEngagement.getDelayed()).isEqualTo(UPDATED_DELAYED);
        assertThat(testEngagement.getReimbursement()).isEqualTo(UPDATED_REIMBURSEMENT);
        assertThat(testEngagement.getAnalyse()).isEqualTo(UPDATED_ANALYSE);
        assertThat(testEngagement.isIsStop()).isEqualTo(UPDATED_IS_STOP);
        assertThat(testEngagement.isIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEngagement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEngagement.getEnDate()).isEqualTo(UPDATED_EN_DATE);
        assertThat(testEngagement.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEngagement.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testEngagement.getDeletedDate()).isEqualTo(UPDATED_DELETED_DATE);
        assertThat(testEngagement.getStopedDate()).isEqualTo(UPDATED_STOPED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEngagement() throws Exception {
        int databaseSizeBeforeUpdate = engagementRepository.findAll().size();

        // Create the Engagement
        EngagementDTO engagementDTO = engagementMapper.toDto(engagement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEngagementMockMvc
            .perform(
                put("/api/engagements").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(engagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Engagement in the database
        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEngagement() throws Exception {
        // Initialize the database
        engagementRepository.saveAndFlush(engagement);

        int databaseSizeBeforeDelete = engagementRepository.findAll().size();

        // Delete the engagement
        restEngagementMockMvc
            .perform(delete("/api/engagements/{id}", engagement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Engagement> engagementList = engagementRepository.findAll();
        assertThat(engagementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
