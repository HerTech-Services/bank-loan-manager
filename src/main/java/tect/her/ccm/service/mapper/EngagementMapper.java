package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.EngagementDTO;

/**
 * Mapper for the entity {@link Engagement} and its DTO {@link EngagementDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { TaskMapper.class, EngagementTypeMapper.class, StatusMapper.class, UserMapper.class, ClientMapper.class, CompteMapper.class }
)
public interface EngagementMapper extends EntityMapper<EngagementDTO, Engagement> {
    @Mapping(source = "currentTask.id", target = "currentTaskId")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.label", target = "typeLabel")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")
    @Mapping(source = "decision.id", target = "decisionId")
    @Mapping(source = "decision.label", target = "decisionLabel")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.firstName", target = "createdByFirstName")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.nomCli", target = "clientNomCli")
    @Mapping(source = "compte.id", target = "compteId")
    @Mapping(source = "compte.libCpt", target = "compteLibCpt")
    EngagementDTO toDto(Engagement engagement);

    @Mapping(source = "currentTaskId", target = "currentTask")
    @Mapping(target = "downgradings", ignore = true)
    @Mapping(target = "removeDowngrading", ignore = true)
    @Mapping(target = "rejects", ignore = true)
    @Mapping(target = "removeReject", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "removeTask", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "removeNote", ignore = true)
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "decisionId", target = "decision")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "compteId", target = "compte")
    Engagement toEntity(EngagementDTO engagementDTO);

    default Engagement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Engagement engagement = new Engagement();
        engagement.setId(id);
        return engagement;
    }
}
