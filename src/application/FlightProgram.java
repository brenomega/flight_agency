package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.FlightException;
import exception.FlightExecException;
import exception.SegmentException;
import exception.SegmentExecException;
import model.entities.Flight;
import model.entities.FlightExec;
import model.entities.Segment;
import model.entities.SegmentExec;
import service.FlightExecService;
import service.FlightService;
import service.SegmentExecService;
import service.SegmentService;

public class FlightProgram {

	private final FlightService flightService = new FlightService();
	private final SegmentService segmentService = new SegmentService();
	private final FlightExecService flightExecService = new FlightExecService();
	private final SegmentExecService segmentExecService = new SegmentExecService();
	Scanner input = new Scanner(System.in);
	
	public void program() {

		String origin;
		String destination;
		Flight myFlight;
		
		boolean proceed = true;
		while (proceed) {
			System.out.println('\n' + "========================================================");
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um voo");
			System.out.println('\n' + "2. Cadastrar uma execução (Data/Hora) para um voo");
			System.out.println('\n' + "3. Remover um voo");
			System.out.println('\n' + "4. Remover uma execução de voo");
			System.out.println('\n' + "5. Listar todos os trechos de um voo");
			System.out.println('\n' + "6. Listar todos os voos");
			System.out.println('\n' + "7. Listar todas as execuções de voo");
			System.out.println('\n' + "8. Listar todas as execuções para um voo");
			System.out.println('\n' + "9. Listar todas execuções de voo entre duas datas");
			System.out.println('\n' + "10. Listar todas as execuções de trecho de uma execução de voo");
			System.out.println('\n' + "11. Voltar");
			
			System.out.println('\n' + "Digite um número entre 1 e 11: ");
			int option = Integer.parseInt(input.nextLine());
			
			UI.clearScreen();
			
			switch (option) {
			case 1 -> { // Register
				System.out.println('\n' + "Informe a origem do vôo: ");
				origin = input.nextLine();
				System.out.println('\n' + "Informe o destino do vôo: ");
				destination = input.nextLine();
				
				UI.clearScreen();
				
				try {
					myFlight = new Flight(origin, destination);
					String lastDestination = origin;
					int count = 1;
					String segmentDestination;
					double segmentPrice;
					double segmentMile;
					Segment mySegment;
					List<Segment> segments = new ArrayList<Segment>();
					while (!lastDestination.equals(destination)) {
						System.out.println('\n' + "Informe o destino do trecho " + count + ": ");
						segmentDestination = input.nextLine();
						System.out.println('\n' + "Informe o preço do trecho " + count + ": ");
						segmentPrice = Double.parseDouble(input.nextLine());
						System.out.println('\n' + "Informe as milhas do trecho " + count + ": ");
						segmentMile = Double.parseDouble(input.nextLine());
						mySegment = new Segment(lastDestination, segmentDestination, segmentPrice, segmentMile);
					
						Segment otherSegment = segmentService.retrieveSegmentByOriginAndDestination(lastDestination, segmentDestination);
						if (mySegment.equals(otherSegment)) {
							segments.add(otherSegment);
							UI.clearScreen();
							System.out.println("\nTrecho número " + otherSegment.getId() + " já existe." + "\n\n");
						} else {
							segmentService.include(mySegment);
							segments.add(mySegment);
							UI.clearScreen();
							System.out.println("\nTrecho número " + mySegment.getId() + " cadastrado com sucesso!" + "\n\n");
						}

						lastDestination = segmentDestination;
						count++;
					}
					myFlight.setSegments(segments);
				
					Flight otherFlight = flightService.retrieveFlightByOriginDestinationAndSegments(origin, destination, segments);
					if (myFlight.equals(otherFlight)) {
						System.out.println("\nVôo número " + otherFlight.getId() + " já existe.");
					} else {
						flightService.include(myFlight);
						System.out.println("\nVôo número " + myFlight.getId() + " cadastrado com sucesso!");
					}
				}
				catch (SegmentException | FlightException e) {
					System.out.println(e.getMessage());
				}
			}
			case 2 -> { // Register execution
			    System.out.println('\n' + "Informe o id do vôo: ");
			    int id = Integer.parseInt(input.nextLine());
			    System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de início: ");
			    String initialDate = input.nextLine();
			    System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de fim: ");
			    String finalDate = input.nextLine();

			    UI.clearScreen();

			    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			    FlightExec myFlightExec = new FlightExec(initialDate, finalDate);
			    try {
			        Flight flight = flightService.retrieveFlightById(id);
			        myFlightExec.setFlight(flight);

			        String lastDate = initialDate;
			        int count = 1;
			        String initialSegmentDate;
			        String finalSegmentDate;
			        SegmentExec mySegmentExec;
			        List<SegmentExec> segmentExecs = new ArrayList<>();

			        if (flight.getSegments().size() == 1) {
			            UI.clearScreen();
			            System.out.println('\n' + "Atribuindo Data/Hora inicial e final do vôo para seu único trecho...");
			            mySegmentExec = new SegmentExec(initialDate, finalDate);
			            Segment mySegment = segmentService.retrieveSegmentById(flight.getSegments().get(0).getId());
			            mySegmentExec.setSegment(mySegment);
			            SegmentExec otherSegmentExec = segmentExecService.retrieveSegmentExecutionByDateTimeAndSegment(initialDate, finalDate, flight.getSegments().get(0).getId());
			            if (mySegmentExec.equals(otherSegmentExec)) {
			                segmentExecs.add(otherSegmentExec);
			                UI.clearScreen();
			                System.out.println("\nExecução de Trecho número " + otherSegmentExec.getId() + " já existe." + "\n\n");
			            } else {
			                segmentExecs.add(mySegmentExec);
			                segmentExecService.include(mySegmentExec);
			                UI.clearScreen();
			                System.out.println("\nExecução de trecho número " + mySegmentExec.getId() + " cadastrada com sucesso!" + "\n\n");
			            }
			        } else {
			            for (Segment segment : flight.getSegments()) {
			                if (count == 1) {
			                    System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de fim do primeiro trecho do vôo: ");
			                    System.out.println('\n' + "Nota: a Data/Hora de início do primeiro trecho do vôo é a mesma do próprio vôo. ");
			                    finalSegmentDate = input.nextLine();
			                    if (LocalDateTime.parse(finalSegmentDate, dateTimeFormatter).isAfter(LocalDateTime.parse(finalDate, dateTimeFormatter))) {
			                        throw new FlightExecException("Segment Date/Time cannot be outside the flight start/end range!");
			                    }
			                    if (finalSegmentDate.equals(finalDate)) {
			                        throw new FlightExecException("Only the final date of the last segment can be the same as the final date of the flight!");
			                    }
			                    mySegmentExec = new SegmentExec(initialDate, finalSegmentDate);
			                    Segment mySegment = segmentService.retrieveSegmentById(segment.getId());
			                    mySegmentExec.setSegment(mySegment);
			                    SegmentExec otherSegmentExec = segmentExecService.retrieveSegmentExecutionByDateTimeAndSegment(initialDate, finalSegmentDate, segment.getId());
			                    if (mySegmentExec.equals(otherSegmentExec)) {
			                        segmentExecs.add(otherSegmentExec);
			                        UI.clearScreen();
			                        System.out.println("\nExecução de Trecho número " + otherSegmentExec.getId() + " já existe." + "\n\n");
			                    } else {
			                        segmentExecs.add(mySegmentExec);
			                        segmentExecService.include(mySegmentExec);
			                        UI.clearScreen();
			                        System.out.println("\nExecução de trecho número " + mySegmentExec.getId() + " cadastrada com sucesso!" + "\n\n");
			                    }
			                    lastDate = finalSegmentDate;
			                    count++;
			                } else if (count == flight.getSegments().size()) {
			                    System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de início do último trecho do vôo: ");
			                    System.out.println('\n' + "Nota: a Data/Hora de fim do último trecho do vôo é a mesma do próprio vôo. ");
			                    initialSegmentDate = input.nextLine();
			                    if (LocalDateTime.parse(initialSegmentDate, dateTimeFormatter).isAfter(LocalDateTime.parse(finalDate, dateTimeFormatter))) {
			                        throw new FlightExecException("Segment Date/Time cannot be outside the flight start/end range!");
			                    }
			                    if (LocalDateTime.parse(initialSegmentDate, dateTimeFormatter).isBefore(LocalDateTime.parse(lastDate, dateTimeFormatter))) {
			                        throw new FlightExecException("Segment initial date cannot be less than that of its predecessor!");
			                    }
			                    mySegmentExec = new SegmentExec(initialSegmentDate, finalDate);
			                    Segment mySegment = segmentService.retrieveSegmentById(segment.getId());
			                    mySegmentExec.setSegment(mySegment);
			                    SegmentExec otherSegmentExec = segmentExecService.retrieveSegmentExecutionByDateTimeAndSegment(initialSegmentDate, finalDate, segment.getId());
			                    if (mySegmentExec.equals(otherSegmentExec)) {
			                        segmentExecs.add(otherSegmentExec);
			                        UI.clearScreen();
			                        System.out.println("\nExecução de Trecho número " + otherSegmentExec.getId() + " já existe." + "\n\n");
			                    } else {
			                        segmentExecs.add(mySegmentExec);
			                        segmentExecService.include(mySegmentExec);
			                        UI.clearScreen();
			                        System.out.println("\nExecução de trecho número " + mySegmentExec.getId() + " cadastrada com sucesso!" + "\n\n");
			                    }
			                } else {
			                    System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de início do trecho " + count + " do vôo: ");
			                    initialSegmentDate = input.nextLine();
			                    System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de fim do trecho " + count + " do vôo: ");
			                    finalSegmentDate = input.nextLine();
			                    if (LocalDateTime.parse(initialSegmentDate, dateTimeFormatter).isBefore(LocalDateTime.parse(lastDate, dateTimeFormatter))) {
			                        throw new FlightExecException("Segment initial date cannot be less than that of its predecessor!");
			                    }
			                    if (LocalDateTime.parse(finalSegmentDate, dateTimeFormatter).isBefore(LocalDateTime.parse(initialSegmentDate, dateTimeFormatter))) {
			                        throw new FlightExecException("Final date cannot be before initial date!");
			                    }
			                    if (LocalDateTime.parse(finalSegmentDate, dateTimeFormatter).isAfter(LocalDateTime.parse(finalDate, dateTimeFormatter))) {
			                        throw new FlightExecException("Segment Date/Time cannot be outside the flight start/end range!");
			                    }
			                    if (finalSegmentDate.equals(finalDate)) {
			                        throw new FlightExecException("Only the final date of the last segment can be the same as the final date of the flight!");
			                    }
			                    mySegmentExec = new SegmentExec(initialSegmentDate, finalSegmentDate);
			                    Segment mySegment = segmentService.retrieveSegmentById(segment.getId());
			                    mySegmentExec.setSegment(mySegment);
			                    SegmentExec otherSegmentExec = segmentExecService.retrieveSegmentExecutionByDateTimeAndSegment(initialSegmentDate, finalSegmentDate, segment.getId());
			                    if (mySegmentExec.equals(otherSegmentExec)) {
			                        segmentExecs.add(otherSegmentExec);
			                        UI.clearScreen();
			                        System.out.println("\nExecução de Trecho número " + otherSegmentExec.getId() + " já existe." + "\n\n");
			                    } else {
			                        segmentExecs.add(mySegmentExec);
			                        segmentExecService.include(mySegmentExec);
			                        UI.clearScreen();
			                        System.out.println("\nExecução de trecho número " + mySegmentExec.getId() + " cadastrada com sucesso!" + "\n\n");
			                    }
			                    lastDate = finalSegmentDate;
			                    count++;
			                }
			            }
			        }
			        myFlightExec.setSegmentExecs(segmentExecs);
			        FlightExec otherFlightExec = flightExecService.retrieveFlightExecutionByDateTimeAndFlight(initialDate, finalDate, id);
			        if (myFlightExec.equals(otherFlightExec)) {
			            System.out.println("\nExecução de Vôo número " + otherFlightExec.getId() + " já existe." + "\n\n");
			        } else {
			            flightExecService.include(myFlightExec);
			            System.out.println("\nExecução de vôo número " + myFlightExec.getId() + " cadastrada com sucesso!" + "\n\n");
			        }
			    } catch (FlightException | SegmentExecException | FlightExecException e) {
			        UI.clearScreen();
			        System.out.println(e.getMessage());
			    }
			}
			case 3 -> { // Remove
				System.out.println('\n' + "Informe o id do vôo: ");
				int id = Integer.parseInt(input.nextLine());
				
				try {
					List<FlightExec> allFlightExecs = flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id);
					if (allFlightExecs.isEmpty()) {
						flightService.remove(id);
						UI.clearScreen();
						System.out.println("\nVôo número " + id + " removido com sucesso!");
					} else {
						System.out.println("\nNão foi possível remover o voo! Há execuções para ele.");
					}
				}
				catch (FlightException | FlightExecException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 4 -> { // Remove execution
				System.out.println('\n' + "Informe o id da execução de voo: ");
				int id = Integer.parseInt(input.nextLine());
				
				try {
					flightExecService.remove(id);
					
					UI.clearScreen();
					
					System.out.println("\nExecução de voo número " + id + " removida com sucesso!");
				} catch (FlightExecException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 5 -> { // All flight segments
				System.out.println('\n' + "Informe o número do vôo: ");
				int id = Integer.parseInt(input.nextLine());
				
				UI.clearScreen();
				
				try {
					System.out.println('\n' + "Os trechos do vôo são:" + '\n');
					for (Segment segment : flightService.retrieveAllFlightSegments(id)) {
						System.out.println(segment + "\n");
					}
				}
				catch (FlightException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 6 -> { // All flights
				System.out.println('\n' + "Segue a lista de todos os vôos:" + '\n');
				for (Flight flight : flightService.retrieveFlights()) {
					System.out.println(flight + "\n");
				}
			}
			case 7 -> { // All executions
				System.out.println('\n' + "Segue a lista de todas as execuções de voo: " + '\n');
				for (FlightExec flightExec : flightExecService.retrieveFlightExecs()) {
					System.out.println(flightExec + "\n");
				}
			}
			case 8 -> { // Get all executions by flight
				System.out.println('\n' + "Informe o número do voo: ");
				int id = Integer.parseInt(input.nextLine());
				
				UI.clearScreen();
				
				try {
					System.out.println('\n' + "Segue a lista de todas as execuções do voo:" + '\n');
					for (FlightExec flightExec : flightExecService.retrieveAllFlightExecutionsWhereFlightAppears(id)) {
						System.out.println(flightExec + "\n");
					}
				} catch (FlightExecException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 9 -> { // Get all executions between dates
				System.out.println('\n' + "Informe a primeira data (dd/MM/yyyy): ");
				String initialDate = input.nextLine();
				System.out.println('\n' + "Informe a última data (dd/MM/yyyy): ");
				String finalDate = input.nextLine();
				
				UI.clearScreen();
				
				System.out.println('\n' + "Segue a lista de todas as execuções de voo entre " + initialDate + " e " + finalDate + ":" + '\n');
				for (FlightExec flightExec : flightExecService.retrieveAllFlightExecutionsBetweenDates(initialDate, finalDate)) {
					System.out.println(flightExec + "\n");
				}
			}
			case 10 -> { // All flight execution segment executions
				System.out.println('\n' + "Informe o número da execução de voo: ");
				int id = Integer.parseInt(input.nextLine());
				
				UI.clearScreen();
				
				try {
					System.out.println('\n' + "As execuções de trecho da execução voo são:" + '\n');
					for (SegmentExec segmentExec : flightExecService.retrieveAllFlightExecSegmentExecs(id)) {
						System.out.println(segmentExec + "\n");
					}
				}
				catch (FlightExecException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 11 -> { // Back
				proceed = false;
			}
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
