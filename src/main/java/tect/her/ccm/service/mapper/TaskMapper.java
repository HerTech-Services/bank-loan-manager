package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.TaskDTO;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaskTypeMapper.class, UserMapper.class, PosteMapper.class, EngagementMapper.class })
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.label", target = "typeLabel")
    @Mapping(source = "processUser.id", target = "processUserId")
    @Mapping(source = "processUser.firstName", target = "processUserFirstName")
    @Mapping(source = "poste.id", target = "posteId")
    @Mapping(source = "poste.label", target = "posteLabel")
    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "engagement.subject", target = "engagementSubject")
    TaskDTO toDto(Task task);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "processUserId", target = "processUser")
    @Mapping(source = "posteId", target = "poste")
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
