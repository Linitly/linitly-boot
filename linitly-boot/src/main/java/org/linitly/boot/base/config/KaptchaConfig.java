package org.linitly.boot.base.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author: linxiunan
 * @date: 2020/12/10 16:11
 * @descrption:
 */
@Component
public class KaptchaConfig {

    private final static String CODE_LENGTH = "4";

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 设置边框
        properties.setProperty("kaptcha.border", "no");
        // 设置字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 设置图片宽度
        properties.setProperty("kaptcha.image.width", "120");
        // 设置图片高度
        properties.setProperty("kaptcha.image.height", "46");
        // 设置字体尺寸
        properties.setProperty("kaptcha.textproducer.font.size", "40");
        // 设置验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", CODE_LENGTH);
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
