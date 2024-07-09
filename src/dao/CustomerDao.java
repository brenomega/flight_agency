package dao;

import model.entities.Customer;

public interface CustomerDao extends GenericDao<Customer> {
	Customer retrieveCustomerByCpf(String cpf);
}
