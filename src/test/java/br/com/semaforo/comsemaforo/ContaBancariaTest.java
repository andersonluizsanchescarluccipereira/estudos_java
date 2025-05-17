package br.com.semaforo.comsemaforo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Test
    void testDepositoSimples() {
        ContaBancaria conta = new ContaBancaria(100);
        conta.depositar(50);
        assertEquals(150, conta.getSaldo());
    }

    @Test
    void testSaqueComSaldoSuficiente() {
        ContaBancaria conta = new ContaBancaria(200);
        conta.sacar(100);
        assertEquals(100, conta.getSaldo());
    }

    @Test
    void testSaqueComSaldoInsuficiente() {
        ContaBancaria conta = new ContaBancaria(50);
        conta.sacar(100); // não deve sacar
        assertEquals(50, conta.getSaldo());
    }

    @Test
    void testConcorrenciaComSemaphore() throws InterruptedException {
        ContaBancaria conta = new ContaBancaria(100);

        Thread t1 = new Thread(() -> conta.depositar(100), "Thread-1");
        Thread t2 = new Thread(() -> conta.sacar(50), "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(150, conta.getSaldo());
    }

    @Test
    void testMultiplasThreads() throws InterruptedException {
        ContaBancaria conta = new ContaBancaria(100);

        Thread[] threads = new Thread[10];

        // 5 depósitos de 50
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> conta.depositar(50), "Deposito-" + i);
        }

        // 5 saques de 20
        for (int i = 5; i < 10; i++) {
            threads[i] = new Thread(() -> conta.sacar(20), "Saque-" + i);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        // Saldo esperado: 100 + (5 * 50) - (5 * 20) = 100 + 250 - 100 = 250
        assertEquals(250, conta.getSaldo());
    }
}