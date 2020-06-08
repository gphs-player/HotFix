### MultiDex



#### 1.问题

将60kmethod.jar包拷贝到工程，编译安装,会有如下错误

```
Caused by: com.android.tools.r8.utils.AbortException: Error: null, Cannot fit requested classes in a single dex file (# methods: 75819 > 65536)
```

#### 2.解决方案-multidex

添加依赖

```
implementation 'androidx.multidex:multidex:2.0.1'
```

```groo
defaultConfig {
    multiDexEnabled true
}
```

然后安装MultiDex，方式有两种

```java
public class App extends MultiDexApplication 
```

或者手动调用

```java
MultiDex.install(this);
```

关键代码

```
makeDexElements.invoke(dexPathList, filesToBeInstalled, context.getExternalCacheDir(), exceptions, loader)
```

