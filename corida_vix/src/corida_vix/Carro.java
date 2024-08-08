package corida_vix;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CountDownLatch; // Importe a classe CountDownLatch

class Carro extends Thread {
    private int id;
    private int distanciaPercorrida;
    private int acelerou;
    private int linhaChegada;
    private Semaphore semaphore;
    private int posicao; // Adicione um campo para rastrear a posição
    private CountDownLatch allCarsFinished; // Adicionar um CountDownLatch

    public Carro(int id, int linhaChegada, Semaphore semaphore, CountDownLatch allCarsFinished) {
        this.id = id;
        this.distanciaPercorrida = 0;
        this.acelerou = 0;
        this.linhaChegada = linhaChegada;
        this.semaphore = semaphore;
        this.posicao = 0; // Inicialize a posição como 0
        this.allCarsFinished = allCarsFinished;
    }

    public void run() {
        while (distanciaPercorrida < linhaChegada) {
            try {
                semaphore.acquire();
                int aceleracao = new Random().nextInt(10) + 1; // Gera uma aceleração randômica
                distanciaPercorrida += aceleracao;
                acelerou++;
                posicao++; // Atualiza a posição do carro
                System.out.println("Carro " + id + " acelerou " + aceleracao + "KM e já percorreu " + distanciaPercorrida + "KM");
                semaphore.release();
                Thread.sleep(100); // Simula um intervalo de tempo entre as iterações
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        allCarsFinished.countDown(); // Sinaliza que o carro terminou a corrida
    }	

    public int getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public int getPulos() {
        return acelerou;
    }

    public int getPosicao() {
        return posicao;
    }
}
