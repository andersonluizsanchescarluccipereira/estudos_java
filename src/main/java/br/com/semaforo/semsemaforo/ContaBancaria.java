package br.com.semaforo.semsemaforo;

public class ContaBancaria {
    private int saldo;

    public ContaBancaria(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized void sacar(int valor) {
        String threadName = Thread.currentThread().getName();
        System.out.println("🔒 " + threadName + " entrou no método sacar");

        if (saldo >= valor) {
            System.out.println(threadName + " está sacando: " + valor);
            saldo -= valor;
            sleep(2500); // Simula processamento
            System.out.println("💰 " + threadName + " novo saldo após saque: " + saldo);
        } else {
            System.out.println("❌ " + threadName + " tentou sacar, mas saldo insuficiente.");
        }

        System.out.println("🔓 " + threadName + " saiu do método sacar\n");
    }

    public synchronized void depositar(int valor) {
        String threadName = Thread.currentThread().getName();
        System.out.println("🔒 " + threadName + " entrou no método depositar");

        System.out.println(threadName + " está depositando: " + valor);
        saldo += valor;
        sleep(1500); // Simula processamento
        System.out.println("💰 " + threadName + " novo saldo após depósito: " + saldo);

        System.out.println("🔓 " + threadName + " saiu do método depositar\n");
    }

    private void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
