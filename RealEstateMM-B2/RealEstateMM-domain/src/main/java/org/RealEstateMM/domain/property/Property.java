package org.RealEstateMM.domain.property;

import java.util.Date;

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
	private Date creationDate;
	private Date saleDate;

	private PropertyFeatures emptyFeatures = new PropertyFeatures(0, 0, 0, 0, 0.00, 0, 0.00, "", "");

	public Property(PropertyType type, PropertyAddress address, double price, String ownerUsername,
			PropertyStatus status) {

		this.type = type;
		this.address = address;
		this.price = price;
		this.owner = ownerUsername;
		this.status = status;
		this.features = emptyFeatures;

		this.saleDate = null;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSolDate(Date saleDate) {
		this.saleDate = saleDate;
	}
}
