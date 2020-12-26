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
public class Shop {
    int id;
    String name;
    String ownerName;
    String address;
    String city;
    String phone;
    Float outStandingBalance;

    public Shop(int id) {
        this.id = id;
    }
    
    public Shop(String name){
        this.name = name;
    }

    public Shop(int id, String name, String ownerName, String address, String city, String phone) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getOutStandingBalance() {
        return outStandingBalance;
    }

    public void setOutStandingBalance(Float outStandingBalance) {
        this.outStandingBalance = outStandingBalance;
    }
    
    
    
}
