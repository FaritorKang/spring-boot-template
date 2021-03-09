package net.unmz.java.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;

/**
 * Project Name:
 * 功能描述：
 *
 * @author Faritor
 * @version 1.0
 * @date 2021-2-1 17:59
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6717813110511681961L;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
