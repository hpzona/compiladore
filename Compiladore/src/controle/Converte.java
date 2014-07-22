package controle;

// Classe desenvolvida por Lucas e Willian
public class Converte {

    public static SubCategoriaVariavelEnum getTipoDeVariavelEnum(TipoEnum tipoPreDefinidoEnum)
    {
        switch(tipoPreDefinidoEnum)
        {
            case BOOLEANO : 
                return SubCategoriaVariavelEnum.BOOLEANO;
            case CADEIA   : 
                return SubCategoriaVariavelEnum.CADEIA;
            case CARACTER : 
                return SubCategoriaVariavelEnum.CARACTER;
            case INTEIRO  : 
                return SubCategoriaVariavelEnum.INTEIRO;
            case REAL : 
                return SubCategoriaVariavelEnum.REAL;
        }
        return SubCategoriaVariavelEnum.BOOLEANO;   
    }
    
    public static TipoEnum getTipoPreDefinidoEnum(SubCategoriaVariavelEnum tipoVariavelDeEnum)
    {
        switch(tipoVariavelDeEnum)
        {
            case BOOLEANO : 
                return TipoEnum.BOOLEANO;
            case CADEIA   : 
                return TipoEnum.CADEIA;
            case CARACTER : 
                return TipoEnum.CARACTER;
            case INTEIRO  : 
                return TipoEnum.INTEIRO;
            case REAL     : 
                return TipoEnum.REAL;
        }
        return TipoEnum.BOOLEANO;   
    }
}
