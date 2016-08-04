package com.yuntao.zhushou.deploy.controller.at;

import com.yuntao.zhushou.common.utils.ResponseObjectUtils;
import com.yuntao.zhushou.dal.annotation.NeedLogin;
import com.yuntao.zhushou.deploy.controller.BaseController;
import com.yuntao.zhushou.model.domain.IdocParam;
import com.yuntao.zhushou.model.domain.IdocUrl;
import com.yuntao.zhushou.model.domain.User;
import com.yuntao.zhushou.model.param.IdocDataParam;
import com.yuntao.zhushou.model.query.IdocUrlQuery;
import com.yuntao.zhushou.model.vo.IdocUrlVo;
import com.yuntao.zhushou.model.web.Pagination;
import com.yuntao.zhushou.model.web.ResponseObject;
import com.yuntao.zhushou.service.inter.IdocUrlService;
import com.yuntao.zhushou.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by shan on 2016/5/5.
 */
@RestController
@RequestMapping("idocUrl")
public class IdocUrlController extends BaseController {


    @Autowired
    private IdocUrlService idocUrlService;

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    @NeedLogin
    public ResponseObject list(IdocUrlQuery query) {
        Pagination<IdocUrlVo> pagination = idocUrlService.selectPage(query);
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        responseObject.setData(pagination);
        return responseObject;
    }

    @RequestMapping("bind")
    @NeedLogin
    public ResponseObject bind(@RequestParam String month,@RequestParam String model,@RequestParam String stackId) {
        IdocUrlVo idocUrlVo = idocUrlService.bind(month, model, stackId);
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        responseObject.setData(idocUrlVo);
        return responseObject;
    }

    @RequestMapping("save")
    @NeedLogin
    public ResponseObject save(IdocDataParam idocDataParam) {
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        User user = userService.getCurrentUser();
        idocUrlService.save(idocDataParam,user);
        return responseObject;
    }

    @RequestMapping("update")
    @NeedLogin
    public ResponseObject update(IdocDataParam idocDataParam) {
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        User user = userService.getCurrentUser();
        idocUrlService.save(idocDataParam,user);
        return responseObject;
    }

    @RequestMapping("deleteById")
    @NeedLogin
    public ResponseObject deleteById(Long id) {
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        idocUrlService.deleteById(id);
        return responseObject;
    }



}