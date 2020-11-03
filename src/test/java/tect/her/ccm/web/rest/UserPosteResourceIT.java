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
import tect.her.ccm.domain.Poste;
import tect.her.ccm.domain.User;
import tect.her.ccm.domain.UserPoste;
import tect.her.ccm.repository.UserPosteRepository;
import tect.her.ccm.service.UserPosteService;
import tect.her.ccm.service.dto.UserPosteDTO;
import tect.her.ccm.service.mapper.UserPosteMapper;

/**
 * Integration tests for the {@link UserPosteResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserPosteResourceIT {
    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PRIMARY = false;
    private static final Boolean UPDATED_IS_PRIMARY = true;

    @Autowired
    private UserPosteRepository userPosteRepository;

    @Autowired
    private UserPosteMapper userPosteMapper;

    @Autowired
    private UserPosteService userPosteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserPosteMockMvc;

    private UserPoste userPoste;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPoste createEntity(EntityManager em) {
        UserPoste userPoste = new UserPoste().role(DEFAULT_ROLE).isPrimary(DEFAULT_IS_PRIMARY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userPoste.setUser(user);
        // Add required entity
        Poste poste;
        if (TestUtil.findAll(em, Poste.class).isEmpty()) {
            poste = PosteResourceIT.createEntity(em);
            em.persist(poste);
            em.flush();
        } else {
            poste = TestUtil.findAll(em, Poste.class).get(0);
        }
        userPoste.setEntities(poste);
        return userPoste;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPoste createUpdatedEntity(EntityManager em) {
        UserPoste userPoste = new UserPoste().role(UPDATED_ROLE).isPrimary(UPDATED_IS_PRIMARY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userPoste.setUser(user);
        // Add required entity
        Poste poste;
        if (TestUtil.findAll(em, Poste.class).isEmpty()) {
            poste = PosteResourceIT.createUpdatedEntity(em);
            em.persist(poste);
            em.flush();
        } else {
            poste = TestUtil.findAll(em, Poste.class).get(0);
        }
        userPoste.setEntities(poste);
        return userPoste;
    }

    @BeforeEach
    public void initTest() {
        userPoste = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPoste() throws Exception {
        int databaseSizeBeforeCreate = userPosteRepository.findAll().size();
        // Create the UserPoste
        UserPosteDTO userPosteDTO = userPosteMapper.toDto(userPoste);
        restUserPosteMockMvc
            .perform(
                post("/api/user-postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPosteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserPoste in the database
        List<UserPoste> userPosteList = userPosteRepository.findAll();
        assertThat(userPosteList).hasSize(databaseSizeBeforeCreate + 1);
        UserPoste testUserPoste = userPosteList.get(userPosteList.size() - 1);
        assertThat(testUserPoste.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testUserPoste.isIsPrimary()).isEqualTo(DEFAULT_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void createUserPosteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPosteRepository.findAll().size();

        // Create the UserPoste with an existing ID
        userPoste.setId(1L);
        UserPosteDTO userPosteDTO = userPosteMapper.toDto(userPoste);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPosteMockMvc
            .perform(
                post("/api/user-postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPosteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPoste in the database
        List<UserPoste> userPosteList = userPosteRepository.findAll();
        assertThat(userPosteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIsPrimaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPosteRepository.findAll().size();
        // set the field null
        userPoste.setIsPrimary(null);

        // Create the UserPoste, which fails.
        UserPosteDTO userPosteDTO = userPosteMapper.toDto(userPoste);

        restUserPosteMockMvc
            .perform(
                post("/api/user-postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPosteDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserPoste> userPosteList = userPosteRepository.findAll();
        assertThat(userPosteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserPostes() throws Exception {
        // Initialize the database
        userPosteRepository.saveAndFlush(userPoste);

        // Get all the userPosteList
        restUserPosteMockMvc
            .perform(get("/api/user-postes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPoste.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].isPrimary").value(hasItem(DEFAULT_IS_PRIMARY.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserPoste() throws Exception {
        // Initialize the database
        userPosteRepository.saveAndFlush(userPoste);

        // Get the userPoste
        restUserPosteMockMvc
            .perform(get("/api/user-postes/{id}", userPoste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPoste.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.isPrimary").value(DEFAULT_IS_PRIMARY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPoste() throws Exception {
        // Get the userPoste
        restUserPosteMockMvc.perform(get("/api/user-postes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPoste() throws Exception {
        // Initialize the database
        userPosteRepository.saveAndFlush(userPoste);

        int databaseSizeBeforeUpdate = userPosteRepository.findAll().size();

        // Update the userPoste
        UserPoste updatedUserPoste = userPosteRepository.findById(userPoste.getId()).get();
        // Disconnect from session so that the updates on updatedUserPoste are not directly saved in db
        em.detach(updatedUserPoste);
        updatedUserPoste.role(UPDATED_ROLE).isPrimary(UPDATED_IS_PRIMARY);
        UserPosteDTO userPosteDTO = userPosteMapper.toDto(updatedUserPoste);

        restUserPosteMockMvc
            .perform(
                put("/api/user-postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPosteDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserPoste in the database
        List<UserPoste> userPosteList = userPosteRepository.findAll();
        assertThat(userPosteList).hasSize(databaseSizeBeforeUpdate);
        UserPoste testUserPoste = userPosteList.get(userPosteList.size() - 1);
        assertThat(testUserPoste.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testUserPoste.isIsPrimary()).isEqualTo(UPDATED_IS_PRIMARY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPoste() throws Exception {
        int databaseSizeBeforeUpdate = userPosteRepository.findAll().size();

        // Create the UserPoste
        UserPosteDTO userPosteDTO = userPosteMapper.toDto(userPoste);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPosteMockMvc
            .perform(
                put("/api/user-postes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPosteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPoste in the database
        List<UserPoste> userPosteList = userPosteRepository.findAll();
        assertThat(userPosteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserPoste() throws Exception {
        // Initialize the database
        userPosteRepository.saveAndFlush(userPoste);

        int databaseSizeBeforeDelete = userPosteRepository.findAll().size();

        // Delete the userPoste
        restUserPosteMockMvc
            .perform(delete("/api/user-postes/{id}", userPoste.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPoste> userPosteList = userPosteRepository.findAll();
        assertThat(userPosteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
