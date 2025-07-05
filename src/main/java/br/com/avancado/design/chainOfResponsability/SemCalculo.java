package br.com.avancado.design.chainOfResponsability;

public class SemCalculo extends Calculo {
    public SemCalculo() {
        super(null); // não há próximo
    }

    @Override
    public String calcular(int numero) {
        return "Nenhum cálculo aplicável";
    }
}
