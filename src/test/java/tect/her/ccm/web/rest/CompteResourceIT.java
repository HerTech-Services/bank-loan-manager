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
import tect.her.ccm.domain.Client;
import tect.her.ccm.domain.Compte;
import tect.her.ccm.repository.CompteRepository;
import tect.her.ccm.service.CompteService;
import tect.her.ccm.service.dto.CompteDTO;
import tect.her.ccm.service.mapper.CompteMapper;

/**
 * Integration tests for the {@link CompteResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompteResourceIT {
    private static final String DEFAULT_COD_BNK = "AAAA";
    private static final String UPDATED_COD_BNK = "BBBB";

    private static final String DEFAULT_COD_CPT = "AAAAAAAAAA";
    private static final String UPDATED_COD_CPT = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_CPT = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CPT = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_CPT = "AAAAAAAAAA";
    private static final String UPDATED_LIB_CPT = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_CPTA = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CPTA = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_AGC = "AAA";
    private static final String UPDATED_NUM_AGC = "BBB";

    private static final String DEFAULT_TYP_CPT = "AAAA";
    private static final String UPDATED_TYP_CPT = "BBBB";

    private static final String DEFAULT_ETAT = "AAAA";
    private static final String UPDATED_ETAT = "BBBB";

    private static final String DEFAULT_COD_CLI = "AAAAAAA";
    private static final String UPDATED_COD_CLI = "BBBBBBB";

    private static final String DEFAULT_NUM_CTR = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CTR = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_FOR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FOR = "BBBBBBBBBB";

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;

    @Autowired
    private CompteService compteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompteMockMvc;

    private Compte compte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte()
            .codBnk(DEFAULT_COD_BNK)
            .codCpt(DEFAULT_COD_CPT)
            .numCpt(DEFAULT_NUM_CPT)
            .libCpt(DEFAULT_LIB_CPT)
            .numCpta(DEFAULT_NUM_CPTA)
            .numAgc(DEFAULT_NUM_AGC)
            .typCpt(DEFAULT_TYP_CPT)
            .etat(DEFAULT_ETAT)
            .codCli(DEFAULT_COD_CLI)
            .numCtr(DEFAULT_NUM_CTR)
            .codeFor(DEFAULT_CODE_FOR);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        compte.setClient(client);
        return compte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createUpdatedEntity(EntityManager em) {
        Compte compte = new Compte()
            .codBnk(UPDATED_COD_BNK)
            .codCpt(UPDATED_COD_CPT)
            .numCpt(UPDATED_NUM_CPT)
            .libCpt(UPDATED_LIB_CPT)
            .numCpta(UPDATED_NUM_CPTA)
            .numAgc(UPDATED_NUM_AGC)
            .typCpt(UPDATED_TYP_CPT)
            .etat(UPDATED_ETAT)
            .codCli(UPDATED_COD_CLI)
            .numCtr(UPDATED_NUM_CTR)
            .codeFor(UPDATED_CODE_FOR);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        compte.setClient(client);
        return compte;
    }

    @BeforeEach
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();
        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);
        restCompteMockMvc
            .perform(post("/api/comptes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getCodBnk()).isEqualTo(DEFAULT_COD_BNK);
        assertThat(testCompte.getCodCpt()).isEqualTo(DEFAULT_COD_CPT);
        assertThat(testCompte.getNumCpt()).isEqualTo(DEFAULT_NUM_CPT);
        assertThat(testCompte.getLibCpt()).isEqualTo(DEFAULT_LIB_CPT);
        assertThat(testCompte.getNumCpta()).isEqualTo(DEFAULT_NUM_CPTA);
        assertThat(testCompte.getNumAgc()).isEqualTo(DEFAULT_NUM_AGC);
        assertThat(testCompte.getTypCpt()).isEqualTo(DEFAULT_TYP_CPT);
        assertThat(testCompte.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testCompte.getCodCli()).isEqualTo(DEFAULT_COD_CLI);
        assertThat(testCompte.getNumCtr()).isEqualTo(DEFAULT_NUM_CTR);
        assertThat(testCompte.getCodeFor()).isEqualTo(DEFAULT_CODE_FOR);
    }

    @Test
    @Transactional
    public void createCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte with an existing ID
        compte.setId(1L);
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc
            .perform(post("/api/comptes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc
            .perform(get("/api/comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].codBnk").value(hasItem(DEFAULT_COD_BNK)))
            .andExpect(jsonPath("$.[*].codCpt").value(hasItem(DEFAULT_COD_CPT)))
            .andExpect(jsonPath("$.[*].numCpt").value(hasItem(DEFAULT_NUM_CPT)))
            .andExpect(jsonPath("$.[*].libCpt").value(hasItem(DEFAULT_LIB_CPT)))
            .andExpect(jsonPath("$.[*].numCpta").value(hasItem(DEFAULT_NUM_CPTA)))
            .andExpect(jsonPath("$.[*].numAgc").value(hasItem(DEFAULT_NUM_AGC)))
            .andExpect(jsonPath("$.[*].typCpt").value(hasItem(DEFAULT_TYP_CPT)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].codCli").value(hasItem(DEFAULT_COD_CLI)))
            .andExpect(jsonPath("$.[*].numCtr").value(hasItem(DEFAULT_NUM_CTR)))
            .andExpect(jsonPath("$.[*].codeFor").value(hasItem(DEFAULT_CODE_FOR)));
    }

    @Test
    @Transactional
    public void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc
            .perform(get("/api/comptes/{id}", compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.codBnk").value(DEFAULT_COD_BNK))
            .andExpect(jsonPath("$.codCpt").value(DEFAULT_COD_CPT))
            .andExpect(jsonPath("$.numCpt").value(DEFAULT_NUM_CPT))
            .andExpect(jsonPath("$.libCpt").value(DEFAULT_LIB_CPT))
            .andExpect(jsonPath("$.numCpta").value(DEFAULT_NUM_CPTA))
            .andExpect(jsonPath("$.numAgc").value(DEFAULT_NUM_AGC))
            .andExpect(jsonPath("$.typCpt").value(DEFAULT_TYP_CPT))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.codCli").value(DEFAULT_COD_CLI))
            .andExpect(jsonPath("$.numCtr").value(DEFAULT_NUM_CTR))
            .andExpect(jsonPath("$.codeFor").value(DEFAULT_CODE_FOR));
    }

    @Test
    @Transactional
    public void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findById(compte.getId()).get();
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte
            .codBnk(UPDATED_COD_BNK)
            .codCpt(UPDATED_COD_CPT)
            .numCpt(UPDATED_NUM_CPT)
            .libCpt(UPDATED_LIB_CPT)
            .numCpta(UPDATED_NUM_CPTA)
            .numAgc(UPDATED_NUM_AGC)
            .typCpt(UPDATED_TYP_CPT)
            .etat(UPDATED_ETAT)
            .codCli(UPDATED_COD_CLI)
            .numCtr(UPDATED_NUM_CTR)
            .codeFor(UPDATED_CODE_FOR);
        CompteDTO compteDTO = compteMapper.toDto(updatedCompte);

        restCompteMockMvc
            .perform(put("/api/comptes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getCodBnk()).isEqualTo(UPDATED_COD_BNK);
        assertThat(testCompte.getCodCpt()).isEqualTo(UPDATED_COD_CPT);
        assertThat(testCompte.getNumCpt()).isEqualTo(UPDATED_NUM_CPT);
        assertThat(testCompte.getLibCpt()).isEqualTo(UPDATED_LIB_CPT);
        assertThat(testCompte.getNumCpta()).isEqualTo(UPDATED_NUM_CPTA);
        assertThat(testCompte.getNumAgc()).isEqualTo(UPDATED_NUM_AGC);
        assertThat(testCompte.getTypCpt()).isEqualTo(UPDATED_TYP_CPT);
        assertThat(testCompte.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testCompte.getCodCli()).isEqualTo(UPDATED_COD_CLI);
        assertThat(testCompte.getNumCtr()).isEqualTo(UPDATED_NUM_CTR);
        assertThat(testCompte.getCodeFor()).isEqualTo(UPDATED_CODE_FOR);
    }

    @Test
    @Transactional
    public void updateNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(put("/api/comptes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Delete the compte
        restCompteMockMvc
            .perform(delete("/api/comptes/{id}", compte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
