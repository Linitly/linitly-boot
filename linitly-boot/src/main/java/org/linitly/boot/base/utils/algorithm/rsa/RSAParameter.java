package org.linitly.boot.base.utils.algorithm.rsa;

import lombok.Builder;
import lombok.Data;
import org.linitly.boot.base.utils.algorithm.aes.OutputType;

/**
 * @author: linxiunan
 * @date: 2020/11/23 14:17
 * @descrption:
 */
@Data
@Builder
public class RSAParameter {

    private OutputType outputType;

    private KeySize keySize;

    private String publicKey;

    private String privateKey;
}
