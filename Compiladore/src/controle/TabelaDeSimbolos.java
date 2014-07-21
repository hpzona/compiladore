package controle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Classe desenvolvida por Lucas e Willian
public class TabelaDeSimbolos {
    private List<Simbolo> tabelaDeSimbolos;

    public TabelaDeSimbolos() {
        tabelaDeSimbolos = new ArrayList<Simbolo>();
    }

    public TabelaDeSimbolos(List<Simbolo> tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public List<Simbolo> getTabelaDeSimbolos() {
        return tabelaDeSimbolos;
    }

    public void setTabelaDeSimbolos(List<Simbolo> tabela) {
        this.tabelaDeSimbolos = tabela;
    }
    
    public void addSimbolo(Simbolo s)
    {
        tabelaDeSimbolos.add(s);            
    }
    
    public void addSimbolo(Simbolo s, int posicao)
    {
        tabelaDeSimbolos.set(posicao, s);
    }
    
    public void removerSimbolo(Simbolo s)
    {
        tabelaDeSimbolos.remove(s);    
    }
    
    public boolean jaExisteSimbolo(Simbolo s)
    {
        if(tabelaDeSimbolos.contains(s))   
        {
            return true;
        }
        else
        {
            return false;
        }
    }   
    
    
    public boolean jaExisteSimboloNoNivel(String s, int nivel)
    {
        for (Iterator<Simbolo> it = tabelaDeSimbolos.iterator(); it.hasNext();) {
            Simbolo simbolo = it.next();
            if(simbolo.getNomeDoSimbolo().equals(s) && (simbolo.getNivel() == nivel || simbolo.getNivel() == 0))
            {
                return true;
            }
        }
        return false;
    }
    
    public Simbolo getSimboloNoNivel(String s, int nivel)
    {
        Simbolo resultado = new Simbolo("", 0);
        for (Iterator<Simbolo> it = tabelaDeSimbolos.iterator(); it.hasNext();) {
            Simbolo simbolo = it.next();
            if((simbolo.getNomeDoSimbolo().equals(s) && simbolo.getNivel() <= nivel) && simbolo.getNivel() > resultado.nivel)
            {
                resultado = simbolo;
            }
        }
        if(resultado.getNomeDoSimbolo().equals(""))
        {
            return null;
        }
        return resultado ;
    }
    
    public void removerSimboloNoNivel(Simbolo s, int nivel)
    {
        Simbolo aRemover =  new Simbolo();
        for (Iterator<Simbolo> it = tabelaDeSimbolos.iterator(); it.hasNext();) {
            Simbolo simbolo = it.next();
            if(simbolo.equals(s) && simbolo.getNivel() == nivel)
            {
                aRemover = simbolo;
            }
        }
        tabelaDeSimbolos.remove(aRemover); 
    }
    
    public int getTamanhoDaTabelaDeSimbolos() {
        return tabelaDeSimbolos.size();
    }
    
    public Simbolo getSimbolo(int i) {
        return tabelaDeSimbolos.get(i);
    }

    public void removeNivelAtual(int nivelAtual) {
        for (int i = 0; i < tabelaDeSimbolos.size(); i++) {
            if (tabelaDeSimbolos.get(i).getNivel() == nivelAtual) {
                tabelaDeSimbolos.remove(i);
                i--;
            }
        }
    }

    public Integer getPosicaoDoSimbolo(String nome, int nivel) {
        Simbolo simbolo = new Simbolo("", 0);
        int resultado = 0;
        for (int i = 0; i < tabelaDeSimbolos.size(); i++) {
            if((tabelaDeSimbolos.get(i).getNomeDoSimbolo().equals(nome) && tabelaDeSimbolos.get(i).getNivel() <= nivel) && (tabelaDeSimbolos.get(i).getNivel() > simbolo.nivel))
            {
                simbolo = tabelaDeSimbolos.get(i);
                resultado = i;
            }
        }
        return resultado;
    }

    public boolean jaExisteSimboloNesteEscopo(String s, int nivel) { 
        for (Iterator<Simbolo> it = tabelaDeSimbolos.iterator(); it.hasNext();) {
            Simbolo simbolo = it.next();
            if(simbolo.getNomeDoSimbolo().equals(s) && simbolo.getNivel() <= nivel)
            {
                return true;
            }
        }
        return false;
    }
}