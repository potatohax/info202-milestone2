/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gui;

import dao.ProductDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Glen G
 */
public class ProductEditorTest {
    
    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;
        
    
    @BeforeEach
    public void setUp() {
		robot = BasicRobot.robotWithNewAwtHierarchy();

		// Slow down the robot a little bit - default is 30 (milliseconds).
		// Do NOT make it less than 10 or you will have thread-race problems.
		robot.settings().delayBetweenEvents(75);

		// add some categories for testing with
		Collection<String> categories = new ArrayList<>();
		categories.add("Casio");
		categories.add("Seiko");
                categories.add("Rolex");

		// create a mock for the DAO
		dao = mock(ProductDAO.class);

		// stub the getMajors method to return the test majors
		when(dao.getCategories()).thenReturn(categories);
                
    }
    
    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void testAddProduct() {
        
        ProductEditor dialog = new ProductEditor(null, true, dao);
        
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();
        
        //entering details to ProductEditor
        fixture.textBox("txtId").enterText("12345");
        fixture.textBox("txtName").enterText("SNK709");
        fixture.textBox("txtDescription").enterText("Mechanical watch housing 21 jewels");
        fixture.comboBox("cmbCategory").selectItem("Seiko");
        fixture.textBox("txtPrice").enterText("248.99");
        fixture.textBox("txtQuantity").enterText("100");
        
        //save details
        fixture.button("btnSave").click();
        
        //creates a mockito argument captor to retrieve created product from mock DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        
        //verifies if the save method was called.
        verify(dao).saveProduct(argument.capture());

        Product savedProduct = argument.getValue();
        
        
        //testing
        assertThat("Ensure the ID was saved", savedProduct, hasProperty("productId", equalTo("12345")));
        assertThat("Ensure the Name was saved", savedProduct, hasProperty("name", equalTo("SNK709")));
        assertThat("Ensure the Description was saved", savedProduct, hasProperty("description", equalTo("Mechanical watch housing 21 jewels")));
        assertThat("Ensure the Category was saved", savedProduct, hasProperty("category", equalTo("Seiko")));
        
        BigDecimal expectedPrice = new BigDecimal("248.99");
        assertThat("Ensure the Price is saved and matches expected price", savedProduct.getListPrice(), comparesEqualTo(expectedPrice));
        
        BigDecimal expectedQuantity = new BigDecimal(100);
        assertThat("Ensure the Quantity was saved and matches expected quantity", savedProduct.getQuantityInStock(), comparesEqualTo(expectedQuantity));
    }
    
}
