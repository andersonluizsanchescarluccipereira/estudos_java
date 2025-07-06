package br.com.avancado.cnpj;

public interface IdentificadorCNPJ {

    String getNumeroLimpo();

    String getNumeroFormatado();

    boolean isNumerico();

    boolean isAlfanumerico();
}
