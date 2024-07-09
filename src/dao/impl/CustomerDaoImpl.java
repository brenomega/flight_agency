package dao.impl;

import dao.CustomerDao;
import model.entities.Customer;

public class CustomerDaoImpl extends GenericDaoImpl<Customer> implements CustomerDao {

	public Customer retrieveCustomerByCpf(String cpf) {
		return map.values()
	              .stream()
	              .filter(customer -> customer.getCpf().equals(cpf))
	              .findFirst()
	              .orElse(null);
	}
}
