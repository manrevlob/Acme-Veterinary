package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select i.comments from Item i where i.id = ?1 and i.isDeleted = false")
	Collection<Comment> findAllByItem(int itemId);

	@Query("select v.comments from Veterinary v where v.id = ?1")
	Collection<Comment> findAllByVeterinary(int veterinaryId);

}
