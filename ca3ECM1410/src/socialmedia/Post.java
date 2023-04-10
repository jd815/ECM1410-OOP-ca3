package socialmedia;

import java.io.*;

public class Post implements java.io.Serializable{
  /* This is the parent class Post for the 3 types of post, Original, Endorsement and Comment. It is also
  * serializable and therefore the children will be too. It has attributes ID, authorID, and body. 
  */
  private int ID;
  private int authorID;
  private String body;

  Post(int id, int aid, String text){
    this.ID = id;
    this.authorID = aid;
    this.body = text;
  }

  public void setID(int id){
    this.ID = id;
  }
  public int getID(){
    return this.ID;
  }
  public void setAuthorID(int aid){
    this.authorID = aid;
  }
  public int getAuthorID(){
    return this.authorID;
  }
  public void setBody(String text){
    this.body = text;
  }
  public String getBody(){
    return this.body;
  }
  public int getRootID(){
    return -1; //This method is here just so that all Post children inherit it and it can be called from an arraylist of posts in the MiniSocialMedia
    //if it returns -1 it has to be the original post ID
  }
}
