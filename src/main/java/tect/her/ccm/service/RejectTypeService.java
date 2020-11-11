package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.RejectType;
import tect.her.ccm.repository.RejectTypeRepository;
import tect.her.ccm.service.dto.RejectTypeDTO;
import tect.her.ccm.service.mapper.RejectTypeMapper;

/**
 * Service Implementation for managing {@link RejectType}.
 */
@Service
@Transactional
public class RejectTypeService {
    private final Logger log = LoggerFactory.getLogger(RejectTypeService.class);

    private final RejectTypeRepository rejectTypeRepository;

    private final RejectTypeMapper rejectTypeMapper;

    public RejectTypeService(RejectTypeRepository rejectTypeRepository, RejectTypeMapper rejectTypeMapper) {
        this.rejectTypeRepository = rejectTypeRepository;
        this.rejectTypeMapper = rejectTypeMapper;
    }

    /**
     * Save a rejectType.
     *
     * @param rejectTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public RejectTypeDTO save(RejectTypeDTO rejectTypeDTO) {
        log.debug("Request to save RejectType : {}", rejectTypeDTO);
        RejectType rejectType = rejectTypeMapper.toEntity(rejectTypeDTO);
        rejectType = rejectTypeRepository.save(rejectType);
        return rejectTypeMapper.toDto(rejectType);
    }

    /**
     * Get all the rejectTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RejectTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RejectTypes");
        return rejectTypeRepository.findAll(pageable).map(rejectTypeMapper::toDto);
    }

    /**
     * Get one rejectType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RejectTypeDTO> findOne(Long id) {
        log.debug("Request to get RejectType : {}", id);
        return rejectTypeRepository.findById(id).map(rejectTypeMapper::toDto);
    }

    /**
     * Delete the rejectType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RejectType : {}", id);
        rejectTypeRepository.deleteById(id);
    }
}
