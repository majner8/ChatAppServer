package database.StartConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import database.Chat.UserChats;

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
	
}
