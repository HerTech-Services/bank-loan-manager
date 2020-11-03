package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.DowngradingStepDTO;

/**
 * Mapper for the entity {@link DowngradingStep} and its DTO {@link DowngradingStepDTO}.
 */
@Mapper(componentModel = "spring", uses = { ScoringMapper.class })
public interface DowngradingStepMapper extends EntityMapper<DowngradingStepDTO, DowngradingStep> {
    @Mapping(source = "scoring.id", target = "scoringId")
    @Mapping(source = "scoring.label", target = "scoringLabel")
    DowngradingStepDTO toDto(DowngradingStep downgradingStep);

    @Mapping(source = "scoringId", target = "scoring")
    DowngradingStep toEntity(DowngradingStepDTO downgradingStepDTO);

    default DowngradingStep fromId(Long id) {
        if (id == null) {
            return null;
        }
        DowngradingStep downgradingStep = new DowngradingStep();
        downgradingStep.setId(id);
        return downgradingStep;
    }
}
