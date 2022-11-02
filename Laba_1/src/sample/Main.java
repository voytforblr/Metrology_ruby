package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {
    public static ObservableList<Operand>operands= FXCollections.observableArrayList();
    public static ObservableList<Operator> operators= FXCollections.observableArrayList();
    public static ObservableList<Variables> variables=FXCollections.observableArrayList();
    static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.primaryStage =primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Лаба №1");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            e.consume();
            showExitDialog();
        });
    }
        public void showExitDialog() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Выход");
            alert.setHeaderText("Вы действительно желаете выйти?");
            alert.getButtonTypes().clear();
            ButtonType yes=new ButtonType("Да", ButtonBar.ButtonData.YES);
            alert.getButtonTypes().add(new ButtonType("Нет", ButtonBar.ButtonData.NO));
            alert.getButtonTypes().add(yes);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get()==yes) {
                    primaryStage.close();
                }
            }
        }


    public static void main(String[] args) {
        launch(args);
    }
}
