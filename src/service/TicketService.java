package service;

import java.util.List;

import dao.TicketDao;
import exception.TicketException;
import model.entities.Ticket;
import util.DaoFactory;

public class TicketService {

	private final TicketDao ticketDao = DaoFactory.getDao(TicketDao.class);
	
	public Ticket include(Ticket ticket) {
		return ticketDao.include(ticket);
	}
	
	public Ticket remove(int id) {
		Ticket ticket = retrieveTicketById(id);
		if (ticket == null) {
			throw new TicketException("Cannot find a ticket with id: " + id);
		}
		ticketDao.remove(id);
		return ticket;
	}
	
	public Ticket retrieveTicketById(int id) {
		Ticket ticket = ticketDao.retrieveById(id);
		if (ticket == null) {
			throw new TicketException("There isn't a ticket with id: " + id);
		}
		return ticket;
	}

	public List<Ticket> retrieveTickets() {
		return ticketDao.retrieveAll();
	}
}
