package controle;

import gals.LexicalError;
import gals.Lexico;
import gals.SemanticError;
import gals.Semantico;
import gals.Sintatico;
import gals.SyntaticError;
import visao.JanelaPrincipal;

// Classe desenvolvida por Lucas e Willian
public class AnalisadorSemantico {

    private final JanelaPrincipal janelaPrincipal;

    public AnalisadorSemantico(JanelaPrincipal jP) {
        this.janelaPrincipal = jP;
    }

    public void analisarSemantica(String codigo) {
        Lexico analisadorLexico = new Lexico();
        Sintatico analisadorSintatico = new Sintatico();
        Semantico analisadorSemantico = new Semantico();

        analisadorLexico.setInput(codigo);
        try {
            analisadorSintatico.setExecutaAcoesSemanticas(true);
            analisadorSintatico.parse(analisadorLexico, analisadorSemantico);
            janelaPrincipal.mostrarResultadoDaAnalise("Análise semântica sem erros.\n");
        } catch (LexicalError | SyntaticError ex) {
            janelaPrincipal.setCursorNoErro(ex.getPosition());
            janelaPrincipal.mostrarResultadoDaAnalise("Erro sintático na posição: " + ex.getPosition() + "\n\n" + ex.getMessage() + "\n\n");
        } catch (SemanticError ex) {
            janelaPrincipal.setCursorNoErro(ex.getPosition());
            janelaPrincipal.mostrarResultadoDaAnalise("Erro Semântico na posição: " + ex.getPosition() + "\n\n" + ex.getMessage() + "\n\n");
        } finally {

        }
    }
}
