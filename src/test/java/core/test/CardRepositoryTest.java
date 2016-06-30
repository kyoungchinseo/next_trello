package core.test;

import org.junit.Test;
import org.nhnnext.domain.Card;
import org.nhnnext.domain.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class CardRepositoryTest extends IntegrationTests {
	@Autowired 
	private CardRepository cardRepository;
	
	@Test
	public void crud() throws Exception {
		cardRepository.save(new Card("new","new"));
		
	}
	
}
