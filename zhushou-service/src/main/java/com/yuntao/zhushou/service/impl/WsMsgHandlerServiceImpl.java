package com.yuntao.zhushou.service.impl;

import com.yuntao.zhushou.common.constant.MsgConstant;
import com.yuntao.zhushou.common.utils.JsonUtils;
import com.yuntao.zhushou.common.web.MsgRequestObject;
import com.yuntao.zhushou.common.web.MsgResponseObject;
import com.yuntao.zhushou.common.web.ShellExecObject;
import com.yuntao.zhushou.model.domain.AppVersion;
import com.yuntao.zhushou.model.domain.DeployLog;
import com.yuntao.zhushou.model.domain.Host;
import com.yuntao.zhushou.model.domain.User;
import com.yuntao.zhushou.model.enums.AppVerionStatus;
import com.yuntao.zhushou.model.enums.DeployLogType;
import com.yuntao.zhushou.model.query.HostQuery;
import com.yuntao.zhushou.service.inter.*;
import com.yuntao.zhushou.service.job.CheckServerStatusJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by tangshengshan on 17-1-1.
 */
@Service
public class WsMsgHandlerServiceImpl extends AbstService implements WsMsgHandlerService {

    @Autowired
    private AppService appService;

    @Autowired
    private AppFrontService appFrontService;

    @Autowired
    private DeployLogService deployLogService;

    @Autowired
    private HostService hostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CheckServerStatusJob checkServerStatusJob;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DeployService deployService;


    /**
     * 发布日志队列,一个服务器一个实例
     */
    private Queue<String> deployLogQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void receiveHandler(MsgRequestObject requestObject) {
        //
    }

    @Transactional
    private void saveLog(Long userId,String appName, String model, String method,boolean deployFront, List<String> ipList,String type) {
        User user = userService.findById(userId);

        //insert log
        String content = null;
        if(!deployFront){
            HostQuery hostQuery = new HostQuery();
            hostQuery.setCompanyId(user.getCompanyId());
            List<Host> hostList = hostService.selectList(hostQuery);
            List<String> hostNameList = new ArrayList(ipList.size());
            //get hostNameList
            for (String ip : ipList) {
                for (Host host : hostList) {
                    if (StringUtils.equals(ip, host.getEth0())) {
                        hostNameList.add(host.getName());
                        break;
                    }
                }
            }
            //end
            content = user.getNickName() + "【" + method + "】了节点【 " + StringUtils.join(hostNameList, ",") + " 】";
        }else{
            content = user.getNickName()  + "【" + method + "】了【"+model+"】【 " + type + " 】";
        }

        DeployLog deployLog = new DeployLog();
        deployLog.setCompanyId(user.getCompanyId());
        deployLog.setAppName(appName);
        deployLog.setModel(model);
        deployLog.setContent(content);
        deployLog.setUserId(user.getId());
        deployLog.setUserName(user.getNickName());
        if (StringUtils.isNotEmpty(type)) {
            if(type.equals(DeployLogType.android.getDescription())){
                deployLog.setType(DeployLogType.android.getCode());
            }else{  //ios
                deployLog.setType(DeployLogType.ios.getCode());
            }
        }else{
            deployLog.setType(DeployLogType.server.getCode());
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder backVerSb = new StringBuilder();
        String backVer = null;
        while (!deployLogQueue.isEmpty()) {
            String deployLogMsg = deployLogQueue.poll();
            //备份版本处理
            if (StringUtils.indexOf(deployLogMsg, "back_ver=") != -1) {
                String backVerStr = deployLogMsg.split("=")[1];
                if (backVer == null) {
                    backVer = backVerStr.split(",")[0];
                }
                String backVerIp = backVerStr.split(",")[1];
                backVerSb.append(backVerIp);
                backVerSb.append(",");
            }
            sb.append(deployLogMsg);
            sb.append("\r\n");

        }
        if (backVerSb.length() > 0) {
            deployLog.setBackVer(backVer + "," + backVerSb.delete(backVerSb.length() - 1, backVerSb.length()).toString());
        }
        //添加自动发布日志 TODO

        //end
        deployLog.setLog(sb.toString().getBytes());
        deployLogService.insert(deployLog);

        //记录app log
        if(deployFront){  //前端发布
            appFrontService.updateLog(user.getCompanyId(),appName,content);
        }else{
            appService.updateLog(user.getCompanyId(),appName,content);
        }


    }


    @Override
    public void sendHandler(MsgResponseObject responseObject) {
        //处理发布日志
        if (responseObject.getType().equals(MsgConstant.ReqResType.USER)) {  //推送到用户端的消息
            if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.EVENT_START)){  //事件开始
                deployLogQueue.clear();
            }else if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.EVENT_END)){  //事件结束
                //存储日志
                Object data = responseObject.getData();
//                String dataStr = JsonUtils.object2Json(data);
                ShellExecObject shellExecObj = JsonUtils.json2Object(data.toString(),ShellExecObject.class);
                saveLog(shellExecObj.getUserId(),shellExecObj.getAppName(),shellExecObj.getModel(),shellExecObj.getMethod(),shellExecObj.getDeployFront(),shellExecObj.getIpList(),shellExecObj.getType());

            }else if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.SHELL)){  //脚本日志
                deployLogQueue.offer(responseObject.getData().toString());
            }else if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.SERVER_STATUS_CHECK)){  //服务状态检查
                AtomicBoolean atomicBoolean = checkServerStatusJob.execMap.get(responseObject.getKey());
                atomicBoolean.set(false);  //执行完毕
            }else if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.FRONT_DEPLOY_END)){  //前端发布完成
                Object data = responseObject.getData();
                AppVersion appVersion = JsonUtils.json2Object(data.toString(),AppVersion.class);
                if(appVersion.getStatus() == AppVerionStatus.error.getCode()){ //直接删掉
                    appVersionService.deleteById(appVersion.getId());
                }else{
                    appVersionService.updateById(appVersion);
                }
            }else if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.AUTO_DEPLOY_START)){  //自动发布开始
                deployService.changeDeployState(true);
            }else if(responseObject.getBizType().equals(MsgConstant.ReqCoreBizType.AUTO_DEPLOY_END)){  //自动发布结束
                deployService.changeDeployState(false);
            }else{
                //TODO 其他消息暂不处理
            }

        }

    }
}
