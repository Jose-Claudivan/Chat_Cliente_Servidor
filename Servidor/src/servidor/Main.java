package servidor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.nio.file.Paths;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juciana
 */
public class Main {
    public static void main(String args[]){
        Servidor servidor = new Servidor();
        
        try {
            servidor.iniciar();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*try{
        System.out.println(Paths.get("images").toAbsolutePath().toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }*/
    }    
}
