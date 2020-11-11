package tect.her.ccm.service;

import io.github.jhipster.service.QueryService;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tect.her.ccm.domain.*; // for static metamodels
import tect.her.ccm.domain.Employe;
import tect.her.ccm.repository.EmployeRepository;
import tect.her.ccm.service.dto.EmployeCriteria;
import tect.her.ccm.service.dto.EmployeDTO;
import tect.her.ccm.service.mapper.EmployeMapper;

/**
 * Service for executing complex queries for {@link Employe} entities in the database.
 * The main input is a {@link EmployeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeDTO} or a {@link Page} of {@link EmployeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeQueryService extends QueryService<Employe> {
    private final Logger log = LoggerFactory.getLogger(EmployeQueryService.class);

    private final EmployeRepository employeRepository;

    private final EmployeMapper employeMapper;

    public EmployeQueryService(EmployeRepository employeRepository, EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeDTO> findByCriteria(EmployeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employe> specification = createSpecification(criteria);
        return employeMapper.toDto(employeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeDTO> findByCriteria(EmployeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employe> specification = createSpecification(criteria);
        return employeRepository.findAll(specification, page).map(employeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employe> specification = createSpecification(criteria);
        return employeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employe> createSpecification(EmployeCriteria criteria) {
        Specification<Employe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employe_.id));
            }
            if (criteria.getCodBnk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodBnk(), Employe_.codBnk));
            }
            if (criteria.getCodEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodEmp(), Employe_.codEmp));
            }
            if (criteria.getRsEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRsEmp(), Employe_.rsEmp));
            }
            if (criteria.getNomEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomEmp(), Employe_.nomEmp));
            }
            if (criteria.getPrenomEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenomEmp(), Employe_.prenomEmp));
            }
            if (criteria.getFctEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFctEmp(), Employe_.fctEmp));
            }
            if (criteria.getAdrEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdrEmp(), Employe_.adrEmp));
            }
            if (criteria.getTeEmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeEmp(), Employe_.teEmp));
            }
            if (criteria.getTypEnmp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypEnmp(), Employe_.typEnmp));
            }
            if (criteria.getNumMat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumMat(), Employe_.numMat));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Employe_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
