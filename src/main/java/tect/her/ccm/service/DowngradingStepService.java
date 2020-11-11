package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.DowngradingStep;
import tect.her.ccm.repository.DowngradingStepRepository;
import tect.her.ccm.service.dto.DowngradingStepDTO;
import tect.her.ccm.service.mapper.DowngradingStepMapper;

/**
 * Service Implementation for managing {@link DowngradingStep}.
 */
@Service
@Transactional
public class DowngradingStepService {
    private final Logger log = LoggerFactory.getLogger(DowngradingStepService.class);

    private final DowngradingStepRepository downgradingStepRepository;

    private final DowngradingStepMapper downgradingStepMapper;

    public DowngradingStepService(DowngradingStepRepository downgradingStepRepository, DowngradingStepMapper downgradingStepMapper) {
        this.downgradingStepRepository = downgradingStepRepository;
        this.downgradingStepMapper = downgradingStepMapper;
    }

    /**
     * Save a downgradingStep.
     *
     * @param downgradingStepDTO the entity to save.
     * @return the persisted entity.
     */
    public DowngradingStepDTO save(DowngradingStepDTO downgradingStepDTO) {
        log.debug("Request to save DowngradingStep : {}", downgradingStepDTO);
        DowngradingStep downgradingStep = downgradingStepMapper.toEntity(downgradingStepDTO);
        downgradingStep = downgradingStepRepository.save(downgradingStep);
        return downgradingStepMapper.toDto(downgradingStep);
    }

    /**
     * Get all the downgradingSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DowngradingStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DowngradingSteps");
        return downgradingStepRepository.findAll(pageable).map(downgradingStepMapper::toDto);
    }

    /**
     * Get one downgradingStep by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DowngradingStepDTO> findOne(Long id) {
        log.debug("Request to get DowngradingStep : {}", id);
        return downgradingStepRepository.findById(id).map(downgradingStepMapper::toDto);
    }

    /**
     * Delete the downgradingStep by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DowngradingStep : {}", id);
        downgradingStepRepository.deleteById(id);
    }
}
