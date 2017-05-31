
import play.*;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
/**
 *
 * @author y-aiyoshizawa
 */
public class Global extends GlobalSettings{
    @Override
    public <T extends EssentialFilter> Class<T>[] filters(){
        return new Class[]{
            CSRFFilter.class
        };
    }
}
