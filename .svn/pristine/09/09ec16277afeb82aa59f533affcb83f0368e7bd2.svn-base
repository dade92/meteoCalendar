package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import bean.LoginBean;
import control.PasswordEncrypter;
import control.UserManager;
import entity.User;
import group.Group;

@RunWith(Arquillian.class)
public class UserManagerIT {
	
	@EJB
	private UserManager um;
	
	private static final String WEBAPP_SRC = "src/main/webapp";
	
	       
    @Deployment
    public static WebArchive createArchiveAndDeploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(UserManager.class)
                .addClass(LoginBean.class)
                .addClass(User.class)
                .addPackage(User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebResource(new File(WEBAPP_SRC+"/user/mainPage.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC+"/loginPage.xhtml"));
    }
    

    
    @Test
    public void UserManagerShouldBeInjected() {
        assertNotNull(um);
    }
    
    @Test
    public void newUsersShouldBelongToUserGroup() {
    	String username="davide";
    	String password="password";
    	String firstname="davide";
    	String lastname="Botti";
    	String email="davide@hotmail.it";
        User newUser = new User(username,password,firstname,lastname,email);
        um.createUser(newUser);
        assertThat(newUser.getGroupName(), is(Group.USERS));
        
    }
    
    @Test
    public void passwordsShouldBeEncryptedOnDB() {
        String email = "prova@hotmail.com";
        String password = "password";
    	String firstname="davide";
    	String lastname="Botti";
    	String username="davide3";
    	User newUser = new User(username,password,firstname,lastname,email);
        um.createUser(newUser);
        User foundUser = um.loadUserByUsername(username);
        assertNotNull(foundUser);
        assertThat(foundUser.getPassword(),is(PasswordEncrypter.encryptPassword(password)));
    }
    
    @Test
    public void userManagerShouldLoadUsers() {
    	String username="davide2";
    	User newUser = new User(username,"password","davide","botti","dade@hotmail.it");
        um.createUser(newUser);
        assertNotNull(um.loadUserByUsername(username));
    }
        
}
