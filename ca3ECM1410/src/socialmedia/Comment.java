package socialmedia;

import java.util.ArrayList;

public class Comment extends Post{
  /* This is the Comment class which extends the Post class. There is a rootID attribute and an arraylist of
  *endorsements added to know to which Original Post a comment points too so that it can later be recreated
  * As comments can be endorsed there is an arraylist of them.
  */
  private int rootID;
  private ArrayList <Endorsement> endorsements = new ArrayList<>();

  Comment(int id, int aid, String text, int rid){
    super(id, aid, text);
    this.rootID = rid;
  }
  public void setRootID(int rid){
    this.rootID = rid;
  }
  @Override
  public int getRootID(){
    return this.rootID;
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
    for (Endorsement i : endorsements){
      if(i.getID() == id){
        endorsements.remove(i);
      }
    }
  }
}
