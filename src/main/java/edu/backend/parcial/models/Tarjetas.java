package edu.backend.parcial.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TARJETAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarjetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "TITULAR", nullable = false)
    private String titular;

    @Column(name = "LIMITE_CREDITO", nullable = false)
    private Double limiteCredito;

    public Tarjetas(String numero, String titular, Double limiteCredito) {
        this.numero = numero;
        this.titular = titular;
        this.limiteCredito = limiteCredito;
    }


    // Para guardar en el CSV
    public String toCsv() {
        return this.id + "," + this.numero + "," + this.titular + "," + this.limiteCredito;
    }

}