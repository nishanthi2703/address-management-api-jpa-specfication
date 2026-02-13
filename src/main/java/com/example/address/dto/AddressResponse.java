package com.example.address.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.example.address.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

	private Long id;
	private String status;
	private String source;
	private String streetName;
	private Integer houseNumber;
	private String houseLetter;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;

	private String formattedCreatedAt;
	private String formattedUpdatedAt;

	public AddressResponse(Address address) {
		this.id = address.getId();
		this.status = String.valueOf(address.getStatus());
		this.source = address.getSource();
		this.streetName = address.getStreetName();
		this.houseNumber = address.getHouseNumber();
		this.houseLetter = address.getHouseLetter();
		this.createdAt = address.getCreatedate();
		this.updatedAt = address.getUpdatedAt();

		// Format dates for human-readable format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (address.getCreatedate() != null) {
			this.formattedCreatedAt = address.getCreatedate().format(formatter);
		}
		if (address.getUpdatedAt() != null) {
			this.formattedUpdatedAt = address.getUpdatedAt().format(formatter);
		}
	}
}