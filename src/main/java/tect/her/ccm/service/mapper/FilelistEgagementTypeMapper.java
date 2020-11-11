package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.FilelistEgagementTypeDTO;

/**
 * Mapper for the entity {@link FilelistEgagementType} and its DTO {@link FilelistEgagementTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { EngagementTypeMapper.class, FilelistMapper.class })
public interface FilelistEgagementTypeMapper extends EntityMapper<FilelistEgagementTypeDTO, FilelistEgagementType> {
    @Mapping(source = "engagementType.id", target = "engagementTypeId")
    @Mapping(source = "engagementType.label", target = "engagementTypeLabel")
    @Mapping(source = "filelist.id", target = "filelistId")
    FilelistEgagementTypeDTO toDto(FilelistEgagementType filelistEgagementType);

    @Mapping(source = "engagementTypeId", target = "engagementType")
    @Mapping(source = "filelistId", target = "filelist")
    FilelistEgagementType toEntity(FilelistEgagementTypeDTO filelistEgagementTypeDTO);

    default FilelistEgagementType fromId(Long id) {
        if (id == null) {
            return null;
        }
        FilelistEgagementType filelistEgagementType = new FilelistEgagementType();
        filelistEgagementType.setId(id);
        return filelistEgagementType;
    }
}
