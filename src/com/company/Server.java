package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server {
    DatagramSocket socket;
    boolean player1, player2, notEmpty;

    //Instàciar el socket
    public void init(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public void runServer() throws IOException {

        elegirjugador();
        jugar();

    }
    private void elegirjugador() throws IOException {
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;
        while(!player1 || !player2) {
            DatagramPacket packet = new DatagramPacket(receivingData,1024);
            socket.receive(packet);
            sendingData = processData(packet.getData(),packet.getLength());
            if (notEmpty){
                if (!player1) {
                    sendingData = "2".getBytes();
                    //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
                    clientIP = packet.getAddress();
                    clientPort = packet.getPort();
                    packet = new DatagramPacket(sendingData, sendingData.length, clientIP, clientPort);
                    socket.send(packet);
                    player1 = true;
                    notEmpty = false;
                }
                else if (!player2){
                    sendingData = "3".getBytes();
                    //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
                    clientIP = packet.getAddress();
                    clientPort = packet.getPort();
                    packet = new DatagramPacket(sendingData, sendingData.length, clientIP, clientPort);
                    socket.send(packet);
                    player2 = true;
                }
            }
        }
    }

    public void jugar() throws IOException {
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        DatagramPacket packet = new DatagramPacket(receivingData,1024);
        socket.receive(packet);
        sendingData = processData(packet.getData(),packet.getLength());

        if (player1 && player2){
            //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
            clientIP = packet.getAddress();
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData,sendingData.length,clientIP,clientPort);
            socket.send(packet);
            System.out.println(new String(sendingData,0, sendingData.length));
        }
    }
    private byte[] processData(byte[] data, int lenght) {
        String msg = new String(data,0,lenght);
        if (!msg.isEmpty() && !msg.matches(".*\\d.*")){
            notEmpty = true;
        }
        System.out.println(msg);
        return msg.getBytes();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.init(5555);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}