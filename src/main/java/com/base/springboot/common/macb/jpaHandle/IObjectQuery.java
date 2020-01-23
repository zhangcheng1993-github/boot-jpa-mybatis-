package com.base.springboot.common.macb.jpaHandle;

import java.util.List;
import java.util.Map;

/**
 * @author zhangCheng
 * @title: IObjectQuery
 * @projectName springboot
 * @description: TODO
 * @date 2019/10/15 10:15
 */
public interface IObjectQuery<T> {




    /**
     * @title equalsParam
     * @description 拼接查询条件 =
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param obj 查询条件
     * @throws
     */
    public void equalsParam(String filedName,Object obj);





    /**
     * @title equalsParam
     * @description 拼接查询条件 >
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param obj 查询条件
     * @throws
     */
    public void moreParam(String filedName,Object obj);


    /**
     * @title moreEqualsParam
     * @description 拼接查询条件 >=
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param obj 查询条件
     * @throws
     */
    public void moreEqualsParam(String filedName,Object obj);




    /**
     * @title lessParam
     * @description 拼接查询条件 <
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param obj 查询条件
     * @throws
     */
    public void lessParam(String filedName,Object obj);


    /**
     * @title lessEqualsParam
     * @description 拼接查询条件 <=
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param obj 查询条件
     * @throws
     */
    public void lessEqualsParam(String filedName,Object obj);



    /**
     * @title lessEqualsParam
     * @description 拼接查询条件 in
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param objectList 查询条件list
     * @throws
     */
    public void inParam(String filedName,List<Object> objectList);

    /**
     * @title likeParam
     * @description 拼接查询条件 like
     * @author zhangCheng
     * @updateTime 2019/10/13 0013 下午 4:00
     * @param filedName 实体字段名称
     * @param objectList 查询条件
     * @throws
     */
    public void likeParam(String filedName,Object obj);



    /**
     * @Description ：查询全部数据列表
     * @author： zhangCheng
     * @return： List<T>
     * @date-Time：   2019/10/17 17:04
     */
    public List<T> findAll();


    /**
     * @Description ：根据id查询单条数据
     * @author： zhangCheng
     * @params： id:数据id
     * @return： T
     * @date-Time：   2019/10/17 17:06
     */
    public T findById(Integer id);



    /**
     * @Description ：删除单条数据
     * @author： zhangCheng
     * @params： t:实体
     * @return： void
     * @date-Time：   2019/10/17 17:49
     */
    public void delete(Integer id);


    /**
     * @Description ：删除多条数据
     * @author： zhangCheng
     * @params：
     * @return：
     * @date-Time：   2019/10/17 18:27
     */
    public void delete(String ids);


    /**
     * @Description ：添加数据
     * @author： zhangCheng
     * @params： t:实体
     * @return： void
     * @date-Time：   2019/10/17 18:28
     */
    public void insert(T t);


    /**
     * @Description ：更新数据
     * @author： zhangCheng
     * @params： t:实体
     * @return： void
     * @date-Time：   2019/10/18 14:02
     */
    public void update(T t);


}
