/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import util.BCrypt;

/**
 *
 * @author y-aiyoshizawa
 */
@Entity
public class User extends Model{
    public User(String userId,String password,String nickName){
        setUserId(userId);
        setPassword(password);
        setNickName(nickName);
        setDeleteFlag(false);
    }
    @Id
    private Long id;
    
    @NotBlank
    @Pattern(message = "1~20の英数字で入力してください。" ,value = "^[a-zA-Z0-9]{1,20}$")
    private String userId;
    

    @NotBlank
    @Pattern(message = "5~20の英数字で入力してください。" ,value = "^[a-zA-Z0-9]{5,20}$")
    private String password;
    
    @NotBlank
    private String nickName;
    
    private boolean deleteFlag = false;
    
    public static Finder<Long, User> find = new Finder<>(Long.class,User.class);
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * 
     * @param the id 
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }
    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    /**
     * @return the deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    /**
     * saveするときにパスワードをハッシュ化する
     */
    @Override
    public void save() {
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        super.save();
    }
}
