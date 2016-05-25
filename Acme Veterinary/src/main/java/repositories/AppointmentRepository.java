package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Appointment;
import domain.Customer;
import domain.Veterinary;

@Repository
public interface AppointmentRepository extends
		JpaRepository<Appointment, Integer> {

	@Query("select a from Appointment a where a.veterinary = ?1 ")
	Collection<Appointment> findAllOwn(Veterinary veterinary);
	
	@Query("select a from Appointment a where a.veterinary = ?1 and a.day >= current_date() order by day, startTime")
	Collection<Appointment> findAllOwnNoExpired(Veterinary veterinary);
	
	@Query("select a from Appointment a where a.veterinary = ?4 and a.day = ?1 and a.startTime = ?2 and a.endTime = ?3") 	
	Collection<Appointment> getVeterinaryIsBooked(Date day, String startTime, String endTime, Veterinary veterinary);

	@Query("select a from Appointment a where a.pet.customer = ?1 and a.day >= current_date() order by day, startTime")
	Collection<Appointment> findByPrincipalNoExpired(Customer principal);
	
	@Query("select a from Appointment a where a.pet.customer = ?1 and a.day <= current_date() order by day, startTime")
	Collection<Appointment> findByPrincipalExpired(Customer principal);

}
