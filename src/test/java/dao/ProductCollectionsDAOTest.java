/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Glen G
 */
public class ProductCollectionsDAOTest {

    private ProductDAO products;
    private Product p1, p2, p3;

    @BeforeAll
    public static void initialise() {
        JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
    }

    @BeforeEach
    public void setUp() {
        /* Product(String productId, String name, String description,
           String category, BigDecimal listPrice, BigDecimal quantityInStock) */

        products = JdbiDaoFactory.getProductDAO();
        //products = new ProductCollectionsDAO();

        p1 = new Product("1234", "Casio F91-W", "Original quartz classic digital watch",
                "Casio", new BigDecimal(59.99), new BigDecimal(10));

        p2 = new Product("1235", "Casio MDV-106", "Quartz dive watch with a 200m water resistance",
                "Casio", new BigDecimal(109.99), new BigDecimal(5));

        p3 = new Product("2015", "Seiko SNK809", "Automatic field watch, 30 hour power reserve",
                "Seiko", new BigDecimal(248.99), new BigDecimal(3));

        products.saveProduct(p1);
        products.saveProduct(p2);

    }

    @AfterEach
    public void tearDown() {
        products.removeProduct(p1);
        products.removeProduct(p2);
        products.removeProduct(p3);
    }

    @Test
    public void testSaveProduct() {
        //checks that p3 should not be exist in products
        assertThat(products.getProducts(), hasSize(2));
        assertThat(products.getProducts(), not(hasItem(p3)));

        //add p3 to products
        products.saveProduct(p3);

        //check if in products
        assertThat(products.getProducts(), hasSize(3));
        assertThat(products.getProducts(), (hasItem(p3)));
    }

    @Test
    public void testRemoveProduct() {
        //check if p1 exists before removing
        assertThat(products.getProducts(), hasSize(2));
        assertThat(products.getProducts(), (hasItem(p1)));

        //remove p1 to products
        products.removeProduct(p1);

        //check if p1 is removed
        assertThat(products.getProducts(), hasSize(1));
        assertThat(products.getProducts(), not(hasItem(p1)));
    }

    @Test
    public void testGetProducts() {
        //check initial products
        assertThat(products.getProducts(), containsInAnyOrder(p1, p2));
        assertThat(products.getProducts(), hasSize(2));

        //add p3 to products
        products.saveProduct(p3);

        //check products contain all test products
        assertThat(products.getProducts(), containsInAnyOrder(p1, p2, p3));
        assertThat(products.getProducts(), hasSize(3));
    }

    @Test
    public void testGetCategories() {
        //check initial product categories
        assertThat(products.getCategories(), containsInAnyOrder("Casio"));
        assertThat(products.getCategories(), not(hasItem("Seiko")));
        assertThat(products.getCategories(), hasSize(1));

        //add p3 to products
        products.saveProduct(p3);

        //check product categories again
        assertThat(products.getCategories(), containsInAnyOrder("Casio", "Seiko"));
        assertThat(products.getCategories(), hasSize(2));
    }

    @Test
    public void testSearchById() {
        String id1 = p1.getProductId();
        assertThat(products.searchById(id1), hasProperty("productId", equalTo("1234")));
    }

    @Test
    public void testFilterByCategory() {
        String cat1 = p1.getCategory();
        assertThat(products.filterByCategory(cat1), containsInAnyOrder(p1, p2));

        products.saveProduct(p3);

        String cat2 = p3.getCategory();
        assertThat(products.filterByCategory(cat2), containsInAnyOrder(p3));
    }

}
