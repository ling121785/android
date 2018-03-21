package toolbox.ll.com.toolbox.ui.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.apache.lucene.util.ToStringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import toolbox.ll.com.common.utility.StringUtils;
import toolbox.ll.com.common.utility.ToastUtils;
import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.ui.base.BaseActivity;

public class LoginActivity  extends BaseActivity {
    @BindView(R.id.login_et_account)
    EditText mEtAccount;
    @BindView(R.id.login_et_pwd)
    EditText mEtPwd;


    @Override
    public void beforeInit(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }

    @OnClick(R.id.login_btn_login)
    public void login(){
        String account=mEtAccount.getText().toString();
        String pwd=mEtPwd.getText().toString();
        if(StringUtils.isEmpty(account)){
            ToastUtils.showInForeground(this,"请输入手机号");
            return;
        }
        if(StringUtils.isEmpty(pwd)){
            ToastUtils.showInForeground(this,"请输入密码");
            return;
        }

    }
}
