package org.linitly.boot.base.utils.filter.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author: linxiunan
 * @date: 2020/4/28 17:13
 * @descrption:
 */
public class XssDeserializer extends JsonDeserializer<String> {

    private final static HTMLFilter htmlFilter = new HTMLFilter();

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String source = jsonParser.getText().trim();
        return StringUtils.isNotBlank(source) ? htmlFilter.filter(source) : source;
    }
}
