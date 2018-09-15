/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.logica;

import bingobi.logica.util.DateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManagerFactory;
import persistence.controllers.BingosJpaController;
import persistence.controllers.CartonesJpaController;
import persistence.controllers.ControlProgramaciónJpaController;
import persistence.controllers.PersonasJpaController;
import persistence.controllers.ProgramacionesJpaController;
import persistence.controllers.TablasJpaController;
import persistence.controllers.TipoBingosJpaController;
import persistence.entity.Bingos;
import persistence.entity.Cartones;
import persistence.entity.ControlProgramación;
import persistence.entity.Personas;
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
        ProgramacionesJpaController pjc = new ProgramacionesJpaController(emf);
        PersonasJpaController pjc1 = new PersonasJpaController(emf);
        BingosJpaController bjc = new BingosJpaController(emf);
        TablasJpaController tjc = new TablasJpaController(emf);
        List<Programaciones> listP = pjc.findProgramacionesEntities();
        Random r = new Random();
        Integer totalTablas = 72;
        for (Programaciones programaciones : listP) {
            if (programaciones.getEstado() != 0) {
                int max = programaciones.getCantidadTablas();
                Map<Integer, Object> tablasR = new HashMap<Integer, Object>();
                Map<Integer, Integer> personasR = new HashMap<Integer, Integer>();
                int i = 1;

                while (i <= max) {
                    Personas persona = pjc1.findPersonas((r.nextInt(pjc1.getPersonasCount()) + 1) + 100);
                    Integer countTablasMaxPersonas = personasR.get(persona.getId()) == null ? 0 : personasR.get(persona.getId());
                    personasR.putIfAbsent(persona.getId(), ++countTablasMaxPersonas);
                    if (countTablasMaxPersonas == 40) {
                        persona = pjc1.findPersonas((r.nextInt(pjc1.getPersonasCount()) + 1) + 100);
                    }
                    int tabla = (r.nextInt(totalTablas)) + 1;

                    Boolean key = (Boolean) tablasR.get(tabla);
                    if (key == null) {
                        tablasR.put(tabla, true);
                        Bingos bingo = new Bingos();
                        bingo.setIdTabla(tjc.findTablas(tabla + 1));
                        bingo.setIdProgramacion(programaciones);
                        bingo.setIdPersona(persona);
                        bingo.setEstado(0);
                        bjc.create(bingo);
                        i++;
                    }
                }
            }
        }
    }

    public void emularBingoControl(EntityManagerFactory emf) {
        ProgramacionesJpaController pjc = new ProgramacionesJpaController(emf);
        PersonasJpaController pjc1 = new PersonasJpaController(emf);
        BingosJpaController bjc = new BingosJpaController(emf);
        TablasJpaController tjc = new TablasJpaController(emf);
        ControlProgramaciónJpaController cpjc = new ControlProgramaciónJpaController(emf);
        Random r = new Random();
        List<Programaciones> listP = pjc.findProgramacionesEntities();
        for (Programaciones programaciones : listP) {
            if (programaciones.getEstado() != 0) {
                for (int i = 0; i < 90; i++) {
                    ControlProgramación cp = new ControlProgramación();
                    cp.setIdProgramacion(programaciones);
                    cp.setNumero(String.valueOf(i + 1));
                    cp.getEstado();

                    cpjc.create(cp);
                }
            }
        }
    }
    
    public void emularBingoControlGanador(EntityManagerFactory emf) throws Exception {
        ProgramacionesJpaController pjc = new ProgramacionesJpaController(emf);
        PersonasJpaController pjc1 = new PersonasJpaController(emf);
        BingosJpaController bjc = new BingosJpaController(emf);
        TablasJpaController tjc = new TablasJpaController(emf);
        CartonesJpaController cjc = new CartonesJpaController(emf);
        ControlProgramaciónJpaController cpjc = new ControlProgramaciónJpaController(emf);
        Random r = new Random();
        
        List<Programaciones> listP = pjc.findProgramacionesEntities();
        for (Programaciones programaciones : listP) {
            if (programaciones.getEstado() != 0) {
                List<Bingos> listBingos = bjc.getBingosXProgramacion(programaciones);
                Map<String, ControlProgramación> map25 = map25Generar(cpjc);
                boolean isGano = false;
                int count = 24;
                while (!isGano && count < 90) {                    
                    for (Bingos bingo : listBingos) {
                        List<Cartones> cartoneses = cjc.getCartonesXTablas(bingo.getIdTabla());
                        int countGanador = 0;
                        for (Cartones cartonese : cartoneses) {
                            if(map25.get(cartonese.getB()) != null) countGanador++;
                            if(map25.get(cartonese.getI()) != null) countGanador++;
                            if(map25.get(cartonese.getN()) != null) countGanador++;
                            if(map25.get(cartonese.getG()) != null) countGanador++;
                            if(map25.get(cartonese.getO()) != null) countGanador++;
                        }
                        if(countGanador == 25){
                            isGano = true;
                            bingo.setEstado(1);
                            bjc.edit(bingo);
                        }
                    }
                    
                    String numero = generateNumero(map25);
                    ControlProgramación cp = generateControlProgramación(cpjc, numero);
                    map25.put(numero, cp);
                    
                }
            }
        }
    }
    
    public Map<String, ControlProgramación> map25Generar(ControlProgramaciónJpaController cpjc) throws Exception{
        Map<String, ControlProgramación> result = new HashMap<>();
        for (int i = 0; i < 25; i++) {
            String numero = generateNumero(result);
            ControlProgramación cp = generateControlProgramación(cpjc, numero);
            result.put(numero, cp);
            cpjc.edit(cp);
        }
        return result;
    }
    
    public String generateNumero(Map<String, ControlProgramación> result){
        Random r = new Random();
            boolean retry = false;
            String numero = String.valueOf((r.nextInt(90) + 1));
            while(!retry){
                if(result.get(numero) != null){
                    numero = String.valueOf((r.nextInt(90) + 1));
                }
            }
            return numero;
            
    }
    
    public ControlProgramación generateControlProgramación(ControlProgramaciónJpaController cpjc,String numero){
        ControlProgramación cp = cpjc.getControlProgramaciónCount(numero);
        cp.setEstado(1);
        return cp;
    }
}
