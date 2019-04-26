package com.company;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Juego {
    Tablero tablero;
    int id;
    boolean finished, correctplay;
    Scanner scanner;
    Cliente cliente;

    public Juego(){
        tablero = new Tablero();
        id = 2;
        scanner = new Scanner(System.in);
        cliente = new Cliente();
    }

    public void jugar(){
        try {
            cliente.init("localhost", 5555);
            System.out.println("Como te llamas?");
            String name = scanner.nextLine();
            id = cliente.selectPlayer(name);
            if (id == tablero.FICAMARILLO){
                System.out.println("Bienvenido! Eres las amarillas");
            }
            else if (id == tablero.FICAZUL){
                System.out.println("Bienvenido! Eres las azules");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

            do {
                tablero.dibujarTablero();
                System.out.println("\u001B[0m"+"\nQue ficha quieres mover?");
                System.out.println("Introduce la columna");
                char col = scanner.next().toLowerCase().charAt(0);
                int numbercol = 0;

                switch (col){
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
                System.out.println("Introduce la fila");
                int fila = scanner.nextInt() - 1;
                scanner.nextLine();
                System.out.println("A donde la quieres mover?");
                System.out.println("Introduce la columna");
                char newcol = scanner.next().toLowerCase().charAt(0);
                int newnumbercol = 0;

                switch (newcol){
                    case 'a':
                        newnumbercol = 0;
                        break;
                    case 'b':
                        newnumbercol = 1;
                        break;
                    case 'c':
                        newnumbercol = 2;
                        break;
                    case 'd':
                        newnumbercol = 3;
                        break;
                    case 'e':
                        newnumbercol = 4;
                        break;
                    case 'f':
                        newnumbercol = 5;
                        break;
                    case 'g':
                        newnumbercol = 6;
                        break;
                    case 'h':
                        newnumbercol = 7;
                        break;
                    case 'i':
                        newnumbercol = 8;
                        break;
                    case 'j':
                        newnumbercol = 9;
                        break;
                }
                System.out.println("Introduce la fila");
                int newfila = scanner.nextInt()-1;
                scanner.nextLine();


                    if (tablero.getMesa()[fila][numbercol] == tablero.CASGRIS || tablero.getMesa()[fila][numbercol] == tablero.CASROJA) {
                        System.out.println("No hay ninguna ficha en esa posición");
                    }
                    else if (tablero.getMesa()[fila][numbercol] != id){
                        System.out.println("Esa ficha no es tuya");
                    }
                    else if (tablero.getMesa()[fila][numbercol] == id){
                        if (tablero.getMesa()[newfila][newnumbercol] != tablero.CASROJA) {
                            System.out.println("No puedes mover ahi");
                        }
                        else if (tablero.getMesa()[newfila][newnumbercol] == tablero.CASROJA) {
                            tablero.getMesa()[fila][numbercol] = tablero.CASROJA;
                            tablero.getMesa()[newfila][newnumbercol] = id;
                            correctplay = true;
                            try {
                                cliente.runClient(fila, numbercol, newfila, newnumbercol, tablero);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                while(!correctplay);

        }
    }


