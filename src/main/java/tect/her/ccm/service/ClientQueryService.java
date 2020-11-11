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
import tect.her.ccm.domain.Client;
import tect.her.ccm.repository.ClientRepository;
import tect.her.ccm.service.dto.ClientCriteria;
import tect.her.ccm.service.dto.ClientDTO;
import tect.her.ccm.service.mapper.ClientMapper;

/**
 * Service for executing complex queries for {@link Client} entities in the database.
 * The main input is a {@link ClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientDTO} or a {@link Page} of {@link ClientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientQueryService extends QueryService<Client> {
    private final Logger log = LoggerFactory.getLogger(ClientQueryService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientQueryService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Return a {@link List} of {@link ClientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientDTO> findByCriteria(ClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Client> specification = createSpecification(criteria);
        return clientMapper.toDto(clientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findByCriteria(ClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.findAll(specification, page).map(clientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Client> createSpecification(ClientCriteria criteria) {
        Specification<Client> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Client_.id));
            }
            if (criteria.getCodBnk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodBnk(), Client_.codBnk));
            }
            if (criteria.getCodCli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodCli(), Client_.codCli));
            }
            if (criteria.getNomCli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomCli(), Client_.nomCli));
            }
            if (criteria.getMendCli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMendCli(), Client_.mendCli));
            }
            if (criteria.getSfCli() != null) {
                specification = specification.and(buildSpecification(criteria.getSfCli(), Client_.sfCli));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), Client_.titre));
            }
            if (criteria.getDatNai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatNai(), Client_.datNai));
            }
            if (criteria.getLieuNai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuNai(), Client_.lieuNai));
            }
            if (criteria.getNatCli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNatCli(), Client_.natCli));
            }
            if (criteria.getLngCli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLngCli(), Client_.lngCli));
            }
            if (criteria.getSocCli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSocCli(), Client_.socCli));
            }
            if (criteria.getEmploi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmploi(), Client_.emploi));
            }
            if (criteria.getSexe() != null) {
                specification = specification.and(buildSpecification(criteria.getSexe(), Client_.sexe));
            }
            if (criteria.getNumCni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumCni(), Client_.numCni));
            }
            if (criteria.getDatCni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatCni(), Client_.datCni));
            }
            if (criteria.getFinCni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinCni(), Client_.finCni));
            }
            if (criteria.getLieuCni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuCni(), Client_.lieuCni));
            }
            if (criteria.getAutoCni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAutoCni(), Client_.autoCni));
            }
            if (criteria.getAdr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdr(), Client_.adr));
            }
            if (criteria.getTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTel(), Client_.tel));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), Client_.ville));
            }
            if (criteria.getSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSite(), Client_.site));
            }
            if (criteria.getLoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoc(), Client_.loc));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Client_.fax));
            }
            if (criteria.getAgnce() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgnce(), Client_.agnce));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), Client_.mail));
            }
            if (criteria.getPays() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPays(), Client_.pays));
            }
            if (criteria.getCompteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompteId(), root -> root.join(Client_.comptes, JoinType.LEFT).get(Compte_.id))
                    );
            }
            if (criteria.getEngagementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEngagementId(),
                            root -> root.join(Client_.engagements, JoinType.LEFT).get(Engagement_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
