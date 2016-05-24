package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Item i join i.comments c where i.id = ?1 and i.isDeleted= false and c.isDeleted = false group by c")
	Collection<Comment> findAllByItem(int itemId);

	@Query("select c from Veterinary v join v.comments c where v.id = ?1 and c.isDeleted = false group by c")
	Collection<Comment> findAllByVeterinary(int veterinaryId);

	@Query("select c from Bulletin b join b.comments c where b.id = ?1 and c.isDeleted = false and b.isDeleted= false group by c")
	Collection<Comment> findAllByBulletin(int bulletinId);

}
