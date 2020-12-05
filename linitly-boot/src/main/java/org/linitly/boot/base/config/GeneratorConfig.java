package org.linitly.boot.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: linxiunan
 * @date: 2020/12/5 19:46
 * @descrption:
 */
@Data
@Component
@ConfigurationProperties(prefix = "linitly.generator")
public class GeneratorConfig {

    // 控制日志和删除的所有功能(表生成和记录)
    private boolean enabled = true;

    // 控制日志的所有功能(表生成和记录)
    private boolean logEnabled = true;

    // 控制日志的记录功能
    private boolean logRecord = true;

    // 控制日志的表生成功能
    private boolean logTableGenerator = true;

    // 控制删除的所有功能(表生成和记录)
    private boolean deleteEnabled = true;

    // 控制删除的表生成功能
    private boolean deleteTableGenerator = true;

    // 控制删除的记录功能
    private boolean deleteBackupRecord = true;

    public boolean generatorLogTable() {
        return this.enabled || this.logEnabled || this.logTableGenerator;
    }

    public boolean generatorDeleteTable() {
        return this.enabled || this.deleteEnabled || this.deleteTableGenerator;
    }

    public boolean logRecord() {
        return this.enabled || this.logEnabled || this.logRecord;
    }

    public boolean deleteBackupRecord() {
        return this.enabled || this.deleteEnabled || this.deleteBackupRecord;
    }
}
