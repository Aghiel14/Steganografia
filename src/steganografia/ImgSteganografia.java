package steganografia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ImgSteganografia {
    
    private String fileName;
    private String key = "123456789abcdefg";
    
    /**
     * Costruttore
     * 
     * @param fileName - path dell'immagine
     */
    public ImgSteganografia(String fileName){
        this.fileName = fileName;
    }
    
    
    /**
     * Metodo che cripta il testo in AES e lo inserisce nel file
     * 
     * @param testo - testo da inserire
     */
    public void crypt(String testo){
        
        try {
            RandomAccessFile f = new RandomAccessFile("res/images.jpg", "rw");
            int dati = 0;
            int nByte =0;
            int flag = 0;
            byte[] chiper;
            dati = f.read();
            while(testo.length() < 16){
                testo += '\0';
            }
           
            while(dati != -1){
                nByte++;
                if(dati == 0xFF){
                    flag = 1;
                }
                if(flag == 1){
                    nByte++;
                    dati = f.read();
                    if(dati == 0xD9){
                        chiper = AES.encrypt(testo, key);
                        f.write(chiper);
                        dati = -1;
                    }
                    flag = 0;
                }
                dati = f.read();
            }
            f.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Metodo che decripta il testo nel file
     * 
     * @return testo decifrato
     */
    public String decrypt(){
        String testo = "";
        
        try {
            RandomAccessFile f = new RandomAccessFile("res/images.jpg", "r");
            int dati = 0;
            int flag = 0;
            int i = 0;
            byte[] chiper = null;
            dati = f.read();
                    
            // cerco la fine del file immagine 
            // e leggo il testo cifrato
            while(dati != -1){
                
                if(dati == 0xFF){
                    flag = 1;
                }
                if(flag == 1){
                    dati = f.read();
                    if(dati == 0xD9){
                        // Leggo il testo cifrato nel file
                        chiper = new byte[(int)(f.length() - f.getFilePointer())];
                        f.read(chiper);
                        // finisco il ciclo
                        dati = -1;
                    }
                    flag = 0;
                }
                dati = f.read();
            }
            // decripto il testo cifrato
            testo = AES.decrypt(chiper, key);
            
            f.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return testo;
    }
    
}
