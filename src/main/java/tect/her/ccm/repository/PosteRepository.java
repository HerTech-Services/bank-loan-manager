package tect.her.ccm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tect.her.ccm.domain.Poste;

/**
 * Spring Data  repository for the Poste entity.
 */
@Repository
public interface PosteRepository extends JpaRepository<Poste, Long> {
    @Query(
        value = "select distinct poste from Poste poste left join fetch poste.parentPostes",
        countQuery = "select count(distinct poste) from Poste poste"
    )
    Page<Poste> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct poste from Poste poste left join fetch poste.parentPostes")
    List<Poste> findAllWithEagerRelationships();

    @Query("select poste from Poste poste left join fetch poste.parentPostes where poste.id =:id")
    Optional<Poste> findOneWithEagerRelationships(@Param("id") Long id);
}
