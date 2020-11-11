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
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.domain.enumeration.MaritalStatus;
import tect.her.ccm.domain.enumeration.Sex;
import tect.her.ccm.repository.ClientRepository;
import tect.her.ccm.service.ClientQueryService;
import tect.her.ccm.service.ClientService;
import tect.her.ccm.service.dto.ClientCriteria;
import tect.her.ccm.service.dto.ClientDTO;
import tect.her.ccm.service.mapper.ClientMapper;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {
    private static final String DEFAULT_COD_BNK = "AAAA";
    private static final String UPDATED_COD_BNK = "BBBB";

    private static final String DEFAULT_COD_CLI = "AAAAAAA";
    private static final String UPDATED_COD_CLI = "BBBBBBB";

    private static final String DEFAULT_NOM_CLI = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLI = "BBBBBBBBBB";

    private static final String DEFAULT_MEND_CLI = "AAAAAAAAAA";
    private static final String UPDATED_MEND_CLI = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_SF_CLI = MaritalStatus.CELIBATIRE;
    private static final MaritalStatus UPDATED_SF_CLI = MaritalStatus.MARIE;

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DAT_NAI = "AAAAAAAAAA";
    private static final String UPDATED_DAT_NAI = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_NAI = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAI = "BBBBBBBBBB";

    private static final String DEFAULT_NAT_CLI = "AAAAAAAAAA";
    private static final String UPDATED_NAT_CLI = "BBBBBBBBBB";

    private static final String DEFAULT_LNG_CLI = "AAAAAAAAAA";
    private static final String UPDATED_LNG_CLI = "BBBBBBBBBB";

    private static final String DEFAULT_SOC_CLI = "AAAAAAAAAA";
    private static final String UPDATED_SOC_CLI = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOI = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOI = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEXE = Sex.MASCULIN;
    private static final Sex UPDATED_SEXE = Sex.FEMININ;

    private static final String DEFAULT_NUM_CNI = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_DAT_CNI = "AAAAAAAAAA";
    private static final String UPDATED_DAT_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_FIN_CNI = "AAAAAAAAAA";
    private static final String UPDATED_FIN_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_CNI = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_AUTO_CNI = "AAAAAAAAAA";
    private static final String UPDATED_AUTO_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_ADR = "AAAAAAAAAA";
    private static final String UPDATED_ADR = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_LOC = "AAAAAAAAAA";
    private static final String UPDATED_LOC = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_AGNCE = "AAAAAAAAAA";
    private static final String UPDATED_AGNCE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientQueryService clientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .codBnk(DEFAULT_COD_BNK)
            .codCli(DEFAULT_COD_CLI)
            .nomCli(DEFAULT_NOM_CLI)
            .mendCli(DEFAULT_MEND_CLI)
            .sfCli(DEFAULT_SF_CLI)
            .titre(DEFAULT_TITRE)
            .datNai(DEFAULT_DAT_NAI)
            .lieuNai(DEFAULT_LIEU_NAI)
            .natCli(DEFAULT_NAT_CLI)
            .lngCli(DEFAULT_LNG_CLI)
            .socCli(DEFAULT_SOC_CLI)
            .emploi(DEFAULT_EMPLOI)
            .sexe(DEFAULT_SEXE)
            .numCni(DEFAULT_NUM_CNI)
            .datCni(DEFAULT_DAT_CNI)
            .finCni(DEFAULT_FIN_CNI)
            .lieuCni(DEFAULT_LIEU_CNI)
            .autoCni(DEFAULT_AUTO_CNI)
            .adr(DEFAULT_ADR)
            .tel(DEFAULT_TEL)
            .ville(DEFAULT_VILLE)
            .site(DEFAULT_SITE)
            .loc(DEFAULT_LOC)
            .fax(DEFAULT_FAX)
            .agnce(DEFAULT_AGNCE)
            .mail(DEFAULT_MAIL)
            .pays(DEFAULT_PAYS);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .codBnk(UPDATED_COD_BNK)
            .codCli(UPDATED_COD_CLI)
            .nomCli(UPDATED_NOM_CLI)
            .mendCli(UPDATED_MEND_CLI)
            .sfCli(UPDATED_SF_CLI)
            .titre(UPDATED_TITRE)
            .datNai(UPDATED_DAT_NAI)
            .lieuNai(UPDATED_LIEU_NAI)
            .natCli(UPDATED_NAT_CLI)
            .lngCli(UPDATED_LNG_CLI)
            .socCli(UPDATED_SOC_CLI)
            .emploi(UPDATED_EMPLOI)
            .sexe(UPDATED_SEXE)
            .numCni(UPDATED_NUM_CNI)
            .datCni(UPDATED_DAT_CNI)
            .finCni(UPDATED_FIN_CNI)
            .lieuCni(UPDATED_LIEU_CNI)
            .autoCni(UPDATED_AUTO_CNI)
            .adr(UPDATED_ADR)
            .tel(UPDATED_TEL)
            .ville(UPDATED_VILLE)
            .site(UPDATED_SITE)
            .loc(UPDATED_LOC)
            .fax(UPDATED_FAX)
            .agnce(UPDATED_AGNCE)
            .mail(UPDATED_MAIL)
            .pays(UPDATED_PAYS);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        restClientMockMvc
            .perform(post("/api/clients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getCodBnk()).isEqualTo(DEFAULT_COD_BNK);
        assertThat(testClient.getCodCli()).isEqualTo(DEFAULT_COD_CLI);
        assertThat(testClient.getNomCli()).isEqualTo(DEFAULT_NOM_CLI);
        assertThat(testClient.getMendCli()).isEqualTo(DEFAULT_MEND_CLI);
        assertThat(testClient.getSfCli()).isEqualTo(DEFAULT_SF_CLI);
        assertThat(testClient.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testClient.getDatNai()).isEqualTo(DEFAULT_DAT_NAI);
        assertThat(testClient.getLieuNai()).isEqualTo(DEFAULT_LIEU_NAI);
        assertThat(testClient.getNatCli()).isEqualTo(DEFAULT_NAT_CLI);
        assertThat(testClient.getLngCli()).isEqualTo(DEFAULT_LNG_CLI);
        assertThat(testClient.getSocCli()).isEqualTo(DEFAULT_SOC_CLI);
        assertThat(testClient.getEmploi()).isEqualTo(DEFAULT_EMPLOI);
        assertThat(testClient.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testClient.getNumCni()).isEqualTo(DEFAULT_NUM_CNI);
        assertThat(testClient.getDatCni()).isEqualTo(DEFAULT_DAT_CNI);
        assertThat(testClient.getFinCni()).isEqualTo(DEFAULT_FIN_CNI);
        assertThat(testClient.getLieuCni()).isEqualTo(DEFAULT_LIEU_CNI);
        assertThat(testClient.getAutoCni()).isEqualTo(DEFAULT_AUTO_CNI);
        assertThat(testClient.getAdr()).isEqualTo(DEFAULT_ADR);
        assertThat(testClient.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testClient.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testClient.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testClient.getLoc()).isEqualTo(DEFAULT_LOC);
        assertThat(testClient.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testClient.getAgnce()).isEqualTo(DEFAULT_AGNCE);
        assertThat(testClient.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testClient.getPays()).isEqualTo(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post("/api/clients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].codBnk").value(hasItem(DEFAULT_COD_BNK)))
            .andExpect(jsonPath("$.[*].codCli").value(hasItem(DEFAULT_COD_CLI)))
            .andExpect(jsonPath("$.[*].nomCli").value(hasItem(DEFAULT_NOM_CLI)))
            .andExpect(jsonPath("$.[*].mendCli").value(hasItem(DEFAULT_MEND_CLI)))
            .andExpect(jsonPath("$.[*].sfCli").value(hasItem(DEFAULT_SF_CLI.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].datNai").value(hasItem(DEFAULT_DAT_NAI)))
            .andExpect(jsonPath("$.[*].lieuNai").value(hasItem(DEFAULT_LIEU_NAI)))
            .andExpect(jsonPath("$.[*].natCli").value(hasItem(DEFAULT_NAT_CLI)))
            .andExpect(jsonPath("$.[*].lngCli").value(hasItem(DEFAULT_LNG_CLI)))
            .andExpect(jsonPath("$.[*].socCli").value(hasItem(DEFAULT_SOC_CLI)))
            .andExpect(jsonPath("$.[*].emploi").value(hasItem(DEFAULT_EMPLOI)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].numCni").value(hasItem(DEFAULT_NUM_CNI)))
            .andExpect(jsonPath("$.[*].datCni").value(hasItem(DEFAULT_DAT_CNI)))
            .andExpect(jsonPath("$.[*].finCni").value(hasItem(DEFAULT_FIN_CNI)))
            .andExpect(jsonPath("$.[*].lieuCni").value(hasItem(DEFAULT_LIEU_CNI)))
            .andExpect(jsonPath("$.[*].autoCni").value(hasItem(DEFAULT_AUTO_CNI)))
            .andExpect(jsonPath("$.[*].adr").value(hasItem(DEFAULT_ADR)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].loc").value(hasItem(DEFAULT_LOC)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].agnce").value(hasItem(DEFAULT_AGNCE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)));
    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.codBnk").value(DEFAULT_COD_BNK))
            .andExpect(jsonPath("$.codCli").value(DEFAULT_COD_CLI))
            .andExpect(jsonPath("$.nomCli").value(DEFAULT_NOM_CLI))
            .andExpect(jsonPath("$.mendCli").value(DEFAULT_MEND_CLI))
            .andExpect(jsonPath("$.sfCli").value(DEFAULT_SF_CLI.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.datNai").value(DEFAULT_DAT_NAI))
            .andExpect(jsonPath("$.lieuNai").value(DEFAULT_LIEU_NAI))
            .andExpect(jsonPath("$.natCli").value(DEFAULT_NAT_CLI))
            .andExpect(jsonPath("$.lngCli").value(DEFAULT_LNG_CLI))
            .andExpect(jsonPath("$.socCli").value(DEFAULT_SOC_CLI))
            .andExpect(jsonPath("$.emploi").value(DEFAULT_EMPLOI))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.numCni").value(DEFAULT_NUM_CNI))
            .andExpect(jsonPath("$.datCni").value(DEFAULT_DAT_CNI))
            .andExpect(jsonPath("$.finCni").value(DEFAULT_FIN_CNI))
            .andExpect(jsonPath("$.lieuCni").value(DEFAULT_LIEU_CNI))
            .andExpect(jsonPath("$.autoCni").value(DEFAULT_AUTO_CNI))
            .andExpect(jsonPath("$.adr").value(DEFAULT_ADR))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.loc").value(DEFAULT_LOC))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.agnce").value(DEFAULT_AGNCE))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS));
    }

    @Test
    @Transactional
    public void getClientsByIdFiltering() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        Long id = client.getId();

        defaultClientShouldBeFound("id.equals=" + id);
        defaultClientShouldNotBeFound("id.notEquals=" + id);

        defaultClientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.greaterThan=" + id);

        defaultClientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllClientsByCodBnkIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codBnk equals to DEFAULT_COD_BNK
        defaultClientShouldBeFound("codBnk.equals=" + DEFAULT_COD_BNK);

        // Get all the clientList where codBnk equals to UPDATED_COD_BNK
        defaultClientShouldNotBeFound("codBnk.equals=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllClientsByCodBnkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codBnk not equals to DEFAULT_COD_BNK
        defaultClientShouldNotBeFound("codBnk.notEquals=" + DEFAULT_COD_BNK);

        // Get all the clientList where codBnk not equals to UPDATED_COD_BNK
        defaultClientShouldBeFound("codBnk.notEquals=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllClientsByCodBnkIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codBnk in DEFAULT_COD_BNK or UPDATED_COD_BNK
        defaultClientShouldBeFound("codBnk.in=" + DEFAULT_COD_BNK + "," + UPDATED_COD_BNK);

        // Get all the clientList where codBnk equals to UPDATED_COD_BNK
        defaultClientShouldNotBeFound("codBnk.in=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllClientsByCodBnkIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codBnk is not null
        defaultClientShouldBeFound("codBnk.specified=true");

        // Get all the clientList where codBnk is null
        defaultClientShouldNotBeFound("codBnk.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByCodBnkContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codBnk contains DEFAULT_COD_BNK
        defaultClientShouldBeFound("codBnk.contains=" + DEFAULT_COD_BNK);

        // Get all the clientList where codBnk contains UPDATED_COD_BNK
        defaultClientShouldNotBeFound("codBnk.contains=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllClientsByCodBnkNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codBnk does not contain DEFAULT_COD_BNK
        defaultClientShouldNotBeFound("codBnk.doesNotContain=" + DEFAULT_COD_BNK);

        // Get all the clientList where codBnk does not contain UPDATED_COD_BNK
        defaultClientShouldBeFound("codBnk.doesNotContain=" + UPDATED_COD_BNK);
    }

    @Test
    @Transactional
    public void getAllClientsByCodCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codCli equals to DEFAULT_COD_CLI
        defaultClientShouldBeFound("codCli.equals=" + DEFAULT_COD_CLI);

        // Get all the clientList where codCli equals to UPDATED_COD_CLI
        defaultClientShouldNotBeFound("codCli.equals=" + UPDATED_COD_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByCodCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codCli not equals to DEFAULT_COD_CLI
        defaultClientShouldNotBeFound("codCli.notEquals=" + DEFAULT_COD_CLI);

        // Get all the clientList where codCli not equals to UPDATED_COD_CLI
        defaultClientShouldBeFound("codCli.notEquals=" + UPDATED_COD_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByCodCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codCli in DEFAULT_COD_CLI or UPDATED_COD_CLI
        defaultClientShouldBeFound("codCli.in=" + DEFAULT_COD_CLI + "," + UPDATED_COD_CLI);

        // Get all the clientList where codCli equals to UPDATED_COD_CLI
        defaultClientShouldNotBeFound("codCli.in=" + UPDATED_COD_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByCodCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codCli is not null
        defaultClientShouldBeFound("codCli.specified=true");

        // Get all the clientList where codCli is null
        defaultClientShouldNotBeFound("codCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByCodCliContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codCli contains DEFAULT_COD_CLI
        defaultClientShouldBeFound("codCli.contains=" + DEFAULT_COD_CLI);

        // Get all the clientList where codCli contains UPDATED_COD_CLI
        defaultClientShouldNotBeFound("codCli.contains=" + UPDATED_COD_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByCodCliNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where codCli does not contain DEFAULT_COD_CLI
        defaultClientShouldNotBeFound("codCli.doesNotContain=" + DEFAULT_COD_CLI);

        // Get all the clientList where codCli does not contain UPDATED_COD_CLI
        defaultClientShouldBeFound("codCli.doesNotContain=" + UPDATED_COD_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNomCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomCli equals to DEFAULT_NOM_CLI
        defaultClientShouldBeFound("nomCli.equals=" + DEFAULT_NOM_CLI);

        // Get all the clientList where nomCli equals to UPDATED_NOM_CLI
        defaultClientShouldNotBeFound("nomCli.equals=" + UPDATED_NOM_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNomCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomCli not equals to DEFAULT_NOM_CLI
        defaultClientShouldNotBeFound("nomCli.notEquals=" + DEFAULT_NOM_CLI);

        // Get all the clientList where nomCli not equals to UPDATED_NOM_CLI
        defaultClientShouldBeFound("nomCli.notEquals=" + UPDATED_NOM_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNomCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomCli in DEFAULT_NOM_CLI or UPDATED_NOM_CLI
        defaultClientShouldBeFound("nomCli.in=" + DEFAULT_NOM_CLI + "," + UPDATED_NOM_CLI);

        // Get all the clientList where nomCli equals to UPDATED_NOM_CLI
        defaultClientShouldNotBeFound("nomCli.in=" + UPDATED_NOM_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNomCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomCli is not null
        defaultClientShouldBeFound("nomCli.specified=true");

        // Get all the clientList where nomCli is null
        defaultClientShouldNotBeFound("nomCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByNomCliContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomCli contains DEFAULT_NOM_CLI
        defaultClientShouldBeFound("nomCli.contains=" + DEFAULT_NOM_CLI);

        // Get all the clientList where nomCli contains UPDATED_NOM_CLI
        defaultClientShouldNotBeFound("nomCli.contains=" + UPDATED_NOM_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNomCliNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where nomCli does not contain DEFAULT_NOM_CLI
        defaultClientShouldNotBeFound("nomCli.doesNotContain=" + DEFAULT_NOM_CLI);

        // Get all the clientList where nomCli does not contain UPDATED_NOM_CLI
        defaultClientShouldBeFound("nomCli.doesNotContain=" + UPDATED_NOM_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByMendCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mendCli equals to DEFAULT_MEND_CLI
        defaultClientShouldBeFound("mendCli.equals=" + DEFAULT_MEND_CLI);

        // Get all the clientList where mendCli equals to UPDATED_MEND_CLI
        defaultClientShouldNotBeFound("mendCli.equals=" + UPDATED_MEND_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByMendCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mendCli not equals to DEFAULT_MEND_CLI
        defaultClientShouldNotBeFound("mendCli.notEquals=" + DEFAULT_MEND_CLI);

        // Get all the clientList where mendCli not equals to UPDATED_MEND_CLI
        defaultClientShouldBeFound("mendCli.notEquals=" + UPDATED_MEND_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByMendCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mendCli in DEFAULT_MEND_CLI or UPDATED_MEND_CLI
        defaultClientShouldBeFound("mendCli.in=" + DEFAULT_MEND_CLI + "," + UPDATED_MEND_CLI);

        // Get all the clientList where mendCli equals to UPDATED_MEND_CLI
        defaultClientShouldNotBeFound("mendCli.in=" + UPDATED_MEND_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByMendCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mendCli is not null
        defaultClientShouldBeFound("mendCli.specified=true");

        // Get all the clientList where mendCli is null
        defaultClientShouldNotBeFound("mendCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByMendCliContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mendCli contains DEFAULT_MEND_CLI
        defaultClientShouldBeFound("mendCli.contains=" + DEFAULT_MEND_CLI);

        // Get all the clientList where mendCli contains UPDATED_MEND_CLI
        defaultClientShouldNotBeFound("mendCli.contains=" + UPDATED_MEND_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByMendCliNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mendCli does not contain DEFAULT_MEND_CLI
        defaultClientShouldNotBeFound("mendCli.doesNotContain=" + DEFAULT_MEND_CLI);

        // Get all the clientList where mendCli does not contain UPDATED_MEND_CLI
        defaultClientShouldBeFound("mendCli.doesNotContain=" + UPDATED_MEND_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySfCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sfCli equals to DEFAULT_SF_CLI
        defaultClientShouldBeFound("sfCli.equals=" + DEFAULT_SF_CLI);

        // Get all the clientList where sfCli equals to UPDATED_SF_CLI
        defaultClientShouldNotBeFound("sfCli.equals=" + UPDATED_SF_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySfCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sfCli not equals to DEFAULT_SF_CLI
        defaultClientShouldNotBeFound("sfCli.notEquals=" + DEFAULT_SF_CLI);

        // Get all the clientList where sfCli not equals to UPDATED_SF_CLI
        defaultClientShouldBeFound("sfCli.notEquals=" + UPDATED_SF_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySfCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sfCli in DEFAULT_SF_CLI or UPDATED_SF_CLI
        defaultClientShouldBeFound("sfCli.in=" + DEFAULT_SF_CLI + "," + UPDATED_SF_CLI);

        // Get all the clientList where sfCli equals to UPDATED_SF_CLI
        defaultClientShouldNotBeFound("sfCli.in=" + UPDATED_SF_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySfCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sfCli is not null
        defaultClientShouldBeFound("sfCli.specified=true");

        // Get all the clientList where sfCli is null
        defaultClientShouldNotBeFound("sfCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where titre equals to DEFAULT_TITRE
        defaultClientShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the clientList where titre equals to UPDATED_TITRE
        defaultClientShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllClientsByTitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where titre not equals to DEFAULT_TITRE
        defaultClientShouldNotBeFound("titre.notEquals=" + DEFAULT_TITRE);

        // Get all the clientList where titre not equals to UPDATED_TITRE
        defaultClientShouldBeFound("titre.notEquals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllClientsByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultClientShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the clientList where titre equals to UPDATED_TITRE
        defaultClientShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllClientsByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where titre is not null
        defaultClientShouldBeFound("titre.specified=true");

        // Get all the clientList where titre is null
        defaultClientShouldNotBeFound("titre.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTitreContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where titre contains DEFAULT_TITRE
        defaultClientShouldBeFound("titre.contains=" + DEFAULT_TITRE);

        // Get all the clientList where titre contains UPDATED_TITRE
        defaultClientShouldNotBeFound("titre.contains=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllClientsByTitreNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where titre does not contain DEFAULT_TITRE
        defaultClientShouldNotBeFound("titre.doesNotContain=" + DEFAULT_TITRE);

        // Get all the clientList where titre does not contain UPDATED_TITRE
        defaultClientShouldBeFound("titre.doesNotContain=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllClientsByDatNaiIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datNai equals to DEFAULT_DAT_NAI
        defaultClientShouldBeFound("datNai.equals=" + DEFAULT_DAT_NAI);

        // Get all the clientList where datNai equals to UPDATED_DAT_NAI
        defaultClientShouldNotBeFound("datNai.equals=" + UPDATED_DAT_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatNaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datNai not equals to DEFAULT_DAT_NAI
        defaultClientShouldNotBeFound("datNai.notEquals=" + DEFAULT_DAT_NAI);

        // Get all the clientList where datNai not equals to UPDATED_DAT_NAI
        defaultClientShouldBeFound("datNai.notEquals=" + UPDATED_DAT_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatNaiIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datNai in DEFAULT_DAT_NAI or UPDATED_DAT_NAI
        defaultClientShouldBeFound("datNai.in=" + DEFAULT_DAT_NAI + "," + UPDATED_DAT_NAI);

        // Get all the clientList where datNai equals to UPDATED_DAT_NAI
        defaultClientShouldNotBeFound("datNai.in=" + UPDATED_DAT_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatNaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datNai is not null
        defaultClientShouldBeFound("datNai.specified=true");

        // Get all the clientList where datNai is null
        defaultClientShouldNotBeFound("datNai.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDatNaiContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datNai contains DEFAULT_DAT_NAI
        defaultClientShouldBeFound("datNai.contains=" + DEFAULT_DAT_NAI);

        // Get all the clientList where datNai contains UPDATED_DAT_NAI
        defaultClientShouldNotBeFound("datNai.contains=" + UPDATED_DAT_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatNaiNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datNai does not contain DEFAULT_DAT_NAI
        defaultClientShouldNotBeFound("datNai.doesNotContain=" + DEFAULT_DAT_NAI);

        // Get all the clientList where datNai does not contain UPDATED_DAT_NAI
        defaultClientShouldBeFound("datNai.doesNotContain=" + UPDATED_DAT_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuNaiIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuNai equals to DEFAULT_LIEU_NAI
        defaultClientShouldBeFound("lieuNai.equals=" + DEFAULT_LIEU_NAI);

        // Get all the clientList where lieuNai equals to UPDATED_LIEU_NAI
        defaultClientShouldNotBeFound("lieuNai.equals=" + UPDATED_LIEU_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuNaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuNai not equals to DEFAULT_LIEU_NAI
        defaultClientShouldNotBeFound("lieuNai.notEquals=" + DEFAULT_LIEU_NAI);

        // Get all the clientList where lieuNai not equals to UPDATED_LIEU_NAI
        defaultClientShouldBeFound("lieuNai.notEquals=" + UPDATED_LIEU_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuNaiIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuNai in DEFAULT_LIEU_NAI or UPDATED_LIEU_NAI
        defaultClientShouldBeFound("lieuNai.in=" + DEFAULT_LIEU_NAI + "," + UPDATED_LIEU_NAI);

        // Get all the clientList where lieuNai equals to UPDATED_LIEU_NAI
        defaultClientShouldNotBeFound("lieuNai.in=" + UPDATED_LIEU_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuNaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuNai is not null
        defaultClientShouldBeFound("lieuNai.specified=true");

        // Get all the clientList where lieuNai is null
        defaultClientShouldNotBeFound("lieuNai.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByLieuNaiContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuNai contains DEFAULT_LIEU_NAI
        defaultClientShouldBeFound("lieuNai.contains=" + DEFAULT_LIEU_NAI);

        // Get all the clientList where lieuNai contains UPDATED_LIEU_NAI
        defaultClientShouldNotBeFound("lieuNai.contains=" + UPDATED_LIEU_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuNaiNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuNai does not contain DEFAULT_LIEU_NAI
        defaultClientShouldNotBeFound("lieuNai.doesNotContain=" + DEFAULT_LIEU_NAI);

        // Get all the clientList where lieuNai does not contain UPDATED_LIEU_NAI
        defaultClientShouldBeFound("lieuNai.doesNotContain=" + UPDATED_LIEU_NAI);
    }

    @Test
    @Transactional
    public void getAllClientsByNatCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where natCli equals to DEFAULT_NAT_CLI
        defaultClientShouldBeFound("natCli.equals=" + DEFAULT_NAT_CLI);

        // Get all the clientList where natCli equals to UPDATED_NAT_CLI
        defaultClientShouldNotBeFound("natCli.equals=" + UPDATED_NAT_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNatCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where natCli not equals to DEFAULT_NAT_CLI
        defaultClientShouldNotBeFound("natCli.notEquals=" + DEFAULT_NAT_CLI);

        // Get all the clientList where natCli not equals to UPDATED_NAT_CLI
        defaultClientShouldBeFound("natCli.notEquals=" + UPDATED_NAT_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNatCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where natCli in DEFAULT_NAT_CLI or UPDATED_NAT_CLI
        defaultClientShouldBeFound("natCli.in=" + DEFAULT_NAT_CLI + "," + UPDATED_NAT_CLI);

        // Get all the clientList where natCli equals to UPDATED_NAT_CLI
        defaultClientShouldNotBeFound("natCli.in=" + UPDATED_NAT_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNatCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where natCli is not null
        defaultClientShouldBeFound("natCli.specified=true");

        // Get all the clientList where natCli is null
        defaultClientShouldNotBeFound("natCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByNatCliContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where natCli contains DEFAULT_NAT_CLI
        defaultClientShouldBeFound("natCli.contains=" + DEFAULT_NAT_CLI);

        // Get all the clientList where natCli contains UPDATED_NAT_CLI
        defaultClientShouldNotBeFound("natCli.contains=" + UPDATED_NAT_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByNatCliNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where natCli does not contain DEFAULT_NAT_CLI
        defaultClientShouldNotBeFound("natCli.doesNotContain=" + DEFAULT_NAT_CLI);

        // Get all the clientList where natCli does not contain UPDATED_NAT_CLI
        defaultClientShouldBeFound("natCli.doesNotContain=" + UPDATED_NAT_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByLngCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lngCli equals to DEFAULT_LNG_CLI
        defaultClientShouldBeFound("lngCli.equals=" + DEFAULT_LNG_CLI);

        // Get all the clientList where lngCli equals to UPDATED_LNG_CLI
        defaultClientShouldNotBeFound("lngCli.equals=" + UPDATED_LNG_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByLngCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lngCli not equals to DEFAULT_LNG_CLI
        defaultClientShouldNotBeFound("lngCli.notEquals=" + DEFAULT_LNG_CLI);

        // Get all the clientList where lngCli not equals to UPDATED_LNG_CLI
        defaultClientShouldBeFound("lngCli.notEquals=" + UPDATED_LNG_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByLngCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lngCli in DEFAULT_LNG_CLI or UPDATED_LNG_CLI
        defaultClientShouldBeFound("lngCli.in=" + DEFAULT_LNG_CLI + "," + UPDATED_LNG_CLI);

        // Get all the clientList where lngCli equals to UPDATED_LNG_CLI
        defaultClientShouldNotBeFound("lngCli.in=" + UPDATED_LNG_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByLngCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lngCli is not null
        defaultClientShouldBeFound("lngCli.specified=true");

        // Get all the clientList where lngCli is null
        defaultClientShouldNotBeFound("lngCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByLngCliContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lngCli contains DEFAULT_LNG_CLI
        defaultClientShouldBeFound("lngCli.contains=" + DEFAULT_LNG_CLI);

        // Get all the clientList where lngCli contains UPDATED_LNG_CLI
        defaultClientShouldNotBeFound("lngCli.contains=" + UPDATED_LNG_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByLngCliNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lngCli does not contain DEFAULT_LNG_CLI
        defaultClientShouldNotBeFound("lngCli.doesNotContain=" + DEFAULT_LNG_CLI);

        // Get all the clientList where lngCli does not contain UPDATED_LNG_CLI
        defaultClientShouldBeFound("lngCli.doesNotContain=" + UPDATED_LNG_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySocCliIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where socCli equals to DEFAULT_SOC_CLI
        defaultClientShouldBeFound("socCli.equals=" + DEFAULT_SOC_CLI);

        // Get all the clientList where socCli equals to UPDATED_SOC_CLI
        defaultClientShouldNotBeFound("socCli.equals=" + UPDATED_SOC_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySocCliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where socCli not equals to DEFAULT_SOC_CLI
        defaultClientShouldNotBeFound("socCli.notEquals=" + DEFAULT_SOC_CLI);

        // Get all the clientList where socCli not equals to UPDATED_SOC_CLI
        defaultClientShouldBeFound("socCli.notEquals=" + UPDATED_SOC_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySocCliIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where socCli in DEFAULT_SOC_CLI or UPDATED_SOC_CLI
        defaultClientShouldBeFound("socCli.in=" + DEFAULT_SOC_CLI + "," + UPDATED_SOC_CLI);

        // Get all the clientList where socCli equals to UPDATED_SOC_CLI
        defaultClientShouldNotBeFound("socCli.in=" + UPDATED_SOC_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySocCliIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where socCli is not null
        defaultClientShouldBeFound("socCli.specified=true");

        // Get all the clientList where socCli is null
        defaultClientShouldNotBeFound("socCli.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsBySocCliContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where socCli contains DEFAULT_SOC_CLI
        defaultClientShouldBeFound("socCli.contains=" + DEFAULT_SOC_CLI);

        // Get all the clientList where socCli contains UPDATED_SOC_CLI
        defaultClientShouldNotBeFound("socCli.contains=" + UPDATED_SOC_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsBySocCliNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where socCli does not contain DEFAULT_SOC_CLI
        defaultClientShouldNotBeFound("socCli.doesNotContain=" + DEFAULT_SOC_CLI);

        // Get all the clientList where socCli does not contain UPDATED_SOC_CLI
        defaultClientShouldBeFound("socCli.doesNotContain=" + UPDATED_SOC_CLI);
    }

    @Test
    @Transactional
    public void getAllClientsByEmploiIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where emploi equals to DEFAULT_EMPLOI
        defaultClientShouldBeFound("emploi.equals=" + DEFAULT_EMPLOI);

        // Get all the clientList where emploi equals to UPDATED_EMPLOI
        defaultClientShouldNotBeFound("emploi.equals=" + UPDATED_EMPLOI);
    }

    @Test
    @Transactional
    public void getAllClientsByEmploiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where emploi not equals to DEFAULT_EMPLOI
        defaultClientShouldNotBeFound("emploi.notEquals=" + DEFAULT_EMPLOI);

        // Get all the clientList where emploi not equals to UPDATED_EMPLOI
        defaultClientShouldBeFound("emploi.notEquals=" + UPDATED_EMPLOI);
    }

    @Test
    @Transactional
    public void getAllClientsByEmploiIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where emploi in DEFAULT_EMPLOI or UPDATED_EMPLOI
        defaultClientShouldBeFound("emploi.in=" + DEFAULT_EMPLOI + "," + UPDATED_EMPLOI);

        // Get all the clientList where emploi equals to UPDATED_EMPLOI
        defaultClientShouldNotBeFound("emploi.in=" + UPDATED_EMPLOI);
    }

    @Test
    @Transactional
    public void getAllClientsByEmploiIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where emploi is not null
        defaultClientShouldBeFound("emploi.specified=true");

        // Get all the clientList where emploi is null
        defaultClientShouldNotBeFound("emploi.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByEmploiContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where emploi contains DEFAULT_EMPLOI
        defaultClientShouldBeFound("emploi.contains=" + DEFAULT_EMPLOI);

        // Get all the clientList where emploi contains UPDATED_EMPLOI
        defaultClientShouldNotBeFound("emploi.contains=" + UPDATED_EMPLOI);
    }

    @Test
    @Transactional
    public void getAllClientsByEmploiNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where emploi does not contain DEFAULT_EMPLOI
        defaultClientShouldNotBeFound("emploi.doesNotContain=" + DEFAULT_EMPLOI);

        // Get all the clientList where emploi does not contain UPDATED_EMPLOI
        defaultClientShouldBeFound("emploi.doesNotContain=" + UPDATED_EMPLOI);
    }

    @Test
    @Transactional
    public void getAllClientsBySexeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sexe equals to DEFAULT_SEXE
        defaultClientShouldBeFound("sexe.equals=" + DEFAULT_SEXE);

        // Get all the clientList where sexe equals to UPDATED_SEXE
        defaultClientShouldNotBeFound("sexe.equals=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllClientsBySexeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sexe not equals to DEFAULT_SEXE
        defaultClientShouldNotBeFound("sexe.notEquals=" + DEFAULT_SEXE);

        // Get all the clientList where sexe not equals to UPDATED_SEXE
        defaultClientShouldBeFound("sexe.notEquals=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllClientsBySexeIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sexe in DEFAULT_SEXE or UPDATED_SEXE
        defaultClientShouldBeFound("sexe.in=" + DEFAULT_SEXE + "," + UPDATED_SEXE);

        // Get all the clientList where sexe equals to UPDATED_SEXE
        defaultClientShouldNotBeFound("sexe.in=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllClientsBySexeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where sexe is not null
        defaultClientShouldBeFound("sexe.specified=true");

        // Get all the clientList where sexe is null
        defaultClientShouldNotBeFound("sexe.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByNumCniIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where numCni equals to DEFAULT_NUM_CNI
        defaultClientShouldBeFound("numCni.equals=" + DEFAULT_NUM_CNI);

        // Get all the clientList where numCni equals to UPDATED_NUM_CNI
        defaultClientShouldNotBeFound("numCni.equals=" + UPDATED_NUM_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByNumCniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where numCni not equals to DEFAULT_NUM_CNI
        defaultClientShouldNotBeFound("numCni.notEquals=" + DEFAULT_NUM_CNI);

        // Get all the clientList where numCni not equals to UPDATED_NUM_CNI
        defaultClientShouldBeFound("numCni.notEquals=" + UPDATED_NUM_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByNumCniIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where numCni in DEFAULT_NUM_CNI or UPDATED_NUM_CNI
        defaultClientShouldBeFound("numCni.in=" + DEFAULT_NUM_CNI + "," + UPDATED_NUM_CNI);

        // Get all the clientList where numCni equals to UPDATED_NUM_CNI
        defaultClientShouldNotBeFound("numCni.in=" + UPDATED_NUM_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByNumCniIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where numCni is not null
        defaultClientShouldBeFound("numCni.specified=true");

        // Get all the clientList where numCni is null
        defaultClientShouldNotBeFound("numCni.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByNumCniContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where numCni contains DEFAULT_NUM_CNI
        defaultClientShouldBeFound("numCni.contains=" + DEFAULT_NUM_CNI);

        // Get all the clientList where numCni contains UPDATED_NUM_CNI
        defaultClientShouldNotBeFound("numCni.contains=" + UPDATED_NUM_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByNumCniNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where numCni does not contain DEFAULT_NUM_CNI
        defaultClientShouldNotBeFound("numCni.doesNotContain=" + DEFAULT_NUM_CNI);

        // Get all the clientList where numCni does not contain UPDATED_NUM_CNI
        defaultClientShouldBeFound("numCni.doesNotContain=" + UPDATED_NUM_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatCniIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datCni equals to DEFAULT_DAT_CNI
        defaultClientShouldBeFound("datCni.equals=" + DEFAULT_DAT_CNI);

        // Get all the clientList where datCni equals to UPDATED_DAT_CNI
        defaultClientShouldNotBeFound("datCni.equals=" + UPDATED_DAT_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatCniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datCni not equals to DEFAULT_DAT_CNI
        defaultClientShouldNotBeFound("datCni.notEquals=" + DEFAULT_DAT_CNI);

        // Get all the clientList where datCni not equals to UPDATED_DAT_CNI
        defaultClientShouldBeFound("datCni.notEquals=" + UPDATED_DAT_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatCniIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datCni in DEFAULT_DAT_CNI or UPDATED_DAT_CNI
        defaultClientShouldBeFound("datCni.in=" + DEFAULT_DAT_CNI + "," + UPDATED_DAT_CNI);

        // Get all the clientList where datCni equals to UPDATED_DAT_CNI
        defaultClientShouldNotBeFound("datCni.in=" + UPDATED_DAT_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatCniIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datCni is not null
        defaultClientShouldBeFound("datCni.specified=true");

        // Get all the clientList where datCni is null
        defaultClientShouldNotBeFound("datCni.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByDatCniContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datCni contains DEFAULT_DAT_CNI
        defaultClientShouldBeFound("datCni.contains=" + DEFAULT_DAT_CNI);

        // Get all the clientList where datCni contains UPDATED_DAT_CNI
        defaultClientShouldNotBeFound("datCni.contains=" + UPDATED_DAT_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByDatCniNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where datCni does not contain DEFAULT_DAT_CNI
        defaultClientShouldNotBeFound("datCni.doesNotContain=" + DEFAULT_DAT_CNI);

        // Get all the clientList where datCni does not contain UPDATED_DAT_CNI
        defaultClientShouldBeFound("datCni.doesNotContain=" + UPDATED_DAT_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByFinCniIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where finCni equals to DEFAULT_FIN_CNI
        defaultClientShouldBeFound("finCni.equals=" + DEFAULT_FIN_CNI);

        // Get all the clientList where finCni equals to UPDATED_FIN_CNI
        defaultClientShouldNotBeFound("finCni.equals=" + UPDATED_FIN_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByFinCniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where finCni not equals to DEFAULT_FIN_CNI
        defaultClientShouldNotBeFound("finCni.notEquals=" + DEFAULT_FIN_CNI);

        // Get all the clientList where finCni not equals to UPDATED_FIN_CNI
        defaultClientShouldBeFound("finCni.notEquals=" + UPDATED_FIN_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByFinCniIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where finCni in DEFAULT_FIN_CNI or UPDATED_FIN_CNI
        defaultClientShouldBeFound("finCni.in=" + DEFAULT_FIN_CNI + "," + UPDATED_FIN_CNI);

        // Get all the clientList where finCni equals to UPDATED_FIN_CNI
        defaultClientShouldNotBeFound("finCni.in=" + UPDATED_FIN_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByFinCniIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where finCni is not null
        defaultClientShouldBeFound("finCni.specified=true");

        // Get all the clientList where finCni is null
        defaultClientShouldNotBeFound("finCni.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByFinCniContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where finCni contains DEFAULT_FIN_CNI
        defaultClientShouldBeFound("finCni.contains=" + DEFAULT_FIN_CNI);

        // Get all the clientList where finCni contains UPDATED_FIN_CNI
        defaultClientShouldNotBeFound("finCni.contains=" + UPDATED_FIN_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByFinCniNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where finCni does not contain DEFAULT_FIN_CNI
        defaultClientShouldNotBeFound("finCni.doesNotContain=" + DEFAULT_FIN_CNI);

        // Get all the clientList where finCni does not contain UPDATED_FIN_CNI
        defaultClientShouldBeFound("finCni.doesNotContain=" + UPDATED_FIN_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuCniIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuCni equals to DEFAULT_LIEU_CNI
        defaultClientShouldBeFound("lieuCni.equals=" + DEFAULT_LIEU_CNI);

        // Get all the clientList where lieuCni equals to UPDATED_LIEU_CNI
        defaultClientShouldNotBeFound("lieuCni.equals=" + UPDATED_LIEU_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuCniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuCni not equals to DEFAULT_LIEU_CNI
        defaultClientShouldNotBeFound("lieuCni.notEquals=" + DEFAULT_LIEU_CNI);

        // Get all the clientList where lieuCni not equals to UPDATED_LIEU_CNI
        defaultClientShouldBeFound("lieuCni.notEquals=" + UPDATED_LIEU_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuCniIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuCni in DEFAULT_LIEU_CNI or UPDATED_LIEU_CNI
        defaultClientShouldBeFound("lieuCni.in=" + DEFAULT_LIEU_CNI + "," + UPDATED_LIEU_CNI);

        // Get all the clientList where lieuCni equals to UPDATED_LIEU_CNI
        defaultClientShouldNotBeFound("lieuCni.in=" + UPDATED_LIEU_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuCniIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuCni is not null
        defaultClientShouldBeFound("lieuCni.specified=true");

        // Get all the clientList where lieuCni is null
        defaultClientShouldNotBeFound("lieuCni.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByLieuCniContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuCni contains DEFAULT_LIEU_CNI
        defaultClientShouldBeFound("lieuCni.contains=" + DEFAULT_LIEU_CNI);

        // Get all the clientList where lieuCni contains UPDATED_LIEU_CNI
        defaultClientShouldNotBeFound("lieuCni.contains=" + UPDATED_LIEU_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByLieuCniNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where lieuCni does not contain DEFAULT_LIEU_CNI
        defaultClientShouldNotBeFound("lieuCni.doesNotContain=" + DEFAULT_LIEU_CNI);

        // Get all the clientList where lieuCni does not contain UPDATED_LIEU_CNI
        defaultClientShouldBeFound("lieuCni.doesNotContain=" + UPDATED_LIEU_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByAutoCniIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where autoCni equals to DEFAULT_AUTO_CNI
        defaultClientShouldBeFound("autoCni.equals=" + DEFAULT_AUTO_CNI);

        // Get all the clientList where autoCni equals to UPDATED_AUTO_CNI
        defaultClientShouldNotBeFound("autoCni.equals=" + UPDATED_AUTO_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByAutoCniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where autoCni not equals to DEFAULT_AUTO_CNI
        defaultClientShouldNotBeFound("autoCni.notEquals=" + DEFAULT_AUTO_CNI);

        // Get all the clientList where autoCni not equals to UPDATED_AUTO_CNI
        defaultClientShouldBeFound("autoCni.notEquals=" + UPDATED_AUTO_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByAutoCniIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where autoCni in DEFAULT_AUTO_CNI or UPDATED_AUTO_CNI
        defaultClientShouldBeFound("autoCni.in=" + DEFAULT_AUTO_CNI + "," + UPDATED_AUTO_CNI);

        // Get all the clientList where autoCni equals to UPDATED_AUTO_CNI
        defaultClientShouldNotBeFound("autoCni.in=" + UPDATED_AUTO_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByAutoCniIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where autoCni is not null
        defaultClientShouldBeFound("autoCni.specified=true");

        // Get all the clientList where autoCni is null
        defaultClientShouldNotBeFound("autoCni.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByAutoCniContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where autoCni contains DEFAULT_AUTO_CNI
        defaultClientShouldBeFound("autoCni.contains=" + DEFAULT_AUTO_CNI);

        // Get all the clientList where autoCni contains UPDATED_AUTO_CNI
        defaultClientShouldNotBeFound("autoCni.contains=" + UPDATED_AUTO_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByAutoCniNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where autoCni does not contain DEFAULT_AUTO_CNI
        defaultClientShouldNotBeFound("autoCni.doesNotContain=" + DEFAULT_AUTO_CNI);

        // Get all the clientList where autoCni does not contain UPDATED_AUTO_CNI
        defaultClientShouldBeFound("autoCni.doesNotContain=" + UPDATED_AUTO_CNI);
    }

    @Test
    @Transactional
    public void getAllClientsByAdrIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adr equals to DEFAULT_ADR
        defaultClientShouldBeFound("adr.equals=" + DEFAULT_ADR);

        // Get all the clientList where adr equals to UPDATED_ADR
        defaultClientShouldNotBeFound("adr.equals=" + UPDATED_ADR);
    }

    @Test
    @Transactional
    public void getAllClientsByAdrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adr not equals to DEFAULT_ADR
        defaultClientShouldNotBeFound("adr.notEquals=" + DEFAULT_ADR);

        // Get all the clientList where adr not equals to UPDATED_ADR
        defaultClientShouldBeFound("adr.notEquals=" + UPDATED_ADR);
    }

    @Test
    @Transactional
    public void getAllClientsByAdrIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adr in DEFAULT_ADR or UPDATED_ADR
        defaultClientShouldBeFound("adr.in=" + DEFAULT_ADR + "," + UPDATED_ADR);

        // Get all the clientList where adr equals to UPDATED_ADR
        defaultClientShouldNotBeFound("adr.in=" + UPDATED_ADR);
    }

    @Test
    @Transactional
    public void getAllClientsByAdrIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adr is not null
        defaultClientShouldBeFound("adr.specified=true");

        // Get all the clientList where adr is null
        defaultClientShouldNotBeFound("adr.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByAdrContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adr contains DEFAULT_ADR
        defaultClientShouldBeFound("adr.contains=" + DEFAULT_ADR);

        // Get all the clientList where adr contains UPDATED_ADR
        defaultClientShouldNotBeFound("adr.contains=" + UPDATED_ADR);
    }

    @Test
    @Transactional
    public void getAllClientsByAdrNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where adr does not contain DEFAULT_ADR
        defaultClientShouldNotBeFound("adr.doesNotContain=" + DEFAULT_ADR);

        // Get all the clientList where adr does not contain UPDATED_ADR
        defaultClientShouldBeFound("adr.doesNotContain=" + UPDATED_ADR);
    }

    @Test
    @Transactional
    public void getAllClientsByTelIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where tel equals to DEFAULT_TEL
        defaultClientShouldBeFound("tel.equals=" + DEFAULT_TEL);

        // Get all the clientList where tel equals to UPDATED_TEL
        defaultClientShouldNotBeFound("tel.equals=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllClientsByTelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where tel not equals to DEFAULT_TEL
        defaultClientShouldNotBeFound("tel.notEquals=" + DEFAULT_TEL);

        // Get all the clientList where tel not equals to UPDATED_TEL
        defaultClientShouldBeFound("tel.notEquals=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllClientsByTelIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where tel in DEFAULT_TEL or UPDATED_TEL
        defaultClientShouldBeFound("tel.in=" + DEFAULT_TEL + "," + UPDATED_TEL);

        // Get all the clientList where tel equals to UPDATED_TEL
        defaultClientShouldNotBeFound("tel.in=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllClientsByTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where tel is not null
        defaultClientShouldBeFound("tel.specified=true");

        // Get all the clientList where tel is null
        defaultClientShouldNotBeFound("tel.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByTelContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where tel contains DEFAULT_TEL
        defaultClientShouldBeFound("tel.contains=" + DEFAULT_TEL);

        // Get all the clientList where tel contains UPDATED_TEL
        defaultClientShouldNotBeFound("tel.contains=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllClientsByTelNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where tel does not contain DEFAULT_TEL
        defaultClientShouldNotBeFound("tel.doesNotContain=" + DEFAULT_TEL);

        // Get all the clientList where tel does not contain UPDATED_TEL
        defaultClientShouldBeFound("tel.doesNotContain=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllClientsByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where ville equals to DEFAULT_VILLE
        defaultClientShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the clientList where ville equals to UPDATED_VILLE
        defaultClientShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllClientsByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where ville not equals to DEFAULT_VILLE
        defaultClientShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the clientList where ville not equals to UPDATED_VILLE
        defaultClientShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllClientsByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultClientShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the clientList where ville equals to UPDATED_VILLE
        defaultClientShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllClientsByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where ville is not null
        defaultClientShouldBeFound("ville.specified=true");

        // Get all the clientList where ville is null
        defaultClientShouldNotBeFound("ville.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByVilleContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where ville contains DEFAULT_VILLE
        defaultClientShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the clientList where ville contains UPDATED_VILLE
        defaultClientShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllClientsByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where ville does not contain DEFAULT_VILLE
        defaultClientShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the clientList where ville does not contain UPDATED_VILLE
        defaultClientShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllClientsBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where site equals to DEFAULT_SITE
        defaultClientShouldBeFound("site.equals=" + DEFAULT_SITE);

        // Get all the clientList where site equals to UPDATED_SITE
        defaultClientShouldNotBeFound("site.equals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllClientsBySiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where site not equals to DEFAULT_SITE
        defaultClientShouldNotBeFound("site.notEquals=" + DEFAULT_SITE);

        // Get all the clientList where site not equals to UPDATED_SITE
        defaultClientShouldBeFound("site.notEquals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllClientsBySiteIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where site in DEFAULT_SITE or UPDATED_SITE
        defaultClientShouldBeFound("site.in=" + DEFAULT_SITE + "," + UPDATED_SITE);

        // Get all the clientList where site equals to UPDATED_SITE
        defaultClientShouldNotBeFound("site.in=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllClientsBySiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where site is not null
        defaultClientShouldBeFound("site.specified=true");

        // Get all the clientList where site is null
        defaultClientShouldNotBeFound("site.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsBySiteContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where site contains DEFAULT_SITE
        defaultClientShouldBeFound("site.contains=" + DEFAULT_SITE);

        // Get all the clientList where site contains UPDATED_SITE
        defaultClientShouldNotBeFound("site.contains=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllClientsBySiteNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where site does not contain DEFAULT_SITE
        defaultClientShouldNotBeFound("site.doesNotContain=" + DEFAULT_SITE);

        // Get all the clientList where site does not contain UPDATED_SITE
        defaultClientShouldBeFound("site.doesNotContain=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllClientsByLocIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where loc equals to DEFAULT_LOC
        defaultClientShouldBeFound("loc.equals=" + DEFAULT_LOC);

        // Get all the clientList where loc equals to UPDATED_LOC
        defaultClientShouldNotBeFound("loc.equals=" + UPDATED_LOC);
    }

    @Test
    @Transactional
    public void getAllClientsByLocIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where loc not equals to DEFAULT_LOC
        defaultClientShouldNotBeFound("loc.notEquals=" + DEFAULT_LOC);

        // Get all the clientList where loc not equals to UPDATED_LOC
        defaultClientShouldBeFound("loc.notEquals=" + UPDATED_LOC);
    }

    @Test
    @Transactional
    public void getAllClientsByLocIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where loc in DEFAULT_LOC or UPDATED_LOC
        defaultClientShouldBeFound("loc.in=" + DEFAULT_LOC + "," + UPDATED_LOC);

        // Get all the clientList where loc equals to UPDATED_LOC
        defaultClientShouldNotBeFound("loc.in=" + UPDATED_LOC);
    }

    @Test
    @Transactional
    public void getAllClientsByLocIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where loc is not null
        defaultClientShouldBeFound("loc.specified=true");

        // Get all the clientList where loc is null
        defaultClientShouldNotBeFound("loc.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByLocContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where loc contains DEFAULT_LOC
        defaultClientShouldBeFound("loc.contains=" + DEFAULT_LOC);

        // Get all the clientList where loc contains UPDATED_LOC
        defaultClientShouldNotBeFound("loc.contains=" + UPDATED_LOC);
    }

    @Test
    @Transactional
    public void getAllClientsByLocNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where loc does not contain DEFAULT_LOC
        defaultClientShouldNotBeFound("loc.doesNotContain=" + DEFAULT_LOC);

        // Get all the clientList where loc does not contain UPDATED_LOC
        defaultClientShouldBeFound("loc.doesNotContain=" + UPDATED_LOC);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax equals to DEFAULT_FAX
        defaultClientShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the clientList where fax equals to UPDATED_FAX
        defaultClientShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax not equals to DEFAULT_FAX
        defaultClientShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the clientList where fax not equals to UPDATED_FAX
        defaultClientShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultClientShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the clientList where fax equals to UPDATED_FAX
        defaultClientShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax is not null
        defaultClientShouldBeFound("fax.specified=true");

        // Get all the clientList where fax is null
        defaultClientShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByFaxContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax contains DEFAULT_FAX
        defaultClientShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the clientList where fax contains UPDATED_FAX
        defaultClientShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax does not contain DEFAULT_FAX
        defaultClientShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the clientList where fax does not contain UPDATED_FAX
        defaultClientShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByAgnceIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where agnce equals to DEFAULT_AGNCE
        defaultClientShouldBeFound("agnce.equals=" + DEFAULT_AGNCE);

        // Get all the clientList where agnce equals to UPDATED_AGNCE
        defaultClientShouldNotBeFound("agnce.equals=" + UPDATED_AGNCE);
    }

    @Test
    @Transactional
    public void getAllClientsByAgnceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where agnce not equals to DEFAULT_AGNCE
        defaultClientShouldNotBeFound("agnce.notEquals=" + DEFAULT_AGNCE);

        // Get all the clientList where agnce not equals to UPDATED_AGNCE
        defaultClientShouldBeFound("agnce.notEquals=" + UPDATED_AGNCE);
    }

    @Test
    @Transactional
    public void getAllClientsByAgnceIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where agnce in DEFAULT_AGNCE or UPDATED_AGNCE
        defaultClientShouldBeFound("agnce.in=" + DEFAULT_AGNCE + "," + UPDATED_AGNCE);

        // Get all the clientList where agnce equals to UPDATED_AGNCE
        defaultClientShouldNotBeFound("agnce.in=" + UPDATED_AGNCE);
    }

    @Test
    @Transactional
    public void getAllClientsByAgnceIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where agnce is not null
        defaultClientShouldBeFound("agnce.specified=true");

        // Get all the clientList where agnce is null
        defaultClientShouldNotBeFound("agnce.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByAgnceContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where agnce contains DEFAULT_AGNCE
        defaultClientShouldBeFound("agnce.contains=" + DEFAULT_AGNCE);

        // Get all the clientList where agnce contains UPDATED_AGNCE
        defaultClientShouldNotBeFound("agnce.contains=" + UPDATED_AGNCE);
    }

    @Test
    @Transactional
    public void getAllClientsByAgnceNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where agnce does not contain DEFAULT_AGNCE
        defaultClientShouldNotBeFound("agnce.doesNotContain=" + DEFAULT_AGNCE);

        // Get all the clientList where agnce does not contain UPDATED_AGNCE
        defaultClientShouldBeFound("agnce.doesNotContain=" + UPDATED_AGNCE);
    }

    @Test
    @Transactional
    public void getAllClientsByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mail equals to DEFAULT_MAIL
        defaultClientShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the clientList where mail equals to UPDATED_MAIL
        defaultClientShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mail not equals to DEFAULT_MAIL
        defaultClientShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the clientList where mail not equals to UPDATED_MAIL
        defaultClientShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByMailIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultClientShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the clientList where mail equals to UPDATED_MAIL
        defaultClientShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mail is not null
        defaultClientShouldBeFound("mail.specified=true");

        // Get all the clientList where mail is null
        defaultClientShouldNotBeFound("mail.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByMailContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mail contains DEFAULT_MAIL
        defaultClientShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the clientList where mail contains UPDATED_MAIL
        defaultClientShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByMailNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where mail does not contain DEFAULT_MAIL
        defaultClientShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the clientList where mail does not contain UPDATED_MAIL
        defaultClientShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllClientsByPaysIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pays equals to DEFAULT_PAYS
        defaultClientShouldBeFound("pays.equals=" + DEFAULT_PAYS);

        // Get all the clientList where pays equals to UPDATED_PAYS
        defaultClientShouldNotBeFound("pays.equals=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllClientsByPaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pays not equals to DEFAULT_PAYS
        defaultClientShouldNotBeFound("pays.notEquals=" + DEFAULT_PAYS);

        // Get all the clientList where pays not equals to UPDATED_PAYS
        defaultClientShouldBeFound("pays.notEquals=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllClientsByPaysIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pays in DEFAULT_PAYS or UPDATED_PAYS
        defaultClientShouldBeFound("pays.in=" + DEFAULT_PAYS + "," + UPDATED_PAYS);

        // Get all the clientList where pays equals to UPDATED_PAYS
        defaultClientShouldNotBeFound("pays.in=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllClientsByPaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pays is not null
        defaultClientShouldBeFound("pays.specified=true");

        // Get all the clientList where pays is null
        defaultClientShouldNotBeFound("pays.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientsByPaysContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pays contains DEFAULT_PAYS
        defaultClientShouldBeFound("pays.contains=" + DEFAULT_PAYS);

        // Get all the clientList where pays contains UPDATED_PAYS
        defaultClientShouldNotBeFound("pays.contains=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllClientsByPaysNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where pays does not contain DEFAULT_PAYS
        defaultClientShouldNotBeFound("pays.doesNotContain=" + DEFAULT_PAYS);

        // Get all the clientList where pays does not contain UPDATED_PAYS
        defaultClientShouldBeFound("pays.doesNotContain=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllClientsByCompteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        Compte compte = CompteResourceIT.createEntity(em);
        em.persist(compte);
        em.flush();
        client.addCompte(compte);
        clientRepository.saveAndFlush(client);
        Long compteId = compte.getId();

        // Get all the clientList where compte equals to compteId
        defaultClientShouldBeFound("compteId.equals=" + compteId);

        // Get all the clientList where compte equals to compteId + 1
        defaultClientShouldNotBeFound("compteId.equals=" + (compteId + 1));
    }

    @Test
    @Transactional
    public void getAllClientsByEngagementIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        Engagement engagement = EngagementResourceIT.createEntity(em);
        em.persist(engagement);
        em.flush();
        client.addEngagement(engagement);
        clientRepository.saveAndFlush(client);
        Long engagementId = engagement.getId();

        // Get all the clientList where engagement equals to engagementId
        defaultClientShouldBeFound("engagementId.equals=" + engagementId);

        // Get all the clientList where engagement equals to engagementId + 1
        defaultClientShouldNotBeFound("engagementId.equals=" + (engagementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientShouldBeFound(String filter) throws Exception {
        restClientMockMvc
            .perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].codBnk").value(hasItem(DEFAULT_COD_BNK)))
            .andExpect(jsonPath("$.[*].codCli").value(hasItem(DEFAULT_COD_CLI)))
            .andExpect(jsonPath("$.[*].nomCli").value(hasItem(DEFAULT_NOM_CLI)))
            .andExpect(jsonPath("$.[*].mendCli").value(hasItem(DEFAULT_MEND_CLI)))
            .andExpect(jsonPath("$.[*].sfCli").value(hasItem(DEFAULT_SF_CLI.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].datNai").value(hasItem(DEFAULT_DAT_NAI)))
            .andExpect(jsonPath("$.[*].lieuNai").value(hasItem(DEFAULT_LIEU_NAI)))
            .andExpect(jsonPath("$.[*].natCli").value(hasItem(DEFAULT_NAT_CLI)))
            .andExpect(jsonPath("$.[*].lngCli").value(hasItem(DEFAULT_LNG_CLI)))
            .andExpect(jsonPath("$.[*].socCli").value(hasItem(DEFAULT_SOC_CLI)))
            .andExpect(jsonPath("$.[*].emploi").value(hasItem(DEFAULT_EMPLOI)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].numCni").value(hasItem(DEFAULT_NUM_CNI)))
            .andExpect(jsonPath("$.[*].datCni").value(hasItem(DEFAULT_DAT_CNI)))
            .andExpect(jsonPath("$.[*].finCni").value(hasItem(DEFAULT_FIN_CNI)))
            .andExpect(jsonPath("$.[*].lieuCni").value(hasItem(DEFAULT_LIEU_CNI)))
            .andExpect(jsonPath("$.[*].autoCni").value(hasItem(DEFAULT_AUTO_CNI)))
            .andExpect(jsonPath("$.[*].adr").value(hasItem(DEFAULT_ADR)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].loc").value(hasItem(DEFAULT_LOC)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].agnce").value(hasItem(DEFAULT_AGNCE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)));

        // Check, that the count call also returns 1
        restClientMockMvc
            .perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientShouldNotBeFound(String filter) throws Exception {
        restClientMockMvc
            .perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientMockMvc
            .perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .codBnk(UPDATED_COD_BNK)
            .codCli(UPDATED_COD_CLI)
            .nomCli(UPDATED_NOM_CLI)
            .mendCli(UPDATED_MEND_CLI)
            .sfCli(UPDATED_SF_CLI)
            .titre(UPDATED_TITRE)
            .datNai(UPDATED_DAT_NAI)
            .lieuNai(UPDATED_LIEU_NAI)
            .natCli(UPDATED_NAT_CLI)
            .lngCli(UPDATED_LNG_CLI)
            .socCli(UPDATED_SOC_CLI)
            .emploi(UPDATED_EMPLOI)
            .sexe(UPDATED_SEXE)
            .numCni(UPDATED_NUM_CNI)
            .datCni(UPDATED_DAT_CNI)
            .finCni(UPDATED_FIN_CNI)
            .lieuCni(UPDATED_LIEU_CNI)
            .autoCni(UPDATED_AUTO_CNI)
            .adr(UPDATED_ADR)
            .tel(UPDATED_TEL)
            .ville(UPDATED_VILLE)
            .site(UPDATED_SITE)
            .loc(UPDATED_LOC)
            .fax(UPDATED_FAX)
            .agnce(UPDATED_AGNCE)
            .mail(UPDATED_MAIL)
            .pays(UPDATED_PAYS);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc
            .perform(put("/api/clients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getCodBnk()).isEqualTo(UPDATED_COD_BNK);
        assertThat(testClient.getCodCli()).isEqualTo(UPDATED_COD_CLI);
        assertThat(testClient.getNomCli()).isEqualTo(UPDATED_NOM_CLI);
        assertThat(testClient.getMendCli()).isEqualTo(UPDATED_MEND_CLI);
        assertThat(testClient.getSfCli()).isEqualTo(UPDATED_SF_CLI);
        assertThat(testClient.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testClient.getDatNai()).isEqualTo(UPDATED_DAT_NAI);
        assertThat(testClient.getLieuNai()).isEqualTo(UPDATED_LIEU_NAI);
        assertThat(testClient.getNatCli()).isEqualTo(UPDATED_NAT_CLI);
        assertThat(testClient.getLngCli()).isEqualTo(UPDATED_LNG_CLI);
        assertThat(testClient.getSocCli()).isEqualTo(UPDATED_SOC_CLI);
        assertThat(testClient.getEmploi()).isEqualTo(UPDATED_EMPLOI);
        assertThat(testClient.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testClient.getNumCni()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testClient.getDatCni()).isEqualTo(UPDATED_DAT_CNI);
        assertThat(testClient.getFinCni()).isEqualTo(UPDATED_FIN_CNI);
        assertThat(testClient.getLieuCni()).isEqualTo(UPDATED_LIEU_CNI);
        assertThat(testClient.getAutoCni()).isEqualTo(UPDATED_AUTO_CNI);
        assertThat(testClient.getAdr()).isEqualTo(UPDATED_ADR);
        assertThat(testClient.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testClient.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testClient.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testClient.getLoc()).isEqualTo(UPDATED_LOC);
        assertThat(testClient.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testClient.getAgnce()).isEqualTo(UPDATED_AGNCE);
        assertThat(testClient.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testClient.getPays()).isEqualTo(UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put("/api/clients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete("/api/clients/{id}", client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
