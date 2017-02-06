package map;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uv2sun on 15/11/9.
 */
public class TestMap {
    public static void main(String[] args) {
        Map<String, Object> m = new HashMap<String, Object>();
        String a = "a";
        m.put("s", a);
        String b = a;
        System.out.println(a == b);
        System.out.println(m.get("s") == b);
    }
}
