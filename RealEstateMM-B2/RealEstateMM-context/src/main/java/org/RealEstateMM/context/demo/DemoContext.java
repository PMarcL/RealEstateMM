package org.RealEstateMM.context.demo;

import org.RealEstateMM.context.Context;

public class DemoContext implements Context {
	private static final String BASE_URL = "http://localhost:8080/";

	public void apply() {
		// Careful: The Order is important for dependencies
		// TODO
		new RepositoryRegisterer().register();
		new DomainHandlerRegisterer(BASE_URL).register();

		new ServiceRegisterer().register();

		new DataInjector().injectData();
	}

}
