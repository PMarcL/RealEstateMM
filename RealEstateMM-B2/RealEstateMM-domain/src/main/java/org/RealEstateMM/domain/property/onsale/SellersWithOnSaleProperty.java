package org.RealEstateMM.domain.property.onsale;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyStatus;

public class SellersWithOnSaleProperty {

	private PropertyRepository repository;

	public SellersWithOnSaleProperty(PropertyRepository repository) {
		this.repository = repository;
	}

	public int findNumberOfSellerWithOnSaleProperty() {
		return (int) repository.getAll().stream().filter(p -> p.getStatus() == PropertyStatus.ON_SALE)
				.map(p -> p.getOwner()).distinct().count();
	}
}
