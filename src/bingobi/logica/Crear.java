/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.logica;

import bingobi.logica.util.DateUtil;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManagerFactory;
import persistence.controllers.ProgramacionesJpaController;
import persistence.controllers.TipoBingosJpaController;
import persistence.entity.Programaciones;
import persistence.entity.TipoBingos;

/**
 *
 * @author kbra
 */
public class Crear {

    private DateUtil dateUtil;

    public boolean updateProgramation(EntityManagerFactory emf) {
        ProgramacionesJpaController pjc = new ProgramacionesJpaController(emf);
        TipoBingosJpaController tbjc = new TipoBingosJpaController(emf);
        List<Programaciones> listP = pjc.findProgramacionesEntities();
        Random r = new Random();
        Integer totalTablas = 72;
        for (Programaciones programaciones : listP) {
            Integer tablasVentas = (r.nextInt(totalTablas) + 1);
            double porcentajeVentas = totalTablas * (0.4);
            if (!validTypeVingo(tablasVentas, porcentajeVentas, programaciones, tbjc, pjc)) {
                programaciones.setEstado(0);
            } else {
                programaciones.setCantidadTablas(tablasVentas);
            }
            try {
                pjc.edit(programaciones);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean validTypeVingo(Integer tablasVentas,
            double porcentajeVentas,
            Programaciones progr,
            TipoBingosJpaController tbjc,
            ProgramacionesJpaController pjc) {
        if (tablasVentas > porcentajeVentas) {
            return true;
        } else {
            if (tablasVentas >= 10) {
                crearProgramacionBingito(tablasVentas, progr, tbjc, pjc);
            }
            return false;
        }
    }

    private void crearProgramacionBingito(Integer tablasVentas,
            Programaciones progr,
            TipoBingosJpaController tbjc,
            ProgramacionesJpaController pjc) {
        //BINGITO
        TipoBingos tipoBingo = tbjc.findTipoBingos(7);
        Programaciones programacion = new Programaciones();
        programacion.setIdSede(progr.getIdSede());
        programacion.setIdHorario(progr.getIdHorario());
        programacion.setIdTipoBingo(tipoBingo);
        programacion.setFecha(progr.getFecha());
        programacion.setCantidadGanadores("0");
        programacion.setCantidadPremio((int) ((tablasVentas * 2500) * 0.45));
        programacion.setCantidadTablas(tablasVentas);
        programacion.setEstado(1);
        programacion.setHoraFin(progr.getHoraFin());
        programacion.setHoraIni(progr.getHoraIni());
        pjc.create(programacion);
    }

    public void emularBingo(EntityManagerFactory emf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
