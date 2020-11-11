package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.DowngradingStep;

/**
 * Spring Data  repository for the DowngradingStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DowngradingStepRepository extends JpaRepository<DowngradingStep, Long> {}
