package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

	@Query("select p from Pet p where p.customer = ?1")
	Collection<Pet> findAllOwner(Customer customer);

}
