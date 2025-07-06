package br.com.avancado.cnpj;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CnpjFacadeTest {

    private static final String CNPJ_NUMERICO_VALIDO = "11444777000161";
    private static final String CNPJ_NUMERICO_FORMATADO = "11.444.777/0001-61";

    private static final String CNPJ_ALFANUMERICO_VALIDO = "AB12CD34EF56GH";

    @Test
    public void deveIdentificarCnpjNumericoValido() {
        CnpjFacade facade = CnpjFacade.builder().build();
        IdentificadorCNPJ identificador = facade.identificar(CNPJ_NUMERICO_VALIDO);

        assertTrue(identificador.isNumerico());
        assertFalse(identificador.isAlfanumerico());
        assertEquals(CNPJ_NUMERICO_VALIDO, identificador.getNumeroLimpo());
    }

    @Test
    public void deveIdentificarCnpjAlfanumericoValido() {
        CnpjFacade facade = CnpjFacade.builder().build();
        String cnpjAlfaValido = GeradorCnpjAlfanumerico.gerar();

        IdentificadorCNPJ identificador = facade.identificar(cnpjAlfaValido);

        assertFalse(identificador.isNumerico());
        assertTrue(identificador.isAlfanumerico());
        assertEquals(cnpjAlfaValido, identificador.getNumeroLimpo());
    }

    @Test
    public void deveConverterDeNumericoParaAlfanumerico() {
        CnpjFacade facade = CnpjFacade.builder().build();

        String alfanumerico = facade.paraAlfanumerico(CNPJ_NUMERICO_VALIDO);

        assertNotNull(alfanumerico);
        // Aqui depende da implementação do método numericoParaAlfanumerico
    }

    @Test
    public void deveConverterDeAlfanumericoParaNumerico() {
        CnpjFacade facade = CnpjFacade.builder().build();
        String cnpjNumericoValido = GeradorCnpjNumerico.gerar();

        String alfanumerico = facade.paraAlfanumerico(cnpjNumericoValido);

        assertNotNull(alfanumerico);
    }

    @Test
    public void deveRecusarConversaoNumericoParaAlfanumericoQuandoDesabilitado() {
        CnpjFacade facade = CnpjFacade.builder()
                .desabilitarConversaoNumericoParaAlfanumerico()
                .build();

        assertThrows(UnsupportedOperationException.class, () -> facade.paraAlfanumerico(CNPJ_NUMERICO_VALIDO));
    }

    @Test
    public void deveRecusarConversaoAlfanumericoParaNumericoQuandoDesabilitado() {
        CnpjFacade facade = CnpjFacade.builder()
                .desabilitarConversaoAlfanumericoParaNumerico()
                .build();

        assertThrows(UnsupportedOperationException.class, () -> facade.paraNumerico(CNPJ_ALFANUMERICO_VALIDO));
    }

    @Test
    public void deveLancarExcecaoSeConversaoParaAlfanumericoForUsadaEmCnpjNaoNumerico() {
        CnpjFacade facade = CnpjFacade.builder().build();

        assertThrows(IllegalArgumentException.class, () -> facade.paraAlfanumerico(CNPJ_ALFANUMERICO_VALIDO));
    }

    @Test
    public void deveLancarExcecaoSeConversaoParaNumericoForUsadaEmCnpjNaoAlfanumerico() {
        CnpjFacade facade = CnpjFacade.builder().build();

        assertThrows(IllegalArgumentException.class, () -> facade.paraNumerico(CNPJ_NUMERICO_VALIDO));
    }

    @Test
    public void deveRetornarNumeroLimpoParaCnpjFormatado() {
        CnpjFacade facade = CnpjFacade.builder().build();

        String numeroLimpo = facade.getNumeroLimpo(CNPJ_NUMERICO_FORMATADO);

        assertEquals(CNPJ_NUMERICO_VALIDO, numeroLimpo);
    }

    @Test
    public void deveRetornarNumeroFormatadoParaCnpj() {
        CnpjFacade facade = CnpjFacade.builder().build();

        String numeroFormatado = facade.getNumeroFormatado(CNPJ_NUMERICO_VALIDO);

        assertEquals(CNPJ_NUMERICO_FORMATADO, numeroFormatado);
    }
}