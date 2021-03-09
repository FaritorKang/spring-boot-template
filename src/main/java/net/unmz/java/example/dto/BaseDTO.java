package net.unmz.java.example.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Project Name:
 * 功能描述：基础 DTO
 *
 * @author Faritor
 * @version 1.0
 * @date 2021-2-1 17:27
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
