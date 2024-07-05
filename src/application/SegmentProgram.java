package application;

import java.util.List;
import java.util.Scanner;

import exception.SegmentException;
import model.entities.Flight;
import model.entities.Segment;
import service.FlightService;
import service.SegmentService;

public class SegmentProgram {
	private final FlightService flightService = new FlightService();
	private final SegmentService segmentService = new SegmentService();
	Scanner input = new Scanner(System.in);
	
	public void program() {

		String origin;
		String destination;
		double price;
		double miles;
		Segment mySegment;
		
		boolean proceed = true;
		while (proceed) {
			System.out.println('\n' + "========================================================");
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um trecho");
			System.out.println('\n' + "2. Remover um trecho");
			System.out.println('\n' + "3. Listar todos os trechos");
			System.out.println('\n' + "4. Listar todos os vôos em que um trecho aparece");
			System.out.println('\n' + "5. Voltar");
			
			System.out.println('\n' + "Digite um número entre 1 e 5: ");
			int option = Integer.parseInt(input.nextLine());
			
			UI.clearScreen();
			
			switch (option) {
			case 1 -> { // Register
				System.out.println('\n' + "Informe a origem do trecho: ");
				origin = input.nextLine();
				System.out.println('\n' + "Informe o destino do trecho: ");
				destination = input.nextLine();
				System.out.println('\n' + "Informe o preço do trecho: ");
				price = Double.parseDouble(input.nextLine());
				System.out.println('\n' + "Informe as milhas do trecho: ");
				miles = Double.parseDouble(input.nextLine());
				
				mySegment = new Segment(origin, destination, price, miles);
				try {
		            Segment otherSegment = segmentService.retrieveSegmentByOriginAndDestination(origin, destination);
		            if (mySegment.equals(otherSegment)) {
		                UI.clearScreen();
		                System.out.println("\nTrecho número " + otherSegment.getId() + " já existe." + "\n\n");
		            } else {
		                segmentService.include(mySegment);
		                UI.clearScreen();
		                System.out.println("\nTrecho número " + mySegment.getId() + " cadastrado com sucesso!" + "\n\n");
		            }
		        } catch (SegmentException e) {
		            System.out.println(e.getMessage());
		        }
			}
			case 2 -> { // Remove
				System.out.println('\n' + "Informe o id do trecho: ");
				int id = Integer.parseInt(input.nextLine());
				
				try {
		            List<Flight> allFlights = flightService.retrieveAllFlightsWhereSegmentAppears(id);
		            if (allFlights.isEmpty()) {
		                segmentService.remove(id);
		                UI.clearScreen();
		                System.out.println("\nTrecho número " + id + " removido com sucesso!");
		            } else {
		                UI.clearScreen();
		                System.out.println("\nNão foi possível remover o trecho! Há vôos em que ele aparece.");
		            }
		        } catch (SegmentException e) {
		        	UI.clearScreen();
		            System.out.println(e.getMessage());
		        }
			}
			case 3 -> { // Get all segments
				System.out.println('\n' + "Segue a lista de todos os trechos:" + '\n');
				for (Segment segment : segmentService.retrieveSegments()) {
					System.out.println(segment + "\n");
				}
			}
			case 4 -> { // Get all flights by segment
				System.out.println('\n' + "Informe o número do trecho: ");
				int id = Integer.parseInt(input.nextLine());
				
				UI.clearScreen();
				
				try {
					System.out.println('\n' + "Segue a lista de todos os vôos em que o trecho aparece" + '\n');
					for (Flight flight : flightService.retrieveAllFlightsWhereSegmentAppears(id)) {
						System.out.println(flight + "\n");
					}
				}
				catch (SegmentException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
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
