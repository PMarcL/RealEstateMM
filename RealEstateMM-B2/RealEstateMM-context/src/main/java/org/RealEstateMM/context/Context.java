package org.RealEstateMM.context;

public abstract class Context {

	public Context() {
	}

	public void apply() {
		registerServices();
		injectData();
	}

	protected abstract void registerServices();

	protected abstract void injectData();
}
