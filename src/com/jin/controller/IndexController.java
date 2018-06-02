package com.jin.controller;

import com.jin.dao.IndexDao;
import com.jin.domin.Publish;
import com.jin.domin.Result;
import com.jin.domin.RoomInfo;
import com.jin.domin.User;
import com.redare.devframework.common.pojo.CommonResult;
import com.redare.devframework.common.pojo.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxia on 2018/5/27.
 */

@Controller
@Scope("prototype")
@RequestMapping("/")
public class IndexController {

    @Autowired
    IndexDao indexDao;


    private static final Logger logger = LogManager.getLogger(IndexController.class);

    /*
    登陆页面
     */
    @RequestMapping(value = { "/" })
    public String index(HttpServletRequest request,Model model) {
        logger.debug("debug===========");
        logger.info("info=========");
        logger.warn("warn=======");
        return  "login";
    }

    /*
    登陆成功去首页
     */
    @RequestMapping(value = { "/index/index" })
    public String toIndex(HttpServletRequest request,Model model) {
        Map<String,String> map=new HashMap<String,String>();
        return  "index";
    }

    /*
    首页管理页面
    */
    @RequestMapping(value = { "/index/firstPageManage" })
    public String firstPageManage(HttpServletRequest request,Model model) {
        return  "first_manage";
    }

    /*
    用户登陆
     */
    @RequestMapping(value = { "/login/login" })
    @ResponseBody
    public CommonResult login(HttpServletRequest request,Model model,String username,String password) {
        //判断用户名和密码
        int count=indexDao.checkUserAndPasswd(username,password);
        if (count==0){
            return  CommonResult.returnFailsWrapper("用户名或密码不对");
        }
        if (count>0){
            request.getSession().setAttribute("user",username);//塞入当前登陆用户
            return   CommonResult.returnSuccessWrapper("");
        }
        return  CommonResult.returnSuccessWrapper("");
    }
    /*
   退出登陆
    */
    @RequestMapping(value = { "/login/loginOut" })
    @ResponseBody
    public CommonResult loginOut(HttpServletRequest request,Model model,String username,String password) {
        //判断用户名和密码
        Object o= request.getSession().getAttribute("user");
        if (o!=null) request.getSession().removeAttribute("user");
        return  CommonResult.returnSuccessWrapper("");
    }


    @RequestMapping(value = { "/index/publish" })
    public String trail_first(HttpServletRequest request,Model model) {
        return  "publish_manage";
    }
    /// listPublish

    @RequestMapping(value = { "/index/listPublish" })
    @ResponseBody
    public Result listPublish(HttpServletRequest request,
                              Model model,String page,String limit,String username,String date
    ) {
        Page<Publish> pages= indexDao.listPublish(Integer.valueOf(page),Integer.valueOf(limit),username,date);
        Result result=Result.successResult();
        result.setCount(pages.getTotalCount());
        result.setData(pages.getResult());
        return  result;

    }

    //删除记录
    @RequestMapping(value = { "/index/deleteRecord" })
    @ResponseBody
    public CommonResult deleteRecord(HttpServletRequest request,
                              Model model,String id,String table
    ) {
        indexDao.deleteRecord(id,table);
        return  CommonResult.returnSuccessWrapper("");

    }

    //新增账号
    @RequestMapping(value = { "/index/createNewUser" })
    @ResponseBody
    public CommonResult createNewUser(HttpServletRequest request,
                                     Model model,String userName,String passWord
    ) {
        indexDao.createNewUser(userName,passWord);
        return  CommonResult.returnSuccessWrapper("");

    }

    //删除记录
    @RequestMapping(value = { "/index/updateUserPasswd" })
    @ResponseBody
    public CommonResult updateUserPasswd(HttpServletRequest request,
                                     Model model,String id,String newPasswd
    ) {
        indexDao.updateUserPasswd(id, newPasswd);
        return  CommonResult.returnSuccessWrapper("");

    }


    /*
    发布详情页面
   */
    @RequestMapping(value ="/index/detail")
    public String getPublishDetail(Model model,String id,HttpServletRequest request) {
        Publish publish=indexDao.getPublishById(Long.valueOf(id));
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("p",publish);
        model.addAllAttributes(map);
        return "view_detail";
    }

    /*
   发布详情页面
  */
    @RequestMapping(value ="/index/updateRoomInfo")
    public String updateRoomInfo(Model model,String id,
                                 HttpServletRequest request,String addr,String name,String value,String type,String roomUrl
                                 ) {
        addr= StringUtils.defaultString(addr,"");
        name= StringUtils.defaultString(name,"");
        roomUrl= StringUtils.defaultString(roomUrl,"");
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("addr",addr);
        map.put("name",name);
        map.put("value",value);
        map.put("type",type);
        map.put("roomUrl",roomUrl);
        model.addAllAttributes(map);
        return "edit_room";
    }


    /*
  用户管理界面
 */
    @RequestMapping(value ="/index/userManage")
    public String userManage(Model model,String id,HttpServletRequest request) {
        return "user_manage";
    }



    @RequestMapping(value = { "/index/listUsers" })
    @ResponseBody
    public Result listUsers(HttpServletRequest request,
                              Model model,String page,String limit
    ) {

        Page<User> pages= indexDao.queryListUsers(Integer.valueOf(page), Integer.valueOf(limit));
        Result result=Result.successResult();
        result.setCount(pages.getTotalCount());
        result.setData(pages.getResult());
        return  result;

    }


    //查询房间信息
    @RequestMapping(value = { "/index/queryRoomList" })
    @ResponseBody
    public Result queryRoomList(HttpServletRequest request
    ) {

       /* String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String docRoot = classpath.replaceAll("WEB-INF/classes/", "upload");
        String filePath=docRoot+ "/" + "jin";*/
        List<RoomInfo> list= indexDao.queryRoomList();
        Result result=Result.successResult();
        result.setCount(list.size());
        result.setData(list);
        return  result;

    }

    /*
    修改房间信息值
     */
    @RequestMapping(value = { "/index/editRoomInfo" })
    @ResponseBody
    public CommonResult editRoomInfo(HttpServletRequest request,String id,String linkAddr,String roomName,String initValue,String roomUrl
    ) {
        try{
            indexDao.editRoomInfo(id,linkAddr,roomName,initValue,roomUrl);
            return  CommonResult.returnSuccessWrapper("");
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.returnFailsWrapper("保存失败");
        }

    }

}
