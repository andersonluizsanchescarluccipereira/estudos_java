package br.com.avancado.cnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CNPJAlfanumericoTest {

    @Test
    public void deveCriarCnpjAlfanumericoValidoComLetrasENumeros() {
        String cnpjValido = "12ABC34501DE35"; // Exemplo fictício com DV correto
        CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjValido);
        assertEquals(cnpjValido, cnpj.getNumeroLimpo());
        assertNotNull(cnpj.getNumeroFormatado());
    }

    @Test
    public void deveCriarCnpjAlfanumericoValidoSomenteNumeros() {
        String cnpjValido = "12345678000195";
        CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjValido);
        assertEquals(cnpjValido, cnpj.getNumeroLimpo());
    }

    @Test
    public void deveRecusarCnpjAlfanumericoComDVInvalido() {
        String cnpjInvalido = "12ABC34501DE00";
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico(cnpjInvalido));
    }

    @Test
    public void deveRecusarCnpjAlfanumericoComTamanhoInvalido() {
        String cnpjInvalido = "12ABC34501DE";
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico(cnpjInvalido));
    }

    @Test
    public void deveRecusarCnpjAlfanumericoComCaracteresInvalidos() {
        String cnpjInvalido = "12@BC34501DE35";
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico(cnpjInvalido));
    }

    @Test
    public void deveRecusarCnpjNulo() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJAlfanumerico(null));
    }

    @Test
    public void deveCompararCnpjsAlfanumericosIguais() {
        String cnpjValido = "12ABC34501DE35";
        CNPJAlfanumerico cnpj1 = new CNPJAlfanumerico(cnpjValido);
        CNPJAlfanumerico cnpj2 = new CNPJAlfanumerico(cnpjValido);
        assertEquals(cnpj1, cnpj2);
    }

    @Test
    public void deveGerarHashcodeConsistente() {
        String cnpjValido = "12ABC34501DE35";
        CNPJAlfanumerico cnpj1 = new CNPJAlfanumerico(cnpjValido);
        CNPJAlfanumerico cnpj2 = new CNPJAlfanumerico(cnpjValido);
        assertEquals(cnpj1.hashCode(), cnpj2.hashCode());
    }

    @Test
    public void deveFormatarCorretamenteOCnpj() {
        String cnpjValido = "12ABC34501DE35";
        CNPJAlfanumerico cnpj = new CNPJAlfanumerico(cnpjValido);
        String formatado = cnpj.getNumeroFormatado();
        assertTrue(formatado.contains(".") && formatado.contains("/") && formatado.contains("-"));
    }
}