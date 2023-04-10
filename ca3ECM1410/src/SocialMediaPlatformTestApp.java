
import socialmedia.*;
import java.util.ArrayList;
import java.io.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 *
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		MiniSocialMedia platform = new MiniSocialMedia();
		int i=0;
		Integer id;
		try {
			platform.erasePlatform();
			platform.loadPlatform("socialmedia/classes.ser");
			assert (platform.getNumberOfAccounts() == 1) : "Innitial SocialMediaPlatform not empty as required.";
			assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
			assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
			assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
			id = platform.createAccount("my_handle");
			id = platform.createAccount("Second_Account");
			assert (platform.getNumberOfAccounts() == 3) : "number of accounts registered in the system does not match";
			platform.changeAccountHandle("my_handle", "my_new_handle");
			assert(platform.createPost("my_new_handle", "This is my first post")==1) : "We have a problem";
			assert(platform.createPost("my_new_handle", "This is my Second post")==2) : "We have a problem";
			assert(platform.endorsePost("Second_Account", 1)==3) : "We have a problem";
			assert(platform.endorsePost("Second_Account", 1)==-1) : "We have a problem";
			assert(platform.commentPost("Second_Account", 2, "This is my first comment.") == 4) : "We have a new problem";
			platform.deletePost(3);
			System.out.println(platform.showPostChildrenDetails(2) + "ABOVE ME");
			System.out.print(platform.showIndividualPost(4));
			System.out.println(platform.showAccount("my_new_handle"));
			System.out.println(platform.showAccount("Second_Account"));
			platform.savePlatform("socialmedia/classes.ser");
			platform.erasePlatform();
		} catch (IllegalHandleException e) {
			System.out.print(e);
		} catch (InvalidHandleException e) {
			System.out.print(e);
		} catch(HandleNotRecognisedException e){
			System.out.print(e);
		} catch(InvalidPostException e){
			System.out.print(e);
		} catch(PostIDNotRecognisedException e){
			System.out.print(e);
		} catch(NotActionablePostException e){
			System.out.print(e);
		} catch(ClassNotFoundException e){
			System.out.print(e);
		} catch(IOException ex){
			System.out.print(ex);
		}

	}

}
