package org.RealEstateMM.servicelocator;

@SuppressWarnings("serial")
public class ServiceNotFoundException extends RuntimeException {

	public ServiceNotFoundException(String className) {
		super("The service " + className + " was not registered.");
	}
}
