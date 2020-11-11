package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.TaskType;
import tect.her.ccm.repository.TaskTypeRepository;
import tect.her.ccm.service.dto.TaskTypeDTO;
import tect.her.ccm.service.mapper.TaskTypeMapper;

/**
 * Service Implementation for managing {@link TaskType}.
 */
@Service
@Transactional
public class TaskTypeService {
    private final Logger log = LoggerFactory.getLogger(TaskTypeService.class);

    private final TaskTypeRepository taskTypeRepository;

    private final TaskTypeMapper taskTypeMapper;

    public TaskTypeService(TaskTypeRepository taskTypeRepository, TaskTypeMapper taskTypeMapper) {
        this.taskTypeRepository = taskTypeRepository;
        this.taskTypeMapper = taskTypeMapper;
    }

    /**
     * Save a taskType.
     *
     * @param taskTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskTypeDTO save(TaskTypeDTO taskTypeDTO) {
        log.debug("Request to save TaskType : {}", taskTypeDTO);
        TaskType taskType = taskTypeMapper.toEntity(taskTypeDTO);
        taskType = taskTypeRepository.save(taskType);
        return taskTypeMapper.toDto(taskType);
    }

    /**
     * Get all the taskTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskTypes");
        return taskTypeRepository.findAll(pageable).map(taskTypeMapper::toDto);
    }

    /**
     * Get one taskType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskTypeDTO> findOne(Long id) {
        log.debug("Request to get TaskType : {}", id);
        return taskTypeRepository.findById(id).map(taskTypeMapper::toDto);
    }

    /**
     * Delete the taskType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskType : {}", id);
        taskTypeRepository.deleteById(id);
    }
}
