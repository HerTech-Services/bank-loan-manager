package tect.her.ccm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Notes;

/**
 * Spring Data  repository for the Notes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
    @Query("select notes from Notes notes where notes.user.login = ?#{principal.username}")
    List<Notes> findByUserIsCurrentUser();
}
