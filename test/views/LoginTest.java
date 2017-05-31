package views;


import dto.LoginUser;
import org.junit.*;
import play.mvc.*;
import views.html.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import play.data.Form;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class LoginTest {
    
    /**
     * Login.htmlが返ってくるかをチェックする
     * ログイン画面はID、PWの入力項目がある
     */
    @Test
    public void testLogin() {
        Content html = login.render(new Form(LoginUser.class));
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Earth");
        assertThat(contentAsString(html)).contains("Login");
        assertThat(contentAsString(html)).contains("UserID");
        assertThat(contentAsString(html)).contains("Password");
    }
}
