package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.RejectType;

/**
 * Spring Data  repository for the RejectType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RejectTypeRepository extends JpaRepository<RejectType, Long> {}
