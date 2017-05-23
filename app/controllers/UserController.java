package controllers;

import java.util.*;
import models.User;
import dto.*;
import Services.*;
import play.libs.F.*;
import play.data.Form;
import play.mvc.*;
import org.mindrot.jbcrypt.BCrypt;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;


import views.html.*;



/**
 *
 * @author y-aiyoshizawa
 */
public class UserController extends Controller{
    public static Result login() {
        Form form = new Form(LoginUser.class);
        return ok(login.render(form));
    }
    public static Result doLogin(){
        Form<LoginUser> form = new Form(LoginUser.class).bindFromRequest();
        if(form.hasErrors()){
            return badRequest(login.render(form));
        }
        
        LoginUser loginUser = form.get();
        
        Option<User> optionUser = UserModelService.login(loginUser);
        if(!optionUser.isDefined()){
            //TODO:パスワードまたはIDが間違っている
            return badRequest(login.render(form));
        }
        session("id",String.valueOf(optionUser.get().getId()));
        return redirect(routes.UserController.index());
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
        //TODO:別クラスに移す
        Form<User> form = new Form(User.class).bindFromRequest();
        if(!form.hasErrors()){
            User formUser = form.get();
            formUser.passwordHashSave();
             return redirect(routes.UserController.login());
        }else{
            return badRequest(register.render(form));
        }
    }
    public static Result userList(){
        Option<List<User>> optionUserList = UserModelService.getUserList();
        if(optionUserList.isDefined()){
            //TODO:削除したい値が見つからないエラー
        }
        return ok(userList.render(optionUserList.get()));
    }
    public static Result delete(long id){
        UserModelService.deleteUser(id);
        return redirect(routes.UserController.userList());
    }
    public static Result edit(){
        long id = Long.parseLong(session("id"));
        User user = User.find.byId(id);
        EditUser editUser = new EditUser();
        editUser.userId = user.getUserId();
        editUser.nickName = user.getNickName();
        
        Form<EditUser> editUserForm = new Form(EditUser.class).fill(editUser);
        Form<EditUserPassword> editUserPasswordForm = new Form(EditUserPassword.class);
        
        return ok(edit.render(editUserForm,editUserPasswordForm));
    }
    public static Result update(){
        Form<EditUser> editUserForm = new Form(EditUser.class).bindFromRequest();
        if(editUserForm.hasErrors()){
            Form<EditUserPassword> editUserPasswordForm = new Form(EditUserPassword.class);
            return badRequest(edit.render(editUserForm,editUserPasswordForm));
        }
        long id = Long.parseLong(session("id"));
        UserModelService.updateUser(id, editUserForm.get());
        return redirect(routes.UserController.index());
    }
    public static Result passwordUpdate(){
        Form<EditUserPassword> editUserPasswordForm = new Form(EditUserPassword.class).bindFromRequest();
        if(editUserPasswordForm.hasErrors()){
            Form<EditUser> editUserForm = new Form(EditUser.class);
            return badRequest(edit.render(editUserForm,editUserPasswordForm));
        }
        long id = Long.parseLong(session("id"));
        UserModelService.updatePasswordUser(id, editUserPasswordForm.get());
        return redirect(routes.UserController.index());

    }
    public static Result logout(){
        session().clear();
        return redirect(routes.UserController.login());
    }
}
