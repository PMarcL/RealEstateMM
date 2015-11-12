package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.property.search.PropertyOrderingStrategy;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private final String OWNER = "owner90";
	private final String PSEUDO = "pseudo32";
	private final PropertySearchParameters PARAM = PropertySearchParameters.RECENTLY_UPLOADED_FIRST;

	private PropertyDTOAssembler assembler;
	private PropertyRepository repository;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyAddress address;
	private PropertyFeatures features;
	private PropertySearchFilter filter;
	private PropertyOrderingFactory orderingFactory;
	private PropertyOrderingStrategy orderingStrategy;

	private PropertyService propertyService;

	@Before
	public void setup() {
		assembler = mock(PropertyDTOAssembler.class);
		repository = mock(PropertyRepository.class);
		orderingFactory = mock(PropertyOrderingFactory.class);
		propertyService = new PropertyService(repository, assembler, orderingFactory);

		propertyDTO = mock(PropertyDTO.class);
		property = mock(Property.class);
		address = mock(PropertyAddress.class);
		features = mock(PropertyFeatures.class);
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.of(property));
		configureAssembler();
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenUseAssemblerToBuildProperty() {
		propertyService.uploadProperty(OWNER, propertyDTO);
		verify(assembler).fromDTO(propertyDTO);
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenUpdateCreationDateOfNewProperty() {
		propertyService.uploadProperty(OWNER, propertyDTO);
		verify(property).setCreationDate(anyObject());
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenUseRepositoryToStoreNewProperty() {
		propertyService.uploadProperty(OWNER, propertyDTO);
		verify(repository).add(property);
	}

	@Test
	public void whenGetAllPropertiesThenGetsAllPropertyFromRepository() {
		propertyService.getAllProperties(PSEUDO);
		verify(repository).getAll();
	}

	@Test
	public void whenGetAllPropertiesThenBuildDTOsFromPropertiesWithAssembler() {
		given(repository.getAll()).willReturn(buildPropertiesList());
		propertyService.getAllProperties(PSEUDO);
		verify(assembler).toDTO(property);
	}

	@Test
	public void whenGetAllPropertiesThenReturnsDTOsOfAllProperties() {
		given(repository.getAll()).willReturn(buildPropertiesList());

		List<PropertyDTO> returnedDTOs = propertyService.getAllProperties(PSEUDO);

		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenAssemblesPropertyAddress() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(assembler).getPropertyAddressFromDTO(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenAssemblesPropertyFeatures() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(assembler).getFeaturesFromDTO(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenGetsPropertyWithAddress() {
		given(assembler.getPropertyAddressFromDTO(propertyDTO)).willReturn(address);
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(repository).getPropertyAtAddress(address);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUpdatesPropertyWithNewFeatures() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(property).updateFeatures(features);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUpdatesPropertyInRepository() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(repository).updateProperty(property);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenGetsPropertyWithRepository() {
		propertyService.getPropertiesFromOwner(OWNER);
		verify(repository).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenConvertPropertiesWithAssembler() {
		given(repository.getPropertiesFromOwner(OWNER)).willReturn(buildPropertiesList());

		List<PropertyDTO> returnedDTOs = propertyService.getPropertiesFromOwner(OWNER);

		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerWithoutPropertiesThenReturnsEmptyPropertiesList() {
		given(repository.getPropertiesFromOwner(OWNER)).willReturn(new ArrayList<Property>());
		List<PropertyDTO> returnedDTOs = propertyService.getPropertiesFromOwner(OWNER);
		assertTrue(returnedDTOs.isEmpty());
	}

	@Test
	public void givenASearchFilterWhenGetOrderedPropertiesThenUsesFactoryWithSearchParamToGetOrderingStrategy() {
		configureSearchStrategy();

		propertyService.getOrderedProperties(PSEUDO, filter);

		verify(orderingFactory).getOrderingStrategy(PARAM);
	}

	@Test
	public void givenASearchFilterWhenGetOrderedPropertiesThenReturnsPropertiesOrderedByOrderingStrategy() {
		configureSearchStrategy();

		List<PropertyDTO> returnedDTOs = propertyService.getOrderedProperties(PSEUDO, filter);

		verify(orderingStrategy).getOrderedProperties(repository);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	private void configureSearchStrategy() {
		filter = mock(PropertySearchFilter.class);
		orderingStrategy = mock(PropertyOrderingStrategy.class);

		given(filter.getParsedSearchParameter()).willReturn(PARAM);
		given(orderingFactory.getOrderingStrategy(PARAM)).willReturn(orderingStrategy);
		given(orderingStrategy.getOrderedProperties(repository)).willReturn(buildPropertiesList());
	}

	private void configureAssembler() {
		given(assembler.toDTO(property)).willReturn(propertyDTO);
		given(assembler.fromDTO(propertyDTO)).willReturn(property);
		given(assembler.getFeaturesFromDTO(propertyDTO)).willReturn(features);
		given(assembler.getPropertyAddressFromDTO(propertyDTO)).willReturn(address);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
