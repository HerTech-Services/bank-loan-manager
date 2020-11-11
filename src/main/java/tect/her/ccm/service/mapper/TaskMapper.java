package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.TaskDTO;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaskTypeMapper.class, UserMapper.class, EngagementMapper.class })
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.label", target = "typeLabel")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "assignee.login", target = "assigneeLogin")
    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "engagement.subject", target = "engagementSubject")
    TaskDTO toDto(Task task);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "assigneeId", target = "assignee")
    @Mapping(source = "engagementId", target = "engagement")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
