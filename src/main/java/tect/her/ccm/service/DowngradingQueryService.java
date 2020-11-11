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
import tect.her.ccm.domain.Downgrading;
import tect.her.ccm.repository.DowngradingRepository;
import tect.her.ccm.service.dto.DowngradingCriteria;
import tect.her.ccm.service.dto.DowngradingDTO;
import tect.her.ccm.service.mapper.DowngradingMapper;

/**
 * Service for executing complex queries for {@link Downgrading} entities in the database.
 * The main input is a {@link DowngradingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DowngradingDTO} or a {@link Page} of {@link DowngradingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DowngradingQueryService extends QueryService<Downgrading> {
    private final Logger log = LoggerFactory.getLogger(DowngradingQueryService.class);

    private final DowngradingRepository downgradingRepository;

    private final DowngradingMapper downgradingMapper;

    public DowngradingQueryService(DowngradingRepository downgradingRepository, DowngradingMapper downgradingMapper) {
        this.downgradingRepository = downgradingRepository;
        this.downgradingMapper = downgradingMapper;
    }

    /**
     * Return a {@link List} of {@link DowngradingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DowngradingDTO> findByCriteria(DowngradingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Downgrading> specification = createSpecification(criteria);
        return downgradingMapper.toDto(downgradingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DowngradingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DowngradingDTO> findByCriteria(DowngradingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Downgrading> specification = createSpecification(criteria);
        return downgradingRepository.findAll(specification, page).map(downgradingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DowngradingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Downgrading> specification = createSpecification(criteria);
        return downgradingRepository.count(specification);
    }

    /**
     * Function to convert {@link DowngradingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Downgrading> createSpecification(DowngradingCriteria criteria) {
        Specification<Downgrading> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Downgrading_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Downgrading_.createdDate));
            }
            if (criteria.getCapital() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapital(), Downgrading_.capital));
            }
            if (criteria.getInterests() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterests(), Downgrading_.interests));
            }
            if (criteria.getPenalties() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPenalties(), Downgrading_.penalties));
            }
            if (criteria.getAccessories() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccessories(), Downgrading_.accessories));
            }
            if (criteria.getStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStepId(),
                            root -> root.join(Downgrading_.step, JoinType.LEFT).get(DowngradingStep_.id)
                        )
                    );
            }
            if (criteria.getEngagementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEngagementId(),
                            root -> root.join(Downgrading_.engagement, JoinType.LEFT).get(Engagement_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
