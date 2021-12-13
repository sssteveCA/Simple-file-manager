import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class CustomDir extends File implements DirErrors {
	
	private String pathname;
	private int error;

	public CustomDir(String path) {
		super(path);
		this.pathname = path;
		this.error = 0;
	}
	
	public String getPathName() {return this.pathname;}
	public int getError() {return this.error;}
	
	public void setPathName(String path) {this.pathname = path;}
	
	//crea una nuova cartella
	public boolean createDir() {
		boolean ok = false;
		this.error = 0;
		if(!this.exists()) {
			File parent = this.getParentFile();
			if(this.canWrite()) {
				ok = this.mkdirs();
			}//if(this.canWrite()) {
			else {
				this.error = DIR_CANTWRITE;
			}
		}//if(!this.exists()) {
		else
			this.error = DIR_ALREADYEXISTS;
		return ok;
	}
	
	//cancella una cartella e il contenuto al suo interno
	public boolean deleteDir() {
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
