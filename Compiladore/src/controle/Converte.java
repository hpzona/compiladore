package controle;

// Classe desenvolvida por Lucas e Willian
public class Converte {

    public static TipoDeVariavelEnum getTipoDeVariavelEnum(TipoPreDefinidoEnum tipoPreDefinidoEnum)
    {
        switch(tipoPreDefinidoEnum)
        {
            case BOOLEANO : 
                return TipoDeVariavelEnum.BOOLEANO;
            case CADEIA   : 
                return TipoDeVariavelEnum.CADEIA;
            case CARACTER : 
                return TipoDeVariavelEnum.CARACTER;
            case INTEIRO  : 
                return TipoDeVariavelEnum.INTEIRO;
            case REAL : 
                return TipoDeVariavelEnum.REAL;
        }
        return TipoDeVariavelEnum.BOOLEANO;   
    }
    
    public static TipoPreDefinidoEnum getTipoPreDefinidoEnum(TipoDeVariavelEnum tipoVariavelDeEnum)
    {
        switch(tipoVariavelDeEnum)
        {
            case BOOLEANO : 
                return TipoPreDefinidoEnum.BOOLEANO;
            case CADEIA   : 
                return TipoPreDefinidoEnum.CADEIA;
            case CARACTER : 
                return TipoPreDefinidoEnum.CARACTER;
            case INTEIRO  : 
                return TipoPreDefinidoEnum.INTEIRO;
            case REAL     : 
                return TipoPreDefinidoEnum.REAL;
        }
        return TipoPreDefinidoEnum.BOOLEANO;   
    }
}
