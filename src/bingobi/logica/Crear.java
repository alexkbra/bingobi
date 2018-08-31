/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.logica;

import bingobi.logica.bean.BingoBean;
import bingobi.logica.util.DateUtil;
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

    DateUtil dateUtil;

    public void crearTablasBingo(EntityManagerFactory emf) {
        CartonesJpaController cartoneController = new CartonesJpaController(emf);
        TablasJpaController tablaController = new TablasJpaController(emf);
        Integer b = 1, i = 18, n = 36, g = 54, o = 72;
        BingoBean bean = new BingoBean(b, i, n, g, o);
        for (int j = 1; j <= 72; j++) {
            Tablas tabla = new Tablas();
            tabla.setNumero(String.valueOf(j));
            tablaController.create(tabla);
            for (int k = 0; k < 5; k++) {
                Cartones carton = new Cartones();
                carton.setB(b.toString());
                carton.setI(i.toString());
                if (k == 2) {
                    carton.setN(" ");
                } else {
                    carton.setN(n.toString());
                }
                carton.setG(g.toString());
                carton.setO(o.toString());
                carton.setIdTabla(tabla);
                cartoneController.create(carton);
                b++;
                i++;
                g++;
                o++;
                if (k != 2) {
                    n++;
                }
                if (b > 17) {
                    b = 1;
                }
                if (i > 35) {
                    i = 18;
                }
                if (n > 53) {
                    n = 36;
                }
                if (g > 71) {
                    g = 54;
                }
                if (o > 90) {
                    o = 72;
                }
            }
        }
    }

    public String[] getStrDays() {
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

    public void crearProgramacion(EntityManagerFactory emf) throws Exception {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SedesJpaController sedes = new SedesJpaController(emf);
        HorariosJpaController horarios = new HorariosJpaController(emf);
        TipoBingosJpaController tipoBingoController = new TipoBingosJpaController(emf);
        ProgramacionesJpaController programaciones = new ProgramacionesJpaController(emf);
        DiasJpaController dias = new DiasJpaController(emf);
        String dateStart = "";
        Date today = new Date();
        System.out.println("date init yyyy-MM-dd " + formatter.format(today));
        //dateStart = sc.next();
        Date dateBegin = today;
        dateUtil = new DateUtil(dateBegin.getYear());
        int countMonths = 0, monthOld = dateBegin.getMonth(), monthNew = 0;
        Horarios lunes = horarios.findHorarios(1);
        String[] strDays = getStrDays();
        while (countMonths < 5) {

            Sedes sede1 = sedes.findSedes(1);
            crearProgramacion(lunes, strDays, dateBegin, monthOld, sede1, programaciones, horarios, tipoBingoController);
            
            Sedes sede2 = sedes.findSedes(2);
            crearProgramacion(lunes, strDays, dateBegin, monthOld, sede2, programaciones, horarios, tipoBingoController);
            
            Sedes sede3 = sedes.findSedes(3);
            crearProgramacion(lunes, strDays, dateBegin, monthOld, sede3, programaciones, horarios, tipoBingoController);
            
            Sedes sede4 = sedes.findSedes(4);
            crearProgramacion(lunes, strDays, dateBegin, monthOld, sede4, programaciones, horarios, tipoBingoController);
            
            dateBegin.setTime((long) dateBegin.getTime() + (86400000));
            monthNew = dateBegin.getMonth();
            if (monthOld != monthNew) {
                countMonths++;
                monthOld = dateBegin.getMonth();
            }
            System.out.println(formatter.format(dateBegin));
        }
    }

    public void crearProgramacion(Horarios lunes, String[] strDays, Date dateBegin, int monthOld, Sedes sedes, ProgramacionesJpaController programaciones, HorariosJpaController horarios, TipoBingosJpaController tipoBingoController) {
        List<Horarios> listHorarios = horarios.findHorariosEntities();
        int countHorariosNormal = 1;
        int countHorariosDJ = 0;//4h
        int countHorariosVSD = 0;//2h
        int counthorariosFinal = 0;
        Random r = new Random();
        Integer totalTablas = 72;
        Integer tablasVentas = r.nextInt(totalTablas) + 1;
        double porcentajeVentas = totalTablas * (0.4);
        Calendar now = Calendar.getInstance();

        for (Horarios horario : listHorarios) {
            if (horario.getIdDia().getId() != lunes.getId()) {
                String dia = strDays[now.get(Calendar.DAY_OF_WEEK) - 1];
                TipoBingos tipoBingo = null;
                //Todos los días, cada 30min y 60 min
                switch (countHorariosNormal) {
                    case 1:
                        //BINGO(NORMAL)
                        if (tablasVentas > porcentajeVentas) {
                            tipoBingo = tipoBingoController.findTipoBingos(1);
                            crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, dateBegin);
                        } else {
                            //BINGITO
                            tipoBingo = tipoBingoController.findTipoBingos(7);
                            if (tablasVentas >= 10) {
                                crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, dateBegin);
                            } else {
                                crearProgramacion(tipoBingo, horario, tablasVentas,  programaciones, sedes, dateBegin,0);    
                            }
                        }
                        break;
                    case 2:
                        //totalTablas
                        tipoBingo = tipoBingoController.findTipoBingos(2);
                        crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, dateBegin);
                        break;

                }
                //TODOS LOS DÏAS
                countHorariosNormal++;
                if (countHorariosNormal > 2) {
                    countHorariosNormal = 1;
                }

                //Domingo y Jueves, 4 horas
                //SUPER BINGO
                countHorariosDJ++;
                if (countHorariosDJ == 8) {
                    tipoBingo = tipoBingoController.findTipoBingos(3);
                    crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, dateBegin);
                    countHorariosDJ = 0;
                }
                //Viernes, Sabado y Domingo, 2 Horas
                //SUPER BINGOTE
                countHorariosVSD++;
                if (countHorariosVSD == 4) {
                    tipoBingo = tipoBingoController.findTipoBingos(4);
                    crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, dateBegin);
                    countHorariosVSD = 0;
                }
                //Final 
                //BINGAZO
                counthorariosFinal++;
                if (counthorariosFinal == (listHorarios.size() - 1)) {
                    tipoBingo = tipoBingoController.findTipoBingos(5);
                    crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, dateBegin);
                    counthorariosFinal = 0;
                }
                //BINGAMES
                Date dateEnd = new Date((long) dateBegin.getTime() + (86400000));
                int monthNewEnd = dateEnd.getMonth();
                if (monthOld != monthNewEnd) {
                    tipoBingo = tipoBingoController.findTipoBingos(6);
                    if (dateUtil.isHoliday(dateEnd.getMonth(), dateEnd.getDay())) {
                        crearProgramacion(tipoBingo, horario, tablasVentas, programaciones, sedes, new Date((long) dateBegin.getTime() - (86400000)));
                    }
                }
            }
        }
    }

    public void crearProgramacion(TipoBingos tipoBingo,
            Horarios horario,
            Integer countTable,
            ProgramacionesJpaController programaciones,
            Sedes sede,
            Date fecha) {
        crearProgramacion(tipoBingo, sede, horario, countTable, programaciones, fecha, 1);
    }
    
    public void crearProgramacion(TipoBingos tipoBingo,
            Horarios horario,
            Integer countTable,
            ProgramacionesJpaController programaciones,
            Sedes sede,
            Date fecha,
            Integer estado) {
        crearProgramacion(tipoBingo, sede, horario, countTable, programaciones, fecha, estado);
    }

    public void crearProgramacion(TipoBingos tipoBingo,
            Sedes sede,
            Horarios horario,
            Integer countTable,
            ProgramacionesJpaController programaciones,
            Date fecha,
            Integer estado) {

        Programaciones programacion = new Programaciones();
        programacion.setIdSede(sede);
        programacion.setIdHorario(horario);
        programacion.setIdTipoBingo(tipoBingo);
        programacion.setCantidadGanadores(String.valueOf(0));
        programacion.setCantidadPremio(tipoBingo.getValorPremio());
        programacion.setCantidadTablas(countTable);
        programacion.setFecha(fecha);
        programacion.setEstado(estado);
        programaciones.create(programacion);

    }
}
