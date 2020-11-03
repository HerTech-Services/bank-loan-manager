package tect.her.ccm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import tect.her.ccm.domain.Parameters;
import tect.her.ccm.repository.ParametersRepository;
import tect.her.ccm.service.ParametersService;
import tect.her.ccm.service.dto.ParametersDTO;
import tect.her.ccm.service.mapper.ParametersMapper;

/**
 * Integration tests for the {@link ParametersResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParametersResourceIT {
    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE_STRING = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARAM_VALUE_INT = 1;
    private static final Integer UPDATED_PARAM_VALUE_INT = 2;

    private static final Instant DEFAULT_PARAM_VALUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PARAM_VALUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ParametersRepository parametersRepository;

    @Autowired
    private ParametersMapper parametersMapper;

    @Autowired
    private ParametersService parametersService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametersMockMvc;

    private Parameters parameters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createEntity(EntityManager em) {
        Parameters parameters = new Parameters()
            .identifier(DEFAULT_IDENTIFIER)
            .description(DEFAULT_DESCRIPTION)
            .paramValueString(DEFAULT_PARAM_VALUE_STRING)
            .paramValueInt(DEFAULT_PARAM_VALUE_INT)
            .paramValueDate(DEFAULT_PARAM_VALUE_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return parameters;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createUpdatedEntity(EntityManager em) {
        Parameters parameters = new Parameters()
            .identifier(UPDATED_IDENTIFIER)
            .description(UPDATED_DESCRIPTION)
            .paramValueString(UPDATED_PARAM_VALUE_STRING)
            .paramValueInt(UPDATED_PARAM_VALUE_INT)
            .paramValueDate(UPDATED_PARAM_VALUE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return parameters;
    }

    @BeforeEach
    public void initTest() {
        parameters = createEntity(em);
    }

    @Test
    @Transactional
    public void createParameters() throws Exception {
        int databaseSizeBeforeCreate = parametersRepository.findAll().size();
        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);
        restParametersMockMvc
            .perform(
                post("/api/parameters").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate + 1);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testParameters.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testParameters.getParamValueString()).isEqualTo(DEFAULT_PARAM_VALUE_STRING);
        assertThat(testParameters.getParamValueInt()).isEqualTo(DEFAULT_PARAM_VALUE_INT);
        assertThat(testParameters.getParamValueDate()).isEqualTo(DEFAULT_PARAM_VALUE_DATE);
        assertThat(testParameters.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createParametersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametersRepository.findAll().size();

        // Create the Parameters with an existing ID
        parameters.setId(1L);
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametersMockMvc
            .perform(
                post("/api/parameters").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametersRepository.findAll().size();
        // set the field null
        parameters.setIdentifier(null);

        // Create the Parameters, which fails.
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        restParametersMockMvc
            .perform(
                post("/api/parameters").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList
        restParametersMockMvc
            .perform(get("/api/parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].paramValueString").value(hasItem(DEFAULT_PARAM_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].paramValueInt").value(hasItem(DEFAULT_PARAM_VALUE_INT)))
            .andExpect(jsonPath("$.[*].paramValueDate").value(hasItem(DEFAULT_PARAM_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get the parameters
        restParametersMockMvc
            .perform(get("/api/parameters/{id}", parameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parameters.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.paramValueString").value(DEFAULT_PARAM_VALUE_STRING))
            .andExpect(jsonPath("$.paramValueInt").value(DEFAULT_PARAM_VALUE_INT))
            .andExpect(jsonPath("$.paramValueDate").value(DEFAULT_PARAM_VALUE_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParameters() throws Exception {
        // Get the parameters
        restParametersMockMvc.perform(get("/api/parameters/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters
        Parameters updatedParameters = parametersRepository.findById(parameters.getId()).get();
        // Disconnect from session so that the updates on updatedParameters are not directly saved in db
        em.detach(updatedParameters);
        updatedParameters
            .identifier(UPDATED_IDENTIFIER)
            .description(UPDATED_DESCRIPTION)
            .paramValueString(UPDATED_PARAM_VALUE_STRING)
            .paramValueInt(UPDATED_PARAM_VALUE_INT)
            .paramValueDate(UPDATED_PARAM_VALUE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        ParametersDTO parametersDTO = parametersMapper.toDto(updatedParameters);

        restParametersMockMvc
            .perform(
                put("/api/parameters").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testParameters.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testParameters.getParamValueString()).isEqualTo(UPDATED_PARAM_VALUE_STRING);
        assertThat(testParameters.getParamValueInt()).isEqualTo(UPDATED_PARAM_VALUE_INT);
        assertThat(testParameters.getParamValueDate()).isEqualTo(UPDATED_PARAM_VALUE_DATE);
        assertThat(testParameters.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                put("/api/parameters").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeDelete = parametersRepository.findAll().size();

        // Delete the parameters
        restParametersMockMvc
            .perform(delete("/api/parameters/{id}", parameters.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
