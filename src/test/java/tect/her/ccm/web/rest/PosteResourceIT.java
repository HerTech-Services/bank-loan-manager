package tect.her.ccm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.BankLoanManagerApp;
import tect.her.ccm.domain.Poste;
import tect.her.ccm.repository.PosteRepository;
import tect.her.ccm.service.PosteService;
import tect.her.ccm.service.dto.PosteDTO;
import tect.her.ccm.service.mapper.PosteMapper;

/**
 * Integration tests for the {@link PosteResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PosteResourceIT {
    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ADRS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    @Autowired
    private PosteRepository posteRepository;

    @Mock
    private PosteRepository posteRepositoryMock;

    @Autowired
    private PosteMapper posteMapper;

    @Mock
    private PosteService posteServiceMock;

    @Autowired
    private PosteService posteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPosteMockMvc;

    private Poste poste;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poste createEntity(EntityManager em) {
        Poste poste = new Poste()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .entity(DEFAULT_ENTITY)
            .enabled(DEFAULT_ENABLED)
            .adrs1(DEFAULT_ADRS_1)
            .adrs2(DEFAULT_ADRS_2)
            .adrs3(DEFAULT_ADRS_3)
            .city(DEFAULT_CITY);
        return poste;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poste createUpdatedEntity(EntityManager em) {
        Poste poste = new Poste()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .entity(UPDATED_ENTITY)
            .enabled(UPDATED_ENABLED)
            .adrs1(UPDATED_ADRS_1)
            .adrs2(UPDATED_ADRS_2)
            .adrs3(UPDATED_ADRS_3)
            .city(UPDATED_CITY);
        return poste;
    }

    @BeforeEach
    public void initTest() {
        poste = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoste() throws Exception {
        int databaseSizeBeforeCreate = posteRepository.findAll().size();
        // Create the Poste
        PosteDTO posteDTO = posteMapper.toDto(poste);
        restPosteMockMvc
            .perform(post("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isCreated());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeCreate + 1);
        Poste testPoste = posteList.get(posteList.size() - 1);
        assertThat(testPoste.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPoste.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPoste.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testPoste.getEntity()).isEqualTo(DEFAULT_ENTITY);
        assertThat(testPoste.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testPoste.getAdrs1()).isEqualTo(DEFAULT_ADRS_1);
        assertThat(testPoste.getAdrs2()).isEqualTo(DEFAULT_ADRS_2);
        assertThat(testPoste.getAdrs3()).isEqualTo(DEFAULT_ADRS_3);
        assertThat(testPoste.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void createPosteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = posteRepository.findAll().size();

        // Create the Poste with an existing ID
        poste.setId(1L);
        PosteDTO posteDTO = posteMapper.toDto(poste);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPosteMockMvc
            .perform(post("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = posteRepository.findAll().size();
        // set the field null
        poste.setCode(null);

        // Create the Poste, which fails.
        PosteDTO posteDTO = posteMapper.toDto(poste);

        restPosteMockMvc
            .perform(post("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = posteRepository.findAll().size();
        // set the field null
        poste.setLabel(null);

        // Create the Poste, which fails.
        PosteDTO posteDTO = posteMapper.toDto(poste);

        restPosteMockMvc
            .perform(post("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = posteRepository.findAll().size();
        // set the field null
        poste.setEnabled(null);

        // Create the Poste, which fails.
        PosteDTO posteDTO = posteMapper.toDto(poste);

        restPosteMockMvc
            .perform(post("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostes() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        // Get all the posteList
        restPosteMockMvc
            .perform(get("/api/postes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poste.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].entity").value(hasItem(DEFAULT_ENTITY)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].adrs1").value(hasItem(DEFAULT_ADRS_1)))
            .andExpect(jsonPath("$.[*].adrs2").value(hasItem(DEFAULT_ADRS_2)))
            .andExpect(jsonPath("$.[*].adrs3").value(hasItem(DEFAULT_ADRS_3)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPostesWithEagerRelationshipsIsEnabled() throws Exception {
        when(posteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPosteMockMvc.perform(get("/api/postes?eagerload=true")).andExpect(status().isOk());

        verify(posteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPostesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(posteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPosteMockMvc.perform(get("/api/postes?eagerload=true")).andExpect(status().isOk());

        verify(posteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPoste() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        // Get the poste
        restPosteMockMvc
            .perform(get("/api/postes/{id}", poste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poste.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.entity").value(DEFAULT_ENTITY))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.adrs1").value(DEFAULT_ADRS_1))
            .andExpect(jsonPath("$.adrs2").value(DEFAULT_ADRS_2))
            .andExpect(jsonPath("$.adrs3").value(DEFAULT_ADRS_3))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY));
    }

    @Test
    @Transactional
    public void getNonExistingPoste() throws Exception {
        // Get the poste
        restPosteMockMvc.perform(get("/api/postes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoste() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        int databaseSizeBeforeUpdate = posteRepository.findAll().size();

        // Update the poste
        Poste updatedPoste = posteRepository.findById(poste.getId()).get();
        // Disconnect from session so that the updates on updatedPoste are not directly saved in db
        em.detach(updatedPoste);
        updatedPoste
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .entity(UPDATED_ENTITY)
            .enabled(UPDATED_ENABLED)
            .adrs1(UPDATED_ADRS_1)
            .adrs2(UPDATED_ADRS_2)
            .adrs3(UPDATED_ADRS_3)
            .city(UPDATED_CITY);
        PosteDTO posteDTO = posteMapper.toDto(updatedPoste);

        restPosteMockMvc
            .perform(put("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isOk());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeUpdate);
        Poste testPoste = posteList.get(posteList.size() - 1);
        assertThat(testPoste.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPoste.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPoste.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testPoste.getEntity()).isEqualTo(UPDATED_ENTITY);
        assertThat(testPoste.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testPoste.getAdrs1()).isEqualTo(UPDATED_ADRS_1);
        assertThat(testPoste.getAdrs2()).isEqualTo(UPDATED_ADRS_2);
        assertThat(testPoste.getAdrs3()).isEqualTo(UPDATED_ADRS_3);
        assertThat(testPoste.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPoste() throws Exception {
        int databaseSizeBeforeUpdate = posteRepository.findAll().size();

        // Create the Poste
        PosteDTO posteDTO = posteMapper.toDto(poste);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPosteMockMvc
            .perform(put("/api/postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoste() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        int databaseSizeBeforeDelete = posteRepository.findAll().size();

        // Delete the poste
        restPosteMockMvc
            .perform(delete("/api/postes/{id}", poste.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
