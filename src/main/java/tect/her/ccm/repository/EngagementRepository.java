package tect.her.ccm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Engagement;

/**
 * Spring Data  repository for the Engagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long>, JpaSpecificationExecutor<Engagement> {
    @Query("select engagement from Engagement engagement where engagement.createdBy.login = ?#{principal.username}")
    List<Engagement> findByCreatedByIsCurrentUser();
}
