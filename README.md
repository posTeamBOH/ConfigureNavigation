# ConfigureNavigation
第一版本（没有校验）
配置导航服务
=======

通过此文件，您能够更容易地使用此服务。 
--------

> #  1 引入此jar包到项目中
> # 2 在pom中添加依赖

![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/1.png)
> # 3 在resource根目录下创建ChoiceNavigation.xml文件并进行配置
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/2.png)

> # 此文件中需要配置许多标签，以下介绍各种标签的用法。
>> ##  3.1 steps标签

>> 所有配置信息都需要在steps标签中配置。

![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/15.png)

>>> ###   3.1.1 location标签

>>> location标签中填写所需配置文件的地址。`注：此地址需填写绝对路径，路径的间隔符需用“/”`<br> 
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/3.png)
>>> ### 3.1.2 step标签

>>> step标签是用来存储每一步所配置的数据。name为每一步的名字。<br>
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/6.png)
>>> ### 3.1.3 textField标签

>>> textField标签是用来创建普通的文本输入框，id为配置文件中的key，value为默认值。<br>
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/9.png)
>>> ### 3.1.4 textFields标签

>>> textFields标签是用来创建需要拼接后存储到配置文件的数据，id为拼接后的key，rule为拼接规则，head为出事头部。
>>>> #### 3.1.4.1 Each标签
>>>> Each标签是textFields标签中拼接的子标签，value为默认值。<br> 
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/8.png)
>>> ### 3.1.5 radio标签

>>> radio标签是单选按钮标签，id为配置文件中的key，text为页面显示文本。
>>>> #### 3.1.5.1 eachradio标签

>>>> eachradio标签是radio标签中的每一个选项，value为值。<br> 
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/7.png)
>>> ### 3.1.6 Affect标签

>>>> Affect标签是编辑此标签后会影响其他配置信息的标签，id为key，text为页面显示文本，value为默认值。

>>>> #### 3.1.6.1 Affected标签

>>>> Affected标签为被Affect标签的数据。id为key，value为默认值。<br> 
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/16.png)



> # 4 实例化NavigationConfigServer对象
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/10.png)


> # 5 在网页中更改数据
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/11.png)
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/12.png)

![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/13.png)



> # 6 配置完成
![image](https://github.com/posTeamBOH/ConfigureNavigation/blob/master/img/14.png)

> # [点击此处查看演示](http://120.27.19.38:7893/welcome.html)

