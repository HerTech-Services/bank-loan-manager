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
import tect.her.ccm.domain.Filelist;
import tect.her.ccm.repository.FilelistRepository;
import tect.her.ccm.service.FilelistService;
import tect.her.ccm.service.dto.FilelistDTO;
import tect.her.ccm.service.mapper.FilelistMapper;

/**
 * Integration tests for the {@link FilelistResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FilelistResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FilelistRepository filelistRepository;

    @Autowired
    private FilelistMapper filelistMapper;

    @Autowired
    private FilelistService filelistService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilelistMockMvc;

    private Filelist filelist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filelist createEntity(EntityManager em) {
        Filelist filelist = new Filelist().label(DEFAULT_LABEL).identifier(DEFAULT_IDENTIFIER).description(DEFAULT_DESCRIPTION);
        return filelist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filelist createUpdatedEntity(EntityManager em) {
        Filelist filelist = new Filelist().label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER).description(UPDATED_DESCRIPTION);
        return filelist;
    }

    @BeforeEach
    public void initTest() {
        filelist = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilelist() throws Exception {
        int databaseSizeBeforeCreate = filelistRepository.findAll().size();
        // Create the Filelist
        FilelistDTO filelistDTO = filelistMapper.toDto(filelist);
        restFilelistMockMvc
            .perform(post("/api/filelists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filelistDTO)))
            .andExpect(status().isCreated());

        // Validate the Filelist in the database
        List<Filelist> filelistList = filelistRepository.findAll();
        assertThat(filelistList).hasSize(databaseSizeBeforeCreate + 1);
        Filelist testFilelist = filelistList.get(filelistList.size() - 1);
        assertThat(testFilelist.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testFilelist.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testFilelist.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFilelistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filelistRepository.findAll().size();

        // Create the Filelist with an existing ID
        filelist.setId(1L);
        FilelistDTO filelistDTO = filelistMapper.toDto(filelist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilelistMockMvc
            .perform(post("/api/filelists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filelistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filelist in the database
        List<Filelist> filelistList = filelistRepository.findAll();
        assertThat(filelistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = filelistRepository.findAll().size();
        // set the field null
        filelist.setLabel(null);

        // Create the Filelist, which fails.
        FilelistDTO filelistDTO = filelistMapper.toDto(filelist);

        restFilelistMockMvc
            .perform(post("/api/filelists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filelistDTO)))
            .andExpect(status().isBadRequest());

        List<Filelist> filelistList = filelistRepository.findAll();
        assertThat(filelistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilelists() throws Exception {
        // Initialize the database
        filelistRepository.saveAndFlush(filelist);

        // Get all the filelistList
        restFilelistMockMvc
            .perform(get("/api/filelists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filelist.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getFilelist() throws Exception {
        // Initialize the database
        filelistRepository.saveAndFlush(filelist);

        // Get the filelist
        restFilelistMockMvc
            .perform(get("/api/filelists/{id}", filelist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filelist.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingFilelist() throws Exception {
        // Get the filelist
        restFilelistMockMvc.perform(get("/api/filelists/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilelist() throws Exception {
        // Initialize the database
        filelistRepository.saveAndFlush(filelist);

        int databaseSizeBeforeUpdate = filelistRepository.findAll().size();

        // Update the filelist
        Filelist updatedFilelist = filelistRepository.findById(filelist.getId()).get();
        // Disconnect from session so that the updates on updatedFilelist are not directly saved in db
        em.detach(updatedFilelist);
        updatedFilelist.label(UPDATED_LABEL).identifier(UPDATED_IDENTIFIER).description(UPDATED_DESCRIPTION);
        FilelistDTO filelistDTO = filelistMapper.toDto(updatedFilelist);

        restFilelistMockMvc
            .perform(put("/api/filelists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filelistDTO)))
            .andExpect(status().isOk());

        // Validate the Filelist in the database
        List<Filelist> filelistList = filelistRepository.findAll();
        assertThat(filelistList).hasSize(databaseSizeBeforeUpdate);
        Filelist testFilelist = filelistList.get(filelistList.size() - 1);
        assertThat(testFilelist.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testFilelist.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testFilelist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFilelist() throws Exception {
        int databaseSizeBeforeUpdate = filelistRepository.findAll().size();

        // Create the Filelist
        FilelistDTO filelistDTO = filelistMapper.toDto(filelist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilelistMockMvc
            .perform(put("/api/filelists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filelistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filelist in the database
        List<Filelist> filelistList = filelistRepository.findAll();
        assertThat(filelistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFilelist() throws Exception {
        // Initialize the database
        filelistRepository.saveAndFlush(filelist);

        int databaseSizeBeforeDelete = filelistRepository.findAll().size();

        // Delete the filelist
        restFilelistMockMvc
            .perform(delete("/api/filelists/{id}", filelist.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Filelist> filelistList = filelistRepository.findAll();
        assertThat(filelistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
