
package convertToCBOR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

public class MainClass {
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException {
		
		String inputLocation = args[0];
		String outputLocation = args[1];
		String inputUserName = args[2];
		String inputUserEmail = args[3];
		
		String myLocation=outputLocation;
		String fileName;
		String fileContent;
		TargetModel myModel;
		
		File[] inputDirectory = (new File(inputLocation)).listFiles();
		
		
		for(int j=0;j<inputDirectory.length;j++)
		{
		// read all files in the inputLocation directory
		File[] inputFiles = inputDirectory[j].listFiles();
		
		outputLocation  = myLocation + File.separator + inputDirectory[j].getName();
		(new File(outputLocation)).mkdir();
		
		// loop : for each file,
		for(int i=0;i<inputFiles.length;i++)
		{
			fileName=inputFiles[i].getName();
			fileContent = getContent(inputFiles[i].getAbsolutePath());
			
			myModel	= new TargetModel(inputUserName, inputUserEmail);
			myModel.setContent(fileContent);
			myModel.setUrl(fileName);
			myModel.timestamp=1421064000;
			HashMap<String, Object> h = (HashMap)myModel.request.get("client");
			h.put("hostname", "gray17.poly.edu");
			h.put("address","128.238.182.77");
			h.put("robots", "classic");
			
			
			FileOutputStream myWriter;
			try {
				myWriter = new FileOutputStream(outputLocation + File.separator + fileName);
				CBORFactory f = new CBORFactory();
				//JsonFactory f = new JsonFactory();
				
				ObjectMapper mapper = new ObjectMapper(f);
				try {
					mapper.writeValue(myWriter, myModel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					myWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("unable to create file " + fileName);
				e.printStackTrace();
			}
			// read the source
			// update the TargetModel Object
			// write to a new file in the output Location
		}
		
		}
		
	}
	
	private static String getContent(String inputfilePath){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(inputfilePath));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}

}
