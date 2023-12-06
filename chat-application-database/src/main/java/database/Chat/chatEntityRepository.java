package database.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface chatEntityRepository extends JpaRepository<ChatEntity,String>  {

}
