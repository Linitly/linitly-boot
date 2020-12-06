package org.linitly.boot.base.helper.tool;

import org.linitly.boot.base.service.SysDataDictItemCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author linxiunan
 * @date 17:11 2020/12/6
 * @description
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE - 2)
public class CacheDataDict implements CommandLineRunner {

    @Autowired
    private SysDataDictItemCacheService sysDataDictItemCacheService;

    @Override
    public void run(String... args) throws Exception {
        sysDataDictItemCacheService.setAllCache();
    }
}
