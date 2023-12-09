package database.StartConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import database.Chat.UserChats;

@Component
public final class QueryInit implements CommandLineRunner {

	 @PersistenceContext
	    private EntityManager entityManager;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void createNamedQueryGetMessageOverView() {
		String query=
		String.format("With messages(Select %s join"
				+ "()"
				+ ""
				+ ")  ", UserChats.chatIDcolumnName);
	}
	
	SELECT TableB.product_id, TableB.product_name
	FROM TableB
	JOIN (
	    SELECT product_id, MAX(amount) AS MaxAmount
	    FROM TableA
	    GROUP BY product_id
	    ORDER BY MaxAmount DESC
	    LIMIT 1
	) AS MaxSale ON TableB.product_id = MaxSale.product_id

}
