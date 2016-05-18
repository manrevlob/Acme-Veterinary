package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bulletin;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Integer> {

	@Query("select c.bulletins from Clinic c where c.id = ?1")
	Collection<Bulletin> findAllFromClinic(int clinicId);

	@Query("select c, c.bulletins from Clinic c")
	Collection<Object[]> findAllBulletins();

}
