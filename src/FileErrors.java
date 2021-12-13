
public interface FileErrors {
	int FILE_NOTEXISTS = 1;
	int FILE_NOTAFILE = 2;
	int FILE_ALREADYEXISTS = 3; //Il file esiste già
	int FILE_WRITEERROR = 4; //Errore di scrittura sul file di destinazione
	int FILE_CANTREAD = 5; //Operazioni di lettura non permesse
	int FILE_CANTWRITE = 6; //Operazioni di scrittura non permesse
}
