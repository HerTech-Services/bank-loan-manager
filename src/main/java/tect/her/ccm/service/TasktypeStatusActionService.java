package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.TasktypeStatusAction;
import tect.her.ccm.repository.TasktypeStatusActionRepository;
import tect.her.ccm.service.dto.TasktypeStatusActionDTO;
import tect.her.ccm.service.mapper.TasktypeStatusActionMapper;

/**
 * Service Implementation for managing {@link TasktypeStatusAction}.
 */
@Service
@Transactional
public class TasktypeStatusActionService {
    private final Logger log = LoggerFactory.getLogger(TasktypeStatusActionService.class);

    private final TasktypeStatusActionRepository tasktypeStatusActionRepository;

    private final TasktypeStatusActionMapper tasktypeStatusActionMapper;

    public TasktypeStatusActionService(
        TasktypeStatusActionRepository tasktypeStatusActionRepository,
        TasktypeStatusActionMapper tasktypeStatusActionMapper
    ) {
        this.tasktypeStatusActionRepository = tasktypeStatusActionRepository;
        this.tasktypeStatusActionMapper = tasktypeStatusActionMapper;
    }

    /**
     * Save a tasktypeStatusAction.
     *
     * @param tasktypeStatusActionDTO the entity to save.
     * @return the persisted entity.
     */
    public TasktypeStatusActionDTO save(TasktypeStatusActionDTO tasktypeStatusActionDTO) {
        log.debug("Request to save TasktypeStatusAction : {}", tasktypeStatusActionDTO);
        TasktypeStatusAction tasktypeStatusAction = tasktypeStatusActionMapper.toEntity(tasktypeStatusActionDTO);
        tasktypeStatusAction = tasktypeStatusActionRepository.save(tasktypeStatusAction);
        return tasktypeStatusActionMapper.toDto(tasktypeStatusAction);
    }

    /**
     * Get all the tasktypeStatusActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TasktypeStatusActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TasktypeStatusActions");
        return tasktypeStatusActionRepository.findAll(pageable).map(tasktypeStatusActionMapper::toDto);
    }

    /**
     * Get one tasktypeStatusAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TasktypeStatusActionDTO> findOne(Long id) {
        log.debug("Request to get TasktypeStatusAction : {}", id);
        return tasktypeStatusActionRepository.findById(id).map(tasktypeStatusActionMapper::toDto);
    }

    /**
     * Delete the tasktypeStatusAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TasktypeStatusAction : {}", id);
        tasktypeStatusActionRepository.deleteById(id);
    }
}
