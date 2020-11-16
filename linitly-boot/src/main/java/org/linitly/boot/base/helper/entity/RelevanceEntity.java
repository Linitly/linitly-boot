package org.linitly.boot.base.helper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: linxiunan
 * @date: 2020/5/22 11:40
 * @descrption:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelevanceEntity<R, S, T> {

    private R id;

    private S oneTableId;

    private T manyTableId;
}
