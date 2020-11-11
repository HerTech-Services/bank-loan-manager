package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Parameters;

/**
 * Spring Data  repository for the Parameters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametersRepository extends JpaRepository<Parameters, Long> {}
