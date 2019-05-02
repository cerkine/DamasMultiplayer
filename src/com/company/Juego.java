package com.company;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {
    Tablero tablero;
    int id,rival;
    boolean finished, correctplay;
    Scanner scanner;
    Cliente cliente;
    int comprobacion;

    public Juego() {
        tablero = new Tablero();
        id = 2;
        scanner = new Scanner(System.in);
        cliente = new Cliente();
    }
    public void conexion(){
        try {
            cliente.init("192.168.253.229", 5555);
            System.out.println("Como te llamas?");
            String name = scanner.nextLine();
            id = cliente.selectPlayer(name);
            if (id == tablero.FICAMARILLO) {
                rival = tablero.FICAZUL;
                System.out.println("Bienvenido! Eres las amarillas");
            } else if (id == tablero.FICAZUL) {
                rival = tablero.FICAMARILLO;
                System.out.println("Bienvenido! Eres las azules");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void movimiento() {
        tablero.dibujarTablero();
        if (id==tablero.FICAMARILLO){
            System.out.println();
            System.out.println("ESPERANDO JUGADA");
            esperajugada();
            tablero.dibujarTablero();
        }

        do {
            int numbercol = 11;
            int newnumbercol = 11;
            int fila;
            int newfila;

            do {
                System.out.println("\u001B[0m" + "\nQue ficha quieres mover? Introducelo en este orden: Columna Antigua-Fila antigua- Columna Nueva-Fila Nueva Ej: j7i6");
                String jugada = scanner.nextLine();

                char col = jugada.toLowerCase().charAt(0);
                numbercol = getNumbercol(col);

                fila = Integer.parseInt(String.valueOf(jugada.toLowerCase().charAt(1))) - 1;

                char newcol = jugada.toLowerCase().charAt(2);
                newnumbercol = getNumbercol(newcol);

                newfila = Integer.parseInt(String.valueOf(jugada.toLowerCase().charAt(3))) - 1;

            }while (numbercol == 11 || newnumbercol == 11);

            if (checkPropietario(numbercol, fila, newnumbercol, newfila) && checkMovimiento(numbercol, fila, newnumbercol, newfila)) {

                tablero.getMesa()[fila][numbercol] = tablero.CASROJA;
                tablero.getMesa()[newfila][newnumbercol] = id;
                correctplay = true;
                try {
                    cliente.runClient(fila, numbercol, newfila, newnumbercol);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (id == tablero.FICAZUL) {
                tablero.dibujarTablero();
            }
        } while (!correctplay);
        correctplay = false;

        if (id == tablero.FICAZUL){
            System.out.println();
            System.out.println("ESPERANDO JUGADA");
            esperajugada();
        }
    }

    private void esperajugada() {
        String jugada = null;
        try {
            jugada = cliente.getJugada();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int oldfila = Integer.parseInt( String.valueOf(jugada.charAt(0)));
        int oldcolumna = Integer.parseInt( String.valueOf(jugada.charAt(1)));
        int newfila = Integer.parseInt( String.valueOf(jugada.charAt(2)));
        int newcolumna = Integer.parseInt( String.valueOf(jugada.charAt(3)));

        if (checkMovimiento(oldcolumna,oldfila,newcolumna,newfila)){
            tablero.getMesa()[oldfila][oldcolumna] = tablero.CASROJA;
            tablero.getMesa()[newfila][newcolumna] = rival;

        }
    }


    private int getNumbercol(char col) {
        switch (col) {
            case 'a':
                return  0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
        }
        return 11;
    }

    private boolean checkMovimiento(int numbercol, int fila, int newnumbercol, int newfila) {
        comprobacion = fila - newfila;
            if (numbercol - newnumbercol == comprobacion || numbercol - newnumbercol == -comprobacion) {
                if (comprobacion == 2 || comprobacion == -2) {
                    if (comprobacion < 0) {
                        if ((tablero.getMesa()[fila - comprobacion/2][numbercol - (newnumbercol- numbercol)/2] == tablero.FICAZUL
                                || tablero.getMesa()[fila - comprobacion/2][numbercol + (newnumbercol- numbercol)/2] == tablero.FICAZUL)
                                && tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA) {
                            int borrar = (newnumbercol - numbercol) /2;
                            tablero.getMesa()[fila-comprobacion/2][numbercol+borrar]=tablero.CASROJA;
                            return true;
                        }
                    } else if ((tablero.getMesa()[fila - comprobacion/2][numbercol - (newnumbercol- numbercol)/2] == tablero.FICAMARILLO
                            || tablero.getMesa()[fila - comprobacion/2][numbercol + (newnumbercol- numbercol)/2] == tablero.FICAMARILLO)
                            && tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA) {
                        int borrar = (newnumbercol - numbercol) /2;
                        tablero.getMesa()[fila-comprobacion/2][numbercol+borrar]=tablero.CASROJA;
                        return true;
                    } else {
                        System.out.println("No puedes moverte dos no hay nada que comer");
                        return false;
                    }
                } else if (comprobacion == 1 || comprobacion == -1) {
                    if (tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA)
                    return true;
                } else {
                    System.out.println("La distancia no es correcta");
                    return false;
                }

        }else {
            System.out.println("No te puedes mover hacia atras");
        }

        return false;
    }

    private boolean checkPropietario(int numbercol, int fila, int newnumbercol, int newfila) {

        if (tablero.getMesa()[fila][numbercol] == tablero.CASGRIS || tablero.getMesa()[fila][numbercol] == tablero.CASROJA) {
            System.out.println("No hay ninguna ficha en esa posición");
            return false;
        } else if (tablero.getMesa()[fila][numbercol] != id) {
            System.out.println("Esa ficha no es tuya");
            return false;
        } else if (tablero.getMesa()[fila][numbercol] == id) {
            if (tablero.getMesa()[newfila][newnumbercol] != tablero.CASROJA) {
                System.out.println("No puedes mover ahi");
                return false;
            }
        }
        return true;
    }

}


