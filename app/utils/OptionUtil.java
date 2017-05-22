/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.util.List;
import play.libs.F;
/**
 *
 * @author y-aiyoshizawa
 */
public class OptionUtil {
    public static <A> F.Option<A> apply(A value){
        if(value != null){
            return F.Option.Some(value);
        }else{
            return F.Option.None();
        }
    }
    public static <A> F.Option<List<A>> apply(List<A> value){
        if(value != null && value.size() != 0){
            return F.Option.Some(value);
        }else{
            return F.Option.None();
        }
    }
}
