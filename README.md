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

>>> `例：` <br> 

>>> `<location>D:/Test.properties</location>`
>>> ### 3.1.2 step标签

>>> step标签是用来存储每一步所配置的数据。name为每一步的名字。<br>

>>> `例：` <br> 
>>> `<step name="数据库配置">`
>>> ### 3.1.3 textField标签

>>> textField标签是用来创建普通的文本输入框，id为配置文件中的key，value为默认值。<br>
>>> `例：` <br>
>>> `<textField id="name" value="root">用户名</textField>`
>>> ### 3.1.4 textFields标签

>>> textFields标签是用来创建需要拼接后存储到配置文件的数据，id为拼接后的key，rule为拼接规则，head为出事头部。
>>>> #### 3.1.4.1 Each标签
>>>> Each标签是textFields标签中拼接的子标签，value为默认值。<br> 
>>>> `例：` <br> 
>>>> `<textFields id="url" rule=":" head="jdbc:oracle:thin:@">`<br>
>>>> `<Each value="1.1.1.1">IP</Each>`<br>
>>>> `<Each value="3306">端口号</Each>`<br>
>>>> `<Each value="orcle">实例名</Each>`<br>
>>>> `</textFields>`<br>
>>> ### 3.1.5 radio标签

>>> radio标签是单选按钮标签，id为配置文件中的key，text为页面显示文本。
>>>> #### 3.1.5.1 eachradio标签

>>>> eachradio标签是radio标签中的每一个选项，value为值。<br> 
>>>> `例：` <br>
>>>> `<radio id="on-tcp-receive" text="是否开启TCP监听">`<br>
>>>> `<eachradio value="0">否</eachradio>`<br>
>>>> `<eachradio value="1">是</eachradio>`<br>

>>>> `</radio>`<br>
>>> ### 3.1.6 Affect标签

>>>> Affect标签是编辑此标签后会影响其他配置信息的标签，id为key，text为页面显示文本，value为默认值。

>>>> #### 3.1.6.1 Affected标签

>>>> Affected标签为被Affect标签的数据。id为key，value为默认值。<br> 
>>>> `例：` <br>

>>>> `<Affect id="center-data-dir" text="订单备份路径" value="D:\\filedir">`<br>
>>>> `<Affected id="cacheFilePath" value="D:\\filedir\\cacheFile"></Affected>`<br>
>>>> `<Affected id="historyFile" value="D:\\filedir\\historycacheFile"></Affected>`<br>
>>>> `</Affect>`<br>



> # 4 实例化NavigationConfigServer对象

> # 5 在网页中更改数据

> # 6 配置完成
> # [点击此处查看演示](http://120.27.19.38:7893/welcome.html)

