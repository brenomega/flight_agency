package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.Id;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String name;
	private String cpf;
	private List<Ticket> tickets = new ArrayList<>();
	
	public Customer() {
		
	}
	
	public Customer(String name, String cpf) {
		this.name = name;
		this.cpf = cpf;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}
