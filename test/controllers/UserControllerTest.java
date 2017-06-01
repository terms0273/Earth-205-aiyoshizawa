package controllers;


import models.User;
import apps.FakeApp;
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
import static play.mvc.Http.Status.SEE_OTHER;
import play.test.FakeApplication;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class UserControllerTest extends FakeApp{
    
    /**
     * ログイン成功時,メイン画面へ遷移する
     * ログイン成功時,セッションに値が入っている
     */
    @Test
    public void testLoginSuccess() {
        
        Map<String,String> map = new HashMap<>();
        map.put("userId","205");
        map.put("password","password");
        
        Result result = route(fakeRequest(POST,"/doLogin").withFormUrlEncodedBody(map));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/doLogin");
        
        assertThat(session(result)).isNotNull();
    }
   /**
     * ログイン失敗時,ログイン画面へ遷移する
     * ログイン失敗時,IDもしくはPasswordが間違っていますと表示される
     * ログイン失敗時,フォームの値がクリアされている
     * ログイン失敗時,セッションに値が入っていない
     */
    @Test
    public void testLoginError() {
        
        Map<String,String> map = new HashMap<>();
        map.put("userId","205");
        map.put("password","aaaa");
        
        Result result = route(fakeRequest(POST,"/doLogin").withFormUrlEncodedBody(map));
        Form<User> form = new Form(User.class).bindFromRequest();
        User getUser = form.get();
        assertThat(getUser.getUserId()).isEmpty();
        assertThat(getUser.getPassword()).isEmpty();
        
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(redirectLocation(result)).isEqualTo("/doLogin");
        assertThat(contentAsString(result)).contains("IDかPasswordまたはその両方が間違っています");
        assertThat(session(result)).isNull();
    }
}
