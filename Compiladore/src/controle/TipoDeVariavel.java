package controle;

// Classe desenvolvida por Lucas e Willian
public class TipoDeVariavel {
    protected SubCategoriaVariavelEnum tipoDeVariavelEnum;
    protected int tamanho;
    protected TipoEnum tipoPreDefinidoEnum;

    public TipoDeVariavel() {
    }

    public TipoDeVariavel(SubCategoriaVariavelEnum tipoDeVariavelEnum, int tamanho, TipoEnum tipoPreDefinidoEnum) {
        this.tipoDeVariavelEnum = tipoDeVariavelEnum;
        this.tamanho = tamanho;
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }
    
    public void setTipoDeVariavelEnum(SubCategoriaVariavelEnum tipo) {
        this.tipoDeVariavelEnum = tipo;
    } 

    public SubCategoriaVariavelEnum getTipoDeVariavelEnum() {
        return tipoDeVariavelEnum;
    }
    
    public TipoEnum getTipoPreDefinidoEnum() {
        return tipoPreDefinidoEnum;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }
}
