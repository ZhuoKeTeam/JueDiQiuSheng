package cc.zkteam.juediqiusheng.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cc.zkteam.juediqiusheng.R
import kotlinx.android.synthetic.main.activity_toolbar_demo.*

class ToolbarActivityDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_demo)
        val mToolbar: Toolbar = toolbar
        mToolbar.title = "hello2!hello3hello4hello5hello6hello7hello8hello9" // 可以在 setSupportActionBar 之前设置文字，也可以如下直接使用 supportActionBar!!.title

        mToolbar.setTitleMargin(0, 0,0,0) // 在固定的 toobar 高度和宽度的文字中，间距左上右下的间距

        mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title)

        //设置Toolbar, 一定要先设置这个
        setSupportActionBar(mToolbar)
        //显示NavigationIcon,这个方法是ActionBar的方法.Toolbar没有这个方法.
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        //设置icon
//        mToolbar.setNavigationIcon(drawable)
//        //设置监听.必须在setSupportActionBar()之后调用
//        mToolbar.setNavigationOnClickListener(clickListener)


        //是否显示文字和右边的 menu，.setNavigationOnClickListener()必须要在setSupportActionBar()之后调用才能生效.
        // 因为setSupportActionBar(Toolbar),会将Toolbar转换成 ActionBar.点击监听也会重新设置.
        supportActionBar!!.setDisplayShowTitleEnabled(true)
//        supportActionBar!!.title = "hello!" 可以这么设置，也可以如下


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        setIconsVisible(menu, true)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * 每次菜单被关闭时调用.
     *菜单被关闭有三种情形:
     *1.展开menu的按钮被再次点击
     *2.back按钮被点击
     *3.用户选择了某一个菜单项
     */
    override fun onOptionsMenuClosed(menu: Menu?) {
        super.onOptionsMenuClosed(menu)
    }

    /**
     * 在onCreateOptionsMenu执行后，菜单被显示前调用；如果菜单已经被创建，则在菜单显示前被调用。 同样的，
     * 返回true则显示该menu,false 则不显示; （可以通过此方法动态的改变菜单的状态，比如加载不同的菜单等）
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 显示menu的icon,通过反射,设置Menu的icon显示.
     * @param view
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    override fun onPrepareOptionsPanel(view: View?, menu: Menu?): Boolean {
        if (menu != null) {
            if (menu::class.java.simpleName == "MenuBuilder") {
                try{

                    val method = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    Log.e("TAG", "onMenuOpened...unable to set icons for overflow menu", e)
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu)
    }

}