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
