package dao.customer;

import dao.order.OrderDaoImpl;
import entity.AddressPojo;
import entity.CustomerPojo;
import entity.OrderPojo;
import helpers.connections.DBFactoryConnection;
import helpers.queries.Queries;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CustomerDaoImpl implements CustomerDao<CustomerPojo>, Queries {

    private final Connection conn;
    private PreparedStatement preparedStatement;
    private QueryRunner queryRunner;


    public CustomerDaoImpl() {
        try (DBFactoryConnection db_conn = new DBFactoryConnection()) {
            this.conn = db_conn.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Check if given id exists in customers table
     *
     * @param id int
     * @return boolean
     */
    private boolean checkIfIdExists(int id) {
        try {
            this.preparedStatement = this.conn.prepareStatement(checkIfIdExistsQuery);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getInt("count") > 0) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates random integer number
     *
     * @param max maximum value
     * @return int
     */
    private int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    @Override
    public List<OrderPojo> getCustomerOrdersFromDB(int customerId) {
        List<OrderPojo> orderPojoList = new ArrayList<>();
        queryRunner = new QueryRunner();
        ResultSetHandler<List<OrderPojo>> orderPojoBeanListHandler = new BeanListHandler<>(OrderPojo.class);

        try {
            orderPojoList.addAll(queryRunner.query(conn, Queries.customersOrdersNotNull, orderPojoBeanListHandler, customerId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderPojoList;
    }

    /**
     * Add Order/s to customer/s
     * @param customers List of CustomerPojo
     */
    @Override
    public void addOrdersToCustomers(List<CustomerPojo> customers) {
        List<OrderPojo> orders;
        OrderDaoImpl orderDao = new OrderDaoImpl();

        for (int i = 0; i < customers.size(); i++) {
            orders = getCustomerOrdersFromDB(i + 1);
            //if not empty
            if (!orders.isEmpty()) {
                // Add product/s to the order
                orders.forEach(o -> {
                    // Check if the result from the DB is not NULL
                    if (orderDao.getOrderProductsFromDB(o.getOrder_id()).size() > 0) {
                        o.setProducts(orderDao.getOrderProductsFromDB(o.getOrder_id()));
                    }
                });
                // Add Order to the customer
                customers.get(i).setOrders(orders);
            }
        }
    }

    /**
     * Just prints the customer's orders
     * @param customer List of CustomerPojo
     */
    @Override
    public void printCustomerOrder(CustomerPojo customer) {
        List<OrderPojo> orders = customer.getOrders();
        if (orders != null) {
            orders.forEach(System.out::println);
        } else {
            System.out.println("No ORDERS");
        }
    }

    /**
     * Just prints the customer's products
     * @param customer List of CustomerPojo
     */
    @Override
    public void printCustomerProducts(CustomerPojo customer) {
        List<OrderPojo> orders = customer.getOrders();
        if (orders != null) {
            orders.forEach(o ->
                    {
                        if (o.getProducts() != null) {
                            o.getProducts().forEach(System.out::println);
                        } else {
                            System.out.println("No Products");
                        }
                    }
            );
        } else {
            System.out.println("No ORDERS");
        }
    }

    /**
     * Add address to customer
     * @return List of CustomerPojo
     */
    @Override
    public List<CustomerPojo> setAddressToCustomer() {
        CustomerPojo customerPojo;
        AddressPojo addressPojo;

        List<CustomerPojo> customerPojoList = new LinkedList<>();
        List<AddressPojo> addressPojoList = new LinkedList<>();

        try {
            queryRunner = new QueryRunner();

            ResultSetHandler<List<CustomerPojo>> customerResultSetHandler = new BeanListHandler<>(CustomerPojo.class);
            ResultSetHandler<List<AddressPojo>> addressResultSetHandler = new BeanListHandler<>(AddressPojo.class);

            // Each of the handlers will take only  the necessary information from the query to create the corresponding object
            // And add them in a Linked List to keep the insertion order
            customerPojoList.addAll(queryRunner.query(conn, Queries.customerAddress, customerResultSetHandler));
            addressPojoList.addAll(queryRunner.query(conn, Queries.customerAddress, addressResultSetHandler));

            // Add Order/s  if not null to Customer/s
            addOrdersToCustomers(customerPojoList);

            for (int i = 0; i < customerPojoList.size(); i++) {

                customerPojo = customerPojoList.get(i);
                addressPojo = addressPojoList.get(i);

                // Set value for addressPojo field in CustomerPojo
                customerPojo.setAddressPojo(addressPojo);
                // Update CustomerPojo
                customerPojoList.set(i, customerPojo);
            }
            return customerPojoList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Object object) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List getAllRecords() {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int getAllRecordsCount() {
        return 0;
    }

    @Override
    public Object getRandomId() {
        return null;
    }

    @Override
    public List getRandomIds(int randomCount) {
        return null;
    }

    @Override
    public List getByIds(List ids) {
        return null;
    }
}
