package controle;

// Classe desenvolvida por Lucas e Willian
public class Constante extends Simbolo {

    protected String valor;
    protected TipoPreDefinidoEnum tipoPreDefinido;

    public Constante() {
    }

    public Constante(TipoPreDefinidoEnum tipoPreDefinido, String valor, String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
        this.tipoPreDefinido = tipoPreDefinido;
        this.valor = valor;
    }

    public Constante(TipoPreDefinidoEnum tipoPreDefinido, String valor) {
        this.tipoPreDefinido = tipoPreDefinido;
        this.valor = valor;
    }

    public Constante(String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
    
    public TipoPreDefinidoEnum getTipoPreDefinido() {
        return tipoPreDefinido;
    }

    public void setTipoPreDefinido(TipoPreDefinidoEnum tipoConstante) {
        this.tipoPreDefinido = tipoConstante;
    }
}
