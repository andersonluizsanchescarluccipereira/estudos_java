package br.com.avancado.cnpj;

public class CNPJAlfanumerico extends CNPJBase implements IdentificadorCNPJ {

    // Regras de formação
    private static final int TAMANHO_CNPJ_SEM_DV = 12;
    private static final String REGEX_CARACTERES_FORMATACAO = "[./-]";
    private static final String REGEX_FORMACAO_BASE_CNPJ = "[A-Z\\d]{12}";
    private static final String REGEX_FORMACAO_DV = "\\d{2}"; // DV continua sendo numérico (mod 11 → 0..9)
    private static final String REGEX_VALOR_ZERADO = "^[0]+$";

    // Pesos (dv2 inclui 6 na frente)
    private static final int[] PESOS_DV = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

    // ============================
    // Construtor (valida ao criar)
    // ============================
    public CNPJAlfanumerico(String cnpj) {
        super(cnpj);
        // Usa o limpar da CNPJBase (e garante maiúsculas para letras)
        this.numeroLimpo = limpar(cnpj).replaceAll(REGEX_CARACTERES_FORMATACAO, "").toUpperCase();

        // Formação: 12 alfanuméricos + 2 dígitos (DV)
        if (!isCnpjFormacaoValidaComDV(this.numeroLimpo)) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido: formação incorreta.");
        }

        // Opcional: reforça que esta classe é 'alfanumérica' (não aceita 14 dígitos puros)
        if (this.numeroLimpo.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido: não pode ser apenas numérico.");
        }

        String base = this.numeroLimpo.substring(0, TAMANHO_CNPJ_SEM_DV);
        String dvCalculado = calculaDV(base);
        String dvInformado = this.numeroLimpo.substring(TAMANHO_CNPJ_SEM_DV);

        if (!dvCalculado.equals(dvInformado)) {
            throw new IllegalArgumentException("CNPJ alfanumérico inválido - dígitos verificadores incorretos.");
        }
    }

    // ============================
    // Implementações da interface
    // ============================
    @Override
    public String getNumeroLimpo() {
        return numeroLimpo;
    }

    @Override
    public String getNumeroFormatado() {
        // Se quiser, aplique uma máscara custom (alfanumérico) no futuro
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

    // ============================
    // API estática de validação/DP
    // ============================
    public static boolean isValid(String cnpj) {
        if (cnpj == null) return false;
        String limpo = removeCaracteresFormatacao(cnpj).toUpperCase();

        // Formação correta + não todo zero + não somente dígitos (para esta classe)
        if (!isCnpjFormacaoValidaComDV(limpo) || limpo.matches("\\d{14}")) return false;

        String dvCalc = calculaDV(limpo.substring(0, TAMANHO_CNPJ_SEM_DV));
        return dvCalc.equals(limpo.substring(TAMANHO_CNPJ_SEM_DV));
    }

    public static String calculaDV(String baseCnpj) {
        if (baseCnpj == null) {
            throw new IllegalArgumentException("Cnpj nulo não é válido para o cálculo do DV");
        }
        String base = removeCaracteresFormatacao(baseCnpj).toUpperCase();

        if (!isCnpjFormacaoValidaSemDV(base)) {
            throw new IllegalArgumentException(String.format("Cnpj %s não é válido para o cálculo do DV", baseCnpj));
        }

        String dv1 = String.valueOf(calculaDigito(base));
        String dv2 = String.valueOf(calculaDigito(base.concat(dv1)));
        return dv1.concat(dv2);
    }

    // ============================
    // Cálculo dos dígitos
    // ============================
    private static int calculaDigito(String cnpjParcial) {
        int soma = 0;
        for (int indice = cnpjParcial.length() - 1; indice >= 0; indice--) {
            int valor = charToInt(cnpjParcial.charAt(indice));      // base 36: 0-9 → 0..9, A-Z → 10..35
            int posPeso = PESOS_DV.length - cnpjParcial.length() + indice;
            soma += valor * PESOS_DV[posPeso];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto; // resultado é 0..9 (DV numérico)
    }

    // Converte caractere alfanumérico para inteiro (base 36)
    private static int charToInt(char c) {
        if (Character.isDigit(c)) return c - '0';
        if (Character.isUpperCase(c)) return 10 + (c - 'A');
        throw new IllegalArgumentException("Caractere inválido no CNPJ: " + c);
    }

    // ============================
    // Helpers de validação/limpeza
    // ============================
    private static String removeCaracteresFormatacao(String cnpj) {
        return cnpj.trim().replaceAll(REGEX_CARACTERES_FORMATACAO, "");
    }

    private static boolean isCnpjFormacaoValidaSemDV(String cnpj) {
        return cnpj.matches(REGEX_FORMACAO_BASE_CNPJ) && !cnpj.matches(REGEX_VALOR_ZERADO);
    }

    private static boolean isCnpjFormacaoValidaComDV(String cnpj) {
        return cnpj.matches(REGEX_FORMACAO_BASE_CNPJ.concat(REGEX_FORMACAO_DV))
                && !cnpj.matches(REGEX_VALOR_ZERADO);
    }
}