package controllers;


import Models.User;
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
import play.test.FakeApplication;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends FakeApp{
    
    /**
     * ログイン成功時,メイン画面へ遷移する
     * ログイン成功時,セッションに値が入っている
     */
    @Test
    public void testLoginSuccessPage() {
        
        Map<String,String> map = new HashMap<>();
        map.put("id","205");
        map.put("password","password");
        
        Result result = route(fakeRequest(POST,"/doLogin").withFormUrlEncodedBody(map));
        
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/doLogin");
        
        assertThat(session(result)).isNotNull();
    }
    
}
