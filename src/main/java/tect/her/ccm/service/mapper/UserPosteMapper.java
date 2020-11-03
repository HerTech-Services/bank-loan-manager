package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.UserPosteDTO;

/**
 * Mapper for the entity {@link UserPoste} and its DTO {@link UserPosteDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, PosteMapper.class })
public interface UserPosteMapper extends EntityMapper<UserPosteDTO, UserPoste> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "entities.id", target = "entitiesId")
    @Mapping(source = "entities.label", target = "entitiesLabel")
    UserPosteDTO toDto(UserPoste userPoste);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "entitiesId", target = "entities")
    UserPoste toEntity(UserPosteDTO userPosteDTO);

    default UserPoste fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPoste userPoste = new UserPoste();
        userPoste.setId(id);
        return userPoste;
    }
}
