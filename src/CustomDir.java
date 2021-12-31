import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CustomDir extends File implements DirErrors {
	
	private String pathname;
	private int error;

	public CustomDir(String path) throws Exception {
		super(path);
		if(this.exists() && !this.isDirectory()){
			throw new Exception("Il percorso specificato non appartiene ad una cartella");
		}
		this.pathname = path;
		this.error = 0;
	}
	
	public String getPathName() {return this.pathname;}
	public int getError() {return this.error;}
	
	public void setPathName(String path) {this.pathname = path;}
	
	//copia l'attuale cartella in un percorso a scelta
	public CustomDir copyDir(String dest) throws Exception {
		System.out.println("Dest => "+dest);
		CustomDir cdOut = null;
		this.error = 0;
		if(this.exists()) {
			boolean make = true;
			cdOut = new CustomDir(dest);
			make = cdOut.createDir();
			if(make) {
				//La cartella esiste già o è stata creata con successo
				File[] files = this.listFiles();
				for(File file : files) {
					if(file.canRead()) {
						String nome = file.getName();
						System.out.println("Nome => "+nome);
						//cambia il percorso sorgente
						String srcPath = file.getAbsolutePath();
						String newPath = cdOut.getAbsolutePath()+File.separatorChar+nome;
						System.out.println("srcPath => "+srcPath);
						System.out.println("NewPath => "+newPath);
						File newF = new File(newPath);
						if(file.isFile()) {
							System.out.println("FILE ");
							CustomFile cfIn = new CustomFile(srcPath);
							//Il contenuto del file viene memorizzato in una lista di aray di byte
							byte[] fileContent = cfIn.fileContentBinary();
							if(cfIn.getError() == 0) {
								//scrivo la lista di byte su il file 'newPath'
								CustomFile cfOut = new CustomFile(newPath);
								boolean writed = cfOut.writeContentBinary(fileContent);
								if(writed)
									System.out.println("File copiato");
								else 
									System.out.println("Errore durante la copia del file. Errore N. "+cfOut.getError());
								
							}//if(cfIn.getError() == 0) {
							else {
								System.out.println("Errore durante la lettura del file. Errore N. "+cfIn.getError());
							}
						}//if(file.isFile()) {
						else if(file.isDirectory()) {
							System.out.println(newPath+"  CARTELLA");
							CustomDir cdIn = new CustomDir(srcPath);
							//copio srcPath in newPath
							cdIn.copyDir(newPath);
						}
						else {
							System.out.println("SCONOSCUTO");
						}
					}//if(file.canRead()) {
					else {
						System.out.println("Impossibile leggere il file");
					}
				}//for(File file : files) {
			}//if(make) {
			else {
				System.out.println("Impossibile creare la cartella. Errore N. "+cdOut.getError());
			}
		}//if(this.exists()) {
		else {
			System.out.println("Il percorso sorgente sorgente non esiste");
			this.error = DIR_NOTEXISTS;
		}
		return cdOut;
		
	}
	
	//crea una nuova cartella
	public boolean createDir() {
		boolean ok = false;
		this.error = 0;
		if(!this.exists()) {
			ok = this.mkdirs();
		}//if(!this.exists()) {
		else
			this.error = DIR_ALREADYEXISTS;
		return ok;
	}
	
	//cancella una cartella e il contenuto al suo interno
	public boolean deleteDir() throws Exception {
		boolean ok = false;
		this.error = 0;
		if(this.exists()) {
			if(this.isDirectory()) {
				File[] files = this.listFiles();
				for(File f: files) {
					if(f.isFile()) {
						boolean delF = f.delete();
						if(delF)
							System.out.println("File "+f.getAbsolutePath()+" cancellato");
						else
							System.out.println("File "+f.getAbsolutePath()+" non cancellato");	
					}//if(f.isFile()) {
					else if(f.isDirectory()) {
						System.out.println("Entro in "+f.getAbsolutePath());
						CustomDir dir = new CustomDir(f.getAbsolutePath());
						boolean del = dir.deleteDir();		
					}//else if(f.isDirectory()) {
					else
						System.out.println("BOH");
				}//for(File f: files) {
				ok = true;
				boolean delD = this.delete();
				if(delD)
					System.out.println("Cartella "+this.getAbsolutePath()+" rimossa");
				else
					System.out.println("Cartella "+this.getAbsolutePath()+" non rimossa");
			}//if(this.isDirectory()) {
			else {
				this.error = DIR_NOTADIR;
			}
		}//if(this.exists()) {
		else {
			this.error = DIR_NOTEXISTS;
		}
		return ok;
	}

	//restituisce una lista di file contenuti in una cartella
	public ArrayList<File> filesList() {
		ArrayList<File> fList = null;
		this.error = 0;
		if(this.exists()) {
			if(this.isDirectory()) {
				if(this.canRead()) {
					fList = new ArrayList<>();
					File[] files = this.listFiles();
					for(File f : files) {
						if(f.getName() != "." && f.getName() != "..") {
							fList.add(f);
						}
					}
				}//if(this.canRead()) {
				else
					this.error = DIR_CANTREAD;
			}//if(this.isDirectory()) {
			else 
				this.error = DIR_NOTADIR;
		}//if(this.exists()) {
		else 
			this.error = DIR_NOTEXISTS;	
		return fList;
		
	}

}
