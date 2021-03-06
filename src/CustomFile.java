import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CustomFile extends File implements FileErrors{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6232248053495656627L;
	public static final int BUFFER_SIZE = 4096;
	public static final String nl = System.lineSeparator();
	
	private String filename; //nome del file
	private int error;
	
	public CustomFile(String file) throws Exception {
		super(file);
		if(this.exists() && !this.isFile()) {
			throw new Exception("Il percorso specificato non appartiene ad un File");
		}
		this.filename = file;
		this.error = 0;
	}
	
	public int getError() {return this.error;}
	public String getFileName() {return this.filename;}
	public void setFileName(String name) {this.filename = name;}
	
	//copia un file e restituisce il puntatore al nuovo file creato
	public CustomFile copy(String dest) throws Exception {
		CustomFile fDest = new CustomFile(dest);
		this.error = 0;
		if(this.exists()) {
			if(this.isFile()) {
				if(fDest.exists()) {
					//se il percorso di destinazione esiste gi?
					this.error = FILE_ALREADYEXISTS;
				}
				else {
					fDest.createNewFile();
					String inputContent = this.fileContent();
					boolean writed = fDest.writeContent(inputContent);
					if(!writed) {
						this.error = FILE_WRITEERROR;
					}
					//copia completata
				}
			}//if(this.isFile()) {
			else {
				this.error = FILE_NOTAFILE;
			}
		}//if(this.exists()) {
		else {
			this.error = FILE_NOTEXISTS;
		}
		return fDest;
	}
	
	//cancella un file
	public boolean deleteFile() {
		boolean ok = false;
		this.error = 0;
		if(this.exists()) {
			if(this.canWrite()) {
				ok = this.delete();
			}//if(this.canWrite()) {
			else {
				this.error = FILE_CANTWRITE;
			}
		}
		else {
			this.error = FILE_NOTEXISTS;
		}
		return ok;
	}
	
	//restituisce il contenuto del file istanziato
	public String fileContent() throws IOException {
		this.error = 0;
		String content = null;
		if(this.exists()) {
			if(this.isFile()) {
				if(this.canRead()) {
					FileReader fr = new FileReader(this);
					BufferedReader br = new BufferedReader(fr);
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					while(line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
					}//while(line != null) {
					content = sb.toString();
					br.close();
					fr.close();
				}//if(this.canRead()) {
				else
					this.error = FILE_CANTREAD;	
			}//if(this.isFile())
			else 
				this.error = FILE_NOTAFILE;
		}//if(this.exists()) {
		else 
			this.error = FILE_NOTEXISTS;
		return content;
	}//public String fileContent() throws IOException {
	
	//restituisce il contenuto del file binario istanziato
	public byte[] fileContentBinary() throws IOException {
		byte[] fileContent = null;
		ByteArrayOutputStream baos = null;
		byte[] fileC = null; //buffer che contiene BUFFER_SIZE bytes letti dallo stream di Input
		this.error = 0;
		if(this.exists()) {
			if(this.isFile()) {
				if(this.canRead()) {
					long size = this.length();
					fileC = new byte[BUFFER_SIZE];
					baos = new ByteArrayOutputStream();
					FileInputStream fis = new FileInputStream(this);
					BufferedInputStream bis = new BufferedInputStream(fis);
					InputStream is = bis;
					while(is.read(fileC) != -1) {
						baos.write(fileC);
					}
					fileContent = baos.toByteArray();
					is.close();
					bis.close();
					fis.close();
					baos.close();
				}//if(this.canRead()) {
				else
					this.error = FILE_CANTREAD;	
			}//if(this.isFile()) {
			else 
				this.error = FILE_NOTAFILE;
		}//if(this.exists()) {
		else 
			this.error = FILE_NOTEXISTS;
		return fileContent;
	}
	
	//Informazioni sul file istanziato
	public String fileInfo() {
		String info = null;
		this.error = 0;
		if(this.exists()) {
			if(this.isFile()) {
				StringBuilder sb = new StringBuilder();
				sb.append("File canonical "+this.getAbsolutePath()).append(nl);
				try {
					sb.append("File "+this.getCanonicalPath()).append(nl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.append("File absolute path "+this.getAbsolutePath()).append(nl);
				sb.append("File path "+this.getPath()).append(nl);
				sb.append("Lunghezza: ").append(this.length()).append(" bytes").append(nl);
				sb.append("Pu? essere eseguito? ").append(this.canExecute()).append(nl);
				sb.append("Pu? essere letto? ").append(this.canRead()).append(nl);
				sb.append("Pu? essere modificato? ").append(this.canWrite()).append(nl);
				sb.append("File nascosto? ").append(this.isHidden()).append(nl);
				sb.append("Ultima modifica: ").append(this.lastModified()).append(nl);
				info = sb.toString();
			}//if(fileP.isFile()) {
			else {
				this.error = FILE_NOTAFILE;
			}
		}//if(fileP.exists()) {
		else {
			this.error = FILE_NOTEXISTS;
		}
		return info;
	}
	
	//scrive del testo sul file istanziato
	public boolean writeContent(String text) throws IOException {
		boolean ok = false;
		this.error = 0;
		if(this.exists() && this.isDirectory()) {
			//se esiste ed ? una cartella
			this.error = FILE_NOTAFILE;
		}
		else {
			if(!this.exists()) {
				this.createNewFile();
			}
			FileWriter fw = new FileWriter(this,true);
			PrintWriter pw = new PrintWriter(fw);
			/*BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);*/
			pw.print(text);
			pw.append(System.lineSeparator());
			pw.close();
			fw.close();
			ok = true;
			
		}//if(this.exists()) {
		return ok;
	}
	
	//scrivi contenuto su un file binario
	public boolean writeContentBinary(byte [] fileContent) throws IOException {
		boolean ok = false;
		this.error = 0;
		if(this.exists() && isDirectory()) {
			//se esiste ed ? una cartella
			this.error = FILE_NOTAFILE;
		}
		else {
			if(!this.exists()) {
				this.createNewFile();
			}
			byte buffer[] = new byte[BUFFER_SIZE];
			ByteArrayInputStream bais = new ByteArrayInputStream(fileContent);
			InputStream is = bais;
			FileOutputStream fos = new FileOutputStream(this,true);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			OutputStream os = bos;
			while(is.read(buffer) != -1) {
				os.write(buffer);
			}
			os.close();
			bos.close();
			fos.close();
			is.close();
			bais.close();
			ok = true;
		}
		return ok;
	}
}
