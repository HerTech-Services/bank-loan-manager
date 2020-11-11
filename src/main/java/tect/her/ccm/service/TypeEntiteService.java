package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.TypeEntite;
import tect.her.ccm.repository.TypeEntiteRepository;
import tect.her.ccm.service.dto.TypeEntiteDTO;
import tect.her.ccm.service.mapper.TypeEntiteMapper;

/**
 * Service Implementation for managing {@link TypeEntite}.
 */
@Service
@Transactional
public class TypeEntiteService {
    private final Logger log = LoggerFactory.getLogger(TypeEntiteService.class);

    private final TypeEntiteRepository typeEntiteRepository;

    private final TypeEntiteMapper typeEntiteMapper;

    public TypeEntiteService(TypeEntiteRepository typeEntiteRepository, TypeEntiteMapper typeEntiteMapper) {
        this.typeEntiteRepository = typeEntiteRepository;
        this.typeEntiteMapper = typeEntiteMapper;
    }

    /**
     * Save a typeEntite.
     *
     * @param typeEntiteDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeEntiteDTO save(TypeEntiteDTO typeEntiteDTO) {
        log.debug("Request to save TypeEntite : {}", typeEntiteDTO);
        TypeEntite typeEntite = typeEntiteMapper.toEntity(typeEntiteDTO);
        typeEntite = typeEntiteRepository.save(typeEntite);
        return typeEntiteMapper.toDto(typeEntite);
    }

    /**
     * Get all the typeEntites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeEntiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeEntites");
        return typeEntiteRepository.findAll(pageable).map(typeEntiteMapper::toDto);
    }

    /**
     * Get one typeEntite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeEntiteDTO> findOne(Long id) {
        log.debug("Request to get TypeEntite : {}", id);
        return typeEntiteRepository.findById(id).map(typeEntiteMapper::toDto);
    }

    /**
     * Delete the typeEntite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeEntite : {}", id);
        typeEntiteRepository.deleteById(id);
    }
}
