package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.FilelistDTO;

/**
 * Mapper for the entity {@link Filelist} and its DTO {@link FilelistDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FilelistMapper extends EntityMapper<FilelistDTO, Filelist> {
    @Mapping(target = "filelistEngagementTypes", ignore = true)
    @Mapping(target = "removeFilelistEngagementType", ignore = true)
    Filelist toEntity(FilelistDTO filelistDTO);

    default Filelist fromId(Long id) {
        if (id == null) {
            return null;
        }
        Filelist filelist = new Filelist();
        filelist.setId(id);
        return filelist;
    }
}
