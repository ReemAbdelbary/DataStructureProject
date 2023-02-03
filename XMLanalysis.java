import java.util.ArrayList;


public class XMLanalysis extends Phase1 {
  
   String userOpen ="<user>";
   String userClose = "</user>";
   String IDOpen ="<id>";
   String IDClose = "</id>";
   String nameOpen = "<name>";
   String nameClose = "</name>";
   String postsOpen = "<posts>";
   String postsClose = "</posts>";
   String postOpen = "<post>";
   String postClose = "</post>";
   String bodyOpen = "<body>";
   String bodyClose = "</body>";
   String topicsOpen = "<topics>";
   String topicsClose = "</topics>";
   String topicOpen = "<topic>";
   String topicClose = "</topic>";
   String followersOpen = "<followers>";
   String followersClose = "</followers>";
   String followerOpen = "<follower>";
   String followerClose = "</follower>";
   String usersOpen = "<users>";
   String usersClose = "</users>";
   
   public User[] getUsers(ArrayList<String> LinesNoSpace){
       char[] LineChar;
       boolean isFollower = false;
       int NoUser =0;
       int indexUser = -1;
       int i =0;
       for(String s : LinesNoSpace){
           LineChar = s.toCharArray();
           if(s.contains( userOpen )){
              NoUser ++;
           }
       }
       
       User[] users = new User[NoUser];
           
        for(String s : LinesNoSpace){
            LineChar = s.toCharArray();
             String data = "" ;
            if(s.contains( userOpen )){
               indexUser ++;
               users[indexUser] = new User();
               isFollower = false;
            }
           if(s.contains( IDOpen) ){
               int index = s.indexOf(">");
               for( index++ ; index< s.length(); index++ ){
                   if(LineChar[index] == '\0'){
                       continue;
                   }
                   else if(LineChar[index] == '<' ){
                       break;
                   }
                   else {
                       data += LineChar[index];
                   }   
               }
               if(isFollower){
                   users[indexUser].Followers.add(data);
               }
               else{
               users[indexUser].ID = data;
               }
           }
          
           if(s.contains( nameOpen)){
               int index = s.indexOf(">");
               for( index++ ; index< s.length(); index++ ){
                   if(LineChar[index] == '\0'){
                       continue;
                   }
                   else if(LineChar[index] == '<' ){
                       break;
                   }
                   else {
                       data += LineChar[index];
                   }
                      
               }
               users[indexUser].name = data;
           }
           if(s.contains(followerOpen)){
               isFollower = true;
           }
           if(s.contains(postOpen)){
               Post post = new Post();
               String y ;
               
               for(int x = i+1 ; x<LinesNoSpace.size(); x++){
                   y= LinesNoSpace.get(x);
                   if(y.contains(postClose)){
                       break;
                   }
                   if(y.contains(bodyOpen) && y.contains(bodyClose)){
                       int index = s.indexOf(">");
                       for( index++ ; index< s.length(); index++ ){
                            if(LineChar[index] == '\0'){
                                continue;
                            }
                            else if(LineChar[index] == '<' ){
                                break;
                            }
                            else {
                                data += LineChar[index];
                            }   
                       }
                       post.body = data;
                   }
                   if(y.contains(bodyOpen) && !(y.contains(bodyClose))){
                      
                       int k = x+1;
                       for(; k<LinesNoSpace.size(); k++){
                            if(LinesNoSpace.get(k).contains(bodyClose)){
                                break;
                            }
                              data += LinesNoSpace.get(k);
                       }
                        post.body = data;   
                   }
                   
                   if(y.contains(topicOpen) && y.contains(topicClose)){
                       int index = s.indexOf(">");
                       for( index++ ; index< s.length(); index++ ){
                            if(LineChar[index] == '\0'){
                                continue;
                            }
                            else if(LineChar[index] == '<' ){
                                break;
                            }
                            else {
                                data += LineChar[index];
                            }   
                       }
                       post.topics.add(data);
                   }
                   
                   if(y.contains(topicOpen) && !(y.contains(topicClose))){
                       data = LinesNoSpace.get(x+1);
                       post.topics.add(data);
                   }
               }
               users[indexUser].Posts.add(post);    
           }
           i++;
       }
       return users;
   } 
    public ArrayList<String> Suggestions (String ID, ArrayList<String> LinesNoSpace){
        User [] users = getUsers(LinesNoSpace);
        User u = new User();
        ArrayList<String> Followers;
        ArrayList<String> Suggestions = new ArrayList<String>();
        for(User x: users){
            if(x.ID.equals(ID)){
                u=x;
                break;
            }
        }
        Followers = u.Followers;
        for(User s: users){
            if(Followers.contains(s.ID)){
                for(String z: s.Followers){
                    if(Suggestions.contains(z) || z.equals(ID) || Followers.contains(z)){
                        continue;
                    }
                    else{
                        Suggestions.add(z);
                    }
                }
            }
        }
        
        return Suggestions;
   }
   public String search(String word, ArrayList<String> LinesNoSpace){
       User[] users = getUsers(LinesNoSpace);
       boolean found = false;
       String result = "";
       boolean firstFound = false;
       
       
       for(User u: users){
           
           int indexx=0;
           for(Post x : u.Posts){
               found = false;
               if(x.body.contains(word)){
                   found=true;
                   firstFound = true;
                   indexx++;
               }
               if(x.topics.contains(word)){
                   found = true; 
                   firstFound = true;
                   indexx++;
               }
               if(found){
                   if(indexx==1){
                       result += "Name is: " + u.name + "\n" + "ID is: " + u.ID + "\n";
                   }
                   result += "Body is: " + x.body + "\n" + "Topics is: "+ x.topics + "\n";
               }
           }   
       }
       if(!firstFound){
               result += "Word isn't Found";
           }
       return result;
   }
   public ArrayList<String> MutualUsers (String ID1, String ID2, ArrayList<String> LinesNoSpace){
       User [] users = getUsers(LinesNoSpace);
       User u1 = new User();
       User u2 = new User();
       ArrayList<String> F1;
       ArrayList<String> F2;
       ArrayList<String> mutual = new ArrayList<String>();
       for(User x : users){
           if(x.ID.equals(ID1)  ){
               u1 = x;
           }
           if(x.ID.equals(ID2)){
               u2 = x;
           }
       }
       F1 = u1.Followers;
       F2=u2.Followers;
       
       for(String y : F1){
        for(String u : F2){
        if(y.equals(u)){
            mutual.add(y);
        }
    }
}
 return mutual;
   }
  public String influencer(ArrayList<String> LinesNoSpace){
       User[] users = getUsers(LinesNoSpace);
       int MostRepeated = 0;
       User MostInfluencer = users[0];
       
       for(User u: users){
           String Id = u.ID;
           int Repeats =0;
           for(User x : users){
               if(x.equals(u)){
                   continue;
               }
               else{
                   if(x.Followers.contains(Id)){
                       Repeats ++;
                   }
               }
           }
           if(Repeats > MostRepeated){
               MostInfluencer = u;
               MostRepeated = Repeats;
           }
       }
       return("Data of most influencer is: " + "\n" + "ID: " + MostInfluencer.ID + "\n" +"Name: "+ MostInfluencer.name + "\n"+ "Number of Followers is :" + MostRepeated);
               
   }

}
