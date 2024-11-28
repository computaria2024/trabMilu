/*
 * Data de escrita: 27/11/2024
 * Autor: Chat GPT - ADAPTADO por Ígor Rocha
 * Código original adaptado para fins educacionais.
 * Baseado nos conceitos apresentados no capítulo 5 sobre Programação Concorrente em Java.
 */

// Declaração da classe principal do programa
public class SomaMatrizParalela {

    // Declaração de uma classe interna estática que implementa a interface Runnable
    private static class SomaLinha implements Runnable {
        // Declaração de variáveis de instância
        private int[] linha; // Armazena a linha da matriz
        private int resultado; // Armazena o resultado da soma da linha
        private int indice; // Armazena o índice da linha

        // Construtor da classe SomaLinha
        public SomaLinha(int[] linha, int indice) {
            this.linha = linha; // Inicializa a linha
            this.indice = indice; // Inicializa o índice
        }

        // Método run que será executado pela thread
        @Override
        public void run() {
            resultado = 0; // Inicializa o resultado da soma como 0
            // Loop para somar todos os valores da linha
            for (int valor : linha) {
                resultado += valor; // Adiciona o valor ao resultado
            }
            // Imprime o resultado da soma da linha
            System.out.println("Thread para linha " + indice + " finalizou. Soma: " + resultado);
        }

        // Método para obter o resultado da soma
        public int getResultado() {
            return resultado; // Retorna o resultado da soma
        }
    }

    // Método principal do programa
    public static void main(String[] args) {
        // Declaração e inicialização de uma matriz 4x4 de inteiros
        int[][] matriz = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };

        // Obtém o número de linhas da matriz
        int numLinhas = matriz.length;
        // Declaração de um array de threads
        Thread[] threads = new Thread[numLinhas];
        // Declaração de um array de objetos SomaLinha
        SomaLinha[] somas = new SomaLinha[numLinhas];

        // Loop para criar e iniciar as threads
        for (int i = 0; i < numLinhas; i++) {
            // Cria um objeto SomaLinha para cada linha da matriz
            somas[i] = new SomaLinha(matriz[i], i);
            // Cria uma nova thread para executar o objeto SomaLinha
            threads[i] = new Thread(somas[i]);
            // Inicia a thread
            threads[i].start();
        }

        // Declaração e inicialização da variável somaTotal
        int somaTotal = 0;
        // Loop para aguardar a conclusão das threads e somar os resultados
        for (int i = 0; i < numLinhas; i++) {
            try {
                // Aguarda a conclusão da thread
                threads[i].join();
                // Adiciona o resultado da soma da linha à soma total
                somaTotal += somas[i].getResultado();
            } catch (InterruptedException e) {
                // Trata a exceção caso a thread seja interrompida
                e.printStackTrace();
            }
        }

        // Imprime o resultado da soma total da matriz
        System.out.println("Soma total da matriz: " + somaTotal);
    }
}