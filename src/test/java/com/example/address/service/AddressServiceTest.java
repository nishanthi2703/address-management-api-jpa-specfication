package com.example.address.service;

import com.example.address.dto.AddressRequest;
import com.example.address.enums.AddressStatus;
import com.example.address.exception.ResourceNotFoundException;
import com.example.address.model.Address;
import com.example.address.repository.AddressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressRepository = Mockito.mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    void testSaveAddress() {

        AddressRequest request = new AddressRequest();
        request.setStatus("ACTIVE");
        request.setSource("SYSTEM");
        request.setStreetName("Main Street");
        request.setHouseNumber(10);
        request.setHouseLetter("A");

        Address savedAddress = Address.builder()
                .id(1L)
                .status(AddressStatus.ACTIVE)
                .source("SYSTEM")
                .streetName("Main Street")
                .houseNumber(10)
                .houseLetter("A")
                .build();

        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);

        var response = addressService.saveAddress(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testGetAddressById_WhenExists() {

        Address address = Address.builder()
                .id(1L)
                .status(AddressStatus.ACTIVE)
                .build();

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        var response = addressService.getAddressById(1L);

        assertEquals(1L, response.getId());
    }

    @Test
    void testGetAddressById_WhenNotExists() {

        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> addressService.getAddressById(1L));
    }

    @Test
    void testDeleteAddress_WhenExists() {

        when(addressRepository.existsById(1L)).thenReturn(true);
        doNothing().when(addressRepository).deleteById(1L);

        addressService.deleteAddressById(1L);

        verify(addressRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAddress_WhenNotExists() {

        when(addressRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> addressService.deleteAddressById(1L));
    }
}

