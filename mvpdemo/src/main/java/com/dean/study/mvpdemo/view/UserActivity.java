package com.dean.study.mvpdemo.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dean.study.mvpdemo.R;
import com.dean.study.mvpdemo.presenter.UserPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户界面
 * Created by TianWei on 2016/4/12.
 */
public class UserActivity extends Activity implements IUserView {

    @Bind(R.id.id_edt)
    EditText mIdEdt;
    @Bind(R.id.first_name_edt)
    EditText mFirstNameEdt;
    @Bind(R.id.last_name_edt)
    EditText mLastNameEdt;
    @Bind(R.id.saveButton)
    Button mSaveButton;
    @Bind(R.id.loadButton)
    Button mLoadButton;
    private UserPresenter mUserPresenter;

    //    @Nullable
    //    @OnClick(R.id.saveButton)
    //    void onSaveClicked() {
    //        mUserPresenter.saveUser(getId(), getFristName(), getLastName());
    //    }
    //
    //    @Nullable
    //    @OnClick(R.id.loadButton)
    //    void onLoadClicked() {
    //        mUserPresenter.loadUser(getId());
    //    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserPresenter = new UserPresenter(this);
        ButterKnife.bind(this);
    }

    @Override
    public int getId() {
        return Integer.parseInt(mIdEdt.getText().toString());
    }

    @Override
    public String getFristName() {
        return mFirstNameEdt.getText().toString();
    }

    @Override
    public String getLastName() {
        return mLastNameEdt.getText().toString();
    }

    @Override
    public void setFristName(String fristName) {
        mFirstNameEdt.setText(fristName);
    }

    @Override
    public void setLastName(String lastName) {
        mLastNameEdt.setText(lastName);
    }

    @OnClick({R.id.saveButton, R.id.loadButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                mUserPresenter.saveUser(getId(), getFristName(), getLastName());
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.loadButton:
                mUserPresenter.loadUser(getId());
                Toast.makeText(this,"读取成功",Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
