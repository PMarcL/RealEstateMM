package org.RealEstateMM.spring;

import static org.junit.Assert.*;
import org.junit.Test;
import org.RealEstateMM.spring.web.controllers.UserConnectionController;

public class UserConnectionControllerTest {
	
	@Test
	public void rendersIndex() {
		assertEquals("index", new UserConnectionController().index());
	}

}
