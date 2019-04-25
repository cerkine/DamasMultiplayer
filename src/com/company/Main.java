package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

//        Cliente client = new Cliente();
//        try {
//            client.init("192.168.247.173",5555);
//            client.runClient();
//        } catch (IOException e) {
//            e.getStackTrace();
//        }
        Tablero tablero = new Tablero();
        tablero.dibujarTablero();
    }
}
