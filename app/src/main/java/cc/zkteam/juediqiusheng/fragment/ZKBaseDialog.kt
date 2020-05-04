package cc.zkteam.juediqiusheng.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.R


/**
 * 参考地址：https://www.jianshu.com/p/049082539e1b
 */
abstract class ZKBaseDialog : DialogFragment() {

    private lateinit var mContext: Context

    abstract fun getLayoutId(): Int
    abstract fun initView(view: View)
    abstract fun initListener()
    abstract fun initData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogFullScreen)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismissAllowingStateLoss()
                true
            }
            false
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return activity!!.layoutInflater.inflate(
            getLayoutId(),
            container,
            false
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initListener()
        initData()
    }

    /**
     * 关闭对话框
     */
    fun close() {
        closeDialog()
    }

    /**
     * 关闭对话框
     */
    fun closeDialog() {
        dismissAllowingStateLoss()
    }

}