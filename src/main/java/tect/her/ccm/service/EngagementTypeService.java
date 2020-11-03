package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.EngagementType;
import tect.her.ccm.repository.EngagementTypeRepository;
import tect.her.ccm.service.dto.EngagementTypeDTO;
import tect.her.ccm.service.mapper.EngagementTypeMapper;

/**
 * Service Implementation for managing {@link EngagementType}.
 */
@Service
@Transactional
public class EngagementTypeService {
    private final Logger log = LoggerFactory.getLogger(EngagementTypeService.class);

    private final EngagementTypeRepository engagementTypeRepository;

    private final EngagementTypeMapper engagementTypeMapper;

    public EngagementTypeService(EngagementTypeRepository engagementTypeRepository, EngagementTypeMapper engagementTypeMapper) {
        this.engagementTypeRepository = engagementTypeRepository;
        this.engagementTypeMapper = engagementTypeMapper;
    }

    /**
     * Save a engagementType.
     *
     * @param engagementTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public EngagementTypeDTO save(EngagementTypeDTO engagementTypeDTO) {
        log.debug("Request to save EngagementType : {}", engagementTypeDTO);
        EngagementType engagementType = engagementTypeMapper.toEntity(engagementTypeDTO);
        engagementType = engagementTypeRepository.save(engagementType);
        return engagementTypeMapper.toDto(engagementType);
    }

    /**
     * Get all the engagementTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EngagementTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EngagementTypes");
        return engagementTypeRepository.findAll(pageable).map(engagementTypeMapper::toDto);
    }

    /**
     * Get one engagementType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EngagementTypeDTO> findOne(Long id) {
        log.debug("Request to get EngagementType : {}", id);
        return engagementTypeRepository.findById(id).map(engagementTypeMapper::toDto);
    }

    /**
     * Delete the engagementType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EngagementType : {}", id);
        engagementTypeRepository.deleteById(id);
    }
}
