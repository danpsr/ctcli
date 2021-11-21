import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static final String VERSION = "0.02";
    // constantes com os caminhos para os arquivos csv

    public static final String CSVBASEDIR = "dados";
    public static final String CSVLOGDIR = "dados/alimento_log/";

    public static final String CSVUSUARIO = "dados/DadosUsuario.csv";
    public static final String CTCLICONFIG = "dados/ctcli.config";

    public static int PRIMEIRAEXEC = 0;

    public static final Config ctcliConfig = new Config(CTCLICONFIG);
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println();

        //Usuario usr = new Usuario();
        // usr.csvPessoalExiste("colette");

        // aq.criarCSVeMontarCabecalho(Main.CSVLOGDIR, "daniel.csv");
        if(init()) {
            InterfaceCLI intf = new InterfaceCLI();
            // System.out.println(PRIMEIRAEXEC);
            intf.mostrar();

        } else {
            System.out.println("R.I.P");
        }
        
        // 
        
    }

    public static boolean init() {
        ArquivoOps aqv = new ArquivoOps();

        File logdir = new File(Main.CSVLOGDIR);

        // verifica se logdir existe, se não existir, tenta criar
        if(!(logdir.exists())) {
            // mkdirs() só retorna verdadeiro se todos os diretórios e subdiretórios foram criados
            if(logdir.mkdirs()) {
                System.out.println("DIR: Diretório 'dados' + subdiretórios criados.");
                PRIMEIRAEXEC++;
            } else {
                System.out.println("ERRO: Diretórios não foram criados!");
                return false;
            }

        }

        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso
        if(!(aqv.csvExiste(Main.CSVUSUARIO))) {
            if(aqv.criarCSVeMontarCabecalho(Main.CSVUSUARIO)) {
                System.out.println("CSV: DadosUsuario.csv não existia e foi criado.");
                PRIMEIRAEXEC++;

            } else {
                System.out.println("CSV: DadosUsuario.csv não foi criado.");
                return false;
            }
    
        }

        if(!(ctcliConfig.configExiste())) {
            if(ctcliConfig.criarConfig()) {
                System.out.println("CONFIG: Arquivo criado. "+"("+ctcliConfig.configArq+")");
                PRIMEIRAEXEC++;
            } else {
                System.out.println("CONFIG: Arquivo não foi criado.");
                return false;
            }
        }
        // só retorna verdadeiro se tudo foi executado sem erro
        return true;
    }

}