/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import dto.LoginUser;
import models.User;
import play.libs.F.*;
import utils.BCrypt;
import utils.OptionUtil;
/**
 *
 * @author y-aiyoshizawa
 */
public class UserModelService {
    public Option<User> login(LoginUser entry){
        Option<LoginUser> entryOps = OptionUtil.apply(entry);
        if(entryOps.isDefined()){
            if(User.find.where().eq("user_id", entry.userId).findList().size() > 1){
                return new None<User>(); 
            }
            User user = User.find.where().eq("user_id", entry.userId).findUnique();
            String dbpw = user.getPassword();
            if(BCrypt.checkpw(entry.password, dbpw)){
                return OptionUtil.apply(user);
            }
        }
        return new None<User>();
    }
}
