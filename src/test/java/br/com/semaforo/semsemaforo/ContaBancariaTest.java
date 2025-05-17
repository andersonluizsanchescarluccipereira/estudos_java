package br.com.semaforo.semsemaforo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Test
    void testDepositar() {
        ContaBancaria conta = new ContaBancaria(100);
        conta.depositar(50); // saldo deve ir para 150

        assertEquals(150, getSaldoPorReflexao(conta));
    }

    @Test
    void testSacarComSaldoSuficiente() {
        ContaBancaria conta = new ContaBancaria(200);
        conta.sacar(100); // saldo deve ir para 100

        assertEquals(100, getSaldoPorReflexao(conta));
    }

    @Test
    void testSacarComSaldoInsuficiente() {
        ContaBancaria conta = new ContaBancaria(50);
        conta.sacar(100); // operação deve ser ignorada

        assertEquals(50, getSaldoPorReflexao(conta));
    }

    // Teste com duas threads acessando a conta
    @Test
    void testConcorrenciaSincronizada() throws InterruptedException {
        ContaBancaria conta = new ContaBancaria(100);

        Thread t1 = new Thread(() -> conta.depositar(100));
        Thread t2 = new Thread(() -> conta.sacar(50));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Esperado: saldo inicial 100 + 100 (depósito) - 50 (saque) = 150
        assertEquals(150, getSaldoPorReflexao(conta));
    }

    // Método auxiliar para acessar saldo privado via reflexão (apenas para fins de teste)
    private int getSaldoPorReflexao(ContaBancaria conta) {
        try {
            var field = ContaBancaria.class.getDeclaredField("saldo");
            field.setAccessible(true);
            return (int) field.get(conta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar saldo via reflexão", e);
        }
    }
}