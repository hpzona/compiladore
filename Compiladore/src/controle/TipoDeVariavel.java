package controle;

// Classe desenvolvida por Lucas e Willian
public class TipoDeVariavel {
    protected SubCategoriaPreDefEnum tipoDeVariavelPreDefEnum;
    protected int tamanho;
    protected TipoPreDefinidoEnum tipoPreDefinido;

    public TipoDeVariavel() {
    }

    public TipoDeVariavel(SubCategoriaPreDefEnum tipoDeVariavelPreDefEnum, int tamanho, TipoPreDefinidoEnum tipoPreDefinidoEnum) {
        this.tipoDeVariavelPreDefEnum = tipoDeVariavelPreDefEnum;
        this.tamanho = tamanho;
        this.tipoPreDefinido = tipoPreDefinidoEnum;
    }
    
    public void setTipoDeVariavelPreDefEnum(SubCategoriaPreDefEnum tipo) {
        this.tipoDeVariavelPreDefEnum = tipo;
    } 

    public SubCategoriaPreDefEnum getTipoDeVariavelPreDefEnum() {
        return tipoDeVariavelPreDefEnum;
    }
    
    public TipoPreDefinidoEnum getTipoPreDefinido() {
        return tipoPreDefinido;
    }
    
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }
}
