package org.RealEstateMM.context;

public abstract class Context {

	public void apply() {
		registerRepositories();
		registerDomainHandlers();
		registerServices();
		injectData();
	}

	protected abstract void registerRepositories();

	protected abstract void registerDomainHandlers();

	protected abstract void registerServices();

	protected abstract void injectData();

}
