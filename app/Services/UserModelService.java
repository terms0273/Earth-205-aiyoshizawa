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
    
    //privateにしてgetメソッドを作ってもいいかも
    public String message;
    
    /**
     * ログイン判定
     * @param entry
     * @return null,それ以外ならUser
     */
    public User login(LoginUser entry){
        message = "";
        
        User user = User.find.where().eq("user_id", entry.userId).findUnique();
        //user_idが見つからない
        if(user == null){
            message = "IDかPasswordまたはその両方が間違っています";
            return null; 
        }

        //user_idに削除フラグがついている
        if(user.isDeleteFlag()){
            message = "IDかPasswordまたはその両方が間違っています";
            return null; 
        }

        //passwordの比較
        String dbpw = user.getPassword();
        if(!BCrypt.checkpw(entry.password, dbpw)){
            message = "IDかPasswordまたはその両方が間違っています";
            return null;
        }
        return user;
       
    }
    /**
     * deleteFlagが立っていないユーザー一覧を返す
     * @return ユーザーリスト
     */
    public List<User> getUserList(Long id){
        message = "";
        List<User> userList = User.find.where().eq("delete_flag",false).ne("id", id).findList();
        if(userList.size() == 0){
            message = "削除できるUserが見つかりませんでした";
            return null;
        }
        return userList;
    }
    /**
     * createUserをuserに変換しDBに保存する
     * @param createUser
     * @return エラー:null,更新成功:User
     */
    public User cureateUser(CreateUser createUser){
        message = "";
        
        if(!createUser.password.equals(createUser.confirmPassword)){
            message = "ConfirmPasswordと一致しません";
            return null;
        }
        if(User.find.where().eq("user_id",createUser.userId).findRowCount() >= 1){
            message = "既に存在するIDです";
            return null;
        }
        User user = new User(
                createUser.userId,
                createUser.password,
                createUser.nickName,
                User.TYPE_USER
        );
        user.passwordHashSave();
        message = "User登録が成功しました";
        return user;
    }
    /**
     * idで引っ張ってきたデータを元にditUserで上書きする
     * @param id
     * @param editUser
     * @return エラー:null,更新成功:User
     */
    public User updateUser(Long id,EditUser editUser){
        message = "";
        User user = User.find.byId(id);
        if(user == null){
            message = "Userが見つかりません";
            return null;
        }
        //ユーザーIDに変更がある時、そのUserIDが既に存在するかどうか
        if(!user.getUserId().equals(editUser.userId) && User.find.where().eq("user_id",editUser.userId).findRowCount() >= 1){
            message = "変更しようとしているUserは既に存在します";
            return null;
        }
        
        user.setUserId(editUser.userId);
        user.setNickName(editUser.nickName);
        user.update();
        
        message = "User変更が成功しました";
        return user;
    }
    /**
     * パスワードの変更の更新
     * @param id
     * @param editUser
     * @return エラー:null,更新成功:User
     */
    public User updatePasswordUser(Long id,EditUserPassword editUserPassword){
        message = "";
        User user = User.find.byId(id);
        if(user == null){
            message = "Userが見つかりません";
            return null;
        }
        if(!editUserPassword.newPassword.equals(editUserPassword.confirmPassword)){
            message = "ConfirmPasswordと一致しません";
            return null;
        }
        if(!BCrypt.checkpw(editUserPassword.oldPassword,user.getPassword())){
            message = "OldPasswordが間違っています";
            return null;
        }        
        
        user.setPassword(editUserPassword.newPassword);
        user.passwordHashUpadate();
        
        message = "User変更が成功しました";
        return user;
    }
    /**
     * idをもらい論理削除する
     * @return idが見つからない:null,削除成功:User
     */
    public User deleteUser(long id){
        message = "";
        User user = User.find.byId(id);
        if(user == null){
            message = "Userが見つかりません";
            return null;
        }
        
        user.setDeleteFlag(true);
        user.update();
        
        message = "User削除が成功しました";
        return user;
    }
}
