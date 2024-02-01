package web;

import dao.CustomerDAO;
import dao.JdbiDaoFactory;
import dao.ProductDAO;
import dao.SaleDAO;
import domain.Customer;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;

public class Server extends Jooby {

    private final static ProductDAO dao = JdbiDaoFactory.getProductDAO();
    private final static CustomerDAO cdao = JdbiDaoFactory.getCustomerDAO();
    private final static SaleDAO sdao = JdbiDaoFactory.getSaleDAO();

    public Server() {
        install(new GsonModule());
        mount(new ProductModule(dao));
        mount(new CustomerModule(cdao));
        mount(new SaleModule(sdao));
        mount(new StaticAssetModule());

    }

    public static void main(String[] args) {
        /*
        dao.saveProduct(new Product("1234", "Casio F91-W", "Original quartz classic digital watch", "Casio", new BigDecimal(59.99), new BigDecimal(10)));
        dao.saveProduct(new Product("1235", "Casio MDV-106", "Quartz dive watch with a 200m water resistance", "Casio", new BigDecimal(109.99), new BigDecimal(5)));
        dao.saveProduct(new Product("2015", "Seiko SNK809", "Automatic field watch, 30-hour power reserve", "Seiko", new BigDecimal(248.99), new BigDecimal(3)));
        */
        cdao.saveCustomer(new Customer("potatohax", "Glen", "Garcia", "gargl791@student.otago.ac.nz", "1 Leith St", "potatohax"));

        System.out.println("\nStarting Server.");
        new Server()
                .setServerOptions(new ServerOptions().setPort(6932))
                .start();

    }

}
