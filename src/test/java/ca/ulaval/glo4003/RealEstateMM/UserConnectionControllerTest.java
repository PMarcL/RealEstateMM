package ca.ulaval.glo4003.RealEstateMM;

import static org.junit.Assert.*;
import org.junit.Test;
import ca.ulaval.glo4003.RealEstateMM.web.controllers.UserConnectionController;

public class UserConnectionControllerTest {
	
	@Test
	public void rendersIndex() {
		assertEquals("index", new UserConnectionController().index());
	}

}
