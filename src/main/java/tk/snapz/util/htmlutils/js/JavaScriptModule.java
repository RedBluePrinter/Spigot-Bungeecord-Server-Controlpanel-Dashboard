package tk.snapz.util.htmlutils.js;

import tk.snapz.util.ThreadSafeList;
import tk.snapz.util.TwoPartObject;

public class JavaScriptModule {
    public String name = "JSModule";
    public ThreadSafeList<TwoPartObject> jsfList = null;
    public JavaScriptModule(String name) {
        this.name = name;
        jsfList = new ThreadSafeList<TwoPartObject>();
    }
    public void addFunction(JavaScriptFunction function) {
        jsfList.add(new TwoPartObject(function.name, function));
    }
    public void removeFunction(String name) {
        jsfList.removeIf(two -> two.part1.equals(name));
    }

    public ThreadSafeList<TwoPartObject> getFunctions() {
        return jsfList;
    }
}
