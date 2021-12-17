//package org.example.EFDb;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//
//import java.io.IOException;
//
//public class HomePage {
//
//    public HomePage(){}
//
//    @FXML
//    private Button filmButton;
//
//    private Button actorButton;
//
//    private Button addCustomerButton;
//
//    private Button rentalButton;
//
//    private Button customerDbButton;
//
//    public void userFilm(ActionEvent actionEvent) throws IOException {
//        checkFilmClick();
//    }
//
//    private void checkFilmClick() throws IOException {
//        EFDb m = new EFDb();
//        m.changeScene("filmer.fxml");
////        FilmFXMLController fc = new FilmFXMLController();
////        fc.getFilmTitles();
//
//    }
//
//    public void userActor(ActionEvent actionEvent) throws IOException {
//        checkActorClick();
//    }
//
//    private void checkActorClick() throws IOException{
//        EFDb m = new EFDb();
//        m.changeScene("actor.fxml");
//    }
//
//    public void userAddCustomer(ActionEvent actionEvent) throws IOException {
//        checkAddCustomerClick();
//    }
//
//    private void checkAddCustomerClick() throws IOException{
//        EFDb m = new EFDb();
//        m.changeScene("addCustomer.fxml");
//    }
//    public void userRental(ActionEvent actionEvent) throws IOException {
//        checkRentalClick();
//    }
//
//    private void checkRentalClick() throws IOException{
//        EFDb m = new EFDb();
//        m.changeScene("rental.fxml");
//    }
//    public void userCustomerDb(ActionEvent actionEvent) throws IOException {
//        checkCustomerDbClick();
//    }
//
//    private void checkCustomerDbClick() throws IOException{
//        EFDb m = new EFDb();
//        m.changeScene("customerDb.fxml");
//    }
//
//}
