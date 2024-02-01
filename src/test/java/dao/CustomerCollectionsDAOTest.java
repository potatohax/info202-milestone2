/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Customer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Glen G
 */
public class CustomerCollectionsDAOTest {

    private CustomerDAO customers;
    private Customer c1, c2, c3;

    @BeforeAll
    public static void initialise() {
        JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
    }

    @BeforeEach
    public void setUp() {
        /*Customer(Integer customerId, String username, String firstName,
            String surname, String shippingAddress, String emailAddress) */
        
        //customers = new CustomerCollectionsDAO();
        customers = JdbiDaoFactory.getCustomerDAO();

        c1 = new Customer(1000, "potatohax", "Glen", "Garcia", "bruh", "glenmarcogarcia123@gmail.com", "123 Goofy Street");
        c2 = new Customer(1001, "thegamingjerpy", "John", "Smith", "qwerty", "johnsmith@gmail.com", "69 Bruh Street");
        c3 = new Customer(1002, "goofyahh", "Goofy", "Gonzalez", "zweihander", "goofygonzalez@gmail.com", "180 Bikini Bottom Rd");
        
//        c1.setPassword("bruh");
//        c2.setPassword("qwerty");
//        c3.setPassword("zweihander");
        customers.saveCustomer(c1);
        customers.saveCustomer(c2);
    }

    @AfterEach
    public void tearDown() {
        customers.removeCustomer(c1);
        customers.removeCustomer(c2);
        customers.removeCustomer(c3);
    }

    @Test
    public void testDoesUserNameAndPasswordExist() {
        String username2 = c2.getUsername();
        String password2 = "qwerty";
        c2.setPassword("qwerty");

        assertThat(customers.doesUserNameAndPasswordExist(username2, password2), is(true));

        customers.removeCustomer(c2);

        assertThat(customers.doesUserNameAndPasswordExist(username2, password2), is(false));
    }

    @Test
    public void testSearchByUsername() {
        String username1 = c1.getUsername();
        assertThat(customers.searchByUsername(username1), hasProperty("username", equalTo("potatohax")));

    }

    @Test
    public void testSaveCustomer() {
        //checks that c3 should not be exist
        assertThat(customers.getCustomers(), hasSize(2));
        assertThat(customers.doesUserNameAndPasswordExist("goofyahh", "zweihander"), is(false));

        //add c3 to products
        customers.saveCustomer(c3);

        //check if in products
        assertThat(customers.getCustomers(), hasSize(3));
        assertThat(customers.doesUserNameAndPasswordExist("goofyahh", "zweihander"), is(true));
    }

    @Test
    public void testRemoveCustomer() {
        //check if c1 exists before removing
        assertThat(customers.getCustomers(), hasSize(2));
        assertThat(customers.doesUserNameAndPasswordExist("potatohax", "bruh"), (is(true)));

        //remove c1 to customers
        customers.removeCustomer(c1);

        //check if p1 is removed
        assertThat(customers.getCustomers(), hasSize(1));
        assertThat(customers.doesUserNameAndPasswordExist("potatohax", "bruh"), is(false));
    }
    
    @Test
    public void testGetCustomers() {
        
        //checks if it has all 3 students in the collections
        assertThat(customers.getCustomers(), hasSize(2));
        assertThat(customers.getCustomers(), not(hasItem(c3)));
    }

}
