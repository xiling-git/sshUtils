package com.hxl.server;

import com.hxl.basic.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CPU extends AbstractEntity {

    //CPU核数
    private int physical;
    //CPU逻辑核数
    private int processes;
    //型号
    private String type;
    //主频
    private String frequency;

}
