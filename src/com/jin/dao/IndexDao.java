package com.jin.dao;

import com.jin.domin.Publish;
import com.jin.domin.RoomInfo;
import com.jin.domin.User;
import com.redare.devframework.common.pojo.Page;

import java.util.List;

/**
 * Created by wuxia on 2018/5/27.
 */
public interface IndexDao {


    Page<Publish> listPublish(Integer integer, Integer integer1,String username,String date);

    void deleteRecord(String id, String table);

    Publish getPublishById(Long aLong);

    Page<User> queryListUsers(Integer integer, Integer integer1);

    void updateUserPasswd(String id, String newPasswd);

    void createNewUser(String userName, String passWord);

    int checkUserAndPasswd(String username, String password);

    List<RoomInfo> queryRoomList();

    void editRoomInfo(String id, String linkAddr, String roomName, String initValue,String roomUrl);
}
