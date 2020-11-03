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
import tect.her.ccm.domain.Engagement;
import tect.her.ccm.repository.EngagementRepository;
import tect.her.ccm.service.dto.EngagementCriteria;
import tect.her.ccm.service.dto.EngagementDTO;
import tect.her.ccm.service.mapper.EngagementMapper;

/**
 * Service for executing complex queries for {@link Engagement} entities in the database.
 * The main input is a {@link EngagementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EngagementDTO} or a {@link Page} of {@link EngagementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EngagementQueryService extends QueryService<Engagement> {
    private final Logger log = LoggerFactory.getLogger(EngagementQueryService.class);

    private final EngagementRepository engagementRepository;

    private final EngagementMapper engagementMapper;

    public EngagementQueryService(EngagementRepository engagementRepository, EngagementMapper engagementMapper) {
        this.engagementRepository = engagementRepository;
        this.engagementMapper = engagementMapper;
    }

    /**
     * Return a {@link List} of {@link EngagementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EngagementDTO> findByCriteria(EngagementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Engagement> specification = createSpecification(criteria);
        return engagementMapper.toDto(engagementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EngagementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EngagementDTO> findByCriteria(EngagementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Engagement> specification = createSpecification(criteria);
        return engagementRepository.findAll(specification, page).map(engagementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EngagementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Engagement> specification = createSpecification(criteria);
        return engagementRepository.count(specification);
    }

    /**
     * Function to convert {@link EngagementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Engagement> createSpecification(EngagementCriteria criteria) {
        Specification<Engagement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Engagement_.id));
            }
            if (criteria.getScoring() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoring(), Engagement_.scoring));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Engagement_.subject));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Engagement_.amount));
            }
            if (criteria.getDelay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelay(), Engagement_.delay));
            }
            if (criteria.getPayment() != null) {
                specification = specification.and(buildSpecification(criteria.getPayment(), Engagement_.payment));
            }
            if (criteria.getDelayed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelayed(), Engagement_.delayed));
            }
            if (criteria.getReimbursement() != null) {
                specification = specification.and(buildSpecification(criteria.getReimbursement(), Engagement_.reimbursement));
            }
            if (criteria.getIsStop() != null) {
                specification = specification.and(buildSpecification(criteria.getIsStop(), Engagement_.isStop));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Engagement_.isDeleted));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Engagement_.startDate));
            }
            if (criteria.getEnDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnDate(), Engagement_.enDate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Engagement_.createdDate));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), Engagement_.updatedDate));
            }
            if (criteria.getDeletedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedDate(), Engagement_.deletedDate));
            }
            if (criteria.getStopedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStopedDate(), Engagement_.stopedDate));
            }
            if (criteria.getCurrentTaskId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCurrentTaskId(),
                            root -> root.join(Engagement_.currentTask, JoinType.LEFT).get(Task_.id)
                        )
                    );
            }
            if (criteria.getDowngradingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDowngradingId(),
                            root -> root.join(Engagement_.downgradings, JoinType.LEFT).get(Downgrading_.id)
                        )
                    );
            }
            if (criteria.getRejectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRejectId(), root -> root.join(Engagement_.rejects, JoinType.LEFT).get(Reject_.id))
                    );
            }
            if (criteria.getTaskId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTaskId(), root -> root.join(Engagement_.tasks, JoinType.LEFT).get(Task_.id))
                    );
            }
            if (criteria.getNoteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNoteId(), root -> root.join(Engagement_.notes, JoinType.LEFT).get(Notes_.id))
                    );
            }
            if (criteria.getTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTypeId(), root -> root.join(Engagement_.type, JoinType.LEFT).get(EngagementType_.id))
                    );
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStatusId(), root -> root.join(Engagement_.status, JoinType.LEFT).get(Status_.id))
                    );
            }
            if (criteria.getDecisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDecisionId(), root -> root.join(Engagement_.decision, JoinType.LEFT).get(Status_.id))
                    );
            }
            if (criteria.getCreatedById() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCreatedById(), root -> root.join(Engagement_.createdBy, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getClientId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClientId(), root -> root.join(Engagement_.client, JoinType.LEFT).get(Client_.id))
                    );
            }
            if (criteria.getCompteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompteId(), root -> root.join(Engagement_.compte, JoinType.LEFT).get(Compte_.id))
                    );
            }
        }
        return specification;
    }
}
