package org.matchmaker.nameserver.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.matchmaker.common.constant.NameServerUriConstant;
import org.matchmaker.common.enums.SvcRole;
import org.matchmaker.common.payload.BaseSvcInfo;
import org.matchmaker.common.payload.PayloadFactory;
import org.matchmaker.common.util.NetUtil;
import org.matchmaker.nameserver.core.SvcInformationContext;
import org.matchmaker.nameserver.core.SvcMateInformation;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Liu Zhongshuai
 * @description svc 相关endpoint
 * @date 2021-03-17 11:23
 **/
@RequestMapping
@RestController
public class SvcEndpoint {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);


    /**
     * svc 注册、beat
     *
     * @return http 标准响应
     */
    @PostMapping(NameServerUriConstant.SVC_REGISTER_URI)
    public ResponseEntity beat(@RequestBody JSONObject svcInfo) throws JSONException, JsonProcessingException {
        final String svcRoleKeyName = "svcRole";
        String svcRole = svcInfo.getString(svcRoleKeyName);

        BaseSvcInfo baseSvcInfo = (BaseSvcInfo) objectMapper.readValue(svcInfo.toString(), PayloadFactory.getClass(svcRole));
        if (null == baseSvcInfo.getSvcRole() || !StringUtils.hasText(baseSvcInfo.getIpAddress())) {
            return ResponseEntity.badRequest().build();
        }

        if (!NetUtil.ipVerify(baseSvcInfo.getIpAddress())) {
            return ResponseEntity.badRequest().build();
        }

        SvcMateInformation svcMateInformation = new SvcMateInformation(baseSvcInfo);

        SvcInformationContext.singletonInstantiate().registerOrRefresh(svcMateInformation);

        return ResponseEntity.ok().build();
    }

    /**
     * 获取健康的svc
     *
     * @return http 标准响应
     */
    @GetMapping(NameServerUriConstant.SVC_HEALTHFUL_URI)
    public ResponseEntity<List> getHealthfulSvc(@PathVariable("role") String role) {

        List<SvcMateInformation> healthfulSvcByRole = SvcInformationContext.singletonInstantiate().getHealthfulSvcByRole(SvcRole.valueOf(role));

        if (CollectionUtils.isEmpty(healthfulSvcByRole)) {
            return ResponseEntity.badRequest().build();
        }

        List<BaseSvcInfo> SvcInfos = healthfulSvcByRole.stream().map(SvcMateInformation::getSvcInfo).collect(Collectors.toList());

        return ResponseEntity.ok(SvcInfos);
    }

}
