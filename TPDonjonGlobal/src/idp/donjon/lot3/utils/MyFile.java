package idp.donjon.lot3.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class MyFile {

	
	public final static File f=setFile();
	
	private static File getEnvVar() {

		File envDossier;
		
			Properties properties = System.getProperties();
			Object defaultFolder= new Object();
			Object userHome = new Object();
			Iterator<Object> iteratorVJVM = properties.keySet().iterator();
			while (iteratorVJVM.hasNext()) {
			    Object cle = iteratorVJVM.next();
			    if (cle.equals("idp.default.folder")) defaultFolder=properties.get(cle);
			    if (cle.equals("user.home")) userHome=properties.get(cle);
			}
			if (defaultFolder.getClass().equals((new String("").getClass()))) 
				envDossier=new File(defaultFolder.toString());
			else envDossier=new File(userHome.toString());

			return envDossier;
	}

private static File setFile(){

	File f = new File(getEnvVar().getAbsolutePath()+"\\TPDonjonScores");
	if (!f.isDirectory())
		f.mkdir();

	f = new File(f.getAbsolutePath()+"\\last games.sav");
	
	if (!f.exists())
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	return f;
}
	// MODIFIER FICHIER POUR LE RENDRE UTILISABLE SUR AUTRE PC
}
