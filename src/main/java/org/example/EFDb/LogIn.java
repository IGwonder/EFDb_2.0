//package org.example.EFDb;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//
//import java.io.IOException;
//
//public class LogIn {
//
//    public LogIn() {}
//
//    @FXML
//    private Button button;
//    @FXML
//    private Label wrongLogIn;
//    @FXML
//    private TextField username;
//    @FXML
//    private PasswordField password;
//
//    public void userLogIn(ActionEvent event) throws IOException {
//        EFDb m = new EFDb();
//        m.changeScene("homePage.fxml");
////        checkLogIn();
//    }
//    private void checkLogIn() throws IOException {
//        EFDb m = new EFDb();
//        if(username.getText().toString().equals("EFDB1") && password.getText().toString().equals("lol123")){
//            wrongLogIn.setText("Success!");
//
//            m.changeScene("homePage.fxml");
//        }
//        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
//            wrongLogIn.setText("Please enter your log in information!");
//        }
//        else {
//            wrongLogIn.setText("Wrong username or password!");
//        }
//    }
//}
