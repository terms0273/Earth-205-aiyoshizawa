/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import models.User;
import com.avaje.ebean.Ebean;
import java.io.IOException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import play.test.FakeApplication;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;


/**
 *
 * @author y-aiyoshizawa
 */
public class FakeApp {
    public static FakeApplication app;
    public static String createDb1 = "";
    public static String dropDb1 = "";
    public User user;
    
    @BeforeClass
    public static void startApp() throws IOException{
        app = fakeApplication(inMemoryDatabase());
        start(app);
        String evolutionCountent = FileUtils.readFileToString(app.getWrappedApplication().getFile("conf/evolutions/default/1.sql"));
        String[] splitEvolutionContent = evolutionCountent.split("# --- !Ups");
        String[] upsDowns = splitEvolutionContent[1].split("# --- !Downs");
        createDb1 = upsDowns[0];
        dropDb1 = upsDowns[1];
    }
    @Before
    public void createCleanDb(){
        initDb();
        
        user = new User("205","aiyoshi","password",User.TYPE_USER);
        user.passwordHashSave();
    }
    public static void initDb(){
        Ebean.execute(Ebean.createCallableSql(dropDb1));
        Ebean.execute(Ebean.createCallableSql(createDb1));
        
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache("play");
        cache.removeAll();
    }
    public static void restartApp(){
        start(app);
    }
    @AfterClass
    public static void stopApp(){
        stop(app);
    }
}
