import java.util.ArrayList;
/**
 * Classe das empresas (sub classe de contribuinte)
 *
 * 
 */
public class Empresas extends Contribuinte
{
    private ArrayList<String> actividadesEconomicas;
    private double fatorDeducao;

    /**
     * Construtor vazio
     */
    public Empresas()
    {
        super();
        this.fatorDeducao = 0;
        this.actividadesEconomicas = new ArrayList<String>();
    }
    /**
     * Construtor parameterizado
     */
    public Empresas(int newNIF, String newEmail, String newNome, String newMorada, String newPassword, ArrayList activEconomicas, double fatorDeduc)
    {
        super(newNIF, newEmail, newNome, newMorada, newPassword);
        this.actividadesEconomicas = activEconomicas;
        this.fatorDeducao = fatorDeduc;
    }
    /**
     * Construtor de copia
     */
    public Empresas(Empresas c)
    {
        super(c);
        this.fatorDeducao = c.getFatorDeducao();
        this.actividadesEconomicas = c.getActividadesEconomicas();
    }
    
    //GETS E SETS
    
    /**
     * Get das atividades economicas de um contribuinte empresarial
     */
    public ArrayList getActividadesEconomicas()
    {
        return this.actividadesEconomicas;
    }
    /**
     * Set das atividades economicas de um contribuinte empresarial
     */
    public void setActividadesEconomicas(ArrayList newActividadesEconomicas)
    {
        this.actividadesEconomicas = newActividadesEconomicas;
    }
    /**
     * Get do fator de deduçao de um contribuinte empresarial
     */
    public double getFatorDeducao()
    {
        return this.fatorDeducao;
    }
    /**
     * Set do fator de deduçao de um contribuinte empresarial
     */
    public void setFatorDeducao(double newFatorDeducao)
    {
        this.fatorDeducao = newFatorDeducao;
    }
    
    /**
     * Funcao equals para comparar dois contribuintes empresariais
     */
    public boolean equals(Object l){
        if(this == l) return true;
        if(l == null || this.getClass() != l.getClass()) return false;
        Empresas ll = (Empresas) l;
        return (super.equals(ll) &&
        this.getActividadesEconomicas() == ll.getActividadesEconomicas() &&
        this.getFatorDeducao() == ll.getFatorDeducao());
    }
    
    /**
     * Funcao para clonar um contribuinte empresarial
     */
    public Empresas clone()
    {
        return new Empresas(this);
    }
    
    /**
     * Funçao para representar uma contribuinte empresarial numa String
     */
    public String toString()
    {
        return super.toString() + 
        "\nActividades Economicas = " + this.actividadesEconomicas +
        "\nFator de Deduçao = " + this.fatorDeducao;
    }
}
