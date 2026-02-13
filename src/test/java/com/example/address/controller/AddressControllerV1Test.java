package com.example.address.controller;

import com.example.address.dto.AddressRequest;
import com.example.address.dto.AddressResponse;
import com.example.address.service.AddressService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressControllerV1Test {

    private AddressService addressService;
    private AddressControllerV1 controller;

    @BeforeEach
    void setUp() {
        addressService = Mockito.mock(AddressService.class);
        controller = new AddressControllerV1(addressService);
    }

    @Test
    void testSaveAddress() {
        AddressRequest request = new AddressRequest();
        request.setStreetName("Main Street");
        request.setSource("USER");

        AddressResponse response = new AddressResponse();
        response.setId(1L);
        response.setStreetName("Main Street");
        response.setSource("New York");

        when(addressService.saveAddress(request)).thenReturn(response);

        ResponseEntity<AddressResponse> result = controller.saveAddress(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
        assertEquals("Main Street", result.getBody().getStreetName());
    }

    @Test
    void testGetAllAddresses_WhenEmpty() {
        when(addressService.getAllAddresses()).thenReturn(Collections.emptyList());

        ResponseEntity<List<AddressResponse>> result = controller.getAllAddresses();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testDeleteAddress() {
        doNothing().when(addressService).deleteAddressById(1L);

        ResponseEntity<Void> result = controller.deleteAddressById(1L);

        assertEquals(HttpStatus.NO_CONTENT , result.getStatusCode());
        verify(addressService, times(1)).deleteAddressById(1L);
    }
}
