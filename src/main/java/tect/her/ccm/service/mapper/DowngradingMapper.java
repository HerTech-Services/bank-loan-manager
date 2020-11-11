package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.DowngradingDTO;

/**
 * Mapper for the entity {@link Downgrading} and its DTO {@link DowngradingDTO}.
 */
@Mapper(componentModel = "spring", uses = { DowngradingStepMapper.class, EngagementMapper.class })
public interface DowngradingMapper extends EntityMapper<DowngradingDTO, Downgrading> {
    @Mapping(source = "step.id", target = "stepId")
    @Mapping(source = "step.label", target = "stepLabel")
    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "engagement.subject", target = "engagementSubject")
    DowngradingDTO toDto(Downgrading downgrading);

    @Mapping(source = "stepId", target = "step")
    @Mapping(source = "engagementId", target = "engagement")
    Downgrading toEntity(DowngradingDTO downgradingDTO);

    default Downgrading fromId(Long id) {
        if (id == null) {
            return null;
        }
        Downgrading downgrading = new Downgrading();
        downgrading.setId(id);
        return downgrading;
    }
}
