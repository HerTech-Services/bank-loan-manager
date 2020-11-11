package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Entite;

/**
 * Spring Data  repository for the Entite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntiteRepository extends JpaRepository<Entite, Long> {}
