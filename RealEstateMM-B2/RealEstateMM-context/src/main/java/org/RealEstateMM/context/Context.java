package org.RealEstateMM.context;

public abstract class Context {
	public final String BASE_URL;

	public Context(String baseUrl) {
		BASE_URL = baseUrl;
	}

	public void apply() {
		registerServices();
		injectData();
	}

	protected abstract void registerServices();

	protected abstract void injectData();
}
