package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Veterinary;

@Repository
public interface VeterinaryRepository extends
		JpaRepository<Veterinary, Integer> {

}
