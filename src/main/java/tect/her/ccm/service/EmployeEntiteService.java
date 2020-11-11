package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.EmployeEntite;
import tect.her.ccm.repository.EmployeEntiteRepository;
import tect.her.ccm.service.dto.EmployeEntiteDTO;
import tect.her.ccm.service.mapper.EmployeEntiteMapper;

/**
 * Service Implementation for managing {@link EmployeEntite}.
 */
@Service
@Transactional
public class EmployeEntiteService {
    private final Logger log = LoggerFactory.getLogger(EmployeEntiteService.class);

    private final EmployeEntiteRepository employeEntiteRepository;

    private final EmployeEntiteMapper employeEntiteMapper;

    public EmployeEntiteService(EmployeEntiteRepository employeEntiteRepository, EmployeEntiteMapper employeEntiteMapper) {
        this.employeEntiteRepository = employeEntiteRepository;
        this.employeEntiteMapper = employeEntiteMapper;
    }

    /**
     * Save a employeEntite.
     *
     * @param employeEntiteDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeEntiteDTO save(EmployeEntiteDTO employeEntiteDTO) {
        log.debug("Request to save EmployeEntite : {}", employeEntiteDTO);
        EmployeEntite employeEntite = employeEntiteMapper.toEntity(employeEntiteDTO);
        employeEntite = employeEntiteRepository.save(employeEntite);
        return employeEntiteMapper.toDto(employeEntite);
    }

    /**
     * Get all the employeEntites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeEntiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeEntites");
        return employeEntiteRepository.findAll(pageable).map(employeEntiteMapper::toDto);
    }

    /**
     * Get one employeEntite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeEntiteDTO> findOne(Long id) {
        log.debug("Request to get EmployeEntite : {}", id);
        return employeEntiteRepository.findById(id).map(employeEntiteMapper::toDto);
    }

    /**
     * Delete the employeEntite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeEntite : {}", id);
        employeEntiteRepository.deleteById(id);
    }
}
