package controle;

// Classe desenvolvida por Lucas e Willian
public class Variavel extends Simbolo{
    protected int deslocamento;
    protected TipoDeVariavel tipoDeVariavel;

    public Variavel() {
    }

    /*public Variavel(int deslocamento, TipoDeVariavel tipoDeVariavel) {
        this.deslocamento = deslocamento;
        this.tipoDeVariavel = tipoDeVariavel;
    }*/

    public Variavel(String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
    }
    
    /*public Variavel(String nome, int nivel) {
        super(nome, nivel);
    }*/

    public Variavel(int deslocamento, TipoDeVariavel tipoDeVariavel, String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
        this.deslocamento = deslocamento;
        this.tipoDeVariavel = tipoDeVariavel;
    }

    public int getDeslocamento() {
        return deslocamento;
    }

    public TipoDeVariavel getTipoDeVariavel() {
        return tipoDeVariavel;
    }

    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setTipoDeVariavel(TipoDeVariavel tipoDeVariavel) {
        this.tipoDeVariavel = tipoDeVariavel;
    }       
}
