package com.yuntao.zhushou.deploy.controller.at;

import com.yuntao.zhushou.common.utils.ResponseObjectUtils;
import com.yuntao.zhushou.common.web.Pagination;
import com.yuntao.zhushou.common.web.ResponseObject;
import com.yuntao.zhushou.dal.annotation.NeedLogin;
import com.yuntao.zhushou.deploy.controller.BaseController;
import com.yuntao.zhushou.model.domain.User;
import com.yuntao.zhushou.model.query.AtProcessInstQuery;
import com.yuntao.zhushou.model.vo.AtProcessInstVo;
import com.yuntao.zhushou.service.inter.AtProcessInstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by shan on 2016/5/5.
 */
@RestController
@RequestMapping("atProcessInst")
public class AtProcessInstController extends BaseController {


    @Autowired
    private AtProcessInstService atProcessInstService;

    @RequestMapping("list")
    @NeedLogin
    public ResponseObject list(AtProcessInstQuery query) {
        User user = userService.getCurrentUser();
        query.setCompanyId(user.getCompanyId());
        Pagination<AtProcessInstVo> pagination = atProcessInstService.selectPage(query);
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        responseObject.setData(pagination);
        return responseObject;
    }



}
