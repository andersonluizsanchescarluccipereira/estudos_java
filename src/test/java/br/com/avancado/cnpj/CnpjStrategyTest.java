package br.com.avancado.cnpj;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CnpjStrategyTestes {

    private final CnpjStrategy cnpjComumStrategy = new CnpjComumStrategy();
    private final CnpjStrategy cnpjAlfanumericoStrategy = new CnpjAlfanumericoStrategy();

    @Test
    public void deveAceitarCnpjComumValido() {
        String cnpjValido = "12.345.678/0001-95";
        assertTrue(cnpjComumStrategy.aceita(cnpjValido));
    }

    @Test
    public void deveRecusarCnpjComumComLetras() {
        String cnpjInvalido = "12AB56780001-95";
        assertFalse(cnpjComumStrategy.aceita(cnpjInvalido));
    }

    @Test
    public void deveCriarCnpjComumValido() {
        String cnpjValido = "12.345.678/0001-95";
        IdentificadorCNPJ cnpj = cnpjComumStrategy.criar(cnpjValido);
        assertNotNull(cnpj);
        assertEquals("12345678000195", cnpj.getNumeroLimpo());
    }

    @Test
    public void deveAceitarCnpjAlfanumericoValido() {
        String cnpjValido = "12ABC34501DE35";
        assertTrue(cnpjAlfanumericoStrategy.aceita(cnpjValido));
    }

    @Test
    public void deveRecusarCnpjAlfanumericoSomenteNumeros() {
        String cnpjInvalido = "12345678000195";
        assertFalse(cnpjAlfanumericoStrategy.aceita(cnpjInvalido));
    }

    @Test
    public void deveCriarCnpjAlfanumericoValido() {
        String cnpjValido = "12ABC34501DE35";
        IdentificadorCNPJ cnpj = cnpjAlfanumericoStrategy.criar(cnpjValido);
        assertNotNull(cnpj);
        assertEquals("12ABC34501DE35", cnpj.getNumeroLimpo());
    }

    @Test
    public void deveDelegarParaStrategyCorretaNoAdaptador() {
        IdentificadorCNPJ cnpj1 = CnpjAdaptador.criar("12.345.678/0001-95");
        assertTrue(cnpj1 instanceof CNPJ);

        IdentificadorCNPJ cnpj2 = CnpjAdaptador.criar("12ABC34501DE35");
        assertTrue(cnpj2 instanceof CNPJAlfanumerico);
    }
    @Test
    public void deveLancarExcecaoParaCnpjInvalidoComMensagem() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CnpjAdaptador.criar("CNPJINVALIDO"));

        assertTrue(ex.getMessage().contains("CNPJ inválido"));
    }
}