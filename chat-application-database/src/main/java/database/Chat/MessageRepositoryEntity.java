package database.Chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepositoryEntity extends JpaRepository<MessageEntity,String> {

    List<MessageEntity> findByChatId(String chatId, Pageable pageable);
    
    default List<MessageEntity> findByChatId(String chatID,
			 long offSetStart,
			 long offSetEnd){
    	long size=offSetEnd-offSetStart;
    	double startPage=Math.floor(offSetStart/offSetEnd);
    	int difference=(int)(offSetStart%size);
    	
    	Pageable page=PageRequest.of((int)startPage,(int)(size+difference),Sort.by(MessageEntity.JPQLorderName));
    	return this.findByChatId(chatID, page);
    }
    /**Metod return list of first */
    default List<MessageEntity>getUserMessageOverview(long UserID,
    		int offSetBegin,int offSetEnd){
    	
    	return null;
    }
	Optional<MessageEntity> findByChatIDAndOrder(String chatID,long order);
}
