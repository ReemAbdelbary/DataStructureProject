 public static ArrayList<String> json (ArrayList<String> data){
    	// store output 
    	ArrayList<String> xmlJson = new ArrayList<String>();
    	
    	xmlJson.add("{");
    	
        Stack<String> tagName = new Stack<String>();     
 
        String tabs="";
      //  String tabsPre="";
        String tag="";//"user"
        String pre = "" ;
        String content = "" ;
        
        for(String dV : data){
           char[] dataChars = dV.toCharArray(); // each line to array of chars
           
           String nameChar ="\""; 
           String nameChar1 = "\""; 
           String cont = ""; // holds content between <>--<>
           
           int f;
           
       if(dV.matches("(.*)<(.*)</(.*)")) {
    	   int st;
    	   st = dV.indexOf(">");
    	   
    	   for(int t =st+1; dataChars[t]!='<';t++) {
    		   if(dataChars[t]!='\t'||(dataChars[t])!=' ') {
    			   cont+= dataChars[t];
    		   }  
    	   }
       }
           for(f=0; f<dataChars.length; f++){
               if(dataChars[f] == '<'){
     
                   int i = f+1;
                   
                   if(dataChars[i] == '/' ){
                       int j = i+1;
                    
                       while(dataChars[j] != '>'){
                    	   if(dataChars[j]!='\t'||dataChars[j]!=' ') {
                            nameChar += dataChars[j];
                            j++;  
                    	   }
                        }
                       
                     if(dataChars[j] == '>') {
                    	j++;
    
                       }
                       if (nameChar.equals(tagName.peek())) {
                    	    pre = tagName.peek();
                    	  
                    	    tagName.pop();       	
                       }
   
                   }
                  
                   else {
                	   while(dataChars[i] != '>'){
                		   if(dataChars[i]!='\t'||dataChars[i]!=' ') {
                           nameChar1 += dataChars[i];
                           i++;
                           }
                	   }
                
                		   
                	   tagName.add(nameChar1);
                	   for(int t=0;t<tagName.size()-1;t++)
                		   tabs+="   ";
               
                	   if(!pre.equals(nameChar1)) {
                		   
                		   if(nameChar1.matches("(.*)s"))
                		   xmlJson.add(tabs+nameChar1+"\":"+"{");
                		   else
                		   xmlJson.add(tabs+nameChar1+"\":"+cont);    
                		           		   
                	   }
                	   tabs="";      	  
                   }
           }
        }
           if (!dV.contains("<")) {
        	   String temp = xmlJson.get(xmlJson.size()-1);
        	   if(!temp.contains(","))
        	   xmlJson.set(xmlJson.size()-1, tabs+temp+"\""+dV+"\""+","); 
        	   else {
        		   char[] tempx = temp.toCharArray();
        		  int i= temp.indexOf(':');
        		  String al="";
        		  for(int y=0;y<i+1;y++)
        		  al+=" ";
        		  
        		   xmlJson.add(al+"\""+dV+"\""); 
        		   xmlJson.add("                "+"]"); 
        	   }
        	  }
    }
      for(int z=0;z< xmlJson.size();z++) {
    	  String check = xmlJson.get(z);
    	  char[] dataC = check.toCharArray();
    	  if(dataC[dataC.length-1]==':') {
    		  check+="["; 
    	  }
    	  xmlJson.set(z, check);
      }  
        xmlJson.add("         ]"+"\n"+"  }"); 
        xmlJson.add("}"); 
        return xmlJson;
        }
 }
