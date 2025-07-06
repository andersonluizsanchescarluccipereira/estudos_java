package br.com.avancado.cnpj;

import java.util.Objects;

import java.util.Objects;

public class CNPJAlfanumerico implements IdentificadorCNPJ {

    private final String numeroLimpo;

    public CNPJAlfanumerico(String cnpj) {
        this.numeroLimpo = limpar(cnpj);
        if (!numeroLimpo.matches("[A-Z0-9]{14}") || numeroLimpo.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido.");
        }
        if (!validaDigitosVerificadores(numeroLimpo)) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido - dígitos verificadores incorretos.");
        }
    }

    @Override
    public String getNumeroLimpo() {
        return numeroLimpo;
    }

    @Override
    public String getNumeroFormatado() {
        return numeroLimpo;
    }

    @Override
    public boolean isNumerico() {
        return false;
    }

    @Override
    public boolean isAlfanumerico() {
        return true;
    }

    private String limpar(String cnpj) {
        return Objects.requireNonNull(cnpj).replaceAll("[^A-Za-z0-9]", "");
    }

    private boolean validaDigitosVerificadores(String cnpj) {
        String base = numeroLimpo.substring(0, 12);
        int dv1 = calculaDV(base, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int dv2 = calculaDV(base + dv1, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return dv1 == charToInt(numeroLimpo.charAt(12)) &&
                dv2 == charToInt(numeroLimpo.charAt(13));
    }

    private int calculaDV(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += charToInt(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto == 0 || resto == 1) ? 0 : 11 - resto;
    }

    private int charToInt(char c) {
        if (Character.isDigit(c)) {
            return c - '0';
        }
        // Conforme documentação: subtrair 48 do valor ASCII
        return c - 48;
    }
}