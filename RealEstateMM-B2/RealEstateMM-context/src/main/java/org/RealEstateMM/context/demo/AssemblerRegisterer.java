package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.user.dtos.UserAssembler;

public class AssemblerRegisterer {

	private UserAssembler userAssembler;
	private PropertyAssembler propertyAssembler;

	public AssemblerRegisterer() {
		userAssembler = new UserAssembler(new UserRoleFactory());
		propertyAssembler = new PropertyAssembler();
	}

	public void register() {
		ServiceLocator.getInstance().registerService(UserAssembler.class, userAssembler);
		ServiceLocator.getInstance().registerService(PropertyAssembler.class, propertyAssembler);
	}

}
