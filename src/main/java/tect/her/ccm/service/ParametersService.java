package tect.her.ccm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.Parameters;
import tect.her.ccm.repository.ParametersRepository;
import tect.her.ccm.service.dto.ParametersDTO;
import tect.her.ccm.service.mapper.ParametersMapper;

/**
 * Service Implementation for managing {@link Parameters}.
 */
@Service
@Transactional
public class ParametersService {
    private final Logger log = LoggerFactory.getLogger(ParametersService.class);

    private final ParametersRepository parametersRepository;

    private final ParametersMapper parametersMapper;

    public ParametersService(ParametersRepository parametersRepository, ParametersMapper parametersMapper) {
        this.parametersRepository = parametersRepository;
        this.parametersMapper = parametersMapper;
    }

    /**
     * Save a parameters.
     *
     * @param parametersDTO the entity to save.
     * @return the persisted entity.
     */
    public ParametersDTO save(ParametersDTO parametersDTO) {
        log.debug("Request to save Parameters : {}", parametersDTO);
        Parameters parameters = parametersMapper.toEntity(parametersDTO);
        parameters = parametersRepository.save(parameters);
        return parametersMapper.toDto(parameters);
    }

    /**
     * Get all the parameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParametersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parameters");
        return parametersRepository.findAll(pageable).map(parametersMapper::toDto);
    }

    /**
     * Get one parameters by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParametersDTO> findOne(Long id) {
        log.debug("Request to get Parameters : {}", id);
        return parametersRepository.findById(id).map(parametersMapper::toDto);
    }

    /**
     * Delete the parameters by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Parameters : {}", id);
        parametersRepository.deleteById(id);
    }
}
