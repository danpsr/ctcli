package com.anhanguera.ctcli;

import java.util.ArrayList;

import java.util.List;

import com.anhanguera.ctcli.arquivo.OperadorArquivos;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

public class Usuario {

    String nome;
    String peso;
    String altura;
    String nivelatv;
    String idade;
    String sexo;
    String diarioPath;

    public final OperadorArquivos diarioArq;
    public final OperadorArquivos csvUsuarioArq;

    public Usuario(String nome, String peso, String altura, String idade, String sexo, String nivelatv) {

        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.nivelatv = nivelatv;
        this.idade = idade;
        this.sexo = sexo;
        // caminho do arquivo do diário desse usuário
        this.diarioPath = Main.CSVLOGDIR + nome + ".csv";
        diarioArq = new OperadorArquivos(diarioPath);
        csvUsuarioArq = new OperadorArquivos(Main.CSVUSUARIO);

    }

    public Usuario(String nome) {
        this.nome = nome;
        this.diarioPath = Main.CSVLOGDIR + nome + ".csv";
        diarioArq = new OperadorArquivos(diarioPath);
        csvUsuarioArq = new OperadorArquivos(Main.CSVUSUARIO);
    }

    // retorna verdadeiro somente se conseguiu criar o usuário + csv pessoal dele
    public boolean criar() {
        // tenta criar o usuário somente se o usuário não existe
        if (!(existe())) {
            System.out.println("teste");
            // armazena a data e hora atual
            String data = UtilidadesCLI.getDataHora();
            // armazena o tdee calculado do usuário
            Double tdee = calcularTDEE();
            Diario d = new Diario(nome);
            // System.out.println(d.diarioPath);
            if (d.diarioExiste()) {
                System.out.println("diario existe!");
                diarioArq.deletarArquivo();
            }
            // se o tdee não foi calculado corretamente, retornar false
            if (tdee == -1.0) {
                System.out.println("tdee -1");
                return false;
            }
            // transforma o tdee em uma string
            String strtdee = String.format("%.0f", tdee);
            // adiciona todos os dados numa fileira
            String[] fileira = { nome, peso, altura, idade, sexo.toUpperCase(), nivelatv, strtdee, data };

            // acrescente esses dados no csv
            csvUsuarioArq.acrescentarAoCSV(fileira);
            // Depois de acrescentar ao CSV, checa se o usuário agora está presente
            if (existe()) {
                System.out.println("Usuário criado");
                // Se está presente, tenta criar o diário do usuário (CSV Pessoal)
                if (d.criarDiario()) {
                    System.out.println("Diário criado");
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;

    }

    // Remove um usuário, se achou o usuário no arquivo e removeu, retorna true, se
    // não, false
    public boolean remover() {
        // lista com todos os usuários
        List<String> b = csvUsuarioArq.listaCSVRemoverHeader(csvUsuarioArq.lerDadosCSV());
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i).contains(nome)) {
                // o +1 é porque o removerFila() lê todo o arquivo, incluindo o cabeçalho
                // então é necessário pular o cabeçalho, por isso o +1
                csvUsuarioArq.substituirFila(i + 1);
                Diario c = new Diario(nome);
                if (c.deletarDiario()) {
                    return true;
                } else {
                    return false;
                }
                // return true;
            }
        }
        return false;
    }

