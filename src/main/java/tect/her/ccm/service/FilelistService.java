package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Filelist;
import tect.her.ccm.repository.FilelistRepository;
import tect.her.ccm.service.dto.FilelistDTO;
import tect.her.ccm.service.mapper.FilelistMapper;

/**
 * Service Implementation for managing {@link Filelist}.
 */
@Service
@Transactional
public class FilelistService {
    private final Logger log = LoggerFactory.getLogger(FilelistService.class);

    private final FilelistRepository filelistRepository;

    private final FilelistMapper filelistMapper;

    public FilelistService(FilelistRepository filelistRepository, FilelistMapper filelistMapper) {
        this.filelistRepository = filelistRepository;
        this.filelistMapper = filelistMapper;
    }

    /**
     * Save a filelist.
     *
     * @param filelistDTO the entity to save.
     * @return the persisted entity.
     */
    public FilelistDTO save(FilelistDTO filelistDTO) {
        log.debug("Request to save Filelist : {}", filelistDTO);
        Filelist filelist = filelistMapper.toEntity(filelistDTO);
        filelist = filelistRepository.save(filelist);
        return filelistMapper.toDto(filelist);
    }

    /**
     * Get all the filelists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FilelistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Filelists");
        return filelistRepository.findAll(pageable).map(filelistMapper::toDto);
    }

    /**
     * Get one filelist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FilelistDTO> findOne(Long id) {
        log.debug("Request to get Filelist : {}", id);
        return filelistRepository.findById(id).map(filelistMapper::toDto);
    }

    /**
     * Delete the filelist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Filelist : {}", id);
        filelistRepository.deleteById(id);
    }
}
