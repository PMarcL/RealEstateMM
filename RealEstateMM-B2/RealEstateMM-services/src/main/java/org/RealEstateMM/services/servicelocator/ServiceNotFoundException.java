package org.RealEstateMM.services.servicelocator;

@SuppressWarnings("serial")
public class ServiceNotFoundException extends RuntimeException {

	public ServiceNotFoundException(String className) {
		super("The service " + " was not registered.");
	}
}
