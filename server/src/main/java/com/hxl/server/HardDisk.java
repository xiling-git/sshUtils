package com.hxl.server;

import com.hxl.basic.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HardDisk extends AbstractEntity {

    private String size;

}
