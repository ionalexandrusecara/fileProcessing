
public class FileIsCorrupt extends Exception{
	public FileIsCorrupt(){
		
	}
	
	public String getMessage(){
		return "File is corrupt or not well formatted!";
	}
}
