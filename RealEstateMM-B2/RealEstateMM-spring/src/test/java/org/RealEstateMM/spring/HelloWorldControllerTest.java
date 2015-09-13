package org.RealEstateMM.spring;

import static org.junit.Assert.*;
import org.junit.Test;
import org.RealEstateMM.spring.web.controllers.HelloWorldController;

public class HelloWorldControllerTest {
	
	@Test
	public void rendersIndex() {
		assertEquals("index", new HelloWorldController().index());
	}

}
