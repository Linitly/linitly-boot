package org.linitly.boot.base.handle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.annotation.Dict;
import org.linitly.boot.base.service.SysDataDictItemCacheService;
import org.linitly.boot.base.utils.bean.SpringBeanUtil;
import org.linitly.boot.base.utils.db.ClassUtil;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author: linxiunan
 * @date: 2020/12/6 11:42
 * @descrption:
 */
@Slf4j
public class DictSerialize extends JsonSerializer {

    private SysDataDictItemCacheService sysDataDictItemCacheService = SpringBeanUtil.getBean(SysDataDictItemCacheService.class);

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String currentName = jsonGenerator.getOutputContext().getCurrentName();
        try {
            Field[] fields = ClassUtil.getAllFields(jsonGenerator.getCurrentValue().getClass(), true);
            Field currentField = null;
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().equals(currentName)) {
                    currentField = fields[i];
                }
            }
            if (currentField == null) {
                log.error("反序列化字典出错，获取不到字段，字段名为：" + currentName);
                objectMapper.writeValue(jsonGenerator, o);
                return;
            }
            Dict dict = currentField.getDeclaredAnnotation(Dict.class);
            if (dict == null) {
                objectMapper.writeValue(jsonGenerator, o);
                return;
            }
            String code = StringUtils.isNotBlank(dict.code()) ? dict.code() :
                    CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, currentName);
            String text = sysDataDictItemCacheService.getItemCache(code, o.toString());
            if (StringUtils.isBlank(text)) {
                objectMapper.writeValue(jsonGenerator, o);
            } else {
                jsonGenerator.writeString(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("反序列化字典出错，字段名为：" + currentName);
        }
    }
}
