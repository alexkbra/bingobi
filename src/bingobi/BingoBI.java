/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi;

import bingobi.logica.Crear;
import bingobi.utilidad.Conexion;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class BingoBI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EntityManagerFactory emf = Conexion.createEntityManagerFactory();
        System.out.println("1: update programations "
                + "\n2: emular bingo "
                + "\n3: emular bingo contro"
                + "\n3: emular bingo contro Ganador" );
        int op = sc.nextInt();
        try {
            Crear crear = new Crear();
            switch(op){
                case 1:
                    crear.updateProgramation(emf);
                    break;
                case 2:
                    crear.emularBingo(emf);
                    break;
                case 3:
                    crear.emularBingoControl(emf);
                case 4:
                    crear.emularBingoControlGanador(emf);
                default: 
                       System.out.println("default");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
