import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
	
	//public static Scanner scan = new Scanner(System.in);
	public static String nl = System.lineSeparator();
	
	public static void menu() {
		System.out.println("");
		System.out.println("A. Apri un file di testo");
		System.out.println("B. Scrivi su un file di testo");
		System.out.println("C. Vedi informazioni di un file");
		System.out.println("D. Elimina un file");
		System.out.println("E. Copia un file");
		System.out.println("F. Sposta un file");
		System.out.println("G. Apri un file binario");
		System.out.println("H. Scrivi su un file binario");
		System.out.println("I. Leggi il contenuto di una cartella");
		System.out.println("J. Elimina una cartella e il suo contenuto");
		System.out.println("ESC. Esci dal programma");
		System.out.println("");
		System.out.print("Azione da eseguire => ");
	}
	
	//elimina una cartella
	public static void deleteDir(Scanner scan) {
		System.out.println("");
		System.out.println("Cartella da cancellare => ");
		String path = scan.next();
		CustomDir cd = new CustomDir(path);
		boolean del = cd.deleteDir();
		if(del)
			System.out.println("Cartella rimossa");
		else
			System.err.println("Errore durante la rimozione della cartella. N. "+cd.getError());
		System.out.println("");
		continua(scan);
	}
	
	//elimina un file
	public static void deleteFile(Scanner scan) {
		System.out.println("");
		System.out.print("File da cancellare => ");
		String path = scan.next();
		CustomFile cf = new CustomFile(path);
		boolean del = cf.deleteFile();
		if(del) 
			System.out.println("Il file è stato eliminato");
		else
			System.err.println("Errore durante l'eliminazione del file. N. "+cf.getError());
		System.out.println("");
		continua(scan);
	}
	
	//copia un file
	public static void fileCopy(Scanner scan) {
		System.out.println("");
		System.out.print("percorso sorgente => ");
		String source = scan.next();
		System.out.println("percorso di destinazione =>");
		String dest = scan.next();
		CustomFile sorgente = new CustomFile(source);
		try {
			CustomFile destin = sorgente.copy(dest);
			if(sorgente.getError() == 0) {
				System.out.println("Copia del file completata");
			}
			else {
				System.err.println("Errore durante la copia del file.");
				//System.err.println("Errore sorgente: "+sorgente.getError()+" Errore destinazione: "+destin.getError());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		continua(scan);
	}
	
	//Informazioni sul file scelto
	public static void fileInfo(Scanner scan) {
		//scan = new Scanner(System.in);
		System.out.println("");
		System.out.println("Percorso del file => ");
		String path = scan.next();
		CustomFile cf = new CustomFile(path);
		String fInfo = cf.fileInfo();
		if(fInfo != null) 
			System.out.println(fInfo);
		else 
			System.err.println("Errore durante la lettura delle informazioni del file. N. "+cf.getError());
		System.out.println("");
		continua(scan);
		//scan.close();
	}
	
	//sposta un file
	public static void fileMove(Scanner scan) {
		System.out.println("");
		System.out.print("percorso sorgente => ");
		String source = scan.next();
		System.out.println("percorso di destinazione =>");
		String dest = scan.next();
		CustomFile sorgente = new CustomFile(source);
		try {
			CustomFile destin = sorgente.copy(dest);
			if(sorgente.getError() == 0) {
				boolean delSrc = sorgente.deleteFile();
				if(delSrc)
					System.out.println("Il file è stato spostato con successo");
				else
					System.err.println("Impossibile cancellare il file sorgente. Errore N. "+sorgente.getError());
			}
			else {
				System.err.println("Errore durante lo spostamento del file.");
				System.err.println("Errore sorgente: "+sorgente.getError()+" Errore destinazione: "+destin.getError());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		continua(scan);
	}
	
	//leggi il contenuto di una cartella
	public static void readDir(Scanner scan) {
		System.out.println("");
		System.out.println("Cartella da aprire => ");
		String path = scan.next();
		CustomDir cd = new CustomDir(path);
		ArrayList<File> dirList = new ArrayList<>();
		dirList = cd.filesList();
		if(dirList != null) {
			for(int i = 0; i < dirList.size(); i++) {
				File file = dirList.get(i);
				String tipo = "";
				if(file.isFile())
					tipo = "FILE";
				else if(file.isDirectory())
					tipo = "CARTELLA";
				else
					tipo = "SCONOSCIUTO";
				System.out.println(file.getName()+" => "+tipo);
			}//for(int i = 0; i < dirList.size(); i++) {
		}//if(dirList != null) {
		else
			System.err.println("Errore durante l'apertura della cartella. N. "+cd.getError());
		System.out.println("");
		continua(scan);
	}
	
	//leggi il contenuto di un file
	public static void readFile(Scanner scan) {
		//scan = new Scanner(System.in);
		System.out.println("");
		System.out.print("File da aprire => ");
		String path = scan.next();
		CustomFile cf = new CustomFile(path);
		try {
			String fileC = cf.fileContent();
			if(fileC != null) {
				System.out.println("");
				System.out.println(fileC);
			}
			else 
				System.err.println("Errore durante la lettura del file. N. "+cf.getError());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		continua(scan);
	}
	
	//leggi un file binario
	public static void readFileBinary(Scanner scan) {
		System.out.println("");
		System.out.println("File da aprire => ");
		String path = scan.next();
		CustomFile cf = new CustomFile(path);
		try {
			List<byte []>binC = cf.fileContentBinary();
			if(binC != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				for(int j = 0; j < binC.size(); j++) {
					byte []buf = binC.get(j);
					baos.write(buf);
				}
				String strC = baos.toString();
				System.out.println(strC);
				baos.close();
			}//if(binC != null) {
			else 
				System.err.println("Errore durante la lettura del file. N"+cf.getError());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		continua(scan);
	}
	
	public static void writeFile(Scanner scan) {
		//scan = new Scanner(System.in);
		System.out.println("");
		System.out.print("File da aprire in scrittura => ");
		String path = scan.next();
		scan.nextLine();
		System.out.print("Testo da scrivere => ");
		String fileText = scan.nextLine();
		CustomFile cf = new CustomFile(path);
		boolean writed = false;;
		try {
			writed = cf.writeContent(fileText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(writed)System.out.println("Scrittura sul file completata");
		else System.err.println("Errore durante la scrittura sul file. Codice: "+cf.getError());
		System.out.println("");
		continua(scan);
	}
	
	public static void writeFileBinary(Scanner scan) {
		System.out.println("");
		System.out.println("File su cui scrivere => ");
		String pathOutput = scan.next();
		System.out.println("File da aggiungere al primo => ");
		String pathInput = scan.next();
		CustomFile cfOutput = new CustomFile(pathOutput);
		CustomFile cfInput = new CustomFile(pathInput);
		try {
			List <byte []> binaryCont = cfInput.fileContentBinary();
			if(binaryCont != null) {
				boolean wr = cfOutput.writeContentBinary(binaryCont);
				if(wr == true) {
					System.out.println("Scrittura completata");
				}
				else {
					System.err.println("Errore durante la scrittura. Errore N. "+cfOutput.getError());
				}
			}//if(binaryCont != null) {
			else {
				System.err.println("Errore durante la lettura del file "+cfInput.getAbsolutePath());
				System.err.println("Errore N. "+cfInput.getError());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Premi Invio per continuare
	public static void continua(Scanner scan) {
		System.out.print("Premi Invio per continuare...");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
