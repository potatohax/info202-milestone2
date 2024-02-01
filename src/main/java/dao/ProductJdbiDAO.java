/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Product;
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
public interface ProductJdbiDAO extends ProductDAO {

    @Override
    @SqlQuery("select * from Product where Product_ID = :productId")
    @RegisterBeanMapper(Product.class)
    public Product searchById(@Bind("productId")String id);

    @Override
    @SqlUpdate("insert into Product(Product_ID, Name, Description, Category, List_Price, Quantity_In_Stock) values(:productId, :name, :description, :category, :listPrice, :quantityInStock)")
    public void saveProduct(@BindBean Product aProduct);

    @Override
    @SqlUpdate("delete from Product where Product_ID = :productId")
    public void removeProduct(@BindBean Product aProduct);

    @Override
    @SqlQuery("select * from Product order by Product_ID")
    @RegisterBeanMapper(Product.class)
    public Collection<Product> getProducts();

    @Override
    @SqlQuery("select distinct Category from Product order by Category")
    public Collection<String> getCategories();

    @Override
    @SqlQuery("select * from Product where Category = :category")
    @RegisterBeanMapper(Product.class)
    public Collection<Product> filterByCategory(@Bind("category")String category);
    
}
