package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i order by i.category.name")
	Collection<Item> findByCategory();

	@Query("select i from Item i where i.category.id = ?1")
	Collection<Item> findByCategory(int categoryId);

	@Query("select i from Item i where i.sku like %?1% or i.name like %?1% or i.description like %?1%")
	Collection<Item> findByKeyword(String keyword);

	@Query("select i from Item i where i.isDeleted is false")
	Collection<Item> findAllNotDeleted();

	@Query("select i from Item i where i.sku like %?1%")
	Item findBySKU(String sku);

	@Query("select i.sku from ItemOrder i group by i.sku having count(i.quantity) >= ALL(select count(i2.quantity) from ItemOrder i2 group by i2.sku)")
	Collection<String> mostDemandedItem();

}
