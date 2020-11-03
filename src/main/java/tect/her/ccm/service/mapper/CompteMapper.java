package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.CompteDTO;

/**
 * Mapper for the entity {@link Compte} and its DTO {@link CompteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClientMapper.class })
public interface CompteMapper extends EntityMapper<CompteDTO, Compte> {
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.nomCli", target = "clientNomCli")
    CompteDTO toDto(Compte compte);

    @Mapping(target = "engagements", ignore = true)
    @Mapping(target = "removeEngagement", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Compte toEntity(CompteDTO compteDTO);

    default Compte fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compte compte = new Compte();
        compte.setId(id);
        return compte;
    }
}
