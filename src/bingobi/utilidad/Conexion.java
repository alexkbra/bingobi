/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.utilidad;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author kbra
 */
public class Conexion {
    
    public static EntityManagerFactory createEntityManagerFactory(){
        return  Persistence.createEntityManagerFactory("BingoPersistenceBIPU");
    }
    
}
