package br.com.avancado.cnpj;

public class CNPJ extends CNPJBase implements IdentificadorCNPJ {

    public CNPJ(String cnpj) {
        super(cnpj);
        this.numeroLimpo = limpar(cnpj);
        if (!numeroLimpo.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ numérico inválido.");
        }
        if (!validaDigitosVerificadores(numeroLimpo)) {
            throw new IllegalArgumentException("CNPJ inválido - dígitos verificadores incorretos.");
        }
    }

    @Override
    public String getNumeroLimpo() {
        return numeroLimpo;
    }

    @Override
    public String getNumeroFormatado() {
        // Exemplo básico de formatação, pode ajustar conforme desejar
        return numeroLimpo.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    @Override
    public boolean isNumerico() {
        return true;
    }

    @Override
    public boolean isAlfanumerico() {
        return false;
    }

    private boolean validaDigitosVerificadores(String cnpj) {
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            }
            int resto = soma % 11;
            int dv1 = (resto < 2) ? 0 : 11 - resto;

            if (dv1 != Character.getNumericValue(cnpj.charAt(12))) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
            }
            resto = soma % 11;
            int dv2 = (resto < 2) ? 0 : 11 - resto;

            return dv2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }
}