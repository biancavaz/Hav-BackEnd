package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customer")
@SuperBuilder
public class Customer extends User {




}
