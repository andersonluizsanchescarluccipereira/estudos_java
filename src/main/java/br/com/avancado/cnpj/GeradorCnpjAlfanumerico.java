package br.com.avancado.cnpj;

import java.util.Random;

public class GeradorCnpjAlfanumerico {

    private static final Random RANDOM = new Random();
    private static final String ALFANUMERICOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String gerar() {
        String base = gerarBaseAlfanumerica(12);
        int dv1 = calculaDV(base, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int dv2 = calculaDV(base + intToChar(dv1), new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return base + intToChar(dv1) + intToChar(dv2);
    }

    private static String gerarBaseAlfanumerica(int tamanho) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < tamanho) {
            char c = ALFANUMERICOS.charAt(RANDOM.nextInt(ALFANUMERICOS.length()));
            sb.append(c);
        }
        // Garantir que não seja 100% numérico
        if (sb.toString().matches("\\d{" + tamanho + "}")) {
            return gerarBaseAlfanumerica(tamanho);
        }
        return sb.toString();
    }

    private static int calculaDV(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += charToInt(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto == 0 || resto == 1) ? 0 : 11 - resto;
    }

    private static int charToInt(char c) {
        if (Character.isDigit(c)) {
            return c - '0';
        }
        // Segue o mesmo critério que você usou no CNPJAlfanumerico
        return c - 48;
    }

    private static char intToChar(int valor) {
        return (char) (valor + '0');
    }
}
