package gals;

public interface ScannerConstants {

    int[] TOKEN_STATE = { 0,  0,  0, -1, 42, 43, 50, 48, 37, 49, 38, 51,  4, 52, 36, 40, 41, 39, -1,  2, 44, 45, 46, 47,  3, 54,  5, -2,  5, 53, 56, 55, 57, -1, -1, -1, -1, -2, -1, -1,  2, -1, -1, -1, -2,  0,  5, -2 };

    int[] SPECIAL_CASES_INDEXES =
        { 0, 0, 0, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };

    String[] SPECIAL_CASES_KEYS =
        {  "booleano", "cadeia", "caracter", "de", "div", "e", "enquanto", "entao", "escreva", "faca", "falso", "fim", "funcao", "inicio", "inteiro", "leia", "metodo", "nao", "ou", "procedimento", "programa", "real", "ref", "retorne", "se", "senao", "val", "var", "verdadeiro", "vetor" };

    int[] SPECIAL_CASES_VALUES =
        {  14, 9, 8, 26, 35, 22, 30, 17, 20, 27, 24, 12, 15, 11, 13, 19, 31, 23, 21, 10, 6, 28, 32, 34, 16, 18, 33, 7, 25, 29 };


    String[] SCANNER_ERROR
            = {
                "Caractere não esperado",
                "",
                "",
                "Erro na construção de um literal",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Erro identificando identificador",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Erro identificando número real",
                "Erro identificando comentário",
                "Erro identificando número inteiro",
                "",
                "",
                "",
                "",
                "Erro identificando identificador",
                "Erro identificando identificador",
                "Erro identificando identificador",
                "Erro identificando número real",
                "Erro identificando comentário",
                "Erro identificando identificador",
                "Erro identificando identificador",
                "",
                "Erro identificando identificador",
                "Erro identificando número real",
                "Erro identificando número real",
                "Erro identificando comentário",
                "",
                "",
                "Erro identificando comentário"
            };
}
