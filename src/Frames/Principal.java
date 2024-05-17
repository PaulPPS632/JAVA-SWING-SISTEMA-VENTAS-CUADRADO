/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frames;
import java.time.LocalDate;

/**
 *
 * @author Paul 
 */
public class Principal {

    public static void main(String[] args) {
        System.out.println(LocalDate.now().toString());
        if(LocalDate.now().isAfter(LocalDate.of(2024, 8, 1))){
            licencia.main(args);
        }else{
            frmLogin.main(args);
        }
    }
}
