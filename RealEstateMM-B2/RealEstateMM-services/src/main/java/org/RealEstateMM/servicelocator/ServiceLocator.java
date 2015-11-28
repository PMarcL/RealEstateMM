package org.RealEstateMM.servicelocator;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private static final ReentrantLock lock = new ReentrantLock();

	private HashMap<Class<?>, Object> services;

	public static ServiceLocator getInstance() {
		if (serviceLocator == null) {
			lock.lock();
			try {
				if (serviceLocator == null) {
					serviceLocator = new ServiceLocator();
				}
			} finally {
				lock.unlock();
			}
		}
		return serviceLocator;
	}

	private ServiceLocator() {
		services = new HashMap<Class<?>, Object>();
	}

	public void clearAllServices() {
		services = new HashMap<Class<?>, Object>();
	}

	public <T> void registerService(Class<T> service, T implementation) {
		if (services.containsKey(service)) {
			throw new ServiceAlreadyRegisteredException(service.toString());
		}
		services.put(service, implementation);
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> service) {
		if (!services.containsKey(service)) {
			throw new ServiceNotFoundException(service.toString());
		}
		return (T) services.get(service);
	}

}
