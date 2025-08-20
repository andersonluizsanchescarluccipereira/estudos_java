package br.com.avancado.cnpj;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CnpjConversorReversivelTest {

    private static final String[] TESTES = {
            "12ABC34501DE35",  // válido
            "1345C3A5000106",  // válido
            "R55231B3000700",  // DV errado
            "1345c3A5000106",  // letra minúscula
            "90.021.382/0001-22",
            "90.024.778/000123",
            "90.025.108/000101", // DV errado
            "90.025.255/0001",  // tamanho inválido
            "90.024.420/0001A2" // letra no DV
    };

    @Test
    public void deveConverterNumericoParaAlfanumericoEReverter() {
        for (String cnpj : TESTES) {
            // Limpa CNPJ, removendo pontuação para conversão
            String numeroLimpo = cnpj.replaceAll("[^\\d]", "");

            // Apenas CNPJs de 14 dígitos numéricos podem ser convertidos
            if (numeroLimpo.matches("\\d{14}")) {
                String cnpjAlfa = CnpjConversorReversivel.numericoParaAlfanumerico(numeroLimpo);
                String cnpjNumericoDeVolta = CnpjConversorReversivel.alfanumericoParaNumerico(cnpjAlfa);

                assertEquals(numeroLimpo, cnpjNumericoDeVolta,
                        "Falha na reversibilidade do CNPJ: " + numeroLimpo);

                // Validação de tipos
                assertTrue(CnpjConversorReversivel.ehNumerico(numeroLimpo));
                assertFalse(CnpjConversorReversivel.ehAlfanumerico(numeroLimpo));
                assertTrue(CnpjConversorReversivel.ehAlfanumerico(cnpjAlfa));
                assertFalse(CnpjConversorReversivel.ehNumerico(cnpjAlfa));
            }
        }
    }

    @Test
    public void deveLancarExcecaoParaNumericosInvalidos() {
        String[] invalidos = {
                "1345c3A5000106", // letra minúscula no alfanumérico
                "90.025.255/0001", // menos de 14 dígitos numéricos
                "90.024.420/0001A2" // letra no DV
        };

        for (String cnpj : invalidos) {
            String numeroLimpo = cnpj.replaceAll("[^\\d]", "");
            if (numeroLimpo.length() != 14) {
                assertThrows(IllegalArgumentException.class, () -> {
                    CnpjConversorReversivel.numericoParaAlfanumerico(numeroLimpo);
                }, "Deveria lançar exceção para: " + cnpj);
            }
        }
    }

    @Test
    public void deveLancarExcecaoParaAlfanumericosInvalidos() {
        String[] invalidos = {
                "R55231B3000700",  // letra fora do mapeamento A-J
                "1345c3A5000106",  // letra minúscula
                "90.024.420/0001A2" // letra fora do mapeamento
        };

        for (String cnpj : invalidos) {
            // Remover pontuação e deixar apenas letras/dígitos
            String alfaLimpo = cnpj.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
            if (!alfaLimpo.matches("[A-J]{14}")) {
                assertThrows(IllegalArgumentException.class, () -> {
                    CnpjConversorReversivel.alfanumericoParaNumerico(alfaLimpo);
                }, "Deveria lançar exceção para: " + cnpj);
            }
        }
    }

    @Test
    public void deveIdentificarNumericoEAlfanumerico() {
        String numerico = "12345678000195";
        String alfanumerico = CnpjConversorReversivel.numericoParaAlfanumerico(numerico);

        assertTrue(CnpjConversorReversivel.ehNumerico(numerico));
        assertFalse(CnpjConversorReversivel.ehAlfanumerico(numerico));

        assertTrue(CnpjConversorReversivel.ehAlfanumerico(alfanumerico));
        assertFalse(CnpjConversorReversivel.ehNumerico(alfanumerico));
    }
}
