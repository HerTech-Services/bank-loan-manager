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
import tect.her.ccm.service.RejectService;
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

    private static final BigDecimal DEFAULT_INTERESTS = new BigDecimal(0);
    private static final BigDecimal UPDATED_INTERESTS = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PENALTIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_PENALTIES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_ACCESSORIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_ACCESSORIES = new BigDecimal(1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RejectRepository rejectRepository;

    @Autowired
    private RejectMapper rejectMapper;

    @Autowired
    private RejectService rejectService;

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
