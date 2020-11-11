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
import tect.her.ccm.domain.User;
import tect.her.ccm.repository.EmployeRepository;
import tect.her.ccm.service.EmployeQueryService;
import tect.her.ccm.service.EmployeService;
import tect.her.ccm.service.dto.EmployeCriteria;
import tect.her.ccm.service.dto.EmployeDTO;
import tect.her.ccm.service.mapper.EmployeMapper;

/**
 * Integration tests for the {@link EmployeResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeResourceIT {
    private static final String DEFAULT_COD_BNK = "AAAA";
    private static final String UPDATED_COD_BNK = "BBBB";

    private static final String DEFAULT_COD_EMP = "AAAAAA";
    private static final String UPDATED_COD_EMP = "BBBBBB";

    private static final String DEFAULT_RS_EMP = "AAAAAAAAAA";
    private static final String UPDATED_RS_EMP = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_EMP = "AAAAAAAAAA";
    private static final String UPDATED_NOM_EMP = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_EMP = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_EMP = "BBBBBBBBBB";

    private static final String DEFAULT_FCT_EMP = "AAAAAAAAAA";
    private static final String UPDATED_FCT_EMP = "BBBBBBBBBB";

    private static final String DEFAULT_ADR_EMP = "AAAAAAAAAA";
    private static final String UPDATED_ADR_EMP = "BBBBBBBBBB";

    private static final String DEFAULT_TE_EMP = "AAAAAAAAAA";
    private static final String UPDATED_TE_EMP = "BBBBBBBBBB";

    private static final String DEFAULT_TYP_ENMP = "AAAAAAAAAA";
    private static final String UPDATED_TYP_ENMP = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_MAT = "AAAAAAAAAA";
    private static final String UPDATED_NUM_MAT = "BBBBBBBBBB";

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeMapper employeMapper;

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeQueryService employeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeMockMvc;

    private Employe employe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createEntity(EntityManager em) {
        Employe employe = new Employe()
            .codBnk(DEFAULT_COD_BNK)
            .codEmp(DEFAULT_COD_EMP)
            .rsEmp(DEFAULT_RS_EMP)
            .nomEmp(DEFAULT_NOM_EMP)
            .prenomEmp(DEFAULT_PRENOM_EMP)
            .fctEmp(DEFAULT_FCT_EMP)
            .adrEmp(DEFAULT_ADR_EMP)
            .teEmp(DEFAULT_TE_EMP)
            .typEnmp(DEFAULT_TYP_ENMP)
            .numMat(DEFAULT_NUM_MAT);
        return employe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createUpdatedEntity(EntityManager em) {
        Employe employe = new Employe()
            .codBnk(UPDATED_COD_BNK)
            .codEmp(UPDATED_COD_EMP)
            .rsEmp(UPDATED_RS_EMP)
            .nomEmp(UPDATED_NOM_EMP)
            .prenomEmp(UPDATED_PRENOM_EMP)
            .fctEmp(UPDATED_FCT_EMP)
            .adrEmp(UPDATED_ADR_EMP)
            .teEmp(UPDATED_TE_EMP)
            .typEnmp(UPDATED_TYP_ENMP)
            .numMat(UPDATED_NUM_MAT);
        return employe;
    }

    @BeforeEach
    public void initTest() {
        employe = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmploye() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();
        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);
        restEmployeMockMvc
            .perform(post("/api/employes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate + 1);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getCodBnk()).isEqualTo(DEFAULT_COD_BNK);
        assertThat(testEmploye.getCodEmp()).isEqualTo(DEFAULT_COD_EMP);
        assertThat(testEmploye.getRsEmp()).isEqualTo(DEFAULT_RS_EMP);
        assertThat(testEmploye.getNomEmp()).isEqualTo(DEFAULT_NOM_EMP);
        assertThat(testEmploye.getPrenomEmp()).isEqualTo(DEFAULT_PRENOM_EMP);
        assertThat(testEmploye.getFctEmp()).isEqualTo(DEFAULT_FCT_EMP);
        assertThat(testEmploye.getAdrEmp()).isEqualTo(DEFAULT_ADR_EMP);
        assertThat(testEmploye.getTeEmp()).isEqualTo(DEFAULT_TE_EMP);
        assertThat(testEmploye.getTypEnmp()).isEqualTo(DEFAULT_TYP_ENMP);
        assertThat(testEmploye.getNumMat()).isEqualTo(DEFAULT_NUM_MAT);
    }

    @Test
    @Transactional
    public void createEmployeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();

        // Create the Employe with an existing ID
        employe.setId(1L);
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeMockMvc
            .perform(post("/api/employes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployes() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList
        restEmployeMockMvc
            .perform(get("/api/employes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].codBnk").value(hasItem(DEFAULT_COD_BNK)))
            .andExpect(jsonPath("$.[*].codEmp").value(hasItem(DEFAULT_COD_EMP)))
            .andExpect(jsonPath("$.[*].rsEmp").value(hasItem(DEFAULT_RS_EMP)))
            .andExpect(jsonPath("$.[*].nomEmp").value(hasItem(DEFAULT_NOM_EMP)))
            .andExpect(jsonPath("$.[*].prenomEmp").value(hasItem(DEFAULT_PRENOM_EMP)))
            .andExpect(jsonPath("$.[*].fctEmp").value(hasItem(DEFAULT_FCT_EMP)))
            .andExpect(jsonPath("$.[*].adrEmp").value(hasItem(DEFAULT_ADR_EMP)))
            .andExpect(jsonPath("$.[*].teEmp").value(hasItem(DEFAULT_TE_EMP)))
            .andExpect(jsonPath("$.[*].typEnmp").value(hasItem(DEFAULT_TYP_ENMP)))
            .andExpect(jsonPath("$.[*].numMat").value(hasItem(DEFAULT_NUM_MAT)));
    }

    @Test
    @Transactional
    public void getEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get the employe
        restEmployeMockMvc
            .perform(get("/api/employes/{id}", employe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employe.getId().intValue()))
            .andExpect(jsonPath("$.codBnk").value(DEFAULT_COD_BNK))
            .andExpect(jsonPath("$.codEmp").value(DEFAULT_COD_EMP))
            .andExpect(jsonPath("$.rsEmp").value(DEFAULT_RS_EMP))
            .andExpect(jsonPath("$.nomEmp").value(DEFAULT_NOM_EMP))
            .andExpect(jsonPath("$.prenomEmp").value(DEFAULT_PRENOM_EMP))
            .andExpect(jsonPath("$.fctEmp").value(DEFAULT_FCT_EMP))
            .andExpect(jsonPath("$.adrEmp").value(DEFAULT_ADR_EMP))
            .andExpect(jsonPath("$.teEmp").value(DEFAULT_TE_EMP))
            .andExpect(jsonPath("$.typEnmp").value(DEFAULT_TYP_ENMP))
            .andExpect(jsonPath("$.numMat").value(DEFAULT_NUM_MAT));
    }

    @Test
    @Transactional
    public void getEmployesByIdFiltering() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        Long id = employe.getId();

        defaultEmployeShouldBeFound("id.equals=" + id);
        defaultEmployeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodBnkIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codBnk equals to DEFAULT_COD_BNK
        defaultEmployeShouldBeFound("codBnk.equals=" + DEFAULT_COD_BNK);

        // Get all the employeList where codBnk equals to UPDATED_COD_BNK
        defaultEmployeShouldNotBeFound("codBnk.equals=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodBnkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codBnk not equals to DEFAULT_COD_BNK
        defaultEmployeShouldNotBeFound("codBnk.notEquals=" + DEFAULT_COD_BNK);

        // Get all the employeList where codBnk not equals to UPDATED_COD_BNK
        defaultEmployeShouldBeFound("codBnk.notEquals=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodBnkIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codBnk in DEFAULT_COD_BNK or UPDATED_COD_BNK
        defaultEmployeShouldBeFound("codBnk.in=" + DEFAULT_COD_BNK + "," + UPDATED_COD_BNK);

        // Get all the employeList where codBnk equals to UPDATED_COD_BNK
        defaultEmployeShouldNotBeFound("codBnk.in=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodBnkIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codBnk is not null
        defaultEmployeShouldBeFound("codBnk.specified=true");

        // Get all the employeList where codBnk is null
        defaultEmployeShouldNotBeFound("codBnk.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByCodBnkContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codBnk contains DEFAULT_COD_BNK
        defaultEmployeShouldBeFound("codBnk.contains=" + DEFAULT_COD_BNK);

        // Get all the employeList where codBnk contains UPDATED_COD_BNK
        defaultEmployeShouldNotBeFound("codBnk.contains=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodBnkNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codBnk does not contain DEFAULT_COD_BNK
        defaultEmployeShouldNotBeFound("codBnk.doesNotContain=" + DEFAULT_COD_BNK);

        // Get all the employeList where codBnk does not contain UPDATED_COD_BNK
        defaultEmployeShouldBeFound("codBnk.doesNotContain=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codEmp equals to DEFAULT_COD_EMP
        defaultEmployeShouldBeFound("codEmp.equals=" + DEFAULT_COD_EMP);

        // Get all the employeList where codEmp equals to UPDATED_COD_EMP
        defaultEmployeShouldNotBeFound("codEmp.equals=" + UPDATED_COD_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codEmp not equals to DEFAULT_COD_EMP
        defaultEmployeShouldNotBeFound("codEmp.notEquals=" + DEFAULT_COD_EMP);

        // Get all the employeList where codEmp not equals to UPDATED_COD_EMP
        defaultEmployeShouldBeFound("codEmp.notEquals=" + UPDATED_COD_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codEmp in DEFAULT_COD_EMP or UPDATED_COD_EMP
        defaultEmployeShouldBeFound("codEmp.in=" + DEFAULT_COD_EMP + "," + UPDATED_COD_EMP);

        // Get all the employeList where codEmp equals to UPDATED_COD_EMP
        defaultEmployeShouldNotBeFound("codEmp.in=" + UPDATED_COD_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codEmp is not null
        defaultEmployeShouldBeFound("codEmp.specified=true");

        // Get all the employeList where codEmp is null
        defaultEmployeShouldNotBeFound("codEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByCodEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codEmp contains DEFAULT_COD_EMP
        defaultEmployeShouldBeFound("codEmp.contains=" + DEFAULT_COD_EMP);

        // Get all the employeList where codEmp contains UPDATED_COD_EMP
        defaultEmployeShouldNotBeFound("codEmp.contains=" + UPDATED_COD_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByCodEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where codEmp does not contain DEFAULT_COD_EMP
        defaultEmployeShouldNotBeFound("codEmp.doesNotContain=" + DEFAULT_COD_EMP);

        // Get all the employeList where codEmp does not contain UPDATED_COD_EMP
        defaultEmployeShouldBeFound("codEmp.doesNotContain=" + UPDATED_COD_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByRsEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where rsEmp equals to DEFAULT_RS_EMP
        defaultEmployeShouldBeFound("rsEmp.equals=" + DEFAULT_RS_EMP);

        // Get all the employeList where rsEmp equals to UPDATED_RS_EMP
        defaultEmployeShouldNotBeFound("rsEmp.equals=" + UPDATED_RS_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByRsEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where rsEmp not equals to DEFAULT_RS_EMP
        defaultEmployeShouldNotBeFound("rsEmp.notEquals=" + DEFAULT_RS_EMP);

        // Get all the employeList where rsEmp not equals to UPDATED_RS_EMP
        defaultEmployeShouldBeFound("rsEmp.notEquals=" + UPDATED_RS_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByRsEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where rsEmp in DEFAULT_RS_EMP or UPDATED_RS_EMP
        defaultEmployeShouldBeFound("rsEmp.in=" + DEFAULT_RS_EMP + "," + UPDATED_RS_EMP);

        // Get all the employeList where rsEmp equals to UPDATED_RS_EMP
        defaultEmployeShouldNotBeFound("rsEmp.in=" + UPDATED_RS_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByRsEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where rsEmp is not null
        defaultEmployeShouldBeFound("rsEmp.specified=true");

        // Get all the employeList where rsEmp is null
        defaultEmployeShouldNotBeFound("rsEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByRsEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where rsEmp contains DEFAULT_RS_EMP
        defaultEmployeShouldBeFound("rsEmp.contains=" + DEFAULT_RS_EMP);

        // Get all the employeList where rsEmp contains UPDATED_RS_EMP
        defaultEmployeShouldNotBeFound("rsEmp.contains=" + UPDATED_RS_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByRsEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where rsEmp does not contain DEFAULT_RS_EMP
        defaultEmployeShouldNotBeFound("rsEmp.doesNotContain=" + DEFAULT_RS_EMP);

        // Get all the employeList where rsEmp does not contain UPDATED_RS_EMP
        defaultEmployeShouldBeFound("rsEmp.doesNotContain=" + UPDATED_RS_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nomEmp equals to DEFAULT_NOM_EMP
        defaultEmployeShouldBeFound("nomEmp.equals=" + DEFAULT_NOM_EMP);

        // Get all the employeList where nomEmp equals to UPDATED_NOM_EMP
        defaultEmployeShouldNotBeFound("nomEmp.equals=" + UPDATED_NOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nomEmp not equals to DEFAULT_NOM_EMP
        defaultEmployeShouldNotBeFound("nomEmp.notEquals=" + DEFAULT_NOM_EMP);

        // Get all the employeList where nomEmp not equals to UPDATED_NOM_EMP
        defaultEmployeShouldBeFound("nomEmp.notEquals=" + UPDATED_NOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nomEmp in DEFAULT_NOM_EMP or UPDATED_NOM_EMP
        defaultEmployeShouldBeFound("nomEmp.in=" + DEFAULT_NOM_EMP + "," + UPDATED_NOM_EMP);

        // Get all the employeList where nomEmp equals to UPDATED_NOM_EMP
        defaultEmployeShouldNotBeFound("nomEmp.in=" + UPDATED_NOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nomEmp is not null
        defaultEmployeShouldBeFound("nomEmp.specified=true");

        // Get all the employeList where nomEmp is null
        defaultEmployeShouldNotBeFound("nomEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByNomEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nomEmp contains DEFAULT_NOM_EMP
        defaultEmployeShouldBeFound("nomEmp.contains=" + DEFAULT_NOM_EMP);

        // Get all the employeList where nomEmp contains UPDATED_NOM_EMP
        defaultEmployeShouldNotBeFound("nomEmp.contains=" + UPDATED_NOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nomEmp does not contain DEFAULT_NOM_EMP
        defaultEmployeShouldNotBeFound("nomEmp.doesNotContain=" + DEFAULT_NOM_EMP);

        // Get all the employeList where nomEmp does not contain UPDATED_NOM_EMP
        defaultEmployeShouldBeFound("nomEmp.doesNotContain=" + UPDATED_NOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenomEmp equals to DEFAULT_PRENOM_EMP
        defaultEmployeShouldBeFound("prenomEmp.equals=" + DEFAULT_PRENOM_EMP);

        // Get all the employeList where prenomEmp equals to UPDATED_PRENOM_EMP
        defaultEmployeShouldNotBeFound("prenomEmp.equals=" + UPDATED_PRENOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenomEmp not equals to DEFAULT_PRENOM_EMP
        defaultEmployeShouldNotBeFound("prenomEmp.notEquals=" + DEFAULT_PRENOM_EMP);

        // Get all the employeList where prenomEmp not equals to UPDATED_PRENOM_EMP
        defaultEmployeShouldBeFound("prenomEmp.notEquals=" + UPDATED_PRENOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenomEmp in DEFAULT_PRENOM_EMP or UPDATED_PRENOM_EMP
        defaultEmployeShouldBeFound("prenomEmp.in=" + DEFAULT_PRENOM_EMP + "," + UPDATED_PRENOM_EMP);

        // Get all the employeList where prenomEmp equals to UPDATED_PRENOM_EMP
        defaultEmployeShouldNotBeFound("prenomEmp.in=" + UPDATED_PRENOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenomEmp is not null
        defaultEmployeShouldBeFound("prenomEmp.specified=true");

        // Get all the employeList where prenomEmp is null
        defaultEmployeShouldNotBeFound("prenomEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenomEmp contains DEFAULT_PRENOM_EMP
        defaultEmployeShouldBeFound("prenomEmp.contains=" + DEFAULT_PRENOM_EMP);

        // Get all the employeList where prenomEmp contains UPDATED_PRENOM_EMP
        defaultEmployeShouldNotBeFound("prenomEmp.contains=" + UPDATED_PRENOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenomEmp does not contain DEFAULT_PRENOM_EMP
        defaultEmployeShouldNotBeFound("prenomEmp.doesNotContain=" + DEFAULT_PRENOM_EMP);

        // Get all the employeList where prenomEmp does not contain UPDATED_PRENOM_EMP
        defaultEmployeShouldBeFound("prenomEmp.doesNotContain=" + UPDATED_PRENOM_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByFctEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where fctEmp equals to DEFAULT_FCT_EMP
        defaultEmployeShouldBeFound("fctEmp.equals=" + DEFAULT_FCT_EMP);

        // Get all the employeList where fctEmp equals to UPDATED_FCT_EMP
        defaultEmployeShouldNotBeFound("fctEmp.equals=" + UPDATED_FCT_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByFctEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where fctEmp not equals to DEFAULT_FCT_EMP
        defaultEmployeShouldNotBeFound("fctEmp.notEquals=" + DEFAULT_FCT_EMP);

        // Get all the employeList where fctEmp not equals to UPDATED_FCT_EMP
        defaultEmployeShouldBeFound("fctEmp.notEquals=" + UPDATED_FCT_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByFctEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where fctEmp in DEFAULT_FCT_EMP or UPDATED_FCT_EMP
        defaultEmployeShouldBeFound("fctEmp.in=" + DEFAULT_FCT_EMP + "," + UPDATED_FCT_EMP);

        // Get all the employeList where fctEmp equals to UPDATED_FCT_EMP
        defaultEmployeShouldNotBeFound("fctEmp.in=" + UPDATED_FCT_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByFctEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where fctEmp is not null
        defaultEmployeShouldBeFound("fctEmp.specified=true");

        // Get all the employeList where fctEmp is null
        defaultEmployeShouldNotBeFound("fctEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByFctEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where fctEmp contains DEFAULT_FCT_EMP
        defaultEmployeShouldBeFound("fctEmp.contains=" + DEFAULT_FCT_EMP);

        // Get all the employeList where fctEmp contains UPDATED_FCT_EMP
        defaultEmployeShouldNotBeFound("fctEmp.contains=" + UPDATED_FCT_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByFctEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where fctEmp does not contain DEFAULT_FCT_EMP
        defaultEmployeShouldNotBeFound("fctEmp.doesNotContain=" + DEFAULT_FCT_EMP);

        // Get all the employeList where fctEmp does not contain UPDATED_FCT_EMP
        defaultEmployeShouldBeFound("fctEmp.doesNotContain=" + UPDATED_FCT_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdrEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adrEmp equals to DEFAULT_ADR_EMP
        defaultEmployeShouldBeFound("adrEmp.equals=" + DEFAULT_ADR_EMP);

        // Get all the employeList where adrEmp equals to UPDATED_ADR_EMP
        defaultEmployeShouldNotBeFound("adrEmp.equals=" + UPDATED_ADR_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdrEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adrEmp not equals to DEFAULT_ADR_EMP
        defaultEmployeShouldNotBeFound("adrEmp.notEquals=" + DEFAULT_ADR_EMP);

        // Get all the employeList where adrEmp not equals to UPDATED_ADR_EMP
        defaultEmployeShouldBeFound("adrEmp.notEquals=" + UPDATED_ADR_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdrEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adrEmp in DEFAULT_ADR_EMP or UPDATED_ADR_EMP
        defaultEmployeShouldBeFound("adrEmp.in=" + DEFAULT_ADR_EMP + "," + UPDATED_ADR_EMP);

        // Get all the employeList where adrEmp equals to UPDATED_ADR_EMP
        defaultEmployeShouldNotBeFound("adrEmp.in=" + UPDATED_ADR_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdrEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adrEmp is not null
        defaultEmployeShouldBeFound("adrEmp.specified=true");

        // Get all the employeList where adrEmp is null
        defaultEmployeShouldNotBeFound("adrEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByAdrEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adrEmp contains DEFAULT_ADR_EMP
        defaultEmployeShouldBeFound("adrEmp.contains=" + DEFAULT_ADR_EMP);

        // Get all the employeList where adrEmp contains UPDATED_ADR_EMP
        defaultEmployeShouldNotBeFound("adrEmp.contains=" + UPDATED_ADR_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdrEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adrEmp does not contain DEFAULT_ADR_EMP
        defaultEmployeShouldNotBeFound("adrEmp.doesNotContain=" + DEFAULT_ADR_EMP);

        // Get all the employeList where adrEmp does not contain UPDATED_ADR_EMP
        defaultEmployeShouldBeFound("adrEmp.doesNotContain=" + UPDATED_ADR_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTeEmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where teEmp equals to DEFAULT_TE_EMP
        defaultEmployeShouldBeFound("teEmp.equals=" + DEFAULT_TE_EMP);

        // Get all the employeList where teEmp equals to UPDATED_TE_EMP
        defaultEmployeShouldNotBeFound("teEmp.equals=" + UPDATED_TE_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTeEmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where teEmp not equals to DEFAULT_TE_EMP
        defaultEmployeShouldNotBeFound("teEmp.notEquals=" + DEFAULT_TE_EMP);

        // Get all the employeList where teEmp not equals to UPDATED_TE_EMP
        defaultEmployeShouldBeFound("teEmp.notEquals=" + UPDATED_TE_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTeEmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where teEmp in DEFAULT_TE_EMP or UPDATED_TE_EMP
        defaultEmployeShouldBeFound("teEmp.in=" + DEFAULT_TE_EMP + "," + UPDATED_TE_EMP);

        // Get all the employeList where teEmp equals to UPDATED_TE_EMP
        defaultEmployeShouldNotBeFound("teEmp.in=" + UPDATED_TE_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTeEmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where teEmp is not null
        defaultEmployeShouldBeFound("teEmp.specified=true");

        // Get all the employeList where teEmp is null
        defaultEmployeShouldNotBeFound("teEmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByTeEmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where teEmp contains DEFAULT_TE_EMP
        defaultEmployeShouldBeFound("teEmp.contains=" + DEFAULT_TE_EMP);

        // Get all the employeList where teEmp contains UPDATED_TE_EMP
        defaultEmployeShouldNotBeFound("teEmp.contains=" + UPDATED_TE_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTeEmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where teEmp does not contain DEFAULT_TE_EMP
        defaultEmployeShouldNotBeFound("teEmp.doesNotContain=" + DEFAULT_TE_EMP);

        // Get all the employeList where teEmp does not contain UPDATED_TE_EMP
        defaultEmployeShouldBeFound("teEmp.doesNotContain=" + UPDATED_TE_EMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTypEnmpIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where typEnmp equals to DEFAULT_TYP_ENMP
        defaultEmployeShouldBeFound("typEnmp.equals=" + DEFAULT_TYP_ENMP);

        // Get all the employeList where typEnmp equals to UPDATED_TYP_ENMP
        defaultEmployeShouldNotBeFound("typEnmp.equals=" + UPDATED_TYP_ENMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTypEnmpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where typEnmp not equals to DEFAULT_TYP_ENMP
        defaultEmployeShouldNotBeFound("typEnmp.notEquals=" + DEFAULT_TYP_ENMP);

        // Get all the employeList where typEnmp not equals to UPDATED_TYP_ENMP
        defaultEmployeShouldBeFound("typEnmp.notEquals=" + UPDATED_TYP_ENMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTypEnmpIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where typEnmp in DEFAULT_TYP_ENMP or UPDATED_TYP_ENMP
        defaultEmployeShouldBeFound("typEnmp.in=" + DEFAULT_TYP_ENMP + "," + UPDATED_TYP_ENMP);

        // Get all the employeList where typEnmp equals to UPDATED_TYP_ENMP
        defaultEmployeShouldNotBeFound("typEnmp.in=" + UPDATED_TYP_ENMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTypEnmpIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where typEnmp is not null
        defaultEmployeShouldBeFound("typEnmp.specified=true");

        // Get all the employeList where typEnmp is null
        defaultEmployeShouldNotBeFound("typEnmp.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByTypEnmpContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where typEnmp contains DEFAULT_TYP_ENMP
        defaultEmployeShouldBeFound("typEnmp.contains=" + DEFAULT_TYP_ENMP);

        // Get all the employeList where typEnmp contains UPDATED_TYP_ENMP
        defaultEmployeShouldNotBeFound("typEnmp.contains=" + UPDATED_TYP_ENMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByTypEnmpNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where typEnmp does not contain DEFAULT_TYP_ENMP
        defaultEmployeShouldNotBeFound("typEnmp.doesNotContain=" + DEFAULT_TYP_ENMP);

        // Get all the employeList where typEnmp does not contain UPDATED_TYP_ENMP
        defaultEmployeShouldBeFound("typEnmp.doesNotContain=" + UPDATED_TYP_ENMP);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumMatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numMat equals to DEFAULT_NUM_MAT
        defaultEmployeShouldBeFound("numMat.equals=" + DEFAULT_NUM_MAT);

        // Get all the employeList where numMat equals to UPDATED_NUM_MAT
        defaultEmployeShouldNotBeFound("numMat.equals=" + UPDATED_NUM_MAT);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumMatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numMat not equals to DEFAULT_NUM_MAT
        defaultEmployeShouldNotBeFound("numMat.notEquals=" + DEFAULT_NUM_MAT);

        // Get all the employeList where numMat not equals to UPDATED_NUM_MAT
        defaultEmployeShouldBeFound("numMat.notEquals=" + UPDATED_NUM_MAT);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumMatIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numMat in DEFAULT_NUM_MAT or UPDATED_NUM_MAT
        defaultEmployeShouldBeFound("numMat.in=" + DEFAULT_NUM_MAT + "," + UPDATED_NUM_MAT);

        // Get all the employeList where numMat equals to UPDATED_NUM_MAT
        defaultEmployeShouldNotBeFound("numMat.in=" + UPDATED_NUM_MAT);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumMatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numMat is not null
        defaultEmployeShouldBeFound("numMat.specified=true");

        // Get all the employeList where numMat is null
        defaultEmployeShouldNotBeFound("numMat.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByNumMatContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numMat contains DEFAULT_NUM_MAT
        defaultEmployeShouldBeFound("numMat.contains=" + DEFAULT_NUM_MAT);

        // Get all the employeList where numMat contains UPDATED_NUM_MAT
        defaultEmployeShouldNotBeFound("numMat.contains=" + UPDATED_NUM_MAT);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumMatNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numMat does not contain DEFAULT_NUM_MAT
        defaultEmployeShouldNotBeFound("numMat.doesNotContain=" + DEFAULT_NUM_MAT);

        // Get all the employeList where numMat does not contain UPDATED_NUM_MAT
        defaultEmployeShouldBeFound("numMat.doesNotContain=" + UPDATED_NUM_MAT);
    }

    @Test
    @Transactional
    public void getAllEmployesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        employe.setUser(user);
        employeRepository.saveAndFlush(employe);
        Long userId = user.getId();

        // Get all the employeList where user equals to userId
        defaultEmployeShouldBeFound("userId.equals=" + userId);

        // Get all the employeList where user equals to userId + 1
        defaultEmployeShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeShouldBeFound(String filter) throws Exception {
        restEmployeMockMvc
            .perform(get("/api/employes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].codBnk").value(hasItem(DEFAULT_COD_BNK)))
            .andExpect(jsonPath("$.[*].codEmp").value(hasItem(DEFAULT_COD_EMP)))
            .andExpect(jsonPath("$.[*].rsEmp").value(hasItem(DEFAULT_RS_EMP)))
            .andExpect(jsonPath("$.[*].nomEmp").value(hasItem(DEFAULT_NOM_EMP)))
            .andExpect(jsonPath("$.[*].prenomEmp").value(hasItem(DEFAULT_PRENOM_EMP)))
            .andExpect(jsonPath("$.[*].fctEmp").value(hasItem(DEFAULT_FCT_EMP)))
            .andExpect(jsonPath("$.[*].adrEmp").value(hasItem(DEFAULT_ADR_EMP)))
            .andExpect(jsonPath("$.[*].teEmp").value(hasItem(DEFAULT_TE_EMP)))
            .andExpect(jsonPath("$.[*].typEnmp").value(hasItem(DEFAULT_TYP_ENMP)))
            .andExpect(jsonPath("$.[*].numMat").value(hasItem(DEFAULT_NUM_MAT)));

        // Check, that the count call also returns 1
        restEmployeMockMvc
            .perform(get("/api/employes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeShouldNotBeFound(String filter) throws Exception {
        restEmployeMockMvc
            .perform(get("/api/employes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeMockMvc
            .perform(get("/api/employes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmploye() throws Exception {
        // Get the employe
        restEmployeMockMvc.perform(get("/api/employes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe
        Employe updatedEmploye = employeRepository.findById(employe.getId()).get();
        // Disconnect from session so that the updates on updatedEmploye are not directly saved in db
        em.detach(updatedEmploye);
        updatedEmploye
            .codBnk(UPDATED_COD_BNK)
            .codEmp(UPDATED_COD_EMP)
            .rsEmp(UPDATED_RS_EMP)
            .nomEmp(UPDATED_NOM_EMP)
            .prenomEmp(UPDATED_PRENOM_EMP)
            .fctEmp(UPDATED_FCT_EMP)
            .adrEmp(UPDATED_ADR_EMP)
            .teEmp(UPDATED_TE_EMP)
            .typEnmp(UPDATED_TYP_ENMP)
            .numMat(UPDATED_NUM_MAT);
        EmployeDTO employeDTO = employeMapper.toDto(updatedEmploye);

        restEmployeMockMvc
            .perform(put("/api/employes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getCodBnk()).isEqualTo(UPDATED_COD_BNK);
        assertThat(testEmploye.getCodEmp()).isEqualTo(UPDATED_COD_EMP);
        assertThat(testEmploye.getRsEmp()).isEqualTo(UPDATED_RS_EMP);
        assertThat(testEmploye.getNomEmp()).isEqualTo(UPDATED_NOM_EMP);
        assertThat(testEmploye.getPrenomEmp()).isEqualTo(UPDATED_PRENOM_EMP);
        assertThat(testEmploye.getFctEmp()).isEqualTo(UPDATED_FCT_EMP);
        assertThat(testEmploye.getAdrEmp()).isEqualTo(UPDATED_ADR_EMP);
        assertThat(testEmploye.getTeEmp()).isEqualTo(UPDATED_TE_EMP);
        assertThat(testEmploye.getTypEnmp()).isEqualTo(UPDATED_TYP_ENMP);
        assertThat(testEmploye.getNumMat()).isEqualTo(UPDATED_NUM_MAT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(put("/api/employes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeDelete = employeRepository.findAll().size();

        // Delete the employe
        restEmployeMockMvc
            .perform(delete("/api/employes/{id}", employe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
