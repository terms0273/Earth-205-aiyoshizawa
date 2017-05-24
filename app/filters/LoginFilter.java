/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import play.mvc.Security;
import play.mvc.Result;
import play.mvc.Http.Context;
/**
 *
 * @author y-aiyoshizawa
 */
public class LoginFilter extends Security.Authenticator {
    @Override
    public String getUsername(Context ctx){
        return ctx.session().get("id");
    }
    
    @Override
    public Result onUnauthorized(Context ctx){
        String returnUrl = ctx.request().uri();
        if(returnUrl == null){
            returnUrl = "/";
        }
        ctx.session().put("returnUrl",returnUrl);
        return redirect(controllers.routes.UserController.login());
    }
}
