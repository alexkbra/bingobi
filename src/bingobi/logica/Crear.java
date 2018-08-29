/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.logica;

import bingobi.logica.bean.BingoBean;
import bingobi.persistence.controllers.CartonesJpaController;
import bingobi.persistence.controllers.DiasJpaController;
import bingobi.persistence.controllers.HorariosJpaController;
import bingobi.persistence.controllers.ProgramacionesJpaController;
import bingobi.persistence.controllers.SedesJpaController;
import bingobi.persistence.controllers.TablasJpaController;
import bingobi.persistence.controllers.TipoBingosJpaController;
import bingobi.persistence.entities.Cartones;
import bingobi.persistence.entities.Horarios;
import bingobi.persistence.entities.Programaciones;
import bingobi.persistence.entities.Sedes;
import bingobi.persistence.entities.Tablas;
import bingobi.persistence.entities.TipoBingos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    
    public String[] getStrDays(){
        return new String[]{
                        "7",//DOMINGO
                        "1",//LUNES
                        "2",//MARTES
                        "3",//MARTES
                        "4",//JUEVES
                        "5",//VIERNES
                        "6"//SÁBADO
                    };
    }
    
    public void crearProgramacion(EntityManagerFactory emf) throws Exception{
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SedesJpaController sedes = new SedesJpaController(emf);
        HorariosJpaController horarios = new HorariosJpaController(emf);
        TipoBingosJpaController tipoBingoController = new TipoBingosJpaController(emf);
        ProgramacionesJpaController programaciones = new ProgramacionesJpaController(emf);
        DiasJpaController dias = new DiasJpaController(emf);
        String dateStart = "";
        System.out.println("date init yyyy-MM-dd");
        dateStart = sc.next();
        
        Date dateBegin = formatter.parse(dateStart);
        int countMonths = 0, monthOld = dateBegin.getMonth(), monthNew = 0;
        Horarios lunes = horarios.findHorarios(1);
        
        String[] strDays = getStrDays();
        
        while (countMonths < 5 ) {
            List<Horarios> listHorarios = horarios.findHorariosEntities();
            int countHorariosNormal = 1;
            int countHorariosDJ = 0;//4h
            int countHorariosVSD = 0;//2h
            int counthorariosFinal = 0;
            Random r = new Random();
            Integer tablas = r.nextInt(72)+1;
            double porcentajeVentas = tablas*0.4;
            Calendar now = Calendar.getInstance();
            for (Horarios horario : listHorarios) {
                if(horario.getIdDia().getId() != lunes.getId()){
                    String dia = strDays[now.get(Calendar.DAY_OF_WEEK) - 1];
                    TipoBingos tipoBingo = null;
                    //Todos los días, cada 30min y 60 min
                    switch(countHorariosNormal){
                        case 1:
                            Sedes sede1 = sedes.findSedes(1);
                            Sedes sede2 = sedes.findSedes(2);
                            Sedes sede3 = sedes.findSedes(3);
                            Sedes sede4 = sedes.findSedes(4);
                            if (tablas > porcentajeVentas){
                                tipoBingo = tipoBingoController.findTipoBingos(1);
                                
                            }else{
                                if(tablas >= 10){
                                    tipoBingo = tipoBingoController.findTipoBingos(7);
                                }
                            }
                            break;
                        case 2:
                            
                            break;
                            
                    }
                    countHorariosNormal++;
                    if (countHorariosNormal > 2)countHorariosNormal = 1;
                    //Domingo y Jueves, 4 horas
                    countHorariosDJ++;
                    if(countHorariosDJ == 8){
                        tipoBingo = tipoBingoController.findTipoBingos(3);
                        countHorariosDJ = 0;
                    }
                    //Viernes, Sabado y Domingo, 2 Horas
                    countHorariosVSD++;    
                    if(countHorariosVSD == 4){
                        tipoBingo = tipoBingoController.findTipoBingos(4);
                        countHorariosVSD = 0;
                    }
                    //Final 
                    counthorariosFinal++;
                    if(counthorariosFinal == (listHorarios.size()-1)){
                        tipoBingo = tipoBingoController.findTipoBingos(5);
                        counthorariosFinal = 0;
                    }
                    //BINGAMES
                    Date dateEnd =new Date((long) dateBegin.getTime()+(86400000));
                    int monthNewEnd = dateEnd.getMonth();
                    if( monthOld != monthNewEnd  ){
                        tipoBingo = tipoBingoController.findTipoBingos(6);
                        //Determinar el 
                        
                    }
                    
                }
            }
            dateBegin.setTime((long) dateBegin.getTime()+(86400000));
            monthNew = dateBegin.getMonth();
               
            if( monthOld != monthNew  ){
                countMonths++;
                monthOld = dateBegin.getMonth();
            }
            System.out.println(formatter.format(dateBegin));
            /*Programaciones programacion = new Programaciones();
            
            Integer idSede = r.nextInt(sedes.getSedesCount())+1;
            Integer idHorarios = r.nextInt(horarios.getHorariosCount())+1;
            Integer idTipoBing = r.nextInt(tipoBingo.getTipoBingosCount())+1;
            
            
            TipoBingos tipoBingos = tipoBingo.findTipoBingos(idTipoBing);
            programacion.setIdSede(sedes.findSedes(idSede));
            programacion.setIdHorario(horarios.findHorarios(idHorarios));
            programacion.setIdTipoBingo(tipoBingos);
            programacion.setEstado(0);
            programacion.setCantidadGanadores(String.valueOf(0));
            programacion.setCantidadPremio(tipoBingos.getValorPremio());
            programacion.setCantidadTablas(tablas);
            programacion.setFecha(dateBegin);
            
            programaciones.create(programacion);*/
            
            
            
            
        }
    }
    
    
}
