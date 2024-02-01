/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Glen G
 */
public class CustomerCollectionsDAO implements CustomerDAO {
    private static final Map<String, Customer> customers = new HashMap<>();

    @Override
    public boolean doesUserNameAndPasswordExist(String username, String password) {
        if(customers.containsKey(username)) {
            Customer existingAccount = customers.get(username);
            if(existingAccount.getPassword().equals(password)) {
                return true;
            }
            
        }
        return false;
    }

    @Override
    public Customer searchByUsername(String username) {
        if(customers.containsKey(username)){
            return customers.get(username);
        }
        return null;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customers.put(customer.getUsername(), customer);
    }

    @Override
    public void removeCustomer(Customer customer) {
        customers.remove(customer.getUsername());
    }

    @Override
    public Collection<Customer> getCustomers() {
        return customers.values();
    }
    
    

    
}
