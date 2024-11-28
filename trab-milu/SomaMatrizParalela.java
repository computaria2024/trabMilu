/*
 * Data de escrita: 27/11/2024
 * Autor: Chat GPT - ADAPTADO por Ígor Rocha
 * Código original adaptado para fins educacionais.
 * Baseado nos conceitos apresentados no capítulo 5 sobre Programação Concorrente em Java.
 */

public class SomaMatrizParalela {

    private static class SomaLinha implements Runnable {
        private int[] linha;
        private int resultado;
        private int indice;

        public SomaLinha(int[] linha, int indice) {
            this.linha = linha;
            this.indice = indice;
        }

        @Override
        public void run() {
            resultado = 0;
            for (int valor : linha) {
                resultado += valor;
            }
            System.out.println("Thread para linha " + indice + " finalizou. Soma: " + resultado);
        }

        public int getResultado() {
            return resultado;
        }
    }

    public static void main(String[] args) {
        int[][] matriz = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };

        int numLinhas = matriz.length;
        Thread[] threads = new Thread[numLinhas];
        SomaLinha[] somas = new SomaLinha[numLinhas];

        for (int i = 0; i < numLinhas; i++) {
            somas[i] = new SomaLinha(matriz[i], i);
            threads[i] = new Thread(somas[i]);
            threads[i].start();
        }

        int somaTotal = 0;
        for (int i = 0; i < numLinhas; i++) {
            try {
                threads[i].join();
                somaTotal += somas[i].getResultado();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Soma total da matriz: " + somaTotal);
    }
}