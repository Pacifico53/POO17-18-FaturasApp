import java.util.Comparator;
import java.io.Serializable;

/**
 * Classe comparator para despesas
 */
public class ComparatorValorDespesa implements Comparator<Despesa>, Serializable
{
    /**
    * Compara dois valores dando duas despesas como argumentos.
    */
    public int compare(Despesa d1, Despesa d2) {
                   if(d1.getValor() == d2.getValor())
                    return 0;
                   if(d1.getValor() > d2.getValor())
                    return 1;
                   else return -1;
    }
}
