package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.TypeEntite;

/**
 * Spring Data  repository for the TypeEntite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeEntiteRepository extends JpaRepository<TypeEntite, Long> {}
