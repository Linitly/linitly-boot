package org.linitly.boot.base.utils.filter.xss;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: linxiunan
 * @date: 2020/4/28 9:40
 * @descrption:
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final static HTMLFilter htmlFilter = new HTMLFilter();

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssEncode(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    private String xssEncode(String input) {
        return StringUtils.isNotBlank(input) ? htmlFilter.filter(input) : input;
    }
}
