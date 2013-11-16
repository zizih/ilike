package live.hz.ilike.server.model;

import live.hz.ilike.server.nio.CallBack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 7:13 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class Command {

    public String id;

    public String prompt;

    public Method method;

    private Class clzz;

    public void setId(String id) {
        this.id = id;
        this.clzz = CallBack.class;
        //通过id获得method，类型和参数是不安全的，Sorry
        try {
            method = clzz.getDeclaredMethod(this.id);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setPrompt(String prompt) {
        this.prompt = String.format("[%s]:%s", id, prompt);
    }

    public String invoke() {
        try {
            return method.invoke(clzz.newInstance()).toString();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "err...";
    }
}
