package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.EmployeEntite;

/**
 * Spring Data  repository for the EmployeEntite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeEntiteRepository extends JpaRepository<EmployeEntite, Long> {}
