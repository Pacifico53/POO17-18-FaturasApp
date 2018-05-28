import java.util.ArrayList;
/**
 * Classe Contribuinte Individual (sub classe de contribuinte)
 *
 * 
 */
public class ContribuinteIndividual extends Contribuinte
{
    private int numeroDependentes;
    private ArrayList<Integer> numerosFiscais;
    private double coeficienteFiscal;
    private ArrayList<Integer> codigosActividades;

    //CONSTRUTORES
    /**
     * Construtor vazio
     */
    public ContribuinteIndividual()
    {
        super();
        this.numeroDependentes = 0;
        this.numerosFiscais = new ArrayList<Integer>();
        this.coeficienteFiscal = 0;
        this.codigosActividades = new ArrayList<Integer>();
    }
    /**
     * Construtor parameterizado
     */
    public ContribuinteIndividual(int newNIF, String newEmail, String newNome, String newMorada, String newPassword, int nmroDependentes, ArrayList nmrosFiscais, double coeficiente, ArrayList codigosActEco)
    {
        super(newNIF, newEmail, newNome, newMorada, newPassword);
        this.numeroDependentes = nmroDependentes;
        this.numerosFiscais = nmrosFiscais;
        this.coeficienteFiscal = coeficiente;
        this.codigosActividades = codigosActEco;
    }
    /**
     * Construtor de copia
     */
    public ContribuinteIndividual(ContribuinteIndividual c)
    {
        super(c);
        this.numeroDependentes = c.getNumeroDependentes();
        this.numerosFiscais = c.getNumerosFiscais();
        this.coeficienteFiscal = c.getCoeficiente();
        this.codigosActividades = c.getCodigosActividades();
    }
    
    //GETS E SETS
    
    /**
     * Get do numero de dependentes
     */
    public int getNumeroDependentes()
    {
        return this.numeroDependentes;
    }
    /**
     * Set do numero de dependentes
     */
    public void setNumeroDependentes(int newNumeroDependentes)
    {
        this.numeroDependentes = newNumeroDependentes;
    }
    
    /**
     * Get dos numeros Fiscais
     */
    public ArrayList getNumerosFiscais()
    {
        return this.numerosFiscais;
    }
    /**
     * Set dos numeros Fiscais
     */
    public void setNumerosFiscais(ArrayList newNumerosFiscais)
    {
        this.numerosFiscais = newNumerosFiscais;
    }
    
    /**
     * Get do coeficiente Fiscal
     */
    public double getCoeficiente()
    {
        return this.coeficienteFiscal;
    }
    /**
     * Set do coeficiente Fiscal
     */
    public void setCoeficienteFiscal(double newCoeficienteFiscal)
    {
        this.coeficienteFiscal = newCoeficienteFiscal;
    }
    
    /**
     * Get dos codigos das atividades economicas para as quais um determinado contribuinte tem possibilidade de deduzir despesas
     */
    public ArrayList getCodigosActividades()
    {
        return this.codigosActividades;
    }
    /**
     * Set dos codigos das atividades economicas para as quais um determinado contribuinte tem possibilidade de deduzir despesas
     */
    public void setCodigosActividades(ArrayList newCodigosAtividades)
    {
        this.codigosActividades = newCodigosAtividades;
    }
    
    /**
     * Funcao equals para comparar dois contribuintes individuais
     */
    public boolean equals(Object l){
        if(this == l) return true;
        if(l == null || this.getClass() != l.getClass()) return false;
        ContribuinteIndividual ll = (ContribuinteIndividual) l;
        return (super.equals(ll) &&
        this.getNumeroDependentes() == ll.getNumeroDependentes() &&
        this.getCodigosActividades() == ll.getCodigosActividades() &&
        this.getCoeficiente() == ll.getCoeficiente());
    }
    
    /**
     * Funcao para clonar um contribuinte
     */
    public ContribuinteIndividual clone()
    {
        return new ContribuinteIndividual(this);
    }
    
    /**
     * Funçao para representar um contribuinte numa String
     */
    public String toString()
    {
        return super.toString() + 
        "\nNumero de dependentes = " + this.numeroDependentes +
        "\nNumeros Fiscais do agregado familiar = " + this.numerosFiscais.toString() + 
        "\nCoeficiente Fiscal = " + this.coeficienteFiscal +
        "\nCodigos das actividades economicas = " + this.codigosActividades;
    }
}



