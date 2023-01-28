    public static ArrayList<String> XMLprettify (ArrayList<String> noSpace){
        int index =0;
        ArrayList<String> prettifyResult = new ArrayList<String>();
        for(int z = 0 ; z <= (noSpace.size() - 1) ; z++ ){
            String nS = noSpace.get(z);
            String nextnS;
            if(z == (noSpace.size() - 1)){
                prettifyResult.add(nS);
                continue;
            }
            else{
                nextnS = noSpace.get(z+1);
             }
            String add ="\0";
            for(int i=0; i<index; i++){
               add += '\t'; 
            }  
            add += nS;
            prettifyResult.add(add);
            
            // opening only done
            if((nS.contains("<")) && !(nS.contains("</"))){
                  if(
                      nextnS.matches("</(.*)")){
                  }
                  else{
                      index++;
                  }
            }
            
            // data only done
            else if(!(nS.contains("<"))){
                if(!(nextnS.contains("<"))){
                }
                else{
                    index--;
                }
            }
            
            //opening and closing in the same line or closing
            else{
                if((nextnS.matches("</(.*)"))){
                    index--;
                }
                else {}
            }
        }
        return prettifyResult;
    }