package com.base.springboot.common.macb.jpaHandle;

import com.base.springboot.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author zhangCheng
 * @title: ObjectQueryImpl
 * @projectName springboot
 * @description: TODO
 * @date 2019/10/15 10:17
 */
@Component
public class ObjectQueryImpl<T,ID extends Serializable> implements IObjectQuery<T>{

    private static final Logger logger= LoggerFactory.getLogger(ObjectQueryImpl.class);

    //首先是注入EntityManager
    @PersistenceContext
    private EntityManager entitymanager;

    //当前对象
    private Class<T> entityClass;

    //静态属性object
    private  static ObjectQueryImpl object;

    //当前entity注解
    private javax.persistence.Entity entity;

    //表名字段
    private String jplTable;

    //当前字段数组
    private Field[] fields;

    //有标注的字段Map
    private Map<String,Object> annotaFieldMap=new HashMap<>();

    //查询参数Map =
    private Map<String, Object> equalsParamMap = new HashMap();

    //查询参数Map >
    private  Map<String, Object> moreParamMap  =new HashMap();

    //查询参数Map >=
    private  Map<String, Object> moreEqualsParamMap  =new HashMap();


    //查询参数Map <
    private  Map<String, Object> lessParamMap  =new HashMap();

    //查询参数Map <=
    private  Map<String, Object> lessEqualsParamMap  =new HashMap();


    //查询参数Map in
    private  Map<String, Object> inParamMap  =new HashMap();

    //查询参数Map like
    private  Map<String, Object> likeParamMap  =new HashMap();

    //init之前就将entitymanager赋值给静态变量object.entitymanager
    @PostConstruct
    public void initialize() {
        object = this;
        object.entitymanager = this.entitymanager;
    }

    /**
     * 构造方法
     */
    public ObjectQueryImpl() {
        try {
            ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
            Type typed = type.getActualTypeArguments()[0];
            this.entityClass = (Class)typed;
            this.entity=this.entityClass.getAnnotation(javax.persistence.Entity.class);
            this.jplTable=this.entity.name();

            boolean flag = this.entityClass.isAnnotationPresent(Entity.class);
            if (flag){
                this.fields = this.entityClass.getDeclaredFields();
                for (int i=0;i<this.fields.length;i++){
                    if (fields[i].isAnnotationPresent(javax.persistence.Column.class) || fields[i].isAnnotationPresent(javax.persistence.Id.class)){
                        //key；字段名  value:当前字段的标注信息
                        annotaFieldMap.put(fields[i].getName().toString(),fields[i].getAnnotation(javax.persistence.Column.class));
                    }
                }
            }
        } catch (Exception var3) {
            this.logger.warn("init query object!");
        }
    }



