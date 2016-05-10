package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query("select m from Actor a join a.messageFolders f join f.messages m where a = ?1 and f = ?2")
	Collection<Message> findByActorAndFolder(Actor actor,
			MessageFolder messageFolder);

	@Query("select m from Actor a join a.messageFolders f join f.messages m where a = ?1")
	Collection<Message> findByActor(Actor actor);

	@Query("select f.messages from MessageFolder f where f = ?1")
	Collection<Message> findByFolder(MessageFolder messageFolder);
}
