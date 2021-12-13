
public interface DirErrors {
	int DIR_NOTEXISTS = 1;
	int DIR_NOTADIR = 2;
	int DIR_ALREADYEXISTS = 3; //la cartella specificata esiste già
	int DIR_CANTREAD = 4; //operazioni di lettura non permesse
	int DIR_CANTWRITE = 5; //operazioni di scrittura non permesse
}
