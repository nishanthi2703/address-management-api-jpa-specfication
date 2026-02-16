package com.example.address.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.address.dto.AddressRequest;
import com.example.address.dto.AddressSearchRequest;
import com.example.address.enums.AddressStatus;
import com.example.address.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.address.dto.AddressResponse;
import com.example.address.model.Address;
import com.example.address.repository.AddressRepository;
import com.example.address.specification.AddressSpecification;
import tools.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

	private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public AddressResponse saveAddress(AddressRequest request) {
        Address address = Address.builder().status(AddressStatus.valueOf(request.getStatus().toUpperCase()))
                .source(request.getSource()).streetName(request.getStreetName())
                .houseNumber(request.getHouseNumber())
                .houseLetter(request.getHouseLetter()).build();
        log.info("Creating address: {}",address);
        return new AddressResponse(addressRepository.save(address));
    }

    @Transactional(readOnly = true)
    public AddressResponse getAddressById(Long id) {
        return new AddressResponse(getAddress(id));
    }

    private Address getAddress(Long id){
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

	@Transactional(readOnly = true)
	public List<AddressResponse> getAllAddresses() {
		return addressRepository.findAll()
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
	}

	@Transactional
	public AddressResponse updateAddress(Long id, AddressRequest request) {
		
		Address existing = getAddress(id);

        Address updatedAddress = Address.builder()
                .id(existing.getId())
                .status(parseStatus(request.getStatus()))
                .source(request.getSource())
                .streetName(request.getStreetName())
                .houseNumber(request.getHouseNumber())
                .houseLetter(request.getHouseLetter())
                .build();

        Address savedAddress = addressRepository.save(updatedAddress);
        log.info("Updated address with id: {}", id);
        return new AddressResponse(savedAddress);
    }

    private AddressStatus parseStatus(String status) {
        if (status == null) return null;
        try {
            return AddressStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

	public void deleteAddressById(Long id) {
		if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
        log.info("Deleted address with id: {}", id);
    }
	
	@Transactional(readOnly = true)
    public List<AddressResponse> searchAddresses(AddressSearchRequest searchRequest) {
        log.info("Searching address with criteria: {}",objectMapper.writeValueAsString(searchRequest));
        Specification<Address> spec = AddressSpecification.getSearchSpecification(searchRequest);
        return addressRepository.findAll(spec)
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
    }

}
