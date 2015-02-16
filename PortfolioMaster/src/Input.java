import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * Input.java
 * RAIK 184H
 * This class is responsible for reading in and parsing asset and people objects
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.1
 *
 */
public class Input {

	/**
	 * Reads in a file of people, located in data/Persons.dat, and adds them into an arraylist of people in the database
	 * @return persons the list of people
	 */
	public ArrayList<Person> readPersons(){
		ArrayList<Person> persons = new ArrayList<Person>();
		try{
			File file = new File("data/Persons.dat"); //read in the file
			BufferedReader read = new BufferedReader(new FileReader(file));

			String line;
			int numLinesLeft = Integer.parseInt(read.readLine());
			
			//Iterate through the file parsing Person objects from the lines
			while(numLinesLeft > 0){ 					
				line = read.readLine();
				persons.add(parsePerson(line));
				numLinesLeft--;	
			}
			read.close();
		} catch(Exception e){
			System.out.println(e);
		}
		return persons;
	}

	/**
	 * Parses a line from a .dat file and parses it into a person object
	 * @param line the line from the .dat file
	 * @return p the person parsed
	 */
	public Person parsePerson(String line){
		Person p;
		String[] info = line.split(";"); //splitting the file line at each semicolon
		
		//Defining split parts of each line
		String[] nameComps = info[2].split(",");
		String lastName = nameComps[0];
		String firstName = nameComps[1];	
		
		//parsing an address
		String[] addressComps = info[3].split(",");
		Address a = new Address(addressComps[0],addressComps[1],addressComps[2],addressComps[4], addressComps[3]);
		
		//parsing a list of emails
		ArrayList<String> email = new ArrayList<String>();
		if(info.length==5){
			String[] emailList = info[4].split(",");
			for(int i=0; i<emailList.length; i++){
				email.add(emailList[i]);
			}
		}
		
		//determining what kind of person the person is
		if(info[1].equalsIgnoreCase("")){
			p = new Person(info[0],firstName, lastName,a, email);
			return p;
		} else{
			String[] secInfo = info[1].split(",");
			String sec = secInfo[1];
			String type ="";
			if(secInfo[0].equalsIgnoreCase("E")){
				type = "E";
			} else if(secInfo[0].equalsIgnoreCase("J")){
				type = "J";
			}
			p = new Broker(info[0], type, sec,firstName, lastName, a, email);
			return p;
		}
	}

	/**
	 * Reads in a file of assets, located in data/Assets.dat, and adds them into an arraylist of assets in the database
	 * @return assets the list of assets
	 */
	public ArrayList<Asset> readAssets(){
		ArrayList<Asset> assets = new ArrayList<Asset>();
		try{
			File file = new File("data/Assets.dat"); //reading in the file
			BufferedReader read = new BufferedReader(new FileReader(file));

			String line;
			int numLinesLeft = Integer.parseInt(read.readLine());
			
			//iterating through the file and parsing asset objects
			while(numLinesLeft != 0){
				line = read.readLine();
				assets.add(parseAsset(line));
				numLinesLeft--;
			}
			read.close();
		} catch(Exception e){
			System.out.println(e);
		}
		return assets;
	}

	/**
	 * parses an asset from a given line of a .dat file
	 * @param line the line from the .dat file
	 * @return a the asset parsed
	 */
	public Asset parseAsset(String line){
		Asset a;
		
		//Splitting the line of text around semicolons
		String[] info = line.split(";");
		String type = "";
		
		//determining type of asset, and instantiating accordingly
		if(info[1].equalsIgnoreCase("D")){
			type = "D";
			a = new Deposit(info[0], info[2], type, Double.parseDouble(info[3]));
			return a;
		} else if(info[1].equalsIgnoreCase("S")){
			type = "S";
			a = new Stock(info[0], info[2], type, Double.parseDouble(info[3]),Double.parseDouble(info[4]),
					Double.parseDouble(info[7]), info[6],Double.parseDouble(info[5]));
			return a;
		} else if(info[1].equalsIgnoreCase("P")){
			type = "P";
			a = new Investment(info[0], info[2], type, Double.parseDouble(info[3]), Double.parseDouble(info[4]),
					Double.parseDouble(info[5]), Double.parseDouble(info[6]));
			return a;
		} else{
			a = new Asset();
			return a;
		}
	}
}