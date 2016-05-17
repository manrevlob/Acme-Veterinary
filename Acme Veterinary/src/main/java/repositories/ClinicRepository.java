package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
	
	@Query("select c from Clinic c where  c.isDeleted = false")
	Collection<Clinic> findAllNotDeleted();

}
