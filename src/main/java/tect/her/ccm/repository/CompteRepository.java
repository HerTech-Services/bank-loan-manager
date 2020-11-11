package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Compte;

/**
 * Spring Data  repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {}
