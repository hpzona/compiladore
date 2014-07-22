package controle;

// Classe desenvolvida por Lucas e Willian
public class Converte {

    public static SubCategoriaPreDefEnum getTipoDeVariavelEnum(TipoPreDefinidoEnum tipoPreDefinidoEnum)
    {
        switch(tipoPreDefinidoEnum)
        {
            case BOOLEANO : 
                return SubCategoriaPreDefEnum.BOOLEANO;
            case CADEIA   : 
                return SubCategoriaPreDefEnum.CADEIA;
            case CARACTER : 
                return SubCategoriaPreDefEnum.CARACTER;
            case INTEIRO  : 
                return SubCategoriaPreDefEnum.INTEIRO;
            case REAL : 
                return SubCategoriaPreDefEnum.REAL;
        }
        return SubCategoriaPreDefEnum.BOOLEANO;   
    }
    
    public static TipoPreDefinidoEnum getTipoPreDefinidoEnum(SubCategoriaPreDefEnum tipoVariavelDeEnum)
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
