package socialmedia;

public class Endorsement extends Post{
  /* The Endoresement extends post adding a rootID attribute to know which post it is endorsing.
  */
  private int rootID;

  Endorsement(int id, int aid, String text, int rid){
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
}
