package sample.compiler.core;

import sample.compiler.core.lexer.constant.Word;
import sample.compiler.core.lexer.handle.AnalyzerHandler;
import sample.compiler.core.lexer.lexicalnalyzer.LexicalAnalyzer;
import sample.compiler.core.lexer.token.TokenPool;

public class CompilerClient {

    private static LexicalAnalyzer lexer ;
    private static TokenPool tokenPool ;

    // 做系统的编译过程
    public void doCompile(){



    }

    // 词法分析
    public static void doLexicalAnalysis(String[] lines){

        /*lexer = new LexicalAnalyzer();

        for (String line : lines) {
            StringBuilder sb = new StringBuilder(line);
            System.out.println(sb.toString());

            // 每次执行完毕后需要清零，重新开始，
            lexer.clear() ;

            AnalyzerHandler.run(sb,lexer);
        }

        // 获取 token 单词
        tokenPool = new TokenPool(lexer);

        while(tokenPool.hasNextToken()){
            Word token = tokenPool.getNextToken();
            System.out.println(token.getValue()+"\t\t\t" + token.getWeakClass()  + "\t\t\t" + token.getToken());
        }*/
    }
}
