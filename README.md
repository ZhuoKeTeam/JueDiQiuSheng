# JueDiQiuSheng
 绝地求生 app
 
 ## 更新记录
 1. 2017年10月28日 更新网络请求的简单操作
 

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

## ZKTeam 的网络请求简易操作

1. 获取通用的 ZKApi:

     ``ZKConnectionManager.getInstance().getZKApi()``
     
2. 创建对应的当前接口数据实体 eg: CategoryBean,  BaseBean (无需创建) 类是默认的顶层返回数据接口。
3. 在接口类 ZKApi 中设置相关的请求接口 eg:
    
    ``Call<BaseBean<CategoryBean>> categoryData()``
    
4. 使用获取的通用接口 ZKApi 对象直接获取 上面的具体请求接口
5. 使用 execute 为同步请求， enqueue 加入异步请求队列，，可以直接使用。
6. 默认使用 ZKCallback，返回的数据为需要的 list 数据。

完整实例如：
    
    ```
    ZKConnectionManager.getInstance().getZKApi().categoryData()
                    .enqueue(new ZKCallback<CategoryBean>() {
                        @Override
                        public void onResponse(List<CategoryBean> resultList) {
                            Log.d(TAG, "onResponse() called with: resultList = [" + resultList + "]");
                        }
    
                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                        }
                    });

    ```