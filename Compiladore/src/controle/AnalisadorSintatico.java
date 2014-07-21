package controle;

import gals.LexicalError;
import gals.Lexico;
import gals.SemanticError;
import gals.Sintatico;
import gals.SyntaticError;
import visao.JanelaPrincipal;

// Classe desenvolvida por Lucas e Willian
public class AnalisadorSintatico {

    private final JanelaPrincipal janelaPrincipal;

    public AnalisadorSintatico(JanelaPrincipal jP) {
        this.janelaPrincipal = jP;
    }

    public void analisarSintaxe(String codigo) {
        Lexico analisadorLexico = new Lexico();
        Sintatico analisadorSintatico = new Sintatico();

        analisadorLexico.setInput(codigo);

        try {
            analisadorSintatico.setExecutaAcoesSemanticas(false);
            analisadorSintatico.parse(analisadorLexico, null);
            janelaPrincipal.mostrarResultadoDaAnalise("Análise sintática sem erros.\n");
        } catch (LexicalError | SyntaticError ex) {
            janelaPrincipal.setCursorNoErro(ex.getPosition());
            janelaPrincipal.mostrarResultadoDaAnalise("Erro sintático na posição: " + ex.getPosition() + "\n\n" + ex.getMessage() + "\n\n");
        } catch (SemanticError ex) {
            janelaPrincipal.mostrarResultadoDaAnalise("Erro Semântico na posição: " + ex.getPosition() + "\n\n" + ex.getMessage() + "\n\n");
        }
    }
}
