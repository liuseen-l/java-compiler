package sample.compiler.ui;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * @author : HK意境
 * @ClassName : DataInputFiled
 * @date : 2022/5/23 15:02
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class DataInputFiled {

    private TextInputDialog dialog = new TextInputDialog("输入数据");

    public DataInputFiled() {
        this.initialize();
    }

    public void initialize(){
        dialog.setTitle("Data Input Dialog");
        dialog.setHeaderText("数据输入面板");
        dialog.setContentText("Please enter your data:");
    }

    public String getInputData(){
        // 清除缓存数据
        Optional<String> res = dialog.showAndWait();
        if (res.isPresent()){
            return res.get();
        }else {
            return null ;
        }
    }



}
