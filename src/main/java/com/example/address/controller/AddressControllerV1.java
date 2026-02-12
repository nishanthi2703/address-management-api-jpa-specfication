package com.example.address.controller;

import java.util.List;

import com.example.address.dto.AddressRequest;
import com.example.address.dto.AddressSearchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.address.dto.AddressResponse;
import com.example.address.service.AddressService;

@CrossOrigin(origins = "${app.cors.allowed-origins}")
@RestController
@RequestMapping("/addresses/v1")
@RequiredArgsConstructor
@Slf4j
public class AddressControllerV1 {

    private final AddressService addressService;

    /**
     * Create a new address.
     *
     * @param request address details
     * @return created address with 201 status and location header
     */
    @PostMapping()
    public ResponseEntity<AddressResponse> saveAddress(@Valid @RequestBody AddressRequest request) {
        log.info("Saving new address: {}", request);
        AddressResponse response = addressService.saveAddress(request);
        log.info("Address saved successfully with id: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get address by ID.
     *
     * @param id address identifier
     * @return address details
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        log.debug("Fetching address with id: {}", id);
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    /**
     * Get all addresses.
     *
     * @return list of all addresses (204 if none found)
     */
    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses(){
        log.debug("Fetching all addresses");
        List<AddressResponse> addresses = addressService.getAllAddresses();

        if (addresses.isEmpty()) {
            log.debug("No addresses found");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(addresses);
    }

    /**
     * Update an existing address.
     *
     * @param id address identifier
     * @param request updated address details
     * @return updated address
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {
        log.info("Updating address with id: {}", id);
        AddressResponse response = addressService.updateAddress(id, request);
        log.info("Address updated successfully with id: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an address.
     *
     * @param id address identifier
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        log.info("Deleting address with id: {}", id);
        addressService.deleteAddressById(id);
        log.info("Address deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search addresses by criteria.
     *
     * @param searchRequest search parameters
     * @return matching addresses (204 if none found)
     */
    @PostMapping("/search")
    public ResponseEntity<List<AddressResponse>> searchAddresses(
            @Valid @RequestBody AddressSearchRequest searchRequest) {
        log.debug("Searching addresses with criteria: {}", searchRequest);
        List<AddressResponse> responses = addressService.searchAddresses(searchRequest);

        if (responses.isEmpty()) {
            log.debug("No addresses found matching search criteria");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }

}