    /**
     * @Description ：拼接jql语句
     * @author： zhangCheng
     * @return： Map(jql:jql语句,paramList:参数列表)
     * @date-Time：   2019/10/17 15:25
     */
    private Map<String,Object> jql(){
        //返回的map数据
        Map<String,Object> returnMap=new HashMap<>();
        //JQL查询语句StringBuffer
        StringBuffer jqlBuffer=new StringBuffer();
        //查询参数值列表(匹配?占位符)
        List<Object> paramList=new ArrayList<>();

        //equalsParamMap迭代器    =
        Iterator<Map.Entry<String, Object>> equalsParamEntries = this.equalsParamMap.entrySet().iterator();
        //moreParamEntries迭代器  >
        Iterator<Map.Entry<String, Object>> moreParamEntries= this.moreParamMap.entrySet().iterator();
        //moreEqualsParamEntries迭代器 >=
        Iterator<Map.Entry<String, Object>> moreEqualsParamEntries = this.moreEqualsParamMap.entrySet().iterator();
        //lessParamEntries迭代器 <
        Iterator<Map.Entry<String, Object>> lessParamEntries= this.lessParamMap.entrySet().iterator();
        //lessEqualsParamEntries迭代器 <=
        Iterator<Map.Entry<String, Object>> lessEqualsParamEntries = this.lessEqualsParamMap.entrySet().iterator();
        //inParamEntries in
        Iterator<Map.Entry<String, Object>> inParamEntries = this.inParamMap.entrySet().iterator();
        //likeParamEntries like
        Iterator<Map.Entry<String, Object>> likeParamEntries = this.likeParamMap.entrySet().iterator();


        //所有参数迭代器Map
        Map<String,Object> allParamEntriesMap=new HashMap<>();
        // = 参数
        allParamEntriesMap.put("=",equalsParamEntries);
        // > 参数
        allParamEntriesMap.put(">",moreParamEntries);
        // >= 参数
        allParamEntriesMap.put(">=",moreEqualsParamEntries);
        // < 参数
        allParamEntriesMap.put("<",lessParamEntries);
        // <= 参数
        allParamEntriesMap.put("<=",lessEqualsParamEntries);
        // in 参数
        allParamEntriesMap.put("in",inParamEntries);
        // like 参数
        allParamEntriesMap.put("like",likeParamEntries);

        Iterator<Map.Entry<String, Object>> allParamEntries=null;

        //参数entry
        Map.Entry<String, Object> entry=null;
        //当前字段的注解Column
        javax.persistence.Column column=null;

        //参数占位符计数器
        Integer count=0;

        //符号
        String symbol="";
        //循环所有参数迭代器Map allParamEntriesMap
        for(String key:allParamEntriesMap.keySet()){
            symbol=key;
            allParamEntries=(Iterator<Map.Entry<String, Object>>)allParamEntriesMap.get(key);
            //循环拼接=条件
            while (allParamEntries.hasNext()) {
                entry = allParamEntries.next();
                if (annotaFieldMap.get(entry.getKey())==null){
                    throw new BaseException("此属性没有被@Column或者@Id标注!");
                }
                //如果是in
                if ("in".equals(symbol)){
                    jqlBuffer.append(" and o."+entry.getKey()+" "+symbol+" (?"+count+")");
                }else if ("like".equals(symbol)){
                    jqlBuffer.append(" and o."+entry.getKey()+" "+symbol+" CONCAT('%',?"+count+",'%')" );
                }else {
                    jqlBuffer.append(" and o."+entry.getKey()+symbol+"?"+count);
                }
                paramList.add(entry.getValue());
                count++;
            }
        }
        returnMap.put("jql",jqlBuffer.toString());
        returnMap.put("paramList",paramList);
        return returnMap;
    }



/*********************************************暴露给外部调用的方法***********************************************/



    @Override
    public void equalsParam(String filedName, Object obj) {
        this.equalsParamMap.put(filedName,obj);
    }

    @Override
    public void moreParam(String filedName, Object obj) {
        this.moreParamMap.put(filedName,obj);
    }

    @Override
    public void moreEqualsParam(String filedName, Object obj) {
        this.moreEqualsParamMap.put(filedName,obj);
    }

    @Override
    public void lessParam(String filedName, Object obj) {
        this.lessParamMap.put(filedName,obj);
    }

    @Override
    public void lessEqualsParam(String filedName, Object obj) {
        this.lessEqualsParamMap.put(filedName,obj);
    }

    @Override
    public void inParam(String filedName, List<Object> objectList) {
        this.inParamMap.put(filedName,objectList);
    }

    @Override
    public void likeParam(String filedName,Object obj) {
        this.likeParamMap.put(filedName,obj);
    }

    @Override
    public List<T> findAll() {
        List<T> list=null;
        Map<String,Object> retrunMap=this.jql();
        String jql="from "+this.jplTable+" o where 1=1 "+retrunMap.get("jql").toString();
            //这个是根据自己写的sql语句查询所有
        Query query = object.entitymanager.createQuery(jql);
        List<Object> paramList=(List<Object>)retrunMap.get("paramList");
        for (int i=0;i<paramList.size();i++){
            query.setParameter(i,paramList.get(i));
        }
        list=query.getResultList();
        object.entitymanager.close();
        //转化为List集合
        return list;
    }


    @Override
    public T findById(Integer id) {
        T t=null;
        t=object.entitymanager.find(entityClass,id);
        object.entitymanager.close();
        return t;
    }


    @Override
    public void delete(Integer id) {
        T t=object.entitymanager.find(entityClass,id);
        object.entitymanager.remove(t);
    }

    @Override
    public void delete(String ids) {
        String jql="delete from "+this.jplTable+" o where 1=1 and o.id IN ("+ids+")";
        Query query =object.entitymanager.createQuery(jql);
        query.executeUpdate();
    }

    @Override
    public void insert(T t) {
       object.entitymanager.persist(t);
    }

    @Override
    public void update(T t) {
        object.entitymanager.merge(t);
    }
}
