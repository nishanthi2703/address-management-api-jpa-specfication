package com.example.address.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressSearchRequest {

    private String status;
    private String source;
    private String streetName;
    private Integer houseNumber;
    private String houseLetter;
}