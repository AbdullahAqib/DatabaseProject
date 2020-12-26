/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author abdul
 */
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.Classes.DBConnection;
import project.Classes.OrderLine;
import project.Classes.OrderLineView;
import project.Classes.Product;
import project.Classes.Recovery;
import project.Classes.Shop;

public class RecoveryController implements Initializable {


    Shop shop = null;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<OrderLineView> orderLineView_table;
    @FXML
    private TableColumn<OrderLineView,Date> col_order_date;
    @FXML
    private TableColumn<OrderLineView,String> col_product;
    @FXML
    private TableColumn<OrderLineView,Integer> col_quantity;
    @FXML
    private TableColumn<OrderLineView,Float> col_price;
    
    @FXML
    private TableView<Recovery> recovery_table;
    @FXML
    private TableColumn<Recovery,Date> col_recovery_date;
    @FXML
    private TableColumn<Recovery,Float> col_recovery_amount;

    
    @FXML
    public TableView shop_table;
    @FXML
    private TableColumn<Shop,String> col_shopName;
    @FXML
    private TableColumn<Shop,Integer> col_ownerName;
    @FXML
    private TableColumn<Shop,Float> col_address;
    @FXML
    private TableColumn<Shop,String> col_city;
    @FXML
    private TableColumn<Shop,Integer> col_phone;

    @FXML
    private TextField shopSearchBar;
    @FXML
    private TextField recoveryAmount;
    @FXML
    private Label outStandingBalance;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       col_order_date.setCellValueFactory(new PropertyValueFactory<OrderLineView,Date>("date"));
       col_product.setCellValueFactory(new PropertyValueFactory<OrderLineView,String>("productName"));
       col_quantity.setCellValueFactory(new PropertyValueFactory<OrderLineView,Integer>("quantity"));
       col_price.setCellValueFactory(new PropertyValueFactory<OrderLineView,Float>("rate"));
       
       col_recovery_date.setCellValueFactory(new PropertyValueFactory<Recovery,Date>("date"));
       col_recovery_amount.setCellValueFactory(new PropertyValueFactory<Recovery,Float>("amount"));
       
       col_shopName.setCellValueFactory(new PropertyValueFactory<Shop,String>("name"));
       col_ownerName.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("ownerName"));
       col_address.setCellValueFactory(new PropertyValueFactory<Shop,Float>("address"));
       col_city.setCellValueFactory(new PropertyValueFactory<Shop,String>("city"));
       col_phone.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("phone"));  
       
       addDataToShopTable(null);
        
    }
    
    @FXML
    private void backToMain(ActionEvent event) throws Exception  {
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
    @FXML
    private void searchShop(ActionEvent event){
        String name = shopSearchBar.getText();
        shopSearchBar.clear();
        addDataToShopTable(name);
    }
    
    public void addDataToShopTable(String name){
        
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
                shop.setOutStandingBalance(DBConnection.resultSet.getFloat("out_standing_balance"));
                shops.add(shop);
      
            }
            //listView.setItems(null);
            shop_table.getItems().setAll(shops);
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
        
    }
    
    @FXML
    public void shopTableViewAction(MouseEvent event) {
        ObservableList selectedProduct = shop_table.getSelectionModel().getSelectedItems();
        shop = (Shop) selectedProduct.get(0);
        
        outStandingBalance.setText(""+shop.getOutStandingBalance());
        
        addDataToOrderLineViewTable();
        addDataToRecoveryTable();
    }
    
    void addDataToOrderLineViewTable(){
        DBConnection dBConnection = new DBConnection();
        ArrayList<OrderLineView> orderLines = new ArrayList<OrderLineView>();
        
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL;
            
            SQL = "select order_date, name, rate, tbl_order_line.quantity from tbl_shop_order inner join tbl_order on tbl_shop_order.order_id = tbl_order.order_id inner join tbl_order_line on tbl_order.order_id = tbl_order_line.order_id inner join tbl_product on tbl_order_line.product_id = tbl_product.id where tbl_shop_order.shop_id="+shop.getId();
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                OrderLineView orderLine = new OrderLineView();
                
                orderLine.setDate(DBConnection.resultSet.getDate("order_date"));
                orderLine.setProductName(DBConnection.resultSet.getString("name"));
                orderLine.setQuantity(DBConnection.resultSet.getInt("quantity"));
                orderLine.setRate(DBConnection.resultSet.getFloat("rate"));
                
                
                orderLines.add(orderLine);
      
            }
            
            orderLineView_table.getItems().setAll(orderLines);
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
    }
    
    void addDataToRecoveryTable(){
        DBConnection dBConnection = new DBConnection();
        ArrayList<Recovery> recoveries = new ArrayList<Recovery>();
        
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL;
            
            SQL = "select * from tbl_recovery where shop_id="+shop.getId();
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                Recovery recovery = new Recovery();
                
                recovery.setDate(DBConnection.resultSet.getDate("recovery_date"));
                recovery.setAmount(DBConnection.resultSet.getFloat("amount_received"));                
                
                recoveries.add(recovery);
      
            }
            
            recovery_table.getItems().setAll(recoveries);
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
    }
    
    
    
   @FXML
   private void recover(ActionEvent event){
       DBConnection dBConnection = new DBConnection();
       
       if(shop==null){
           return;
       }
       
       try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            
            String SQL = "call recover("+shop.getId()+","+Float.parseFloat(recoveryAmount.getText())+")";
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
           
            preparedStatement.executeQuery();
            
            recoveryAmount.clear();
            addDataToShopTable(null);
            outStandingBalance.setText("0");
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
   }
   
}
