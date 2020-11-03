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
import tect.her.ccm.domain.enumeration.MaritalStatus;
import tect.her.ccm.domain.enumeration.Sex;
import tect.her.ccm.repository.ClientRepository;
import tect.her.ccm.service.ClientService;
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
