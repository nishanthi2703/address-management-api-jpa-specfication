package com.example.address.specification;

import java.util.ArrayList;
import java.util.List;

import com.example.address.enums.AddressStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import com.example.address.dto.AddressSearchRequest;
import com.example.address.model.Address;
import jakarta.persistence.criteria.Predicate;

@Slf4j
public class AddressSpecification {

	public static Specification<Address> getSearchSpecification(AddressSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Adding default sorting for consistent results
            if (query.getResultType() != Long.class && query.getOrderList().isEmpty()) {
                query.orderBy(criteriaBuilder.asc(root.get("streetName")));
            }
            
            // status - exact match
            if (StringUtils.hasText(request.getStatus())) {
                try {
                    AddressStatus statusEnum = AddressStatus.valueOf(request.getStatus().toUpperCase());
                    predicates.add(criteriaBuilder.equal(root.get("status"), statusEnum));
                } catch (IllegalArgumentException e) {
                    // If invalid enum, return no results or handle gracefully
                    predicates.add(criteriaBuilder.isTrue(criteriaBuilder.literal(false)));
                    log.warn("Invalid status value: {}", request.getStatus());
                }
            }

            // Source - case insensitive LIKE
            if (StringUtils.hasText(request.getSource())) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("source")), 
                    "%" + request.getSource().toLowerCase() + "%"
                ));
                log.debug("Filtering by source containing: {}", request.getSource());
            }

            // Street name - case insensitive LIKE
            if (StringUtils.hasText(request.getStreetName())) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("streetName")), 
                    "%" + request.getStreetName().toLowerCase() + "%"
                ));
                log.debug("Filtering by street name containing: {}", request.getStreetName());
            }

            // House number - exact match
            if (request.getHouseNumber() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("houseNumber"),
                        request.getHouseNumber()
                ));
                log.debug("Filtering by exact house number: {}", request.getHouseNumber());
            }

            if (request.getHouseLetter() != null) {
                String houseLetter = request.getHouseLetter().trim();

                if (houseLetter.isEmpty()) {
                    // Empty string in request -> find addresses with NO houseLetter
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.isNull(root.get("houseLetter")),
                            criteriaBuilder.equal(root.get("houseLetter"), ""),
                            criteriaBuilder.equal(root.get("houseLetter"), " ")
                    ));
                    log.debug("Filtering addresses with no/empty house letter");
                } else {
                    // Non-empty -> partial match (LIKE)
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("houseLetter")),
                            "%" + houseLetter.toLowerCase() + "%"
                    ));
                    log.debug("Filtering house letter containing: {}", houseLetter);
                }
            }

            log.debug("Total predicates: {}", predicates.size());
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
