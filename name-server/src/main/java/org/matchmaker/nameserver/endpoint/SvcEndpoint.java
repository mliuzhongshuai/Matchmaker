package org.matchmaker.nameserver.endpoint;

import org.matchmaker.common.constant.NameServerUriConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liu Zhongshuai
 * @description svc 相关endpoint
 * @date 2021-03-17 11:23
 **/
@RequestMapping
@RestController
public class SvcEndpoint {

    /**
     * svc 注册、beat
     *
     * @return http 标准响应
     */
    @PostMapping(NameServerUriConstant.SVC_REGISTER_URI)
    public ResponseEntity register() {
        return null;
    }

    /**
     * 获取健康的svc
     *
     * @return http 标准响应
     */
    @GetMapping(NameServerUriConstant.SVC_HEALTHFUL_URI)
    public ResponseEntity<List> getHealthfulSvc(@PathVariable("role") String role) {
        return null;
    }

}
