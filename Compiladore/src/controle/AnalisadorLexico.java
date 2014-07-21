package controle;

import gals.LexicalError;
import gals.Lexico;
import gals.Token;
import visao.JanelaPrincipal;

// Classe desenvolvida por Lucas e Willian
public class AnalisadorLexico {

    private final JanelaPrincipal janelaPrincipal;

    public AnalisadorLexico(JanelaPrincipal jP) {
        this.janelaPrincipal = jP;
    }

    public void analisarLexico(String codigo) {

        Lexico analisadorLexico = new Lexico();
        analisadorLexico.setInput(codigo);

        try {
            Token token = null;
            while ((token = analisadorLexico.nextToken()) != null) {
            }
            janelaPrincipal.mostrarResultadoDaAnalise("Análise léxica sem erros.\n");
        } catch (LexicalError e) {
            janelaPrincipal.setCursorNoErro(e.getPosition());
            janelaPrincipal.mostrarResultadoDaAnalise("Erro léxico na posição: " + e.getPosition() + "\n\n" + e.getMessage() + "\n\n");
        } 
    }

}
