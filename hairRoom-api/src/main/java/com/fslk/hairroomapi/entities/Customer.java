package com.fslk.hairroomapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String phone;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private Hairdresser hairdresser;

    /*@JsonProperty("hairId")
    private void unpackNested(Long hairId) {
        this.hairdresser = new Hairdresser();
        hairdresser.setHairId(hairId);
    }*/
}
