package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.TaskLog;
import tect.her.ccm.repository.TaskLogRepository;
import tect.her.ccm.service.dto.TaskLogDTO;
import tect.her.ccm.service.mapper.TaskLogMapper;

/**
 * Service Implementation for managing {@link TaskLog}.
 */
@Service
@Transactional
public class TaskLogService {
    private final Logger log = LoggerFactory.getLogger(TaskLogService.class);

    private final TaskLogRepository taskLogRepository;

    private final TaskLogMapper taskLogMapper;

    public TaskLogService(TaskLogRepository taskLogRepository, TaskLogMapper taskLogMapper) {
        this.taskLogRepository = taskLogRepository;
        this.taskLogMapper = taskLogMapper;
    }

    /**
     * Save a taskLog.
     *
     * @param taskLogDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskLogDTO save(TaskLogDTO taskLogDTO) {
        log.debug("Request to save TaskLog : {}", taskLogDTO);
        TaskLog taskLog = taskLogMapper.toEntity(taskLogDTO);
        taskLog = taskLogRepository.save(taskLog);
        return taskLogMapper.toDto(taskLog);
    }

    /**
     * Get all the taskLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskLogs");
        return taskLogRepository.findAll(pageable).map(taskLogMapper::toDto);
    }

    /**
     * Get one taskLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskLogDTO> findOne(Long id) {
        log.debug("Request to get TaskLog : {}", id);
        return taskLogRepository.findById(id).map(taskLogMapper::toDto);
    }

    /**
     * Delete the taskLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskLog : {}", id);
        taskLogRepository.deleteById(id);
    }
}
