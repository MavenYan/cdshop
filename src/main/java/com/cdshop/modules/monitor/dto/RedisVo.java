package com.cdshop.modules.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisVo {
    @NotBlank
    private String key;

    @NotBlank
    private String value;
}
