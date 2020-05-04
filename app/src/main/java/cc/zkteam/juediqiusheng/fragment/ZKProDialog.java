package cc.zkteam.juediqiusheng.fragment;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;

import org.jetbrains.annotations.NotNull;

import cc.zkteam.juediqiusheng.R;

public class ZKProDialog extends ZKBaseDialog {

    TextView tv;
    Button greenBt;
    Button cancelBt;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_pro;
    }

    @Override
    public void initView(@NotNull View view) {
        tv = view.findViewById(R.id.proTV);
        greenBt = view.findViewById(R.id.greenBt);
        cancelBt = view.findViewById(R.id.cancelBt);
    }

    @Override
    public void initListener() {
        cancelBt.setOnClickListener(v -> {
            dismissAllowingStateLoss();
            System.exit(0);
        });
        greenBt.setOnClickListener(v -> {
            dismissAllowingStateLoss();
            SPUtils.getInstance("first_data_file").put("agree", true);
        });
    }

    @Override
    public void initData() {
        tv.setText(Html.fromHtml(getString(R.string.zk_pro)));

    }
}
