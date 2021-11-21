// import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Ex: usuario logar daniel; alimento adicionar arroz 40; usuario remover daniel

public class InterfaceCLI {

    // É, eu sei que seria melhor criar uma classe num outro arquivo só pra interpretar os comandos
    // É minha primeira vez fazendo algo assim e percebi isso muito tarde, agora não tem tempo.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String NEGRITO = "\033[0;1m";
    public static final String ESTL = "\t\033[0;1m*\033[0;0m ";
    public static final String NORMAL = "\033[0;0m";
    public static final String SUBL = "\033[0;4m";
    public static final String BIGL = "⊢ ";
    public static final String SETA = "❯ ";



    public static final ArquivoOps aq = new ArquivoOps();
    public static final StringBuilder sb = new StringBuilder(50);

    public static final Config ctcliConfig = new Config(Main.CTCLICONFIG);
    

    // public static final Alimento ali = new Alimento();

    void mostrar() {
        // Caracteres especiais ANSI para fazer o texto ficar em negrito
        System.out.println("\033[0;1m");
        // mensagem de boas vindas + explicação do app
        System.out.println("ctcli"+" v"+Main.VERSAO);

        String usr = ctcliConfig.getPermaLoginUsr();
        if(!(usr.equals("") || usr.equals(null))) {
            if(Usuario.usuarioExiste(usr)) {
                MenuPrincipal mp = new MenuPrincipal();
                System.out.println("\nLogado automaticamente como "+usr);
                System.out.println("\nPara desabilitar o login automático, digite: permalogin 0");
                System.out.println(ANSI_GREEN+"\nDigite \"ajuda\" para obter os comandos disponíveis."+ANSI_RESET);
                mp.entradaAlimentos(usr);
            } else {
                System.out.println(ANSI_RED+"\nERRO: Usuário especificado em $permalogin não existe (ctcli.config)"+ANSI_RESET);
            }
        }

        System.out.println("\n"+NEGRITO+"Entre como um usuário para obter acesso aos demais comandos.");
        System.out.println("Use: usuario logar [nome do usuário]");

        System.out.println("\n"+ANSI_GREEN+"Digite \"ajuda\" para obter todos os comandos disponíveis"+ANSI_RESET);



        // CLIUtil.waitNext();
        
        // Criando instância da subclasse MenuPrincipal
        // executando o método sobrescrito mostrar()
        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();
    }

    // Printa a explicação sobre o nível de atividade física
    void expNivelAtv() {
        System.out.println("\nEXPLICAÇÃO SOBRE COMO O CÁLCULO DO TDEE É FEITO");
        System.out.println("\nNíveis de atividade física:\n1. Sedentário\n2. Exercício leve");
        System.out.println("3. Exercício moderado\n4. Exercício intenso\n5. Exercício muito intenso (Atleta)");
        System.out.print("\nDigite o nível de atividade física do usuário ");
    }

    // Pede ao usuário seu nível de atividade física
    String getNivelAtv() {
        String resp = CLIUtil.getUserInput();
        // Pedir pra tentar novamente se a resposta não tiver apenas 
        // um número ou ser algum outro caractere
        if(resp.length() != 1 || !(resp.matches(".*\\d.*"))) {
            System.out.println("Nível de atividade inválido. Tente novamente.");
            getNivelAtv();
        // também pede pra tentar novamente se a resposta não estiver entre 1 e 4.
        } else if (Integer.parseInt(resp) < 1 || Integer.parseInt(resp) > 5) {
            System.out.println("Nível de atividade inválido. Tente novamente.");
            getNivelAtv();

        } else {
            return resp;
        }
        return resp;
 
    }

