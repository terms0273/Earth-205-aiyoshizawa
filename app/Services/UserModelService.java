/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import dto.*;
import models.User;
import play.libs.F.*;
import utils.BCrypt;
import utils.OptionUtil;
import java.util.*;
/**
 * @author y-aiyoshizawa
 * UserControllerとDBの接続部部の処理をする
 */
public class UserModelService {
    
    /**
     * ログイン判定
     * @param entry
     * @return エラーならNone<User>(),それ以外ならUser
     */
    public static Option<User> login(LoginUser entry){
        
        Option<LoginUser> entryOps = OptionUtil.apply(entry);
        if(entryOps.isDefined()){
            User user = User.find.where().eq("user_id", entry.userId).findUnique();
            //user_idが見つからない
            if(user == null){
                return new None<User>(); 
            }
            
            //user_idに削除フラグがついている
            if(user.isDeleteFlag()){
                return new None<User>(); 
            }
            
            //passwordの比較
            String dbpw = user.getPassword();
            if(BCrypt.checkpw(entry.password, dbpw)){
                return OptionUtil.apply(user);
            }
        }
        return new None<User>();
    }
    /**
     * deleteFlagが立っていないユーザー一覧を返す
     * @return ユーザーリスト
     */
    public static Option<List<User>> getUserList(){
        Option<List<User>> userList = OptionUtil.apply(User.find.where().eq("delete_flag",false).findList());
        if(userList.get().size() == 0){
            return new None<List<User>>();
        }
        return userList;
    }
    /**
     * createUserをuserに変換しDBに保存する
     * @param createUser
     * @return エラー:null,更新成功:User
     */
    public static User cureateUser(CreateUser createUser){
        if(!createUser.password.equals(createUser.confirmPassword)){
            //パスワードが一致しない
            return null;
        }
        if(User.find.where().eq("user_id",createUser.userId).findRowCount() >= 1){
            //すでに存在するID
            return null;
        }
        User user = new User(
                createUser.userId,
                createUser.password,
                createUser.nickName,
                User.TYPE_USER
        );
        user.passwordHashSave();
        return user;
    }
    /**
     * idで引っ張ってきたデータを元にditUserで上書きする
     * @param id
     * @param editUser
     * @return エラー:null,更新成功:User
     */
    public static User updateUser(Long id,EditUser editUser){
        User user = User.find.byId(id);
        if(user == null){
            return null;
        }
        //ユーザーIDに変更があるAND変更後のUserIDが既に存在する
        if(!user.getUserId().equals(editUser.userId) && User.find.where().eq("user_id",editUser.userId).findRowCount() >= 1){
            return null;
        }
        
        user.setUserId(editUser.userId);
        user.setNickName(editUser.nickName);
        user.update();
        
        return user;
    }
    /**
     * パスワードの変更の更新
     * @param id
     * @param editUser
     * @return エラー:null,更新成功:User
     */
    public static User updatePasswordUser(Long id,EditUserPassword editUserPassword){
        User user = User.find.byId(id);
        if(user == null){
            return null;
        }
        //確認用パスワードが一致していない
        if(!editUserPassword.newPassword.equals(editUserPassword.confirmPassword)){
            return null;
        }
        //現在のパスワードが間違っている
        if(!BCrypt.checkpw(editUserPassword.oldPassword,user.getPassword())){
            return null;
        }        
        
        user.setPassword(editUserPassword.newPassword);
        user.passwordHashUpadate();
        
        return user;
    }
    /**
     * idをもらい論理削除する
     * @return idが見つからない:null,削除成功:User
     */
    public static User deleteUser(long id){
        User user = User.find.byId(id);
        if(user == null){
            return null;
        }
        
        user.setDeleteFlag(true);
        user.update();
        
        return user;
    }
}
