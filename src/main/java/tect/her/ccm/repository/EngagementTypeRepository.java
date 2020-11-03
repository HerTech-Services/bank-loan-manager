package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.EngagementType;

/**
 * Spring Data  repository for the EngagementType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EngagementTypeRepository extends JpaRepository<EngagementType, Long> {}
