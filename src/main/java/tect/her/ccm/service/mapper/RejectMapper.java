package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.RejectDTO;

/**
 * Mapper for the entity {@link Reject} and its DTO {@link RejectDTO}.
 */
@Mapper(componentModel = "spring", uses = { RejectTypeMapper.class, EngagementMapper.class })
public interface RejectMapper extends EntityMapper<RejectDTO, Reject> {
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.label", target = "typeLabel")
    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "engagement.subject", target = "engagementSubject")
    RejectDTO toDto(Reject reject);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "engagementId", target = "engagement")
    Reject toEntity(RejectDTO rejectDTO);

    default Reject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reject reject = new Reject();
        reject.setId(id);
        return reject;
    }
}
