package com.dean.study.mvpdemo.presenter;

import com.dean.study.mvpdemo.bean.UserBean;
import com.dean.study.mvpdemo.model.IUserModel;
import com.dean.study.mvpdemo.model.UserModel;
import com.dean.study.mvpdemo.view.IUserView;

/**
 * Created by TianWei on 2016/4/12.
 */
public class UserPresenter {
    private IUserView mUserView;
    private IUserModel mUserModel;

    public UserPresenter(IUserView userView) {
        mUserView = userView;
        mUserModel = new UserModel();
    }

    public void saveUser(int id, String firstName, String lastName) {
        mUserModel.setId(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);

    }

    public void loadUser(int id) {
        UserBean userBean = mUserModel.load(id);
        mUserView.setLastName(userBean.getLastName());
        mUserView.setFristName(userBean.getFirstName());
    }
}
