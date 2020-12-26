/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 *
 * @author abdul
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private AnchorPane rootPane; 
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("addfxml.fxml"));
        rootPane.getChildren().setAll(newPane);
        
//        Parent root = FXMLLoader.load(getClass().getResource("addfxml.fxml"));
//        
//        Stage stage = (Stage) event.getScene().getWindow();
//        Scene scene = new Scene(loader.getRoot());
//        stage.setScene(scene);
    }
    
     @FXML
    private void addShopEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("shopfxml.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void addShipmentEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("Shipment.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void reportsEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void addToCartEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("AddToCart.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void recoveryEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("Recovery.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void addToCartForShopEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("AddToCartForShop.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void searchAndRepairEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("searchAndRepair.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
     @FXML
    private void addProductEvent(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        rootPane.getChildren().setAll(newPane);
        
    }
    
    @FXML
    private void backToMain(ActionEvent event) throws Exception  {
        System.out.println("You clicked me!");
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//       FXMLLoader fxmlLoader;
//       fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
    }    
    
}
