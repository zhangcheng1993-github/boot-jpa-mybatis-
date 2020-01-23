package com.base.springboot.controller.Admin;

import com.base.springboot.common.enumPackage.TargetDs;
import com.base.springboot.common.macb.query.ListVo;
import com.base.springboot.entity.User;
import com.base.springboot.service.Admin.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    public IUserService userService;




    @TargetDs(value="lkj")
    @RequestMapping("/getObjectList/{id}")
    public String getObjectList(@PathVariable("id") Integer id, Model model){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("id",id);
        List<Object> list=userService.getObjectList(paramMap);
        model.addAttribute("list",list);
        return "admin/userManage";
    }








    @RequestMapping("/getObjectListJson/{id}")
    @ResponseBody
    public List<Object> getObjectListJson(@PathVariable("id") Integer id){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("id",id);
        List<Object> list=userService.getObjectList(paramMap);
        return list;
    }

    @RequestMapping("/getObjectPage")
    public String getObjectPage(Model model, HttpServletRequest request){
        Map<String,Object> paramMap=new HashMap<>();
        String id=request.getParameter("id");
        if (StringUtils.isNotBlank(id)){
            paramMap.put("id",id);
        }
        ListVo<Object> listVo=userService.getObjectPage("0","5",paramMap);
        model.addAttribute("list",listVo.getList());
        return "admin/userManage";
    }



    @RequestMapping("/insertUser")
    @ResponseBody
    public void insertUser(){
        logger.info("info==============");
        User user=new User();
        user.setName("cc");
        userService.insertUser(user);
    }



/********************************************JPA测试方法******************************************/

    @TargetDs(value="lkj")
    @RequestMapping("/getUserList")
    public String getUserList(Model model){
        Map<String,Object> paramMap=new HashMap<>();
//        paramMap.put("name","23");
//        paramMap.put("addDate","2019-10-17");
        List<User> list=userService.getUserList(paramMap);
        model.addAttribute("list",list);
        return "admin/userManage";
    }



    @RequestMapping("/findByIdToUser/{id}")
    public String findByIdToUser(@PathVariable("id") Integer id, Model model){
        User user=userService.findByIdToUser(id);
        model.addAttribute("user",user);
        return "admin/userManage";
    }


    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
        return "admin/userManage";
    }



    @RequestMapping("/deleteUser")
    public String deleteUser(){
        userService.deleteUser("1,2");
        return "admin/userManage";
    }


    @RequestMapping("/saveUser")
    public String saveUser(){
        User user=new User();
        user.setName("asd");
        user.setAddDate(new Date());
        user.setAge(123);
        userService.insertUser(user);
        return "admin/userManage";
    }

    @RequestMapping("/updateUser")
    public String updateUser(){
        User user=new User();
        user.setId(1);
        user.setName("kkk");
        userService.updateUser(user);
        return "admin/userManage";
    }

}
