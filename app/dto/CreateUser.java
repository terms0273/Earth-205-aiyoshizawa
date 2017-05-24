package dto;
import play.data.validation.Constraints.*;
/**
 *
 * @author y-aiyoshizawa
 */
public class CreateUser {
    @Required(message="userIdが空白です")
    public String userId;
    
    @Required(message="nickNameが空白です")
    public String nickName;
    
    @Required(message="Passwordが空白です")
    public String password;
    
    @Required(message="確認用Passwordが空白です")
    public String confirmPassword;
}
