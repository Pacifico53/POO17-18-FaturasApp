import java.io.Serializable;
/**
 * Classe do contribuinte
 *
 * 
 */
public abstract class Contribuinte implements Serializable
{
    private int NIF;
    private String email;
    private String nome;
    private String morada;
    private String password;
    
    //CONSTRUTORES:
    /**
     * Construtor vazio
     */
    public Contribuinte()
    {
        this.NIF = 0;
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.password = "";
    }
    /**
     * Construtor parameterizado
     */
    public Contribuinte(int newNIF, String newEmail, String newNome, String newMorada, String newPassword)
    {
        this.NIF = newNIF;
        this.email = newEmail;
        this.nome = newNome;
        this.morada = newMorada;
        this.password = newPassword;
    }
    /**
     * Construtor de copia
     */
    public Contribuinte(Contribuinte c)
    {
        this.NIF = c.getNIF();
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.morada = c.getMorada();
        this.password = c.getPassword();
    }
    
    /**
     * Funcao equals para comparar dois contribuintes
     */
    public boolean equals(Object l){
        if(this == l) return true;
        if(l == null || this.getClass() != l.getClass()) return false;
        Contribuinte ll = (Contribuinte) l;
        return (this.getEmail() == ll.getEmail() &&
        this.getNIF() == ll.getNIF() &&
        this.getMorada() == ll.getMorada() &&
        this.getNome() == ll.getNome() &&
        this.getPassword() == ll.getPassword());
    }
    
    //GETS E SETS
    /**
     * Get do NIF do contribuinte
     */
    public int getNIF()
    {
        return this.NIF;
    }
    /**
     * Set do NIF do contribuinte
     */
    public void setNIF(int newNif)
    {
        this.NIF = newNif;
    }
    
    /**
     * Get do email do contribuinte
     */
    public String getEmail()
    {
        return this.email;
    }
    /**
     * Set do email do contribuinte
     */
    public void setEmail(String newEmail)
    {
        this.email = newEmail;
    }
    
    /**
     * Get do nome do contribuinte
     */
    public String getNome()
    {
        return this.nome;
    }
    /**
     * Set do nome do contribuinte
     */
    public void setNome(String newNome)
    {
        this.nome = newNome;
    }
    
    /**
     * Get da Morada do contribuinte
     */
    public String getMorada()
    {
        return this.morada;
    }
    /**
     * Set da Morada do contribuinte
     */
    public void setMorada(String newMorada)
    {
        this.morada = newMorada;
    }
    
    /**
     * Get da Password do contribuinte
     */
    public String getPassword()
    {
        return this.password;
    }
    /**
     * Set da password do contribuinte
     */
    public void setPassword(String newPassword)
    {
        this.password = newPassword;
    }
    
    /**
     * Funçao para representar uma contribuinte numa String
     */
    public String toString()
    {
        return "\nNIF = " + this.NIF +
        "\nNome = " + this.nome + 
        "\nMorada = " + this.morada +
        "\nE-Mail = " + this.email + 
        "\nPassword = " + this.password;
    }
}
