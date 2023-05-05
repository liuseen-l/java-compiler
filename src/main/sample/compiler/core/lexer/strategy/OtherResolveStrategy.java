package sample.compiler.core.lexer.strategy;


import sample.compiler.core.lexer.constant.InitialStatus;
import sample.compiler.core.lexer.exception.WordAnalyzerException;
import sample.compiler.core.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : OtherResolveStrategy
 * @date : 2022/3/14 14:01
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class OtherResolveStrategy extends ResolveStrategy{
    @Override
    public Object resolveWord(int status, LexicalAnalyzer analyzer) {

        if(status == InitialStatus.Other_Status.statusCode){

            // 其他字符，直接异常
            analyzer.getWordAnalyzerExceptionList().add(
                    new WordAnalyzerException(analyzer.getCurrentCursor(),"Symbol invalid error: An invalid symbol occurs instead of a defined symbol!"));
        }

        return null;
    }
}
