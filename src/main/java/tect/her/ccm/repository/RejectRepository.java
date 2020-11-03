package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Reject;

/**
 * Spring Data  repository for the Reject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RejectRepository extends JpaRepository<Reject, Long> {}
