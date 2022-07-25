import dao.customer.CustomerDaoImpl;
import entity.CustomerPojo;

public class Main {
    public static void main(String[] args) {

        CustomerDaoImpl customerDao = new CustomerDaoImpl();

        for (CustomerPojo customerPojo : customerDao.setAddressToCustomer()) {
            System.out.printf("Customer info: Id = %s, Name = %s, Address = %s \n",
                    customerPojo.getCustomer_id(),
                    customerPojo.getName(),
                    customerPojo.getAddressPojo().getAddress());

            System.out.println("**************************** < ORDERS > ****************************");
            customerDao.printCustomerOrder(customerPojo);
            System.out.println("**************************** < Products > ****************************");
            customerDao.printCustomerProducts(customerPojo);
            System.out.println();
        }
    }
}
