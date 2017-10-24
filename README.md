# JueDiQiuSheng
 绝地求生 app

相关数据和接口地址是：http://zkteam.cc/JueDiQiuSheng/index.html

# 卓客工具类说明

## ZKBase
ZKBase 初始化 必须在 Application 中 ZKBase.init()。

 - ZKBase.getContext() 获取全局的 Context
 - ZKBase.isDebug() 判断当前是否是 debug 的状态


### ZKSharedPreferences
在项目中如果需要使用 SharedPreferences 文件操作，可以直接继承 ZKSharedPreferences 抽象类。

使用方式：

```
    public class TestSP extends ZKSharedPreferences {
    
        // SP 文件的 name
        private static final String SHARED_PREFERENCES_FILE_NAME = "test";
    
    
        // SharedPreferences 的 key
        public static final String SHARED_PREFERENCES_ENVIRONMENT_KEY = "test_key";
        
        public TestSP(Context context) {
            super(context);
        }
    
        @Override
        public String sharedPreferencesFileName() {
            return SHARED_PREFERENCES_FILE_NAME;
        }
    }

```
