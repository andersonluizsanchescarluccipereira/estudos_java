package br.com.avancado.cnpj;

/**
 * Conversor determinístico reversível de CNPJ numérico para alfanumérico e vice-versa.
 * Mapeamento fixo: 0 → A, 1 → B, ..., 9 → J
 */
public class CnpjConversorReversivel {

    private static final char[] MAPEAMENTO_NUMERO_PARA_LETRA =
            {'A','B','C','D','E','F','G','H','I','J'};
    private static final String LETRAS_VALIDAS = "ABCDEFGHIJ";

    /**
     * Converte um CNPJ numérico limpo (14 dígitos) para alfanumérico.
     * Ex.: "12345678000195" → "BCDEFGHAAABIF"
     */
    public static String numericoParaAlfanumerico(String cnpjNumerico) {
        if (cnpjNumerico == null || !cnpjNumerico.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ numérico inválido: deve ter 14 dígitos");
        }

        StringBuilder resultado = new StringBuilder(14);
        for (char c : cnpjNumerico.toCharArray()) {
            int digito = c - '0';
            resultado.append(MAPEAMENTO_NUMERO_PARA_LETRA[digito]);
        }
        return resultado.toString();
    }

    /**
     * Converte um CNPJ alfanumérico limpo (14 caracteres A-J) para numérico.
     * Ex.: "BCDEFGHAAABIF" → "12345678000195"
     */
    public static String alfanumericoParaNumerico(String cnpjAlfanumerico) {
        if (cnpjAlfanumerico == null || !cnpjAlfanumerico.matches("[A-J]{14}")) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido: deve ter 14 caracteres de A a J");
        }

        StringBuilder resultado = new StringBuilder(14);
        for (char c : cnpjAlfanumerico.toCharArray()) {
            resultado.append(LETRAS_VALIDAS.indexOf(c));
        }
        return resultado.toString();
    }

    public static boolean ehNumerico(String cnpj) {
        return cnpj != null && cnpj.matches("\\d{14}");
    }

    public static boolean ehAlfanumerico(String cnpj) {
        return cnpj != null && cnpj.matches("[A-J]{14}");
    }
}