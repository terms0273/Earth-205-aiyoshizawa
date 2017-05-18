package models;


import apps.FakeApp;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.*;
import static org.fest.assertions.Assertions.*;
import play.data.Form;
import static play.data.Form.form;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class UserTest extends FakeApp{
    /**
     * 正常系のuserId入力チェック
     * 入力規則:1~20の英数字
     */
    @Test
    public void testIdCheck(){
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        ids.add("1");
        ids.add("1235678901234567890");
        for(String id:ids){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isFalse();
        }
    }
    /**
     * 異常系のuserId入力チェック
     */
    @Test
    public void testIdCheckErr(){
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        ids.add("");
        ids.add("123456789012345678901");
        for(String id:ids){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isTrue();
        }
    }
    /**
     * 正常系のpassword入力チェック
     * 入力規則:5~20の英数字
     */
    @Test
    public void testPwCheck(){
        List<String> pws = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        pws.add("12345");
        pws.add("1235678901234567890");
        for(String id:pws){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isFalse();
        }
    }
    /**
     * 異常系のpassword入力チェック
     */
    @Test
    public void testPwCheckErr(){
        List<String> pws = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        pws.add("1234");
        pws.add("12356789012345678901");
        for(String id:pws){
            map.put("Id",id);
            Form<User> form = form(User.class).bind(map);
            assertThat(form.hasErrors()).isTrue();
        }
    }
    /**
     * DBに値を入れ,DBから取得し同じかどうかチェックする
     */
    @Test
    public void testUserDbCheck(){
        
        String sql = "select id,nick_name,password from User where id=:id";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("id","205").findList();
        assertThat(sqlRows.get(0).getString("password")).isEqualTo(user.password);
        assertThat(sqlRows.get(0).getString("nick_name")).isEqualTo(user.nickName);
    }
    /**
     * dbに入れた値がModelにも入っているかチェックする
     */
    @Test
    public void testUserCheck(){
        User getUser = User.find.byId("205");
        assertThat(getUser.password).isEqualTo(user.password);
        assertThat(getUser.nickName).isEqualTo(user.nickName);
    }
}
