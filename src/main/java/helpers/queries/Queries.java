package helpers.queries;

public interface Queries {
    String checkIfIdExistsQuery = "SELECT COUNT(customer_id) FROM customers WHERE customer_id = ?";

    String customerAddress ="SELECT *  FROM customers JOIN customers_addresses  ON" +
            " customers.customer_id = customers_addresses.address_id";

    String customersOrdersNotNull = "SELECT o.order_id, o.customer_id, o.is_order_completed, o.is_order_payed, o.date_of_order, o.date_order_completed\n" +
            " FROM customers as c\n" +
            " LEFT JOIN orders as o\n" +
            " ON c.customer_id = o.customer_id   \n" +
            " WHERE o.order_id IS NOT NULL AND c.customer_id = ?;";

    String getOrderProducts = "SELECT  op.product_id, op.quantity, pi.price_without_vat, pi.price_with_vat, pi.product_type\n" +
            " FROM orders_products as op JOIN products_inventory as pi on pi.product_id = op.product_id WHERE op.order_id = ?";

}
