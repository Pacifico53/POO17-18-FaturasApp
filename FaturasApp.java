import java.io.IOException;
import static java.util.stream.Collectors.toMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;
import java.text.SimpleDateFormat; 
/**
 * Aqui se encontram os metodos para executar os requisitos do programa.
 *
 * 
 */
public class FaturasApp
{
    private static FaturasData fatura;
    private static Menu menuMain, menuRegistar, menuEmp, menuIndividual, menuAdmin;
    
    /**
     * Construtor vazio.
     */
    private FaturasApp() {}

    /**
     * Metodo que inicia/fecha a app e os menus e grava o estado do programa.
     */
    public static void main(String[] args) {
        initMenus();
        initApp();
        correMenus();
        try {
            fatura.gravarState("faturas.dados");
            fatura.log("log.txt", true);
        }
        catch (IOException e) {
            System.out.println("Erro ao gravar os dados!" + e);
        }
        System.out.println("Obrigado por usar o nosso programa!");     
    }
    
    /**
     * Metodo que mostra e executa os varios menus (tanto dos contribuintes individuais, como das empresas, como do administrador).
     */
    private static void initMenus(){
        String[] ops = {"Registar Conta",
                        "Iniciar Sessão"};
                        
        String [] opsEmp = {"Registar Nova Fatura",
                            "Mostrar Lista Ordenada de Despesas",
                            "Mostrar Lista de Despesas por Contribuinte num intervalo de tempo",
                            "Mostrar Lista de Despesas por Contribuinte ordenadas pelo valor decrescente da Despesa",
                            "Mostrar valor total facturado num intervalo de tempo",
                            "Terminar Sessão"};
                            
        String [] opsInd = {"Ver despesas e montante de deduçao fiscal",
                            "Alterar Classificaçao de uma Despesa",
                            "Terminar Sessão"};
                            
        String [] opsregista = {"Registar Conta Individual",
                                "Registar Conta Empresas"};
        
        String [] opsAdmin = {"Ver os 10 contribuintes que mais gastam",
                              "Ver as X empresas com mais faturas",
                              "Terminar Sessão"};
        menuMain = new Menu(ops);
        menuEmp = new Menu(opsEmp);
        menuIndividual = new Menu(opsInd);
        menuRegistar = new Menu(opsregista);
        menuAdmin = new Menu(opsAdmin);
    }
    
    /**
     * Metodo que executa as varias opçoes dos menus.
     */
    private static void correMenus(){
        do {
           if(fatura.checkEmpresa()){
             do {
                 menuEmp.executa(fatura.getNameEmpresa());
                 switch (menuEmp.getOpcao()) {
                  case 1: novaFatura();
                          break;
                  case 2: listagemDespesasOrdenadas();
                          break;
                  case 3: try{listaDespesasPorContribuinteData();}
                          catch(Exception e){System.out.println("Erro no formato das datas.\nDetalhes do erro: " + e);}
                          break;
                  case 4: listaDespesasPorContribuinteValor();
                          break;
                  case 5: try{totalFaturadoEmpresa();}
                          catch(Exception e){System.out.println("Erro no formato das datas.\nDetalhes do erro: " + e);}
                          break;
                  case 6: logOUT();
                          break;
                 }
             } while (menuEmp.getOpcao() != 0 && fatura.checkEmpresa());
           }
           if(menuEmp.getOpcao() == 0) break;
           
           
           if(fatura.checkIndividual() && !fatura.checkAdmin()){
              do {
                   menuIndividual.executa(fatura.getNameIndividual());
                   switch (menuIndividual.getOpcao()) {
                         case 1: verDespesasMontante();
                                 break;
                         case 2: mudarClassificacaoDespesa();
                                 break;
                         case 3: logOUT();
                                 break;
                   }
              } while (menuIndividual.getOpcao() != 0 && fatura.checkIndividual() && !fatura.checkAdmin());
           }
           if(menuIndividual.getOpcao() == 0) break;
           
           if(fatura.checkAdmin()){
              do {
                   menuAdmin.executa(fatura.getNameIndividual());
                   switch (menuAdmin.getOpcao()) {
                         case 1: admin10MaisGastam();
                                 break;
                         case 2: adminXEmpresas();
                                 break;
                         case 3: logOUT();
                                 break;
                   }
              } while (menuAdmin.getOpcao() != 0 && fatura.checkAdmin());
           }
           if(menuAdmin.getOpcao() == 0) break;
           
           
           
           if(!fatura.checkAccount()){
               do{
                   menuMain.executa(null);
                   switch (menuMain.getOpcao()) {
                       case 1: registaConta();
                           break;
                       case 2: iniciaSessao();
                           break;
                   }
               } while (menuMain.getOpcao() != 0 && !fatura.checkAccount());
           }
        } while (menuMain.getOpcao()!=0);
    }
    
