package br.com.avancado.cnpj;

import java.util.Arrays;
import java.util.List;

public class CnpjAdaptador {

    private static final List<CnpjStrategy> STRATEGIES = Arrays.asList(
            new CnpjComumStrategy(),
            new CnpjAlfanumericoStrategy()
    );

    public static IdentificadorCNPJ criar(String cnpj) {
        for (CnpjStrategy strategy : STRATEGIES) {
            if (strategy.aceita(cnpj)) {
                return strategy.criar(cnpj);
            }
        }
        throw new IllegalArgumentException("CNPJ inválido: " + cnpj);
    }
}
