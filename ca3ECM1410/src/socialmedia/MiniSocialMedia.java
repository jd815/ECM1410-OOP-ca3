package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

/**
 * MiniSocialMedia compiles by implementing the MiniSocialMediaPlatform interface
 *
 * @author Jakub Davison
 * @version 1.0
 */
public class MiniSocialMedia implements MiniSocialMediaPlatform {
  static Account delAcc = new Account(0, "admin");
  static ArrayList <Account> accounts = new ArrayList<>(Arrays.asList(delAcc));
  static ArrayList <Post> allPosts = new ArrayList<>();

  public int getNumberOfAccounts(){
    //returns the number of accounts
    return accounts.size();
  }
  public int getTotalOriginalPosts(){
    //returns the total number of original Posts created by all users
    int counter = 0;
    for (Post i : allPosts){
      if (i instanceof Original){
        counter ++;
      }
    }return counter;
  }
  public int getTotalEndorsmentPosts(){
    //returns the total number of Endoresement Posts created by all users
    int counter = 0;
    for (Post i : allPosts){
      if (i instanceof Endorsement){
        counter ++;
      }
    }return counter;
  }
  public int getTotalCommentPosts(){
    //returns the total number of Comments created by all users
    int counter = 0;
    for (Post i : allPosts){
      if (i instanceof Comment){
        counter ++;
      }
    }return counter;
  }

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
    int x=handle.length();
    if (x > 30){
      throw new InvalidHandleException ("The handle you have entered is longer than 30 characters. Pelase try again.");
    }else if (x == 0){
      throw new InvalidHandleException ("The handle you have entered is empty. Pelase try again.");
    }else if(handle.indexOf(" ") != -1){
      throw new InvalidHandleException ("The handle you have entered contains a white space. Pelase try again.");
    }
    if(accounts.size()>0){
      for (Account i : accounts){
        if (i.getUsername()==handle){
          throw new IllegalHandleException("The handle you have entered is already in use. Pelase try again.");
        }
      }
      int id = this.accounts.get(accounts.size()-1).getID() +1;
  		Account acc = new Account(id, handle);
  		accounts.add(acc);
      return id;
      }
    else{
  		Account acc = new Account(1, handle);
  		accounts.add(acc);
      return 1;
  	}
  }

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
    if(id==0){
      throw new AccountIDNotRecognisedException ("You tried to delete the admin account. Please try again");
    }
    for (Account i : accounts){
      if (i.getID() == id){
        int temp=-1;
        try{
          for (int x = 0; x<i.getPosts().size(); x++){
            temp = i.getFirstPostID();
            deletePost(temp);
          }
          for (int x = 0; x<i.getEndorsements().size(); x++){
            temp = i.getFirstEndorsementID();
            deletePost(temp);
          }
          for (int x = 0; x<i.getComments().size(); x++){
            temp = i.getFirstCommentID();
            deletePost(temp);
          }
        } catch(PostIDNotRecognisedException e){
          System.out.print(e);
        }
        accounts.remove(i);
        return;
      }
    }
    throw new AccountIDNotRecognisedException ("The id isnt recognized " + id);
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        int x=newHandle.length();
        if (x > 30){
          throw new InvalidHandleException ("The new handle you have entered is longer than 30 characters. Pelase try again.");
        }else if (x == 0){
          throw new InvalidHandleException ("The new handle you have entered is empty. Pelase try again.");
        }else if(newHandle.indexOf(" ") != -1){
          throw new InvalidHandleException ("The new handle you have entered contains a white space. Pelase try again.");
        }
        if(accounts.size()>0){
          for (Account i : accounts){
            if (i.getUsername()==newHandle){
              throw new IllegalHandleException("The new handle you have entered is already in use. Pelase try again.");
            }
          }
          Account acc=null;
          for (Account i : accounts){
            if (i.getUsername() == oldHandle){
              acc=i;
            }
          }if(acc==null){throw new HandleNotRecognisedException("The handle you have entered does not match any in our records. Please try again.");}
          acc.setUsername(newHandle);
        }
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
    String output;
    Account acc=null;
    for (Account i : accounts){
      if (i.getUsername() == handle){
        acc=i;
      }
    }if(acc==null){throw new HandleNotRecognisedException("The handle you have entered does not match any in our records. Please try again.");}
    output = "ID: " + acc.getID() + "\nHandle: " + acc.getUsername() + "\nDescription: " + acc.getDescription() + "\nPost count:" + acc.getPostCount() + "\nEndorsement count: " + acc.getEndorsementCount();
    return output;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
    int x=message.length();
    if (x > 100){
      throw new InvalidPostException ("The message you have entered is longer than 100 characters. Pelase try again.");
    }else if (x == 0){
      throw new InvalidPostException ("The message you have entered is empty. Pelase try again.");
    }
    Account acc=null;
    for (Account i : accounts){
      if (i.getUsername() == handle){
        acc=i;
      }
    }if(acc==null){throw new HandleNotRecognisedException("The handle you have entered does not match any in our records. Please try again.");}
    if(allPosts.size() == 0){
      Original og = new Original(1, acc.getID(), message);
      acc.addOriginal(og);
      allPosts.add(og);
      return og.getID();

    }
    Original og = new Original(allPosts.get(allPosts.size()-1).getID()+1, acc.getID(), message);
    acc.addOriginal(og);
    allPosts.add(og);
    return og.getID();
  }

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        for (Post i: allPosts){
          if(i.getID() == id){
            if(i instanceof Endorsement){
              throw new NotActionablePostException ("The post you are trying to endorse is already an endorsement. Please try again.");
            }
            Account acc=null;
            for (Account x : accounts){
              if (x.getUsername() == handle){
                acc=x;
              }
            }if(acc==null){throw new HandleNotRecognisedException("The handle you have entered does not match any in our records. Please try again.");}


            int uid =acc.getID();
            String text = "EP@" + i.getAuthorID() + ": " + i.getBody();
            Endorsement en = new Endorsement (allPosts.get(allPosts.size()-1).getID()+1, acc.getID(), text, id);
            for (Account x : accounts){
              if (x.getID() == i.getAuthorID()){
                if (x.getPost(id).checkDoubleEndorsements(uid) == false){
                  return -1;//if this user already endorsed the post then reutnr -1 so no new endorsement is created and the platform is unchanged
                }
                allPosts.add(en);
                acc.addEndorsement(en);
                x.endorsePost(en, id);
              }
            }
            return en.getID();
          }
        }throw new PostIDNotRecognisedException ("The ID isnt recognized " + id);
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
    if (message.length() > 100){
      throw new InvalidPostException ("The comment you are trying to post has more than 100 characters. Please try again.");
    }
    else if(message.length() == 0){
      throw new InvalidPostException ("The comment youa re trying to post is empty. Please try again.");
    }
		for (Post i: allPosts){
      if(i.getID() == id){
        if(i instanceof Endorsement){
          throw new NotActionablePostException ("The post you are trying to endorse is already an endorsement. Please try again.");
        }
        Account acc=null;
        for (Account x : accounts){
          if (x.getUsername() == handle){
            acc=x;
          }
        }if(acc==null){throw new HandleNotRecognisedException("The handle you have entered does not match any in our records. Please try again.");}
        int uid = acc.getID();
        Comment com;
        if (i instanceof Comment){
          com = new Comment(allPosts.get(allPosts.size()-1).getID()+1, acc.getID(), message, acc.getRootIDPost(i.getID()));
        }else{
          com = new Comment(allPosts.get(allPosts.size()-1).getID()+1, acc.getID(), message, id);
        }
        allPosts.add(com);
        acc.addComment(com);
        for (Account x : accounts){
          if (x.getID() == i.getAuthorID()){
            x.commentPost(com, id);
          }
        }
        return com.getID();
      }
    }throw new PostIDNotRecognisedException ("The ID isnt recognized " + id);
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		for (Post i: allPosts){
      if(i.getID() == id){
        Account acc=null;
        int aid = i.getAuthorID();
        for (Account x : accounts){
          if (aid == x.getID()){
            acc=x;
          }
        }if (acc==null){return;};
        if(i instanceof Original){
          acc.removeOriginal(id);
          i.setBody("The original content was removed from the system and is no longer available.");
          i.setAuthorID(0);
          accounts.get(0).addOriginal((Original) i);
          return;
        }
          for (Post x : allPosts){
            int rootID = acc.getRootIDPost(id);
            if (x.getID() == rootID){
              for (Account z : accounts){
                if (z.getID() == x.getAuthorID()){
                  if (i instanceof Endorsement){
                    acc.removeEndorsement(id);
                    z.removeEndorsementFromPosts(rootID, id);
                    allPosts.remove(i);
                    return;
                  }else if(i instanceof Comment){
                    acc.removeComment(id);
                    i.setBody("The original content was removed from the system and is no longer available.");
                    i.setAuthorID(0);
                    accounts.get(0).addComment((Comment) i);
                    return;
                  }
                }
              }
            }
          }
    }
	}throw new PostIDNotRecognisedException ("The ID isnt recognized " + id);
}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for (Post i : allPosts){
      if (i.getID() == id){
        Account acc=null;
        for (Account x : accounts){
          if (x.getID() == i.getAuthorID()){
            acc = x;
            break;
          }
        }if (acc==null){return "";}
        String text ="ID: " + i.getID() + " \nAccount: " + acc.getUsername();
        if(i instanceof Endorsement){
          return text;
        }else if (i instanceof Comment){
          text = text + " \nNo. endorsements: " + acc.getComment(id).getLenEndorsements();
          int temp = i.getRootID();
          int authorOGID = -1;
          int postOGID = -1;
          while (true){
            for (Post z : allPosts){
              if (z.getID() == temp){
                temp = z.getRootID();
                authorOGID = z.getAuthorID();
                postOGID = z.getID();

                break;
              }
            }
            if(temp == -1){
              break;
            }
          }
          for (Account z : accounts){
            if (z.getID() == authorOGID){
              if(z.getPost(postOGID).isLastComment(id) == true){
                text = text + " | No. comments: " + 0 ;
              }
              else{
                text = text +" | No. comments: " + 1;
              }
            }
          }
        }
        else{
          text = text + " \nNo. endorsements: " + acc.getPost(id).getLenEndorsements() + " | No. comments: " + acc.getPost(id).getLenComments();
        }
        text = text +" \n" + i.getBody() + " \n";
        return text;
      }
    }throw new PostIDNotRecognisedException ("The ID isnt recognized " + id);
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		for (Post i : allPosts){
      if (i.getID() == id){
        StringBuilder sb=new StringBuilder();
        if (i instanceof Endorsement){
          throw new NotActionablePostException ("The post you entered has no children because it is an Endorsement. Please try again");
        } else if (i instanceof Original){
          sb.append(showIndividualPost(id));
          Original og = (Original) i;
          for (Comment c : og.getComments()){
            sb.append("|\n| > " + showIndividualPost(c.getID()));
          }
          sb = indent(sb);
          return sb;
        } else{
          Comment com = (Comment) i;
          for (Post x : allPosts){
            if (x.getID() == com.getRootID()){
              sb.append(showIndividualPost(id));
              Original og = (Original) x;
              for (Comment c : og.getComments()){
                if (c.getID() > id){
                  sb.append("|\n| > " + showIndividualPost(c.getID()));
                }
              }sb = indent(sb);
              return sb;
            }
          }
        }
      }
    } throw new PostIDNotRecognisedException ("The ID isnt recognized " + id);
	}
  public StringBuilder indent (StringBuilder sb){
    //This indents the Stringbuilder by the appropriate ammount as per the assignment
    String temp = sb.toString();
    String splits [];
    sb.setLength(0);
    splits = temp.split("\\n");
    int counter =0;
    for(String i : splits){
      for (int x=0; x<counter; x++){
        i = "    " + i;
      }
      sb.append(i + "\n");
      if (i.indexOf("| > ") !=-1){counter++;}
    }
    return sb;
  }
	@Override
	public int getMostEndorsedPost() {
    int[] values = {-1, -1};
		for (Account i : accounts){
      int[] temp = i.getMostEndorsed();
      if (temp[0] > values[0]){
        values = temp;
      }
    }
		return values[1];
	}

	@Override
	public int getMostEndorsedAccount() {
    int id=-1;
    int temp = -1;
		for (Account i : accounts){
      if(i.getEndorsementCount() > temp){
        temp = i.getEndorsementCount();
        id = i.getID();
      }
    }
		return id;
	}

	@Override
	public void erasePlatform() {
    try{
      new FileWriter("socialmedia/classes.ser", false).close();
    } catch(IOException e){
      System.out.print(e);
    }
	}

	@Override
	public void savePlatform(String filename) throws IOException {
    try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream (filename))){
      for (Post i : allPosts){
        out.writeObject(i);
      }
      for(Account i : accounts){
        out.writeObject(i);
      }
      Object a=null;
      out.writeObject(a);
    }
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
    if(new File(filename).length() == 0){return;}
    FileInputStream file = new FileInputStream(filename);
    ObjectInputStream in = new ObjectInputStream(file);
    Object obj = in.readObject();
    Post p1 = null;
    Account a1 = null;
    while (obj != null){
      try{
        if (obj instanceof Original){
          p1 = (Original) obj;
          allPosts.add(p1);
        }else if(obj instanceof Endorsement){
          p1 = (Endorsement) obj;
          allPosts.add(p1);
        }else if(obj instanceof Comment){
          p1 = (Comment) obj;
          allPosts.add(p1);
        }else if(obj instanceof Account){
          a1 = (Account) obj;
          accounts.add(a1);
        }
        obj = in.readObject();
      } catch (EOFException eofEx){
        in.close();
        break;
      }
    }
  }
}
