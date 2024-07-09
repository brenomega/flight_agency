package application;

import java.util.List;
import java.util.Scanner;

import exception.SegmentException;
import exception.SegmentExecException;
import model.entities.Flight;
import model.entities.Segment;
import model.entities.SegmentExec;
import service.FlightService;
import service.SegmentExecService;
import service.SegmentService;

public class SegmentProgram {
	private final FlightService flightService = new FlightService();
	private final SegmentService segmentService = new SegmentService();
	private final SegmentExecService segmentExecService = new SegmentExecService();
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
			System.out.println('\n' + "2. Cadastrar uma execução (Data/Hora) para um trecho");
			System.out.println('\n' + "3. Remover um trecho");
			System.out.println('\n' + "4. Remover uma execução de trecho");
			System.out.println('\n' + "5. Listar todos os trechos");
			System.out.println('\n' + "6. Listar todas as execuções de trecho");
			System.out.println('\n' + "7. Listar todos os vôos em que um trecho aparece");
			System.out.println('\n' + "8. Listar todas execuções para um trecho");
			System.out.println('\n' + "9. Listar todas execuções de trecho entre duas datas");
			System.out.println('\n' + "10. Voltar");
			
			System.out.println('\n' + "Digite um número entre 1 e 10: ");
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
		        	UI.clearScreen();
		            System.out.println(e.getMessage());
		        }
			}
			case 2 -> { // Register execution
				System.out.println('\n' + "Informe o id do trecho: ");
				int id = Integer.parseInt(input.nextLine());
				System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de início: ");
				String initialDate = input.nextLine();
				System.out.println('\n' + "Informe a Data/Hora (dd/MM/yyyy HH:mm) de fim: ");
				String finalDate = input.nextLine();
				
				SegmentExec mySegmentExec = new SegmentExec(initialDate, finalDate);
				try {
					Segment segment = segmentService.retrieveSegmentById(id);
					mySegmentExec.setSegment(segment);
					SegmentExec otherSegmentExec = segmentExecService.retrieveSegmentExecutionByDateTimeAndSegment(initialDate, finalDate, id);
					if (mySegmentExec.equals(otherSegmentExec)) {
						UI.clearScreen();
						System.out.println("\nExecução de Trecho número " + otherSegmentExec.getId() + " já existe." + "\n\n");
					} else {
						segmentExecService.include(mySegmentExec);
						UI.clearScreen();
						System.out.println("\nExecução de trecho número " + mySegmentExec.getId() + " cadastrada com sucesso!" + "\n\n");
					}
				} catch (SegmentException | SegmentExecException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 3 -> { // Remove
				System.out.println('\n' + "Informe o id do trecho: ");
				int id = Integer.parseInt(input.nextLine());
				
				try {
		            List<Flight> allFlights = flightService.retrieveAllFlightsWhereSegmentAppears(id);
		            List<SegmentExec> allSegmentExecutions = segmentExecService.retrieveAllSegmentExecutionsWhereSegmentAppears(id);
		            if (allFlights.isEmpty()) {
		            	if (allSegmentExecutions.isEmpty() ) {
		            		segmentService.remove(id);
		            		UI.clearScreen();
			                System.out.println("\nTrecho número " + id + " removido com sucesso!");
		            	} else {
		            		UI.clearScreen();
		            		System.out.println("\nNão foi possível remover o trecho! Há execuções para ele.");
		            	}
		            } else {
		                UI.clearScreen();
		                System.out.println("\nNão foi possível remover o trecho! Há vôos em que ele aparece.");
		            }
		        } catch (SegmentException | SegmentExecException e) {
		        	UI.clearScreen();
		            System.out.println(e.getMessage());
		        }
			}
			case 4 -> { // Remove execution
				System.out.println('\n' + "Informe o id da execução de trecho: ");
				int id = Integer.parseInt(input.nextLine());
				
				try {
					segmentExecService.remove(id);
					
					UI.clearScreen();
					
					System.out.println("\nExecução de trecho número " + id + " removida com sucesso!");
				} catch (SegmentExecException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 5 -> { // Get all segments
				System.out.println('\n' + "Segue a lista de todos os trechos:" + '\n');
				for (Segment segment : segmentService.retrieveSegments()) {
					System.out.println(segment + "\n");
				}
			}
			case 6 -> { // Get all executions
				System.out.println('\n' + "Segue a lista de todas as execuções de trecho: " + '\n');
				for (SegmentExec segmentExec : segmentExecService.retrieveSegmentExecs()) {
					System.out.println(segmentExec + "\n");
				}
			}
			case 7 -> { // Get all flights by segment
				System.out.println('\n' + "Informe o número do trecho: ");
				int id = Integer.parseInt(input.nextLine());
				
				UI.clearScreen();
				
				try {
					System.out.println('\n' + "Segue a lista de todos os vôos em que o trecho aparece:" + '\n');
					for (Flight flight : flightService.retrieveAllFlightsWhereSegmentAppears(id)) {
						System.out.println(flight + "\n");
					}
				}
				catch (SegmentException e) {
					UI.clearScreen();
					System.out.println(e.getMessage());
				}
			}
			case 8 -> { // Get all executions by segment
				System.out.println('\n' + "Informe o número do trecho: ");
				int id = Integer.parseInt(input.nextLine());
				
				UI.clearScreen();
				
				try {
					System.out.println('\n' + "Segue a lista de todas as execuções do trecho:" + '\n');
					for (SegmentExec segmentExec : segmentExecService.retrieveAllSegmentExecutionsWhereSegmentAppears(id)) {
						System.out.println(segmentExec + "\n");
					}
				} catch (SegmentExecException e) {
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
				
				System.out.println('\n' + "Segue a lista de todas as execuções de trecho entre " + initialDate + " e " + finalDate + ":" + '\n');
				for (SegmentExec segmentExec : segmentExecService.retrieveAllSegmentExecutionsBetweenDates(initialDate, finalDate)) {
					System.out.println(segmentExec + "\n");
				}
			}
			case 10 -> { // Back
				proceed = false;
			}
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
