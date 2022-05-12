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
public class AvvioClient {
    public static void main(String[] args) {
       UDPClient cli = new UDPClient(2000);
       cli.invia();
       cli.ricevi();  
    }
}
