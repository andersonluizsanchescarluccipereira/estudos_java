package br.com.avancado.design.chainOfResponsability;

public class EfetuarCalculos {

    public static void executarCadeias() {
        Calculo cadeia = CadeiaCalculoBuilder
                .nova(SemCalculo::new)
                .com(CalcularPorTresECinco::new)
                .com(CalcularPorCinco::new)
                .com(CalcularPorTres::new)
                .construir();
        System.out.println(cadeia.calcular(10));
    }

    public static void main(String[] args) {
        executarCadeias();
    }
}
