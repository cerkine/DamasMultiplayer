package com.company;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Created by jordi on 26/02/17.
 * Exemple Client UDP extret dels apunts del IOC i ampliat
 * El server és DatagramSocketServer
 *
 * Aquest client reb del server el mateix que se li envia
 * Si s'envia adeu s'acaba la connexió
 */

public class Cliente {
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;
    Scanner sc;

    public Cliente() {
        sc = new Scanner(System.in);
    }

    public void init(String host, int port) throws SocketException, UnknownHostException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public int selectPlayer(String nom) throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;
        String player = "";

        sendingData = nom.getBytes();
        System.out.println(sendingData);
        DatagramPacket packet = new DatagramPacket(sendingData,sendingData.length,serverIP,serverPort);
        socket.send(packet);
        packet = new DatagramPacket(receivedData,1024);
        socket.receive(packet);
        player = new String(packet.getData(),0, packet.getLength());


        return Integer.valueOf(player);
    }

    public void runClient(int newfila, int newnumbercol) throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;

        sendingData = (String.valueOf(newfila) + String.valueOf(newnumbercol)).getBytes();
        System.out.println(sendingData);
        while (mustContinue(sendingData)) {
            DatagramPacket packet = new DatagramPacket(sendingData,sendingData.length,serverIP,serverPort);
            socket.send(packet);
            packet = new DatagramPacket(receivedData,1024);
            socket.receive(packet);
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }

    }

    private byte[] getDataToRequest(byte[] data, int length) {
        String rebut = new String(data,0, length);
        int fila = rebut.charAt(0);
        int columna = rebut.charAt(1);
        //Imprimeix el nom del client + el que es reb del server i demana més dades
        System.out.print(fila + columna);
        String msg = "prueba";
        return msg.getBytes();
    }

    private byte[] getPlayertoRequest(byte[] data, int length) {
        String rebut = new String(data,0, length);
        //Imprimeix el nom del client + el que es reb del server i demana més dades
        String msg = rebut;
        return msg.getBytes();
    }

    private boolean mustContinue(byte [] data) {
        String msg = new String(data).toLowerCase();
        if(!msg.equals("adeu")) return true;
        else return false;
    }


}