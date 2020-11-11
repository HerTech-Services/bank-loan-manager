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
import org.springframework.util.Base64Utils;
import tect.her.ccm.BankLoanManagerApp;
import tect.her.ccm.domain.Entite;
import tect.her.ccm.repository.EntiteRepository;
import tect.her.ccm.service.EntiteService;
import tect.her.ccm.service.dto.EntiteDTO;
import tect.her.ccm.service.mapper.EntiteMapper;

/**
 * Integration tests for the {@link EntiteResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EntiteResourceIT {
    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARENT = 1;
    private static final Integer UPDATED_PARENT = 2;

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final String DEFAULT_ADRS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIPCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETERS = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETERS = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ID = "BBBBBBBBBB";

    @Autowired
    private EntiteRepository entiteRepository;

    @Autowired
    private EntiteMapper entiteMapper;

    @Autowired
    private EntiteService entiteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntiteMockMvc;

    private Entite entite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entite createEntity(EntityManager em) {
        Entite entite = new Entite()
            .identifier(DEFAULT_IDENTIFIER)
            .label(DEFAULT_LABEL)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .parent(DEFAULT_PARENT)
            .isEnabled(DEFAULT_IS_ENABLED)
            .adrs1(DEFAULT_ADRS_1)
            .adrs2(DEFAULT_ADRS_2)
            .adrs3(DEFAULT_ADRS_3)
            .zipcode(DEFAULT_ZIPCODE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .email(DEFAULT_EMAIL)
            .parameters(DEFAULT_PARAMETERS)
            .parentId(DEFAULT_PARENT_ID);
        return entite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entite createUpdatedEntity(EntityManager em) {
        Entite entite = new Entite()
            .identifier(UPDATED_IDENTIFIER)
            .label(UPDATED_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .parent(UPDATED_PARENT)
            .isEnabled(UPDATED_IS_ENABLED)
            .adrs1(UPDATED_ADRS_1)
            .adrs2(UPDATED_ADRS_2)
            .adrs3(UPDATED_ADRS_3)
            .zipcode(UPDATED_ZIPCODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .email(UPDATED_EMAIL)
            .parameters(UPDATED_PARAMETERS)
            .parentId(UPDATED_PARENT_ID);
        return entite;
    }

    @BeforeEach
    public void initTest() {
        entite = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntite() throws Exception {
        int databaseSizeBeforeCreate = entiteRepository.findAll().size();
        // Create the Entite
        EntiteDTO entiteDTO = entiteMapper.toDto(entite);
        restEntiteMockMvc
            .perform(post("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isCreated());

        // Validate the Entite in the database
        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeCreate + 1);
        Entite testEntite = entiteList.get(entiteList.size() - 1);
        assertThat(testEntite.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testEntite.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testEntite.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testEntite.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testEntite.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testEntite.getAdrs1()).isEqualTo(DEFAULT_ADRS_1);
        assertThat(testEntite.getAdrs2()).isEqualTo(DEFAULT_ADRS_2);
        assertThat(testEntite.getAdrs3()).isEqualTo(DEFAULT_ADRS_3);
        assertThat(testEntite.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testEntite.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEntite.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEntite.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEntite.getParameters()).isEqualTo(DEFAULT_PARAMETERS);
        assertThat(testEntite.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    public void createEntiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entiteRepository.findAll().size();

        // Create the Entite with an existing ID
        entite.setId(1L);
        EntiteDTO entiteDTO = entiteMapper.toDto(entite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntiteMockMvc
            .perform(post("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entite in the database
        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = entiteRepository.findAll().size();
        // set the field null
        entite.setIdentifier(null);

        // Create the Entite, which fails.
        EntiteDTO entiteDTO = entiteMapper.toDto(entite);

        restEntiteMockMvc
            .perform(post("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isBadRequest());

        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = entiteRepository.findAll().size();
        // set the field null
        entite.setLabel(null);

        // Create the Entite, which fails.
        EntiteDTO entiteDTO = entiteMapper.toDto(entite);

        restEntiteMockMvc
            .perform(post("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isBadRequest());

        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = entiteRepository.findAll().size();
        // set the field null
        entite.setIsEnabled(null);

        // Create the Entite, which fails.
        EntiteDTO entiteDTO = entiteMapper.toDto(entite);

        restEntiteMockMvc
            .perform(post("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isBadRequest());

        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntites() throws Exception {
        // Initialize the database
        entiteRepository.saveAndFlush(entite);

        // Get all the entiteList
        restEntiteMockMvc
            .perform(get("/api/entites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entite.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].adrs1").value(hasItem(DEFAULT_ADRS_1)))
            .andExpect(jsonPath("$.[*].adrs2").value(hasItem(DEFAULT_ADRS_2)))
            .andExpect(jsonPath("$.[*].adrs3").value(hasItem(DEFAULT_ADRS_3)))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].parameters").value(hasItem(DEFAULT_PARAMETERS.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)));
    }

    @Test
    @Transactional
    public void getEntite() throws Exception {
        // Initialize the database
        entiteRepository.saveAndFlush(entite);

        // Get the entite
        restEntiteMockMvc
            .perform(get("/api/entites/{id}", entite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entite.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.adrs1").value(DEFAULT_ADRS_1))
            .andExpect(jsonPath("$.adrs2").value(DEFAULT_ADRS_2))
            .andExpect(jsonPath("$.adrs3").value(DEFAULT_ADRS_3))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.parameters").value(DEFAULT_PARAMETERS.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingEntite() throws Exception {
        // Get the entite
        restEntiteMockMvc.perform(get("/api/entites/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntite() throws Exception {
        // Initialize the database
        entiteRepository.saveAndFlush(entite);

        int databaseSizeBeforeUpdate = entiteRepository.findAll().size();

        // Update the entite
        Entite updatedEntite = entiteRepository.findById(entite.getId()).get();
        // Disconnect from session so that the updates on updatedEntite are not directly saved in db
        em.detach(updatedEntite);
        updatedEntite
            .identifier(UPDATED_IDENTIFIER)
            .label(UPDATED_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .parent(UPDATED_PARENT)
            .isEnabled(UPDATED_IS_ENABLED)
            .adrs1(UPDATED_ADRS_1)
            .adrs2(UPDATED_ADRS_2)
            .adrs3(UPDATED_ADRS_3)
            .zipcode(UPDATED_ZIPCODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .email(UPDATED_EMAIL)
            .parameters(UPDATED_PARAMETERS)
            .parentId(UPDATED_PARENT_ID);
        EntiteDTO entiteDTO = entiteMapper.toDto(updatedEntite);

        restEntiteMockMvc
            .perform(put("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isOk());

        // Validate the Entite in the database
        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeUpdate);
        Entite testEntite = entiteList.get(entiteList.size() - 1);
        assertThat(testEntite.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testEntite.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testEntite.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testEntite.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testEntite.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testEntite.getAdrs1()).isEqualTo(UPDATED_ADRS_1);
        assertThat(testEntite.getAdrs2()).isEqualTo(UPDATED_ADRS_2);
        assertThat(testEntite.getAdrs3()).isEqualTo(UPDATED_ADRS_3);
        assertThat(testEntite.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testEntite.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEntite.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEntite.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntite.getParameters()).isEqualTo(UPDATED_PARAMETERS);
        assertThat(testEntite.getParentId()).isEqualTo(UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEntite() throws Exception {
        int databaseSizeBeforeUpdate = entiteRepository.findAll().size();

        // Create the Entite
        EntiteDTO entiteDTO = entiteMapper.toDto(entite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntiteMockMvc
            .perform(put("/api/entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entite in the database
        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntite() throws Exception {
        // Initialize the database
        entiteRepository.saveAndFlush(entite);

        int databaseSizeBeforeDelete = entiteRepository.findAll().size();

        // Delete the entite
        restEntiteMockMvc
            .perform(delete("/api/entites/{id}", entite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entite> entiteList = entiteRepository.findAll();
        assertThat(entiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
