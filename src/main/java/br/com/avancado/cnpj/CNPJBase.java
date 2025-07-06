package br.com.avancado.cnpj;

import java.util.Objects;

public abstract class CNPJBase {
    protected String numeroLimpo;

    protected CNPJBase(String numeroLimpo) {
        this.numeroLimpo = numeroLimpo;
    }
    protected String limpar(String cnpj) {
        return Objects.requireNonNull(cnpj)
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();
    }
}
