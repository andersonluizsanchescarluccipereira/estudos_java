package br.com.avancado.cnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CNPJAlfanumericoTest {

    @Test
    public void deveCriarCnpjAlfanumericoValidoComDvCorreto() {
        // Exemplo fictício baseado na documentação, ajuste conforme casos reais
        String cnpjValido = "12ABC34501DE35";
        CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjValido);

        assertEquals("12ABC34501DE35", cnpj.getNumeroLimpo());
        assertEquals("12ABC34501DE35", cnpj.getNumeroFormatado());
        assertTrue(cnpj.isAlfanumerico());
        assertFalse(cnpj.isNumerico());
    }

    @Test
    public void deveRemoverCaracteresEspeciaisENormalizar() {
        String cnpjValidoComMascara = "12.ABC.345/01DE-35";
        CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjValidoComMascara);

        assertEquals("12ABC34501DE35", cnpj.getNumeroLimpo());
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