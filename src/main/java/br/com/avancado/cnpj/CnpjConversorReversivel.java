package br.com.avancado.cnpj;

/**
 * Conversor determinístico reversível de CNPJ numérico para alfanumérico e vice-versa.
 * Mapeamento fixo baseado na tabela: 0 → A, 1 → B, ..., 9 → J.
 */
public class CnpjConversorReversivel {

    private static final char[] MAPEAMENTO_NUMERO_PARA_LETRA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private static final String LETRAS_VALIDAS = "ABCDEFGHIJ";

    /**
     * Converte um CNPJ numérico para alfanumérico de forma determinística.
     * Exemplo: "12345678000195" → "BCDEFGHAAABIF"
     */
    public static String numericoParaAlfanumerico(String cnpjNumerico) {
        StringBuilder resultado = new StringBuilder();
        for (char c : cnpjNumerico.toCharArray()) {
            if (Character.isDigit(c)) {
                int digito = c - '0';
                resultado.append(MAPEAMENTO_NUMERO_PARA_LETRA[digito]);
            } else {
                resultado.append(c); // Mantém pontos, traços, barras, etc.
            }
        }
        return resultado.toString();
    }

    /**
     * Converte um CNPJ alfanumérico para numérico de forma determinística e reversível.
     * Exemplo: "BCDEFGHAAABIF" → "12345678000195"
     */
    public static String alfanumericoParaNumerico(String cnpjAlfanumerico) {
        StringBuilder resultado = new StringBuilder();
        for (char c : cnpjAlfanumerico.toUpperCase().toCharArray()) {
            if (LETRAS_VALIDAS.indexOf(c) >= 0) {
                resultado.append(LETRAS_VALIDAS.indexOf(c));
            } else {
                resultado.append(c); // Mantém pontos, traços, barras, etc.
            }
        }
        return resultado.toString();
    }

    public static boolean ehNumerico(String cnpj) {
        // Exemplo simples, ajuste conforme sua regra
        return cnpj.matches("\\d{14}");
    }

    public static boolean ehAlfanumerico(String cnpj) {
        return cnpj.matches("[A-Z0-9]{14}") && !ehNumerico(cnpj);
    }
}
