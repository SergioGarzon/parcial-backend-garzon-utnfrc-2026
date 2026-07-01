package edu.backend.parcial;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.models.Consumo;
import edu.backend.parcial.models.Cotizacion;
import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.repositories.CotizacionesRepository;
import edu.backend.parcial.services.ConsumosServiceImpl;
import edu.backend.parcial.services.LiquidacionesServiceImpl;
import edu.backend.parcial.services.TarjetasServicesImpl;
import edu.backend.parcial.models.Tarjeta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.h2.tools.Server;


public class App {

    public static void main(String[] args) throws SQLException {

        
        // Inicia en el navegador web el motor H2
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parcial-garzon");
        EntityManager em = emf.createEntityManager();

        TarjetasServicesImpl tarjetasServices = new TarjetasServicesImpl(em);
        ConsumosServiceImpl consumosService = new ConsumosServiceImpl(em);
        LiquidacionesServiceImpl liquidacionesService = new LiquidacionesServiceImpl(em);
        CotizacionesRepository cotizacionesRepository = new CotizacionesRepository(em);

        /*
        for (Tarjeta t: tarjetasServices.getAll()) {
            System.out.println(t.toString());
        }

        System.out.println("\nBuscamos tarjeta por ID = 1");
        System.out.println(tarjetasServices.getById(1l));

        System.out.println("\n\nBuscamos tarjeta por Numero = 4500123412340001");
        System.out.println(tarjetasServices.getByNumero("4500123412340001"));

        System.out.println("\n\nAgregamos una tarjeta");
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumero("45646688878788");
        tarjeta.setTitular("erere");
        tarjeta.setLimiteCredito(545.2d);

        System.out.println((tarjetasServices.addTarjeta(tarjeta)) ? "AGREGADA" : "NO AGREGADA");

        System.out.println("\n\nBuscamos Consumo de Tarjeta");
        List<Consumo> listaConsumos = consumosService.getConsumosByTarjetaAnioMes("5555", 2026, 5);

        listaConsumos.stream().forEach(System.out::println);

        */

        Liquidacion liquidaciones = liquidacionesService.getLiquidacion(1l, 2026, 5);

        List<Cotizacion> listaCotizaciones = cotizacionesRepository.getAll();

        List<Consumo> consumosLista = consumosService.getConsumosByTarjetaAnioMes(liquidaciones.getTarjeta().getNumero(), 2026, 5);

        double descuento = 0d;
        double descuentoAplicado = 0d;
        double impuesto = 0d;
        double impuestoExtraordinario = 0d;
        double consumos = 0d;
        double auxiliarConsumo = 0d;

        for (Consumo con: consumosLista) {
            if(con.getMoneda().equalsIgnoreCase("ARS")) {
                switch (con.getRubro()) {
                    case "COMBUSTIBLE":
                        //COMBUSTIBLE: 15% con tope de ARS 750 por transacción

                        descuento = (con.getMonto() * 0.15);

                        if (descuento <= 750)
                            descuentoAplicado += descuento;
                        else
                            descuentoAplicado += 750;
                        break;
                    case "SUPERMERCADO":
                        //SUPERMERCADOS: 20% con tope de ARS 3000 por transacción
                        descuento = (con.getMonto() * 0.2);

                        if (descuento <= 3000)
                            descuentoAplicado += descuento;
                        else
                            descuentoAplicado += 3000;
                        break;
                    case "RESTAURANTES":
                        //RESTAURANTES: 25% entre los días 10 y 15 de cada mes (ambos incluídos)
                        descuento = (con.getMonto() * 0.25);

                        if(con.getDia() >= 10 && con.getDia() <= 15) {
                            descuentoAplicado += descuento;
                        }
                        break;
                }

                if(con.getRubro().equalsIgnoreCase("OTROS")) {
                    impuesto += con.getMonto() * (0.12 + 0.21);
                } else {
                    impuesto += con.getMonto() * 0.21;
                }

                consumos += con.getMonto();
            }

            /*Cotizacion(moneda=ARS, tasaCambio=1.0)
                Cotizacion(moneda=USD, tasaCambio=1550.0)
                Cotizacion(moneda=EUR, tasaCambio=1680.0)
                Cotizacion(moneda=BRL, tasaCambio=280.0)
                Cotizacion(moneda=CLP, tasaCambio=1.45)*/

            switch (con.getMoneda()) {
                case "USD":
                    auxiliarConsumo = (con.getMonto() * 1550);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
                case "EUR":
                    auxiliarConsumo = (con.getMonto() * 1680);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
                case "BRL":
                    auxiliarConsumo = (con.getMonto() * 280);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
                case "CLP":
                    auxiliarConsumo = (con.getMonto() * 1.45);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
            }


        }

        LiquidacionDTO liquidacionDTO = new LiquidacionDTO();
        liquidacionDTO.setId(liquidaciones.getId());
        liquidacionDTO.setNumeroTarjeta(liquidaciones.getTarjeta().getNumero());
        liquidacionDTO.setTitular(liquidaciones.getTarjeta().getTitular());
        liquidacionDTO.setMes(liquidaciones.getMes());
        liquidacionDTO.setAnio(liquidaciones.getAnio());
        liquidacionDTO.setTotalAPagar(consumos - descuentoAplicado + impuesto + impuestoExtraordinario);
        liquidacionDTO.setTotalConsumos(consumos);
        liquidacionDTO.setTotalImpuestos(impuesto + impuestoExtraordinario);
        liquidacionDTO.setTotalDescuentos(descuentoAplicado);

        System.out.println(liquidacionDTO.toString());

        /*

        List<Tarjeta> tarjetas = tarjetasServices.getTarjetasSinLiquidacion(2026, 5);
        List<String> listaNumerosTarjetas = new LinkedList<>();

        for (Tarjeta t: tarjetas) {
            listaNumerosTarjetas.add(t.getNumero());
        }

        for (int i = 0; i < listaNumerosTarjetas.size() ; i++) {
            System.out.println(listaNumerosTarjetas.get(i));
        }
        */


        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }

