package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Downgrading;

/**
 * Spring Data  repository for the Downgrading entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DowngradingRepository extends JpaRepository<Downgrading, Long> {}