    /**
     * Metodo que inicia a aplicaçao e retorna uma mensagem em caso de erro, dependendo do erro que ocorrer.
     */
    private static void initApp() {
        try {
             fatura = FaturasData.leObj("faturas.dados");
        }
        catch (IOException e) {
             fatura = new FaturasData();
             System.out.println("Não consegui ler os dados!\nErro de leitura." + e);
        }
        catch (ClassNotFoundException e) {
             fatura = new FaturasData();
             System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido." + e);
        }
        catch (ClassCastException e) {
             fatura = new FaturasData();
             System.out.println("Não consegui ler os dados!\nErro de formato." + e);        
        }
    }
    
    /**
     * Metodo que regista um utilizador no sistema.
     */
    private static void registaConta(){
        ContribuinteIndividual contrInd = null;
        Empresas emp = null;
        Scanner scan = new Scanner(System.in);
        
        menuRegistar.executa(null);
        if (menuRegistar.getOpcao() != 0){
            int i = 0;
            int nif;
            String email, nome, morada, password;
            
            System.out.println("Email: ");
            email = scan.nextLine();
            System.out.println("Nome: ");
            nome = scan.nextLine();
            System.out.println("Password: ");
            password = scan.nextLine();
            System.out.println("Morada: ");
            morada = scan.nextLine();
            System.out.println("NIF: ");
            nif = scan.nextInt();
            
            int numeroDependentes;
            ArrayList<Integer> numerosFiscais = new ArrayList<Integer>();
            int umNIF = 0;
            double coeficienteFiscal;
            int codigo = 0;
            ArrayList<Integer> codigosActividades = new ArrayList<Integer>(1);
            
            ArrayList<String> actividadesEconomicas = new ArrayList<String>(1);
            String actividade = "";
            double fatorDeducao;
            
            switch (menuRegistar.getOpcao()) {
                case 1: System.out.println("Numero de dependentes: ");
                        numeroDependentes = scan.nextInt();
                        
                        for(i = 0; i < numeroDependentes; i++){
                            System.out.println("NIF de dependente " + (i+1) + ":");
                            umNIF = scan.nextInt();
                            numerosFiscais.add(umNIF);
                        }
                        
                        System.out.println("Coeficiente Fiscal: ");
                        coeficienteFiscal = scan.nextDouble();
                        
                        System.out.println("Inserir codigos das Actividades Economicas.");
                        System.out.println("Existem os seguintes codigos:");
                        System.out.println("0 -> TRANSPORTES     1 -> CABELEIREIROS     2 -> ELECTRICIDADE     3 -> EDUCAÇAO");
                        System.out.println("4 -> GAS             5 -> SAUDE             6 -> AGUA              7 -> SERVIÇOS BANCARIOS");
                        System.out.println("8 -> RESTAURAÇAO     9 -> REPARAÇAO DE VEICULOS");
                        System.out.println("Insira qualquer outro numero para finalizar.");                        
                        for(i = 0; codigo >= 0 && codigo < 10; i++){
                            System.out.println("Codigo " + (i+1) + ":");
                            codigo = scan.nextInt();
                            if(codigo >= 0 && codigo < 10) codigosActividades.add(codigo);
                        }
                        
                        contrInd = new ContribuinteIndividual(nif, email, nome, morada, password, numeroDependentes, numerosFiscais, coeficienteFiscal, codigosActividades);
                        System.out.println("Done!");
                        break;
                        
                case 2: System.out.println("Inserir Actividades Economicas.");
                        System.out.println("Existem as seguintes actividades disponiveis:");
                        System.out.println("0 -> TRANSPORTES     1 -> CABELEIREIROS     2 -> ELECTRICIDADE     3 -> EDUCAÇAO");
                        System.out.println("4 -> GAS             5 -> SAUDE             6 -> AGUA              7 -> SERVIÇOS BANCARIOS");
                        System.out.println("8 -> RESTAURAÇAO     9 -> REPARAÇAO DE VEICULOS");
                        System.out.println("Insira qualquer outro numero para finalizar.");                        
                        for(i = 0; codigo >= 0 && codigo < 10; i++){
                            System.out.println("Codigo " + (i+1) + ":");
                            codigo = scan.nextInt();
                            switch(codigo){
                                case 0: actividade = "TRANSPORTES";
                                        break;
                                case 1: actividade = "CABELEIREIROS";
                                        break;
                                case 2: actividade = "ELECTRICIDADE";
                                        break;
                                case 3: actividade = "EDUCAÇAO";
                                        break;        
                                case 4: actividade = "GAS";
                                        break;        
                                case 5: actividade = "SAUDE";
                                        break;
                                case 6: actividade = "AGUA";
                                        break;
                                case 7: actividade = "SERVIÇOS_BANCARIOS";
                                        break;
                                case 8: actividade = "RESTAURAÇAO";
                                        break;
                                case 9: actividade = "REPARAÇAO_VEICULOS";
                                        break;
                            }
                            if(codigo >= 0 && codigo < 10)actividadesEconomicas.add(actividade);
                        }
                        
                        System.out.println("Fator de Deduçao: ");
                        fatorDeducao = scan.nextDouble();
                        emp = new Empresas(nif, email, nome, morada, password, actividadesEconomicas, fatorDeducao);
                        System.out.println("Done!");
                        break;
            }
            switch(menuRegistar.getOpcao()){
                    case 1: try{fatura.addIndividual(contrInd);
                            System.out.println("Adicionado Contribuinte Individual ao Sistema.");
                           }
                            catch(Exception e){System.out.println("\n"+e);}
                           break;
                    case 2: try{fatura.addEmpresa(emp);
                            System.out.println("Adicionada Empresa ao Sistema.");
                           }
                           catch(Exception e){System.out.println("\n"+e);}
                           break;
            }
        }
        else{
            System.out.println("Registo de conta cancelado.");
        }
        scan.close();
    }
    