    public void mostrarComandosUsuario() {
        CLIUtil.clear();
        // TODO: pode estar desatualizado, verificar
        // System.out.println("\t\t"+NEGRITO+"Comandos"+NORMAL+"\n");       
        // Checa se é a primeira vez que o programa está sendo executado
        System.out.println(NEGRITO+"Comandos de Usuário"+NORMAL);
        System.out.println("Para usar os comandos de usuário, use como prefixo \"usuario\" ou o seu atalho \"u\"");
        System.out.println("Ex: usuario remover [usuario]\n");

        System.out.println("\t\"adicionar\" [nome] [peso (kg)] [altura (cm)] [idade]\t\tAtalho: \"a\"");
        System.out.println("\tDescrição: adiciona um usuário à base de dados.\n");

        // System.out.println("remusuario [nome]");

        System.out.println("\t\"editar\" [nome] [propriedade a ser alterada] [novo valor]\tAtalho: \"e\"");
        System.out.println("\tPropriedades válidas: nome, peso, altura, idade, nivelatv");
        System.out.println("\tDescrição: edita uma propriedade do usuário dado como argumento.\n");

        System.out.println("\t\"remover\" [nome]\t\t\t\t\t\tAtalho: \"r\"");
        System.out.println("\tDescrição: remove o usuário dado como argumento.\n");

        System.out.println("\t\"print\" [nome]\t\t\t\t\t\t\tAtalho: \"p\"");
        System.out.println("\tDescrição: printa o usuário dado como argumento.\n");

        System.out.println("\t\"printall\"\t\t\t\t\t\t\tAtalho: \"pa\"");
        System.out.println("\tDescrição: printa todos os usuários salvos na base de dados.\n");

        System.out.println("\t\"logar\" [nome]\t\t\t\t\t\t\tAtalho: \"l\"");
        System.out.println("\tDescrição: loga como o usuário dado como argumento.\n");




        System.out.println(NEGRITO+"Comandos Globais"+NORMAL);
        System.out.println("Esses comandos não necessitam de um prefixo.\n");

        System.out.println("\t\"sair\"\t\t\t\t\t\t\t\tAtalho: \"s\"");
        System.out.println("\tDescrição: sai do programa.\n");

        System.out.println("\t\"clear\"\t\t\t\t\t\t\t\tAtalho: \"c\"");
        System.out.println("\tDescrição: limpa a tela do terminal (se for possível).\n");


        System.out.println("\n"+ANSI_GREEN+"Entre como um usuário para obter acesso aos demais comandos."+ANSI_RESET);
        System.out.println("Use: usuario logar [nome do usuário]");

        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();

    }
    

    void mostrarComandosUsuarioAdicionais() {
        System.out.println(NEGRITO+"Comandos Adicionais"+NORMAL);
        System.out.println("\n\tusuario: \"limparcsv\"\t\t\t\t\t\tAtalho: \"lcsv\"");
        System.out.println("\tDescrição: limpa o arquivo CSV do usuário, só deixa o cabeçalho.");

        
        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();

    }

    void mostrarComandosAlimentos() {
        CLIUtil.clear();
        System.out.println(NEGRITO+"Comandos do Submenu Pessoal"+NORMAL);
        System.out.println(NEGRITO+"\nComandos Referentes aos Alimentos\n"+NORMAL);
        System.out.println("Para usar os comandos de alimentos, use como prefixo \"alimento\" ou o seu atalho \"a\"");
        System.out.println("Ex: alimento remover [nome], que é a mesma coisa que \"a r [nome]\"\n");
        System.out.println("\t\"adicionar\" [nome] [kcal/100g]\t\t\t\t\tAtalho: \"a\"");
        System.out.println("\tDescrição: Adiciona um alimento à base de dados."+
        "\n\tKcal/100g são quantas calorias tem 100g desse alimento.\n");

        System.out.println("\t\"print\" [nome]\tAtalho: \"p\"");
        System.out.println("\tDescrição: edita uma propriedade do usuário dado como argumento.\n");

        System.out.println("\t\"remover\" [nome]\t\t\t\t\t\tAtalho: \"r\"");
        System.out.println("\tDescrição: remove o alimento dado como argumento do banco de dados.\n");

        System.out.println("\t\"alterar\" [nome] [propriedade a ser alterada] [novo valor]\tAtalho: \"alt\"");
        System.out.println("\tPropriedades válidas: nome, kcal");
        System.out.println("\tDescrição: alterar uma propriedade do alimento dado como argumento.\n");


        System.out.println("\t\"print\" [nome]\t\t\t\t\t\t\tAtalho: \"p\"");
        System.out.println("\tDescrição: printa os dados do alimento dado como argumento.\n");

        System.out.println("\t\"printpretty\" [nome]\t\t\t\t\t\tAtalho: \"pp\"");
        System.out.println("\tDescrição: printa os dados do alimento dado como argumento, de forma mais bonitinha.\n");

        System.out.println("\t\"printall\" [nome]\t\t\t\t\t\tAtalho: \"pa\"");
        System.out.println("\tDescrição: printa todos os alimentos.\n");
        

    }

