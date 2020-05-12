package com.ismarthealth.notice.component.server.controller;


import com.ismarthealth.notice.component.server.discovery.ServiceDiscovery;
import com.ismarthealth.osp.core.common.Result;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @description: 获取服务handler入口
 * @author: Liuxk
 * @create: 2020年4月22日10:53:14
 **/
@RestController
@RequestMapping("/noticeComponent")
public class NoticeComponentController {

    @Autowired
    ServiceDiscovery serviceDiscovery;

    /**
     * @return java.lang.String
     * @description
     * @params [serverId 服务人员id, calcCode发现服务使用的算法标识]
     * @author Liuxk
     * @creat 2020/4/30
     * @修改人和其它信息
     */
    @ApiOperation(value = "获取netty集群服务信息", notes = "获取netty集群服务信息")
    @RequestMapping(value = "/getNettyServerAddress", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Result getNettyServerAddr(@RequestParam(value="serverId", required = false) @ApiParam(name = "服务人员ID") String serverId,
                                     @RequestParam(value="calcCode", required = false) @ApiParam(name = "发现服务使用的算法标识") String calcCode) throws IOException {
        String serverInfo = serviceDiscovery.discover(serverId, calcCode);
        return new Result(ReturnCodeEnum.Code1000000.getCode(), serverInfo, "获取服务信息成功", "success");
    }
}
