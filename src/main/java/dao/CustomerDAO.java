/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.Collection;

/**
 *
 * @author Glen G
 */
public interface CustomerDAO {
    
    boolean doesUserNameAndPasswordExist(String username, String pass);
    
    Customer searchByUsername(String username);
    
    void saveCustomer(Customer customer);
    
    void removeCustomer(Customer customer);
    
    Collection<Customer> getCustomers();
}
