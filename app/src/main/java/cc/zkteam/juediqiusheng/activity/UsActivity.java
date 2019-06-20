package cc.zkteam.juediqiusheng.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;

import butterknife.BindView;
import cc.zkteam.juediqiusheng.R;

public class UsActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_us;
    }

    @Override
    protected void initViews() {
        tvTitle.setText("关于我们 ");
        tvAppName.setText(AppUtils.getAppName());
        tvVersion.setText("版本号: "+AppUtils.getAppVersionName());

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
