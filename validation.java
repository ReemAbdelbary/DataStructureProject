public static Object[] validation (ArrayList<String> data){
        Stack<String> tagName = new Stack<String>();
        Stack<Character> tokens = new Stack<Character>();
        Stack<Integer> LineNumber = new Stack<Integer>();
        int line = 0;
        Object [] validationResult;
        validationResult = new Object[4];
        String nameChar1="\0" ;
        
        for(String dV : data){
            char[] dataChars = dV.toCharArray(); 
            String nameChar ="\0";
            nameChar1 = "\0";
            int f;
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

                            nameChar1 += dataChars[j];
                            j++;
                        
                        }
                        if(tagName.empty()){
                           break;
                       }
                       if(nameChar1 .equals(tagName.peek() )){
                           tagName.pop();
                           tokens.pop();
                           tokens.pop();
                           LineNumber.pop();
                           LineNumber.pop();
                           
                       }
                       else{
                           //System.out.println("1");
                           validationResult[0]  = LineNumber.peek();
                           LineNumber.pop();
                           validationResult[1] = nameChar1;
                           validationResult[2]  = LineNumber.peek();
                           LineNumber.pop();
                           validationResult[3] = tagName.peek();
                           return validationResult;
                       }
                   }
                   else{    
                        while(dataChars[i] != '>'){
                                nameChar += dataChars[i];
                                i++;
                            
                        }
                        tagName.add(nameChar);
//                        System.out.println(tagName);
                        
                   }
               }
           }
           line ++;
           
        }
//        System.out.println(tokens.empty());
//        System.out.println(tagName.empty());
        if(tagName.empty() && tokens.empty()){
//            System.out.println("111");
            validationResult[0] = -1;
            validationResult[2] = -1;
            return validationResult;   
        }
        else{
            if(!(tagName.empty())){
                validationResult[0]  = -1;

                validationResult[2]  = LineNumber.peek();

                validationResult[3]  = tagName.peek();
            }
            else{
                validationResult[0]  = LineNumber.peek();

                validationResult[2]  = -1;

                validationResult[1]  = nameChar1;
            }
           return validationResult;
        }
    }