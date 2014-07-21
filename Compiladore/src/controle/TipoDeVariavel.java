package controle;

// Classe desenvolvida por Lucas e Willian
public class TipoDeVariavel {
    protected TipoDeVariavelEnum tipoDeVariavelEnum;
    protected int tamanho;
    protected TipoPreDefinidoEnum tipoPreDefinidoEnum;

    public TipoDeVariavel() {
    }

    public TipoDeVariavel(TipoDeVariavelEnum tipoDeVariavelEnum, int tamanho, TipoPreDefinidoEnum tipoPreDefinidoEnum) {
        this.tipoDeVariavelEnum = tipoDeVariavelEnum;
        this.tamanho = tamanho;
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }
    
    public void setTipoDeVariavelEnum(TipoDeVariavelEnum tipo) {
        this.tipoDeVariavelEnum = tipo;
    } 

    public TipoDeVariavelEnum getTipoDeVariavelEnum() {
        return tipoDeVariavelEnum;
    }
    
    public TipoPreDefinidoEnum getTipoPreDefinidoEnum() {
        return tipoPreDefinidoEnum;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }
}
