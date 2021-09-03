package org.study.cloud.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2019\8\17 0017.
 */
@Data
public class OrderDTO {
    private Integer id;
    private String orderNo;
    private String money;

    @JsonFormat(pattern = DateConstant.DATE_TIME, timezone = DateConstant.TIME_ZONE)
    private Date createTime;
}
