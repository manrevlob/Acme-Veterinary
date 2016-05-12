package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Appointment;
import domain.Veterinary;

@Repository
public interface AppointmentRepository extends
		JpaRepository<Appointment, Integer> {

	@Query("select a from Appointment a where a.veterinary = ?1 ")
	Collection<Appointment> findAllOwn(Veterinary veterinary);

}
