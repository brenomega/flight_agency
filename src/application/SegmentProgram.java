package application;

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
		
		boolean proceed = true;
		while (proceed) {
			System.out.println('\n' + "========================================================");
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Listar todos os trechos");
			System.out.println('\n' + "2. Listar todas as execuções de trecho");
			System.out.println('\n' + "3. Listar todos os vôos em que um trecho aparece");
			System.out.println('\n' + "4. Listar todas execuções para um trecho");
			System.out.println('\n' + "5. Listar todas execuções de trecho entre duas datas");
			System.out.println('\n' + "6. Voltar");
			
			System.out.println('\n' + "Digite um número entre 1 e 6: ");
			int option = Integer.parseInt(input.nextLine());
			
			UI.clearScreen();
			
			switch (option) {
			case 1 -> { // Get all segments
				System.out.println('\n' + "Segue a lista de todos os trechos:" + '\n');
				for (Segment segment : segmentService.retrieveSegments()) {
					System.out.println(segment + "\n");
				}
			}
			case 2 -> { // Get all executions
				System.out.println('\n' + "Segue a lista de todas as execuções de trecho: " + '\n');
				for (SegmentExec segmentExec : segmentExecService.retrieveSegmentExecs()) {
					System.out.println(segmentExec + "\n");
				}
			}
			case 3 -> { // Get all flights by segment
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
			case 4 -> { // Get all executions by segment
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
			case 5 -> { // Get all executions between dates
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
			case 6 -> { // Back
				proceed = false;
			}
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
