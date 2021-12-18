package org.example.EFDb;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.EFDb.Entities.CustomerEntity;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EFDb extends Application {

//    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");
//    private static final EntityManager entityManager = emFactory.createEntityManager();

    public static void main(String[] args) {

//       connectToDatabase(entityManager);

       launch(args);
    }



    public static void updateCustomerList(EntityManager entityManager){
//        Query customerIDQuery = entityManager.createNativeQuery("SELECT customer_id FROM customer");
//        Query storeIDQuery = entityManager.createNativeQuery("SELECT store_id FROM customer");
//        Query customerFirstNameQuery = entityManager.createNativeQuery("SELECT first_name FROM customer");
//        Query customerLastNameQuery = entityManager.createNativeQuery("SELECT last_name FROM customer");
//        Query emailQuery = entityManager.createNativeQuery("SELECT email FROM customer");
//        Query addressIDQuery = entityManager.createNativeQuery("SELECT address_id FROM customer");
//        Query activeQuery = entityManager.createNativeQuery("SELECT active FROM customer");
//        Query createDateQuery = entityManager.createNativeQuery("SELECT create_date FROM customer");
//        Query lastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM customer");
//
//        List customerIDList = customerIDQuery.getResultList();
//        List storeIDList = storeIDQuery.getResultList();
//        List customerFirstNameList = customerFirstNameQuery.getResultList();
//        List customerLastNameList = customerLastNameQuery.getResultList();
//        List emailList = emailQuery.getResultList();
//        List addressIDList = addressIDQuery.getResultList();
//        List activeList = activeQuery.getResultList();
//        List createDateList = createDateQuery.getResultList();
//        List lastUpdateList = lastUpdateQuery.getResultList();

//        List<CustomerEntity> customers = new ArrayList<>();
//        customers.add(new CustomerEntity(1,1,"Ismar","Gutic","Email.com", 1, true, null, null));

//        for(int i = 0; i < customerIDList.size(); i++){
//            Short customerID = (Short) customerIDList.get(i);
//            int intCustomerID = customerID.intValue();
//            Byte storeID = (Byte)storeIDList.get(i);
//            String customerFirstName = (String) customerFirstNameList.get(i);
//            String customerLastName = (String) customerLastNameList.get(i);
//            String email = (String)emailList.get(i);
//            Short addressID = (Short) addressIDList.get(i);
//            Boolean active = (Boolean)activeList.get(i);
//            Timestamp dateList = (Timestamp)createDateList.get(i);
//            Timestamp lastUpdate = (Timestamp)lastUpdateList.get(i);
//            CustomerEntity customer = new CustomerEntity(intCustomerID, storeID, customerFirstName, customerLastName, email, addressID, active, dateList, lastUpdate);
//            customers.add(customer);
//        }
//        olCustomer.add(customers);
    }

    public static void connectToDatabase(EntityManager entityManager){
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            updateCustomerList(entityManager);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        createCustomerDbPage(primaryStage);
    }


    private void createCustomerDbPage(Stage primaryStage){
        TableView customerTable = new TableView();
        TableColumn <Byte, CustomerEntity> col_customerID = new TableColumn<>("Customer ID");
        TableColumn <Byte, CustomerEntity> col_StoreID = new TableColumn<>("Store ID");
        TableColumn <String, CustomerEntity> col_firstName = new TableColumn<>("First Name");
        TableColumn <String, CustomerEntity> col_lastName = new TableColumn<>("Last Name");
        TableColumn <String, CustomerEntity> col_email = new TableColumn<>("Email");
        TableColumn <Byte, CustomerEntity> col_addressID = new TableColumn<>("Address ID");
        TableColumn <Byte, CustomerEntity> col_activeMember = new TableColumn<>("Active");
        TableColumn <Timestamp, CustomerEntity> col_createDate = new TableColumn<>("Create Date");
        TableColumn <Timestamp, CustomerEntity> col_lastUpdate = new TableColumn<>("Last Update");

        col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_StoreID.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_addressID.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        col_activeMember.setCellValueFactory(new PropertyValueFactory<>("active"));
        col_createDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

//        ObservableList olCustomer = FXCollections.observableArrayList();
//        olCustomer.add(new CustomerEntity(10,10,"hej","då","Email.com", 10, true, null, null));
        customerTable.getColumns().addAll(col_customerID, col_StoreID, col_firstName, col_lastName, col_email, col_addressID, col_activeMember, col_createDate, col_lastUpdate);
        List<CustomerEntity> customerList = new ArrayList<CustomerEntity>();

        //Dessa ska skapas från databasen. Testa skapa en Query och en lista för varje kolumn som hämtar datan.
        // I en for-loop bör man då lägga till varje index i listan innuti konstruktorn enl nedan.
        CustomerEntity customer1 = new CustomerEntity(10, 10,"hej","då","Email.com", 10, 10, Timestamp.valueOf("2021-11-11 12:12:12"), Timestamp.valueOf("2021-11-11 12:12:12"));
        CustomerEntity customer2 = new CustomerEntity(1, 1,"Ismar","Gut","Email.com", 1, 1, Timestamp.valueOf("2021-11-12 12:12:12"), Timestamp.valueOf("2021-11-12 12:12:12"));
        customerList.add(customer1);
        customerList.add(customer2);
        for (CustomerEntity customer : customerList){
            customerTable.getItems().add(customer);
        }
        customerTable.getItems().add(customerList.get(0));
        customerTable.getItems().add(customerList.get(1));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(customerTable);
        BorderPane customerBorderPane = new BorderPane(vbox);
        Scene scene5 = new Scene(customerBorderPane);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }


    private Boolean checkLogInCredentials(TextField tfUserName, TextField tfPassword, Label lLogInAnswer) {
        if(!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("lol123")){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }
}