package br.com.avancado.design.chainOfResponsability;

import java.util.function.Function;
import java.util.function.Supplier;

public class CadeiaCalculoBuilder {
    private Calculo atual;

    private CadeiaCalculoBuilder(Calculo fimDaCadeia) {
        this.atual = fimDaCadeia;
    }

    public static CadeiaCalculoBuilder nova(Function<Void, Calculo> fimDaCadeia) {
        return new CadeiaCalculoBuilder(fimDaCadeia.apply(null));
    }

    public static CadeiaCalculoBuilder nova(Supplier<Calculo> fimDaCadeia) {
        return new CadeiaCalculoBuilder(fimDaCadeia.get());
    }

    public CadeiaCalculoBuilder com(Function<Calculo, Calculo> proximoCalculo) {
        this.atual = proximoCalculo.apply(atual);
        return this;
    }

    public Calculo construir() {
        return atual;
    }
}
