package br.com.avancado.cnpj;

import java.util.Objects;

public class CNPJ implements IdentificadorCNPJ {

    private final String numeroLimpo;
    public CNPJ(String cnpj) {
        if (cnpj == null) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo");
        }
        String apenasNumeros = cnpj.replaceAll("[^\\d]", "");

        if (apenasNumeros.length() != 14 || !isValid(apenasNumeros)) {
            throw new IllegalArgumentException("CNPJ inválido: " + cnpj);
        }
        this.numeroLimpo = apenasNumeros;
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
        if (cnpj.chars().distinct().count() == 1) {
            return false; // Exclui sequências como 000... ou 111...
        }

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
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CNPJ)) return false;
        CNPJ cnpj = (CNPJ) o;
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
