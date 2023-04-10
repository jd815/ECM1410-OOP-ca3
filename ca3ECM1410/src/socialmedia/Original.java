package socialmedia;

import java.util.ArrayList;
public class Original extends Post{
  /* This is the Original class which extends the Post class. It has added atttributes Endorsements and Comments
  * to keep track of the number of times this post has been endoresed or commented. In addition to make import junit.framework.TestCase;
  * easier to recreate when needed.
  */
  private ArrayList <Endorsement> endorsements = new ArrayList<>();
  private ArrayList <Comment> comments = new ArrayList<>();


  Original(int id, int aid, String text){
    super(id, aid, text);
  }
  public void setEndorsements(ArrayList<Endorsement> endors){
    this.endorsements = endors;
  }
  public void addEndorsement(Endorsement endor){
    this.endorsements.add(endor);
  }
  public ArrayList <Endorsement> getEndorsements(){
    return this.endorsements;
  }
  public int getLenEndorsements(){
    return this.endorsements.size();
  }
  public void removeEndorsement(int id){
    int temp = 0;
    for (int i=0; i<endorsements.size(); i++){
      if(id == endorsements.get(i).getID() ){
        temp = i;
        break;
      }
    }
    endorsements.remove(temp);
  }
  public Boolean checkDoubleEndorsements(int id){
    for (Endorsement i: endorsements){
      if(i.getAuthorID() == id){
        return false;
      }
    }return true;
  }
  public void setComments(ArrayList<Comment> comms){
    this.comments = comms;
  }
  public void addComment(Comment comms){
    this.comments.add(comms);
  }
  public ArrayList<Comment> getComments(){
    return this.comments;
  }
  public int getLenComments(){
    return this.comments.size();
  }
  public void removeComment(int id){
    for (Comment i : comments){
      if(i.getID() == id){
        i.setBody("The original content was removed from the system and is no longer available.");
        i.setAuthorID(0);
      }
    }
  }
  public Boolean isLastComment(int id){
    if (comments.get(this.comments.size()-1).getID() == id){
      return true;
    }else{
      return false;
    }
  }

}
