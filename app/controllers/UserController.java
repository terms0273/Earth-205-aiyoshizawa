package controllers;

import models.User;
import play.data.Form;
import play.mvc.*;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;
import util.BCrypt;

import views.html.*;



/**
 *
 * @author y-aiyoshizawa
 */
public class UserController extends Controller{
    public static Result login() {
        Form form = new Form(User.class);
        return ok(login.render(form));
    }
    public static Result doLogin(){
        boolean errFlag = false;
        Form<User> form = new Form(User.class).bindFromRequest( );
        
        if(!form.hasErrors()){
            //dbチェック
            User formUser = form.get();
            User getUser = User.find.where().eq("user_id",formUser.getUserId()).findUnique();
            
            String formPw = formUser.getPassword();
            String dbPw = getUser.getPassword();
            if(BCrypt.checkpw(formPw,dbPw)){
                //sessionの登録

            }else{
                System.out.println("パスワードが一致しない");
                errFlag = true;
            }
        }else{
            System.out.println("アノテーションエラー");
            errFlag = true;
        }
        if(!errFlag){
            return redirect(routes.UserController.index());
        }else{
            return badRequest(login.render(form));
        }
    }
    public static Result index() {
        Form form = new Form(User.class);
        return ok(index.render(form));
    }
    public static Result register() {
        Form form = new Form(User.class);
        return ok(register.render(form));
    }
    public static Result create(){
        Form<User> form = new Form(User.class).bindFromRequest();
        if(!form.hasErrors()){
            User formUser = form.get();
            formUser.save();
             return redirect(routes.UserController.login());
        }else{
            return badRequest(register.render(form));
        }
    }
}
