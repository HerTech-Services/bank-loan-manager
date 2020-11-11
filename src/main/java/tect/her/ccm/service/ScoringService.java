package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Scoring;
import tect.her.ccm.repository.ScoringRepository;
import tect.her.ccm.service.dto.ScoringDTO;
import tect.her.ccm.service.mapper.ScoringMapper;

/**
 * Service Implementation for managing {@link Scoring}.
 */
@Service
@Transactional
public class ScoringService {
    private final Logger log = LoggerFactory.getLogger(ScoringService.class);

    private final ScoringRepository scoringRepository;

    private final ScoringMapper scoringMapper;

    public ScoringService(ScoringRepository scoringRepository, ScoringMapper scoringMapper) {
        this.scoringRepository = scoringRepository;
        this.scoringMapper = scoringMapper;
    }

    /**
     * Save a scoring.
     *
     * @param scoringDTO the entity to save.
     * @return the persisted entity.
     */
    public ScoringDTO save(ScoringDTO scoringDTO) {
        log.debug("Request to save Scoring : {}", scoringDTO);
        Scoring scoring = scoringMapper.toEntity(scoringDTO);
        scoring = scoringRepository.save(scoring);
        return scoringMapper.toDto(scoring);
    }

    /**
     * Get all the scorings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScoringDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scorings");
        return scoringRepository.findAll(pageable).map(scoringMapper::toDto);
    }

    /**
     * Get one scoring by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScoringDTO> findOne(Long id) {
        log.debug("Request to get Scoring : {}", id);
        return scoringRepository.findById(id).map(scoringMapper::toDto);
    }

    /**
     * Delete the scoring by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Scoring : {}", id);
        scoringRepository.deleteById(id);
    }
}
