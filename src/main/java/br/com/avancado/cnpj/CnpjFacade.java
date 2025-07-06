package br.com.avancado.cnpj;

/**
 * Facade para abstrair o uso de CNPJ comum, alfanumérico, adaptador e conversão reversível.
 * Agora com suporte a padrão Builder.
 */
public class CnpjFacade {

    private final boolean permitirConversaoNumericoParaAlfanumerico;
    private final boolean permitirConversaoAlfanumericoParaNumerico;

    private CnpjFacade(Builder builder) {
        this.permitirConversaoNumericoParaAlfanumerico = builder.permitirConversaoNumericoParaAlfanumerico;
        this.permitirConversaoAlfanumericoParaNumerico = builder.permitirConversaoAlfanumericoParaNumerico;
    }

    public static Builder builder() {
        return new Builder();
    }

    public IdentificadorCNPJ identificar(String cnpj) {
        return CnpjAdaptador.criar(cnpj);
    }

    public String paraAlfanumerico(String cnpjNumerico) {
        if (!permitirConversaoNumericoParaAlfanumerico) {
            throw new UnsupportedOperationException("Conversão para alfanumérico está desabilitada neste Facade.");
        }

        IdentificadorCNPJ identificador = CnpjAdaptador.criar(cnpjNumerico);

        if (!(identificador instanceof CNPJ)) {
            throw new IllegalArgumentException("A conversão para alfanumérico só é permitida para CNPJ comum numérico.");
        }

        return CnpjConversorReversivel.numericoParaAlfanumerico(cnpjNumerico);
    }

    public String paraNumerico(String cnpjAlfanumerico) {
        if (!permitirConversaoAlfanumericoParaNumerico) {
            throw new UnsupportedOperationException("Conversão para numérico está desabilitada neste Facade.");
        }

        IdentificadorCNPJ identificador = CnpjAdaptador.criar(cnpjAlfanumerico);

        if (!(identificador instanceof CNPJAlfanumerico)) {
            throw new IllegalArgumentException("A conversão para numérico só é permitida para CNPJ alfanumérico.");
        }

        return CnpjConversorReversivel.alfanumericoParaNumerico(cnpjAlfanumerico);
    }

    public String getNumeroLimpo(String cnpj) {
        return identificar(cnpj).getNumeroLimpo();
    }

    public String getNumeroFormatado(String cnpj) {
        return identificar(cnpj).getNumeroFormatado();
    }

    public static class Builder {

        private boolean permitirConversaoNumericoParaAlfanumerico = true;
        private boolean permitirConversaoAlfanumericoParaNumerico = true;

        public Builder desabilitarConversaoNumericoParaAlfanumerico() {
            this.permitirConversaoNumericoParaAlfanumerico = false;
            return this;
        }

        public Builder desabilitarConversaoAlfanumericoParaNumerico() {
            this.permitirConversaoAlfanumericoParaNumerico = false;
            return this;
        }

        public CnpjFacade build() {
            return new CnpjFacade(this);
        }
    }
}
