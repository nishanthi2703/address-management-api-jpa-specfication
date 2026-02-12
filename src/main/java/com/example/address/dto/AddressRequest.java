package com.example.address.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotBlank(message="Status is required")
    private String status;

    @NotBlank(message="Source is required")
    private String source;

    @NotBlank(message="StreetName is required")
    private String streetName;

    @NotNull(message="HouseNumber is required")
    @Positive(message = "House number must be positive")
    private Integer houseNumber;

    private String houseLetter;
}
