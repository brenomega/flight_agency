package application;

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		
		FlightProgram flightProgram = new FlightProgram();
		SegmentProgram segmentProgram = new SegmentProgram();
		Scanner input = new Scanner(System.in);
		
		UI.clearScreen();
		
		boolean proceed = true;
		while (proceed) {
			System.out.println('\n' + "========================================================");
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Tratar Voos");
			System.out.println('\n' + "2. Tratar Trechos");
			System.out.println('\n' + "3. Sair");
			
			System.out.println('\n' + "Digite um número entre 1 e 3:");
			int option = Integer.parseInt(input.nextLine());
			
			UI.clearScreen();
			
			switch (option) {
			case 1 -> {
				flightProgram.program();
			}
			case 2 -> {
				segmentProgram.program();
			}
			case 3 -> {
				proceed = false;
			}
			
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
		input.close();
	}
}
