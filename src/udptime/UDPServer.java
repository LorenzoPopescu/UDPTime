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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer{
    DatagramSocket dSocket;
    //datagramma UDP ricevuto dal client
    DatagramPacket inPacket;
    //datagramma UDP di risposta da inviare
    DatagramPacket outPacket;
    //Buffer per il contenuto del segmento da ricevere
    byte[] buffer;
    //Testo dei messaggi in I/O
    String messageIn, messageOut;
    //Data e ora correnti
    Date d;
    //numero di porta
    int port;
    InetAddress clientAddress;
    int clientPort;
                
    public UDPServer(int port){
        this.port=port;
        try {
            //si crea il socket e si associa alla porta specifica
            dSocket = new DatagramSocket(port);
            System.out.println("Apertura porta in corso!");
        } catch (SocketException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
		
    public void ascolta(){
        try{
            System.out.println("Server in ascolto sulla porta " + port + "!\n");
            buffer = new byte[256];
            //si crea un datagramma UDP in cui trasportare il buffer di lunghezza length
            inPacket = new DatagramPacket(buffer, buffer.length);
            //si ricevono i byte dal client e si blocca finchè arrivano i pacchetti
            dSocket.receive(inPacket);
            //si stampa a video il messaggio ricevuto dal client
        }catch (IOException ex){
                Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void ricevuto(){
        //si recupera l'indirizzo IP e la porta UDP del client
	clientAddress = inPacket.getAddress();
	clientPort = inPacket.getPort();
	//si stampa a video il messaggio ricevuto dal client
	messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
	System.out.println("SONO IL CLIENT " + clientAddress + ":" + clientPort + "> " + messageIn);
    }
    
    public void invia(){
        try {
            //si crea un oggetto Date con la data corrente
            d = new Date();
            //si crea il messaggio del server in uscita associandolo alla connessione aperta con il client
            messageOut = d.toString();
            //si crea un datagramma UDP in cui trasportare il messaggio di lunghezza length
            outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
            //si invia il messaggio al client
            dSocket.send(outPacket);
            System.out.println("Risposta inviata!");
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}