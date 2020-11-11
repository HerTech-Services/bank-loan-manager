package tect.her.ccm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.FilelistEgagementType;

/**
 * Spring Data  repository for the FilelistEgagementType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilelistEgagementTypeRepository extends JpaRepository<FilelistEgagementType, Long> {}
