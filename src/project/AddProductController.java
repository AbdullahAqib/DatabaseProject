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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.Classes.*;
import java.sql.*;
import javafx.scene.control.ToggleGroup;
/**
 *
 * @author abdul
 */
public class AddProductController implements Initializable{
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField name,sort,company,retailRate,wholeSaleRate,purchaseRate,quantity;
    @FXML
    private ToggleGroup size;
    @FXML
    private RadioButton std;
    
    @FXML
    private void backToMain(ActionEvent event) throws Exception  {
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void addProduct(ActionEvent event) throws Exception  {
 
      Product product; 
      product = new Product(name.getText());
      
      RadioButton radioButton = (RadioButton) size.getSelectedToggle();
      
      product.setSort(sort.getText());
      product.setSize(radioButton.getText());
      product.setCompany(company.getText());
      product.setRetailRate(Float.parseFloat(retailRate.getText()));
      product.setWholeSaleRate(Float.parseFloat(wholeSaleRate.getText()));
      product.setPurchaseRate(Float.parseFloat(purchaseRate.getText()));
      product.setQuantity(Integer.parseInt(quantity.getText()));
      
      name.clear();
      
      DBConnection dBConnection = new DBConnection();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL = "insert into tbl_product (name,sort,size,company,retailRate,wholeSaleRate,purchaseRate,quantity) values (?,?,?,?,?,?,?,?)";
            preparedStatement = dBConnection.connection.prepareStatement(SQL);

            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getSort());
            preparedStatement.setString(3,product.getSize());
            preparedStatement.setString(4,product.getCompany());
            preparedStatement.setFloat(5,product.getRetailRate());
            preparedStatement.setFloat(6,product.getWholeSaleRate());
            preparedStatement.setFloat(7,product.getPurchaseRate());
            preparedStatement.setInt(8,product.getQuantity());
            
            preparedStatement.executeUpdate();
            
            clear();
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
      
    }
    
    public void clear(){
        size.selectToggle(std);
        name.clear();
        sort.clear();
        company.clear();
        retailRate.clear();
        wholeSaleRate.clear();
        purchaseRate.clear();
        quantity.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

}
