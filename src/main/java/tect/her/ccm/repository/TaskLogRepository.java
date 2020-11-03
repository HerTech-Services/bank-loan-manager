package tect.her.ccm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.TaskLog;

/**
 * Spring Data  repository for the TaskLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
    @Query("select taskLog from TaskLog taskLog where taskLog.user.login = ?#{principal.username}")
    List<TaskLog> findByUserIsCurrentUser();
}
