/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udptime;
/**
 *
 * @author loren
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClient {
    //numero di porta
    int port;
    //indirizzo del server
    InetAddress serverAddress;
    //socket UDP
    DatagramSocket dSocket;
    //Datagramma UDP con la richiesta da inviare al server
    DatagramPacket outPacket;
    //Datagramma UDP di risposta ricevuto dal server
    DatagramPacket inPacket;
    //buffer per i dati da inviare
    byte[] buffer;
    //messaggio di richiesta
    String message="RICHIESTA DATA E ORA";
    //messaggio di risposta
    String response;
    
    public UDPClient(int port){
        try {
            this.port=port;
            /*
            * si usa getLocalHost() se il server è sulla stessa macchina locale
            * altrimenti si deve conoscere l'IP del server
            */
            serverAddress = InetAddress.getLocalHost();
            System.out.println("Indirizzo del server trovato!");
            try {
                dSocket = new DatagramSocket();
            } catch (SocketException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void invia(){
        try {
            //si prepara il datagramma con i dati da inviare
            outPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, port);
            //si inviano i dati
            dSocket.send(outPacket);
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ricevi(){
        try {
            //si prepara il buffer di ricezione
            buffer = new byte[256];
            //e il datagramma UDP per ricevere i dati del buffer
            inPacket = new DatagramPacket(buffer, buffer.length);
            //si accetta il datagramma di risposta
            dSocket.receive(inPacket);
            //si estrae il messaggio
            response = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println("Connessione stabilita!");
            System.out.println("Data e ora del server: " + response);
            System.out.println("Connessione chiusa!");
            //si chiude la connessione
            dSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Test");
        }
    }
}
