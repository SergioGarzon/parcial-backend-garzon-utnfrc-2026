package edu.backend.parcial.excepciones;

public class TarjetaInexistenteException extends RuntimeException {

    public TarjetaInexistenteException(String nroTarjeta) {
        super(String.format("La tarjeta %s no existe.", nroTarjeta));
    }

    public TarjetaInexistenteException(long idTarjeta) {
        super(String.format("La tarjeta %d no existe.", idTarjeta));
    }

}
