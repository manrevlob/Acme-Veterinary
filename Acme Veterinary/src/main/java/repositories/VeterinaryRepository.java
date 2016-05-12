package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Veterinary;

@Repository
public interface VeterinaryRepository extends
		JpaRepository<Veterinary, Integer> {

	@Query("select v from Veterinary v where v.userAccount.id = ?1")
	Veterinary findByPrincipal(int id);

}
