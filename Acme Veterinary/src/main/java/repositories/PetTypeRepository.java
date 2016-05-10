package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PetType;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {

}
