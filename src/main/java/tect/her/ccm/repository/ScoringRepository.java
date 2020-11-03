package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Scoring;

/**
 * Spring Data  repository for the Scoring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoringRepository extends JpaRepository<Scoring, Long> {}
