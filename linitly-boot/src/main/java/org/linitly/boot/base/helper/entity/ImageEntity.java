package org.linitly.boot.base.helper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: linxiunan
 * @date: 2020/12/10 13:39
 * @descrption:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

    private String name;

    private String url;

    private String thumbnailUrl;

    public ImageEntity(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
