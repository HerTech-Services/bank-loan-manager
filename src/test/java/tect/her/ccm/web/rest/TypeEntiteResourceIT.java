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
import tect.her.ccm.domain.TypeEntite;
import tect.her.ccm.repository.TypeEntiteRepository;
import tect.her.ccm.service.TypeEntiteService;
import tect.her.ccm.service.dto.TypeEntiteDTO;
import tect.her.ccm.service.mapper.TypeEntiteMapper;

/**
 * Integration tests for the {@link TypeEntiteResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeEntiteResourceIT {
    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    @Autowired
    private TypeEntiteRepository typeEntiteRepository;

    @Autowired
    private TypeEntiteMapper typeEntiteMapper;

    @Autowired
    private TypeEntiteService typeEntiteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeEntiteMockMvc;

    private TypeEntite typeEntite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeEntite createEntity(EntityManager em) {
        TypeEntite typeEntite = new TypeEntite().identifier(DEFAULT_IDENTIFIER).label(DEFAULT_LABEL).shortLabel(DEFAULT_SHORT_LABEL);
        return typeEntite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeEntite createUpdatedEntity(EntityManager em) {
        TypeEntite typeEntite = new TypeEntite().identifier(UPDATED_IDENTIFIER).label(UPDATED_LABEL).shortLabel(UPDATED_SHORT_LABEL);
        return typeEntite;
    }

    @BeforeEach
    public void initTest() {
        typeEntite = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeEntite() throws Exception {
        int databaseSizeBeforeCreate = typeEntiteRepository.findAll().size();
        // Create the TypeEntite
        TypeEntiteDTO typeEntiteDTO = typeEntiteMapper.toDto(typeEntite);
        restTypeEntiteMockMvc
            .perform(
                post("/api/type-entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeEntiteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypeEntite in the database
        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeEntite testTypeEntite = typeEntiteList.get(typeEntiteList.size() - 1);
        assertThat(testTypeEntite.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testTypeEntite.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTypeEntite.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void createTypeEntiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeEntiteRepository.findAll().size();

        // Create the TypeEntite with an existing ID
        typeEntite.setId(1L);
        TypeEntiteDTO typeEntiteDTO = typeEntiteMapper.toDto(typeEntite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeEntiteMockMvc
            .perform(
                post("/api/type-entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeEntite in the database
        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeEntiteRepository.findAll().size();
        // set the field null
        typeEntite.setIdentifier(null);

        // Create the TypeEntite, which fails.
        TypeEntiteDTO typeEntiteDTO = typeEntiteMapper.toDto(typeEntite);

        restTypeEntiteMockMvc
            .perform(
                post("/api/type-entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeEntiteRepository.findAll().size();
        // set the field null
        typeEntite.setLabel(null);

        // Create the TypeEntite, which fails.
        TypeEntiteDTO typeEntiteDTO = typeEntiteMapper.toDto(typeEntite);

        restTypeEntiteMockMvc
            .perform(
                post("/api/type-entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeEntites() throws Exception {
        // Initialize the database
        typeEntiteRepository.saveAndFlush(typeEntite);

        // Get all the typeEntiteList
        restTypeEntiteMockMvc
            .perform(get("/api/type-entites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeEntite.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)));
    }

    @Test
    @Transactional
    public void getTypeEntite() throws Exception {
        // Initialize the database
        typeEntiteRepository.saveAndFlush(typeEntite);

        // Get the typeEntite
        restTypeEntiteMockMvc
            .perform(get("/api/type-entites/{id}", typeEntite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeEntite.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL));
    }

    @Test
    @Transactional
    public void getNonExistingTypeEntite() throws Exception {
        // Get the typeEntite
        restTypeEntiteMockMvc.perform(get("/api/type-entites/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeEntite() throws Exception {
        // Initialize the database
        typeEntiteRepository.saveAndFlush(typeEntite);

        int databaseSizeBeforeUpdate = typeEntiteRepository.findAll().size();

        // Update the typeEntite
        TypeEntite updatedTypeEntite = typeEntiteRepository.findById(typeEntite.getId()).get();
        // Disconnect from session so that the updates on updatedTypeEntite are not directly saved in db
        em.detach(updatedTypeEntite);
        updatedTypeEntite.identifier(UPDATED_IDENTIFIER).label(UPDATED_LABEL).shortLabel(UPDATED_SHORT_LABEL);
        TypeEntiteDTO typeEntiteDTO = typeEntiteMapper.toDto(updatedTypeEntite);

        restTypeEntiteMockMvc
            .perform(
                put("/api/type-entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeEntiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeEntite in the database
        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeUpdate);
        TypeEntite testTypeEntite = typeEntiteList.get(typeEntiteList.size() - 1);
        assertThat(testTypeEntite.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testTypeEntite.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTypeEntite.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeEntite() throws Exception {
        int databaseSizeBeforeUpdate = typeEntiteRepository.findAll().size();

        // Create the TypeEntite
        TypeEntiteDTO typeEntiteDTO = typeEntiteMapper.toDto(typeEntite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeEntiteMockMvc
            .perform(
                put("/api/type-entites").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeEntite in the database
        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeEntite() throws Exception {
        // Initialize the database
        typeEntiteRepository.saveAndFlush(typeEntite);

        int databaseSizeBeforeDelete = typeEntiteRepository.findAll().size();

        // Delete the typeEntite
        restTypeEntiteMockMvc
            .perform(delete("/api/type-entites/{id}", typeEntite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeEntite> typeEntiteList = typeEntiteRepository.findAll();
        assertThat(typeEntiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
