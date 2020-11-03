package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Downgrading;
import tect.her.ccm.repository.DowngradingRepository;
import tect.her.ccm.service.dto.DowngradingDTO;
import tect.her.ccm.service.mapper.DowngradingMapper;

/**
 * Service Implementation for managing {@link Downgrading}.
 */
@Service
@Transactional
public class DowngradingService {
    private final Logger log = LoggerFactory.getLogger(DowngradingService.class);

    private final DowngradingRepository downgradingRepository;

    private final DowngradingMapper downgradingMapper;

    public DowngradingService(DowngradingRepository downgradingRepository, DowngradingMapper downgradingMapper) {
        this.downgradingRepository = downgradingRepository;
        this.downgradingMapper = downgradingMapper;
    }

    /**
     * Save a downgrading.
     *
     * @param downgradingDTO the entity to save.
     * @return the persisted entity.
     */
    public DowngradingDTO save(DowngradingDTO downgradingDTO) {
        log.debug("Request to save Downgrading : {}", downgradingDTO);
        Downgrading downgrading = downgradingMapper.toEntity(downgradingDTO);
        downgrading = downgradingRepository.save(downgrading);
        return downgradingMapper.toDto(downgrading);
    }

    /**
     * Get all the downgradings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DowngradingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Downgradings");
        return downgradingRepository.findAll(pageable).map(downgradingMapper::toDto);
    }

    /**
     * Get one downgrading by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DowngradingDTO> findOne(Long id) {
        log.debug("Request to get Downgrading : {}", id);
        return downgradingRepository.findById(id).map(downgradingMapper::toDto);
    }

    /**
     * Delete the downgrading by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Downgrading : {}", id);
        downgradingRepository.deleteById(id);
    }
}
