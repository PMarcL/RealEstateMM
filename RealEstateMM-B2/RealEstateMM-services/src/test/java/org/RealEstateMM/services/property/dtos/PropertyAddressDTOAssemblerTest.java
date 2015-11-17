package org.RealEstateMM.services.property.dtos;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class PropertyAddressDTOAssemblerTest {

	private static final String A_STREET_ADDRESS = "123 fakestreet";
	private static final String A_STREET_CITY = "Lévis";
	private static final String A_STREET_PROVINCE = "Québec";
	private static final String A_ZIP_CODE = "G6G6G6";

	private PropertyAddressDTOAssembler assembler;
	
	private PropertyAddress address;
	private PropertyAddressDTO addressDTO;
	
	@Before
	public void setup(){
		assembler = new PropertyAddressDTOAssembler();
		address = new PropertyAddress(A_STREET_ADDRESS, A_STREET_CITY, A_STREET_PROVINCE, A_ZIP_CODE);
	}
	
	@Test
	public void givenAnAddressDTOWhenBuildingAPropertyAddressThenTheReturnedAddressShouldHaveTheSameValues(){
		addressDTO = assembler.toDTO(address);
		
		assertEquals(address.streetAddress, addressDTO.getStreetAddress());
		assertEquals(address.city, addressDTO.getCity());
		assertEquals(address.province, addressDTO.getProvince());
		assertEquals(address.zipCode, addressDTO.getZipCode());
	}
	
	@Test
	public void givenAnAddressDTOWhenAssembledShouldReturnedAPropertyAddressWithTheSameInformations(){
		addressDTO = assembler.toDTO(address);
		PropertyAddress result = assembler.fromDTO(addressDTO);
		
		assertEquals(addressDTO.getStreetAddress(), result.streetAddress);
		assertEquals(addressDTO.getCity(), result.city);
		assertEquals(addressDTO.getProvince(), result.province);
		assertEquals(addressDTO.getZipCode(), result.zipCode);
	}
}
