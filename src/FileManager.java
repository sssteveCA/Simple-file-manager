import java.util.Scanner;

public class FileManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String azione = "";
		Scanner scanner = new Scanner(System.in);
		while(azione.toUpperCase() != "ESC") {
			Utils.menu();
			azione = scanner.next();
			azione = azione.toUpperCase();
			switch(azione) {
			case "A": //Apri un file di testo
				Utils.readFile(scanner);
				break;
			case "B": //Scrivi su un file di testo
				Utils.writeFile(scanner);
				break;
			case "C": //Vedi informazioni di un file
				Utils.fileInfo(scanner);
				scanner.nextLine();
				break;
			case "D": //Elimina un file
				Utils.deleteFile(scanner);
				break;
			case "E": //Copia un file
				Utils.fileCopy(scanner);
				break;
			case "F": //Sposta un file
				Utils.fileMove(scanner);
				break;
			case "G": //Apri un file binario
				Utils.readFileBinary(scanner);
				break;
			case "H"://Scrivi su un file binario
				Utils.writeFileBinary(scanner);
				break;
			case "I": //Leggi il contenuto della cartella
				Utils.readDir(scanner);
				break;
			case "J": //Elimina una cartella
				Utils.deleteDir(scanner);
				break;
			case "K":
				Utils.copyDir(scanner);
				break;
			case "ESC":
				scanner.close();
				System.exit(0);
				break;
			default:
				break;
			}
		}//while(azione.toUpperCase() != "ESC") {
		scanner.close();
	}

}
