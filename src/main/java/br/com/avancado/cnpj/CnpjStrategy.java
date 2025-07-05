package br.com.avancado.cnpj;

public interface CnpjStrategy {

    boolean aceita(String cnpj);

    IdentificadorCNPJ criar(String cnpj);

}
