package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.PosteDTO;

/**
 * Mapper for the entity {@link Poste} and its DTO {@link PosteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PosteMapper extends EntityMapper<PosteDTO, Poste> {
    @Mapping(target = "userPostes", ignore = true)
    @Mapping(target = "removeUserPoste", ignore = true)
    @Mapping(target = "removeParentPoste", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChild", ignore = true)
    Poste toEntity(PosteDTO posteDTO);

    default Poste fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poste poste = new Poste();
        poste.setId(id);
        return poste;
    }
}
