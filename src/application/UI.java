package application;

public class UI {

	public static void clearScreen() {
		if (System.console() != null && System.getenv().get("TERM") != null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
	}
}
