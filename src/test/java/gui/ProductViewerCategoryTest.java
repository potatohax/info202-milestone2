/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gui;

import dao.ProductDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Glen G
 */
public class ProductViewerCategoryTest {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;
    private Product p1, p2, p3;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(300);

        Collection<Product> products = new HashSet<>();

        p1 = new Product("1234", "Casio F91-W", "Original quartz classic digital watch",
                "Casio", new BigDecimal(59.99), new BigDecimal(10));

        p2 = new Product("1235", "Casio MDV-106", "Quartz dive watch with a 200m water resistance",
                "Casio", new BigDecimal(109.99), new BigDecimal(5));

        p3 = new Product("2015", "Seiko SNK809", "Automatic field watch, 30 hour power reserve",
                "Seiko", new BigDecimal(248.99), new BigDecimal(3));

        // add some categories for testing with
        Collection<String> categories = new ArrayList<>();
        categories.add("Casio");
        categories.add("Seiko");
        categories.add("Rolex");

        dao = mock(ProductDAO.class);

        products.add(p1);
        products.add(p2);
        products.add(p3);

        when(dao.getProducts()).thenReturn(products);

        // stub the getMajors method to return the test majors
        when(dao.getCategories()).thenReturn(categories);

        Collection<Product> productsTest = new HashSet<>();
        productsTest.add(p1);
        productsTest.add(p2);

        when(dao.filterByCategory("Casio")).thenReturn(productsTest);

    }

    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void testCategory() {
        ProductViewer dialog = new ProductViewer(null, true, dao);

        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        fixture.comboBox("cmbCategories").selectItem("Casio");

        //get SimpleListModel
        SimpleListModel mdl = (SimpleListModel) fixture.list("lstProducts").target().getModel();

        //check that JList contains 2 products
        fixture.list("lstProducts").requireItemCount(2);


        //checking that SimpleListModel contains p1 and p2
        assertThat(mdl, containsInAnyOrder(p1, p2));



        verify(dao).filterByCategory("Casio");
    }

}
