/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

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
import project.Classes.Product;
import project.Classes.Shop;

/**
 *
 * @author itlife
 */
public class ShipmentController implements Initializable {

    float totalPrice =0;
    Product product = null;
    ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();

    @FXML
    private AnchorPane rootPane;
    
//    @FXML
//    private ToggleGroup size;
//    @FXML private RadioButton std;
//    @FXML private RadioButton small;
//    @FXML private RadioButton medium;
//    @FXML private RadioButton large;
    
    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private Button btn_addToList;
    @FXML
    private TableView<Product> order_table;
    @FXML
    private TableColumn<Product,String> product_col;
    @FXML
    private TableColumn<Product,Integer> quantity_col;
    @FXML
    private TableColumn<Product,Float> price_col;
    @FXML
    private TableView<Product> data_table;
    @FXML
    private TableColumn<Product,String> col_name;
    @FXML
    private TableColumn<Product,Float> col_retailRate;
    @FXML
    private TableColumn<Product,Integer> col_size;
//    @FXML
//    private TextField discount;
    @FXML
    private TextField searchBar;
//    @FXML
//    private Button btn_addToList1;
//    @FXML
//    private ToggleGroup toggle_rate;
    @FXML
    private TextField totalAmount_txt;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       product_col.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
       quantity_col.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
       price_col.setCellValueFactory(new PropertyValueFactory<Product,Float>("purchaseRate"));
       
       col_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
       col_retailRate.setCellValueFactory(new PropertyValueFactory<Product,Float>("purchaseRate"));
       col_size.setCellValueFactory(new PropertyValueFactory<Product,Integer>("size")); 
       
       addDataToTable(null);
    }
    
    @FXML
    private void backToMain(ActionEvent event) throws Exception  {
        
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
    }
    
//the given function increases the value of quantity by one
    @FXML
public void increaseByOne(ActionEvent event){

   String num;
   num = "" +(Integer.parseInt(quantity.getText()) +1);

   if(quantity.getText()== null){
   
        quantity.setText("1");
   }
   
   quantity.setText(num);


}

//the given function decreases the value of quantity by one
@FXML
public void decreasesByOne(ActionEvent event){

   String num;
   num = "" +(Integer.parseInt(quantity.getText()) -1);


   quantity.setText(num);


}

    
//    public void listViewAction(MouseEvent event) {
//         ObservableList<String> selectedName = listView.getSelectionModel().getSelectedItems();
//         Product product = getProduct(selectedName.get(0));
//         
//         productName.setText(product.getName());
//         productName.setEditable(false);
//         quantity.setText("1");
//         discount.setText("0");
//         price.setText(""+product.getRetailRate());
//         
//    }

    public void addDataToTable(String name){
        ObservableList<Product> products = getProducts(name);
        
        data_table.getItems().setAll(products);
    }
    
    ObservableList<Product> getProducts(String name){
        DBConnection dBConnection = new DBConnection();
        ObservableList<Product> products = FXCollections.observableArrayList();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL=null;
            
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

    @FXML
    private void getSelectedItem(MouseEvent event) throws Exception  {
        ObservableList selectedProduct = data_table.getSelectionModel().getSelectedItems();
        product = (Product) selectedProduct.get(0);
        
        name.setText(product.getName());
        
//        if(product.getSize().equalsIgnoreCase("std"))
//            std.setSelected(true);
//        else if(product.getSize().equalsIgnoreCase("small"))
//            small.setSelected(true);
//        else if(product.getSize().equalsIgnoreCase("medium"))
//            medium.setSelected(true);
//        else if(product.getSize().equalsIgnoreCase("large"))
//            large.setSelected(true);
        
//        RadioButton radioButton = (RadioButton) toggle_rate.getSelectedToggle();
        
//        if(radioButton.getText().equalsIgnoreCase("Retail Sale Rate"))
//            price.setText(""+product.getRetailRate());
//        else if(radioButton.getText().equalsIgnoreCase("Whole Sale Rate"))
//            price.setText(""+product.getWholeSaleRate());
        
        quantity.setText(""+1);
        price.setText(""+product.getPurchaseRate());
//        discount.setText(""+0);
        
    }
    
//    @FXML
//    private void setRetailRate(MouseEvent event) throws Exception{
//        price.setText(""+product.getRetailRate());
//    }
//    
//    @FXML
//    private void setWholeSaleRate(MouseEvent event) throws Exception{
//       price.setText(""+product.getWholeSaleRate());
//    }


    @FXML
    private void searchProduct(ActionEvent event){
        String name = searchBar.getText();
        searchBar.clear();
        addDataToTable(name);
    }
    
    @FXML  
    private void addDataToShipmentTable(ActionEvent event) {
        
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct_id(product.getId());
        orderLine.setQuantity(Integer.parseInt(quantity.getText()));
        orderLine.setRate(Float.parseFloat(price.getText()));
        
        Product product = new Product();
        product.setName(name.getText());
        product.setQuantity(Integer.parseInt(quantity.getText()));
        product.setPurchaseRate(Float.parseFloat(price.getText()));
       
        totalPrice+=(Float.parseFloat(price.getText())*(orderLine.getQuantity()));
        totalAmount_txt.setText(" "+totalPrice);
  
        order_table.getItems().add(product);
        orderLines.add(orderLine);
        
        name.clear();
        quantity.clear();
        price.clear();
//        discount.clear();
    }
    
    
   @FXML
   private void check(ActionEvent event){
       DBConnection dBConnection = new DBConnection();
       int order_id=0;
       OrderLine orderLine = null;
       
       if(totalAmount_txt.getText()==null){
           return;
       }
       
       try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            
            String SQL = "insert into tbl_shipment (shipment_date,shipment_amt) values (?,?)";
            preparedStatement = dBConnection.connection.prepareStatement(SQL,RETURN_GENERATED_KEYS);
            
            Date date = new Date(System.currentTimeMillis());
            
            preparedStatement.setDate(1,date);
            preparedStatement.setFloat(2,totalPrice);
            
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                order_id=rs.getInt(1);
            }
            
            for(int i=0;i<orderLines.size();i++){
                orderLine = orderLines.get(i);
                
//                soldOut(orderLine.getProduct_id(),orderLine.getQuantity());
                
                SQL = "insert into tbl_shipment_line (shipment_id,product_id,quantity,rate) values (?,?,?,?)";
                
                preparedStatement = dBConnection.connection.prepareStatement(SQL);
                
                preparedStatement.setInt(1,order_id);
                preparedStatement.setInt(2,orderLine.getProduct_id());
                preparedStatement.setInt(3,orderLine.getQuantity());
                preparedStatement.setFloat(4,orderLine.getRate());
                
                preparedStatement.executeUpdate();
                
            }
            
            order_table.getItems().clear();
            totalAmount_txt.clear();
            totalPrice=0;
            orderLines.clear();
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
   }
   
   
//   void soldOut(int product_id,int quantity){
//       DBConnection dBConnection = new DBConnection();
//       
//       try {
//            PreparedStatement preparedStatement;
//            dBConnection.createLink();
//            
//            String SQL = "select quantity from tbl_product where id="+product_id;
//            preparedStatement = dBConnection.connection.prepareStatement(SQL);
//            
//            preparedStatement.setDate(1,date);
//            preparedStatement.setFloat(2,totalPrice);
//            
//            preparedStatement.executeUpdate();
//            
//        }catch(SQLException exp){
//            exp.printStackTrace();
//        }finally{
//            dBConnection.closeLink();
//        }
//   }
   
}