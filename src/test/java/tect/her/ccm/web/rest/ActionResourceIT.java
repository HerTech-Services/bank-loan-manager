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
import org.springframework.util.Base64Utils;
import tect.her.ccm.BankLoanManagerApp;
import tect.her.ccm.domain.Action;
import tect.her.ccm.repository.ActionRepository;
import tect.her.ccm.service.ActionService;
import tect.her.ccm.service.dto.ActionDTO;
import tect.her.ccm.service.mapper.ActionMapper;

/**
 * Integration tests for the {@link ActionResource} REST controller.
 */
@SpringBootTest(classes = BankLoanManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActionResourceIT {
    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_KEYWORD = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SYSTEM = false;
    private static final Boolean UPDATED_IS_SYSTEM = true;

    private static final String DEFAULT_ACTION_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_PAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HISTORY = false;
    private static final Boolean UPDATED_HISTORY = true;

    private static final String DEFAULT_COMPOSANT = "AAAAAAAAAA";
    private static final String UPDATED_COMPOSANT = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETERS = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETERS = "BBBBBBBBBB";

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private ActionService actionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionMockMvc;

    private Action action;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .label(DEFAULT_LABEL)
            .keyword(DEFAULT_KEYWORD)
            .isSystem(DEFAULT_IS_SYSTEM)
            .actionPage(DEFAULT_ACTION_PAGE)
            .history(DEFAULT_HISTORY)
            .composant(DEFAULT_COMPOSANT)
            .parameters(DEFAULT_PARAMETERS);
        return action;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createUpdatedEntity(EntityManager em) {
        Action action = new Action()
            .label(UPDATED_LABEL)
            .keyword(UPDATED_KEYWORD)
            .isSystem(UPDATED_IS_SYSTEM)
            .actionPage(UPDATED_ACTION_PAGE)
            .history(UPDATED_HISTORY)
            .composant(UPDATED_COMPOSANT)
            .parameters(UPDATED_PARAMETERS);
        return action;
    }

    @BeforeEach
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();
        // Create the Action
        ActionDTO actionDTO = actionMapper.toDto(action);
        restActionMockMvc
            .perform(post("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAction.getKeyword()).isEqualTo(DEFAULT_KEYWORD);
        assertThat(testAction.isIsSystem()).isEqualTo(DEFAULT_IS_SYSTEM);
        assertThat(testAction.getActionPage()).isEqualTo(DEFAULT_ACTION_PAGE);
        assertThat(testAction.isHistory()).isEqualTo(DEFAULT_HISTORY);
        assertThat(testAction.getComposant()).isEqualTo(DEFAULT_COMPOSANT);
        assertThat(testAction.getParameters()).isEqualTo(DEFAULT_PARAMETERS);
    }

    @Test
    @Transactional
    public void createActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action with an existing ID
        action.setId(1L);
        ActionDTO actionDTO = actionMapper.toDto(action);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc
            .perform(post("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setLabel(null);

        // Create the Action, which fails.
        ActionDTO actionDTO = actionMapper.toDto(action);

        restActionMockMvc
            .perform(post("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeywordIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setKeyword(null);

        // Create the Action, which fails.
        ActionDTO actionDTO = actionMapper.toDto(action);

        restActionMockMvc
            .perform(post("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSystemIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setIsSystem(null);

        // Create the Action, which fails.
        ActionDTO actionDTO = actionMapper.toDto(action);

        restActionMockMvc
            .perform(post("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc
            .perform(get("/api/actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].keyword").value(hasItem(DEFAULT_KEYWORD)))
            .andExpect(jsonPath("$.[*].isSystem").value(hasItem(DEFAULT_IS_SYSTEM.booleanValue())))
            .andExpect(jsonPath("$.[*].actionPage").value(hasItem(DEFAULT_ACTION_PAGE)))
            .andExpect(jsonPath("$.[*].history").value(hasItem(DEFAULT_HISTORY.booleanValue())))
            .andExpect(jsonPath("$.[*].composant").value(hasItem(DEFAULT_COMPOSANT)))
            .andExpect(jsonPath("$.[*].parameters").value(hasItem(DEFAULT_PARAMETERS.toString())));
    }

    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc
            .perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.keyword").value(DEFAULT_KEYWORD))
            .andExpect(jsonPath("$.isSystem").value(DEFAULT_IS_SYSTEM.booleanValue()))
            .andExpect(jsonPath("$.actionPage").value(DEFAULT_ACTION_PAGE))
            .andExpect(jsonPath("$.history").value(DEFAULT_HISTORY.booleanValue()))
            .andExpect(jsonPath("$.composant").value(DEFAULT_COMPOSANT))
            .andExpect(jsonPath("$.parameters").value(DEFAULT_PARAMETERS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .label(UPDATED_LABEL)
            .keyword(UPDATED_KEYWORD)
            .isSystem(UPDATED_IS_SYSTEM)
            .actionPage(UPDATED_ACTION_PAGE)
            .history(UPDATED_HISTORY)
            .composant(UPDATED_COMPOSANT)
            .parameters(UPDATED_PARAMETERS);
        ActionDTO actionDTO = actionMapper.toDto(updatedAction);

        restActionMockMvc
            .perform(put("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAction.getKeyword()).isEqualTo(UPDATED_KEYWORD);
        assertThat(testAction.isIsSystem()).isEqualTo(UPDATED_IS_SYSTEM);
        assertThat(testAction.getActionPage()).isEqualTo(UPDATED_ACTION_PAGE);
        assertThat(testAction.isHistory()).isEqualTo(UPDATED_HISTORY);
        assertThat(testAction.getComposant()).isEqualTo(UPDATED_COMPOSANT);
        assertThat(testAction.getParameters()).isEqualTo(UPDATED_PARAMETERS);
    }

    @Test
    @Transactional
    public void updateNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Create the Action
        ActionDTO actionDTO = actionMapper.toDto(action);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(put("/api/actions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Delete the action
        restActionMockMvc
            .perform(delete("/api/actions/{id}", action.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
