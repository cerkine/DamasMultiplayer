package com.company;

public class Tablero {

    private int[][] mesa = new int[10][10];
    public final int CASGRIS = 0;
    public final int CASROJA = 1;
    public final int FICAZUL = 2;
    public final int FICAMARILLO = 3;

    private final String[] simbolos = {"\u001B[37m⬛","\u001B[31m⬛","\u001B[34m⚫","\u001B[33m⚫"};
    private final String[] letras = {"\u001B[37mⒶ","\u001B[31mⒷ","\u001B[37mⒸ","\u001B[31mⒹ","\u001B[37mⒺ","\u001B[31mⒻ","\u001B[37mⒼ","\u001B[31mⒽ","\u001B[37mⒾ","\u001B[31mⒿ"};
    public Tablero(){
        pintarCasillas();
    }

    public int[][] getMesa() {
        return mesa;
    }

    public void dibujarTablero(){
        for (int i = 0; i < mesa.length; i++) {
            for (int j = 0; j < mesa.length; j++) {
                System.out.print(" "+simbolos[mesa[i][j]]+"\u001B[0m");
            }
            System.out.print("  "+(i+1));
            System.out.println();
        }
        for (int i = 0; i <letras.length; i++) {
            System.out.print(" "+letras[i]);
        }
    }

    private void pintarCasillas(){
        for (int i = 0; i < mesa.length; i++) {
            for (int j = 0; j < mesa.length; j++) {
                if (i%2 == 0){
                    if (j%2==0)mesa[i][j]=0;
                    else mesa[i][j]=1;
                }else{
                    if (j%2==0)mesa[i][j]=1;
                    else mesa[i][j]=0;
                }
            }
        }
        for (int i = 0; i <mesa.length; i++) {
            for (int j = 0; j < mesa.length; j++) {
                if (!(i == 4 || i == 5)){
                    if (mesa[i][j]== CASROJA){
                        if (i/5==0)mesa[i][j] = FICAMARILLO;
                        else mesa[i][j] = FICAZUL;
                    }
                }
            }
        }
    }
    public boolean checkWinner(){
        int contadorAmarillo =0;
        int contadorAzul = 0;
        for (int i = 0; i < mesa.length; i++) {
            for (int j = 0; j < mesa.length; j++) {
                if (mesa[i][j] == FICAMARILLO)contadorAmarillo++;
                else if (mesa[i][j] == FICAZUL)contadorAzul++;
            }
        }
        if (contadorAmarillo == 0 || contadorAzul == 0)return true;
        return false;
    }
}
