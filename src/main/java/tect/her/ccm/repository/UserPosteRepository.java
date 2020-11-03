package tect.her.ccm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.UserPoste;

/**
 * Spring Data  repository for the UserPoste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPosteRepository extends JpaRepository<UserPoste, Long> {
    @Query("select userPoste from UserPoste userPoste where userPoste.user.login = ?#{principal.username}")
    List<UserPoste> findByUserIsCurrentUser();
}
