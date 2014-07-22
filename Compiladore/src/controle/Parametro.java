package controle;

// Classe desenvolvida por Lucas e Willian
public class Parametro extends Simbolo{
      
    protected TipoEnum tipoPreDefinidoEnum;
    protected MecanismoDePassagem passagemValOuRefEnum;
    protected int deslocamento;

    public Parametro() {
    }

    public Parametro(String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
    }
    
    public Parametro(String nome, int nivel) {
        super(nome, nivel);
    }

    public Parametro(int deslocamento, MecanismoDePassagem passagemValOuRefEnum, TipoEnum tipoPreDefinidoEnum) {
        this.deslocamento = deslocamento;
        this.passagemValOuRefEnum = passagemValOuRefEnum;
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }

    public Parametro(int deslocamento, MecanismoDePassagem passagemValOuRefEnum, TipoEnum tipoPreDefinidoEnum, String nome, CategoriaIDEnum categoria, int nivel) {
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

    public MecanismoDePassagem getPassagemValOuRefEnum() {
        return passagemValOuRefEnum;
    }
    
    public void setPassagemValOuRefEnum(MecanismoDePassagem passagemValOuRefEnum) {
        this.passagemValOuRefEnum = passagemValOuRefEnum;
    }

    public TipoEnum getTipoPreDefinidoEnum() {
        return tipoPreDefinidoEnum;
    }    

    public void setTipoPreDefinidoEnum(TipoEnum tipoPreDefinidoEnum) {
        this.tipoPreDefinidoEnum = tipoPreDefinidoEnum;
    }    
}
