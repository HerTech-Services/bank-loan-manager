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
import tect.her.ccm.domain.Reject;
import tect.her.ccm.repository.RejectRepository;
import tect.her.ccm.service.dto.RejectCriteria;
import tect.her.ccm.service.dto.RejectDTO;
import tect.her.ccm.service.mapper.RejectMapper;

/**
 * Service for executing complex queries for {@link Reject} entities in the database.
 * The main input is a {@link RejectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RejectDTO} or a {@link Page} of {@link RejectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RejectQueryService extends QueryService<Reject> {
    private final Logger log = LoggerFactory.getLogger(RejectQueryService.class);

    private final RejectRepository rejectRepository;

    private final RejectMapper rejectMapper;

    public RejectQueryService(RejectRepository rejectRepository, RejectMapper rejectMapper) {
        this.rejectRepository = rejectRepository;
        this.rejectMapper = rejectMapper;
    }

    /**
     * Return a {@link List} of {@link RejectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RejectDTO> findByCriteria(RejectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reject> specification = createSpecification(criteria);
        return rejectMapper.toDto(rejectRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RejectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RejectDTO> findByCriteria(RejectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reject> specification = createSpecification(criteria);
        return rejectRepository.findAll(specification, page).map(rejectMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RejectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reject> specification = createSpecification(criteria);
        return rejectRepository.count(specification);
    }

    /**
     * Function to convert {@link RejectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reject> createSpecification(RejectCriteria criteria) {
        Specification<Reject> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reject_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Reject_.createdDate));
            }
            if (criteria.getCapital() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapital(), Reject_.capital));
            }
            if (criteria.getInterests() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterests(), Reject_.interests));
            }
            if (criteria.getPenalties() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPenalties(), Reject_.penalties));
            }
            if (criteria.getAccessories() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccessories(), Reject_.accessories));
            }
            if (criteria.getTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTypeId(), root -> root.join(Reject_.type, JoinType.LEFT).get(RejectType_.id))
                    );
            }
            if (criteria.getEngagementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEngagementId(),
                            root -> root.join(Reject_.engagement, JoinType.LEFT).get(Engagement_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
