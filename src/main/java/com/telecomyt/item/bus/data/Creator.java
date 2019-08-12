package com.telecomyt.item.bus.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Creator implements Serializable {

    private static final long serialVersionUID = -63333092095082624L;

    /**
     * 创建者昵称
     */
    private String nickName ;

    /**
     * 创建者用户名（身份证号）
     */
    private String username ;


}
