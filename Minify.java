public static String XMLminify (ArrayList<String> noSpace){
    String minifyResult = null;
    for(String n : noSpace){
      char[] noSpaceChars = n.toCharArray(); 
      int y ;
      for(y=0; y<noSpaceChars.length; y++){
          if(noSpaceChars[y] == '\n'){
              noSpaceChars[y] = '\0';
          }
      }
      n = String.valueOf(noSpaceChars);
      minifyResult += n;
    }
    
    return minifyResult;
}