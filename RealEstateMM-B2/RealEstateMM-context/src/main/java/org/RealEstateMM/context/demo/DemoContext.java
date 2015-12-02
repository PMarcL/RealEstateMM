package org.RealEstateMM.context.demo;

import org.RealEstateMM.context.Context;

public class DemoContext extends Context {
	private static final String BASE_URL = "http://localhost:8080/";

	@Override
	protected void registerRepositories() {
		new RepositoryRegisterer().register();
	}

	@Override
	protected void registerDomainHandlers() {
		new DomainHandlerRegisterer(BASE_URL).register();
	}

	@Override
	protected void registerServices() {
		new ServiceRegisterer().register();
	}

	@Override
	protected void injectData() {
		new DataInjector().injectData();
	}
}
