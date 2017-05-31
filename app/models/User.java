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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author y-aiyoshizawa
 */
@Entity
public class User extends Model{
    
    public static final int TYPE_ADMIN = 1;
    public static final int TYPE_USER = 0;
    
    @Id
    private Long id;
    
    private String userId;
    
    private String password;
    
    private String nickName;
    
    private boolean deleteFlag = false;
    
    private int type;
    
    public static Finder<Long, User> find = new Finder<>(Long.class,User.class);
    
    
    public User(String userId,String password,String nickName,int type){
        setUserId(userId);
        setPassword(password);
        setNickName(nickName);
        setDeleteFlag(false);
        
    }
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
     * @return the type
     */
    public int getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
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
    public void passwordHashSave(){
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        super.save();
    }
        /**
     * saveするときにパスワードをハッシュ化する
     */
    public void passwordHashUpadate(){
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        super.update();
    }
}
