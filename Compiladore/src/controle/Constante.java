package controle;

// Classe desenvolvida por Lucas e Willian
public class Constante extends Simbolo {

    protected String valor;
    protected TipoEnum tipoPreDefinidoEnum;

    public Constante() {
    }

    /*public Constante(TipoEnum tipoPreDefinidoEnum, String valor, String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
        this.valor = valor;
    }*/

    /*public Constante(TipoEnum tipoPreDefinidoEnum, String valor) {
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
    
    public TipoEnum getTipoPreDefinidoEnum() {
        return tipoPreDefinidoEnum;
    }

    public void setTipoPreDefinidoEnum(TipoEnum tipoPreDefinidoEnum) {
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }
}
