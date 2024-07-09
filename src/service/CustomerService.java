package service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.CustomerDao;
import exception.CustomerException;
import model.entities.Customer;
import util.DaoFactory;

public class CustomerService {

	private final CustomerDao customerDao = DaoFactory.getDao(CustomerDao.class);

	private static boolean isValidCPF(String cpf) {
        	String regex = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        	Pattern pattern = Pattern.compile(regex);
        	Matcher matcher = pattern.matcher(cpf);
        	return matcher.matches();
    	}
	
	public Customer include(Customer customer) {
		if (!isValidCPF(customer.getCpf())) {
			throw new CustomerException("Invalid CPF!");
		}
		return customerDao.include(customer);
	}
	
	public Customer remove(int id) {
		Customer customer = retrieveCustomerById(id);
		if (customer == null) {
			throw new CustomerException("Cannot find a customer with id: " + id);
		}
		customerDao.remove(id);
		return customer;
	}
	
	public Customer retrieveCustomerById(int id) {
		Customer customer = customerDao.retrieveById(id);
		if (customer == null) {
			throw new CustomerException("There isn't a customer with id: " + id);
		}
		return customer;
	}

	public List<Customer> retrieveCustomers() {
		return customerDao.retrieveAll();
	}
	
	public Customer retrieveCostumerByCpf(String cpf) {
		return customerDao.retrieveCustomerByCpf(cpf);
	}
}
