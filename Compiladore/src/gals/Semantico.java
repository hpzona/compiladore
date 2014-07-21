package gals;

import controle.CategoriaIDEnum;
import controle.Constante;
import controle.ContextoLIDEnum;
import controle.Converte;
import controle.ExpressaoEnum;
import controle.Metodo;
import controle.OperadorAddEnum;
import controle.OperadorMultEnum;
import controle.OperadorRelEnum;
import controle.Parametro;
import controle.PassagemValOuRefEnum;
import controle.Simbolo;
import controle.SubCategoriaEnum;
import controle.TabelaDeSimbolos;
import controle.TipoDeVariavel;
import controle.TipoDeVariavelEnum;
import controle.TipoPreDefinidoEnum;
import controle.Variavel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Semantico implements Constants {

    private TabelaDeSimbolos tabSimbolos;

    private int NA;
    private Stack<Integer> deslocamento = new Stack<>();
    private int primeiroID;
    private int ultimoID;
    private String valConst;
    private int quantidadeID;
    private int numeroDeElementos;
    private TipoPreDefinidoEnum tipoAtualTipoPreDefinido;
    private SubCategoriaEnum subCategoria;
    private CategoriaIDEnum categoriaAtual;
    private ContextoLIDEnum contextoLID;
    private TipoPreDefinidoEnum tipoConstanteTipoPreDefinido;
    private int NPF;
    private int NPA;
    private PassagemValOuRefEnum passagemValOuRef;
    private List<Parametro> listaDeParametros = new ArrayList<>();
    private TipoPreDefinidoEnum tipoVarTipoPreDefinido;
    private TipoPreDefinidoEnum tipoLadoEsqTipoPreDefinido;
    private TipoPreDefinidoEnum retornoMetodoTipoPreDefinido;
    private TipoPreDefinidoEnum tipoExpressaoTipoPreDefinido;
    private boolean retornoNull;
    private boolean temRetorno = false;

    //PILHAS
    private Stack<Integer> pilhaPosicaoID = new Stack<>();
    private Stack<Integer> pilhaIdMetodo = new Stack<>();
    private Stack<Boolean> pilhaOperadorNega = new Stack<>();
    private Stack<Boolean> pilhaOperadorUnario = new Stack<>();
    private Stack<OperadorAddEnum> pilhaOperadorAdd = new Stack<>();
    private Stack<OperadorRelEnum> pilhaOperadorRelacional = new Stack<>();
    private Stack<OperadorMultEnum> pilhaOperadorMult = new Stack<>();
    private Stack<TipoPreDefinidoEnum> pilhaTipoTermoTipoPreDefinido = new Stack<>();
    private Stack<TipoPreDefinidoEnum> pilhaTipoExpressaoSimplesTipoPreDefinido = new Stack<>();
    private Stack<TipoPreDefinidoEnum> pilhaTipoExpressaoTipoPreDefinido = new Stack<>();
    private Stack<ExpressaoEnum> pilhaExpressao = new Stack<>();
    private Stack<TipoPreDefinidoEnum> pilhaTipoVariavelIndexadaTipoPreDefinido = new Stack<>();
    private Stack<TipoPreDefinidoEnum> pilhaTipoFatorTipoPreDefinido = new Stack<>();
    private Stack<Integer> pilhaNPA = new Stack<>();
    private Stack<PassagemValOuRefEnum> pilhaERef = new Stack<>();
    private Stack<Stack<Parametro>> pilhaValidarParametros = new Stack<>();
    private Stack<Boolean> pilhaRetorno = new Stack<>();

    //AUX
    private Simbolo simboloAux;
    private Parametro parametroAux;
    private Metodo metodoAux;
    private Variavel variavelAux;
    private Constante constanteAux;
    private OperadorRelEnum operadorRelAux;
    private TipoPreDefinidoEnum tipoExprAux;
    private TipoPreDefinidoEnum tipoTermoAux;
    private TipoPreDefinidoEnum tipoFatorAux;

    public void executeAction(int action, Token token) throws SemanticError {
        /*try {
         System.out.println("Ação " + action + " - token: " + token.getLexeme());
         Method execAcao = this.getClass().getMethod("execAcao" + action, Token.class);
         execAcao.invoke(this, token);
         } catch (InvocationTargetException e) {
         Throwable targetException = e.getTargetException();
         if (targetException instanceof SemanticError) {
         SemanticError semanticError = (SemanticError) targetException;
         throw semanticError;
         }
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Erro");
         throw new RuntimeException(e);
         }*/

        switch (action) {
            /*
             #101 – Inicializa com zero nível atual (NA) e deslocamento
             - Insere id na TS juntamente com seus atributos categoria (id - programa) e nível (NA )
             */
            case 101:
                this.tabSimbolos = new TabelaDeSimbolos();
                this.NA = 0;
                this.deslocamento.push(0);
                Simbolo id = new Simbolo(token.getLexeme(), CategoriaIDEnum.PROGRAMA, 0);
                this.NA++;
                this.tabSimbolos.addSimbolo(id);
                break;
            /*
             #102 - seta contextoLID para “decl” 
             Guarda pos. na TS do primeiro id da lista 
             */
            case 102:
                this.contextoLID = ContextoLIDEnum.DECL;
                this.primeiroID = this.tabSimbolos.getTamanhoDaTabelaDeSimbolos();
                this.quantidadeID = 0;
                break;
            /*
             #103 - Guarda pos. na TS do último id da lista 
             */
            case 103:
                this.ultimoID = this.primeiroID + this.quantidadeID;
                break;
            /*
             #104 - Atualiza atributos dos id de <lid> de acordo com a CategoriaAtual e com a SubCategoria. 
             Para cálculo do Deslocamento de variáveis, considere que toda variável ocupa 1 célula de memória 
             (exceto vetor que ocupa 1 célula para cada elemento)                  
             */
            case 104:
                int pos = this.primeiroID;
                int desloc = this.deslocamento.pop();
                do {
                    if (categoriaAtual == CategoriaIDEnum.CONSTANTE) {

                        this.simboloAux = this.tabSimbolos.getSimbolo(pos);
                        this.constanteAux = new Constante(this.simboloAux.getNomeDoSimbolo(), this.categoriaAtual, this.simboloAux.getNivel());
                        this.constanteAux.setTipoPreDefinidoEnum(this.tipoAtualTipoPreDefinido);
                        this.constanteAux.setValor(this.valConst);
                        this.constanteAux.setCategoria(this.categoriaAtual);
                        this.tabSimbolos.addSimbolo(this.constanteAux, pos);
                        //desloc++;

                    } else if (categoriaAtual == CategoriaIDEnum.VARIAVEL) {

                        this.simboloAux = this.tabSimbolos.getSimbolo(pos);
                        this.variavelAux = new Variavel(this.simboloAux.getNomeDoSimbolo(), this.simboloAux.getCategoria(), this.simboloAux.getNivel());

                        if (this.subCategoria == SubCategoriaEnum.preDefinido) {
                            TipoDeVariavel tipo = new TipoDeVariavel(Converte.getTipoDeVariavelEnum(this.tipoAtualTipoPreDefinido), 0, null);
                            variavelAux.setTipoDeVariavel(tipo);
                            variavelAux.setCategoria(categoriaAtual);
                            variavelAux.setDeslocamento(desloc);
                            desloc++;
                        } else if (this.subCategoria == SubCategoriaEnum.cadeia) {
                            TipoDeVariavel tipo = new TipoDeVariavel(Converte.getTipoDeVariavelEnum(this.tipoAtualTipoPreDefinido), Integer.parseInt(this.valConst), null);
                            variavelAux.setTipoDeVariavel(tipo);
                            variavelAux.setCategoria(this.categoriaAtual);
                            variavelAux.setDeslocamento(desloc);
                            desloc++;

                        } else if (this.subCategoria == SubCategoriaEnum.vetor) {
                            TipoDeVariavel tipo = new TipoDeVariavel(TipoDeVariavelEnum.VETOR, numeroDeElementos, this.tipoAtualTipoPreDefinido);
                            variavelAux.setTipoDeVariavel(tipo);
                            variavelAux.setCategoria(this.categoriaAtual);
                            variavelAux.setDeslocamento(desloc);
                            desloc += numeroDeElementos;
                        }
                        this.tabSimbolos.addSimbolo(variavelAux, pos);
                    }
                    pos++;
                } while (pos != this.ultimoID);
                deslocamento.push(desloc);
                break;

            /*
             #105 - TipoAtual := “inteiro”                
             */
            case 105:
                this.tipoAtualTipoPreDefinido = TipoPreDefinidoEnum.INTEIRO;
                break;
            /*
             #106 - TipoAtual := “real”                
             */
            case 106:
                this.tipoAtualTipoPreDefinido = TipoPreDefinidoEnum.REAL;
                break;
            /*
             #107 - TipoAtual := “booleano”                
             */
            case 107:
                this.tipoAtualTipoPreDefinido = TipoPreDefinidoEnum.BOOLEANO;
                break;
            /*
             #108 - TipoAtual := “caracter”                
             */
            case 108:
                this.tipoAtualTipoPreDefinido = TipoPreDefinidoEnum.CARACTER;
                break;

            /*
             #109 - Se tipoConstanteTipoPreDefinido <> “inteiro” então ERRO(“esperava - se uma const. inteira”)
             senão se valConst > 256 então ERRO(“cadeia > que o permitido”) 
             senão TipoAtual := “cadeia”                
             */
            case 109:
                if (tipoConstanteTipoPreDefinido != TipoPreDefinidoEnum.INTEIRO) {
                    throw new SemanticError("Esperava-se uma constante inteira.", token.getPosition());
                } else if (Integer.parseInt(valConst) > 256) {
                    throw new SemanticError("Cadeia maior do que o permitido.", token.getPosition());
                } else {
                    this.tipoAtualTipoPreDefinido = TipoPreDefinidoEnum.CADEIA;
                }
                break;
            /*
             #110 - Se TipoAtual = “cadeia” Então ERRO(“Vetor do tipo cadeia não é permitido”)
             senão SubCategoria := “vetor”                
             */
            case 110:
                if (tipoAtualTipoPreDefinido == TipoPreDefinidoEnum.CADEIA) {
                    throw new SemanticError("Vetor do tipo cadeia não é permitido.", token.getPosition());
                } else {
                    this.subCategoria = SubCategoriaEnum.vetor;
                }
                break;
            /*
             #111 -  Se tipoConstanteTipoPreDefinido <> inteiro Então ERRO (“A dim.deve ser uma constante inteira”)
             Senão Seta numeroDeElementos para valConst            
             */
            case 111:
                if (tipoConstanteTipoPreDefinido != TipoPreDefinidoEnum.INTEIRO) {
                    throw new SemanticError("A dimensão deve ser uma constante inteira.", token.getPosition());
                } else {
                    this.numeroDeElementos = Integer.parseInt(valConst);
                }
                break;
            /*
             #112 -  Se TipoAtual = “cadeia” Então SubCategoria := “cadeia”
             Senão SubCategoria := “pré-definido”            
             */
            case 112:
                if (this.tipoAtualTipoPreDefinido == TipoPreDefinidoEnum.CADEIA) {
                    this.subCategoria = SubCategoriaEnum.cadeia;
                    return;
                } else {
                    this.subCategoria = SubCategoriaEnum.preDefinido;
                }
                break;
            /*
             #113 - Se contextoLID = “decl” entao se id já declarado no NA então ERRO(“Id já declarado”) senão insere id na TS
             - Se contextoLID = “par-formal” entao se id já declarado no NA então ERRO (“Id de parâmetro repetido”) senão incrementa NPF; insere id na TS
             - Se contextoLID = “leitura” Então se id não declarado então ERRO (“Id não declarado”) senão se categoria ou tipo invalido para leitura então ERRO(“Tipo inv. p/ leitura”)
             senão (* Gera Cód. para leitura *)            
             */
            case 113:
                if (this.contextoLID == ContextoLIDEnum.DECL) {
                    if (tabSimbolos.jaExisteSimboloNoNivel(token.getLexeme(), NA)) {
                        throw new SemanticError("id já declarado.", token.getPosition());
                    } else {
                        this.simboloAux = new Simbolo(token.getLexeme(), categoriaAtual, NA);
                        tabSimbolos.addSimbolo(this.simboloAux);
                        this.quantidadeID++;
                    }
                } else if (this.contextoLID == ContextoLIDEnum.PAR_FORMAL) {
                    if (tabSimbolos.jaExisteSimboloNoNivel(token.getLexeme(), NA)) {
                        throw new SemanticError("id de parâmetro repetido.", token.getPosition());
                    } else {
                        this.parametroAux = new Parametro(token.getLexeme(), NA);
                        tabSimbolos.addSimbolo(this.parametroAux);
                        this.NPF++;
                        this.quantidadeID++;
                    }
                } else if (this.contextoLID == ContextoLIDEnum.LEITURA) {
                    if (!tabSimbolos.jaExisteSimboloNesteEscopo(token.getLexeme(), NA)) {
                        throw new SemanticError("id não declarado.", token.getPosition());
                    } else {
                        this.simboloAux = this.tabSimbolos.getSimboloNoNivel(token.getLexeme(), this.NA);
                        if (this.simboloAux.getCategoria() == CategoriaIDEnum.VARIAVEL || this.simboloAux.getCategoria() == CategoriaIDEnum.PARAMETRO) {
                            boolean valido = false;

                            if (this.simboloAux.getCategoria() == CategoriaIDEnum.VARIAVEL) {
                                this.variavelAux = (Variavel) this.simboloAux;
                                valido = !(this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum() == TipoDeVariavelEnum.BOOLEANO || this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum() == TipoDeVariavelEnum.VETOR);
                            } else if (this.simboloAux.getCategoria() == CategoriaIDEnum.PARAMETRO) {
                                this.parametroAux = (Parametro) this.simboloAux;
                                valido = !(this.parametroAux.getTipoPreDefinidoEnum() == TipoPreDefinidoEnum.BOOLEANO);
                            }

                            if (!valido) {
                                throw new SemanticError("Tipo inválido para a leitura.", token.getPosition());
                            } else {
                                ///////////////////// GERAR CÓDIGO PARA LEITURA /////////////////////////
                            }
                        } else {
                            throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                        }
                    }
                }
                break;
            /*
             #114 - Se SubCategoria = “cadeia” ou “vetor” Então ERRO (“Apenas id de tipo pré-def podem ser declarados como constante”) senão CategoriaAtual := “constante”            
             */
            case 114:
                if (this.subCategoria == SubCategoriaEnum.cadeia || this.subCategoria == SubCategoriaEnum.vetor) {
                    throw new SemanticError("Apenas id de tipo pré-definido podem ser declarados como constante.", token.getPosition());
                } else {
                    this.categoriaAtual = CategoriaIDEnum.CONSTANTE;
                }
                break;
            /*
             #115 - Se tipoConstanteTipoPreDefinido <> TipoAtual Então ERRO (“Tipo da constante incorreto”)            
             */
            case 115:
                if (this.tipoConstanteTipoPreDefinido != this.tipoAtualTipoPreDefinido) {
                    if(!(tipoAtualTipoPreDefinido == TipoPreDefinidoEnum.REAL && tipoConstanteTipoPreDefinido == TipoPreDefinidoEnum.INTEIRO))
                    throw new SemanticError("Tipo da constante incorreto.", token.getPosition());
                }
                break;
            /*
             #116 - CategoriaAtual := “variavel”            
             */
            case 116:
                this.categoriaAtual = CategoriaIDEnum.VARIAVEL;
                break;
            /*
             #117 - Se id já está declarado no NA, então ERRO(“Id já declarado”) 
             senão insere id na TS, junto com NA e categ. zera número de parâmetros Formais (NPF) incrementa nível atual (NA := NA + 1)            
             */
            case 117:
                if (tabSimbolos.jaExisteSimboloNoNivel(token.getLexeme(), this.NA)) {
                    throw new SemanticError("id já declarado.", token.getPosition());
                } else {
                    this.metodoAux = new Metodo(token.getLexeme(), CategoriaIDEnum.METODO, this.NA);
                    this.tabSimbolos.addSimbolo(this.metodoAux);
                    this.NPF = 0;
                    this.NA++;
                    this.deslocamento.push(0);
                    this.quantidadeID = 0;
                    this.listaDeParametros = new ArrayList<>();
                    this.pilhaIdMetodo.push(tabSimbolos.getTamanhoDaTabelaDeSimbolos() - 1);
                }
                break;
            /*
             #118 - Atualiza num. de par. Formais (NPF) na TS          
             */
            case 118:
                this.metodoAux = (Metodo) this.tabSimbolos.getSimbolo(this.pilhaIdMetodo.peek());
                this.metodoAux.setListaParametros(this.listaDeParametros);
                this.metodoAux.setNumParametros(this.NPF);
                break;

            /*  
             #119 - Atualiza tipo do método na TS          
             */
            case 119:
                this.metodoAux = (Metodo) this.tabSimbolos.getSimbolo(this.pilhaIdMetodo.peek());
                if (!retornoNull) {
                    this.metodoAux.setResultado(this.retornoMetodoTipoPreDefinido);
                    this.pilhaRetorno.push(true);
                } else {
                    this.pilhaRetorno.push(false);
                }
                this.metodoAux.setRetornoNull(retornoNull);
                break;

            /*  
             #120 - Atualiza tipo do método na TS          
             */
            case 120:
                this.tabSimbolos.removeNivelAtual(this.NA);
                this.NA--;
                deslocamento.pop();

                this.metodoAux = (Metodo) this.tabSimbolos.getSimbolo(this.pilhaIdMetodo.peek());
                if (!this.metodoAux.isRetornoNull() && !this.temRetorno) {
                    throw new SemanticError("Método sem declaração de retorno.", token.getPosition());
                }
                this.pilhaIdMetodo.pop();
                this.pilhaRetorno.pop();
                this.temRetorno = false;
          
                break;
            /*  
             #121 - Seta contextoLID para “par-formal” Marca pos.na TS do primeiro id da lista           
             */
            case 121:
                this.contextoLID = ContextoLIDEnum.PAR_FORMAL;
                this.primeiroID = this.tabSimbolos.getTamanhoDaTabelaDeSimbolos();
                this.quantidadeID = 0;
                break;
            /*  
             #122 - Marca pos. na TS do último id da lista            
             */
            case 122:
                this.ultimoID = this.primeiroID + this.quantidadeID;
                break;
            /*  
             #123 - Se TipoAtual diferente de “pre-definido” Então ERRO (“Par. devem ser de tipo pré-def.”) 
             Senão Atualiza atributos dos id’s de <lid> : Cat.(“Parâmetro”), TipoAtual e MPP. Insere os par em uma lista auxiliar (ListaPar) a ser usada na chamada do método.            
             */
            case 123:
                //AQUI CHAMARIA O CASE 112, se aceitasse cadeia também!!
                if (this.tipoAtualTipoPreDefinido == TipoPreDefinidoEnum.CADEIA) {
                    this.subCategoria = SubCategoriaEnum.cadeia;
                    return;
                } else {
                    this.subCategoria = SubCategoriaEnum.preDefinido;
                }
                
                if (this.subCategoria != SubCategoriaEnum.preDefinido) {
                    throw new SemanticError("Parâmetros devem ser do tipo pré-definido.", token.getPosition());
                } else {
                    pos = this.primeiroID;
                    do {
                        desloc = this.deslocamento.pop();

                        this.simboloAux = this.tabSimbolos.getSimbolo(pos);
                        this.parametroAux = new Parametro(this.simboloAux.getNomeDoSimbolo(), this.simboloAux.getCategoria(), this.simboloAux.getNivel());
                        this.parametroAux.setCategoria(CategoriaIDEnum.PARAMETRO);
                        this.parametroAux.setTipoPreDefinidoEnum(this.tipoAtualTipoPreDefinido);
                        this.parametroAux.setPassagemValOuRefEnum(passagemValOuRef);
                        this.parametroAux.setDeslocamento(desloc);
                        desloc++;

                        this.deslocamento.push(desloc);
                        this.tabSimbolos.addSimbolo(this.parametroAux, (pos));
                        pos++;
                        this.listaDeParametros.add(this.parametroAux);

                    } while (pos <= this.ultimoID - 1);
                }
                break;
            /*  
             #124 - Se TipoAtual = “cadeia” Então ERRO (“Métodos devem ser de tipo pré-def.”) Senão Seta tipo do método para TipoAtual
             */
            case 124:
                if (this.tipoAtualTipoPreDefinido == TipoPreDefinidoEnum.CADEIA) {
                    throw new SemanticError("Métodos devem ser do tipo pré-definido e não cadeia.", token.getPosition());
                } else {
                    this.retornoMetodoTipoPreDefinido = this.tipoAtualTipoPreDefinido;
                    this.retornoNull = false;
                }
                break;
            /*  
             #125 - Seta tipo do método para “nulo”
             */
            case 125:
                this.metodoAux.setRetornoNull(true);
                break;
            /*  
             #126 - Seta MPP para “referência” 
             */
            case 126:
                this.passagemValOuRef = PassagemValOuRefEnum.REFERENCIA;
                break;
            /*
             #127 - Seta MPP para “valor” 
             */
            case 127:
                this.passagemValOuRef = PassagemValOuRefEnum.VALOR;
                break;
            /*
             #128 - Se id não está declarado (não esta na TS) então ERRO(“Identificador não declarado”) 
             senão guarda pos ocup por id na TS em POSID
             */
            case 128:
                if (!tabSimbolos.jaExisteSimboloNesteEscopo(token.getLexeme(), this.NA)) {
                    throw new SemanticError("id não declarado.", token.getPosition());
                } else {
                    this.pilhaPosicaoID.push(tabSimbolos.getPosicaoDoSimbolo(token.getLexeme(), this.NA));
                }
                break;

            /*
             #129 - Se TipoExpr <> “booleano” e <> “inteiro” então ERRO(“Tipo inválido da expressão”) 
             senao (* ação de G. Código *)
             */
            case 129:
                this.tipoExpressaoTipoPreDefinido = this.pilhaTipoExpressaoTipoPreDefinido.pop();
                if (this.tipoExpressaoTipoPreDefinido != TipoPreDefinidoEnum.BOOLEANO && this.tipoExpressaoTipoPreDefinido != TipoPreDefinidoEnum.INTEIRO) {
                    throw new SemanticError("Tipo inválido da expressão.", token.getPosition());
                } else {
                    ///////////////////// GERA CÓDIGO /////////////////////////
                }
                break;
            /*
             #130 - Seta ContextoLID para “Leitura”
             */
            case 130:
                this.contextoLID = ContextoLIDEnum.LEITURA;
                break;
            /*
             #131 - Seta ContextoEXPR para “impressão”
             */
            case 131:
                this.pilhaExpressao.push(ExpressaoEnum.IMPRESSAO);
                break;
            /*
             #132 - Se está fora do escopo de um método com tipo Então ERRO (“Retorne” só pode ser usado em Método com tipo”) 
             Senão se TipoExpr <> tipo do método então ERRO(“Tipo de retorno inválido”)
             senao (* ação de Geração de Código *)
             */
            case 132:
                if (this.pilhaRetorno.isEmpty() && !this.pilhaRetorno.peek()) {
                    throw new SemanticError("'Retorne' só pode ser usado em método com tipo.", token.getPosition());
                } else {
                    this.tipoExprAux = this.pilhaTipoExpressaoTipoPreDefinido.pop();
                    this.simboloAux = tabSimbolos.getSimbolo(this.pilhaIdMetodo.peek());
                    Metodo metodo = (Metodo) this.simboloAux;
                    TipoPreDefinidoEnum tipoMetodo = metodo.getResultado();
                    if (this.tipoExprAux != tipoMetodo) {
                        if (!((tipoMetodo == TipoPreDefinidoEnum.REAL && this.tipoExprAux == TipoPreDefinidoEnum.INTEIRO) || (tipoMetodo == TipoPreDefinidoEnum.CADEIA && this.tipoExprAux == TipoPreDefinidoEnum.CARACTER))) {
                            throw new SemanticError("Tipo de retorno inválido.", token.getPosition());
                        }
                        temRetorno = true;
                    } else {
                        temRetorno = true;
                        ///////////////////// GERA CÓDIGO /////////////////////////
                    }
                }
                break;

            /*
             #133 - Se categ. de id = “Variável” ou “Parâmetro” então se tipo de id = “vetor” então ERRO (“id. Deveria ser indexado”)
             senão tipoLadoEsqTipoPreDefinido := tipo de id
             senão ERRO (“id. deveria ser var ou par”)       
             */
            case 133:
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.pop());
                if (this.simboloAux.getCategoria() == CategoriaIDEnum.VARIAVEL || this.simboloAux.getCategoria() == CategoriaIDEnum.PARAMETRO) {
                    if (this.simboloAux.getCategoria() == CategoriaIDEnum.VARIAVEL) {
                        this.variavelAux = (Variavel) this.simboloAux;
                        if (this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum() == TipoDeVariavelEnum.VETOR) {
                            throw new SemanticError("id deveria ser indexado.", token.getPosition());
                        } else {
                            this.tipoLadoEsqTipoPreDefinido = Converte.getTipoPreDefinidoEnum(this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum());
                        }
                    }
                    if (this.simboloAux.getCategoria() == CategoriaIDEnum.PARAMETRO) {
                        this.parametroAux = (Parametro) this.simboloAux;
                        this.tipoLadoEsqTipoPreDefinido = this.parametroAux.getTipoPreDefinidoEnum();
                    }
                } else {
                    throw new SemanticError("id deveria ser variável ou parâmetro.", token.getPosition());
                }
                break;

            /*
             #134 – se TipoExpr não compatível com tipoLadoesq então ERRO (“tipos incompatíveis”) 
             senão (* G. Código *)
             */
            case 134:
                this.tipoExpressaoTipoPreDefinido = pilhaTipoExpressaoTipoPreDefinido.pop();
                if (this.tipoLadoEsqTipoPreDefinido != this.tipoExpressaoTipoPreDefinido) {
                    if (!((this.tipoExpressaoTipoPreDefinido == TipoPreDefinidoEnum.INTEIRO && this.tipoLadoEsqTipoPreDefinido == TipoPreDefinidoEnum.REAL) || (this.tipoExpressaoTipoPreDefinido == TipoPreDefinidoEnum.CARACTER && this.tipoLadoEsqTipoPreDefinido == TipoPreDefinidoEnum.CADEIA))) {
                        throw new SemanticError("Tipos incompatíveis na expressão.", token.getPosition());
                    }
                } else {
                    ///////////////////// GERA CÓDIGO /////////////////////////                    
                }
                break;

            /*
             #135 – se categoria de id <> “variável” então ERRO (“esperava-se uma variável”) 
             senao se tipo de id <> vetor e <> de cadeia então ERRO(“apenas vetores e cadeias podem ser indexados”) 
             senão TipoVarIndexada = tipo de id (vetor ou cadeia) 
             */
            case 135:
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.peek());
                if (this.simboloAux.getCategoria() != CategoriaIDEnum.VARIAVEL) {
                    throw new SemanticError("Esperava-se uma variável.", token.getPosition());
                } else {
                    this.variavelAux = (Variavel) this.simboloAux;
                    if (this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum() != TipoDeVariavelEnum.VETOR && this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum() != TipoDeVariavelEnum.CADEIA) {
                        throw new SemanticError("Apenas vetores e cadeias podem ser indexados.", token.getPosition());
                    } else {
                        this.pilhaTipoVariavelIndexadaTipoPreDefinido.push(Converte.getTipoPreDefinidoEnum(this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum()));
                    }
                }
                break;

            /*
             #136 – se TipoExpr <> “inteiro” então ERRO(“índice deveria ser inteiro”) 
             senão se TipoVarIndexada = cadeia então tipoLadoEsqTipoPreDefinido := “caracter” 
             senao tipoLadoEsqTipoPreDefinido := TipoElementos do vetor
             */
            case 136:
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.pop());
                this.variavelAux = (Variavel) this.simboloAux;
                this.tipoExpressaoTipoPreDefinido = this.pilhaTipoExpressaoTipoPreDefinido.pop();
                if (this.tipoExpressaoTipoPreDefinido != TipoPreDefinidoEnum.INTEIRO) {
                    throw new SemanticError("Índice deveria ser inteiro.", token.getPosition());
                } else {
                    TipoPreDefinidoEnum tipoVarIndexada = this.pilhaTipoVariavelIndexadaTipoPreDefinido.pop();
                    if (tipoVarIndexada == TipoPreDefinidoEnum.CADEIA) {
                        this.tipoLadoEsqTipoPreDefinido = TipoPreDefinidoEnum.CADEIA;
                    } else {
                        this.tipoLadoEsqTipoPreDefinido = this.variavelAux.getTipoDeVariavel().getTipoPreDefinidoEnum();
                    }
                }
                break;

            /*
             #137 – se categoria de id <> método então ERRO(“id deveria ser um método”) 
             senão se tipo do método <> nulo então ERRO(“esperava-se mét sem tipo”)
             */
            case 137:
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.peek());
                if (this.simboloAux.getCategoria() != CategoriaIDEnum.METODO) {
                    throw new SemanticError("id deveria ser um método.", token.getPosition());
                } else {
                    Metodo metodo = (Metodo) this.simboloAux;
                    if (!metodo.isRetornoNull()) {
                        throw new SemanticError("Esperava-se método sem tipo.", token.getPosition());
                    }
                    this.pilhaValidarParametros.push(metodo.getPilhaParametro());
                    this.pilhaExpressao.push(ExpressaoEnum.PAR_ATUAL);
                }
                break;

            /*
             #138 – NPA := 0 (Número de Parâmetros Atuais) seta contextoEXPR para “par-atual” 
             */
            case 138:
                this.pilhaNPA.push(0);
                this.pilhaExpressao.push(ExpressaoEnum.PAR_ATUAL);
                break;

            /*
             #139 – se NPA <> NPF então ERRO(“Erro na quant.de parâmetros”)
             senao (* G. Código para chamada de proc*)
             */
            case 139:
                this.NPA = this.pilhaNPA.pop();
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.pop());
                this.metodoAux = (Metodo) this.simboloAux;

                if (this.NPA != this.metodoAux.getNumeroDeParametros()) {
                    throw new SemanticError("Erro na quantidade de parâmetros.", token.getPosition());
                } else {
                    ///////////////////// GERA CÓDIGO PARA CHAMADA DE PROC /////////////////////////                    
                }
                this.pilhaExpressao.pop();
                this.pilhaValidarParametros.pop();
                break;

            /*
             #140 - se categoria de id <> método então ERRO(“id deveria ser um método”) 
             senão se tipo do método <> nulo então ERRO(“esperava-se método sem tipo”) 
             senão se NPF <> 0 então ERRO(“Erro na quantidade de parametros”) 
             senão(*GC p/ chamada de método *)
             */
            case 140:
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.pop());
                if (this.simboloAux.getCategoria() != CategoriaIDEnum.METODO) {
                    throw new SemanticError("id deveria ser um método", token.getPosition());
                } else {
                    this.metodoAux = (Metodo) this.simboloAux;
                    if (!this.metodoAux.isRetornoNull()) {
                        throw new SemanticError("Esperava-se método sem tipo.", token.getPosition());
                    } else {
                        if (this.metodoAux.getNumeroDeParametros() > 0) {
                            throw new SemanticError("Erro na quantidade de parametros.", token.getPosition());
                        } else {
                            ///////////////////// GERA CÓDIGO PARA CHAMADA DE PROC /////////////////////////  
                        }
                    }
                }
                break;
            /*
             #141 - se ContextoEXPR = “par-atual” então incrementa NPA e Verifica se existe ParâmetroFormal correspondente e se o tipo e o MPP são compatíveis
             - se ContextoEXPR = “impressão”entao se TipoExpr = booleano então ERRO(“tipo invalido para impressão”)
             senão (* G. Código para impressão *)                 
             */
            case 141:
                if (this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                   
                    this.NPA = this.pilhaNPA.pop();
                    if (!this.pilhaValidarParametros.peek().isEmpty()) {
                        
                        this.tipoExprAux = this.pilhaTipoExpressaoTipoPreDefinido.pop();
                        this.parametroAux = this.pilhaValidarParametros.peek().pop();
                        PassagemValOuRefEnum eRef = this.pilhaERef.pop();

                        if (this.tipoExprAux != this.parametroAux.getTipoPreDefinidoEnum()) {
                            if (!(this.parametroAux.getTipoPreDefinidoEnum() == TipoPreDefinidoEnum.REAL && this.tipoExprAux == TipoPreDefinidoEnum.INTEIRO)
                                    && !(this.parametroAux.getTipoPreDefinidoEnum() == TipoPreDefinidoEnum.CADEIA && this.tipoExprAux == TipoPreDefinidoEnum.CARACTER)) {

                                throw new SemanticError("Parametro formal e atual não correspondem.", token.getPosition());
                            }
                        }

                        if (this.parametroAux.getPassagemValOuRefEnum() != eRef) {
                            if (this.parametroAux.getPassagemValOuRefEnum() == PassagemValOuRefEnum.REFERENCIA) {
                                throw new SemanticError("Esperava passagem por referência.", token.getPosition());
                            }
                        }
                    }
                    NPA++;
                    this.pilhaNPA.push(NPA);
                }
                if (this.pilhaExpressao.peek() == ExpressaoEnum.IMPRESSAO) {
                    this.tipoExprAux = this.pilhaTipoExpressaoTipoPreDefinido.pop();
                    if (this.tipoExprAux == TipoPreDefinidoEnum.BOOLEANO) {
                        throw new SemanticError("Tipo inválido para impressão.", token.getPosition());
                    }
                }
                break;

            /*
             #142 - TipoExpr := TipoExpSimples
             */
            case 142:
                this.pilhaTipoExpressaoTipoPreDefinido.push(this.pilhaTipoExpressaoSimplesTipoPreDefinido.pop());
                break;

            /*
             #143 – Se TipoExpSimples incompatível com TipoExpr então ERRO (“Operandos incompatíveis”)
             senão TipoExpr := “booleano”
             */
            case 143:
                this.tipoExprAux = this.pilhaTipoExpressaoTipoPreDefinido.pop();
                TipoPreDefinidoEnum tipoExpSimples = this.pilhaTipoExpressaoSimplesTipoPreDefinido.pop();

                if (this.tipoExprAux != tipoExpSimples) {
                    if ((this.tipoExprAux == TipoPreDefinidoEnum.INTEIRO && tipoExpSimples == TipoPreDefinidoEnum.REAL)
                            || (this.tipoExprAux == TipoPreDefinidoEnum.REAL && tipoExpSimples == TipoPreDefinidoEnum.INTEIRO)
                            || (this.tipoExprAux == TipoPreDefinidoEnum.CADEIA && tipoExpSimples == TipoPreDefinidoEnum.CARACTER)
                            || (this.tipoExprAux == TipoPreDefinidoEnum.CARACTER && tipoExpSimples == TipoPreDefinidoEnum.CADEIA)) {
                        this.pilhaTipoExpressaoTipoPreDefinido.push(TipoPreDefinidoEnum.BOOLEANO);
                    } else {
                        throw new SemanticError("Operandos incompatíveis.", token.getPosition());
                    }
                } else {
                    this.pilhaTipoExpressaoTipoPreDefinido.push(TipoPreDefinidoEnum.BOOLEANO);
                }
                if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                    this.pilhaERef.pop();
                    this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                }
                break;

            /*
             #144 a #149 – Guarda Operador Relacional para futura Geração de Código
             */
            case 144:
                this.pilhaOperadorRelacional.push(OperadorRelEnum.OPERADORIGUAL);
                break;

            case 145:
                this.pilhaOperadorRelacional.push(OperadorRelEnum.OPERADORMENOR);
                break;

            case 146:
                this.pilhaOperadorRelacional.push(OperadorRelEnum.OPERADORMAIOR);
                break;

            case 147:
                this.pilhaOperadorRelacional.push(OperadorRelEnum.OPERADORMAIORIGUAL);
                break;

            case 148:
                this.pilhaOperadorRelacional.push(OperadorRelEnum.OPERADORMENORIGUAL);
                break;

            case 149:
                this.pilhaOperadorRelacional.push(OperadorRelEnum.OPERADORDIFERENTE);
                break;

            /*
             #150 - TipoExpSimples := TipoTermo
             */
            case 150:
                this.pilhaTipoExpressaoSimplesTipoPreDefinido.push(this.pilhaTipoTermoTipoPreDefinido.pop());
                break;

            /*
             #151 – Se operador não se aplica a TipoExpSimples então ERRO(“Op. e Operando incompatíveis”) 
             */
            case 151:
                OperadorAddEnum operadorAddAux = this.pilhaOperadorAdd.peek();
                TipoPreDefinidoEnum tipo = this.pilhaTipoExpressaoSimplesTipoPreDefinido.peek();

                if ((operadorAddAux == OperadorAddEnum.OPERADORADICAO || operadorAddAux == OperadorAddEnum.OPERADORSUBTRACAO)
                        && (tipo != TipoPreDefinidoEnum.INTEIRO && tipo != TipoPreDefinidoEnum.REAL)) {
                    throw new SemanticError("Operador e operando incompatíveis.", token.getPosition());
                }

                if (operadorAddAux == OperadorAddEnum.OPERADOROU && tipo != TipoPreDefinidoEnum.BOOLEANO) {
                    throw new SemanticError("Operador e operando incompatíveis.", token.getPosition());
                }

                if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                    this.pilhaERef.pop();
                    this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                }
                break;

            /*
             #152 - Se TipoTermo incompatível com TipoExpSimples então ERRO (“Operandos incompatíveis”) 
             senão TipoExpSimples := tipo do resultado da operação(* Gera Código de acordo com oppad *)
             */
            case 152:
                TipoPreDefinidoEnum tipoTermo = this.pilhaTipoTermoTipoPreDefinido.pop();
                TipoPreDefinidoEnum tipoExprSimp = this.pilhaTipoExpressaoSimplesTipoPreDefinido.pop();
                OperadorAddEnum op = this.pilhaOperadorAdd.pop();

                //Se for Adição ou Subtração:
                if (op == OperadorAddEnum.OPERADORADICAO || op == OperadorAddEnum.OPERADORSUBTRACAO) {
                    if ((tipoExprSimp != TipoPreDefinidoEnum.INTEIRO && tipoExprSimp != TipoPreDefinidoEnum.REAL)
                            || (tipoTermo != TipoPreDefinidoEnum.INTEIRO && tipoTermo != TipoPreDefinidoEnum.REAL)) {
                        throw new SemanticError("Operandos incompatíveis.", token.getPosition());
                    } else {
                        //Se um dos dois forem Real, o resultado é Real
                        if (tipoExprSimp == TipoPreDefinidoEnum.REAL || tipoTermo == TipoPreDefinidoEnum.REAL) {
                            this.pilhaTipoExpressaoSimplesTipoPreDefinido.push(TipoPreDefinidoEnum.REAL);
                        } else {
                            this.pilhaTipoExpressaoSimplesTipoPreDefinido.push(TipoPreDefinidoEnum.INTEIRO);
                        }
                    }
                }

                //Se for operação lógica
                if (op == OperadorAddEnum.OPERADOROU) {
                    if (tipoTermo != TipoPreDefinidoEnum.BOOLEANO) {
                        throw new SemanticError("Operandos incompatíveis.", token.getPosition());
                    } else {
                        this.pilhaTipoExpressaoSimplesTipoPreDefinido.push(TipoPreDefinidoEnum.BOOLEANO);
                    }
                }
                break;

            /*
             #153 a #155 – guarda operador para futura G. código
             */
            case 153:
                this.pilhaOperadorAdd.push(OperadorAddEnum.OPERADORADICAO);
                break;

            case 154:
                this.pilhaOperadorAdd.push(OperadorAddEnum.OPERADORSUBTRACAO);
                break;

            case 155:
                this.pilhaOperadorAdd.push(OperadorAddEnum.OPERADOROU);
                break;

            /*
             #156 – TipoTermo := TipoFator
             */
            case 156:
                this.pilhaTipoTermoTipoPreDefinido.push(this.pilhaTipoFatorTipoPreDefinido.pop());
                break;

            /*
             #157 – Se operador não se aplica a TipoTermo então ERRO(“Op. e Operando incompatíveis”) 
             */
            case 157:
                OperadorMultEnum operadorMult = this.pilhaOperadorMult.peek();
                this.tipoTermoAux = this.pilhaTipoTermoTipoPreDefinido.peek();

                if ((operadorMult == OperadorMultEnum.OPERADORMULTIPLICACAO || operadorMult == OperadorMultEnum.OPERADORDIVISAO)
                        && (this.tipoTermoAux != TipoPreDefinidoEnum.INTEIRO && this.tipoTermoAux != TipoPreDefinidoEnum.REAL)) {

                    throw new SemanticError("Operador e operando incompatíveis.", token.getPosition());
                }
                if (operadorMult == OperadorMultEnum.OPERADORDIV && this.tipoTermoAux != TipoPreDefinidoEnum.INTEIRO) {
                    throw new SemanticError("Operador e operando incompatíveis.", token.getPosition());
                }
                if (operadorMult == OperadorMultEnum.OPERADORE && this.tipoTermoAux != TipoPreDefinidoEnum.BOOLEANO) {
                    throw new SemanticError("Operador e operando incompatíveis.", token.getPosition());
                }

                if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                    this.pilhaERef.pop();
                    this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                }
                break;

            /*
             #158 - Se TipoFator incompatível com TipoTermo então ERRO (“Operandos incompatíveis”) 
             senão TipoTermo := tipo do res. da operação (* G. Código de acordo com opmult *)    
             */
            case 158:
                this.tipoTermoAux = this.pilhaTipoTermoTipoPreDefinido.pop();
                this.tipoFatorAux = this.pilhaTipoFatorTipoPreDefinido.pop();
                OperadorMultEnum opMult = this.pilhaOperadorMult.pop();
                if (opMult == OperadorMultEnum.OPERADORMULTIPLICACAO || opMult == OperadorMultEnum.OPERADORDIVISAO) {

                    if ((this.tipoTermoAux != TipoPreDefinidoEnum.INTEIRO && this.tipoTermoAux != TipoPreDefinidoEnum.REAL)
                            || (this.tipoFatorAux != TipoPreDefinidoEnum.INTEIRO && this.tipoFatorAux != TipoPreDefinidoEnum.REAL)) {
                        throw new SemanticError("Operandos incompatíveis.", token.getPosition());
                    } else {
                        if ((this.tipoTermoAux == TipoPreDefinidoEnum.REAL || this.tipoFatorAux == TipoPreDefinidoEnum.REAL) || opMult == OperadorMultEnum.OPERADORDIVISAO) {
                            this.pilhaTipoTermoTipoPreDefinido.push(TipoPreDefinidoEnum.REAL);
                        } else {
                            this.pilhaTipoTermoTipoPreDefinido.push(TipoPreDefinidoEnum.INTEIRO);
                        }
                        ///////////////////// GERA CÓDIGO DE ACORDO COM OPMULT ///////////////////////// 
                    }
                }
                if(opMult == OperadorMultEnum.OPERADORDIV)
         {
             if(this.tipoTermoAux != TipoPreDefinidoEnum.INTEIRO || this.tipoFatorAux != TipoPreDefinidoEnum.INTEIRO)
             {
                 throw new SemanticError("Operandos incompatíveis", token.getPosition());                 
             }else
             {
                 this.pilhaTipoTermoTipoPreDefinido.push(TipoPreDefinidoEnum.INTEIRO);
             }
         }
         if(opMult == OperadorMultEnum.OPERADORE)
         {
             if(this.tipoTermoAux != TipoPreDefinidoEnum.BOOLEANO || this.tipoFatorAux != TipoPreDefinidoEnum.BOOLEANO)
             {
                 throw new SemanticError("Operandos incompatíveis", token.getPosition());                  
             }else
             {
                 this.pilhaTipoTermoTipoPreDefinido.push(TipoPreDefinidoEnum.BOOLEANO);                 
             }
         }
                break;

            /*
             #159 a #162 – guarda operador para futura G. código 
             */
            case 159:
                this.pilhaOperadorMult.push(OperadorMultEnum.OPERADORMULTIPLICACAO);
                break;

            case 160:
                this.pilhaOperadorMult.push(OperadorMultEnum.OPERADORDIVISAO);
                break;

            case 161:
                this.pilhaOperadorMult.push(OperadorMultEnum.OPERADORE);
                break;

            case 162:
                this.pilhaOperadorMult.push(OperadorMultEnum.OPERADORDIV);
                break;

            /*
             163 – se OpNega então ERRO(“Op. “não” repetido –não pode!”) 
             Senão OpNega := true 
             */
            case 163:
                if (!this.pilhaOperadorNega.isEmpty()) {
                    if (this.pilhaOperadorNega.peek()) {
                        throw new SemanticError("Operador 'não' repetido indevidamente.", token.getPosition());
                    } else {
                        this.pilhaOperadorNega.push(true);
                        if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                            this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                        }
                    }
                }
                break;

            /*
             #164 – Se TipoFator <> “booleano”então ERRO(“Op. ‘não’ exige operando bool.”)
             senao OpNega := false 
             */
            case 164:
                this.tipoFatorAux = this.pilhaTipoFatorTipoPreDefinido.peek();
                if (this.tipoFatorAux != TipoPreDefinidoEnum.BOOLEANO) {
                    throw new SemanticError("Operador 'não' exige operando booleano.", token.getPosition());
                } else {
                    this.pilhaOperadorNega.pop();
                    this.pilhaOperadorNega.push(false);
                }
                break;

            /*
             #165 – se OpUnario então ERRO(“Op. “unário” repetido ”) 
             Senão OpUnario := true
             */
            case 165:
                if (!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek()) {
                    throw new SemanticError("Operador 'unário' repetido.", token.getPosition());
                } else {
                    this.pilhaOperadorUnario.push(true);
                    if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                        this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                    }
                }
                break;

            /* 
             #166 - Se TipoFator <> “inteiro” ou de “real” então ERRO(“Op. unário exige operando num.”)
             senao OpUnario := false
             */
            case 166:
                this.tipoFatorAux = this.pilhaTipoFatorTipoPreDefinido.peek();
                if (this.tipoFatorAux != TipoPreDefinidoEnum.INTEIRO && this.tipoFatorAux != TipoPreDefinidoEnum.REAL) {
                    throw new SemanticError("Operador unário exige operando numeral.", token.getPosition());
                } else {
                    this.pilhaOperadorUnario.pop();
                    this.pilhaOperadorUnario.push(false);
                }

                break;

            /*
             #167 - OpNega := OpUnario := false
             */
            case 167:
                this.pilhaOperadorNega.push(false);
                this.pilhaOperadorUnario.push(false);
                break;

            /*
             #168 – TipoFator := TipoExpr 
             */
            case 168:
                this.pilhaTipoFatorTipoPreDefinido.push(this.pilhaTipoExpressaoTipoPreDefinido.pop());
                /*   this.pilhaOperadorNega.pop();
                 this.pilhaOperadorUnario.pop();
                 if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                 if ((!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) || (!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                 this.pilhaERef.pop();
                 this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                 }

                 }*/
                break;

            /*
             #169 – TipoFator := tipoVarTipoPreDefinido
             */
            case 169:
                this.pilhaTipoFatorTipoPreDefinido.push(tipoVarTipoPreDefinido);
                break;

            /*
             #170 – TipoFator := TipoCte
             */
            case 170:
                this.pilhaTipoFatorTipoPreDefinido.push(this.tipoConstanteTipoPreDefinido);
                /*
                 if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                 if (!(!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) && !(!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                 this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                 }
                 }*/
                break;

            /*
             #171 - se categoria de id <> método então ERRO(“id deveria ser um método”) 
             senão se tipo método = “nulo” ntão ERRO(“esperava-se mét. com tipo”) 
             senao NPA := 0 (Núm. de Par. Atuais) seta contextoEXPR para “par-atual” 
             */
            case 171:
                this.simboloAux = tabSimbolos.getSimbolo(this.pilhaPosicaoID.peek());
                if (this.simboloAux.getCategoria() != CategoriaIDEnum.METODO) {
                    throw new SemanticError("id deveria ser um método.", token.getPosition());
                } else {
                    this.metodoAux = (Metodo) this.simboloAux;
                    if (this.metodoAux.isRetornoNull()) {
                        throw new SemanticError("Esperava-se método com tipo.", token.getPosition());
                    } else {
                        this.pilhaNPA.push(0);
                        this.pilhaExpressao.push(ExpressaoEnum.PAR_ATUAL);
                    }
                    this.pilhaValidarParametros.push(this.metodoAux.getPilhaParametro());
                }
                this.pilhaExpressao.push(ExpressaoEnum.PAR_ATUAL);
                break;

            /*
             #172 – se NPA = NPF então tipoVarTipoPreDefinido := Tipo do resultado da função (* Gera Código p/ ativação do método *) 
             senão ERRO(“Erro na quant de parâmetros”) 
             */
            case 172:
                this.NPA = this.pilhaNPA.pop();
                this.simboloAux = tabSimbolos.getSimbolo(pilhaPosicaoID.pop());
                this.metodoAux = (Metodo) this.simboloAux;
                if (NPA == this.metodoAux.getNumeroDeParametros()) {
                    this.tipoVarTipoPreDefinido = this.metodoAux.getResultado();
                    ////////////////////////////// GERA CODIGO PARA ATIVAÇÃO DO METODO ///////////////////////////
                } else {
                    throw new SemanticError("Erro na quantidade de parâmetros.", token.getPosition());
                }
                /*  this.pilhaExpressao.push(ExpressaoEnum.PAR_ATUAL);

                 if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                 if (!(!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) && !(!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                 this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                 }
                 }

                 this.pilhaValidarParametros.pop();*/
                break;

            /*
             #173 – se TipoExpr <> “inteiro” ntão ERRO(“índice deveria ser inteiro”) 
             senão se TipoVarIndexada = cadeia então tipoVarTipoPreDefinido := “caracter” 
             senao tipoVarTipoPreDefinido := TipoElementos do vetor
             */
            case 173:
                if (this.pilhaTipoExpressaoTipoPreDefinido.pop() != TipoPreDefinidoEnum.INTEIRO) {
                    throw new SemanticError("Índice deveria ser inteiro.", token.getPosition());
                } else {
                    if (this.pilhaTipoVariavelIndexadaTipoPreDefinido.pop() == TipoPreDefinidoEnum.CADEIA) {
                        this.tipoVarTipoPreDefinido = TipoPreDefinidoEnum.CARACTER;
                        this.pilhaPosicaoID.pop();
                    } else {
                        this.variavelAux = (Variavel) this.tabSimbolos.getSimbolo(this.pilhaPosicaoID.pop());
                        this.tipoVarTipoPreDefinido = this.variavelAux.getTipoDeVariavel().getTipoPreDefinidoEnum();
                    }
                }
                /*
                 if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                 if (!(!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) && !(!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                 this.pilhaERef.push(PassagemValOuRefEnum.REFERENCIA);
                 }
                 }*/
                break;

            /*
             #174 - se categoria de id = “variável” ou “Parâmetro” então se tipo de id = “vetor” então ERRO(“vetor deve ser indexado”)
             senão tipoVarTipoPreDefinido := Tipo de id 
             senão se categoria de id = método então se tipo método = “nulo” então ERRO(“Esperava-se método com tipo”)
             senão se NPF <> 0 então ERRO(“Erro na quant. de parâmetros”) 
             senão tipoVarTipoPreDefinido:=Tipo resultado(* Gera Código *)
             Senão se categoria de id = “constante” então tipoVarTipoPreDefinido:= tipoConstanteTipoPreDefinido 
             Senão ERRO(“esperava-se var, id- método ou constante”
             */
            case 174:
                this.simboloAux = this.tabSimbolos.getSimbolo(this.pilhaPosicaoID.pop());
                if (this.simboloAux.getCategoria() == CategoriaIDEnum.PARAMETRO || this.simboloAux.getCategoria() == CategoriaIDEnum.VARIAVEL) {
                    if (this.simboloAux.getCategoria() == CategoriaIDEnum.VARIAVEL) {
                        this.variavelAux = (Variavel) this.simboloAux;
                        if (this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum() == TipoDeVariavelEnum.VETOR) {
                            throw new SemanticError("Vetor deve ser indexado.", token.getPosition());
                        } else {
                            tipoVarTipoPreDefinido = Converte.getTipoPreDefinidoEnum(this.variavelAux.getTipoDeVariavel().getTipoDeVariavelEnum());
                        }
                    } else {
                        this.parametroAux = (Parametro) this.simboloAux;
                        tipoVarTipoPreDefinido = this.parametroAux.getTipoPreDefinidoEnum();
                    }

                    if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                        if (!(!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) && !(!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                            this.pilhaERef.push(PassagemValOuRefEnum.REFERENCIA);
                        }
                    }

                } else {
                    if (this.simboloAux.getCategoria() == CategoriaIDEnum.METODO) {
                        this.metodoAux = (Metodo) this.simboloAux;
                        if (this.metodoAux.isRetornoNull()) {
                            throw new SemanticError("Esperava-se método com tipo.", token.getPosition());
                        } else {
                            if (this.metodoAux.getNumeroDeParametros() != 0) {
                                throw new SemanticError("Erro na quantidade de parâmetros.", token.getPosition());
                            } else {
                                tipoVarTipoPreDefinido = this.metodoAux.getResultado();
                            }

                            if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {
                                if (!(!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) && !(!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                                    this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                                }
                            }
                        }

                    } else {
                        if (this.simboloAux.getCategoria() == CategoriaIDEnum.CONSTANTE) {
                            this.constanteAux = (Constante) this.simboloAux;
                            tipoVarTipoPreDefinido = this.constanteAux.getTipoPreDefinidoEnum();
                            if (!this.pilhaExpressao.isEmpty() && this.pilhaExpressao.peek() == ExpressaoEnum.PAR_ATUAL) {

                                if (!(!this.pilhaOperadorNega.isEmpty() && this.pilhaOperadorNega.peek()) && !(!this.pilhaOperadorUnario.isEmpty() && this.pilhaOperadorUnario.peek())) {
                                    this.pilhaERef.push(PassagemValOuRefEnum.VALOR);
                                }
                            }
                        } else {
                            throw new SemanticError("Esperava-se var, id, método ou constante.", token.getPosition());
                        }
                    }
                }
                break;

            /*
             #175 - Se id não está declarado então ERRO(“Id não declarado”) 
             senão se categoria de id <> constante entao ERRO (“id de Constante esperado ”) 
             senão tipoConstanteTipoPreDefinido = Tipo do id-constante valConst = Valor da constante id
             */
            case 175:
                this.simboloAux = this.tabSimbolos.getSimboloNoNivel(token.getLexeme(), this.NA);
                if (this.simboloAux == null) {
                    throw new SemanticError("id não declarado.", token.getPosition());
                } else {
                    if (this.simboloAux.getCategoria() != CategoriaIDEnum.CONSTANTE) {
                        throw new SemanticError("id de constante esperado.", token.getPosition());
                    } else {
                        this.constanteAux = (Constante) this.simboloAux;
                        this.valConst = this.constanteAux.getValor();
                        this.tipoConstanteTipoPreDefinido = this.constanteAux.getTipoPreDefinidoEnum();
                    }
                }
                break;

            /*
             #176 a #180 – tipoConstanteTipoPreDefinido:= tipo da constante
             valConst:= valor da constante
             */
            case 176:
                this.tipoConstanteTipoPreDefinido = TipoPreDefinidoEnum.INTEIRO;
                this.valConst = token.getLexeme();
                break;

            case 177:
                this.tipoConstanteTipoPreDefinido = TipoPreDefinidoEnum.REAL;
                this.valConst = token.getLexeme();
                break;

            case 178:
                this.tipoConstanteTipoPreDefinido = TipoPreDefinidoEnum.BOOLEANO;
                this.valConst = token.getLexeme();
                break;

            case 179:
                this.tipoConstanteTipoPreDefinido = TipoPreDefinidoEnum.BOOLEANO;
                this.valConst = token.getLexeme();
                break;

            case 180:
                if (token.getLexeme().length() - 2 == 1) {
                    // OU 
                    //if (token.getLexeme().length() == 1) {
                    //TESTAR O CERTO!!!
                    this.tipoConstanteTipoPreDefinido = TipoPreDefinidoEnum.CARACTER;
                } else {
                    this.tipoConstanteTipoPreDefinido = TipoPreDefinidoEnum.CADEIA;
                }
                this.valConst = token.getLexeme();
                break;
        }
        /*public void execAcao101(Token token) {
       
         this.tabSimbolos = new TabelaDeSimbolos();
         this.NA = 0;
         this.deslocamento.push(0);
         Simbolo id = new Simbolo(token.getLexeme(), CategoriaIDEnum.PROGRAMA, NA);
         this.NA++;
         this.tabSimbolos.addSimbolo(id);
         }*/

    }

}
