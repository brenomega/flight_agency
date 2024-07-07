package application;

import java.util.Scanner;

import dao.FlightDao;
import dao.SegmentDao;
import dao.SegmentExecDao;
import model.entities.Flight;
import model.entities.Segment;
import model.entities.SegmentExec;
import util.DaoFactory;

import java.io.*;
import java.util.Map;

public class Program {

	public static void main(String[] args) {
		
		FlightProgram flightProgram = new FlightProgram();
		SegmentProgram segmentProgram = new SegmentProgram();
		
		UI.clearScreen();
		
		retrieveData();
		
		Scanner input = new Scanner(System.in);
		
		
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
				saveData();
			}
			
			default -> System.out.println('\n' + "Opção inválida!");
			}
		}
		input.close();
	}
	
	@SuppressWarnings("unchecked")
	private static void retrieveData() {
	    FlightDao flightDao = DaoFactory.getDao(FlightDao.class);
	    SegmentDao segmentDao = DaoFactory.getDao(SegmentDao.class);
	    SegmentExecDao segmentExecDao = DaoFactory.getDao(SegmentExecDao.class);
	    File file = new File("myObjects.txt");

	    if (!file.exists() || file.length() == 0) {
	        System.out.println("The file myObjects.txt does not exist or is empty.");
	        return;
	    }

	    ObjectInputStream ois = null;

	    try {
	        FileInputStream fis = new FileInputStream(file);
	        ois = new ObjectInputStream(fis);

	        Map<Integer, Flight> flightsMap = (Map<Integer, Flight>) ois.readObject();
	        flightDao.setMap(flightsMap);

	        Integer flightCounter = (Integer) ois.readObject();
	        flightDao.setCounter(flightCounter);

	        Map<Integer, Segment> segmentsMap = (Map<Integer, Segment>) ois.readObject();
	        segmentDao.setMap(segmentsMap);

	        Integer segmentCounter = (Integer) ois.readObject();
	        segmentDao.setCounter(segmentCounter);
	        
	        Map<Integer, SegmentExec> segmentExecsMap = (Map<Integer, SegmentExec>) ois.readObject();
	        segmentExecDao.setMap(segmentExecsMap);
	        
	        Integer segmentExecCounter = (Integer) ois.readObject();
	        segmentExecDao.setCounter(segmentExecCounter);

	        System.out.println("Data retrieved successfully.");

	    } catch (FileNotFoundException e) {
	        System.out.println("The file myObjects.txt was not found.");
	    } catch (IOException e) {
	        throw new RuntimeException("Error retrieving data", e);
	    } catch (ClassNotFoundException e) {
	        throw new RuntimeException("Class not found during data retrieval", e);
	    } finally {
	        if (ois != null) {
	            try {
	                ois.close();
	            } catch (IOException e) {
	                System.out.println("Error closing ObjectInputStream: " + e.getMessage());
	            }
	        }
	    }
	}
	
	private static void saveData() {
	    FlightDao flightDao = DaoFactory.getDao(FlightDao.class);
	    SegmentDao segmentDao = DaoFactory.getDao(SegmentDao.class);
	    SegmentExecDao segmentExecDao = DaoFactory.getDao(SegmentExecDao.class);
	    Map<Integer, Flight> flightsMap = flightDao.getMap();
	    Map<Integer, Segment> segmentsMap = segmentDao.getMap();
	    Map<Integer, SegmentExec> segmentExecsMap = segmentExecDao.getMap();
	    Integer flightCounter = flightDao.getCounter();
	    Integer segmentCounter = segmentDao.getCounter();
	    Integer segmentExecCounter = segmentExecDao.getCounter();

	    try (FileOutputStream fos = new FileOutputStream(new File("myObjects.txt"));
	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	        oos.writeObject(flightsMap);
	        oos.writeObject(flightCounter);
	        oos.writeObject(segmentsMap);
	        oos.writeObject(segmentCounter);
	        oos.writeObject(segmentExecsMap);
	        oos.writeObject(segmentExecCounter);
	        
	        System.out.println("Data saved successfully.");
	        
	    } catch (IOException e) {
	        throw new RuntimeException("Error saving data", e);
	    }
	}
}