    /**
     * Metodo que inicia a sessao de um utilizador.
     */
    private static void iniciaSessao(){
        Scanner scan = new Scanner(System.in);
        int nif;
        String password;
        
        System.out.println("NIF: ");
        nif = scan.nextInt();
        System.out.println("Password: ");
        password = scan.next();
        
        try{fatura.iniciaSessao(nif, password);}
        catch(Exception e){System.out.println("\n" + e);}
    }
    
    /**
     * Metodo que termina a sessao de um utilizador.
     */
    private static void logOUT(){
        fatura.logOut();
    }
    
    /**
     * Metodo que regista uma nova fatura no sistema.
     */
    private static void novaFatura(){
        Despesa desp = null;
        int nifEmitente = fatura.getEmpresa().getNIF();
        int nifCliente = 0;
        double valor = 0;
        int naturezaCode;
        String designacao, descricao;
        String natureza = "";
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Inserir NIF do Cliente: ");
        nifCliente = scan.nextInt();
        System.out.println("Inserir designaçao da despesa: ");
        designacao = scan.next();
        System.out.println("Inserir descriçao da despesa: ");
        descricao = scan.next();
        System.out.println("Inserir natureza economica da despesa: ");
        System.out.println("Existem as seguintes naturezas economicas disponiveis:");
        System.out.println("0 -> TRANSPORTES     1 -> CABELEIREIROS     2 -> ELECTRICIDADE     3 -> EDUCAÇAO");
        System.out.println("4 -> GAS             5 -> SAUDE             6 -> AGUA              7 -> SERVIÇOS BANCARIOS");
        System.out.println("8 -> RESTAURAÇAO     9 -> REPARAÇAO DE VEICULOS");
        naturezaCode = scan.nextInt();
        
        switch(naturezaCode){
            case 0: natureza = "TRANSPORTES";
                    break;
            case 1: natureza = "CABELEIREIROS";
                    break;
            case 2: natureza = "ELECTRICIDADE";
                    break;
            case 3: natureza = "EDUCAÇAO";
                    break;        
            case 4: natureza = "GAS";
                    break;        
            case 5: natureza = "SAUDE";
                    break;
            case 6: natureza = "AGUA";
                    break;
            case 7: natureza = "SERVIÇOS_BANCARIOS";
                    break;
            case 8: natureza = "RESTAURAÇAO";
                    break;
            case 9: natureza = "REPARAÇAO_VEICULOS";
                    break;
        }
        
        System.out.println("Inserir valor da despesa: ");
        valor = scan.nextDouble();
        Date dateDesp = new Date();
        System.out.println("Vai ser utilizada a data actual, ( " + dateDesp.toString() + " )");        
        desp = new Despesa(nifEmitente, designacao, dateDesp, nifCliente, descricao, natureza, valor);
        fatura.addDespesa(desp);
        System.out.println("Despesa adicionada com sucesso.");
    }
    
