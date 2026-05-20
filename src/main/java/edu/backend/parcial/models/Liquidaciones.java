package edu.backend.parcial.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LIQUIDACIONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Liquidaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // FK -> TARJETAS(ID)
    @ManyToOne
    @JoinColumn(name = "ID_TARJETA", nullable = false)
    private Tarjetas tarjeta;

    @Column(name = "MES", nullable = false)
    private Integer mes;

    @Column(name = "ANIO", nullable = false)
    private Integer anio;

    @Column(name = "TOTAL_A_PAGAR", nullable = false)
    private Double totalAPagar;

    @Column(name = "TOTAL_CONSUMOS", nullable = false)
    private Double totalConsumos;

    @Column(name = "TOTAL_IMPUESTOS", nullable = false)
    private Double totalImpuestos;

    @Column(name = "TOTAL_DESCUENTOS", nullable = false)
    private Double totalDescuentos;
}