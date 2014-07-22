package controle;

import java.util.Objects;

// Classe desenvolvida por Lucas e Willian
public class Simbolo {
    
    protected CategoriaIDEnum categoria;
    protected String nomeDoSimbolo;
    protected int nivel;

    public Simbolo() {
    }
    
    public Simbolo(String nomeDoSimbolo, int nivel) {
        this.nomeDoSimbolo = nomeDoSimbolo;        
        this.nivel = nivel;
    }

    public Simbolo(String nomeDoSimbolo, CategoriaIDEnum categoria, int nivel) {
        this.nomeDoSimbolo = nomeDoSimbolo;
        this.categoria = categoria;
        this.nivel = nivel;
    }

    public CategoriaIDEnum getCategoria() {
        return categoria;
    }

    public int getNivel() {
        return nivel;
    }

    public String getNomeDoSimbolo() {
        return nomeDoSimbolo;
    }

    public void setCategoria(CategoriaIDEnum categoria) {
        this.categoria = categoria;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setNomeDoSimbolo(String nome) {
        this.nomeDoSimbolo = nome;
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof Simbolo) ) {
            return false;
        }
        return this.nomeDoSimbolo.equals(((Simbolo)obj).nomeDoSimbolo);
    }   

    @Override
    public int hashCode() {
        int hash = 8;
        hash = 46 * hash + Objects.hashCode(this.nomeDoSimbolo);
        hash = 46 * hash + (this.categoria != null ? this.categoria.hashCode() : 0);
        hash = 46 * hash + this.nivel;
        return hash;
    }
}
