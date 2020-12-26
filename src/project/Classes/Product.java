/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.Classes;

/**
 *
 * @author abdul
 */
public class Product {
    int id;
    String name,sort,size,company;
    float retailRate,wholeSaleRate,purchaseRate;
    int quantity;

    public Product(String name) {
        this.name = name;
    }
    
    public Product() {
       
    }

    public Product(int id) {
        this.id = id;
    }
    
    
    
    

    public Product(int id, String name, String sort, String size, String company, float retailRate, float wholeSaleRate, float purchaseRate, int quantity) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.size = size;
        this.company = company;
        this.retailRate = retailRate;
        this.wholeSaleRate = wholeSaleRate;
        this.purchaseRate = purchaseRate;
        this.quantity = quantity;
    }

    public Product(String name, String sort, String size, String company, float retailRate, float wholeSaleRate, float purchaseRate, int quantity) {
        this.name = name;
        this.sort = sort;
        this.size = size;
        this.company = company;
        this.retailRate = retailRate;
        this.wholeSaleRate = wholeSaleRate;
        this.purchaseRate = purchaseRate;
        this.quantity = quantity;
    }
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getRetailRate() {
        return retailRate;
    }

    public void setRetailRate(float retailRate) {
        this.retailRate = retailRate;
    }

    public float getWholeSaleRate() {
        return wholeSaleRate;
    }

    public void setWholeSaleRate(float wholeSaleRate) {
        this.wholeSaleRate = wholeSaleRate;
    }

    public float getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(float purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", sort=" + sort + ", size=" + size + ", company=" + company + ", retailRate=" + retailRate + ", wholeSaleRate=" + wholeSaleRate + ", purchaseRate=" + purchaseRate + ", quantity=" + quantity + '}';
    }
    
    
}
