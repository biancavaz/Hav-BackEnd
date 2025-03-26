package com.hav.hav_imobiliaria.model.entity.Scheduling;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Future
    private LocalDate day;

    @Column
    private LocalTime start_hour;

    @Column
    private LocalTime end_hour;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Realtor realtor;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Customer customer;


    Schedules(LocalDate day, LocalTime start_hour, LocalTime end_hour) {
        if (start_hour.isAfter(LocalTime.of(8, 0))) {
            System.err.println("abaixo da hora mínima");
            return;

        }
        if (end_hour.isBefore(LocalTime.of(20, 0))) {
            System.err.println("acima da hora mínima");
            return;

        }
        if (start_hour.isAfter(end_hour) || start_hour == end_hour) {
            System.err.println("horarios mal formatados");
            return;

        }
        Duration duration = Duration.between(start_hour, end_hour);
        if (!duration.equals(Duration.ofHours(1))) {
            System.err.println("cada agendamento deve ser de uma hora");
            return;
        }

        this.day = day;
        this.start_hour = start_hour;
        this.end_hour = end_hour;


    }

    @Override
    public String toString() {
        return "Schedules{" +
                "id=" + id +
                ", day=" + day +
                ", start_hour=" + start_hour +
                ", end_hour=" + end_hour +
                ", realtor=" + realtor +
                ", customer=" + customer +
                '}';
    }
}
