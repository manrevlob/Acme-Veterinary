package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bulletin;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Integer> {

	@Query("select b from Bulletin b where b.clinic.id = ?1 and b.isDeleted= false")
	Collection<Bulletin> findAllFromClinic(int clinicId);

	@Query("select b.clinic, b from Bulletin b where b.isDeleted= false")
	Collection<Object[]> findAllBulletins();

}
