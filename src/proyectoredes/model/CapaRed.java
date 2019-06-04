/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Kevin F
 */
public class CapaRed {
    private final Integer puerto = 13000;
    private InetAddress ipServidor;
    private InetAddress ipDestino;//para casos donde se usa conexion TCP
    private Socket socket;
    private Scanner scanner;
    
    public CapaRed(String direccionDestino) throws IOException{
        //este constructor es para casos donde se usa TCP, por eso se recibe una unica direccion IP de destino
        this.socket = new Socket(ipServidor,puerto);
        this.ipDestino = InetAddress.getByName(direccionDestino);
    }
    
    public CapaRed(List<String> Destinos) throws UnknownHostException{
        //esto es cuando es con UDP y hay que hacer broadcast, por eso la lista
        ipServidor = InetAddress.getLocalHost();
    }
    
    private void crearServidorTCP(){
        
    }
    
    private void crearClienteTCP(){
        
    }
}
