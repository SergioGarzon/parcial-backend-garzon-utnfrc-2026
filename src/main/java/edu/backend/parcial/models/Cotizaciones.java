package edu.backend.parcial.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "COTIZACIONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cotizaciones {

    @Id
    @Column(name = "MONEDA", nullable = false, length = 3)
    private String moneda;

    @Column(name = "TASA_CAMBIO", nullable = false, precision = 10, scale = 4)
    private BigDecimal tasaCambio;
}