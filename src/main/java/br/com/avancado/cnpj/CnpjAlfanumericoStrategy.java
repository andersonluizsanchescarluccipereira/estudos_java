package br.com.avancado.cnpj;

public class CnpjAlfanumericoStrategy implements CnpjStrategy {

    @Override
    public boolean aceita(String cnpj) {
        String limpo = cnpj.replaceAll("[^A-Z\\d]", "").toUpperCase();
        return limpo.length() == 14 && limpo.matches(".*[A-Z]+.*");
    }

    @Override
    public IdentificadorCNPJ criar(String cnpj) {
        return new CNPJAlfanumerico(cnpj);
    }
}
