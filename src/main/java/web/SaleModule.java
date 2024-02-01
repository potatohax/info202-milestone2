package web;

import dao.SaleDAO;
import domain.Sale;
import domain.SaleItem;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class SaleModule extends Jooby {

    public SaleModule(SaleDAO dao) {
        post("/api/sales", ctx -> {
            Sale sale = ctx.body().to(Sale.class);
            dao.save(sale);

            Email email = new SimpleEmail();
            email.setHostName("localhost");
            email.setSmtpPort(2525);
            email.setFrom("watchwonders@example.com");
            email.setSubject("Thank you for your order");
            StringBuilder sb = new StringBuilder();
            sb.append("Hi " + sale.getCustomer().getUsername() + " Order Confirmed\n");
            for (SaleItem item : sale.getItems()) {
                sb.append(item.getProduct().getName() + "\n");
                sb.append("Price: $" + item.getSalePrice() + "\n");
                sb.append("Quantity Purchased: " + item.getQuantityPurchased() + "\n");
                sb.append("\n");
            }

            email.setMsg(sb.toString());
            email.addTo(sale.getCustomer().getEmailAddress());
            email.send();

            return ctx.send(StatusCode.CREATED);
        });
    }
}
