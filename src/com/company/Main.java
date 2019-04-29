package com.company;

public class Main {

    public static void main(String[] args) {

        Juego juego = new Juego();
        juego.conexion();
        do {
            juego.movimiento();
        }while (true);
    }
}
