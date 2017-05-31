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
public class EditUserPassword {
    
    @Required(message="Passwordが空白です")
    public String oldPassword;
    
    @Required(message="Passwordが空白です")
    public String newPassword;
    
    @Required(message="確認用Passwordが空白です")
    public String confirmPassword;
}
