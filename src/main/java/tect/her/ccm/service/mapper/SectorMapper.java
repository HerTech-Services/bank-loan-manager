package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.SectorDTO;

/**
 * Mapper for the entity {@link Sector} and its DTO {@link SectorDTO}.
 */
@Mapper(componentModel = "spring", uses = { ScoringMapper.class })
public interface SectorMapper extends EntityMapper<SectorDTO, Sector> {
    @Mapping(source = "scoring.id", target = "scoringId")
    @Mapping(source = "scoring.label", target = "scoringLabel")
    SectorDTO toDto(Sector sector);

    @Mapping(source = "scoringId", target = "scoring")
    Sector toEntity(SectorDTO sectorDTO);

    default Sector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sector sector = new Sector();
        sector.setId(id);
        return sector;
    }
}
