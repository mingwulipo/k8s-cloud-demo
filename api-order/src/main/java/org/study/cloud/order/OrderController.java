package org.study.cloud.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.cloud.common.OrderDTO;
import org.study.cloud.common.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;

/**
 * @author lipo
 * @version v1.0
 * @date 2019-10-23 10:59
 */
@RequestMapping("order")
@RestController
@Slf4j
public class OrderController {

    /**
     * zuul转发： http://localhost:8771/api-order/order/listOrder, 转发成http://192.168.10.197:9030/order/listOrder
     * 直接请求： http://localhost:9030/order/listOrder
     * @return
     */
    @RequestMapping("listOrder")
    public Result listOrder(HttpServletRequest request) {
        log.info(request.getRequestURL().toString());
        OrderDTO dto = new OrderDTO();
        dto.setMoney("12.3");
        dto.setOrderNo("orderNo");
        dto.setCreateTime(new Date());
        return Result.ok(Collections.singletonList(dto));
    }

}
