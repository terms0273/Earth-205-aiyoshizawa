package controllers;


import .*;
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
public class ApplicationTest {
    
    /**
     * ログイン成功時,メイン画面へ遷移する
     * ログイン成功時,セッションに値が入っている
     */
    @Test
    public void testLoginSuccessPage() {
        FakeApplication app = fakeApplication();
        
        Map<String,String> map = new HashMap<>();
        map.put("Id","admin");
        map.put("Password","admin");
        
        Form<User> form = form(User.class).bind(map);
        Result result = route(fakeRequest(POST,"/doLogin").withBody(form));
        
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(contentAsString(result)).contains("ログアウト");
        assertThat(session(result)).isNotNull();
    }
    
}
