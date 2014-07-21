package controle;

// Classe desenvolvida por Lucas e Willian
public class Parametro extends Simbolo{
      
    protected TipoPreDefinidoEnum tipoPreDefinidoEnum;
    protected PassagemValOuRefEnum passagemValOuRefEnum;
    protected int deslocamento;

    public Parametro() {
    }

    public Parametro(String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
    }
    
    public Parametro(String nome, int nivel) {
        super(nome, nivel);
    }

    public Parametro(int deslocamento, PassagemValOuRefEnum passagemValOuRefEnum, TipoPreDefinidoEnum tipoPreDefinidoEnum) {
        this.deslocamento = deslocamento;
        this.passagemValOuRefEnum = passagemValOuRefEnum;
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }

    public Parametro(int deslocamento, PassagemValOuRefEnum passagemValOuRefEnum, TipoPreDefinidoEnum tipoPreDefinidoEnum, String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
        this.deslocamento = deslocamento;
        this.passagemValOuRefEnum = passagemValOuRefEnum;
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }  

    public int getDeslocamento() {
        return deslocamento;
    }
    
    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    public PassagemValOuRefEnum getPassagemValOuRefEnum() {
        return passagemValOuRefEnum;
    }
    
    public void setPassagemValOuRefEnum(PassagemValOuRefEnum passagemValOuRefEnum) {
        this.passagemValOuRefEnum = passagemValOuRefEnum;
    }

    public TipoPreDefinidoEnum getTipoPreDefinidoEnum() {
        return tipoPreDefinidoEnum;
    }    

    public void setTipoPreDefinidoEnum(TipoPreDefinidoEnum tipoPreDefinidoEnum) {
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }    
}