    // altera um dado de um usuário específico dentro do CSV
    // Parâmetros: propriedade a ser alterada, novo valor da propriedade
    public boolean alterarDados(String prop, String novoValor) {
        // Pegando a lista de usuários
        List<String> lista = new ArrayList<String>(
                csvUsuarioArq.listaCSVRemoverHeader(csvUsuarioArq.lerDadosCSV()));
        Usuario u = new Usuario(nome);
        // Iterando a lista
        for (int i = 0; i < lista.size(); i++) {
            // tenta acha o nome do usuário
            System.out.println(nome);
            System.out.println(lista.get(i));
            if (lista.get(i).contains(nome)) {
                // controla qual propriedade irá ser substituida
                switch (prop) {

                    case "nome":
                        // pega os dados do usuário
                        String[] alt = getDados();
                        // substitui a propriedade no array com um novo valor
                        alt[0] = novoValor;
                        // muda a data de alteração dos dados
                        alt[7] = UtilidadesCLI.getDataHora();
                        csvUsuarioArq.substituirFila(i + 1, alt);

                        return true;

                    case "peso":
                        String[] alt2 = getDados();
                        alt2[1] = novoValor;
                        alt2[7] = UtilidadesCLI.getDataHora();
                        // cria um novo usuário com os dados substituidos para assim poder calcular o
                        // novo TDEE
                        // baseado nos novos dados
                        u = new Usuario(alt2[0], alt2[1], alt2[2], alt2[3], alt2[4], alt2[5]);
                        // adiciona o novo TDEE ao arquivo
                        alt2[6] = String.format("%.0f", u.calcularTDEE());
                        csvUsuarioArq.substituirFila(i + 1, alt2);

                        return true;

                    case "altura":
                        String[] alt3 = getDados();
                        alt3[2] = novoValor;
                        alt3[7] = UtilidadesCLI.getDataHora();
                        u = new Usuario(alt3[0], alt3[1], alt3[2], alt3[3], alt3[4], alt3[5]);
                        alt3[6] = String.format("%.0f", u.calcularTDEE());
                        csvUsuarioArq.substituirFila(i + 1, alt3);

                        return true;

                    case "idade":
                        String[] alt4 = getDados();
                        alt4[3] = novoValor;
                        alt4[7] = UtilidadesCLI.getDataHora();
                        u = new Usuario(alt4[0], alt4[1], alt4[2], alt4[3], alt4[4], alt4[5]);
                        alt4[6] = String.format("%.0f", u.calcularTDEE());
                        csvUsuarioArq.substituirFila(i + 1, alt4);

                        return true;

                    case "sexo":
                        String[] alt5 = getDados();
                        alt5[4] = novoValor.toUpperCase();
                        alt5[7] = UtilidadesCLI.getDataHora();
                        u = new Usuario(alt5[0], alt5[1], alt5[2], alt5[3], alt5[4], alt5[5]);
                        alt5[6] = String.format("%.0f", u.calcularTDEE());
                        csvUsuarioArq.substituirFila(i + 1, alt5);

                        return true;

                    case "nivelatv":
                        String[] alt6 = getDados();
                        alt6[5] = novoValor;
                        alt6[7] = UtilidadesCLI.getDataHora();
                        u = new Usuario(alt6[0], alt6[1], alt6[2], alt6[3], alt6[4], alt6[5]);
                        alt6[6] = String.format("%.0f", u.calcularTDEE());
                        csvUsuarioArq.substituirFila(i + 1, alt6);

                        return true;

                    default:
                        return false;

                }

            }

        }
        return false;

    }

