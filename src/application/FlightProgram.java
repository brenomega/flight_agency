package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.FlightException;
import exception.SegmentException;
import model.entities.Flight;
import model.entities.Segment;
import service.FlightService;
import service.SegmentService;

public class FlightProgram {

	private final FlightService flightService = new FlightService();
	private final SegmentService segmentService = new SegmentService();
	Scanner input = new Scanner(System.in);
	
	public void program() {

		String origin;
		String destination;
		Flight myFlight;
		
		boolean proceed = true;
		while (proceed) {
			System.out.println('\n' + "========================================================");
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um vôo");
			System.out.println('\n' + "2. Remover um vôo");
			System.out.println('\n' + "3. Listar todos os trechos de um vôo");
			System.out.println('\n' + "4. Listar todos os vôos");
			System.out.println('\n' + "5. Voltar");
			
			System.out.println('\n' + "Digite um número entre 1 e 5: ");
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
			case 2 -> { // Remove
				System.out.println('\n' + "Informe o id do vôo: ");
				int id = Integer.parseInt(input.nextLine());
				
				try {
					flightService.remove(id);
					
					UI.clearScreen();
					
					System.out.println("\nVôo número " + id + " removido com sucesso!");
				}
				catch (FlightException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 3 -> { // All flight segments
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
			case 4 -> { // All flights
				System.out.println('\n' + "Segue a lista de todos os vôos:" + '\n');
				for (Flight flight : flightService.retrieveFlights()) {
					System.out.println(flight + "\n");
				}
			}
			case 5 -> { // Back
				proceed = false;
			}
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
