package corida_vix;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CountDownLatch;

public class CorridaCarros {
    public static void main(String[] args) throws InterruptedException {
        int numeroCarros = 10; // Número total de carros
        int linhaChegada = 100; // Distância total da linha de chegada

        Semaphore semaphore = new Semaphore(1);
        List<Carro> carros = new ArrayList<>();
        CountDownLatch allCarsFinished = new CountDownLatch(numeroCarros); // Contador para aguardar carros terminarem

        for (int i = 1; i <= numeroCarros; i++) {
            Carro carro = new Carro(i, linhaChegada, semaphore, allCarsFinished); // Passa i como ID
            carros.add(carro);
            carro.start();
        }

        allCarsFinished.await(); // Aguarda até que todos os carros terminem

        // classificar os carros com base em suas posições
        Collections.sort(carros, (carro1, carro2) -> Integer.compare(carro1.getPosicao(), carro2.getPosicao()));

        // Armazena as posições dos carros que chegaram
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < 3 && i < carros.size(); i++) {
            posicoes.add((int) carros.get(i).getId()); // Usando getId() para obter o ID dos três primeiros carros
        }

        System.out.println("Os carros nas posições de 1 a 3 são: " + posicoes);
    }

	
}

