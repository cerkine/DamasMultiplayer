package com.company;

import java.util.Scanner;

public class Juego {
    Tablero tablero;
    int id;
    boolean finished, correctplay;
    int[][] array = new int[10][10];
    Scanner scanner = new Scanner(System.in);

    public void jugar(){
        do {
            if (id == 0){
                System.out.println("Que ficha quieres mover?");
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
                char newcol = scanner.next().toLowerCase().charAt(0);
                int newnumbercol = 0;

                switch (col){
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
                        numbercol = 8;
                        break;
                    case 'j':
                        numbercol = 9;
                        break;
                }
                System.out.println("Introduce la fila");
                int newfila = scanner.nextInt()-1;
                scanner.nextLine();

                do {
                    if (array[numbercol][fila] == 0) {
                        System.out.println("No hay ninguna ficha en esa posición");
                    }
                    else if (array[numbercol][fila] == 2){
                        System.out.println("Esa ficha no es tuya");
                    }
                    else if (array[numbercol][fila] == 1){
                        if (array[newnumbercol][newfila] == 1) {
                            System.out.println("No puedes mover ahi");
                        }
                        else if (array[newcol][newfila] == 0) {
                            array[numbercol][fila] = 0;
                            array[newnumbercol][newfila] = 1;
                            correctplay = true;
                        }
                    }
                }
                while(!correctplay);
            }
        }
        while (!finished);
    }

}
