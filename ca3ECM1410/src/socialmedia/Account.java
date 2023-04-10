package socialmedia;
import java.util.ArrayList;
import java.io.*;

public class Account implements java.io.Serializable{
  /* The Account class that is serializable so that i can be saved into a file later.
  * Has ID, username, and description as requested. Has an arraylist of posts(original), ednorsements and comments
  * to more easily keep track of their count and simplify the way they will be shown. It has a description
  * field which is never used because the methods never had the the arguments for it but I added it because
  * it is in the spects. They it has been given a get and set method if needed and there is a constructor method for it.
  */
  private int ID;
  private String username;
  private String description;
  private ArrayList<Original> posts = new ArrayList<>();
  private ArrayList<Endorsement> endorsements = new ArrayList<>();
  private ArrayList<Comment> comments = new ArrayList<>();

  Account(int id, String uname){
    this.ID = id;
    this.username = uname;
    this.description = "";
  }
  Account(int id, String uname, String desc){
    this.ID = id;
    this.username = uname;
    this.description = desc;
  }

  public void setID(int id){
    this.ID = id;
  }
  public int getID(){
    return this.ID;
  }
  public void setUsername(String uname){
    this.username = uname;
  }
  public String getUsername(){
    return this.username;
  }
  public void setDescription(String desc){
    this.description = desc;
  }
  public String getDescription(){
    return this.description;
  }
  public int getPostCount(){
    return posts.size() + endorsements.size() + comments.size();
  }
  public void addOriginal(Original post){
    posts.add(post);
  }
  public void addEndorsement(Endorsement post){
    endorsements.add(post);
  }
  public void addComment(Comment post){
    comments.add(post);
  }
  public ArrayList<Original> getPosts(){
    return this.posts;
  }
  public ArrayList<Endorsement> getEndorsements(){
    return this.endorsements;
  }
  public ArrayList<Comment> getComments(){
    return this.comments;
  }

  public int getRootIDPost(int id){
    for (Comment i: comments){
      if (i.getID() == id){
        return i.getRootID();
      }
    }
    for (Endorsement i: endorsements){
      if (i.getID() == id){
        return i.getRootID();
      }
    }return -1;
  }
  public Original getPost(int pid){
    for (Original i : posts){
      if (i.getID() == pid){
        return i;
      }
    }
    return null;
  }
  public Comment getComment(int cid){
    for (Comment i : comments){
      if (i.getID() == cid){
        return i;
      }
    }
    return null;
  }
  public Endorsement getEndorsement(int eid){
    for (Endorsement i : endorsements){
      if (i.getID() == eid){
        return i;
      }
    }
    return null;
  }
  public int getFirstPostID(){
    return this.posts.get(0).getID();
  }
  public int getFirstEndorsementID(){
    return this.endorsements.get(0).getID();
  }
  public int getFirstCommentID(){
    return this.comments.get(0).getID();
  }
  public int getEndorsementCount(){
    int count=0;
    for (Original i : posts){
      count += i.getLenEndorsements();
    }
    for (Comment x : comments){
      count += x.getLenEndorsements();
    }
    return count;
  }
  public int [] getMostEndorsed(){
    int [] temp = {-1, -1};
    for (Original i : posts){
      if(i.getLenEndorsements() > temp[0]){
        temp[0] = i.getLenEndorsements();
        temp[1] = i.getID();
      }
    }
    for (Comment x : comments){
      if(x.getLenEndorsements() > temp[0]){
        temp[0] = x.getLenEndorsements();
        temp[1] = x.getID();
      }
    }
    return temp;
  }

  public void endorsePost(Endorsement en, int id){
    for (Original i : posts){
      if (i.getID() == id){
        i.addEndorsement(en);
      }
    }for (Comment x : comments){
      if(x.getID() == id){
        x.addEndorsement(en);
      }
    }
  }
  public void commentPost(Comment com, int id){
    for (Original i:posts){
      if(i.getID() == id){
        i.addComment(com);
      }
    }
  }
  public void removeEndorsement(int id){
    int temp = 0;
    for (int i=0; i<endorsements.size(); i++){
      if(id == endorsements.get(i).getID()){
        temp = i;
        break;
      }
    }
    endorsements.remove(temp);
  }
  public void removeEndorsementFromPosts(int id, int eid){
    for (Original i : posts){
      if (i.getID() == id){
        i.removeEndorsement(eid);
      }
    }
  }
  public void removeComment(int id){
    int temp = 0;
    for (int i=0; i<comments.size(); i++){
      if(id == comments.get(i).getID()){
        temp = i;
        break;
      }
    }
    comments.remove(temp);
  }

  public void removeCommentFromPosts(int id, int cid){
    for (Original i : posts){
      if (i.getID() == id){
        i.removeComment(cid);
      }
    }
  }
  public void removeOriginal(int id){
    for (Original i : posts){
      if (i.getID() == id){
        i.setBody("The original content was removed from the system and is no longer available.");
        i.setAuthorID(0);
      }
    }
    int temp = 0;
    for (int i=0; i<posts.size(); i++){
      if(id == posts.get(i).getID()){
        temp = i;
        break;
      }
    }
    posts.remove(temp);
  }

}
