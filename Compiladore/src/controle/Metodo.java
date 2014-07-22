package controle;

import java.util.List;
import java.util.Stack;

// Classe desenvolvida por Lucas e Willian
public class Metodo extends Simbolo{
    protected int enderecoDaPrimeiraInstrucao;
    protected int numeroDeParametros;
    protected List<Parametro> listaDeParametros;
    protected TipoPreDefinidoEnum resultadoDoTipo; 
    protected boolean retornoNull;

    public Metodo() {
    }

    public Metodo(String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public Metodo(int endereço1Instrução, int numParametros, List<Parametro> listaDeParametros, TipoPreDefinidoEnum resultadoDoTipo) {
        this.enderecoDaPrimeiraInstrucao = endereço1Instrução;
        this.numeroDeParametros = numParametros;
        this.listaDeParametros = listaDeParametros;
        this.resultadoDoTipo = resultadoDoTipo;
    }

    public Metodo(int endereço1Instrução, int numParametros, List<Parametro> listaDeParametros, TipoPreDefinidoEnum resultadoDoTipo, String nome, CategoriaIDEnum categoria, int nivel) {
        super(nome, categoria, nivel);
        this.enderecoDaPrimeiraInstrucao = endereço1Instrução;
        this.numeroDeParametros = numParametros;
        this.listaDeParametros = listaDeParametros;
        this.resultadoDoTipo = resultadoDoTipo;
    }   

    public int getEnderecoPrimeiraInstrucao() {
        return enderecoDaPrimeiraInstrucao;
    }

    public List<Parametro> getListaDeParametros() {
        return listaDeParametros;
    }

    public int getNumeroDeParametros() {
        return numeroDeParametros;
    }

    public TipoPreDefinidoEnum getResultado() {
        return resultadoDoTipo;
    }

    public boolean isRetornoNull() {
        return retornoNull;
    }   

    public void setEndereço1Instrução(int endereço1Instrução) {
        this.enderecoDaPrimeiraInstrucao = endereço1Instrução;
    }

    public void setListaParametros(List<Parametro> listaParametros) {
        this.listaDeParametros = listaParametros;
    }

    public void setNumParametros(int numParametros) {
        this.numeroDeParametros = numParametros;
    }

    public void setResultado(TipoPreDefinidoEnum resultado) {
        this.resultadoDoTipo = resultado;
    }

    public void setRetornoNull(boolean retornoNull) {
        this.retornoNull = retornoNull;
    }
    
    public Stack<Parametro> getPilhaParametro()
    {
        Stack<Parametro> retorno = new Stack<Parametro>();
        for (int i = this.listaDeParametros.size() - 1; i >=  0; i--) {
            retorno.push(this.listaDeParametros.get(i));
        }
        return retorno;
    }
}
