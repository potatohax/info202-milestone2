/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gui;

import dao.ProductDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.math.BigDecimal;
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
public class ProductViewerIdSearchTest {
    
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
        
        dao = mock(ProductDAO.class);
        
        products.add(p1);
        products.add(p2);
        products.add(p3);
        
        when(dao.getProducts()).thenReturn(products);
        when(dao.searchById("2015")).thenReturn(p3);
        
    }
    
    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void testIdSearch() {
        ProductViewer dialog = new ProductViewer(null, true, dao);
        
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();
        
        //robot types the id for p3
        fixture.textBox("txtSearchId").enterText("2015");
        
        //search for id button
        fixture.button("btnSearch").click();
        
        //get SimpleListModel
        SimpleListModel mdl = (SimpleListModel) fixture.list("lstProducts").target().getModel();
        
        //check that JList contains 1 product
        fixture.list("lstProducts").requireItemCount(1);
        
        fixture.list("lstProducts").selectItem(p3.toString());
        
        //checking that SimpleListModel contains only p3
        assertThat(mdl, containsInAnyOrder(p3));
        assertThat(mdl, not(containsInAnyOrder(p1, p2)));
        
        verify(dao).getProducts();
        
        //verifies that searchById("2015") was called
        verify(dao).searchById("2015");
        
    }
    
}
