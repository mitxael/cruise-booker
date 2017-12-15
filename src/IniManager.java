
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


public class IniManager {

	public static String readINIFile(String filename, String field) { //"config.ini"
		File ini_file = new File(System.getProperty("user.dir") + "\\res\\" + filename);
		if(!ini_file.exists() || !ini_file.isDirectory()) {
			try {
				Properties props = new Properties();
				props.load(new FileInputStream(ini_file));
				if (props.getProperty(field) != null)
					return(props.getProperty(field));
				else
					return("");
			} catch (Exception ex) {
				ex.printStackTrace();
			}			
		}
		return("");
	}

	public static void writeKeyValuePairsINIFile(String filename, String key, String value) {

		File ini_file = new File(System.getProperty("user.dir") + "\\res\\" + filename);
		if(!ini_file.exists() || !ini_file.isDirectory()) {		
			try {
				ini_file.createNewFile();
				String DT = "**Sistema di Prenotazione -> Impostazioni**";
				Properties props = new Properties();
				FileInputStream FIS=new FileInputStream(ini_file);
				props.load(FIS);
				FIS.close();
				FileOutputStream FOS = new FileOutputStream(ini_file);
				props.setProperty(key, value);
				props.store(FOS,DT);
				FOS.close();			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static String readINIFileXML(String filename, String field) { //"config.xml"
		File ini_file = new File(System.getProperty("user.dir") + "\\res\\" + filename);
		if(!ini_file.exists() || !ini_file.isDirectory()) {
			try {
				Properties props = new Properties();
				props.load(new FileInputStream(ini_file));
				props.loadFromXML(new FileInputStream(ini_file));
				if (props.getProperty(field) != null)
					return(props.getProperty(field));
				else
					return("");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return("");
	}

	public static void writeKeyValuePairsINIFileXML (String filename, Map<String, String> keyValuePairs) {
		File ini_file = new File(System.getProperty("user.dir") + "\\res\\" + filename);
		if(!ini_file.exists() || !ini_file.isDirectory()) {
			try {
				ini_file.createNewFile();
				String DT = "**Sistema di Prenotazione -> Impostazioni**";
				Properties props = new Properties();
				props.load(new FileInputStream(ini_file));
				Iterator<Entry<String, String>> mapInterator = keyValuePairs.entrySet().iterator();
				while (mapInterator.hasNext()) {
					Map.Entry<String, String> pairs = (Entry<String, String>) mapInterator.next();
					props.setProperty(pairs.getKey(), pairs.getValue());
				}
				props.storeToXML(new FileOutputStream(ini_file, false), DT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
