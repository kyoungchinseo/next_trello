package resttemplate.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nhnnext.domain.Board;
import org.nhnnext.domain.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class BoardTest extends WebIntegrationTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardTest.class);
	
	@Autowired
	private BoardRepository boardRepository;
	private RestTemplate resttemplate;

	@Before
	public void setup() {
		resttemplate = new RestTemplate();
	}
	
	@Test
	public void create() {
		Board board = new Board();
		
		
	}

}
