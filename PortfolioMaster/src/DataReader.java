import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DataReader.java
 * RAIK 184H
 * This class is responsible for reading in and parsing asset and people objects
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 3.0
 */
public class DataReader{
	public static final String PORTFOLIO_FILENAME = "data/Portfolios.dat";
	public static final String ASSET_FILENAME = "data/Assets.dat";
	public static final String PERSON_FILENAME = "data/Persons.dat";
	
	/**
	 * Reads in a file of portfolios, located at data/Persons.dat, and creates an arraylist of portfolios
	 * @return portfolio, a list of portfolios
	 */
	public ArrayList<Portfolio> readPortfolios(){
		ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
		try{
			File file = new File(PORTFOLIO_FILENAME); //read in the file
			PortfolioData pd = new PortfolioData();
			BufferedReader read = new BufferedReader(new FileReader(file));

			String line;
			int numLinesLeft = Integer.parseInt(read.readLine());
			
			ArrayList<Asset> assets = readAssets();
			ArrayList<Person> persons = readPersons();
			//Iterate through the file parsing Portfolio objects from the lines
			while(numLinesLeft > 0){ 					
				line = read.readLine();
				portfolios.add(parsePortfolio(line, assets, persons));
				numLinesLeft--;	
			}
			read.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return portfolios;
	}
	
	/**
	 * This method parses a portfolio object from a line of text
	 * @param line
	 * @return
	 */
	public Portfolio parsePortfolio(String line, ArrayList<Asset> assets, ArrayList<Person> persons){
		Portfolio p;		
		String[] portInfo = line.split(";",-1);

		String code = portInfo[0];
		String ownerID = portInfo[1];
		String managerID = portInfo[2];
		String beneficiaryID = portInfo[3];
		String[] assetIDList = portInfo[4].split(",");
		
		if(beneficiaryID.trim().equalsIgnoreCase("")){
			p = new Portfolio(code, searchPerson(ownerID, persons), (Broker) searchPerson(managerID, persons), searchAssets(assetIDList, assets));
			return p;
		} 
		else{
			p = new Portfolio(code, searchPerson(ownerID, persons), (Broker) searchPerson(managerID, persons), searchPerson(beneficiaryID, persons), searchAssets(assetIDList,assets));
			return p;
		}
	}
	
	/**
	 * This method searches a given list of persons to find the specified person
	 * @param id, the person's id
	 * @param persons, the ArrayList of persons
	 * @return the person matching the id, if there is one. Else, returns null
	 */
	public Person searchPerson(String id, ArrayList<Person> persons){
		Person subject;
		for(Person p: persons){
			if(p.getCode().equalsIgnoreCase(id)){
				subject=p;
				return subject;
			}
		}
		return null;
	}
	
	/**
	 * Searches for specific assets in a given ArrayList of assets
	 * @param idList, list of ids to be searched for
	 * @param assets, arraylist of assets to search through
	 * @return assetList, a HashMap of assets to values given
	 */
	public HashMap<Asset, Double> searchAssets(String[] idList, ArrayList<Asset> assets){
		HashMap<Asset,Double> assetList = new HashMap<Asset,Double>();
		for(String id: idList){
			if(!id.equalsIgnoreCase("")) {
				double value = Double.parseDouble(id.split(":")[1]);
				id = id.split(":")[0];
			
				for(Asset a: assets){
					if(a.getCode().equalsIgnoreCase(id)){
						assetList.put(a,value);
					}
				}
			}
		}
		return assetList;
	}
	
	

	/**
	 * Reads in a file of people, located in data/Persons.dat, and adds them into an arraylist of people in the database
	 * @return persons the list of people
	 */
	public ArrayList<Person> readPersons(){
		ArrayList<Person> persons = new ArrayList<Person>();
		try{
			File file = new File(PERSON_FILENAME); //read in the file
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
			p = new Person(info[0],lastName, firstName,a, email);
			
		} else{
			String[] secInfo = info[1].split(",");
			String sec = secInfo[1];
			char type =' ';
			if(secInfo[0].equalsIgnoreCase("E")){
				type = 'E';
			} else if(secInfo[0].equalsIgnoreCase("J")){
				type = 'J';
			}
			p = new Broker(info[0], type, sec,lastName, firstName, a, email);
		}
		return p;
	}

	
	//TODO CHeck my stuffs here
	public ArrayList<Asset> findAssets(){
		PortfolioData pd =new PortfolioData();
		return pd.getAssets();
	}
	/**
	 * Reads in a file of assets, located in data/Assets.dat, and adds them into an arraylist of assets in the database
	 * @return assets the list of assets
	 */
	public ArrayList<Asset> readAssets(){
		ArrayList<Asset> assets = new ArrayList<Asset>();
		try{
			File file = new File(ASSET_FILENAME); //reading in the file
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
			
		} else if(info[1].equalsIgnoreCase("S")){
			type = "S";
			a = new Stock(info[0], info[2], type, Double.parseDouble(info[3]),Double.parseDouble(info[4]),
					Double.parseDouble(info[7]), info[6],Double.parseDouble(info[5]));
			
		} else if(info[1].equalsIgnoreCase("P")){
			type = "P";
			a = new Investment(info[0], info[2], type, Double.parseDouble(info[3]), Double.parseDouble(info[4]),
					Double.parseDouble(info[5]), Double.parseDouble(info[6]));
			
		} else{
			a = new Asset();
			
		}
		return a;
	}
}