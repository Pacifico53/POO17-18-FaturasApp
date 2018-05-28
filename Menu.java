import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * Classe do menu, que vai receber as opçoes da faturaApp
 *
 *
 */
public class Menu {   
    private List<String> opcoes;
    private int op;
    
    /**
     * Construtor que cria o menu em si
     */
    public Menu(String[] opcoes) {
        this.opcoes = new ArrayList<String>();
        
        for (String option : opcoes){
            this.opcoes.add(option);
        }
        this.op = 1;
    }

    /**
     * Mostra o menu e permite que o utilizador escolha uma opcao
     * 
     */
    public void executa(String nome) {
        do {
            showMenu(nome);
            this.op = lerOpcao();
        } while (this.op == -1);
    }
    
    /** 
     * Metodo que imprime o menu e as opçoes
     */
    private void showMenu(String nome) {
        System.out.println("\n|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=| SISTEMA DE DESPESAS |=|=|=|=|=|=|=|=|=|=|=|=|=|=|=|=| ");
        if(nome != null)System.out.println("--> Sessão iniciada: " + "'" + nome + "'.");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" -> ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 -> Sair");
    }
    
    /** 
     * Metodo que recebe uma opçao do utilizador e verifica se esta se encontra dentro das opçoes validas 
     */
    private int lerOpcao() {
        int op; 
        Scanner is = new Scanner(System.in);
        
        System.out.print("Opção: ");
        try {
            op = is.nextInt();
        }
        catch (InputMismatchException e) {
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção inválida.");
            op = -1;
        }
        return op;
    }
    
    /**
     * Get da opcao escolhida pelo utilizador
     */
    public int getOpcao() {
        return this.op;
    }
}
