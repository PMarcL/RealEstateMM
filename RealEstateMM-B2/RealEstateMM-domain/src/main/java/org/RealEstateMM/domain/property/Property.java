package org.RealEstateMM.domain.property;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class Property {

	private PropertyType type;
	private PropertyAddress address;
	private double price;
	private String owner;
	private PropertyStatus status;
	private PropertyFeatures features;
	
	private PropertyFeatures emptyFeatures = new PropertyFeatures(0, 0, 0, 0, 0.00, 0, 0.00, "", ""); //TODO Valider la pertinence de ça.

	public Property(PropertyType type, PropertyAddress address, double price, String ownerUsername,
			PropertyStatus status) {

		this.type = type;
		this.address = address;
		this.price = price;
		this.owner = ownerUsername;
		this.status = status;
		this.features = emptyFeatures;
	}

	public PropertyType getType() {
		return type;
	}

	public PropertyAddress getAddress() {
		return address;
	}

	public double getPrice() {
		return price;
	}

	public String getOwner() {
		return owner;
	}

	public PropertyStatus getPropertyStatus() {
		return status;
	}

	public String getZipCode() {
		return address.zipCode;
	}

	public void updateFeatures(PropertyFeatures newFeatures) {
		features = newFeatures;
	}

	public PropertyFeatures getFeatures() {
		return features;
	}

	public boolean isOwnedBy(String owner) {
		return this.owner.equals(owner);
	}
}
