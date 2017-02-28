package com.dean.study.mvpdemo.model;

import com.dean.study.mvpdemo.bean.UserBean;

/**
 * Created by TianWei on 2016/4/12.
 */
public interface IUserModel {
    void setId(int id);

    int getId();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    UserBean load(int id);
    //    void save(int id,UserBean bean);

}
