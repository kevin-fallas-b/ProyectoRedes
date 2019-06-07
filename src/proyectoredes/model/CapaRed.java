/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin F
 */
public class CapaRed {

    private List<Segmento> listaSegmentos = new ArrayList();
    private List<List<Paquete>> listaPaquetes = new ArrayList();
    private InetAddress ipOrigen;
    private List<InetAddress> ipsDestinos = new ArrayList();

    //constructor para la hora de envio
    public CapaRed(List<Segmento> segmentos, InetAddress ipOrigen, List<String> ipsDestinos) {
        this.listaSegmentos = segmentos;
        try {
            this.ipOrigen = ipOrigen;
            for (int i = 0; i < ipsDestinos.size(); i++) {
                this.ipsDestinos.add(InetAddress.getByName(ipsDestinos.get(i)));
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(CapaRed.class.getName()).log(Level.SEVERE, null, ex);
        }

        enPaquetar();
    }

    //constructor para la hora de recepcion
    public CapaRed(List<Paquete> paquetes) {
        this.listaPaquetes.add(paquetes);
        desEnPaquetar();
    }

    public List<Segmento> getListaSegmentos() {
        return listaSegmentos;
    }

    public void setListaSegmentos(List<Segmento> listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
    }

    public List<List<Paquete>> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(List<List<Paquete>> listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }

    private void enPaquetar() {
        for (int k = 0; k < ipsDestinos.size(); k++) {
            List<Paquete> paquetesNuevos = new ArrayList();
            for (int i = 0; i < listaSegmentos.size(); i++) {
                Paquete paquete = new Paquete(ipOrigen, ipsDestinos.get(k), listaSegmentos.get(i));
                paquetesNuevos.add(paquete);
            }
            listaPaquetes.add(paquetesNuevos);
        }
    }

    private void desEnPaquetar() {
        for (int i = 0; i < listaPaquetes.get(0).size(); i++) {
            Segmento segmento = listaPaquetes.get(0).get(i).getDatos();
            listaSegmentos.add(segmento);
        }
        System.out.println("Se despaquetaron "+listaSegmentos.size()+" segmentos");
    }
}