    // Retorna true se o usuário existe, se não, false
    public boolean existe() {
        String[] arrt;
        String element;
        // ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = new ArrayList<String>(
                csvUsuarioArq.listaCSVRemoverHeader(csvUsuarioArq.lerDadosCSV()));
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).contains(nome)) {
                // Limpando a string, removendo caracteres inUteis
                element = lista.get(i).replaceAll("[\\[\\] ]", "");
                // quebrando a string em um array usando , como ponto de quebra
                arrt = element.split(",");
                for (int j = 0; j < arrt.length; j++) {
                    if (arrt[j].equals(nome)) {
                        return true;
                    }
                }

                return false;
            }
        }
        return false;
    }

    // Printa todos os usuários

    // Retorna os dados do usuário em um array[]
    public String[] getDados() {
        // inicializações tão dentro do if porque se estivessem fora,
        // e o usuário não existisse, seria perda de tempo iniciar e instanciar tudo
        // isso
        if (existe()) {
            // ArquivoOps a = new ArquivoOps();
            List<String> b = csvUsuarioArq.listaCSVRemoverHeader(csvUsuarioArq.lerDadosCSV());
            String[] arrt;
            String element;
            for (int i = 0; i < b.size(); i++) {
                if (b.get(i).contains(nome)) {
                    // Limpando a string, removendo caracteres inUteis
                    element = b.get(i).replaceAll("[\\[\\] ]", "");
                    // quebrando a string em um array usando , como ponto de quebra
                    arrt = element.split(",");

                    return arrt;
                }

            }

        } else {
            System.out.println("Usuário nao existe.");
            return null;
        }
        return null;

    }

    // printa os dados do usuário para o stdout
    public void printDados() {
        String[] dados = getDados();
        if (dados != null) {
            System.out.println("Nome: " + dados[0]);
            System.out.println("Peso: " + dados[1] + "kg");
            System.out.println("Altura: " + dados[2] + "cm");
            System.out.println("Idade: " + dados[3] + " anos");
            System.out.println("Sexo: " + dados[4]);
            switch (dados[5]) {
                case "1":
                    System.out.println("Nivel de Atividade: Sedentário");
                    break;
                case "2":
                    System.out.println("Nivel de Atividade: Levemente Ativo");
                    break;
                case "3":
                    System.out.println("Nivel de Atividade: Moderadamente Ativo");
                    break;
                case "4":
                    System.out.println("Nivel de Atividade: Muito Ativo");
                    break;
                case "5":
                    System.out.println("Nivel de Atividade: Atleta");
                    break;
                default:
                    System.out.println("printDados: nivelatv inválido.");
                    break;
            }

            System.out.println("TDEE: " + dados[6]);
            System.out.println("Ultima atualizacao: " + dados[7].replace("-", " "));

        }
    }

    // Calcula BMI do usuário usando a fórmula de Harris-Benedict
    // BMI = Índice Metabólico Basal, quantas calorias o corpo gasta em repouso
    // durante 24h
    // Necessário pra calcular o TDEE
    // Fórmula:
    // Mulheres: 655 + (9,6 x peso em kg) + (1,8 x altura em cm) – (4,7 x idade em
    // anos)
    // Homens: 66 + (13,7 x peso em kg) + (5 x altura em cm) – (6,5 x idade em anos)
    public double calcularBMI() {

        if (sexo.equalsIgnoreCase("f")) {
            double bmi = (10 * Double.parseDouble(peso)) + (6.25 * Double.parseDouble(altura))
                    - (5 * Double.parseDouble(idade)) - 161;

            return Math.round(bmi);

        } else if (sexo.equalsIgnoreCase("m")) {
            double bmi = (10 * Double.parseDouble(peso)) + (6.25 * Double.parseDouble(altura))
                    - (5 * Double.parseDouble(idade)) + 5;

            return Math.round(bmi);
        }
        return 0.0;
    }

    public double calcularTDEE() {
        Double tdee;
        Double bmi = calcularBMI();
        if (bmi == 0.0) {
            return -1.0;
        }
        switch (nivelatv) {
            case "1":
                tdee = bmi * 1.2;
                return Math.round(tdee);
            case "2":
                tdee = bmi * 1.375;
                return Math.round(tdee);
            case "3":
                tdee = bmi * 1.55;
                return Math.round(tdee);
            case "4":
                tdee = bmi * 1.725;
                return Math.round(tdee);
            case "5":
                tdee = bmi * 1.9;
                return Math.round(tdee);
            default:
                return -1.0;

        }

    }

    public String getTDEE() {
        String[] dados = getDados();
        if (dados.length < 8) {
            return "";
        }
        return dados[6].trim();

    }

    // seria melhor usar códigos de retorno ao invés de true ou false.
    // TODO: migrar esses prints pra interfaceCLI, mudar de boolean pra int pra
    // controlar mensagens de erro

    // Códigos de retorno:
    // 0 - Sucesso
    // 1 - Falha - Usuário já existe
    // 2 - Falha - Valor do parâmetro peso inválido
    // 3 - Falha - Valor do parâmetro altura inválido
    // 4 - Falha - Valor do parâmetro idade inválido
    // 5 - Falha - Valor do parâmetro sexo inválido
    public static int validarDadosUsuario(String[] dados) {
        Usuario u = new Usuario(dados[0]);
        if (u.existe()) {
            return 1;
        }

        if (!dados[1].matches("[0-9]+") || dados[1].length() < 2 || Integer.parseInt(dados[1]) > 500) {
            return 2;
        }

        if (!dados[2].matches("[0-9]+") || dados[2].length() < 2 || Integer.parseInt(dados[2]) > 300) {
            return 3;
        }

        if (!dados[3].matches("[0-9]+") || Integer.parseInt(dados[3]) > 110 || Integer.parseInt(dados[3]) < 10) {
            return 4;
        }

        if (!(dados[4].equalsIgnoreCase("f") || dados[4].equalsIgnoreCase("m"))) {
            return 5;
        }

        return 0;

    }

    // método sobrecarregado, ao invés de validar todos os dados, valida só um $dado
    // conforme $prop

    public static boolean validarDadosUsuario(String prop, String dado) {
        switch (prop) {
            case "peso":
                if (!dado.matches("[0-9]+") || dado.length() < 2 || Integer.parseInt(dado) > 500) {
                    return false;
                }
                return true;

            case "altura":
                if (!dado.matches("[0-9]+") || dado.length() < 2 || Integer.parseInt(dado) > 300) {
                    return false;
                }
                return true;

            case "idade":
                int d5 = Integer.parseInt(dado);
                if (!dado.matches("[0-9]+") || d5 > 110 || d5 < 10) {
                    return false;
                }
                return true;

            case "sexo":

                if (!(dado.equalsIgnoreCase("f") || dado.equalsIgnoreCase("m"))) {
                    return false;
                }
                return true;

            case "nivelatv":

                int d6 = Integer.parseInt(dado);
                if (!dado.matches("[0-9]+") || d6 > 5 || d6 < 1) {
                    return false;
                }
                return true;

            case "default":
                return false;
        }
        return false;
    }

}
