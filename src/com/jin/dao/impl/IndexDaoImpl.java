package com.jin.dao.impl;

import com.jin.dao.IndexDao;
import com.jin.domin.Publish;
import com.jin.domin.RoomInfo;
import com.jin.domin.User;
import com.redare.devframework.common.pojo.Page;
import com.redare.devframework.common.spring.db.SpringJdbcHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wuxia on 2018/5/27.
 */
@Service
public class IndexDaoImpl  implements IndexDao {

    @Autowired
    SpringJdbcHelper jdbcHelper;


    @Override
    public Page<Publish> listPublish(Integer page, Integer pageSize,String userName,String date) {
        String addSql="";
        if (!StringUtils.isEmpty(userName)){
            addSql+=" and t2.username like '%"+userName+"%'";
        }
        if (!StringUtils.isEmpty(date)){
            addSql+=" and t1.create_time like '%"+date+"%'";
        }
        String sql="SELECT t1.id,t2.username,t1.publish_date publishDate,t1.create_time createTime,t1.title FROM t_publish t1,t_user t2\n" +
                "WHERE t1.author =t2.id  "+addSql+"  order by t1.id desc  ";
        return  jdbcHelper.queryForPageBean(sql,Publish.class,page,pageSize);
    }

    //根据table id 删除记录
    @Override
    public void deleteRecord(String id, String table) {
        jdbcHelper.update("delete from   "+table+" where id ="+ Integer.valueOf(id));
    }

    @Override
    public Publish getPublishById(Long aLong) {
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append(" SELECT t1.title, t1.publish_date publishDate, t1.content, t2.username ")
                .append(" FROM t_publish t1, t_user t2 ")
                .append(" WHERE t1.author = t2.id ")
                .append(" 	AND t1.id = "+aLong);
        return jdbcHelper.queryForBean(sqlBuf.toString(),Publish.class);
    }

    @Override
    public Page<User> queryListUsers(Integer page, Integer pageSize) {
        String sql="SELECT id,username,passwd,create_time FROM t_user where username!='admin' order by id desc   ";
        return  jdbcHelper.queryForPageBean(sql,User.class,page,pageSize);
    }

    @Override
    public void updateUserPasswd(String id, String newPasswd) {
        jdbcHelper.update("update t_user set passwd ='"+newPasswd+"'  where id ="+ Integer.valueOf(id));
    }

    @Override
    public void createNewUser(String userName, String passWord) {
            String sql="INSERT INTO t_user(userName,passWd,create_time,mobile)VALUES('"+userName+"','"+passWord+"',NOW(),'123')";
            jdbcHelper.update(sql);
    }

    @Override
    public int checkUserAndPasswd(String username, String password) {
        long count=jdbcHelper.queryForNumber("SELECT COUNT(1) FROM t_user WHERE username ='"+username+"'  AND passwd = '"+password+"'");
        if (count==0) return  0;
        if (count>0)  return 1;
        return 0;
    }

    @Override
    public List<RoomInfo> queryRoomList() {
        String sql="SELECT t.id,t.link_addr linkAddr," +
                "t.room_name roomName," +
                "t.room_url roomUrl," +
                "t.init_value initValue," +
                "t.index_type  indexType " +
                "FROM  t_index_manage t order by id asc ";
        return jdbcHelper.queryForListBean(sql,RoomInfo.class);

    }

    @Override
    public void editRoomInfo(String id, String linkAddr, String roomName, String initValue,String roomUrl) {
        String sql="UPDATE t_index_manage SET link_addr='"+linkAddr+"',room_name='"+roomName+"',room_url='"+roomUrl+"',init_value= "+initValue+" WHERE id = "+id;
        jdbcHelper.update(sql);

    }

}

