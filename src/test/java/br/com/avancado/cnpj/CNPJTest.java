package br.com.avancado.cnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CNPJTest {

    @Test
    void deveCriarCNPJValido() {
        CNPJ cnpj = new CNPJ("11.222.333/0001-81");
        assertEquals("11222333000181", cnpj.getNumeroLimpo());
        assertEquals("11.222.333/0001-81", cnpj.getNumeroFormatado());
    }

    @Test
    void deveRecusarCNPJComDigitosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("11.222.333/0001-00"));
    }

    @Test
    void deveRecusarCNPJComTamanhoIncorreto() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("112223330001"));
    }

    @Test
    void deveRecusarCNPJComTodosOsDigitosIguais() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("11.111.111/1111-11"));
    }

    @Test
    void deveSerIgualSeOsCNPJsForemIguais() {
        CNPJ cnpj1 = new CNPJ("11.222.333/0001-81");
        CNPJ cnpj2 = new CNPJ("11222333000181");

        assertEquals(cnpj1, cnpj2);
    }

    @Test
    void deveRecusarCNPJComCaracteresEspeciaisInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("11.22A.333/0001-81@"));
        assertThrows(IllegalArgumentException.class, () -> new CNPJ("abcdefg"));
    }

    @Test
    void deveRecusarCNPJVazio() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ(""));
    }

    @Test
    void deveRecusarCNPJNulo() {
        assertThrows(IllegalArgumentException.class, () -> new CNPJ(null));
    }

}