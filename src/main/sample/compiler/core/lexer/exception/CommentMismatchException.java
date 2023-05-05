package sample.compiler.core.lexer.exception;


//import lombok.Data;
//import lombok.ToString;
import sample.compiler.core.lexer.status.CommentStatus;

/**
 * @author : HK意境
 * @ClassName : CommentMismatchException
 * @date : 2022/3/13 16:19
 * @description : 注释不匹配异常
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
//@Data
//@ToString
public class CommentMismatchException extends WordAnalyzerException{

    private int row ;
    private int col ;
    private String message ;

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommentMismatchException{" +
                "row=" + row +
                ", col=" + col +
                ", message='" + message + '\'' +
                '}';
    }

    public CommentMismatchException(CommentStatus status){
        super("CommentMismatchException");
        this.row = status.row;
        this.col = status.column ;
        this.message = "CommentMismatchException";

    }
}