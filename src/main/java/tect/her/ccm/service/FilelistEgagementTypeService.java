package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.FilelistEgagementType;
import tect.her.ccm.repository.FilelistEgagementTypeRepository;
import tect.her.ccm.service.dto.FilelistEgagementTypeDTO;
import tect.her.ccm.service.mapper.FilelistEgagementTypeMapper;

/**
 * Service Implementation for managing {@link FilelistEgagementType}.
 */
@Service
@Transactional
public class FilelistEgagementTypeService {
    private final Logger log = LoggerFactory.getLogger(FilelistEgagementTypeService.class);

    private final FilelistEgagementTypeRepository filelistEgagementTypeRepository;

    private final FilelistEgagementTypeMapper filelistEgagementTypeMapper;

    public FilelistEgagementTypeService(
        FilelistEgagementTypeRepository filelistEgagementTypeRepository,
        FilelistEgagementTypeMapper filelistEgagementTypeMapper
    ) {
        this.filelistEgagementTypeRepository = filelistEgagementTypeRepository;
        this.filelistEgagementTypeMapper = filelistEgagementTypeMapper;
    }

    /**
     * Save a filelistEgagementType.
     *
     * @param filelistEgagementTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FilelistEgagementTypeDTO save(FilelistEgagementTypeDTO filelistEgagementTypeDTO) {
        log.debug("Request to save FilelistEgagementType : {}", filelistEgagementTypeDTO);
        FilelistEgagementType filelistEgagementType = filelistEgagementTypeMapper.toEntity(filelistEgagementTypeDTO);
        filelistEgagementType = filelistEgagementTypeRepository.save(filelistEgagementType);
        return filelistEgagementTypeMapper.toDto(filelistEgagementType);
    }

    /**
     * Get all the filelistEgagementTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FilelistEgagementTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FilelistEgagementTypes");
        return filelistEgagementTypeRepository.findAll(pageable).map(filelistEgagementTypeMapper::toDto);
    }

    /**
     * Get one filelistEgagementType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FilelistEgagementTypeDTO> findOne(Long id) {
        log.debug("Request to get FilelistEgagementType : {}", id);
        return filelistEgagementTypeRepository.findById(id).map(filelistEgagementTypeMapper::toDto);
    }

    /**
     * Delete the filelistEgagementType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FilelistEgagementType : {}", id);
        filelistEgagementTypeRepository.deleteById(id);
    }
}
