package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.TasktypeStatusAction;

/**
 * Spring Data  repository for the TasktypeStatusAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TasktypeStatusActionRepository extends JpaRepository<TasktypeStatusAction, Long> {}
