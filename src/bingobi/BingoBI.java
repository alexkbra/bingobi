/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi;

import bingobi.logica.Crear;
import java.util.Scanner;

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
        System.out.println("1: creación Cartones \n 2: emular programacion");
        int op = sc.nextInt();
        
        Crear crear = new Crear();
        switch(op){
            case 1:
                crear.crearTablasBingo();
                break;
            case 2:
                
            
        }
        
        
            
    }
    
}
