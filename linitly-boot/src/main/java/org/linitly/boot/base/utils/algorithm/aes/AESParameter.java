package org.linitly.boot.base.utils.algorithm.aes;

import lombok.Builder;
import lombok.Data;
import org.linitly.boot.base.utils.algorithm.AlgorithmEnum;

/**
 * @author: linxiunan
 * @date: 2020/11/23 13:31
 * @descrption:
 */
@Data
@Builder
public class AESParameter {

    private AlgorithmEnum algorithm;

    private Model model;

    private Padding padding;

    private OutputType outputType;

    private String key;

    private String ivKey;
}
