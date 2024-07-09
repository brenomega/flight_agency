package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.CustomerException;
import exception.FlightException;
import exception.FlightExecException;
import exception.SegmentException;
import exception.SegmentExecException;
import exception.TicketException;
import model.entities.Customer;
import model.entities.Flight;
import model.entities.FlightExec;
import model.entities.SegmentExec;
import model.entities.Ticket;
import service.FlightExecService;
import service.FlightService;
import service.TicketService;
import service.CustomerService;

public class CustomerProgram {

	private final FlightService flightService = new FlightService();
	private final FlightExecService flightExecService = new FlightExecService();
	private final CustomerService customerService = new CustomerService();
	private final TicketService ticketService = new TicketService();
	Scanner input = new Scanner(System.in);
		
	public void program() {
		boolean proceed = true;
		while (proceed) {
			System.out.println('\n' + "========================================================");
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Fazer Cadastro");
			System.out.println('\n' + "2. Fazer Login");
			System.out.println('\n' + "3. Voltar");
			
			System.out.println('\n' + "Digite um número entre 1 e 3: ");
			int option = Integer.parseInt(input.nextLine());
			
			UI.clearScreen();
			
			switch (option) {
			case 1 -> {
				System.out.println('\n' + "Insira seu nome: ");
				String name = input.nextLine();
				System.out.println('\n' + "Insira seu cpf (xxx.xxx.xxx-xx): ");
				String cpf = input.nextLine();
				
				UI.clearScreen();
				try {
					Customer myCustomer = new Customer(name, cpf);
					Customer otherCustomer = customerService.retrieveCostumerByCpf(cpf);
					if (otherCustomer != null) {
						System.out.println('\n' + "Cpf já cadastrado!");
					} else {
						customerService.include(myCustomer);
						System.out.println('\n' + "O cliente número " + myCustomer.getId() + " foi cadastrado!");
					}
				} catch (CustomerException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 2 -> {
				System.out.println('\n' + "Insira o cpf: ");
				String cpf = input.nextLine();
				
				Customer myCustomer = customerService.retrieveCostumerByCpf(cpf);
				if (myCustomer == null) {
					UI.clearScreen();
					System.out.println('\n' + "Não há nenhum cadastro com esse cpf!");
				} else {
					boolean prooced2 = true;
					while (prooced2) {
						System.out.println('\n' + "========================================================");
						System.out.println('\n' + "Login feito com sucesso!");
						System.out.println('\n' + "O que você deseja fazer?");
						System.out.println('\n' + "1. Comprar passagem");
						System.out.println('\n' + "2. Excluir passagem");
						System.out.println('\n' + "3. Total de milhas voadas");
						System.out.println('\n' + "4. Voltar");
						
						System.out.println('\n' + "Digite um número entre 1 e 3: ");
						int choice = Integer.parseInt(input.nextLine());
						
						UI.clearScreen();
						switch(choice) {
						case 1 -> {
							System.out.println('\n' + "Nota: Digite 0 em qualquer ocasião para cancelar compra");
							System.out.println('\n' + "Quantos voos essa passagem terá?");
							int number = Integer.parseInt(input.nextLine());
							try {
								if (number == 0) {
									throw new TicketException("Compra cancelada");
								}
								int i = 1;
								SegmentExec lastSegmentExec = null;
								List<SegmentExec> segmentExecs = new ArrayList<>();
								while (i <= number) {
									if (number < 1) {
										UI.clearScreen();
										System.out.println('\n' + "Insira um número válido!");
										break;
									} else if (number == 1) {
										if (flightService.retrieveFlights().isEmpty()) {
											UI.clearScreen();
											System.out.println('\n' + "Não há voos registrados!");
											break;
										}
										if (flightExecService.retrieveFlightExecs().isEmpty()) {
											UI.clearScreen();
											System.out.println('\n' + "Não há execuções de voo registradas!");
											break;
										}
										UI.clearScreen();
										for (Flight flight : flightService.retrieveFlights()) {
											System.out.println(flight);
										}
										System.out.println('\n' + "Escolha o id do voo " + i + " de sua passagem:");
										int id = Integer.parseInt(input.nextLine());
										if (id == 0) {
											throw new TicketException("Compra cancelada");
										}
										Flight flight = flightService.retrieveFlightById(id);
										if (flight != null) {
											if (flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id).isEmpty()) {
												UI.clearScreen();
												System.out.println('\n' + "Não há execuções para o voo escolhido");
											} else {
												UI.clearScreen();
												for (FlightExec flightExec : flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id)) {
													System.out.println(flightExec);
												}
												System.out.println('\n' + "Escolha uma execução para o voo:");
												id = Integer.parseInt(input.nextLine());
												if (id == 0) {
													throw new TicketException("Compra cancelada");
												}
												FlightExec flightExec = flightExecService.retrieveFlightExecById(id);
												if (flightExec != null) {
													for (SegmentExec segmentExec : flightExec.getSegmentExecs()) {
														segmentExecs.add(segmentExec);
													}
													i++;
												} else {
													UI.clearScreen();
													System.out.println('\n' + "Insira um id válido!");
												}
											}
										} else {
											UI.clearScreen();
											System.out.println('\n' + "Insira um id válido!");
										}
									} else {
										if (flightService.retrieveFlights().isEmpty()) {
											UI.clearScreen();
											System.out.println('\n' + "Não há voos registrados!");
											break;
										}
										if (flightExecService.retrieveFlightExecs().isEmpty()) {
											UI.clearScreen();
											System.out.println('\n' + "Não há execuções de voo registradas!");
											break;
										}
										UI.clearScreen();
										for (Flight flight : flightService.retrieveFlights()) {
											System.out.println(flight);
										}
										System.out.println('\n' + "Escolha o id do voo " + i + " de sua passagem:");
										int id = Integer.parseInt(input.nextLine());
										if (id == 0) {
											throw new TicketException("Compra cancelada");
										}
										Flight flight = flightService.retrieveFlightById(id);
										if (i == 1) {
											if (flight != null) {
												if (flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id).isEmpty()) {
													UI.clearScreen();
													System.out.println('\n' + "Não há execuções para o voo escolhido");
												} else {
													UI.clearScreen();
													for (FlightExec flightExec : flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id)) {
														System.out.println(flightExec);
													}
													System.out.println('\n' + "Escolha uma execução para o voo:");
													id = Integer.parseInt(input.nextLine());
													if (id == 0) {
														throw new TicketException("Compra cancelada");
													}
													FlightExec flightExec = flightExecService.retrieveFlightExecById(id);
													if (flightExec != null) {
														for (SegmentExec segmentExec : flightExec.getSegmentExecs()) {
															segmentExecs.add(segmentExec);
														}
														i++;
														lastSegmentExec = flightExec.getSegmentExecs().get(flightExec.getSegmentExecs().size() - 1);
													} else {
														UI.clearScreen();
														System.out.println('\n' + "Insira um id válido!");
													}
												}
											} else {
												UI.clearScreen();
												System.out.println('\n' + "Insira um id válido!");
											}
										} else {
											if (flight != null) {
												if (flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id).isEmpty()) {
													UI.clearScreen();
													System.out.println('\n' + "Não há execuções para o voo escolhido");
												} else {
													UI.clearScreen();
													for (FlightExec flightExec : flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id)) {
														System.out.println(flightExec);
													}
													System.out.println('\n' + "Escolha uma execução para o voo:");
													if (id == 0) {
														throw new TicketException("Compra cancelada");
													}
													System.out.println('\n' + "Nota: Deve estar conectada a execução de voo anterior");
													id = Integer.parseInt(input.nextLine());
													FlightExec flightExec = flightExecService.retrieveFlightExecById(id);
													if (flightExec != null) {
														if (flightExec.getSegmentExecs().get(0).getSegment().getOrigin().equals(lastSegmentExec.getSegment().getDestination())
																&& flightExec.getSegmentExecs().get(0).getInitialDate().isAfter(lastSegmentExec.getFinalDate())) {
															for (SegmentExec segmentExec : flightExec.getSegmentExecs()) {
																segmentExecs.add(segmentExec);
															}
															i++;
															lastSegmentExec = flightExec.getSegmentExecs().get(flightExec.getSegmentExecs().size() - 1);
														} else {
															UI.clearScreen();
															System.out.println('\n' + "Voos não conectados!");
														}
													} else {
														UI.clearScreen();
														System.out.println('\n' + "Insira um id válido!");
													}
												}
											} else {
												UI.clearScreen();
												System.out.println('\n' + "Insira um id válido!");
											}
										}
									}
								}
								if (segmentExecs.isEmpty()) {
									UI.clearScreen();
									System.out.println('\n' + "Não foi possível comprar passagem");
								}
								else {
									Ticket ticket = new Ticket(myCustomer, segmentExecs);
									ticketService.include(ticket);
									myCustomer.getTickets().add(ticket);
									System.out.println('\n' + "Passagem número " + ticket.getId() + " cadastrada com sucesso!");
								}
							} catch (FlightException | FlightExecException | SegmentException | SegmentExecException | TicketException | CustomerException e) {
								UI.clearScreen();
						        System.out.println(e.getMessage());
							}
						}
						case 2 -> {
							System.out.println('\n' + "Informe o id da passagem: ");
							int id = Integer.parseInt(input.nextLine());
							try {
								Ticket myTicket = ticketService.retrieveTicketById(id);
								if (myCustomer.getTickets().contains(myTicket)) {
									ticketService.remove(id);
									myCustomer.getTickets().remove(myTicket);
									System.out.println('\n' + "Passagem número " + id + " removida com sucesso!");
								} else {
									UI.clearScreen();
									System.out.println('\n' + "O cliente não possui a passagem de id: " + id);
								}
							} catch (TicketException e) {
								UI.clearScreen();
						        System.out.println(e.getMessage());
							}
						}
						case 3 -> {
							UI.clearScreen();
							double miles = 0;
							for (Ticket ticket : myCustomer.getTickets()) {
								miles += ticket.getDoubleMiles();
							}
							System.out.println('\n' + "Total de milhas: " + miles);
						}
						case 4 -> {
							prooced2 = false;
						}
						}
					}
			}
			}
			case 3 -> {
				proceed = false;
			}
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
