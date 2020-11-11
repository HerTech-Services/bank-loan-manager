package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.TasktypeStatusActionDTO;

/**
 * Mapper for the entity {@link TasktypeStatusAction} and its DTO {@link TasktypeStatusActionDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaskTypeMapper.class, ActionMapper.class, StatusMapper.class })
public interface TasktypeStatusActionMapper extends EntityMapper<TasktypeStatusActionDTO, TasktypeStatusAction> {
    @Mapping(source = "tasktype.id", target = "tasktypeId")
    @Mapping(source = "tasktype.label", target = "tasktypeLabel")
    @Mapping(source = "action.id", target = "actionId")
    @Mapping(source = "action.label", target = "actionLabel")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")
    TasktypeStatusActionDTO toDto(TasktypeStatusAction tasktypeStatusAction);

    @Mapping(source = "tasktypeId", target = "tasktype")
    @Mapping(source = "actionId", target = "action")
    @Mapping(source = "statusId", target = "status")
    TasktypeStatusAction toEntity(TasktypeStatusActionDTO tasktypeStatusActionDTO);

    default TasktypeStatusAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        TasktypeStatusAction tasktypeStatusAction = new TasktypeStatusAction();
        tasktypeStatusAction.setId(id);
        return tasktypeStatusAction;
    }
}
