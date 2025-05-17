package br.com.semaforo.comsemaforo;

import java.util.concurrent.Semaphore;

public class ContaBancaria {
    private int saldo;
    private final Semaphore semaphore = new Semaphore(1); // 1 = acesso exclusivo

    public ContaBancaria(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void sacar(int valor) {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println("⏳ " + threadName + " esperando para sacar...");
            semaphore.acquire();
            System.out.println("🔒 " + threadName + " entrou no método sacar");

            if (saldo >= valor) {
                System.out.println(threadName + " está sacando: " + valor);
                saldo -= valor;
                sleep(500);
                System.out.println("💰 " + threadName + " novo saldo após saque: " + saldo);
            } else {
                System.out.println("❌ " + threadName + " tentou sacar, mas saldo insuficiente.");
            }

            System.out.println("🔓 " + threadName + " saiu do método sacar\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public void depositar(int valor) {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println("⏳ " + threadName + " esperando para depositar...");
            semaphore.acquire();
            System.out.println("🔒 " + threadName + " entrou no método depositar");

            System.out.println(threadName + " está depositando: " + valor);
            saldo += valor;
            sleep(500);
            System.out.println("💰 " + threadName + " novo saldo após depósito: " + saldo);

            System.out.println("🔓 " + threadName + " saiu do método depositar\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    private void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public int getSaldo() {
        return saldo;
    }
}

