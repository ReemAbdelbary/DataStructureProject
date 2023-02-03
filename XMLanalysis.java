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
}