package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.ActionDTO;

/**
 * Mapper for the entity {@link Action} and its DTO {@link ActionDTO}.
 */
@Mapper(componentModel = "spring", uses = { StatusMapper.class })
public interface ActionMapper extends EntityMapper<ActionDTO, Action> {
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")
    ActionDTO toDto(Action action);

    @Mapping(source = "statusId", target = "status")
    Action toEntity(ActionDTO actionDTO);

    default Action fromId(Long id) {
        if (id == null) {
            return null;
        }
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
