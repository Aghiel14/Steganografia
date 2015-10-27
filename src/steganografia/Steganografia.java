/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografia;

import java.io.*;

/**
 *
 * @author FamigliaMeschini
 */
public class Steganografia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ImgSteganografia imgS = new ImgSteganografia("res/images.jpg");
        imgS.crypt("123456789abcdefg");
        System.out.println(imgS.decrypt());
        
    }
    
}
