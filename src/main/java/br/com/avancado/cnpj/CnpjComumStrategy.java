package br.com.avancado.cnpj;

public class CnpjComumStrategy implements CnpjStrategy {

    @Override
    public boolean aceita(String cnpj) {
        String limpo = cnpj.replaceAll("[^\\d]", "");
        return limpo.length() == 14 && limpo.matches("\\d{14}");
    }

    @Override
    public IdentificadorCNPJ criar(String cnpj) {
        return new CNPJ(cnpj);
    }
}
