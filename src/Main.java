import jdk.jshell.execution.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileManager fileToCompile = new FileManager(args[0]);
        System.out.println(fileToCompile.readString());

        Lexer mLexer = new Lexer(fileToCompile.readString());
        List<Token> tokens = mLexer.process();

        for (Token t : tokens) {
            System.out.println(t + "\n");
        }
    }
}
