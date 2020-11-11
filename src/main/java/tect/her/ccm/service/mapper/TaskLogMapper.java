package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.TaskLogDTO;

/**
 * Mapper for the entity {@link TaskLog} and its DTO {@link TaskLogDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, ActionMapper.class, TaskMapper.class, EngagementMapper.class })
public interface TaskLogMapper extends EntityMapper<TaskLogDTO, TaskLog> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "action.id", target = "actionId")
    @Mapping(source = "action.label", target = "actionLabel")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "engagement.subject", target = "engagementSubject")
    TaskLogDTO toDto(TaskLog taskLog);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "actionId", target = "action")
    @Mapping(source = "taskId", target = "task")
    @Mapping(source = "engagementId", target = "engagement")
    TaskLog toEntity(TaskLogDTO taskLogDTO);

    default TaskLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskLog taskLog = new TaskLog();
        taskLog.setId(id);
        return taskLog;
    }
}
