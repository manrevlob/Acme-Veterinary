package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
	
	@Query("select c from Clinic c where  c.isDeleted != true ")
	Collection<Clinic> findAllNotDeleted();

	@Query("select v.clinic, count(a) from Appointment a join a.veterinary v")
	Collection<Object[]> clinicWithNumAppoint();

	@Query("select c, CAST((b.size / (select count(b) from Bulletin b))as float) from Clinic c join c.bulletins b group by c")
	Collection<Object[]> avgBulletinsByClinic();

}
