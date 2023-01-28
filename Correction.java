 public static ArrayList<String> correction ( Object [] validated, ArrayList<String> data){
         int i = (Integer)validated[0];
         int j = (Integer)validated[2];
         char[] Line1;
         char[] PrevLine;
         char[] NextLine;
         String x;
         char[] Line2;
         String  y;
         String correctedString;
         char[] correctedLine;
         ArrayList <String> Result = new ArrayList<String>();
         
         if(validated[0] == validated[2] )  //name is different in opening and closing -- same lines
         {  
             y= validated[3].toString();
             char[] opening = y.toCharArray();
             Line2 = (data.get(j)).toCharArray();
             Line1 = (data.get(i)).toCharArray();
             x= validated[1].toString();
             char[] closing = x.toCharArray();
             correctedLine = new char[(Line1.length) - (opening.length) + (closing.length)];
             int openingNum =0;
             int f =0;
             for(int z=0; z<Line1.length; z++){
                 if((Line1[z] == '<') && (openingNum==0) ){
                     
                     openingNum ++;
                     correctedLine[f] = Line1[z];
                     z += (opening.length -1);
                     f++;
                     for(char k : closing){
                         if(k == '\0'){
                             continue;
                         }
                         correctedLine[f] = k;
                         f++;
                     }
                 }
                 else if(Line1[z] == '>'){
                     correctedLine[f] = Line1[z];
                     f++;
                 }
                 else{
                 correctedLine[f] = Line1[z];
                 f++;
                 }
                 
             }

             correctedString = String.valueOf(correctedLine);
             for(int r = 0; r<data.size(); r++){
                if(r==i){
                    Result.add(correctedString);
                }
                else{
                    Result.add(data.get(r));
                }   
            }
         } 
         if(i == -1 || j ==-1)
         {
             if(i == -1){
			  // no closing
                Line2 = (data.get(j)).toCharArray();
                y= validated[3].toString();
                char[] opening = y.toCharArray();
              correctedLine = new char[(Line2.length) + 1];
              int f =0;
              for(int z = 0; z<Line2.length; z++){
                  if((Line2[z] == '<') ){     
                     correctedLine[f] = Line2[z];
                     z += (opening.length -1);
                     f++;
                     correctedLine[f] = '/';
                     f++;
                     for(char k : opening){
                         if(k == '\0'){
                             continue;
                         }
                         correctedLine[f] = k;
                         f++;
                     }
                 }
                 else if(Line2[z] == '>'){
                     correctedLine[f] = Line2[z];
                     f++;
                 }
                 else{
                 correctedLine[f] = Line2[z];
                 f++;
                 }
              }
              
              correctedString = String.valueOf(correctedLine);
              
              for(int r = 0; r<data.size()+1; r++){
                if(r == data.size() ){
                    Result.add(correctedString);
                }
                else{
                    Result.add(data.get(r));
                }   
              }
             } 
             else{
                  // no opening
                x= validated[1].toString();
                char[] closing = x.toCharArray();
                Line1 = (data.get(i)).toCharArray(); 
                correctedLine = new char[(Line1.length) - 1];
                int f =0;
                for(int z = 0; z<Line1.length; z++){
                      if(Line1[z] == '/'){
                              
                              continue;
                      }
                      correctedLine[f] = Line1[z];
                      f++;
                }
                correctedString = String.valueOf(correctedLine);
                for(int r = 0; r<data.size(); r++){
                      if(r == 0){
                              Result.add(correctedString);
                      }
                      Result.add(data.get(r));  
                } 
             }
         }
         
         if(i != j && (i != -1) && (j !=-1)){
              Line2 = (data.get(j)).toCharArray();
               y= validated[3].toString();
               char[] opening = y.toCharArray();
             Line1 = (data.get(i)).toCharArray();
             PrevLine = (data.get(i-1)).toCharArray();
             x= validated[1].toString();
             char[] closing = x.toCharArray();
             int NumOpen = 0;
             int NumClose = 0;
             //for opening tag
             String OpentagCheck = "<";
             String ClosetagCheck = "</";
             //for closing tag
             String Closingtag = "</";
             
             boolean missingClose = false;
             boolean missingOpen = false;
             int k;
             for(k=1; k<opening.length; k++){
                 OpentagCheck += opening[k];
                 ClosetagCheck += opening[k];
             }
             for(k=1; k<closing.length; k++){
                 Closingtag += closing[k];
             }
             Closingtag += ">";
             OpentagCheck += ">";
             ClosetagCheck += ">";
             for( k = i; k<data.size(); k++ ){
                 if(data.get(k).contains(OpentagCheck) ){
                     NumOpen ++;
                 }
                 if(data.get(k).contains(ClosetagCheck) ){
                     NumClose ++;
                 }
             }
             if((NumOpen - NumClose) == 0){
                 missingClose = true;   
             }
             else if((NumClose - NumOpen) == 1){
                 missingOpen=true;
             }
             
             if(missingOpen){
                 int correctPlace = -1;
                 boolean containData =false; //name and id
                 boolean dataOnlyPrev = false; //topic and body
                 if(closing[closing.length -1] != 's'){
                         int LineIterator =0;
                         while(Line1[LineIterator] != '<'  ){ 
                            if(Line1[LineIterator] == ' ' || Line1[LineIterator] == '\0' || Line1[LineIterator] == '\n' || Line1[LineIterator] == '\t'){
                                LineIterator ++;
                            }
                            else{
                                System.out.println(Line1[LineIterator]);
                                containData = true; 
                                correctPlace = i;
                                break;
                            }
                        } 
                         
                        LineIterator =0;
                        while((LineIterator<PrevLine.length) && PrevLine[LineIterator] != '<' ){
                            LineIterator ++;
                        }
                        if((LineIterator== PrevLine.length) ){
                            dataOnlyPrev = true;
                            correctPlace = i-2;
                        }
                        int s;
                        for( s=i-1; s>j; s--){
                            if(data.get(s).contains(Closingtag)){
                                correctPlace = s+1;
                                break;
                            }
                        }
                        if(s==j){
                            correctPlace = j+1;
                        }
                        
                 }
                 
                 else{
                     int q;
                     int o;
                     char[] openNoNull = new char[closing.length -2];
                     for( o =0, q=0; q<closing.length-1; q++){
                         if(closing[q] == '\0'){ 
                             continue;
                         }
                         openNoNull[o] = closing[q];
                         o++;   
                     }
                     String NoNull = String.valueOf(openNoNull); 
                     for( q=i-1; q>j; q--){
                           if(data.get(q).contains(NoNull)){
                               correctPlace = q;
                           }
                        }
                    

                 }
//                  System.out.println(containData);
//                     System.out.println(dataOnlyPrev);
                 if(containData){
                     correctedLine = new char[(Line1.length) + 2 + closing.length-1];
                     int qq;
                     int kk;
                     for(qq=0 ,kk=0; qq<Line1.length; qq++, kk++){
                         if(Line1[qq] != ' '){
                             break;
                         }
                         correctedLine[qq] = Line1[qq];
                     }
                     correctedLine[qq] = '<';
                     qq++;
                     int ff;
                     for(ff=1; ff<closing.length; ff++){
                         correctedLine[qq] = closing[ff];
                         qq++;
                     }
                     correctedLine[qq] = '>';
                     qq++;
                     for(; kk<Line1.length; kk++){
                         correctedLine[qq] = Line1[kk];
                         qq++;
                     }
                     correctedString = String.valueOf(correctedLine);
                     for(int r = 0; r<data.size(); r++){
                        if(r==i){
                            Result.add(correctedString);
                        }
                        else{
                            Result.add(data.get(r));
                        }   
                    }
                 }
                 
                 else {
                     
                     correctedLine = new char[(Line1.length) -1];
                     int jj=0;
                     for(int ss = 0; ss<Line1.length; ss++){
                         if(Line1[ss] == '/'){
                            continue; 
                         }
                         correctedLine[jj] = Line1[ss];
                         jj++;
                     }
                     correctedString = String.valueOf(correctedLine);
                     for(int r = 0; r<data.size(); r++){
                        if(r==correctPlace){
                            Result.add(correctedString);
                        }
                            Result.add(data.get(r));
                          
                    }
                 }
                  
                  
             }
             
             if(missingClose){
                 int correctPlace = -1;
                 boolean containData =false; //name and id
                 boolean dataOnlyNext = false; //topic and body
                 NextLine = (data.get(j+1)).toCharArray();
                 if(opening[opening.length -1] != 's'){
                         int LineIterator =0;
                         while(Line2[LineIterator] != '>'  ){
                             LineIterator++;
                         }
                         LineIterator++;
                         while(LineIterator != Line2.length ){
                             if(Line2[LineIterator] == ' ' || Line2[LineIterator] == '\n'){
                                 LineIterator ++;
                                 
                             }
                             else{
                                 containData = true;
                                 correctPlace = j;
                                 break;
                             }
                         }
                         LineIterator =0;
                        while((LineIterator<NextLine.length) && NextLine[LineIterator] != '<' ){
                            LineIterator ++;
                        }
                        if((LineIterator== NextLine.length) ){
                            dataOnlyNext = true;
                            correctPlace = j+2;
                        }
                        int s;
                        for( s=j+1; s<i; s++){
                            if(data.get(s).contains(OpentagCheck)){
                                correctPlace = s;
                                break;
                            }
                        }
                        if(s==i && !dataOnlyNext){
                            correctPlace = i;
                        }
                 }
                 
                 else{
                     int q;
                     int o;
                     char[] closeNoNull = new char[opening.length -2];
                     for( o =0, q=0; q<opening.length-1; q++){
                         if(opening[q] == '\0'){ 
                             continue;
                         }
                         closeNoNull[o] = opening[q];
                         o++;   
                     }
                     String NoNull = String.valueOf(closeNoNull);
                     for( q=j+1; q<i; q++){
                           if(data.get(q).contains(NoNull)){
                               correctPlace = q+1;
                           }
                        }
                 }
             
             if(containData){
                     correctedLine = new char[(Line2.length) + 3 + opening.length-1];
                     int qq;
                     for(qq=0 ; qq<Line2.length; qq++){
                         correctedLine[qq] = Line2[qq];
                     }
                     correctedLine[qq] = '<';
                     qq++;
                     correctedLine[qq] = '/';
                     qq++;
                     int ff;
                     for(ff=1; ff<opening.length; ff++){
                         correctedLine[qq] = opening[ff];
                         qq++;
                     }
                     correctedLine[qq] = '>';
                     correctedString = String.valueOf(correctedLine);
                     for(int r = 0; r<data.size(); r++){
                        if(r==j){
                            Result.add(correctedString);
                        }
                        else{
                            Result.add(data.get(r));
                        }   
                    }
                 }
             else {
                     correctedLine = new char[(Line2.length) +1];
                     int jj=0;
                     for(int ss = 0; ss<Line2.length; ss++){
                         if(Line2[ss] == '<'){
                            correctedLine[jj] = Line2[ss];
                            jj++;
                            correctedLine[jj] = '/';
                            jj++;
                            continue;
                         }
                         correctedLine[jj] = Line2[ss];
                         jj++;
                     }
                     correctedString = String.valueOf(correctedLine);
                     for(int r = 0; r<data.size(); r++){
                        if(r==correctPlace){
                            Result.add(correctedString);
                        }
                            Result.add(data.get(r));
                          
                    }
                 }
            }
         }
        return Result;