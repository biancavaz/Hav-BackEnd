package com.hav.hav_imobiliaria.model.entity.Scheduling;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Data
public class Schedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @FutureInclusive
    private LocalDate day;

    @Column
    private LocalTime start_hour;



    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Realtor realtor;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Property property;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;


    Schedules(LocalDate day, LocalTime start_hour) {
        if (start_hour.isBefore(LocalTime.of(8, 0))) {
            System.err.println("abaixo da hora mínima");
            return;
        }
        if (start_hour.getMinute() != 0 && start_hour.getMinute() != 30) {
            System.err.println("hora deve terminar em 00 ou 30 minutos");
            return;
        }
        if (start_hour.isAfter(LocalTime.of(19, 30))) {
            System.err.println("abaixo da hora mínima");
            return;
        }



        this.day = day;
        this.start_hour = start_hour;

    }

    @Override
    public String toString() {
        return "Schedules{" +
                "id=" + id +
                ", day=" + day +
                ", start_hour=" + start_hour +
                ", realtor=" + realtor +
                ", customer=" + customer +
                '}';
    }
}
