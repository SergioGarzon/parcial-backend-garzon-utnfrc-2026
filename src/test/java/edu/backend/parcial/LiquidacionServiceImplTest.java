package edu.backend.parcial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import edu.backend.parcial.repositories.LiquidacionesRepository;
import edu.backend.parcial.repositories.TarjetasRepository;
import edu.backend.parcial.repositories.ConsumosRepository;
import edu.backend.parcial.repositories.CotizacionesRepository;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;
import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.servicetest.LiquidacionService;
import edu.backend.parcial.servicetest.LiquidacionesServiceImplement;

public class LiquidacionServiceImplTest {

    private EntityManager em;
    private EntityManagerFactory emf;
    private LiquidacionService liquidacionService;

}