    /**
     * Metodo que verifica, por parte do contribuinte individual, as despesas que foram emitidas em seu nome
       e verifica o montante de dedução fiscal acumulado, por si e pelo agregado familiar.
     */
    private static void verDespesasMontante(){
        ContribuinteIndividual contr = fatura.getContrIndividual();       
        double valorTotal = 0;
        double valorAux = 0;
        ArrayList<Despesa> listaDesps = fatura.getListaDespesas();
        HashMap<Integer, ContribuinteIndividual> individuaisHash = fatura.getHashIndividuais();
        
        //Percorrer as despesas e procurar as despesas do contribuinte
        for(Despesa despesa : listaDesps){
            if (despesa.getNFCliente() == contr.getNIF()){
                //Este if esta aqui porque so queremos as despesas que tenham uma natureza que seja dedutivel ao contribuinte (uma das actividades economicas que o contribuinte escolheu no registo)
                if(contr.getCodigosActividades().contains(despesa.getCodigoNatureza())) valorTotal += despesa.getValor() * despesa.getFatorDedCodigo(despesa.getCodigoNatureza());
                System.out.println(despesa.toString());
            }
        }
        valorTotal = valorTotal * contr.getCoeficiente();
        System.out.println("\nValor deduzido = " + valorTotal + ".\n");
        
        //Se o contribuinte tiver contribuintes, aplica se este if, para calcular o valor deduzido desses contribuintes (apenas se estiverem registados no sistema)
        if(contr.getNumeroDependentes() > 0){
            ArrayList<Integer> listaNifs = contr.getNumerosFiscais();
            System.out.println("A verificar faturas de dependentes do agregado...");
            for(int umNif : listaNifs){
                valorAux = 0;
                System.out.println("A verificar faturas do contribuinte NIF = " + umNif);
                if(!fatura.getHashIndividuais().containsKey(umNif)){
                    System.out.println("NIF = " + umNif + " nao encontrado no sistema.");
                }else{
                    ContribuinteIndividual agregado = individuaisHash.get(umNif);
                    //Aqui vai se buscar os dados do contribuinte que faz parte do agregado do contribuinte original, e faz se o mesmo que se fez antes
                    for(Despesa despesa : listaDesps){
                        if (despesa.getNFCliente() == umNif){
                            if(agregado.getCodigosActividades().contains(despesa.getCodigoNatureza())) valorAux += despesa.getValor() * despesa.getFatorDedCodigo(despesa.getCodigoNatureza());
                            System.out.println(despesa.toString());
                        }
                    }
                    valorAux = valorAux * agregado.getCoeficiente();
                    System.out.println("\nValor deduzido do NIF " + umNif + " = " + valorAux + ".\n");
                }
            }
        }
    }
    
    /**
    * Metodo que muda a atividade economica de uma fatura.
    */
    
