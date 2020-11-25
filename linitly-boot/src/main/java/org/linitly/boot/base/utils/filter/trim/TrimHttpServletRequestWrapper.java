package org.linitly.boot.base.utils.filter.trim;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author: linxiunan
 * @date: 2020/11/25 11:21
 * @descrption:
 */
public class TrimHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    public TrimHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);

        this.params.putAll(request.getParameterMap());
        this.modifyParameterValues();
    }

    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }
}
