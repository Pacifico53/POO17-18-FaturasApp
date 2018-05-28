import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Serializable;
/**
 * Aqui se encontram as estruturas utilizadas no programa
 *
 * 
 */
public class FaturasData implements Serializable
{
    private HashMap<Integer, Empresas> empresasHash;
    private HashMap<Integer, ContribuinteIndividual> individuaisHash;
    private ArrayList<Despesa> despesas;
    
    private ContribuinteIndividual contrInd = null;
    private Empresas empresa = null;
    private Despesa desp = null;
    
    /**
     * Constructor que inicializa a estrutura
     */
    public FaturasData()
    {
        this.empresasHash = new HashMap<Integer, Empresas>();
        this.individuaisHash = new HashMap<Integer, ContribuinteIndividual>();
        this.despesas = new ArrayList<>();
    }

    //GETS E SETS
    /**
     * Get da HashMap das Empresas
     */
    public HashMap getHashEmpresas()
    {
        return this.empresasHash;
    }  
    /**
     * Get da HashMap dos contribuintes individuais
     */
    public HashMap getHashIndividuais()
    {
        return this.individuaisHash;
    }
    /**
     * Get da ArrayList das despesas
     */
    public ArrayList getListaDespesas()
    {
        return this.despesas;
    }
    /**
     * Get da Empresa logged in no momento
     */
    public Empresas getEmpresa()
    {
        return this.empresa;
    }
    /**
     * Get do contribuinte individual logged in no momento
     */
    public ContribuinteIndividual getContrIndividual()
    {
        return this.contrInd;
    }
 
    /**
     * Inserir uma Empresa nova na HashMap
     */
    public void addEmpresa(Empresas emp) throws ExceptionErroLogIn
    {
        if(this.empresasHash.containsKey(emp.getNIF())) throw new ExceptionErroLogIn("Erro, o NIF inserido ja esta associado a um contribuinte.");        
        this.empresasHash.put(emp.getNIF(), emp);
    }
    /**
     * Inserir um Contribuinte individual novo na HashMap
     */
    public void addIndividual(ContribuinteIndividual contr) throws ExceptionErroLogIn
    {
        if(this.individuaisHash.containsKey(contr.getNIF())) throw new ExceptionErroLogIn("Erro, o NIF inserido ja esta associado a um contribuinte.");
        else {this.individuaisHash.put(contr.getNIF(), contr);}
    }
    /**
     * Inserir uma Despesa na ArrayList
     */
    public void addDespesa(Despesa desp)
    {
        this.despesas.add(desp);
    }
    
    /** 
     * Metodo em que um utilizador (individual ou empresa) faz login no programa
     */
    public void iniciaSessao(int nif, String password) throws ExceptionErroLogIn{
        if(contrInd != null || empresa != null) throw new ExceptionErroLogIn("Ainda existe uma conta logged in. Por favor termine sessao.");
        ContribuinteIndividual contr = null;
        Empresas emp = null;
        if(individuaisHash.containsKey(nif)){
            contr = individuaisHash.get(nif);
            if(password.equals(contr.getPassword())) this.contrInd = contr;
            else{throw new ExceptionErroLogIn("Password Incorrecta");}
        }else{
            if(empresasHash.containsKey(nif)){
                emp = empresasHash.get(nif);
                if(password.equals(emp.getPassword())) this.empresa = emp;
                else{throw new ExceptionErroLogIn("Password Incorrecta");}
            } 
            else{throw new ExceptionErroLogIn("Nao existe uma conta registada com esse NIF.");}
        }
    }
    
    /** 
     * Verifica se alguma conta esta logged in.
     */
    public boolean checkAccount(){
        if(contrInd == null && empresa == null) return false;
        else return true;
    }
    
    /** 
     * Verifica se a conta que esta logged in é um contribuinte empresa.
     */
    public boolean checkEmpresa(){
        if(contrInd == null && empresa != null) return true;
        else return false;
    }
    /** 
     * Verifica se a conta que esta logged in é um contribuinte individual.
     */
    public boolean checkIndividual(){
        if(contrInd != null && empresa == null) return true;
        else return false;
    }
    
    /** 
     * Verifica se a conta que esta logged in é o administrador.
     */
    public boolean checkAdmin(){
        if(contrInd != null && this.getContrIndividual().getNIF() == 99999) return true;
        else return false;
    }
    
    /** 
     * Retorna o nome da Empresa que está logged in no Sistema.
     */
    public String getNameEmpresa(){
        return this.empresa.getNome();
    }
    
    /** 
     * Retorna o nome do Contribuinte Individual que está logged in no Sistema.
     */
    public String getNameIndividual(){
        return this.contrInd.getNome();
    }
    
    /** 
     * Metodo para fazer log out do sistema.
     */
    public void logOut(){
        contrInd = null;
        empresa = null;
    }
    
    /** 
     * Metodo que guarda o estado do sistema
     */
    public void gravarState(String file) throws IOException { 
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)); 
        oos.writeObject(this);
        
        oos.flush();
        oos.close();
    }
    
    /** 
     * Metodo que altera o estado do sistema, no ficheiro
     */
    public static FaturasData leObj(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
      
        FaturasData state = (FaturasData)ois.readObject();
        
        ois.close();
        return state;
    }
    
    /** 
     * Metodo que escreve em log.txt todas as alteraçoes ao estado do programa desde que ele é iniciado.
     * Por exemplo se registarmos uma fatura no programa.
     * Este metodo mostra a estrutura toda.
     */
    public void log(String file, boolean ap) throws IOException {
        FileWriter fileW = new FileWriter(file, ap);
        fileW.write("\n======================>LOG<======================\n");
        
        for (ContribuinteIndividual contrInd : individuaisHash.values()) {
            fileW.write("\n----Contribuinte Individual----\n");
            fileW.write(contrInd.toString());
            fileW.write("\n");
        }
        
        for (Empresas emp : empresasHash.values()) {
            fileW.write("\n----Empresa----\n");
            fileW.write(emp.toString());
            fileW.write("\n");
        }
        
        for (Despesa desp : despesas) {
            fileW.write("\n----Despesa----\n");
            fileW.write(desp.toString());
            fileW.write("\n");
        }
        
        fileW.write(this.toString());
        fileW.write("\n======================>LOG<======================\n");
        fileW.flush();
        fileW.close();
    }
}
