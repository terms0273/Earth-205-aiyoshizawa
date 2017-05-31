package controllers;

import java.util.*;
import models.User;
import dto.*;
import Services.*;
import filters.*;
import play.libs.F.*;
import play.data.Form;
import play.mvc.*;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;


import views.html.*;



/**
 *
 * @author y-aiyoshizawa
 */
public class UserController extends Controller{
    private static UserModelService userModelService = new UserModelService();
    
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
        
        Option<User> optionUser = userModelService.login(loginUser);
        if(!optionUser.isDefined()){
            //TODO:パスワードまたはIDが間違っている
            return badRequest(login.render(form));
        }
        session("id",String.valueOf(optionUser.get().getId()));
        return redirect(routes.UserController.index());
    }
    @Security.Authenticated(LoginFilter.class)
    public static Result index() {
        Form form = new Form(User.class);
        return ok(index.render(form));
    }
    public static Result register() {
        Form form = new Form(CreateUser.class);
        return ok(register.render(form));
    }
    public static Result create(){
        Form<CreateUser> form = new Form(CreateUser.class).bindFromRequest();
        if(form.hasErrors()){
            return badRequest(register.render(form));
        }
        User user = userModelService.cureateUser(form.get());
        if(user == null){
            return badRequest(register.render(form));
        }
        return redirect(routes.UserController.login());
    }
    @Security.Authenticated(AdminFilter.class)
    public static Result userList(){
        Option<List<User>> optionUserList = userModelService.getUserList();
        if(optionUserList.isDefined()){
            //TODO:削除したい値が見つからないエラー
        }
        return ok(userList.render(optionUserList.get()));
    }
    @Security.Authenticated(AdminFilter.class)
    public static Result delete(long id){
        userModelService.deleteUser(id);
        return redirect(routes.UserController.userList());
    }
    @Security.Authenticated(LoginFilter.class)
    public static Result edit(){
        long id = Long.parseLong(session("id"));
        User user = User.find.byId(id);
        EditUser editUser = new EditUser(user);
        
        Form<EditUser> editUserForm = new Form(EditUser.class).fill(editUser);
        Form<EditUserPassword> editUserPasswordForm = new Form(EditUserPassword.class);
        
        return ok(edit.render(editUserForm,editUserPasswordForm));
    }
    @Security.Authenticated(LoginFilter.class)
    public static Result update(){
        Form<EditUser> editUserForm = new Form(EditUser.class).bindFromRequest();
        if(editUserForm.hasErrors()){
            
            Form<EditUserPassword> editUserPasswordForm = new Form(EditUserPassword.class);
            return badRequest(edit.render(editUserForm,editUserPasswordForm));
        }
        long id = Long.parseLong(session("id"));
        userModelService.updateUser(id, editUserForm.get());
        return redirect(routes.UserController.index());
    }
    @Security.Authenticated(LoginFilter.class)
    public static Result passwordUpdate(){
        Form<EditUserPassword> editUserPasswordForm = new Form(EditUserPassword.class).bindFromRequest();
        if(editUserPasswordForm.hasErrors()){
            long id = Long.parseLong(session("id"));
            User user = User.find.byId(id);
            EditUser editUser = new EditUser(user);
        
            Form<EditUser> editUserForm = new Form(EditUser.class).fill(editUser);
            return badRequest(edit.render(editUserForm,editUserPasswordForm));
        }
        long id = Long.parseLong(session("id"));
        userModelService.updatePasswordUser(id, editUserPasswordForm.get());
        return redirect(routes.UserController.index());

    }
    @Security.Authenticated(LoginFilter.class)
    public static Result logout(){
        session().clear();
        return redirect(routes.UserController.login());
    }
}
