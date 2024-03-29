package com.anhanguera.ctcli.terminal.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.anhanguera.ctcli.terminal.menu.mensagens.Msg;

public class UtilidadesCLI {

    // scanners usados em múltiplos lugares devem ficar fora de um método e ter como
    // características public, static e final
    public static final Scanner scanner = new Scanner(System.in);

    // Método que cuida de pegar os dados entrados pelo usuário
    public static String getUserInput() {
        System.out.print(">> ");
        String str = scanner.nextLine();
        str.replace("\n", "");
        return str.trim();
    }

    // Esperar pela próxima nova linha antes de continuar
    public static void waitNext() {
        // Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // scanner.close();
    }

    // Limpar saída do terminal
    public static void clear() {

        final String os = System.getProperty("os.name");

        System.out.println(Msg.CLEARSC + "" + Msg.CLEARSC);

        if (os.contains("Windows")) {
            for (int i = 0; i <= 2; i++) {
                // System.out.println(Msg.CLEARSC);
                System.out.println(Msg.CLEARSC + "" + Msg.CLEARSC);

            }
        } else {
            // Sempre use println e não print quando usar esse caractere especial
            // Se usar print(), dá erro depois de um tempo.
            System.out.print("\n" + "\033[H\033[2J");
        }
    }

    public static String getDataHora() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now).toString());

    }

    public static List<String> getListaArq(String dir) {

        // TODO: migrar isso pra OperadorArquivos

        // TODO: Tem que retornar uma List<String>
        List<String> arqs = new ArrayList<String>();

        // Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(dir);

        // Populates the array with names of files and directories
        pathnames = f.list();

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            arqs.add(pathname);

            // Print the names of files and directories
            // System.out.println(pathname);
        }
        return arqs;

    }

    public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
}
