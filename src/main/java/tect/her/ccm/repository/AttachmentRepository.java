package tect.her.ccm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Attachment;

/**
 * Spring Data  repository for the Attachment entity.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("select attachment from Attachment attachment where attachment.user.login = ?#{principal.username}")
    List<Attachment> findByUserIsCurrentUser();

    @Query("select attachment from Attachment attachment where attachment.updatedBy.login = ?#{principal.username}")
    List<Attachment> findByUpdatedByIsCurrentUser();

    @Query(
        value = "select distinct attachment from Attachment attachment left join fetch attachment.origins",
        countQuery = "select count(distinct attachment) from Attachment attachment"
    )
    Page<Attachment> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct attachment from Attachment attachment left join fetch attachment.origins")
    List<Attachment> findAllWithEagerRelationships();

    @Query("select attachment from Attachment attachment left join fetch attachment.origins where attachment.id =:id")
    Optional<Attachment> findOneWithEagerRelationships(@Param("id") Long id);
}
