package com.anhanguera.ctcli.arquivo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.anhanguera.ctcli.Main;
import com.anhanguera.ctcli.Usuario;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// Essa classe contém todos os métodos que fazem operações com arquivos
public class OperadorArquivos {

    // Lê os dados do arquivo csv especificado como parâmetro
    public List<List<String>> lerDadosCSV(String arq) {
        
        // Cria uma lista vazia que irá armazenar todos os dados do arquivo csv
        List<List<String>> records = new ArrayList<List<String>>();
        // Cria uma lista vazia que irá armazenar tudo menos o cabeçalho

        try {
            // Criando objeto para ler o arquivo CSV
            CSVReader csvReader = new CSVReader(new FileReader(arq));
            String[] val = null;
            // Enquanto uma linha não for nula, leia e adicione ela a lista
            while ((val = csvReader.readNext()) != null) {
                records.add(Arrays.asList(val));
            }

            return records;

        } catch (Exception e) {
            e.printStackTrace();
            }
        return records;
}
    // Só executa se o arquivo CSV não existe
    // Cria um CSV sem nenhum dado exceto o cabeçalho
    // Qual CSV irá criar e qual cabeçalho irá usar depende no parâmetro passado
    // Existem duas possibilidades: CSV c/ dados do usuário e CSV c/ os alimentos
    // TODO: usar método escreverAoCSV() ao invés de fazer toda essa porra ai em baixo
    public boolean criarCSVeMontarCabecalho(String caminhoArq) {

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv
        File file = new File(caminhoArq);
        if (file.length() != 0) {System.out.println("Arquivo não está vazio, abortando..."); return false;}
        else {
            try {
                // Cria objeto da classe FileWriter com file como parâmetro
                FileWriter outputfile = new FileWriter(file);
        
                // Cria objeto da classe CSVWriter com objeto da classe FileWriter como parâmetro
                CSVWriter writer = new CSVWriter(outputfile);
                
                // Decide se tem que escrever o cabeçalho de alimentos ou do usuário
                if (caminhoArq.contains("Usuario")) {
                    // String[] header = {"Nome", "KCAL/100g", "Data da Adição"};
                    // writer.writeNext(header);
                    // // TODO: DEBUG print, remover antes de enviar o código
                    // System.out.println("header criado");
                    String[] header = { "Nome", "Peso", "Altura", "Idade", "Sexo", "Nível de Atividade", "TDEE", "Última Atualização" };
                    writer.writeNext(header);
                    // TODO: DEBUG print, remover antes de enviar o código
                    System.out.println("header criado");

                } else {
                    writer.close();
                    return false;

                }
                // fechando o writer
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    // Mesma coisa do de cima, mas pro diário contendo os alimentos consumidos pelo usuário.
    // Ao invés de ter como parâmetro o caminho relativo do arquivo DadosUsuario, ele usa o caminho do diretório
    // do diário.
    public boolean criarCSVeMontarCabecalho(String caminhoDir, String uNome) {
        Usuario u = new Usuario(uNome);
        u.csvPessoalExiste(uNome);
        if(u.csvPessoalExiste(uNome)) {
           System.out.println("Arquivo diário já existe. Abortando...");
           return false;
        } else {
            String arq = caminhoDir+uNome+".csv";
            String[] header = {"Nome", "Kcal", "Data da Adição", "Notas"};
            if(escreverAoCSV(arq, header)) {
                return true;

            } else {
                return false;
            }

        }

    }

    public boolean csvExiste(String caminhoArq) {
        if(new File(caminhoArq).exists()) {
            return true;
        } else {return false;}

    }

    // Acrescenta dados ao final do arquivo csv
    public void acrescentarAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arq, true));
            writer.writeNext(fileira);
            writer.close();

        } catch (IOException e) {e.printStackTrace();}
    }

    // Deleta tudo, e escreve um String[] array ao CSV
    // array pode ser nulo pra limpar o arquivo completamente
    public boolean escreverAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter (new FileWriter(arq, false));
            writer.writeNext(fileira);
            writer.close();
            return true;
        } catch (IOException e) {e.printStackTrace(); return false;}
    }

    // Remove o cabeçalho da lista CSV lida por lerDadosCSV();
    public List<String> listaCSVRemoverHeader(List<List<String>> listaHeader) {
        List<String> listaNoHeader = new ArrayList<String>();

        for (int i=1; i<listaHeader.size(); i++) {
            listaNoHeader.add(listaHeader.get(i).toString());
        }

        return listaNoHeader;
    }

    // Substitui uma linha (fila) no arquivo CSV por outra.
    public void substituirFila(String arq, int numFila, String[] novaFila) {
        
        String arqRem = arq;
        // Index de numFila começa com 0, não 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arqRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Adiciona os novos dados (novaFila) no lugar do objeto removido
            allElements.add(numFila, novaFila);
            // Cria objeto da classe FileWriter para reescrever todo o arquivo, agora sem a linha
            FileWriter sw = new FileWriter(arqRem);
            // Nova instância de CSVWriter, que irá escrever os dados no arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        
    }

    // Método sobrecarregado que faz a mesma coisa desse de cima, porém não adiciona nenhum elemento
    // Só remove.
    public void substituirFila(String arq, int numFila) {
        // Caminho do arquivo
        String arqRem = arq;
        // Index de numFila começa com 0, não 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arqRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Cria objeto da classe FileWriter para reescrever todo o arquivo, agora sem a linha
            FileWriter sw = new FileWriter(arqRem);
            // Nova instância de CSVWriter, que irá escrever os dados no arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    // cria um arquivo se não existe.
    public boolean criarArquivo(String arquivo) {
        try {
            File arq = new File(arquivo);
            if (arq.createNewFile()) {
              //System.out.println("Arquivo de configuração criado: " + arq.getName());
              return true;
            } else {
              //System.out.println("Arquivo já existe.");
              return false;
              
            }
          } catch (IOException e) {
            e.printStackTrace();
            return false;
          }

    }

    // lê todas as linhas do arquivo para uma Lista<String> e retorna ela
    public List<String> lerArquivo(String arq) {
        List<String> result = new ArrayList<>();
	    try (Stream<String> lines = Files.lines(Paths.get(arq))) {
		result = lines.collect(Collectors.toList());
        return result;
	    } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return result;

    }

    // substituir uma linha $match no arquivo $arq, pela linha $subs
    // TODO: O que acontece se tiver mais de um permalogin no arquivo config?
    public boolean substituirNoArquivo(String arq, String match, String subs) {
        List<String> lista = new ArrayList<String>();
        ArquivoConfig ctcliConf = new ArquivoConfig(Main.CTCLICONFIG);
        lista.addAll(lerArquivo(ctcliConf.configArq));
        int cont = 0;
        for(int i=0;i<lista.size();i++) {
            if(lista.get(i).contains(match)) {
                lista.remove(i);
                lista.add(i, subs);
                cont++;
            }
        }
        // só continua se só achou uma instância de permalogin=
        if(cont == 1) {
            if(escreverAoArquivo(arq)) {
                for(int i=0;i<lista.size();i++) {
                    if(!(acrescentarAoArquivo(arq, lista.get(i).toString()))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        } else {
            System.out.println("ERRO: Mais de um 'permalogin' no arquivo ctcli.config?");
        }
        return false;
        
    }

    // acrescenta $asc ao final do arquivo, não sobrescreve nada
    public boolean acrescentarAoArquivo(String arq, String asc) {
        try {
            String str = asc;
            String fileName = arq;
            // cria BW com objeto FileWriter em modo append (acrescentar)
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(str+"\n");
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    // escreve ao arquivo, deletando tudo
    // TODO: Esse é suposto a ser um método sobrecarregado mas eu to com mt sono pra codar o resto
    public boolean escreverAoArquivo(String arq) {
        try {
            String str = "";
            BufferedWriter writer = new BufferedWriter(new FileWriter(arq));
            writer.write(str);
            
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deletarArquivo(String arq) {
        File f = new File(arq); 
        if (f.delete()) { 
          System.out.println("DEBUG: Arquivo deletado: " + f.getName());
          return true;
        } else {
          System.out.println("DEBUG: Arquivo não deletado.");
          return false;
        } 

    }

    public boolean arquivoExiste(String arq) {
        if(new File(arq).exists()) {
            return true;
        }
        return false;

    }



}