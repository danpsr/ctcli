package com.anhanguera.ctcli.terminal.menu.mensagens;

// import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

// esse enum armazena informações sobre os comandos globais, como o comando em si, seu atalho e sua Descricao

public enum CmdGlobal {
    HEADER("Comandos Globais"),
    GLOBAL_CMD_INFO("Esses comandos nao necessitam de um prefixo."),
    
    SAIR("sair"
    ,"s"
    ,"sai do programa."),

    CLEAR("clear"
    ,"c"
    ,"limpa a tela do terminal (se for possivel).");
    


    private final String cmd;
    private final String atalho;
    private final String descricao;
    private final String info;

    private CmdGlobal(String cmd, String atalho, String descricao) {
        this.cmd = cmd;
        this.atalho = atalho;
        this.descricao = descricao;
        this.info = "";
    }
    
    private CmdGlobal(String info) {
        this.info = info;
        this.cmd = "";
        this.atalho = "";
        this.descricao = "";
    }

    public String getComando() {
        return cmd;
    }

    public String getAtalho() {
        return atalho;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {

        // se info nao estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(info)) {
            return info+"\n";
        }

        // se estiver em branco sim, retornar isso
        return cmd+"\n"+"Atalho: "
        +"\""+atalho+"\""+"\n"
        +"Descricao: "+descricao+"\n";



    }
}
