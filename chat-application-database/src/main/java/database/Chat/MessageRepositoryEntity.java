package database.Chat;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepositoryEntity extends JpaRepository<MessageEntity,String> {

	
}
