/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import play.data.validation.Constraints.*;

/**
 *
 * @author y-aiyoshizawa
 */
public class LoginUser {
    @Required(message="UserIDが空白です")
    public String userId;
    
    @Required(message="passwordが空白です")
    public String password;
}
