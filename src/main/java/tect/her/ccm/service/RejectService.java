package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Reject;
import tect.her.ccm.repository.RejectRepository;
import tect.her.ccm.service.dto.RejectDTO;
import tect.her.ccm.service.mapper.RejectMapper;

/**
 * Service Implementation for managing {@link Reject}.
 */
@Service
@Transactional
public class RejectService {
    private final Logger log = LoggerFactory.getLogger(RejectService.class);

    private final RejectRepository rejectRepository;

    private final RejectMapper rejectMapper;

    public RejectService(RejectRepository rejectRepository, RejectMapper rejectMapper) {
        this.rejectRepository = rejectRepository;
        this.rejectMapper = rejectMapper;
    }

    /**
     * Save a reject.
     *
     * @param rejectDTO the entity to save.
     * @return the persisted entity.
     */
    public RejectDTO save(RejectDTO rejectDTO) {
        log.debug("Request to save Reject : {}", rejectDTO);
        Reject reject = rejectMapper.toEntity(rejectDTO);
        reject = rejectRepository.save(reject);
        return rejectMapper.toDto(reject);
    }

    /**
     * Get all the rejects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RejectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rejects");
        return rejectRepository.findAll(pageable).map(rejectMapper::toDto);
    }

    /**
     * Get one reject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RejectDTO> findOne(Long id) {
        log.debug("Request to get Reject : {}", id);
        return rejectRepository.findById(id).map(rejectMapper::toDto);
    }

    /**
     * Delete the reject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reject : {}", id);
        rejectRepository.deleteById(id);
    }
}
