package controle;

// Classe desenvolvida por Lucas e Willian
public class Constante extends Simbolo {

    protected String valor;
    protected TipoPreDefinidoEnum tipoPreDefinidoEnum;

    public Constante() {
    }

    /*public Constante(TipoPreDefinidoEnum tipoPreDefinidoEnum, String valor, String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
        this.valor = valor;
    }*/

    /*public Constante(TipoPreDefinidoEnum tipoPreDefinidoEnum, String valor) {
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
        this.valor = valor;
    }*/

    public Constante(String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
    
    public TipoPreDefinidoEnum getTipoPreDefinidoEnum() {
        return tipoPreDefinidoEnum;
    }

    public void setTipoPreDefinidoEnum(TipoPreDefinidoEnum tipoPreDefinidoEnum) {
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }
}
