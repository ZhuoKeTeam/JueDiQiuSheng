# 科普 LifeCycleComponents 文档说明

详细内容查阅 https://mp.weixin.qq.com/s/D_K-ILxBgMukrzg_tDDVug

# 优势
    - 保持 UI controllers （activities and fragments）越简洁越好，不要臃肿 。UI controllers不要去获取数据，而是用ViewModel来执行，并监听lifeData来更新视图。
    - 尝试编写用数据驱动的UI，UI controllers的职责就是数据改变是更新视图，并且把用户的操作通知给ViewModel。
    - 将业务逻辑放入在VIewModel中，VIewModel应该用作UI控制器和其他应用程序之间的连接器。请注意，ViewModel的责任不是获取数据（例如从网络获取数据），ViewModel应该调用相应的组件来获取数据，然后把结果提供给UI controller。
    - 使用数据绑定来维护视图view和UI控制器接口的清洁。使view视图更具有生命性，减少在fragment或者activity编写更新数据的代码。如果使用Java语言来开发，请使用像Butter Knife这样的库来避免使用样板代
    - 如果UI非常复杂，可以考虑创建一个Persenter类来处理UI的修改。也许使用Persenter有点大材小用，但是可以使ui变得更容易测试
    - 不要在ViewModel中引用View或者Activity中的context，如果ViewModel活的比Activity更长，activity可能会引发内存泄露，而不是正常的垃圾回收。

# Demo 
代码位于：cc.zkteam.juediqiusheng.lifecycle.components.demo

## 创建 LiveData 对象
1. 创建 ZKLiveData ，继承 LiveData，泛型传入对应的数据类型，例如 String，Bean
2. 覆写 onActive 和 onInactive， onActive 表示已经进入 onResume 状态，可以直接与用户交互， onInactive 表示进入后台，用户切换了其他的页面
3. 在该类中 可以通过 postValue() 和 setValue() 设置数据。其中 postValue() 是在子线程里面调用， setValue() 在主线程调用

## 创建 ViewModel 对象
1. 创建 ZKViewModule， 继承 ViewModel
2. 添加方法 getText(), 对 zkLiveData 获取 对象
3. 添加测试方法 loadNewNumber(), 内部启动一个线程进行循环更新 count， 如果小于5，就给 zkLiveData  里面设置值，并自加1， 休眠1s, 等大于5 以后，就将 zkLiveData 内容设置为 『测试完成』

## 在 MainActivity 里面测试
1. 获取 ZKViewModule， ViewModelProviders.of(this).get(ZKViewModule.class);
2. 执行 getText(), 在 内部的 onChanged() 里面更新 变化后的数据
3. 只要 ZKLiveData 里面的数据变化了，那么 界面上的文本也跟着变化


至此，相关的操作已经完成。 下面来点更高级的

## 在组件里面获取 生命周期的状态
1. 创建 ZKText， 并实现接口 LifecycleObserver
2. 添加变量 Lifecycle， 并在构造函数中传入 Lifecycle, 并 lifecycle.addObserver(this);
3. 添加三个函数，对应使用注解写上相关获取的周期状态：resume， onStop，onDestroy
4. 添加 普通方法 updateText()

在 MainActivity 里面进行测试，直接获取：new WQText(this.getLifecycle())， 然后使用手机锁屏，进后台，等操作，查看相关 log， 结果证明都正确。

## 小结
AppCompatActivity 中默认已经集成了LifecycleOwner哦， 所以可以直接使用并获取：getLifecycle()。 其他的，请大家在项目中合理使用吧！
