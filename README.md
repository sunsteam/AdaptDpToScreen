# AdaptDpToScreen
android 多屏幕适配方案

<img src="http://ofaeieqjq.bkt.clouddn.com/AdaptDpToScreen/Screenshot_emulator_Nexus_5X.png" width = "360" height = "640" alt="1080*1920" /><img src="http://ofaeieqjq.bkt.clouddn.com/AdaptDpToScreen/Screenshot_emulator_Nexus_6P.png" width = "360" height = "640" alt="1440*2560" /><img src="http://ofaeieqjq.bkt.clouddn.com/AdaptDpToScreen/Screenshot_emulator_Nexus_4.png" width = "360" height = "640" alt="768*1280" />

## 工具下载

autoGenDp.bat、autoGenDp.jar

https://github.com/sunsteam/AdaptDpToScreen/tree/master/app/src/main

dimens.xml

https://github.com/sunsteam/AdaptDpToScreen/blob/master/app/src/main/res/values/dimens.xml

## 使用方法：

1. 默认的 values 文件夹中需要一份特定的 dimens.xml 文件。项目里直接拷贝 values 文件夹下的 dimens.xml，有缺的尺寸就在使用中慢慢补吧。

2. 把 screenMatchDP.bat 和 screenMatchDP.jar 两个文件拷贝到你项目的 main 目录下，和 res 文件夹平级。

3. 修改 screenMatchDP.bat 文件中你需要适配的屏幕 dp 参数，第一个为设计的基准 widthDpi，后面为需要适配的 widthDpi。

4. 如果有带参数的文件夹需要适配，屏幕 dp 参数后面在加上 ` -a ` 后面是需要适配的参数，举个栗子：

```
java -jar %~dp0\autoGenDp.jar 360 320 360 384 392 400 411 533 590 -a -v19
pause
```

5. 进到 screenMatchDP.bat 文件所在的硬盘位置，双击执行。执行是不要在 AS 里面双击，AS 不可执行 bat 文件。

#### 如果 bat 文件中没有跟参数或者参数少于 3 个，会使用默认值生成，默认值是下面这样的

```java
if (args == null || args.length < 3)
        widthDps = new String[]{"360", "384", "400", "411", "533", "640", "720", "768", "820"};
```

---

## 说明

jar包的源码来自于 [PhoneScreenMatch](https://github.com/mengzhinan/PhoneScreenMatch) ，先感谢一下作者。原作者的文章在这里 [Android 屏幕适配 dp、px 两套解决办法](http://blog.csdn.net/fesdgasdgasdg/article/details/52325590)

我改了一下源码，主要有 3 点：

1. 去掉 px 适配的部分，这个限制较多，不好用

2. dp 适配方案从 `w<N>dp` 改为 `sw<N>dp`，取舍原因请看我的博客 [android 适配相关知识(二) -- 自动生成swNdp适配方案 ](http://blog.csdn.net/sunsteam/article/details/73122999)。

3. 增加了对 `-sdk` 参数的支持。 比如尺寸参数后面跟上 ` -a -v19 -v21` 这样的话会为 -v19 和 -v21 在分别生成一遍屏幕适配的文件夹，其他种类的参数也可以，但在适配上没啥用。

生成工具已提取成 java 库，可以自行修改满足需求，Terminal 中运行 `gradlew buildLib` 即可在目录下生成新的 jar 包，拷贝到 demo 中测试。
