package com.hav.hav_imobiliaria.model.entity.Users;

import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "realtor")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Realtor extends User {



    @Column(nullable = false, unique = true)
    private String creci;


}