        return;

    }

        /*
        TarjetasServicesImpl tarjetasServices = new TarjetasServicesImpl();

        LiquidacionesServiceImpl liquidacionesService = new LiquidacionesServiceImpl();*/

        /*
        System.out.println("DATOS DE LA TARJETA DESDE EL CSV PARA LA BASE DE DATOS");

        String ruta = System.getProperty("user.dir");

        Path archivo = Path.of(ruta + "/src/main/resources/files/tarjetas.csv");

        int contador = 0;
        String linea = "";

        try (BufferedReader br = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {

            while ((linea = br.readLine()) != null) {

                if(contador != 0) {
                    String[] columna = linea.split(",");

                    if(columna.length == 4) {
                        //tarjeta.setId(Long.parseLong(columna[0]));

                        Tarjetas tarjeta = new Tarjetas(columna[1],columna[2],Double.parseDouble(columna[3]));

                        tarjetasServices.add(tarjeta);
                    }
                }

                contador++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        List<Tarjetas> tarjetas = tarjetasServices.getAll();

        System.out.println("\n\nMOSTRAMOS LOS DATOS DE LA TARJETA DESDE LA BASE DE DATOS");

        for (Tarjetas tar : tarjetas) {
            System.out.println(tar);
        }

        guardarTarjetas(ruta, tarjetas);


         */

        /*
        List<Liquidaciones> l = liquidacionesService.getAll();

        for (Liquidaciones li: l) {
            System.out.println(li.toString());
        }
        */


    //LiquidacionDTO l = liquidacionesService.obtenerYMostrar();

    //System.out.println(l.toString());

/*


/*
    private static void guardarTarjetas(String ruta, List<Tarjetas> tarjetta) {
        try (FileWriter fw = new FileWriter(ruta + "/src/main/resources/files/tarjetasnuevo.csv");

            PrintWriter pw = new PrintWriter(fw)) {

            pw.println("ID,NUMERO,TITULAR,LIMITE_CREDITO");

            for (Tarjetas tarje : tarjetta) {
                pw.println(tarje.toCsv());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
