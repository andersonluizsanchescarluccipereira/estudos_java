package br.com.avancado.cnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CNPJAlfanumericoTest {

    @Test
    public void deveCriarCnpjAlfanumericoValidoComDvCorreto() {
        // Base alfanumérica (12 caracteres)
        String base = "12ABC34501DE";

        // Calcula DV correto
        String dv = CNPJAlfanumerico.calculaDV(base); // retorna 2 dígitos válidos

        // CNPJ completo com DV correto
        String cnpjValido = base + dv;

        // Instancia a classe
        CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjValido);

        // Asserts
        assertEquals(cnpjValido, cnpj.getNumeroLimpo());
        assertEquals(cnpjValido, cnpj.getNumeroFormatado());
        assertTrue(cnpj.isAlfanumerico());
        assertFalse(cnpj.isNumerico());
    }

    @Test
    public void deveRemoverCaracteresEspeciaisENormalizar() {
    // Base alfanumérica (12 primeiros caracteres)
    String base = "12ABC34501DE";

    // Calcula os dígitos verificadores corretos (DV) para a base
    String dv = CNPJAlfanumerico.calculaDV(base);

    // CNPJ completo válido
    String cnpjValido = base + dv; // ex: "12ABC34501DE35" se dv="35"

    // Adiciona caracteres de formatação para testar limpeza
    String cnpjComMascara = "12.ABC.345/01DE-" + dv;

    // Instancia a classe (valida automaticamente o DV)
    CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjComMascara);

    // Verifica se os caracteres especiais foram removidos corretamente
    assertEquals(cnpjValido, cnpj.getNumeroLimpo());
}

    @Test
    public void deveLancarExcecaoParaCnpjComDvInvalido() {
        String cnpjComDvInvalido = "12ABC34501DE00"; // DV final errado

        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico(cnpjComDvInvalido));
    }

    @Test
    public void deveLancarExcecaoParaCnpjComMenosDe14Caracteres() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico("ABC123"));
    }

    @Test
    public void deveLancarExcecaoParaCnpjComMaisDe14Caracteres() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico("ABC123DEF456GHI7"));
    }

    @Test
    public void deveLancarExcecaoParaCnpjApenasNumerico() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico("12345678901234"));
    }

    @Test
    public void deveLancarExcecaoParaNull() {
        assertThrows(NullPointerException.class, () -> new CNPJAlfanumerico(null));
    }
}