package com.example.address.model;

import java.time.LocalDateTime;

import com.example.address.enums.AddressStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * An entity class represents a table in a relational database
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Status is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AddressStatus status;
	
	private String source;

	@NotBlank(message = "Street name is required")
	@Column(name = "street_name", nullable = false, length = 255)
	private String streetName;

	@Column(name = "house_number", nullable = false)
	private Integer houseNumber;

	@Column(name = "house_letter", length = 10)
	private String houseLetter;
	
	@CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
	private LocalDateTime createdate;
	
	@LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime updatedAt;


}
