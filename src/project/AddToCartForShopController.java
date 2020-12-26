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
import project.Classes.Product;
import project.Classes.Shop;

/**
 *
 * @author itlife
 */
public class AddToCartForShopController implements Initializable {

    float totalPrice =0;
    Product product = null;
    Shop shop = null;
    ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
    ArrayList<Product> products = new ArrayList<Product>();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Product> order_table;
    @FXML
    private TableColumn<Product,String> product_col;
    @FXML
    private TableColumn<Product,Integer> quantity_col;
    @FXML
    private TableColumn<Product,Float> price_col;
    @FXML
    private TableView<Product> product_table;
    @FXML
    private TableColumn<Product,String> col_name;
    @FXML
    private TableColumn<Product,Float> col_retailRate;
    @FXML
    private TableColumn<Product,Float> col_wholeSaleRate;
    @FXML
    private TableColumn<Product,Integer> col_size;
    
    @FXML
    public TableView shop_table;
    @FXML
    private TableColumn<Shop,String> col_shopName;
    @FXML
    private TableColumn<Shop,Integer> col_ownerName;
    @FXML
    private TableColumn<Shop,Float> col_address;

    @FXML
    private TextField productSearchBar;
    @FXML
    private TextField shopSearchBar;
    @FXML
    private TextField totalAmount_txt;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       product_col.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
       quantity_col.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
       price_col.setCellValueFactory(new PropertyValueFactory<Product,Float>("wholeSaleRate"));
       
       col_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
       col_retailRate.setCellValueFactory(new PropertyValueFactory<Product,Float>("retailRate"));
       col_wholeSaleRate.setCellValueFactory(new PropertyValueFactory<Product,Float>("wholeSaleRate"));
       col_size.setCellValueFactory(new PropertyValueFactory<Product,Integer>("size")); 
       
       col_shopName.setCellValueFactory(new PropertyValueFactory<Shop,String>("name"));
       col_ownerName.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("ownerName"));
       col_address.setCellValueFactory(new PropertyValueFactory<Shop,Float>("address"));
       
       addDataToShopTable(null);
       addDataToProductTable(null);
        
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
        if(!order_table.getItems().isEmpty()){
            order_table.getItems().clear();
            totalPrice = 0;
            totalAmount_txt.setText(null);
            orderLines.clear();
            products.clear();
        }
    }
    
    @FXML
    private void searchProduct(ActionEvent event){
        String name = productSearchBar.getText();
        productSearchBar.clear();
        addDataToProductTable(name);
    }

    public void addDataToProductTable(String name){
        ObservableList<Product> products = getProducts(name);
        
        product_table.getItems().setAll(products);
    }
    
    ObservableList<Product> getProducts(String name){
        DBConnection dBConnection = new DBConnection();
        ObservableList<Product> products = FXCollections.observableArrayList();
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL = "select * from tbl_product";
            
            if(name!=null)     
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
   
    private void addDataToOrderTable(MouseEvent event) {
        
        ObservableList selectedProduct = product_table.getSelectionModel().getSelectedItems();
        product = (Product) selectedProduct.get(0); 
        products.add(product);
        product.setQuantity(12);
        
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct_id(product.getId());
        orderLine.setQuantity(product.getQuantity());
        orderLine.setRate(product.getWholeSaleRate());
       
        totalPrice+=(product.getWholeSaleRate()*(orderLine.getQuantity()));
        totalAmount_txt.setText(" "+totalPrice);
  
        order_table.getItems().setAll(products);
        orderLines.add(orderLine);
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
            
            String SQL = "insert into tbl_order (order_date,order_amt) values (?,?)";
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
                
                SQL = "insert into tbl_order_line (order_id,product_id,quantity, rate) values (?,?,?,?)";
                
                preparedStatement = dBConnection.connection.prepareStatement(SQL);
                
                preparedStatement.setInt(1,order_id);
                preparedStatement.setInt(2,orderLine.getProduct_id());
                preparedStatement.setInt(3,orderLine.getQuantity());
                preparedStatement.setFloat(4,orderLine.getRate());
                
                preparedStatement.executeUpdate();
                
            }
            
            SQL = "insert into tbl_shop_order (shop_id, order_id) values (?,?)";
                
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            preparedStatement.setInt(1,shop.getId());
            preparedStatement.setInt(2,order_id);
            
            preparedStatement.executeUpdate();
            
            order_table.getItems().clear();
            totalAmount_txt.clear();
            totalPrice=0;
            orderLines.clear();
            products.clear();
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
   }
   
}