    private static void mudarClassificacaoDespesa(){
        ContribuinteIndividual contr = fatura.getContrIndividual();       
        ArrayList<Despesa> listaDesps = fatura.getListaDespesas();
        int op = 0;
        Scanner scan = new Scanner(System.in);
        
        //Aqui procura se despesas que pertençam ao contribuinte
        for(Despesa despesa : listaDesps){
            if (despesa.getNFCliente() == contr.getNIF()){
                System.out.println(despesa.toString());
                System.out.println("Pretende mudar a classificaçao desta fatura?\n1 -> Sim\n2 -> Nao");
                op = scan.nextInt();
                //Caso o contribuinte queira mudar a natureza da despesa, escolhe a opçao 1, e usa se um set para a modificar
                if(op == 1){
                    System.out.println("Inserir nova classificaçao da despesa: ");
                    System.out.println("Existem as seguintes naturezas economicas disponiveis:");
                    System.out.println("0 -> TRANSPORTES     1 -> CABELEIREIROS     2 -> ELECTRICIDADE     3 -> EDUCAÇAO");
                    System.out.println("4 -> GAS             5 -> SAUDE             6 -> AGUA              7 -> SERVIÇOS BANCARIOS");
                    System.out.println("8 -> RESTAURAÇAO     9 -> REPARAÇAO DE VEICULOS");
                    op = scan.nextInt();
                    switch(op){
                        case 0: despesa.setNaturezaDespesa("TRANSPORTES");
                                break;
                        case 1: despesa.setNaturezaDespesa("CABELEIREIROS");
                                break;        
                        case 2: despesa.setNaturezaDespesa("ELECTRICIDADE");
                                break;
                        case 3: despesa.setNaturezaDespesa("EDUCAÇAO");
                                break;  
                        case 4: despesa.setNaturezaDespesa("GAS");
                                break;
                        case 5: despesa.setNaturezaDespesa("SAUDE");
                                break;        
                        case 6: despesa.setNaturezaDespesa("AGUA");
                                break;
                        case 7: despesa.setNaturezaDespesa("SERVIÇOS_BANCARIOS");
                                break;
                        case 8: despesa.setNaturezaDespesa("RESTAURAÇAO");
                                break;
                        case 9: despesa.setNaturezaDespesa("REPARAÇAO_DE_VEICULOS");
                                break;
                    }
                }
            }
        }
    }  
   
    
    /**
    * Metodo que imprime uma lista das faturas de uma empresa ordenadas por valor ou por data.
    */
   private static void listagemDespesasOrdenadas(){
        Empresas emp = fatura.getEmpresa();       
        ArrayList<Despesa> listaDesps = fatura.getListaDespesas();
        ArrayList<Despesa> listaDespsEmpresa = new ArrayList<>();
        int op = 0;
        Scanner scan = new Scanner(System.in);  
       
        // Aqui enche se uma ArrayList com as despesas apenas da empresa
        for(Despesa despesa : listaDesps){
            if (despesa.getNFEmitente() == emp.getNIF()){
                listaDespsEmpresa.add(despesa);
            }
        }
       
        System.out.println("Pretende ordenar as despesas por data, ou por valor?\n1 -> Data\n2 -> Valor");
        op = scan.nextInt();
       
        //Usamos classes de comparaçao feitas por nos para comparar as despesas e ordena las da maneira que queremos
        if(op == 1){
            Collections.sort(listaDespsEmpresa, new ComparatorDataDespesa());
         }
         else{
         if(op == 2){
            Collections.sort(listaDespsEmpresa, new ComparatorValorDespesa());
         }
        }
       
        if(op == 1 || op == 2){
            for(Despesa despesa : listaDespsEmpresa){
                System.out.println(despesa.toString());
             }
        } else{System.out.println("Operaçao cancelada.");}
    }
   
