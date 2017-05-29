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
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
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
     * ログアウトできる
     * ログインユーザーのセッションが破棄されている
     * ログイン画面に遷移する
     */
    @Test
    public void testLoginSuccess() {
        
        Map<String,String> map = new HashMap<>();
        map.put("userId","205");
        map.put("password","password");
        
        Result result = route(fakeRequest(POST,"/doLogin").withFormUrlEncodedBody(map));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/");
        assertThat(session(result)).isNotEmpty();
        
        result = route(fakeRequest(GET,"/logout").withSession("id", "1"));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/login");
        assertThat(session(result)).isEmpty();
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
        

        assertThat(contentAsString(result)).doesNotContain(map.get("password"));
        
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentAsString(result)).contains("Login");
        //assertThat(contentAsString(result)).contains("IDかPasswordまたはその両方が間違っています");
        assertThat(session(result)).isEmpty();
    }
    
    @Test
    public void testLogout(){

    }
}