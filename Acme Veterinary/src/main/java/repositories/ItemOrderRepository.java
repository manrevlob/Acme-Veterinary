package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ItemOrder;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Integer> {

}
