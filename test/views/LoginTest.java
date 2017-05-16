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
     */
    @Test
    public void testLogin() {
        Content html = views.html.doLogin.render(new Form(User.class));
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Earth");
        assertThat(contentAsString(html)).contains("ログイン");
    }
    /**
     * ログイン画面はID、PWの入力項目がある
     */
    @Test
    public void testLoginIdPw() {
        
    
        Content html = views.html.doLogin.render(new Form(User.class));
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("ID");
        assertThat(contentAsString(html)).contains("PW");
    }

    
    /**
     * 正常系のid入力チェック
     */
    @Test
    public void testIdCheck(){
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        ids.add("1235");
        ids.add("123567890");
        for(String id:ids){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isFalse();
        }
    }
    /**
     * 正常系のid入力チェック
     */
    @Test
    public void testIdCheckErr(){
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        ids.add("1234");
        ids.add("1235678901");
        for(String id:ids){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isTrue();
        }
    }
}
