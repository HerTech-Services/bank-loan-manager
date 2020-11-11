package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.RejectTypeDTO;

/**
 * Mapper for the entity {@link RejectType} and its DTO {@link RejectTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ScoringMapper.class })
public interface RejectTypeMapper extends EntityMapper<RejectTypeDTO, RejectType> {
    @Mapping(source = "scoring.id", target = "scoringId")
    @Mapping(source = "scoring.label", target = "scoringLabel")
    RejectTypeDTO toDto(RejectType rejectType);

    @Mapping(source = "scoringId", target = "scoring")
    RejectType toEntity(RejectTypeDTO rejectTypeDTO);

    default RejectType fromId(Long id) {
        if (id == null) {
            return null;
        }
        RejectType rejectType = new RejectType();
        rejectType.setId(id);
        return rejectType;
    }
}
