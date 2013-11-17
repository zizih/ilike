package live.hz.ilike.server.model;

import app.CallBack;

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
        Method[] methods = clzz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(id)) {
                this.method = method;
            }

        }
    }

    public void setPrompt(String prompt) {
        this.prompt = String.format("[%s]: %s", id, prompt);
    }

    public String invoke(String... params) {
        try {
            return method.invoke(clzz.newInstance(), params).toString();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "参数不对";
    }
}
