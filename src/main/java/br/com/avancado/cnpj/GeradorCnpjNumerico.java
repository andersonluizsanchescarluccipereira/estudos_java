package br.com.avancado.cnpj;

import java.util.Random;

public class GeradorCnpjNumerico {

    private static final Random RANDOM = new Random();

    public static String gerar() {
        String base = gerarBase(12);
        int dv1 = calculaDV(base, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int dv2 = calculaDV(base + dv1, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return base + dv1 + dv2;
    }

    private static String gerarBase(int tamanho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    private static int calculaDV(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
