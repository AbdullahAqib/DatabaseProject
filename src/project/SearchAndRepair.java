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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 *
 * @author abdul
 */
public class SearchAndRepair implements Initializable{
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField name,sort,company,retailRate,wholeSaleRate,purchaseRate,quantity;
    @FXML
    private TextField searchBar;
    @FXML
    private ToggleGroup size;
    
    @FXML private RadioButton std;
    @FXML private RadioButton small;
    @FXML private RadioButton medium;
    @FXML private RadioButton large;
    
    @FXML private TableView<Product> table;

    @FXML private TableColumn col_id;
    @FXML private TableColumn col_name;
    @FXML private TableColumn col_sort;
    @FXML private TableColumn col_size;
    @FXML private TableColumn col_company;
    @FXML private TableColumn col_retailRate;
    @FXML private TableColumn col_wholeSaleRate;
    @FXML private TableColumn col_purchaseRate;
    @FXML private TableColumn col_quantity;
    
    Product product;
    
    @FXML
    private void backToMain(ActionEvent event) throws Exception  {
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void filteration(ActionEvent event){
        String name = searchBar.getText();
        searchBar.clear();
        addDataToTable(name);
    }
    
    @FXML
    private void updateProduct(ActionEvent event) throws Exception  {
      
      RadioButton radioButton = (RadioButton) size.getSelectedToggle();
      
      product.setName(name.getText());
      product.setSort(sort.getText());
      product.setSize(radioButton.getText());
      product.setCompany(company.getText());
      product.setRetailRate(Float.parseFloat(retailRate.getText()));
      product.setWholeSaleRate(Float.parseFloat(wholeSaleRate.getText()));
      product.setPurchaseRate(Float.parseFloat(purchaseRate.getText()));
      product.setQuantity(Integer.parseInt(quantity.getText()));
      
      DBConnection dBConnection = new DBConnection();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL = "update tbl_product set name='"+product.getName()+"', sort='"+product.getSort()+"', size='"+product.getSize()+"', company='"+product.getCompany()+"', retailRate="+product.getRetailRate()+", wholeSaleRate="+product.getWholeSaleRate()+", purchaseRate="+product.getPurchaseRate()+", quantity="+product.getQuantity()+" where id="+product.getId();
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            preparedStatement.executeUpdate();
            
            clear();
            addDataToTable(null);
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
    }
    
    @FXML
    private void getSelectedItem(MouseEvent event) throws Exception  {
        ObservableList selectedProduct = table.getSelectionModel().getSelectedItems();
        product = (Product) selectedProduct.get(0);
        
        name.setText(product.getName());
        sort.setText(product.getSort());
        
        if(product.getSize().equalsIgnoreCase("std"))
            std.setSelected(true);
        else if(product.getSize().equalsIgnoreCase("smalll"))
            small.setSelected(true);
        else if(product.getSize().equalsIgnoreCase("medium"))
            medium.setSelected(true);
        else if(product.getSize().equalsIgnoreCase("large"))
            large.setSelected(true);
            
        company.setText(product.getCompany());
        retailRate.setText(""+product.getRetailRate());
        wholeSaleRate.setText(""+product.getWholeSaleRate());
        purchaseRate.setText(""+product.getPurchaseRate());
        quantity.setText(""+product.getQuantity());
        
    }
    
    public void addDataToTable(String name){
        ObservableList<Product> products = getProducts(name);
        
        table.getItems().setAll(products);
    }
    
    ObservableList<Product> getProducts(String name){
        DBConnection dBConnection = new DBConnection();
        ObservableList<Product> products = FXCollections.observableArrayList();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL = null;
            
            if(name==null)
                SQL = "select * from tbl_product";
            else
                SQL = "select * from tbl_product where name like '"+name+"%'";
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                Product product = new Product(DBConnection.resultSet.getInt("id"));
                product.setName(DBConnection.resultSet.getString("name"));
                product.setSort(DBConnection.resultSet.getString("sort"));
                product.setSize(DBConnection.resultSet.getString("size"));
                product.setCompany(DBConnection.resultSet.getString("company"));
                product.setRetailRate(DBConnection.resultSet.getFloat("retailRate"));
                product.setWholeSaleRate(DBConnection.resultSet.getFloat("wholeSaleRate"));
                product.setPurchaseRate(DBConnection.resultSet.getFloat("purchaseRate"));
                product.setQuantity(DBConnection.resultSet.getInt("quantity"));

                products.add(product);
            }
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
      
      return products;
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
       col_id.setCellValueFactory(new PropertyValueFactory("id"));
       col_name.setCellValueFactory(new PropertyValueFactory("name"));
       col_sort.setCellValueFactory(new PropertyValueFactory("sort"));
       col_size.setCellValueFactory(new PropertyValueFactory("size"));
       col_company.setCellValueFactory(new PropertyValueFactory("company"));
       col_retailRate.setCellValueFactory(new PropertyValueFactory("retailRate"));
       col_wholeSaleRate.setCellValueFactory(new PropertyValueFactory("wholeSaleRate"));
       col_purchaseRate.setCellValueFactory(new PropertyValueFactory("purchaseRate"));
       col_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
       
       addDataToTable(null);
    }

}
