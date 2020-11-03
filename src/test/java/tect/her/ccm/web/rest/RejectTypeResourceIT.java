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
import tect.her.ccm.domain.RejectType;
import tect.her.ccm.domain.Scoring;
import tect.her.ccm.repository.RejectTypeRepository;
import tect.her.ccm.service.RejectTypeService;
import tect.her.ccm.service.dto.RejectTypeDTO;
import tect.her.ccm.service.mapper.RejectTypeMapper;

/**
 * Integration tests for the {@link RejectTypeResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RejectTypeResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private RejectTypeRepository rejectTypeRepository;

    @Autowired
    private RejectTypeMapper rejectTypeMapper;

    @Autowired
    private RejectTypeService rejectTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRejectTypeMockMvc;

    private RejectType rejectType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RejectType createEntity(EntityManager em) {
        RejectType rejectType = new RejectType().label(DEFAULT_LABEL).identifier(DEFAULT_IDENTIFIER);
        // Add required entity
        Scoring scoring;
        if (TestUtil.findAll(em, Scoring.class).isEmpty()) {
            scoring = ScoringResourceIT.createEntity(em);
            em.persist(scoring);
            em.flush();
        } else {
            scoring = TestUtil.findAll(em, Scoring.class).get(0);
        }
        rejectType.setScoring(scoring);
        return rejectType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RejectType createUpdatedEntity(EntityManager em) {
        RejectType rejectType = new RejectType().label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER);
        // Add required entity
        Scoring scoring;
        if (TestUtil.findAll(em, Scoring.class).isEmpty()) {
            scoring = ScoringResourceIT.createUpdatedEntity(em);
            em.persist(scoring);
            em.flush();
        } else {
            scoring = TestUtil.findAll(em, Scoring.class).get(0);
        }
        rejectType.setScoring(scoring);
        return rejectType;
    }

    @BeforeEach
    public void initTest() {
        rejectType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRejectType() throws Exception {
        int databaseSizeBeforeCreate = rejectTypeRepository.findAll().size();
        // Create the RejectType
        RejectTypeDTO rejectTypeDTO = rejectTypeMapper.toDto(rejectType);
        restRejectTypeMockMvc
            .perform(
                post("/api/reject-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RejectType in the database
        List<RejectType> rejectTypeList = rejectTypeRepository.findAll();
        assertThat(rejectTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RejectType testRejectType = rejectTypeList.get(rejectTypeList.size() - 1);
        assertThat(testRejectType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testRejectType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createRejectTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rejectTypeRepository.findAll().size();

        // Create the RejectType with an existing ID
        rejectType.setId(1L);
        RejectTypeDTO rejectTypeDTO = rejectTypeMapper.toDto(rejectType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRejectTypeMockMvc
            .perform(
                post("/api/reject-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RejectType in the database
        List<RejectType> rejectTypeList = rejectTypeRepository.findAll();
        assertThat(rejectTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectTypeRepository.findAll().size();
        // set the field null
        rejectType.setLabel(null);

        // Create the RejectType, which fails.
        RejectTypeDTO rejectTypeDTO = rejectTypeMapper.toDto(rejectType);

        restRejectTypeMockMvc
            .perform(
                post("/api/reject-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<RejectType> rejectTypeList = rejectTypeRepository.findAll();
        assertThat(rejectTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRejectTypes() throws Exception {
        // Initialize the database
        rejectTypeRepository.saveAndFlush(rejectType);

        // Get all the rejectTypeList
        restRejectTypeMockMvc
            .perform(get("/api/reject-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rejectType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)));
    }

    @Test
    @Transactional
    public void getRejectType() throws Exception {
        // Initialize the database
        rejectTypeRepository.saveAndFlush(rejectType);

        // Get the rejectType
        restRejectTypeMockMvc
            .perform(get("/api/reject-types/{id}", rejectType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rejectType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER));
    }

    @Test
    @Transactional
    public void getNonExistingRejectType() throws Exception {
        // Get the rejectType
        restRejectTypeMockMvc.perform(get("/api/reject-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRejectType() throws Exception {
        // Initialize the database
        rejectTypeRepository.saveAndFlush(rejectType);

        int databaseSizeBeforeUpdate = rejectTypeRepository.findAll().size();

        // Update the rejectType
        RejectType updatedRejectType = rejectTypeRepository.findById(rejectType.getId()).get();
        // Disconnect from session so that the updates on updatedRejectType are not directly saved in db
        em.detach(updatedRejectType);
        updatedRejectType.label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER);
        RejectTypeDTO rejectTypeDTO = rejectTypeMapper.toDto(updatedRejectType);

        restRejectTypeMockMvc
            .perform(
                put("/api/reject-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the RejectType in the database
        List<RejectType> rejectTypeList = rejectTypeRepository.findAll();
        assertThat(rejectTypeList).hasSize(databaseSizeBeforeUpdate);
        RejectType testRejectType = rejectTypeList.get(rejectTypeList.size() - 1);
        assertThat(testRejectType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testRejectType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingRejectType() throws Exception {
        int databaseSizeBeforeUpdate = rejectTypeRepository.findAll().size();

        // Create the RejectType
        RejectTypeDTO rejectTypeDTO = rejectTypeMapper.toDto(rejectType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRejectTypeMockMvc
            .perform(
                put("/api/reject-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rejectTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RejectType in the database
        List<RejectType> rejectTypeList = rejectTypeRepository.findAll();
        assertThat(rejectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRejectType() throws Exception {
        // Initialize the database
        rejectTypeRepository.saveAndFlush(rejectType);

        int databaseSizeBeforeDelete = rejectTypeRepository.findAll().size();

        // Delete the rejectType
        restRejectTypeMockMvc
            .perform(delete("/api/reject-types/{id}", rejectType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RejectType> rejectTypeList = rejectTypeRepository.findAll();
        assertThat(rejectTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
