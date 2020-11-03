package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Notes;
import tect.her.ccm.repository.NotesRepository;
import tect.her.ccm.service.dto.NotesDTO;
import tect.her.ccm.service.mapper.NotesMapper;

/**
 * Service Implementation for managing {@link Notes}.
 */
@Service
@Transactional
public class NotesService {
    private final Logger log = LoggerFactory.getLogger(NotesService.class);

    private final NotesRepository notesRepository;

    private final NotesMapper notesMapper;

    public NotesService(NotesRepository notesRepository, NotesMapper notesMapper) {
        this.notesRepository = notesRepository;
        this.notesMapper = notesMapper;
    }

    /**
     * Save a notes.
     *
     * @param notesDTO the entity to save.
     * @return the persisted entity.
     */
    public NotesDTO save(NotesDTO notesDTO) {
        log.debug("Request to save Notes : {}", notesDTO);
        Notes notes = notesMapper.toEntity(notesDTO);
        notes = notesRepository.save(notes);
        return notesMapper.toDto(notes);
    }

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        return notesRepository.findAll(pageable).map(notesMapper::toDto);
    }

    /**
     * Get one notes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotesDTO> findOne(Long id) {
        log.debug("Request to get Notes : {}", id);
        return notesRepository.findById(id).map(notesMapper::toDto);
    }

    /**
     * Delete the notes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notes : {}", id);
        notesRepository.deleteById(id);
    }
}
