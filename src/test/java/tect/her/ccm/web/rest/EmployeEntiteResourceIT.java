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
import tect.her.ccm.domain.Employe;
import tect.her.ccm.domain.EmployeEntite;
import tect.her.ccm.domain.Entite;
import tect.her.ccm.repository.EmployeEntiteRepository;
import tect.her.ccm.service.EmployeEntiteService;
import tect.her.ccm.service.dto.EmployeEntiteDTO;
import tect.her.ccm.service.mapper.EmployeEntiteMapper;

/**
 * Integration tests for the {@link EmployeEntiteResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeEntiteResourceIT {
    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PRIMARY = false;
    private static final Boolean UPDATED_IS_PRIMARY = true;

    private static final Boolean DEFAULT_IS_CHIEF = false;
    private static final Boolean UPDATED_IS_CHIEF = true;

    @Autowired
    private EmployeEntiteRepository employeEntiteRepository;

    @Autowired
    private EmployeEntiteMapper employeEntiteMapper;

    @Autowired
    private EmployeEntiteService employeEntiteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeEntiteMockMvc;

    private EmployeEntite employeEntite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeEntite createEntity(EntityManager em) {
        EmployeEntite employeEntite = new EmployeEntite().role(DEFAULT_ROLE).isPrimary(DEFAULT_IS_PRIMARY).isChief(DEFAULT_IS_CHIEF);
        // Add required entity
        Employe employe;
        if (TestUtil.findAll(em, Employe.class).isEmpty()) {
            employe = EmployeResourceIT.createEntity(em);
            em.persist(employe);
            em.flush();
        } else {
            employe = TestUtil.findAll(em, Employe.class).get(0);
        }
        employeEntite.setEmploye(employe);
        // Add required entity
        Entite entite;
        if (TestUtil.findAll(em, Entite.class).isEmpty()) {
            entite = EntiteResourceIT.createEntity(em);
            em.persist(entite);
            em.flush();
        } else {
            entite = TestUtil.findAll(em, Entite.class).get(0);
        }
        employeEntite.setEntite(entite);
        return employeEntite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeEntite createUpdatedEntity(EntityManager em) {
        EmployeEntite employeEntite = new EmployeEntite().role(UPDATED_ROLE).isPrimary(UPDATED_IS_PRIMARY).isChief(UPDATED_IS_CHIEF);
        // Add required entity
        Employe employe;
        if (TestUtil.findAll(em, Employe.class).isEmpty()) {
            employe = EmployeResourceIT.createUpdatedEntity(em);
            em.persist(employe);
            em.flush();
        } else {
            employe = TestUtil.findAll(em, Employe.class).get(0);
        }
        employeEntite.setEmploye(employe);
        // Add required entity
        Entite entite;
        if (TestUtil.findAll(em, Entite.class).isEmpty()) {
            entite = EntiteResourceIT.createUpdatedEntity(em);
            em.persist(entite);
            em.flush();
        } else {
            entite = TestUtil.findAll(em, Entite.class).get(0);
        }
        employeEntite.setEntite(entite);
        return employeEntite;
    }

    @BeforeEach
    public void initTest() {
        employeEntite = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeEntite() throws Exception {
        int databaseSizeBeforeCreate = employeEntiteRepository.findAll().size();
        // Create the EmployeEntite
        EmployeEntiteDTO employeEntiteDTO = employeEntiteMapper.toDto(employeEntite);
        restEmployeEntiteMockMvc
            .perform(
                post("/api/employe-entites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeEntiteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeEntite in the database
        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeEntite testEmployeEntite = employeEntiteList.get(employeEntiteList.size() - 1);
        assertThat(testEmployeEntite.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testEmployeEntite.isIsPrimary()).isEqualTo(DEFAULT_IS_PRIMARY);
        assertThat(testEmployeEntite.isIsChief()).isEqualTo(DEFAULT_IS_CHIEF);
    }

    @Test
    @Transactional
    public void createEmployeEntiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeEntiteRepository.findAll().size();

        // Create the EmployeEntite with an existing ID
        employeEntite.setId(1L);
        EmployeEntiteDTO employeEntiteDTO = employeEntiteMapper.toDto(employeEntite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeEntiteMockMvc
            .perform(
                post("/api/employe-entites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeEntite in the database
        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIsPrimaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeEntiteRepository.findAll().size();
        // set the field null
        employeEntite.setIsPrimary(null);

        // Create the EmployeEntite, which fails.
        EmployeEntiteDTO employeEntiteDTO = employeEntiteMapper.toDto(employeEntite);

        restEmployeEntiteMockMvc
            .perform(
                post("/api/employe-entites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsChiefIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeEntiteRepository.findAll().size();
        // set the field null
        employeEntite.setIsChief(null);

        // Create the EmployeEntite, which fails.
        EmployeEntiteDTO employeEntiteDTO = employeEntiteMapper.toDto(employeEntite);

        restEmployeEntiteMockMvc
            .perform(
                post("/api/employe-entites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeEntites() throws Exception {
        // Initialize the database
        employeEntiteRepository.saveAndFlush(employeEntite);

        // Get all the employeEntiteList
        restEmployeEntiteMockMvc
            .perform(get("/api/employe-entites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeEntite.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].isPrimary").value(hasItem(DEFAULT_IS_PRIMARY.booleanValue())))
            .andExpect(jsonPath("$.[*].isChief").value(hasItem(DEFAULT_IS_CHIEF.booleanValue())));
    }

    @Test
    @Transactional
    public void getEmployeEntite() throws Exception {
        // Initialize the database
        employeEntiteRepository.saveAndFlush(employeEntite);

        // Get the employeEntite
        restEmployeEntiteMockMvc
            .perform(get("/api/employe-entites/{id}", employeEntite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeEntite.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.isPrimary").value(DEFAULT_IS_PRIMARY.booleanValue()))
            .andExpect(jsonPath("$.isChief").value(DEFAULT_IS_CHIEF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeEntite() throws Exception {
        // Get the employeEntite
        restEmployeEntiteMockMvc.perform(get("/api/employe-entites/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeEntite() throws Exception {
        // Initialize the database
        employeEntiteRepository.saveAndFlush(employeEntite);

        int databaseSizeBeforeUpdate = employeEntiteRepository.findAll().size();

        // Update the employeEntite
        EmployeEntite updatedEmployeEntite = employeEntiteRepository.findById(employeEntite.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeEntite are not directly saved in db
        em.detach(updatedEmployeEntite);
        updatedEmployeEntite.role(UPDATED_ROLE).isPrimary(UPDATED_IS_PRIMARY).isChief(UPDATED_IS_CHIEF);
        EmployeEntiteDTO employeEntiteDTO = employeEntiteMapper.toDto(updatedEmployeEntite);

        restEmployeEntiteMockMvc
            .perform(
                put("/api/employe-entites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeEntiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeEntite in the database
        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeUpdate);
        EmployeEntite testEmployeEntite = employeEntiteList.get(employeEntiteList.size() - 1);
        assertThat(testEmployeEntite.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testEmployeEntite.isIsPrimary()).isEqualTo(UPDATED_IS_PRIMARY);
        assertThat(testEmployeEntite.isIsChief()).isEqualTo(UPDATED_IS_CHIEF);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeEntite() throws Exception {
        int databaseSizeBeforeUpdate = employeEntiteRepository.findAll().size();

        // Create the EmployeEntite
        EmployeEntiteDTO employeEntiteDTO = employeEntiteMapper.toDto(employeEntite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeEntiteMockMvc
            .perform(
                put("/api/employe-entites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeEntiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeEntite in the database
        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeEntite() throws Exception {
        // Initialize the database
        employeEntiteRepository.saveAndFlush(employeEntite);

        int databaseSizeBeforeDelete = employeEntiteRepository.findAll().size();

        // Delete the employeEntite
        restEmployeEntiteMockMvc
            .perform(delete("/api/employe-entites/{id}", employeEntite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeEntite> employeEntiteList = employeEntiteRepository.findAll();
        assertThat(employeEntiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
