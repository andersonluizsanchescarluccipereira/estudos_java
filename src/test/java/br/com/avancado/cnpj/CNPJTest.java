package br.com.avancado.cnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CNPJTest {

    @Test
    public void deveCriarCnpjNumericoValido() {
        CNPJ cnpj = new CNPJ("11222333000181");

        assertEquals("11222333000181", cnpj.getNumeroLimpo());
        assertEquals("11.222.333/0001-81", cnpj.getNumeroFormatado());
        assertTrue(cnpj.isNumerico());
        assertFalse(cnpj.isAlfanumerico());
    }

    @Test
    public void deveRemoverCaracteresEspeciaisENormalizar() {
        CNPJ cnpj = new CNPJ("11.222.333/0001-81");

        assertEquals("11222333000181", cnpj.getNumeroLimpo());
    }

    @Test
    public void deveLancarExcecaoParaCnpjComMenosDe14Numeros() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("112223330001"));
    }

    @Test
    public void deveLancarExcecaoParaCnpjComMaisDe14Numeros() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("1122233300018111"));
    }

    @Test
    public void deveLancarExcecaoParaCnpjComLetras() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("AB222333000181"));
    }

    @Test
    public void deveLancarExcecaoParaNull() {
        assertThrows(NullPointerException.class, () -> new CNPJ(null));
    }
}