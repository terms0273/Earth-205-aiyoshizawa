/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.*;
import play.db.ebean.Model.Finder;

/**
 *
 * @author y-aiyoshizawa
 */
@Entity
public class User {
    @Id
    public Long id;
    
    @Required
    @Pattern(message = "5~10の英数字で入力してください。" ,value = "[a-zA-Z0-9]{5,10}")
    public String userId;
    
    @Required
    @Pattern(message = "5~20英数字で入力してください。" ,value = "[a-zA-Z0-9]{5,20}")
    public String password;
    
    @Required
    public String nickName;
    
    public static Finder<Long, User> find = new Finder<>(Long.class,User.class);
}
