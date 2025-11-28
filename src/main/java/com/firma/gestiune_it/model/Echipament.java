package com.firma.gestiune_it.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "echipamente")
public class Echipament {

    @Id // Cheie primara care NU e auto-increment (o introducem noi, ex: IT-001)
    @Column(name = "numar_inventar")
    private String numarInventar;

    private String brand; // ex: Dell
    private String model; // ex: Latitude 5420
    private String status; // ex: Activ, Defect (in loc de culoare)

    @Column(name = "an_achizitie")
    private Integer anAchizitie; // in loc de an fabricatie

    private String procesor; // in loc de capacitate cilindrica

    @Column(name = "tip_echipament")
    private String tipEchipament; // ex: Laptop, Server (in loc de combustibil) - FILTRU

    private Integer memorieRam; // in loc de putere
    private Integer capacitateStocare; // in loc de cuplu
    private String sistemOperare; // in loc de volum portbagaj
    private Double pret;

    // Relatia cu Utilizatorul (Cine a adaugat echipamentul)
    // ManyToOne = Multe echipamente pot fi adaugate de un singur utilizator
    @ManyToOne
    @JoinColumn(name = "id_utilizator", nullable = false)
    private Utilizator utilizator;
}