package json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Data  {

	
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
    
    public static ArrayList<String> spaceRemover(ArrayList<String> data){
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> finalresult = new ArrayList<String>();
        for(String d : data){
            char[] lineChars = d.toCharArray();
            boolean isTag = false;
            int start = 0;
            int end = lineChars.length - 1;
            for(int x=0; x<lineChars.length; x++){
                if(lineChars[x] == '<'){
                    isTag = true;
                    break;
                }
            }
            if(isTag){
                while(lineChars[start] != '<' ){
                    if(lineChars[start] == ' '){
                        lineChars[start] = '\0'; 
                    }
                    start++;   
                }
                while(lineChars[end] != '>' ){
                    if(lineChars[end] == ' '){
                       lineChars[end] = '\0'; 
                    }
                    end--;
                
                }   
            }
            else{
                while(lineChars[start] ==  ' ' ){
                     if(lineChars[start] == ' '){
                        lineChars[start] = '\0'; 
                    }
                    start++;   
                }
                while(lineChars[end] ==  ' ' ){
                     if(lineChars[end] == ' '){
                        lineChars[end] = '\0'; 
                    }
                    end--;   
                }
            }
           d = String.valueOf(lineChars);
           result.add(d);
        }
        
        for(String r : result){
            char[] lineChars = r.toCharArray();
            int sizee = lineChars.length;
            for(int x=0; x<lineChars.length; x++){
                if(lineChars[x] == '\0'){
                    sizee--;
                }
            }
            char[] finallineChars = new char[sizee];
            int finalx = 0;
            for(int x=0; x<lineChars.length; x++){
                if(lineChars[x] == '\0'){
                    continue;
                }
                finallineChars[finalx] = lineChars[x];
                finalx++;
            }
            
           r = String.valueOf(finallineChars);
           finalresult.add(r);
        }
        return finalresult;
    }
    
    public static ArrayList<String> json (ArrayList<String> data){
    	// store output + concat.
    	ArrayList<String> xmlJson = new ArrayList<String>();
    	xmlJson.add("{");
    	
        Stack<String> tagName = new Stack<String>();    
        Stack<Character> tokens = new Stack<Character>();   
        Stack<Integer> LineNumber = new Stack<Integer>();  
        int line = 0;
        
        String tabs="";
        String tag="";//"user"
        String pre = "" ;
        String content = "" ;
        
        for(String dV : data){
           char[] dataChars = dV.toCharArray(); 
           
           String nameChar ="\"";
           String nameChar1 = "\"";
           
           int f;
           
           // kol satr array of chars
           
           for(f=0; f<dataChars.length; f++){
               if(dataChars[f] == '<'){
                   tokens.add(dataChars[f]);
                   LineNumber.add(line);
//                   System.out.println(tokens);
//                   System.out.println(LineNumber);
                   int i = f+1;
                   
                   if(dataChars[i] == '/' ){
                       int j = i+1;
                       while(dataChars[j] != '>'){
                            nameChar += dataChars[j];
                            j++;  
                          
                        }
                       if (nameChar.equals(tagName.peek())) {
                    	    pre = tagName.peek();
                    	   tagName.pop();
                    	 //  System.out.println("hh"); 
                       }
                       // pop to count tabs
                   }
                  
                   else {
                	   while(dataChars[i] != '>'){
                           nameChar1 += dataChars[i];
                           i++;
                           
                	   }
                	   
                	   tagName.add(nameChar1);
                	   for(int t=0;t<tagName.size()-1;t++)
                		   tabs+="   ";
                	   if(!pre.equals(nameChar1)) {
                		   
                		   if(nameChar1.matches("(.*)s"))
                		   xmlJson.add(tabs+nameChar1+"\":"+"{");
                		   else
                		   xmlJson.add(tabs+nameChar1+"\":");  
                	   }
                	 
                	   
                	   
                	   tabs="";
                	  // System.out.println(xmlJson.get(1));
                	  // System.out.println(tagName);
                	  
                   }

           }// wa2f hna <
             
	     
        }
           if (!dV.contains("<")) {
        	   String temp = xmlJson.get(xmlJson.size()-1);//pre element
        	   if(!temp.contains(","))
        	   xmlJson.set(xmlJson.size()-1, tabs+temp+"\""+dV+"\""+","); 
        	   else {
        		   char[] tempx = temp.toCharArray();
        		  int i= temp.indexOf(':');
        		  String al="";
        		  for(int y=0;y<i+1;y++)
        		  al+=" ";
        		  
        		   xmlJson.add(al+"\""+dV+"\""); 
        		   xmlJson.add(al+"]"); 
        	   }
        	  }
    }
        return xmlJson;}
 }
}
           
        