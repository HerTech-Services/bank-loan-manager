package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.NotesDTO;

/**
 * Mapper for the entity {@link Notes} and its DTO {@link NotesDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, EngagementMapper.class })
public interface NotesMapper extends EntityMapper<NotesDTO, Notes> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "engagement.subject", target = "engagementSubject")
    NotesDTO toDto(Notes notes);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "engagementId", target = "engagement")
    Notes toEntity(NotesDTO notesDTO);

    default Notes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notes notes = new Notes();
        notes.setId(id);
        return notes;
    }
}
