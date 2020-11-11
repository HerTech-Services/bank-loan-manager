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
import tect.her.ccm.domain.EngagementType;
import tect.her.ccm.repository.EngagementTypeRepository;
import tect.her.ccm.service.EngagementTypeService;
import tect.her.ccm.service.dto.EngagementTypeDTO;
import tect.her.ccm.service.mapper.EngagementTypeMapper;

/**
 * Integration tests for the {@link EngagementTypeResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EngagementTypeResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final Integer DEFAULT_PROCESS_DELAY = 1;
    private static final Integer UPDATED_PROCESS_DELAY = 2;

    private static final Integer DEFAULT_DELAY_1 = 1;
    private static final Integer UPDATED_DELAY_1 = 2;

    private static final Integer DEFAULT_DELAY_2 = 1;
    private static final Integer UPDATED_DELAY_2 = 2;

    @Autowired
    private EngagementTypeRepository engagementTypeRepository;

    @Autowired
    private EngagementTypeMapper engagementTypeMapper;

    @Autowired
    private EngagementTypeService engagementTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEngagementTypeMockMvc;

    private EngagementType engagementType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EngagementType createEntity(EntityManager em) {
        EngagementType engagementType = new EngagementType()
            .label(DEFAULT_LABEL)
            .isEnabled(DEFAULT_IS_ENABLED)
            .processDelay(DEFAULT_PROCESS_DELAY)
            .delay1(DEFAULT_DELAY_1)
            .delay2(DEFAULT_DELAY_2);
        return engagementType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EngagementType createUpdatedEntity(EntityManager em) {
        EngagementType engagementType = new EngagementType()
            .label(UPDATED_LABEL)
            .isEnabled(UPDATED_IS_ENABLED)
            .processDelay(UPDATED_PROCESS_DELAY)
            .delay1(UPDATED_DELAY_1)
            .delay2(UPDATED_DELAY_2);
        return engagementType;
    }

    @BeforeEach
    public void initTest() {
        engagementType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEngagementType() throws Exception {
        int databaseSizeBeforeCreate = engagementTypeRepository.findAll().size();
        // Create the EngagementType
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);
        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EngagementType in the database
        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EngagementType testEngagementType = engagementTypeList.get(engagementTypeList.size() - 1);
        assertThat(testEngagementType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testEngagementType.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testEngagementType.getProcessDelay()).isEqualTo(DEFAULT_PROCESS_DELAY);
        assertThat(testEngagementType.getDelay1()).isEqualTo(DEFAULT_DELAY_1);
        assertThat(testEngagementType.getDelay2()).isEqualTo(DEFAULT_DELAY_2);
    }

    @Test
    @Transactional
    public void createEngagementTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = engagementTypeRepository.findAll().size();

        // Create the EngagementType with an existing ID
        engagementType.setId(1L);
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EngagementType in the database
        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementTypeRepository.findAll().size();
        // set the field null
        engagementType.setLabel(null);

        // Create the EngagementType, which fails.
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementTypeRepository.findAll().size();
        // set the field null
        engagementType.setIsEnabled(null);

        // Create the EngagementType, which fails.
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcessDelayIsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementTypeRepository.findAll().size();
        // set the field null
        engagementType.setProcessDelay(null);

        // Create the EngagementType, which fails.
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelay1IsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementTypeRepository.findAll().size();
        // set the field null
        engagementType.setDelay1(null);

        // Create the EngagementType, which fails.
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelay2IsRequired() throws Exception {
        int databaseSizeBeforeTest = engagementTypeRepository.findAll().size();
        // set the field null
        engagementType.setDelay2(null);

        // Create the EngagementType, which fails.
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        restEngagementTypeMockMvc
            .perform(
                post("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEngagementTypes() throws Exception {
        // Initialize the database
        engagementTypeRepository.saveAndFlush(engagementType);

        // Get all the engagementTypeList
        restEngagementTypeMockMvc
            .perform(get("/api/engagement-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(engagementType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].processDelay").value(hasItem(DEFAULT_PROCESS_DELAY)))
            .andExpect(jsonPath("$.[*].delay1").value(hasItem(DEFAULT_DELAY_1)))
            .andExpect(jsonPath("$.[*].delay2").value(hasItem(DEFAULT_DELAY_2)));
    }

    @Test
    @Transactional
    public void getEngagementType() throws Exception {
        // Initialize the database
        engagementTypeRepository.saveAndFlush(engagementType);

        // Get the engagementType
        restEngagementTypeMockMvc
            .perform(get("/api/engagement-types/{id}", engagementType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(engagementType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.processDelay").value(DEFAULT_PROCESS_DELAY))
            .andExpect(jsonPath("$.delay1").value(DEFAULT_DELAY_1))
            .andExpect(jsonPath("$.delay2").value(DEFAULT_DELAY_2));
    }

    @Test
    @Transactional
    public void getNonExistingEngagementType() throws Exception {
        // Get the engagementType
        restEngagementTypeMockMvc.perform(get("/api/engagement-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEngagementType() throws Exception {
        // Initialize the database
        engagementTypeRepository.saveAndFlush(engagementType);

        int databaseSizeBeforeUpdate = engagementTypeRepository.findAll().size();

        // Update the engagementType
        EngagementType updatedEngagementType = engagementTypeRepository.findById(engagementType.getId()).get();
        // Disconnect from session so that the updates on updatedEngagementType are not directly saved in db
        em.detach(updatedEngagementType);
        updatedEngagementType
            .label(UPDATED_LABEL)
            .isEnabled(UPDATED_IS_ENABLED)
            .processDelay(UPDATED_PROCESS_DELAY)
            .delay1(UPDATED_DELAY_1)
            .delay2(UPDATED_DELAY_2);
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(updatedEngagementType);

        restEngagementTypeMockMvc
            .perform(
                put("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EngagementType in the database
        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeUpdate);
        EngagementType testEngagementType = engagementTypeList.get(engagementTypeList.size() - 1);
        assertThat(testEngagementType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testEngagementType.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testEngagementType.getProcessDelay()).isEqualTo(UPDATED_PROCESS_DELAY);
        assertThat(testEngagementType.getDelay1()).isEqualTo(UPDATED_DELAY_1);
        assertThat(testEngagementType.getDelay2()).isEqualTo(UPDATED_DELAY_2);
    }

    @Test
    @Transactional
    public void updateNonExistingEngagementType() throws Exception {
        int databaseSizeBeforeUpdate = engagementTypeRepository.findAll().size();

        // Create the EngagementType
        EngagementTypeDTO engagementTypeDTO = engagementTypeMapper.toDto(engagementType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEngagementTypeMockMvc
            .perform(
                put("/api/engagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(engagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EngagementType in the database
        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEngagementType() throws Exception {
        // Initialize the database
        engagementTypeRepository.saveAndFlush(engagementType);

        int databaseSizeBeforeDelete = engagementTypeRepository.findAll().size();

        // Delete the engagementType
        restEngagementTypeMockMvc
            .perform(delete("/api/engagement-types/{id}", engagementType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EngagementType> engagementTypeList = engagementTypeRepository.findAll();
        assertThat(engagementTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
