package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Poste;
import tect.her.ccm.repository.PosteRepository;
import tect.her.ccm.service.dto.PosteDTO;
import tect.her.ccm.service.mapper.PosteMapper;

/**
 * Service Implementation for managing {@link Poste}.
 */
@Service
@Transactional
public class PosteService {
    private final Logger log = LoggerFactory.getLogger(PosteService.class);

    private final PosteRepository posteRepository;

    private final PosteMapper posteMapper;

    public PosteService(PosteRepository posteRepository, PosteMapper posteMapper) {
        this.posteRepository = posteRepository;
        this.posteMapper = posteMapper;
    }

    /**
     * Save a poste.
     *
     * @param posteDTO the entity to save.
     * @return the persisted entity.
     */
    public PosteDTO save(PosteDTO posteDTO) {
        log.debug("Request to save Poste : {}", posteDTO);
        Poste poste = posteMapper.toEntity(posteDTO);
        poste = posteRepository.save(poste);
        return posteMapper.toDto(poste);
    }

    /**
     * Get all the postes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PosteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Postes");
        return posteRepository.findAll(pageable).map(posteMapper::toDto);
    }

    /**
     * Get all the postes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PosteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return posteRepository.findAllWithEagerRelationships(pageable).map(posteMapper::toDto);
    }

    /**
     * Get one poste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PosteDTO> findOne(Long id) {
        log.debug("Request to get Poste : {}", id);
        return posteRepository.findOneWithEagerRelationships(id).map(posteMapper::toDto);
    }

    /**
     * Delete the poste by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Poste : {}", id);
        posteRepository.deleteById(id);
    }
}
