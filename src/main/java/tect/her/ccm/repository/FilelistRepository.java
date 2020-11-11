package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Filelist;

/**
 * Spring Data  repository for the Filelist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilelistRepository extends JpaRepository<Filelist, Long> {}
