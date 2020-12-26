/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import project.Classes.DBConnection;
import project.Classes.Recovery;

/**
 * FXML Controller class
 *
 * @author abdul
 */
public class ReportsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private LineChart<String,Number> salesLineChart;
    @FXML
    private NumberAxis sales_yAxis;
    @FXML
    private CategoryAxis sales_xAxis;
    @FXML
    private LineChart<String,Number> recoveriesLineChart;
    @FXML
    private NumberAxis recoveries_yAxis;
    @FXML
    private CategoryAxis recoveries__xAxis;
    @FXML
    private LineChart<String,Number> shipmentLineChart;
    @FXML
    private NumberAxis shipment_yAxis;
    @FXML
    private CategoryAxis shipment_xAxis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        XYChart.Series<String, Number> sales = new XYChart.Series<String, Number>();
        sales.setName("Sales");
        XYChart.Series<String, Number> recoveries = new XYChart.Series<String, Number>();
        recoveries.setName("Recoveries");
        XYChart.Series<String, Number> shipments = new XYChart.Series<String, Number>();
        shipments.setName("Shipments");
        
        DBConnection dBConnection = new DBConnection();
        
        
      try {
            PreparedStatement preparedStatement;
            dBConnection.createLink();
            String SQL;
            
            SQL = "select order_date as date,sum(order_amt) as total_sales from tbl_order group by order_date";
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                
                Date date = DBConnection.resultSet.getDate("date");  
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                String strDate = dateFormat.format(date);      
                
                sales.getData().add(new XYChart.Data<String, Number>(strDate, DBConnection.resultSet.getFloat("total_sales")));
                //sales.add(new XYChart.Data<String,Number>(strDate, DBConnection.resultSet.getFloat("total_sales")));

            }
            
            SQL = "select recovery_date as date,sum(amount_received) as total_recovery from tbl_recovery group by recovery_date";
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                
                Date date = DBConnection.resultSet.getDate("date");  
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                String strDate = dateFormat.format(date);  
                
                recoveries.getData().add(new XYChart.Data<String, Number>(strDate, DBConnection.resultSet.getFloat("total_recovery")));
                //recoveries.getData().add(new XYChart.Data<>(strDate, DBConnection.resultSet.getFloat("total_recovery")));

            }
            
            SQL = "select shipment_date as date,sum(shipment_amt) as total_purchase from tbl_shipment group by shipment_date";
            
            preparedStatement = dBConnection.connection.prepareStatement(SQL);
            
            DBConnection.resultSet = preparedStatement.executeQuery();
            
            while(DBConnection.resultSet.next()){
                
                Date date = DBConnection.resultSet.getDate("date");  
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                String strDate = dateFormat.format(date);      
                
                shipments.getData().add(new XYChart.Data<String, Number>(strDate, DBConnection.resultSet.getFloat("total_purchase")));
                //sales.add(new XYChart.Data<String,Number>(strDate, DBConnection.resultSet.getFloat("total_sales")));

            }
            
            salesLineChart.getData().add(sales);
            recoveriesLineChart.getData().add(recoveries);
            shipmentLineChart.getData().add(shipments);
            
//            lineChart.getData().add(sales);
//            lineChart.getData().add(recoveries);
            
        }catch(SQLException exp){
            exp.printStackTrace();
        }finally{
            dBConnection.closeLink();
        }
    }
          

    @FXML
    private void backToMain(ActionEvent event) throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(newPane);
        
    }
    
}
