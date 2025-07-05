package br.com.avancado.cnpj;

import java.util.Objects;

public class CNPJAlfanumerico implements IdentificadorCNPJ {
    private final String numeroLimpo;

    public CNPJAlfanumerico(String cnpj) {
        if (cnpj == null) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo");
        }
        String apenasAlfanumerico = cnpj.replaceAll("[^A-Z\\d]", "").toUpperCase();

        if (apenasAlfanumerico.length() != 14 || !isValid(apenasAlfanumerico)) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido: " + cnpj);
        }
        this.numeroLimpo = apenasAlfanumerico;
    }

    @Override
    public String getNumeroLimpo() {
        return numeroLimpo;
    }

    @Override
    public String getNumeroFormatado() {
        return String.format("%s.%s.%s/%s-%s",
                numeroLimpo.substring(0, 2),
                numeroLimpo.substring(2, 5),
                numeroLimpo.substring(5, 8),
                numeroLimpo.substring(8, 12),
                numeroLimpo.substring(12));
    }

    private boolean isValid(String cnpj) {
        String cnpjBase = cnpj.substring(0, 12);
        String dvCalculado = calculateDV(cnpjBase);
        return cnpj.endsWith(dvCalculado);
    }

    private String calculateDV(String cnpjBase) {
        int firstDV = calculateDigit(cnpjBase, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int secondDV = calculateDigit(cnpjBase + firstDV, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        return String.valueOf(firstDV) + secondDV;
    }

    private int calculateDigit(String cnpj, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            int valor = valorParaCalculo(cnpj.charAt(i));
            soma += valor * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private int valorParaCalculo(char c) {
        if (Character.isDigit(c)) {
            return c - '0';
        } else if (Character.isLetter(c)) {
            return c - 48;
        } else {
            throw new IllegalArgumentException("Caractere inválido no CNPJ: " + c);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CNPJAlfanumerico)) return false;
        CNPJAlfanumerico cnpj = (CNPJAlfanumerico) o;
        return numeroLimpo.equals(cnpj.numeroLimpo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroLimpo);
    }

    @Override
    public String toString() {
        return getNumeroFormatado();
    }
}
