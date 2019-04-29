package com.company;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    public void movimiento() {
        try {
            cliente.init("localhost", 5555);
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
        if (id==tablero.FICAMARILLO){
            esperajugada();
        }
        do {

            tablero.dibujarTablero();
            System.out.println("\u001B[0m" + "\nQue ficha quieres mover?");
            System.out.println("Introduce la columna");
            char col = scanner.next().toLowerCase().charAt(0);
            int numbercol = 0;

            numbercol = getNumbercol(col, numbercol);
            System.out.println("Introduce la fila");
            int fila = scanner.nextInt() - 1;
            scanner.nextLine();
            System.out.println("A donde la quieres mover?");
            System.out.println("Introduce la columna");
            char newcol = scanner.next().toLowerCase().charAt(0);
            int newnumbercol = 0;

            newnumbercol = getNumbercol(newcol, newnumbercol);
            System.out.println("Introduce la fila");
            int newfila = scanner.nextInt() - 1;
            scanner.nextLine();
            if (checkPropietario(numbercol, fila, newnumbercol, newfila) && checkMovimiento(numbercol, fila, newnumbercol, newfila)) {

                tablero.getMesa()[fila][numbercol] = tablero.CASROJA;
                tablero.getMesa()[newfila][newnumbercol] = id;
                correctplay = true;
                try {
                    cliente.runClient(fila, numbercol, newfila, newnumbercol);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                if (id == tablero.FICAZUL) id = tablero.FICAMARILLO;
//                else id = tablero.FICAZUL;

            }
        }

        while (!correctplay);
        if (id == tablero.FICAZUL)esperajugada();
    }

    private void esperajugada() {
        String jugada = null;
        try {
            jugada = cliente.getJugada();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] arrayRebut = jugada.toCharArray();
        int oldfila = (int) arrayRebut[0];
        int oldcolumna = (int) arrayRebut[1];
        int newfila = (int) arrayRebut[2];
        int newcolumna = (int) arrayRebut[3];

        if (checkPropietario(oldcolumna,oldfila,newcolumna,newfila) &&
        checkMovimiento(oldcolumna,oldfila,newcolumna,newfila)){
            tablero.getMesa()[oldfila][oldcolumna] = tablero.CASROJA;
            tablero.getMesa()[newfila][newcolumna] = rival;

        }
    }


    private int getNumbercol(char col, int numbercol) {
        switch (col) {
            case 'a':
                numbercol = 0;
                break;
            case 'b':
                numbercol = 1;
                break;
            case 'c':
                numbercol = 2;
                break;
            case 'd':
                numbercol = 3;
                break;
            case 'e':
                numbercol = 4;
                break;
            case 'f':
                numbercol = 5;
                break;
            case 'g':
                numbercol = 6;
                break;
            case 'h':
                numbercol = 7;
                break;
            case 'i':
                numbercol = 8;
                break;
            case 'j':
                numbercol = 9;
                break;
        }
        return numbercol;
    }

    private boolean checkMovimiento(int numbercol, int fila, int newnumbercol, int newfila) {
        comprobacion = fila - newfila;
//        if (id == tablero.FICAMARILLO){
//            comprobacion = -comprobacion;
//        }
        if ((id==tablero.FICAMARILLO && comprobacion<0)|| (id==tablero.FICAZUL && comprobacion>0) ) {
            if (numbercol - newnumbercol == comprobacion || numbercol - newnumbercol == -comprobacion) {
                if (comprobacion == 2 || comprobacion == -2) {
                    if (comprobacion < 0) {
                        if ((tablero.getMesa()[fila - comprobacion/2][numbercol - (newnumbercol- numbercol)/2] == tablero.FICAZUL
                                || tablero.getMesa()[fila - comprobacion/2][numbercol + (newnumbercol- numbercol)/2] == tablero.FICAZUL)
                                && tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA) {
                            int borrar = (newnumbercol - numbercol) /2;
                            tablero.getMesa()[fila-comprobacion/2][numbercol+borrar]=tablero.CASROJA;
                            System.out.println("correcto");
                            return true;
                        }
                    } else if ((tablero.getMesa()[fila - comprobacion/2][numbercol - (newnumbercol- numbercol)/2] == tablero.FICAMARILLO
                            || tablero.getMesa()[fila - comprobacion/2][numbercol + (newnumbercol- numbercol)/2] == tablero.FICAMARILLO)
                            && tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA) {
                        int borrar = (newnumbercol - numbercol) /2;
                        tablero.getMesa()[fila-comprobacion/2][numbercol+borrar]=tablero.CASROJA;
                        System.out.println("correcto");
                        return true;
                    } else {
                        System.out.println("No puedes moverte dos no hay nada que comer");
                        return false;
                    }
                } else if (comprobacion == 1 || comprobacion == -1) {
                    if (tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA) System.out.println("correcto");
                    return true;
                } else {
                    System.out.println("La distancia no es correcta");
                    return false;
                }
            }
        }else {
            System.out.println("No te puedes mover hacia atras");
        }

        System.out.println("mal");
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


