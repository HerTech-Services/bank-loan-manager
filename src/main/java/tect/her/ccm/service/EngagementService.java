package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.repository.EngagementRepository;
import tect.her.ccm.service.dto.EngagementDTO;
import tect.her.ccm.service.mapper.EngagementMapper;

/**
 * Service Implementation for managing {@link Engagement}.
 */
@Service
@Transactional
public class EngagementService {
    private final Logger log = LoggerFactory.getLogger(EngagementService.class);

    private final EngagementRepository engagementRepository;

    private final EngagementMapper engagementMapper;

    public EngagementService(EngagementRepository engagementRepository, EngagementMapper engagementMapper) {
        this.engagementRepository = engagementRepository;
        this.engagementMapper = engagementMapper;
    }

    /**
     * Save a engagement.
     *
     * @param engagementDTO the entity to save.
     * @return the persisted entity.
     */
    public EngagementDTO save(EngagementDTO engagementDTO) {
        log.debug("Request to save Engagement : {}", engagementDTO);
        Engagement engagement = engagementMapper.toEntity(engagementDTO);
        engagement = engagementRepository.save(engagement);
        return engagementMapper.toDto(engagement);
    }

    /**
     * Get all the engagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EngagementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Engagements");
        return engagementRepository.findAll(pageable).map(engagementMapper::toDto);
    }

    /**
     * Get one engagement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EngagementDTO> findOne(Long id) {
        log.debug("Request to get Engagement : {}", id);
        return engagementRepository.findById(id).map(engagementMapper::toDto);
    }

    /**
     * Delete the engagement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Engagement : {}", id);
        engagementRepository.deleteById(id);
    }
}