    /**
    * Metodo que imprime a lista de faturas de uma empresa (por contribuinte) num determinado intervalo de tempo.
    */
   private static void listaDespesasPorContribuinteData() throws Exception{
        Empresas emp = fatura.getEmpresa();
        String beginS = "";
        String endS = "";
        Date begin = null;
        Date end = null;
        ArrayList<Despesa> listaDesps = fatura.getListaDespesas();
        ArrayList<Despesa> listaDespsEmpresa = new ArrayList<>();
        ArrayList<Despesa> listaData = new ArrayList<>();
        ArrayList<Integer> listaNifs = new ArrayList<>();
        int nif = 0;
        int op = 0;
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Indique a data inicial (DD/MM/YYYY):");
        beginS = scan.next();
        begin = new SimpleDateFormat("dd/MM/yyyy").parse(beginS);  
       
        System.out.println("Indique a data final (DD/MM/YYYY):");
        endS = scan.next();
        end = new SimpleDateFormat("dd/MM/yyyy").parse(endS);
              
        //Aqui obtemos uma ArrayList com as despesas da empresa logged in
        for(Despesa despesa : listaDesps){
            if (despesa.getNFEmitente() == emp.getNIF()){
                listaDespsEmpresa.add(despesa);
            }
        }
       
        //E aqui vamos buscar apenas as despesas que estao no intervalo de tempo dado
        for(Despesa despesa : listaDespsEmpresa){
            if (despesa.getDataDespesa().after(begin) && despesa.getDataDespesa().before(end)){
                listaData.add(despesa);
            }
        }
        
        //No fim de cada loop, adiciona se o nif a um ArrayList, para que nao se repitam prints do mesmo contribuinte (depois de ja ter mostrado todos dele)
        for(Despesa despesa : listaData){
            nif = despesa.getNFCliente();
            if(!listaNifs.contains(nif)) System.out.println("\n---->Despesas do NIF Cliente " + nif + " : ");
           
            for(Despesa despesa2 : listaData){
                if(despesa2.getNFCliente() == nif && !listaNifs.contains(nif)){
                    System.out.println(despesa2.toString());
                 }
            }
           
            listaNifs.add(nif);
        }
    }
    
    /**
    * Metodo que imprime uma lista ordenada das faturas de uma empresa (por contribinte) por valor, em ordem decrescente.
    */
   private static void listaDespesasPorContribuinteValor(){
        Empresas emp = fatura.getEmpresa();
        ArrayList<Despesa> listaDesps = fatura.getListaDespesas();
        ArrayList<Despesa> listaDespsEmpresa = new ArrayList<>();
        ArrayList<Integer> listaNifs = new ArrayList<>();
        int nif = 0;
        int op = 0;
        Scanner scan = new Scanner(System.in);
        
        for(Despesa despesa : listaDesps){
            if (despesa.getNFEmitente() == emp.getNIF()){
                listaDespsEmpresa.add(despesa);
            }
        }
        
        //Aqui ordena se a lista da despesas da empresa, pelo valor
        Collections.sort(listaDespsEmpresa, new ComparatorValorDespesa());
        //E aqui faz se reverse da lista, para termos em ordem decrescente de valor
        Collections.reverse(listaDespsEmpresa);
        
        //Este for usa a mesma abordagem que o da funçao anterior
        for(Despesa despesa : listaDespsEmpresa){
            nif = despesa.getNFCliente();
            if(!listaNifs.contains(nif)) System.out.println("\n---->Despesas do NIF Cliente " + nif + " : ");
           
            for(Despesa despesa2 : listaDespsEmpresa){
                if(despesa2.getNFCliente() == nif && !listaNifs.contains(nif)){
                    System.out.println(despesa2.toString());
                 }
            }
           
            listaNifs.add(nif);
        }
    }
    
    /**
    * Metodo que imprime o total faturado por uma empresa num determinado intervalo de tempo.
    */
   private static void totalFaturadoEmpresa() throws Exception{
        Empresas emp = fatura.getEmpresa();
        String beginS = "";
        String endS = "";
        Date begin = null;
        Date end = null;
        ArrayList<Despesa> listaDesps = fatura.getListaDespesas();
        ArrayList<Despesa> listaDespsEmpresa = new ArrayList<>();
        ArrayList<Despesa> listaData = new ArrayList<>();
        int valorTotal = 0;
        int op = 0;
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Indique a data inicial (DD/MM/YYYY):");
        beginS = scan.next();
        begin = new SimpleDateFormat("dd/MM/yyyy").parse(beginS);  
       
        System.out.println("Indique a data final (DD/MM/YYYY):");
        endS = scan.next();
        end = new SimpleDateFormat("dd/MM/yyyy").parse(endS);
        
        //Obtemos uma lista das despesas da empresa, no intervalo de tempo dado
        for(Despesa despesa : listaDesps){
            if (despesa.getNFEmitente() == emp.getNIF()){
                listaDespsEmpresa.add(despesa);
            }
        }
        
        for(Despesa despesa : listaDespsEmpresa){
            if (despesa.getDataDespesa().after(begin) && despesa.getDataDespesa().before(end)){
                listaData.add(despesa);
            }
        }
        
        //Aqui vamos somando o valor total de cada despesa, para obter o valor total
        for(Despesa despesa : listaData){
            valorTotal += despesa.getValor();
        }
        
        System.out.println("Valor total faturado no intervalo de tempo dado: " + valorTotal + " .");
    }
    
