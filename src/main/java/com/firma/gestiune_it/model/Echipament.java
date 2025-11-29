package com.firma.gestiune_it.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "echipamente")
public class Echipament {

    @Id // cheie primara care nu e auto-increment (o introducem noi, ex: IT-001)
    @Column(name = "numar_inventar")
    private String numarInventar;

    private String brand;
    private String model;
    private String status;

    @Column(name = "an_achizitie")
    private Integer anAchizitie;

    private String procesor;

    @Column(name = "tip_echipament")
    private String tipEchipament;

    private Integer memorieRam;
    private Integer capacitateStocare;
    private String sistemOperare;
    private Double pret;

    // ManyToOne = Multe echipamente pot fi adaugate de un singur utilizator
    @ManyToOne
    @JoinColumn(name = "id_utilizator", nullable = false)
    private Utilizator utilizator;
}