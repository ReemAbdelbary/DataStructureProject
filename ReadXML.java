  public static ArrayList<String> ReadXML(String path){
         try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
		        
                                String line;
                                ArrayList<String> LinesArray = new ArrayList<String>();
                                while((line=reader.readLine()) != null){
                                    LinesArray.add(line);
                                }
                                reader.close();
                                return LinesArray;
                       
	} 
      catch(IOException c){
          c.printStackTrace();
      }
         return null;
    }