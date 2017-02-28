package com.dean.study.mvpdemo.model;

import android.util.SparseArray;

import com.dean.study.mvpdemo.bean.UserBean;

/**
 * [title]
 * Created by TianWei on 2016/4/12.
 */
public class UserModel implements IUserModel {
    private int mId;
    private String mFirstName;
    private String mLastName;
    private SparseArray<UserBean> mUserBeanSparseArray = new SparseArray<>();

    @Override
    public void setId(int id) {
        mId = id;
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public void setFirstName(String firstName) {
        mFirstName = firstName;

    }

    @Override
    public void setLastName(String lastName) {
        mLastName = lastName;
        UserBean userBean = new UserBean(mFirstName, mLastName);
        mUserBeanSparseArray.append(mId, userBean);
    }

    @Override
    public UserBean load(int id) {
        mId = id;
        return mUserBeanSparseArray.get(id, new UserBean("not fond", "not fond"));
    }
}
