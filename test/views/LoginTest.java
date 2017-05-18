package views;


import Models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.*;
import play.mvc.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import play.data.Form;
import static play.data.Form.form;
import play.test.FakeApplication;


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
        Content html = views.html.doLogin.render(new Form(User.class));
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Earth");
        assertThat(contentAsString(html)).contains("ログイン");
        assertThat(contentAsString(html)).contains("ID");
        assertThat(contentAsString(html)).contains("PW");
    }
}
