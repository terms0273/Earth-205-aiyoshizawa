package controllers;

import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Login extends Controller {

    public static Result login() {
        Form form = new Form(User.class);
        return ok(login.render(form));
    }
    public static Result doLogin(){
        boolean errFlag = false;
        Form form = new Form(User.class).bindFromRequest();
        
        if(!form.hasErrors()){
            //dbチェック
        }else{
            errFlag = true;
        }
        if(!errFlag){
            return ok(index.render());
        }else{
            Form loginForm = new Form(User.class);
            return ok(login.render(loginForm));
        }
    }

}
