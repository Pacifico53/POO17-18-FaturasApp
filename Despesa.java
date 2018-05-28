import java.util.Date;
import java.io.Serializable;
/**
 * Classe das despesas
 *
 * 
 */
public class Despesa implements Serializable
{

    private int NIFemitente;
    private String designacaoEmitente;
    private Date dataDesp;
    private int NIFcliente;
    private String descricaoDesp;
    private String naturezaDesp;
    private double valorDesp;

    //CONSTRUTORES
    /**
     * Construtor vazio
     */
    public Despesa()
    {
        this.NIFemitente = 0;
        this.designacaoEmitente = "";
        this.dataDesp = new Date();
        this.NIFcliente = 0;
        this.descricaoDesp = "";
        this.naturezaDesp = "";
    } 
    /**
     * Construtor parameterizado
     */
    public Despesa(int nFEmitente, String desigEmitente, Date data, int nFCliente, String descrDespesa, String naturDespesa, double valor)
    {
        this.NIFemitente = nFEmitente;
        this.designacaoEmitente = desigEmitente;
        this.dataDesp = data;
        this.NIFcliente = nFCliente;
        this.descricaoDesp = descrDespesa;
        this.naturezaDesp = naturDespesa;
        this.valorDesp = valor;
    }
    /**
     * Construtor de copia
     */
    public Despesa(Despesa d)
    {
        this.NIFcliente = d.getNFEmitente();
        this.designacaoEmitente = d.getDesignEmitente();
        this.dataDesp = d.getDataDespesa();
        this.NIFcliente = d.getNFCliente();
        this.descricaoDesp = d.getDescrDespesa();
        this.naturezaDesp = d.getNaturezaDespesa();
        this.valorDesp = d.getValor();
    }
    
    //GETS E SETS
    
    /**
     * Get do numero fiscal emitente
     */
    public int getNFEmitente()
    {
        return this.NIFemitente;
    }
    /**
     * Set do numero fiscal emitente
     */
    public void setNFEmitente(int numero)
    {
        this.NIFemitente = numero;
    }
    
    /**
     * Get da designacao do emitente
     */
    public String getDesignEmitente()
    {
        return this.designacaoEmitente;
    }
    /**
     * Set da designacao do emitente
     */
    public void setDesignEmitente(String designacao)
    {
        this.designacaoEmitente = designacao;
    }
    
    
    /**
     * Get da data da despesa
     */
    public Date getDataDespesa()
    {
        return this.dataDesp;
    }
    /**
     * Set da data da despesa
     */
    public void setDataDespesa(Date data)
    {
        this.dataDesp = data;
    }
    
    /**
     * Get do numero fiscal do cliente
     */
    public int getNFCliente()
    {
        return this.NIFcliente;
    }
    /**
     * Set do numero fiscal do cliente
     */
    public void setNFCliente(int numero)
    {
        this.NIFcliente = numero;
    }
    
    
    /**
     * Get da descricao da despesa
     */
    public String getDescrDespesa()
    {
        return this.descricaoDesp;
    }
    /**
     * Set da descricao da despesa
     */
    public void setDescrDespesa(String descricao)
    {
        this.descricaoDesp = descricao;
    }
    
    /**
     * Get da natureza da despesa
     */
    public String getNaturezaDespesa()
    {
        return this.naturezaDesp;
    }
    /**
     * Set da natureza da despesa
     */
    public void setNaturezaDespesa(String natureza)
    {
        this.naturezaDesp = natureza;
    }
    
    /**
     * Get do valor da despesa
     */
    public double getValor()
    {
        return this.valorDesp;
    }
    /**
     * Set do valor da despesa
     */
    public void setValor(double valor)
    {
        this.valorDesp = valor;
    }
    
    /**
     * Retorna o fator de deducao de uma despesa dado o codigo de uma actividade economica
     */
    public double getFatorDedCodigo(int codigo){
        double fatorDeducao = 0;
        switch(codigo){
            case 0: fatorDeducao = 0.10;
                    break;
            case 1: fatorDeducao = 0.08;
                    break;
            case 2: fatorDeducao = 0.12;
                    break;
            case 3: fatorDeducao = 0.11;
                    break;
            case 4: fatorDeducao = 0.13;
                    break;
            case 5: fatorDeducao = 0.09;
                    break;
            case 6: fatorDeducao = 0.11;
                    break;
            case 7: fatorDeducao = 0.09;
                    break;
            case 8: fatorDeducao = 0.06;
                    break;
            case 9: fatorDeducao = 0.15;
                    break;
        }
        return fatorDeducao;
    }
    
    /**
     * Retorna o fator de deducao de uma despesa dado a String de uma actividade economica
     */
    public double getFatorDedString(String natureza){
        double fatorDeducao = 0;
        if(natureza.equals("TRANSPORTES")) fatorDeducao = 0.10;
        if(natureza.equals("CABELEIREIROS")) fatorDeducao = 0.08;
        if(natureza.equals("ELECTRICIDADE")) fatorDeducao = 0.12;
        if(natureza.equals("EDUCAÇAO")) fatorDeducao = 0.11;
        if(natureza.equals("GAS")) fatorDeducao = 0.13;
        if(natureza.equals("SAUDE")) fatorDeducao = 0.09;
        if(natureza.equals("AGUA")) fatorDeducao = 0.11;
        if(natureza.equals("SERVIÇOS_BANCARIOS")) fatorDeducao = 0.09;
        if(natureza.equals("RESTAURAÇAO")) fatorDeducao = 0.06;
        if(natureza.equals("REPARAÇAO_VEICULOS")) fatorDeducao = 0.15;
        return fatorDeducao;
    }
    
    /**
     * Retorna o codigo da natureza dada a String dela
     */
    public int getCodigoNatureza(){
        int codigo = 0;
        if(this.naturezaDesp.equals("TRANSPORTES")) codigo = 0;
        if(this.naturezaDesp.equals("CABELEIREIROS")) codigo = 1;
        if(this.naturezaDesp.equals("ELECTRICIDADE")) codigo = 2;
        if(this.naturezaDesp.equals("EDUCAÇAO")) codigo = 3;
        if(this.naturezaDesp.equals("GAS")) codigo = 4;
        if(this.naturezaDesp.equals("SAUDE")) codigo = 5; 
        if(this.naturezaDesp.equals("AGUA")) codigo = 6;
        if(this.naturezaDesp.equals("SERVIÇOS_BANCARIOS")) codigo = 7;
        if(this.naturezaDesp.equals("RESTAURAÇAO")) codigo = 8;
        if(this.naturezaDesp.equals("REPARAÇAO_VEICULOS")) codigo = 9;
        return codigo;
    }
    
    /**
     * Funçao para representar uma despesa numa String
     */
    public String toString()
    {
        return "\nNIF Emitente = " + this.NIFemitente +
        "\nDesignacao do Emitente = " + this.designacaoEmitente + 
        "\nData da Despesa = " + this.dataDesp +
        "\nNIF Cliente = " + this.NIFcliente + 
        "\nDescricao da Despesa = " + this.descricaoDesp + 
        "\nNatureza da Despesa = " + this.naturezaDesp + 
        "\nValor da Despesa = " + this.valorDesp;
    }
}
