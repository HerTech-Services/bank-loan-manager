package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.UserExtra;

/**
 * Spring Data  repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {}
