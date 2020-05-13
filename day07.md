## day07

### 1.Fragment

​	可以将fragment看做一个控件，在其中可以放其他控件组合。不可单独存在，依附于activity。一个activity可添加多个fragment，一个fragment也可以被多个activity复用。无须在manifest中注册。

<br/>	1.1 创建<br/>		继承fragment并重写onCreatView方法，该方法指定了显示的view。<br/>	1.2 生命周期<br/>		和activity基本类似![image-20200513191343068](C:\Users\uncle drew\AppData\Roaming\Typora\typora-user-images\image-20200513191343068.png)

在测试中可以看出，activity执行到onResume时，fragment才开始执行onAttach，并且fragment的onPauser方法也早于activity的onPasuer执行。<br/>	1.3 添加到activity<br/>		可通过xml直接添加也可以通过代码动态添加。FragmentManager fragmentManager=getFragmentManager();
FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();<br/>	注意，如果是support.v4应该是getSupportManager()!!!!!通过获取管理再获取到事务，通过事务对fragment进行操作即可。fragmentTransaction.add(R.id.activity,fragment);
fragmentTransaction.commit();

### 2.ViewPager

​	用于多页面切换的控件，如软件引导页面，微信页面切换等方面。使用时与listview一样也需要new一个适配器，然后将需要显示的页面list传入adpter，再setAdpter即可。重写适配器和listview类似，不多说。

### 3.ActionBar

​	导航栏。响应菜单，没啥好说的。为了统一页面风格等用处。

### 4.其他常用控件

​	4.1GridView

​	GridView 跟ListView 很类似，Listview 主要以列表形式显示数据，GridView 则是以网格形式显示数据。也需要写一个适配器。

​	4.2 ScrollView

​	滚动条，没啥说的。

​	4.3 ImageSwitcher

 	图片切换器，可以有淡进淡出的动画效果，感觉可以用来替代viewpager的那个引导界面功能，简单一点。 

​	4.4 Gallery

​	滚动图片效果，和上面那个适用的场景差不多，具体等试验后补充。https://blog.csdn.net/WeLoveSunFlower/article/details/8095525?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-24.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-24.nonecase

​	4.5 Spinner

​	下拉框。

​	4.6 DatePicker

 	日期选择器 。

​	4.7 TimePicker

​	 时间选择器。

​	4.8 SurfaceView

 	参考：https://blog.csdn.net/android_cmos/article/details/68955134?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase

​	4.9 TextureView

 	参考：https://blog.csdn.net/Andreaw/article/details/88761062

 