    /**
    * Metodo que imprime os 10 contribuintes que mais gastam mais no sistema.
    */
   private static void admin10MaisGastam(){
        HashMap<Integer, ContribuinteIndividual> individuaisHash = fatura.getHashIndividuais();
        ArrayList<Despesa> listaDesp = fatura.getListaDespesas();
        ArrayList<ContribuinteIndividual> lista10 = new ArrayList<>();
        //Aqui usamos uma treemap, porque queremos uma maneira de termos os contribuintes ordenados pelos gastos, usa se Collections.reverseOrder para ter uma ordem inversa (maiores gastos no topo))
        TreeMap<Integer, ContribuinteIndividual> contrEGastos = new TreeMap<Integer, ContribuinteIndividual>(Collections.reverseOrder());
        int gastos = 0;
        int i = 0;
        
        //Aqui enche se a TreeMap com os contribuintes, e como key usamos os seus gastos
        for(ContribuinteIndividual contr : individuaisHash.values()){
            gastos = 0;
            for(Despesa desp : listaDesp){
                if(desp.getNFCliente() == contr.getNIF()){
                    gastos += desp.getValor();
                }
            }
            contrEGastos.put(gastos, contr);
        }
        
        //Aqui itera se pela tree, como é ordenada pelos gastos, basta tirar os primeiros 10 contribuintes
        for(Map.Entry<Integer, ContribuinteIndividual> entry : contrEGastos.entrySet()){
            ContribuinteIndividual contr = entry.getValue();
            if(i < 10) {
                System.out.println(contr.toString());
                System.out.println("===>Gastos = " + entry.getKey() + " .");
            }
            i++;
        }
    }
    
    /**
    * Metodo que imprime as X empresas mais faturadas no sistema e o respetivo montante de deduçoes fiscais de cada empresa.
    */
   private static void adminXEmpresas(){
        HashMap<Integer, Empresas> empresasHash = fatura.getHashEmpresas();
        ArrayList<Despesa> listaDesp = fatura.getListaDespesas();
        ArrayList<ContribuinteIndividual> listaX = new ArrayList<>();
        //Aqui usa se uma abordagem parecida com a funçao anterior, mas tambem se usa uma hashmap para guardar os montantes, usando como key o numero de faturas
        TreeMap<Integer, Empresas> empEDesp = new TreeMap<Integer, Empresas>(Collections.reverseOrder());
        HashMap<Integer, Double> listaDeducaoFiscal = new HashMap<Integer, Double>();
        int nFaturas = 0;
        int i = 0;
        int x = 0;
        double deducaoFiscal = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Limite de empresas a mostrar: ");
        x = scan.nextInt();
        
        //Enche se a TreeMap com as empresas, e o numero de faturas, e enche se a hashmap com a deducaoFiscal calculada, e tambem usando o numero de faturas como key
        for(Empresas emp : empresasHash.values()){
            nFaturas = 0;
            for(Despesa desp : listaDesp){
                if(desp.getNFEmitente() == emp.getNIF()){
                    nFaturas++;
                    deducaoFiscal = desp.getValor() * emp.getFatorDeducao();
                }
            }
            listaDeducaoFiscal.put(nFaturas, deducaoFiscal);
            empEDesp.put(nFaturas, emp);
        }
        
        
        for(Map.Entry<Integer, Empresas> entry : empEDesp.entrySet()){
            Empresas contr = entry.getValue();
            if(i < x) {
                System.out.println(contr.toString());
                System.out.println("===>Numero de Faturas = " + entry.getKey() + " .");
                System.out.println("===>Montante de deduçoes fiscais = " + listaDeducaoFiscal.get(entry.getKey()));
            }
            i++;
        }
    }
}
