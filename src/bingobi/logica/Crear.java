/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.logica;

import bingobi.logica.bean.BingoBean;
import bingobi.persistence.controllers.CartonesJpaController;
import bingobi.persistence.controllers.TablasJpaController;
import bingobi.persistence.entities.Cartones;
import bingobi.persistence.entities.Tablas;
import bingobi.utilidad.Conexion;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class Crear {
    
    public void crearTablasBingo(){
        EntityManagerFactory emf = Conexion.createEntityManagerFactory();
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
    
    public void crearProgramacion(){
        
    }
}
