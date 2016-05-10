package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.MessageFolder;

@Repository
public interface MessageFolderRepository extends
		JpaRepository<MessageFolder, Integer> {

	@Query("select m from Actor a join a.messageFolders m where a.id = ?1")
	Collection<MessageFolder> findByActor(int actorID);

	@Query("select m from Actor a join a.messageFolders m where a = ?1 and m.name = ?2")
	MessageFolder findByActorAndNameFolder(Actor actor, String nameFolder);

}
