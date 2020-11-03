package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.ScoringDTO;

/**
 * Mapper for the entity {@link Scoring} and its DTO {@link ScoringDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScoringMapper extends EntityMapper<ScoringDTO, Scoring> {
    default Scoring fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scoring scoring = new Scoring();
        scoring.setId(id);
        return scoring;
    }
}
