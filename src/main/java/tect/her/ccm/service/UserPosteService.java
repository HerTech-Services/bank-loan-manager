package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.UserPoste;
import tect.her.ccm.repository.UserPosteRepository;
import tect.her.ccm.service.dto.UserPosteDTO;
import tect.her.ccm.service.mapper.UserPosteMapper;

/**
 * Service Implementation for managing {@link UserPoste}.
 */
@Service
@Transactional
public class UserPosteService {
    private final Logger log = LoggerFactory.getLogger(UserPosteService.class);

    private final UserPosteRepository userPosteRepository;

    private final UserPosteMapper userPosteMapper;

    public UserPosteService(UserPosteRepository userPosteRepository, UserPosteMapper userPosteMapper) {
        this.userPosteRepository = userPosteRepository;
        this.userPosteMapper = userPosteMapper;
    }

    /**
     * Save a userPoste.
     *
     * @param userPosteDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPosteDTO save(UserPosteDTO userPosteDTO) {
        log.debug("Request to save UserPoste : {}", userPosteDTO);
        UserPoste userPoste = userPosteMapper.toEntity(userPosteDTO);
        userPoste = userPosteRepository.save(userPoste);
        return userPosteMapper.toDto(userPoste);
    }

    /**
     * Get all the userPostes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserPosteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPostes");
        return userPosteRepository.findAll(pageable).map(userPosteMapper::toDto);
    }

    /**
     * Get one userPoste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserPosteDTO> findOne(Long id) {
        log.debug("Request to get UserPoste : {}", id);
        return userPosteRepository.findById(id).map(userPosteMapper::toDto);
    }

    /**
     * Delete the userPoste by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPoste : {}", id);
        userPosteRepository.deleteById(id);
    }
}
