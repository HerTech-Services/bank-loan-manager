package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.EntiteDTO;

/**
 * Mapper for the entity {@link Entite} and its DTO {@link EntiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntiteMapper extends EntityMapper<EntiteDTO, Entite> {
    @Mapping(target = "employeEntites", ignore = true)
    @Mapping(target = "removeEmployeEntite", ignore = true)
    Entite toEntity(EntiteDTO entiteDTO);

    default Entite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entite entite = new Entite();
        entite.setId(id);
        return entite;
    }
}
