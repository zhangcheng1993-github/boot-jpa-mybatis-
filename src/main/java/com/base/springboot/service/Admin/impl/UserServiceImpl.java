package com.base.springboot.service.Admin.impl;

import com.base.springboot.common.macb.jpaHandle.IObjectQuery;
import com.base.springboot.common.macb.query.ListVo;
import com.base.springboot.dao.IBaseDao;
import com.base.springboot.entity.User;
import com.base.springboot.service.Admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    public IBaseDao baseDao;


    @Override
    public List<Object> getObjectList(Map<String, Object> paramMap) {
        return baseDao.getObjectList("admin.getUserList",paramMap);
    }

    @Override
    public ListVo<Object> getObjectPage(String start, String limit, Map<String, Object> paramMap) {
        return baseDao.getObjectPage(start,limit,"admin.getUserList",paramMap);
    }

//    @Override
//    public void insertUser(User user) {
//        baseDao.insertObject("lkjadmin.insertUser",user);
//        String s="";
//        String[] arr=s.split(",");
//        String W=arr[1];
//        Map<String, Object> paramMap=new HashMap<>();
//        paramMap.put("id",2);
//        System.out.println(baseDao.getObjectList("lkjadmin.getUserList",paramMap));
//        throw new RuntimeException("");
//    }


    @Override
    public List<User> getUserList(Map<String, Object> paramMap) {
        IObjectQuery<User> query=new User();
//        query.equalsParam("name","23");
//        query.equalsParam("age",1);
//        query.moreEqualsParam("addDate","2019-10-19");

//        List<Object> inList=new ArrayList<Object>();
//        inList.add("23");
//        inList.add("er");
//        query.inParam("name",inList);

//        query.likeParam("name","2");

        List<User> list=query.findAll();
        return list;
    }


    @Override
    public User findByIdToUser(Integer id) {
        IObjectQuery<User> query=new User();
        return query.findById(id);
    }


    @Override
    public void deleteUser(Integer id) {
        IObjectQuery<User> query=new User();
        query.delete(id);
    }


    @Override
    public void deleteUser(String ids) {
        IObjectQuery<User> query=new User();
        query.delete(ids);
    }

    @Override
    public void insertUser(User user) {
        IObjectQuery<User> query=new User();
        user.insert(user);
    }

    @Override
    public void updateUser(User user) {
        IObjectQuery<User> query=new User();
        query.update(user);
    }
}