    class MenuPrincipal extends InterfaceCLI {

        // Método que cuida da entrada e sanitização de dados do usuário
        void entradaUsuario() {
            // ex: peso, altura, nivelatv só devem conter números
            System.out.println();
            String[] cmd = new String[15];
            String cmdStr = "";
            while(cmdStr.isEmpty()) {
                cmdStr = CLIUtil.getUserInput();
            }

            // Interpreta corretamente o comando se só tiver um argumento
            try {
                // System.out.println(cmdStr);
                cmd = cmdStr.split(" ");

            } catch (NullPointerException e) {
                // System.out.println("caught");
                cmd[0] = cmdStr;
                System.out.println(cmdStr);

            }

            String cmdPrinc = cmd[0];
            String cmdSec;
            try {
                cmdSec = cmd[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                cmdSec = null;
            }
            
            // Comandos que o usuário pode usar
            // sintaxe: [categoria de comando] [comando] [parametros...]
            // ex: usuario logar daniel
            // ex2: usuario editar daniel peso 80
            
            if(cmdPrinc.matches("usuario") || cmdPrinc.matches("u")) {
                // adiciona um novo usuário ao csv
                if(cmdSec.matches("adicionar") || cmdSec.matches("a")) {


                    if(cmd.length != 7) {
                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                        entradaUsuario();
                    }
                    
                    // copia só os dados que usuário entrou, sem os comandos
                    String[] dados = Arrays.copyOfRange(cmd, 2, cmd.length);
                    System.out.println(Arrays.toString(dados));

                    int validar = Usuario.validarDadosUsuario(dados);

                    switch(validar) {
                        case 1:
                            System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                            System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                            entradaUsuario();

                        case 2:
                            System.out.println("Valor do argumento [peso] inválido. Tente novamente.");
                            System.out.println("Valor tem que ser número, ter pelo menos dois dígitos, e ser menor que 500.");
                            System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                            entradaUsuario();

                        case 3:
                            System.out.println("Valor do argumento [altura] inválido. Tente novamente.");
                            System.out.println("Valor tem que ser número, ter pelo menos dois dígitos, e ser menor que 300.");
                            System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                            entradaUsuario();

                        case 4:
                            System.out.println("Valor do argumento [idade] inválido. Tente novamente.");
                            System.out.println("Valor tem que ser número, ser maior que 10, e ser menor que 110.");
                            System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                            entradaUsuario();

                        case 5:
                            System.out.println("Valor do argumento [sexo] inválido. Tente novamente.");
                        System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                        entradaUsuario();
                        
                    }

                    expNivelAtv();
                    String resp = getNivelAtv();
                    Usuario usuario = new Usuario(cmd[2],cmd[3],cmd[4],cmd[5],cmd[6],resp);
                    if(usuario.criarUsuario()) {
                        System.out.println("Usuário criado com sucesso.");
                        System.out.println("\nEntre como um usuário para obter acesso aos demais comandos!");
                        System.out.println("Use: usuario logar [nome do usuário]");
                    } else {
                        System.out.println("Usuário não criado. Tente novamente.");
                        System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade] [sexo]");
                    }
                    entradaUsuario();
                    
                // remove um usuário do csv, deleta seu diário
                } else if(cmdSec.matches("remover") || cmdSec.matches("r")) {
                    // esse if de checagem de comprimento de cmd[] pode funcionar melhor na linha acima
                    if(cmd.length != 3) {
                        System.out.println("Quantidade de argumentos inválida. Tente novamente.");
                        System.out.println("Uso: usuario remover [nome]");
                        entradaUsuario();
                    }
                    Usuario u = new Usuario(cmd[2]);
                    if(u.removerUsuario()) {
                        System.out.println("Usuário removido");
                        entradaUsuario();
                    } else {
                        System.out.println("Usuário não removido"+
                        " pois não foi encontrado.");
                        System.out.println("Uso: usuario remover [nome]");
                        entradaUsuario();
                    }
                    // System.out.println("Argumento aceito");
                    entradaUsuario();
                
                // edita o dado especificado
                } else if(cmdSec.matches("editar") || cmdSec.matches("e")) {
                    if(cmd.length < 5) {
                        System.out.println("Quantidade de argumentos insuficiente. Tente novamente.");
                        System.out.println("Uso: usuario editar [nome] [propriedade] [novo valor]");

                        entradaUsuario();

                    } else if(cmd.length > 5) {
                        System.out.println("Quantidade de argumentos excedida para esse comando. Tente novamente");
                        System.out.println("Uso: usuario editar [nome] [propriedade] [novo valor]");
                        entradaUsuario();

                    } 
                    // System.out.println("+"+cmd[2]+"+");
                    // TODO: refatorar esse bagulho, mt espaguete
                    if(!(cmd[3].matches("peso") 
                    || cmd[3].matches("altura") 
                    || cmd[3].matches("nome") 
                    || cmd[3].matches("nivelatv") 
                    || cmd[3].matches("idade") 
                    || cmd[3].matches("sexo"))) {
                        System.out.println("Propriedade inválida.");
                        System.out.println("Uso: usuario editar [nome] [propriedade] [novo valor]");
                        System.out.println("Propriedades válidas: nome, peso, altura, idade, sexo, nivelatv");
                        entradaUsuario();
                    }

                    if(!(Usuario.validarDadosUsuario(cmd[3], cmd[4]))) {
                        System.out.println("ERRO: bla bla bla");
                        entradaUsuario();
                    }
                    
                    Usuario u2 = new Usuario(cmd[2]);
                    u2.alterarDados(cmd[3], cmd[4]);
                    System.out.println("Usuário editado com sucesso.");
                    
                    entradaUsuario();
                
                // printa os dados de todos os usuários no csv
                } else if(cmdSec.matches("printall") || cmdSec.matches("pa")) {
                    Usuario.printUsuarios();
                    entradaUsuario();
                
                // printa os dados do usuário especificado
                } else if(cmdSec.matches("print") || cmdSec.matches("p")) {

                    if(cmd.length != 3) {
                        System.out.println("Argumentos inválidos/insuficientes.");
                            entradaUsuario();
                        } else {
                            Usuario.printDadosUsuario(cmd[2]);
                            entradaUsuario();
                        }
                        entradaUsuario();
                
                // limpa csv + cabeçalho do CSV com os dados dos usuários, se o arquivo estiver vazio
                } else if(cmdSec.matches("limparcsv") || cmdSec.matches("lcsv")) {
                    aq.escreverAoCSV(Main.CSVUSUARIO, null);
                    aq.criarCSVeMontarCabecalho(Main.CSVUSUARIO);
                    System.out.println("CSV limpo.");
                    entradaUsuario();

                // "loga" o usuário no app
                } else if(cmdSec.matches("logar") || cmdSec.matches("l")) {
                    if(cmd.length != 3) {
                        System.out.println("Campo de usuário em branco. Digite um usuário para logar");
                        System.out.println("Comando: logar [usuario]");
                        entradaUsuario();

                    } else if(!(Usuario.usuarioExiste(cmd[2]))) {
                        System.out.println("Usuário não encontrado. Tente novamente");
                        entradaUsuario();

                    } else {
                        CLIUtil.clear();
                        System.out.println("Logado com sucesso.\n");
                        System.out.println("Você está no seu submenu pessoal.");
                        System.out.println(ANSI_GREEN+"\nDigite \"ajuda\" para obter os comandos disponíveis neste submenu.\n"+ANSI_RESET);
                        // System.out.println();
                        System.out.println(ANSI_GREEN+"Digite \"permalogin 1\" para habilitar login automático para esse usuário."+ANSI_RESET);


                        entradaAlimentos(cmd[2]);
                        entradaUsuario();
                    }
                }
                
            
                
                else {System.out.println("Comando inválido."); entradaUsuario();}

            /* COMANDOS PRINCIPAIS */
            } else if(cmdPrinc.matches("sair") || cmdPrinc.matches("s")) {
                CLIUtil.clear();
                System.exit(0);
                
            } else if(cmdPrinc.matches("clear") || cmdPrinc.matches("c")) {
                CLIUtil.clear();
                entradaUsuario();

            } else if(cmdPrinc.matches("ajuda")) {
                mostrarComandosUsuario();
                entradaUsuario();

            } else if(cmdPrinc.matches("adc")) {
                mostrarComandosUsuarioAdicionais();
                entradaUsuario();
            }

            else {System.out.println("entradaUsuario: Comando inválido."); entradaUsuario();}

        }

