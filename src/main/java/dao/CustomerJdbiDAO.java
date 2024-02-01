/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author Glen G
 */
public interface CustomerJdbiDAO extends CustomerDAO {

    @Override
    @SqlQuery("select * from Customer order by Username")
    @RegisterBeanMapper(Customer.class)
    public Collection<Customer> getCustomers();

    @Override
    @SqlUpdate("delete from Customer where Username = :username")
    public void removeCustomer(@BindBean Customer aCustomer);

    @Override
    @SqlUpdate("insert into Customer(Username, First_Name, Last_Name, Password, Email_Address, Shipping_Address) values(:username, :firstName, :surname, :password, :emailAddress, :shippingAddress)")
    public void saveCustomer(@BindBean Customer aCustomer);

    @Override
    @SqlQuery("select * from Customer where Username = :username")
    @RegisterBeanMapper(Customer.class)
    public Customer searchByUsername(@Bind("username")String username);

    @Override
    @SqlQuery("select case when count(*) = 1 then true else false end from Customer where Username = :username and Password = :password")
    public boolean doesUserNameAndPasswordExist(@Bind("username")String username, @Bind("password")String pass);
    
}
