package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.TaskTypeDTO;

/**
 * Mapper for the entity {@link TaskType} and its DTO {@link TaskTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskTypeMapper extends EntityMapper<TaskTypeDTO, TaskType> {
    default TaskType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskType taskType = new TaskType();
        taskType.setId(id);
        return taskType;
    }
}
