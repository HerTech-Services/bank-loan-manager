package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.TypeEntiteDTO;

/**
 * Mapper for the entity {@link TypeEntite} and its DTO {@link TypeEntiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeEntiteMapper extends EntityMapper<TypeEntiteDTO, TypeEntite> {
    default TypeEntite fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeEntite typeEntite = new TypeEntite();
        typeEntite.setId(id);
        return typeEntite;
    }
}
