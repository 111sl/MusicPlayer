## day06

### 控件

1.TextView<br/>

​	1.1 TextView中支持使用html标签。带代码设置如下：<br/>		String html="< font color ='red'>Hello</ font>";
​		CharSequence charSequence=Html.fromHtml(html);
 		textView.setText(charSequence);<br/>	1.2 TextView支持显示图片、文字阴影和自定义字体，自定义字		体需要有字体对应的TTF文件。显示图片的主要应用在于在显		示的文字旁边加上icon之类的功能，不必再加一个控件专门显		示那个小icon。

2.AutoCompleteTextView<br/>	自动提示输入补全的功能。在做搜索功能时会用到，在下拉框中提示用户可能要用到的搜索信息。

3.EditText<br/>	编辑框，继承自TextView。

4.RadioButton<br/>	单选按钮，重点掌握check属性即可。使用时外层有一个radioGroup包着多个radioButton，一次只能选中group中的其中一个。

5.CheckBox<br/>	复选框，重点掌握check属性即可。可应用于调查问卷等。

6.ToggleButton<br/>	开关按钮，设置中常见，比如开关WiFi按钮之类的。

7.ProgressBar<br/>	进度条。可在style属性中设置不同的进度条样式，例如圆圈的，水平的长条。还可以在属性中设置是一层进度条还是两层进度条，属性中加secondaryProgress属性即可，这一属性应用场景较少。

8.SeekBar<br/>	拖动进度条，和进度条基本类似，可以通过设置属性android:thumb来改变拖动进度条上那个图案。其余的属性和进度条差不多了。

9.RatingBar<br/>	星级评分。和拖动进度条差不多，就是用星级表示进度条。

10.ImageView<br/>	可以通过设置scaleType属性来设置所显示的图片如何缩放或移动以适应ImageView的大小。应用在图片缩放旋转等动画效果。

11.ListView<br/>	需要构建一个适配器（可以自己继承编写），将上下文，单项布局和数据源传入适配器，再将适配器绑定给listView进行使用。