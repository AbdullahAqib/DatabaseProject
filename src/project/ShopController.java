/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.Classes.DBConnection;
import project.Classes.Product;
import project.Classes.Shop;

/**
 *
 * @author abdul
 */
public class ShopController implements Initializable{
    
    Shop shop = null;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField name,owner_name,address,city,phone,searchBar;
    @FXML
    public TableView tableView;
    @FXML
    private TableColumn<Shop,String> col_name;
    @FXML
    private TableColumn<Shop,Integer> col_ownerName;
    @FXML
    private TableColumn<Shop,Float> col_address;
    @FXML
    private TableColumn<Shop,String> col_city;
    @FXML
    private TableColumn<Shop,Integer> col_phone;
    @FXML
    private Button button;
     
    @FXML
    private void backToMain(ActionEvent event) throws Exception  {
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    public void tableViewAction(MouseEvent event) {
         ObservableList<Shop> selectedShop = tableView.getSelectionModel().getSelectedItems();
         shop = (Shop) selectedShop.get(0);
         
         name.setText(shop.getName());
         owner_name.setText(shop.getOwnerName());
         address.setText(shop.getAddress());
         city.setText(shop.getCity());
         phone.setText(shop.getPhone());
         button.setText("UPDATE");
         
    }
    
//    Shop getShop(String name){
//        DBConnection dBConnection = new DBConnection();
//        
//      try {
//            PreparedStatement preparedStatement;
//            dBConnection.createLink();
//            String SQL = "select * from tbl_shops where name='"+name+"'";
//            preparedStatement = dBConnection.connection.prepareStatement(SQL);
//            
//            DBConnection.resultSet = preparedStatement.executeQuery();
//            while(DBConnection.resultSet.next()){
//                Shop shop = new Shop(DBConnection.resultSet.getInt("id"));
//
//                shop.setName(DBConnection.resultSet.getString("name"));
//                shop.setOwnerName(DBConnection.resultSet.getString("owner_name"));
//                shop.setAddress(DBConnection.resultSet.getString("address"));
//                shop.setCity(DBConnection.resultSet.getString("city"));
//                shop.setPhone(DBConnection.resultSet.getString("phone"));
//
//                return shop;
//            }
//        }catch(SQLException exp){
//            exp.printStackTrace();
//        }finally{
//            dBConnection.closeLink();
//        }
//      
//      return null;
//    }
    
    @FXML
    private void buttonEvent(ActionEvent event) throws Exception{
        if(button.getText()=="UPDATE"){
            updateShop();
        }else{
            addShop();
        }
    }
    
    void updateShop(){
        DBConnection dBConnection = new DBConnection();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL = "update tbl_shops set name='"+name.getText()+"', owner_name='"+owner_name.getText()+"', address='"+address.getText()+"', city='"+city.getText()+"', phone='"+phone.getText()+"' where id='"+shop.getId()+"'";
            System.out.println(SQL);
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            preparedStatement.executeUpdate();
            
            addDataToTableView(null);
            
            clear();
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
    }
    
    @FXML
    private void addShop() throws Exception  {
      System.out.println("Add shop");
      Shop shop = new Shop(name.getText());
      
      shop.setOwnerName(owner_name.getText());
      shop.setAddress(address.getText());
      shop.setCity(city.getText());
      shop.setPhone(phone.getText());
      
      DBConnection dBConnection = new DBConnection();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL = "insert into tbl_shops (name,owner_name,address,city,phone,out_standing_balance) values (?,?,?,?,?,?)";
            preparedStatement = dBConnection.connection.prepareStatement(SQL);

            preparedStatement.setString(1,shop.getName());
            preparedStatement.setString(2,shop.getOwnerName());
            preparedStatement.setString(3,shop.getAddress());
            preparedStatement.setString(4,shop.getCity());
            preparedStatement.setString(5,shop.getPhone());
            preparedStatement.setFloat(6,0);
            
            preparedStatement.executeUpdate();
            
            addDataToTableView(null);
            
            clear();
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
    }
    
    public void clear(){
        name.clear();
        owner_name.clear();
        address.clear();
        city.clear();
        phone.clear();
    }
    
    @FXML
    private void searchShop(ActionEvent event){
        String name = searchBar.getText();
        searchBar.clear();
        addDataToTableView(name);
    }
    
    @FXML
    public void addDataToTableView(String name){
        
        DBConnection dBConnection = new DBConnection();
        ArrayList<Shop> shops = new ArrayList<Shop>();
        //listView = new ListView();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL;
            if(name==null)
                SQL = "select * from tbl_shops";
            else
                SQL = "select * from tbl_shops where name like '"+name+"%'";
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                Shop shop = new Shop(DBConnection.resultSet.getInt("id"));
                
                shop.setName(DBConnection.resultSet.getString("name"));
                shop.setOwnerName(DBConnection.resultSet.getString("owner_name"));
                shop.setAddress(DBConnection.resultSet.getString("address"));
                shop.setCity(DBConnection.resultSet.getString("city"));
                shop.setPhone(DBConnection.resultSet.getString("phone"));
                
                shops.add(shop);
      
            }
            //listView.setItems(null);
            tableView.getItems().setAll(shops);
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       col_name.setCellValueFactory(new PropertyValueFactory<Shop,String>("name"));
       col_ownerName.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("ownerName"));
       col_address.setCellValueFactory(new PropertyValueFactory<Shop,Float>("address"));
       col_city.setCellValueFactory(new PropertyValueFactory<Shop,String>("city"));
       col_phone.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("phone"));       

        addDataToTableView(null);
       
    }
    
}
