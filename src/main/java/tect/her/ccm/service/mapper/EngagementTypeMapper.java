package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.EngagementTypeDTO;

/**
 * Mapper for the entity {@link EngagementType} and its DTO {@link EngagementTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EngagementTypeMapper extends EntityMapper<EngagementTypeDTO, EngagementType> {
    default EngagementType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EngagementType engagementType = new EngagementType();
        engagementType.setId(id);
        return engagementType;
    }
}
