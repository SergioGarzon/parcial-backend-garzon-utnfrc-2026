package edu.backend.parcial;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import edu.backend.parcial.models.Consumos;
import edu.backend.parcial.models.Cotizaciones;
import edu.backend.parcial.models.Liquidaciones;
import edu.backend.parcial.models.Tarjetas;
import edu.backend.parcial.services.ConsumosServiceImpl;
import edu.backend.parcial.services.CotizacionesServiceImpl;
import edu.backend.parcial.services.LiquidacionesServiceImpl;
import edu.backend.parcial.services.TarjetasServicesImpl;

import org.h2.tools.Server;


public class App {

    public static void main(String[] args) throws SQLException {       

        
        // Inicia en el navegador web el motor H2
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        
        TarjetasServicesImpl tarjetasServices = new TarjetasServicesImpl();


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


    }

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
    }
}
