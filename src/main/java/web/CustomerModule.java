package web;

import dao.CustomerDAO;
import domain.Customer;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class CustomerModule extends Jooby {

    public CustomerModule(CustomerDAO dao) {
        get("/api/customers/{username}", ctx -> {

            String username = ctx.path("username").toString();
            Customer customer = dao.searchByUsername(username);

            if (customer == null) {
                // no customer with that username found, so return a 404/Not Found error
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return customer;
            }
        });
        post("/api/register", ctx -> {
            Customer customer = ctx.body().to(Customer.class);
            dao.saveCustomer(customer);
            return ctx.send(StatusCode.CREATED);
        });
    }
}
