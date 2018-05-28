import java.util.Comparator;
import java.io.Serializable;

/**
 * Classe comparator para datas.
 *
 *
 */
public class ComparatorDataDespesa implements Comparator<Despesa>, Serializable
{
    /**
    * Compara duas datas dando duas despesas como argumentos.
    */    
    public int compare(Despesa d1, Despesa d2) {
                   return d1.getDataDespesa().compareTo(d2.getDataDespesa());
    }
}