        void entradaAlimentos(String usuario) {
            sb.setLength(0);
            // System.out.println("werks");

            // ex: peso, altura, nivelatv só devem conter números
            System.out.println();
            String[] cmd = new String[10];
            String cmdStr = "";
            while(cmdStr.isEmpty()) {
                System.out.print(usuario+" ");
                cmdStr = CLIUtil.getUserInput();
            }

            // Interpreta corretamente o comando se só tiver um argumento
            try {
                // System.out.println(cmdStr);
                cmd = cmdStr.split(" ");

            } catch (NullPointerException e) {
                // System.out.println("caught");
                cmd[0] = cmdStr;
                System.out.println(cmdStr);

            }

            String cmdPrinc = cmd[0];
            String cmdSec;
            try {
                cmdSec = cmd[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                cmdSec = "";
            }

            // TODO: comandos de logar no CSV pessoal

            if(cmdPrinc.matches("voltar")) {
                entradaUsuario();
            } else if(cmdPrinc.matches("sair") || cmdPrinc.matches("s")) {
                CLIUtil.clear();
                System.exit(0);
            } else if(cmdPrinc.matches("clear") || cmdPrinc.matches("c")) {
                CLIUtil.clear();
            } else if(cmdPrinc.matches("ajuda")) {
                mostrarComandosAlimentos();

            } else if(cmdPrinc.matches("permalogin")) {
                if(cmd.length<2) {
                    System.out.println("Argumentos insuficientes.");
                    System.out.println("Uso: permalogin [0 ou 1]");
                } else if(cmd.length>2) {
                    System.out.println("Quantidade de argumentos excedida.");
                    System.out.println("Uso: permalogin [0 ou 1]");
                } else if(cmdSec.matches("1")) {
                    if(ctcliConfig.getPermaLoginUsr().equals(usuario)) {
                        System.out.println("Permalogin já está ativado para esse usuário.");
                    }
                    if(ctcliConfig.addPermaLoginUsr(usuario)) {
                        System.out.println("Permalogin habilitado para o usuário "+usuario);
                    } else {
                        System.out.println("ERRO: Permalogin não habilitado.");
                        System.out.println("Uso: permalogin [0 ou 1]");
                    }
                } else if(cmdSec.matches("0")) {
                    if(ctcliConfig.getPermaLoginUsr().equals(usuario)) {
                        if(ctcliConfig.addPermaLoginUsr("")) {
                            System.out.println("Permalogin desativado para o usuário "+usuario);
                        } else {
                            System.out.println("Permalogin não desativado por algum motivo.");
                        }
                    } else {
                        System.out.println("Permalogin não está habilitado para esse usuário.");
                    }

                } else {
                    System.out.println("Argumentos inválidos.");
                    System.out.println("Uso: permalogin [0 ou 1]");

                }
                entradaAlimentos(usuario);


            // comandos de diário
            } else if(cmdPrinc.matches("diario") || cmdPrinc.matches("d")) {

                if(cmdSec.matches("adicionar") || cmdSec.matches("a")) {
                    if(cmd.length < 4) {

                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                        entradaAlimentos(usuario);

                    // detecta se o usuário errou a ordem dos argumentos
                   } else if(cmd[2].matches("[0-9]+")) {
                       System.out.println("O nome do alimento vem antes das calorias.");
                       System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                       entradaAlimentos(usuario);
                   }

                    // c contém o número de partes do comando que tem letras
                    // ATENÇÃO: Também conta o comando em si (diario adicionar)
                    int c = 0;
                    // localização do número de gramas em cmd[]
                    int temNum = 0;
                    // Controle: usado pra checar se o usuário digitou mais de um argumento [kcal]
                    int temNumQ = 0;

                    for(int i=0;i<cmd.length;i++) {
                        // verdadeiro se cmd[i] conter SOMENTE letras
                        // Fix: só adicionar a c se ainda não foi achado nenhum número
                        if(cmd[i].matches("[a-zA-Z]+") && temNum == 0) {
                            c++;
                        }
                        
                        // verdadeiro se cmd[i] conter SOMENTE números
                        if(cmd[i].matches("[0-9]+")) {
                            if(temNumQ == 0) {
                                temNum = i;
                            }
                            temNumQ++;
                        }

                    }

                    // System.out.println(cmd[temNum-1]);
                    
                    // checa se o usuário realmente adicionou as calorias antes de continuar
                    if(temNum == 0) {
                        System.out.println("\nERRO: valor [calorias consumidas] não encontrado.");
                        System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                        entradaAlimentos(usuario);

                    }
                    // checa se o nome do alimento tem muitos espaços
                    if(c > 5) {
                        System.out.println("\nNome do alimento muito grande. (Muitos espaços)");
                        System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                        entradaAlimentos(usuario);

                    }

                    if(c == 2) {
                        System.out.println("\nNome só pode conter letras e espaços. Tente novamente.");
                        System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                        entradaAlimentos(usuario);

                    }

                    if(cmd[temNum].length() > 4 || Integer.parseInt(cmd[temNum]) == 0) {
                        System.out.println("\nERRO: valor [quantidade consumida (g)] inválido.");
                        System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                        entradaAlimentos(usuario);
                        
                    }

                    // debug
                    //System.out.println("num array position: "+temNum);
                    // System.out.println(c);

                    // Objeto StringBuilder pra juntar as partes do comando que são o nome do alimento
                    // StringBuilder sb = new StringBuilder(50);
                    for(int i=2;i<c;i++) {
                        // Se o nome do alimento for muito grande, printar erro.
                        if(cmd[i].length() > 10) {
                            System.out.println("\nNome do alimento muito grande!");
                            System.out.println("Uso: alimento adicionar [nome] [calorias consumidas] [notas (opcional)]");
                            entradaAlimentos(usuario);
                        }
                        sb.append(" "+cmd[i]);

                    }
                    // no CSV, os espaços serão substituidos por underlines
                    // System.out.println(sb.toString().trim().replace(" ","_"));
                    String validA = sb.toString().trim().replace(" ","_");

                    // stringbuilder que vai armazenar a nota
                    StringBuilder nom = new StringBuilder(50);
                    int diff = cmd.length - temNum;
                    if(diff > 6) {
                        System.out.println("erro nota mt grande");
                        entradaAlimentos(usuario);
                    }
                    // System.out.println(diff);
                    for(int i=1;i<diff;i++) {
                        nom.append(" "+cmd[temNum+i]);
                    }
                    String no = nom.toString().trim().replace(" ","_");
                    System.out.println("length of no: "+no.length());

                    // quantidade de caracteres da nota não pode ter maior que 18 (inclui espaços)
                    if(no.length() > 18) {
                        System.out.println("erro nota mt grande 2");
                        entradaAlimentos(usuario);
                    }

                    String[] dados = { validA, cmd[temNum], no };

                    Diario diario = new Diario(usuario);

                    if(diario.adicionarAlimentoAoDiario(dados)) {
                        System.out.println("Alimento adicionado com sucesso! (Diário)");
                        entradaAlimentos(usuario);

                    } else {
                        System.out.println("Alimento não adicionado pois não existe no banco de dados global.");
                        System.out.println("Adicione o alimento no banco de dados para poder adicioná-lo ao diário.");
                        System.out.println("Uso: alimento adicionar [nome] [quantidade consumida (g)]");
                        entradaAlimentos(usuario);
                    }

                    System.out.println("Você não é suposto a ver essa mensagem. Oops!");
                    System.exit(0);

                } else if(cmdSec.matches("remover") || cmdSec.matches("r")) {
                    if(cmd.length<3) {
                        System.out.println("Quantidade de argumentos insuficiente.");
                        System.out.println("Uso: diario remover [nome do alimento]");
                        entradaAlimentos(usuario);
                    }
                    // acrescentando todas as partes do nome do alimento ao objeto sb (StringBuilder)
                    for(int i=2;i<cmd.length;i++) {
                        sb.append(" "+cmd[i]);

                    }
                    // recriando o objeto ali denovo
                    Diario ali = new Diario(usuario);
                    if(ali.removerAlimento(sb.toString().trim().replace(" ","_")) == false) {
                        System.out.println("Alimento não removido pois não existe.");
                        System.out.println("Uso: diario remover [nome]");
                        entradaAlimentos(usuario);

                    }
                    System.out.println("Alimento removido.");
                    entradaAlimentos(usuario);

                } else if(cmdSec.matches("print") || cmdSec.matches("p")) {
                    // printa o cabeçalho da lista
                    // usa um mínimo de 22 caracteres para cada string e é alinhado à esquerda por causa do - antes do 22.
                    System.out.printf("%-22s%-22s%-22s%-22s","NOME","KCAL","DATA","NOTAS");
                    System.out.println();
                    // Cria um objeto diário que vamos usar pra ler o conteúdo do diário do usuário
                    Diario d = new Diario(usuario);
                    // lista que vai armazenar os dados lidos
                    List<String> lista = new ArrayList<>();
                    // pega os dados e adiciona eles na lista
                    lista.addAll(d.getDadosAlimentosDiario());
                    // irá armazenar os dados de um registro individualmente
                    String[] indiv;
                    // armazena todos os dados de um registro, irá ser quebrado
                    // e os dados serão adicionados individualmente em indiv[]
                    String el;
                    // calorias consumidas no dia (hoje)
                    double kDia = 0.0;
                    // armazena a data de hoje, necessita de data correta no SO
                    LocalDate dataHoje = LocalDate.now();
                    // armazena a data de um registro do diário
                    LocalDate dataNoDiario;
                    // armazena a data e hora de um registro do diário, irá ser convertido pra
                    // LocalDate.
                    LocalDateTime dataHoraNoDiario;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  


                    for(int i=0;i<lista.size();i++) {
                        el = lista.get(i).replaceAll("[\\[\\]]", "");
                        indiv = el.split(",");
                        for(int j=0;j<indiv.length;j++) {
                            System.out.print(String.format("%-22s",indiv[j].replace("_"," ").trim()));
                            if(j==1) {
                                dataHoraNoDiario = LocalDateTime.parse(indiv[j+1].trim(), dtf);
                                dataNoDiario = dataHoraNoDiario.toLocalDate();
                                // System.out.println(dataNoDiario);
                                // System.out.println(dataHoje);
                                if(dataNoDiario.isEqual(dataHoje)) {
                                    kDia += Double.parseDouble(indiv[j]);
                                }
                            }
                            if(j==3) {



                                // LocalDateTime.parse() precisa de um DateTimeFormatter pra formatar a data corretamente


                            }
                            // System.out.println("\t");
                        }
                        System.out.println();
                    }

                    System.out.printf("\nTOTAL DE CALORIAS CONSUMIDAS HOJE: %.0f\n",kDia);
                    

                }
                else {
                    System.out.println("Comando inválido. [Diário]");
                }

            } // else ifs de cmdPrinc são aqui
            

            else {
                System.out.println("Comando inválido. [Submenu]"); entradaAlimentos(usuario);
            }
            entradaAlimentos(usuario);
        }
    }
}


    

