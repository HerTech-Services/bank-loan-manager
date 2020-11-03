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
import tect.her.ccm.domain.FilelistEgagementType;
import tect.her.ccm.repository.FilelistEgagementTypeRepository;
import tect.her.ccm.service.FilelistEgagementTypeService;
import tect.her.ccm.service.dto.FilelistEgagementTypeDTO;
import tect.her.ccm.service.mapper.FilelistEgagementTypeMapper;

/**
 * Integration tests for the {@link FilelistEgagementTypeResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FilelistEgagementTypeResourceIT {
    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    @Autowired
    private FilelistEgagementTypeRepository filelistEgagementTypeRepository;

    @Autowired
    private FilelistEgagementTypeMapper filelistEgagementTypeMapper;

    @Autowired
    private FilelistEgagementTypeService filelistEgagementTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilelistEgagementTypeMockMvc;

    private FilelistEgagementType filelistEgagementType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilelistEgagementType createEntity(EntityManager em) {
        FilelistEgagementType filelistEgagementType = new FilelistEgagementType().mandatory(DEFAULT_MANDATORY);
        return filelistEgagementType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilelistEgagementType createUpdatedEntity(EntityManager em) {
        FilelistEgagementType filelistEgagementType = new FilelistEgagementType().mandatory(UPDATED_MANDATORY);
        return filelistEgagementType;
    }

    @BeforeEach
    public void initTest() {
        filelistEgagementType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilelistEgagementType() throws Exception {
        int databaseSizeBeforeCreate = filelistEgagementTypeRepository.findAll().size();
        // Create the FilelistEgagementType
        FilelistEgagementTypeDTO filelistEgagementTypeDTO = filelistEgagementTypeMapper.toDto(filelistEgagementType);
        restFilelistEgagementTypeMockMvc
            .perform(
                post("/api/filelist-egagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filelistEgagementTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FilelistEgagementType in the database
        List<FilelistEgagementType> filelistEgagementTypeList = filelistEgagementTypeRepository.findAll();
        assertThat(filelistEgagementTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FilelistEgagementType testFilelistEgagementType = filelistEgagementTypeList.get(filelistEgagementTypeList.size() - 1);
        assertThat(testFilelistEgagementType.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
    }

    @Test
    @Transactional
    public void createFilelistEgagementTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filelistEgagementTypeRepository.findAll().size();

        // Create the FilelistEgagementType with an existing ID
        filelistEgagementType.setId(1L);
        FilelistEgagementTypeDTO filelistEgagementTypeDTO = filelistEgagementTypeMapper.toDto(filelistEgagementType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilelistEgagementTypeMockMvc
            .perform(
                post("/api/filelist-egagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filelistEgagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilelistEgagementType in the database
        List<FilelistEgagementType> filelistEgagementTypeList = filelistEgagementTypeRepository.findAll();
        assertThat(filelistEgagementTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilelistEgagementTypes() throws Exception {
        // Initialize the database
        filelistEgagementTypeRepository.saveAndFlush(filelistEgagementType);

        // Get all the filelistEgagementTypeList
        restFilelistEgagementTypeMockMvc
            .perform(get("/api/filelist-egagement-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filelistEgagementType.getId().intValue())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())));
    }

    @Test
    @Transactional
    public void getFilelistEgagementType() throws Exception {
        // Initialize the database
        filelistEgagementTypeRepository.saveAndFlush(filelistEgagementType);

        // Get the filelistEgagementType
        restFilelistEgagementTypeMockMvc
            .perform(get("/api/filelist-egagement-types/{id}", filelistEgagementType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filelistEgagementType.getId().intValue()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFilelistEgagementType() throws Exception {
        // Get the filelistEgagementType
        restFilelistEgagementTypeMockMvc
            .perform(get("/api/filelist-egagement-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilelistEgagementType() throws Exception {
        // Initialize the database
        filelistEgagementTypeRepository.saveAndFlush(filelistEgagementType);

        int databaseSizeBeforeUpdate = filelistEgagementTypeRepository.findAll().size();

        // Update the filelistEgagementType
        FilelistEgagementType updatedFilelistEgagementType = filelistEgagementTypeRepository.findById(filelistEgagementType.getId()).get();
        // Disconnect from session so that the updates on updatedFilelistEgagementType are not directly saved in db
        em.detach(updatedFilelistEgagementType);
        updatedFilelistEgagementType.mandatory(UPDATED_MANDATORY);
        FilelistEgagementTypeDTO filelistEgagementTypeDTO = filelistEgagementTypeMapper.toDto(updatedFilelistEgagementType);

        restFilelistEgagementTypeMockMvc
            .perform(
                put("/api/filelist-egagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filelistEgagementTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the FilelistEgagementType in the database
        List<FilelistEgagementType> filelistEgagementTypeList = filelistEgagementTypeRepository.findAll();
        assertThat(filelistEgagementTypeList).hasSize(databaseSizeBeforeUpdate);
        FilelistEgagementType testFilelistEgagementType = filelistEgagementTypeList.get(filelistEgagementTypeList.size() - 1);
        assertThat(testFilelistEgagementType.isMandatory()).isEqualTo(UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void updateNonExistingFilelistEgagementType() throws Exception {
        int databaseSizeBeforeUpdate = filelistEgagementTypeRepository.findAll().size();

        // Create the FilelistEgagementType
        FilelistEgagementTypeDTO filelistEgagementTypeDTO = filelistEgagementTypeMapper.toDto(filelistEgagementType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilelistEgagementTypeMockMvc
            .perform(
                put("/api/filelist-egagement-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filelistEgagementTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilelistEgagementType in the database
        List<FilelistEgagementType> filelistEgagementTypeList = filelistEgagementTypeRepository.findAll();
        assertThat(filelistEgagementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFilelistEgagementType() throws Exception {
        // Initialize the database
        filelistEgagementTypeRepository.saveAndFlush(filelistEgagementType);

        int databaseSizeBeforeDelete = filelistEgagementTypeRepository.findAll().size();

        // Delete the filelistEgagementType
        restFilelistEgagementTypeMockMvc
            .perform(delete("/api/filelist-egagement-types/{id}", filelistEgagementType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FilelistEgagementType> filelistEgagementTypeList = filelistEgagementTypeRepository.findAll();
        assertThat(filelistEgagementTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
