package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Veterinary;

@Repository
public interface VeterinaryRepository extends
		JpaRepository<Veterinary, Integer> {

	@Query("select v from Veterinary v where v.userAccount.id = ?1")
	Veterinary findByPrincipal(int id);

	@Query("select v from Veterinary v where v.clinic.id = ?1")
	Collection<Veterinary> findByClinic(int clinicId);

	@Query("select v from Veterinary v join v.appointments a where datediff(current_timestamp(), a.day) <= 31) group by v having count(a) >= ALL(select count(a2) from Veterinary v2 join v2.appointments a2 where datediff(current_timestamp(), a2.day) <= 31) group by v2)")
	Collection<Veterinary> vetMoreBusy();
	
	@Query("select v from Veterinary v join v.appointments a where datediff(current_timestamp(), a.day) <= 31) group by v having count(a) <= ALL(select count(a2) from Veterinary v2 join v2.appointments a2 where datediff(current_timestamp(), a2.day) <= 31) group by v2)")
	Collection<Veterinary> vetLessBusy();

}