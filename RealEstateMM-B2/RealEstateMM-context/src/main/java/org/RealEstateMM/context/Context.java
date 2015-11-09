package org.RealEstateMM.context;

public abstract class Context {

	public Context() {
	}

	public void apply() {
		registerServiceDependencies();
		registerServices();
		injectData();
	}

	protected abstract void registerServiceDependencies();

	protected abstract void registerServices();

	protected abstract void injectData();
}
