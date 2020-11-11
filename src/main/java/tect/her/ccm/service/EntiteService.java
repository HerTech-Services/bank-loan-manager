package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Entite;
import tect.her.ccm.repository.EntiteRepository;
import tect.her.ccm.service.dto.EntiteDTO;
import tect.her.ccm.service.mapper.EntiteMapper;

/**
 * Service Implementation for managing {@link Entite}.
 */
@Service
@Transactional
public class EntiteService {
    private final Logger log = LoggerFactory.getLogger(EntiteService.class);

    private final EntiteRepository entiteRepository;

    private final EntiteMapper entiteMapper;

    public EntiteService(EntiteRepository entiteRepository, EntiteMapper entiteMapper) {
        this.entiteRepository = entiteRepository;
        this.entiteMapper = entiteMapper;
    }

    /**
     * Save a entite.
     *
     * @param entiteDTO the entity to save.
     * @return the persisted entity.
     */
    public EntiteDTO save(EntiteDTO entiteDTO) {
        log.debug("Request to save Entite : {}", entiteDTO);
        Entite entite = entiteMapper.toEntity(entiteDTO);
        entite = entiteRepository.save(entite);
        return entiteMapper.toDto(entite);
    }

    /**
     * Get all the entites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entites");
        return entiteRepository.findAll(pageable).map(entiteMapper::toDto);
    }

    /**
     * Get one entite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntiteDTO> findOne(Long id) {
        log.debug("Request to get Entite : {}", id);
        return entiteRepository.findById(id).map(entiteMapper::toDto);
    }

    /**
     * Delete the entite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entite : {}", id);
        entiteRepository.deleteById(id);
    }
}
