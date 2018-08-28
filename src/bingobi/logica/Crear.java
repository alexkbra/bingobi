/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.logica;

import bingobi.logica.bean.BingoBean;
import bingobi.persistence.controllers.CartonesJpaController;
import bingobi.persistence.controllers.HorariosJpaController;
import bingobi.persistence.controllers.ProgramacionesJpaController;
import bingobi.persistence.controllers.SedesJpaController;
import bingobi.persistence.controllers.TablasJpaController;
import bingobi.persistence.controllers.TipoBingosJpaController;
import bingobi.persistence.entities.Cartones;
import bingobi.persistence.entities.Programaciones;
import bingobi.persistence.entities.Tablas;
import bingobi.persistence.entities.TipoBingos;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class Crear {
    
    public void crearTablasBingo(EntityManagerFactory emf){
        CartonesJpaController cartoneController = new  CartonesJpaController(emf);
        TablasJpaController tablaController = new TablasJpaController(emf);
        Integer b=1,i=18,n=36,g=54,o=72;
        BingoBean bean = new BingoBean(b, i, n, g, o);
        for (int j = 1; j <= 72; j++) {
            Tablas tabla = new Tablas();
            tabla.setNumero(String.valueOf(j));
            tablaController.create(tabla);
            for (int k = 0; k < 5; k++) {
                Cartones carton = new Cartones();
                carton.setB(b.toString());
                carton.setI(i.toString());
                if(k == 2){
                    carton.setN(" ");
                }else{
                    carton.setN(n.toString());
                }
                carton.setG(g.toString());
                carton.setO(o.toString());
                carton.setIdTabla(tabla);
                cartoneController.create(carton);
                b++;i++;g++;o++;
                if(k != 2){
                    n++;
                }
                if(b > 17){
                    b = 1;
                }
                if(i > 35){
                    i = 18;
                }
                if(n > 53){
                    n = 36;
                }
                if(g > 71){
                    g = 54;
                }
                if(o > 90){
                    o = 72;
                }
            }
        }
    }
    
    public void crearProgramacion(EntityManagerFactory emf) throws Exception{
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SedesJpaController sedes = new SedesJpaController(emf);
        HorariosJpaController horarios = new HorariosJpaController(emf);
        TipoBingosJpaController tipoBingo = new TipoBingosJpaController(emf);
        ProgramacionesJpaController programaciones = new ProgramacionesJpaController(emf);
        Integer yearStart = 0, yearEnd = 0;
        System.out.println("Año init");
        yearStart = sc.nextInt();
        System.out.println("Año fin");
        yearEnd = sc.nextInt();
        if (yearStart > yearEnd ) throw new Exception("Año final mayor que el inical.");
        Date dateBegin = formatter.parse(yearStart.toString()+"-01-01");
        Date dateEnd =  formatter.parse(yearEnd.toString()+"-01-01");
        while (dateBegin.getTime() < dateEnd.getTime() ) {
            System.out.println(formatter.format(dateBegin));
            Programaciones programacion = new Programaciones();
            Random r = new Random();
            Integer idSede = r.nextInt(sedes.getSedesCount())+1;
            Integer idHorarios = r.nextInt(horarios.getHorariosCount())+1;
            Integer idTipoBing = r.nextInt(tipoBingo.getTipoBingosCount())+1;
            Integer tablas = r.nextInt(72)+1;
            
            TipoBingos tipoBingos = tipoBingo.findTipoBingos(idTipoBing);
            programacion.setIdSede(sedes.findSedes(idSede));
            programacion.setIdHorario(horarios.findHorarios(idHorarios));
            programacion.setIdTipoBingo(tipoBingos);
            programacion.setEstado(0);
            programacion.setCantidadGanadores(String.valueOf(0));
            programacion.setCantidadPremio(tipoBingos.getValorPremio());
            programacion.setCantidadTablas(tablas);
            programacion.setFecha(dateBegin);
            
            programaciones.create(programacion);
            dateBegin.setTime((long) dateBegin.getTime()+(86400000));
        }
        
        
        
        
        
        
    }
    
    
}
