package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecifications {

    public static Specification<Property> withCriteria(PropertySearchCriteria criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por propertyCode (busca parcial com LIKE)
            if (criteria.getPropertyCode() != null && !criteria.getPropertyCode().isEmpty()) {
                predicates.add(builder.like(
                        builder.lower(root.get("propertyCode")), // Converte para minúsculas
                        "%" + criteria.getPropertyCode().toLowerCase() + "%" // Adiciona wildcards
                ));
            }

            // Filtro por nome do proprietário (busca parcial com LIKE)
            if (criteria.getProprietorName() != null && !criteria.getProprietorName().isEmpty()) {
                Join<Property, Proprietor> proprietorJoin = root.join("proprietor");
                predicates.add(builder.like(
                        builder.lower(proprietorJoin.get("name")), // Converte para minúsculas
                        "%" + criteria.getProprietorName().toLowerCase() + "%" // Adiciona wildcards
                ));
            }

            // Filtro por tipo de imóvel (busca parcial com LIKE)
            if (criteria.getPropertyType() != null && !criteria.getPropertyType().isEmpty()) {
                predicates.add(builder.like(
                        builder.lower(root.get("propertyType")), // Converte para minúsculas
                        "%" + criteria.getPropertyType().toLowerCase() + "%" // Adiciona wildcards
                ));
            }

            // Filtro por finalidade (busca parcial com LIKE)
            if (criteria.getPurpose() != null && !criteria.getPurpose().isEmpty()) {
                predicates.add(builder.like(
                        builder.lower(root.get("purpose")), // Converte para minúsculas
                        "%" + criteria.getPurpose().toLowerCase() + "%" // Adiciona wildcards
                ));
            }

            // Filtro por status (busca parcial com LIKE)
            if (criteria.getPropertyStatus() != null && !criteria.getPropertyStatus().isEmpty()) {
                predicates.add(builder.like(
                        builder.lower(root.get("propertyStatus")), // Converte para minúsculas
                        "%" + criteria.getPropertyStatus().toLowerCase() + "%" // Adiciona wildcards
                ));
            }

            // Filtro por intervalo de preço (não usa LIKE, pois é numérico)
            if (criteria.getMinPrice() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }
            if (criteria.getMaxPrice() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }

            // Combina todos os predicados com AND
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
