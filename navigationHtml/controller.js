function init() {
    /*
`               ``````````````````````````````````````````````````````````````````````
      变量区
`               ``````````````````````````````````````````````````````````````````````
*/

    // 全局变量
    class Number {
        constructor(nowPageNumber) {
            this.nowPageNumber = nowPageNumber;
        }
    }

    let Num = new Number(1);  //指定当前页码
    const allPageNumber = document.getElementsByTagName("section").length;

    // 全局 Array
    let infos = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input'); // now Page Info
    let allInfos = document.querySelectorAll('.main input'); // all Page Info
    let radioInfos = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input');  // now Page Info
    let allRadioInfos = document.querySelectorAll('.radioMain input'); // all Page Info

    // 按钮
    let reset = document.getElementById('reset');
    let last = document.getElementById("last");
    let next = document.getElementById('next');
    let final = document.getElementById('final');

    // 信息
    let param = '';

    /*
`               ``````````````````````````````````````````````````````````````````````
    功能区Func
`               ``````````````````````````````````````````````````````````````````````
 */

    //基础信息获取函数
    function getParam() {
        param = '';
        console.log(Num.nowPageNumber)
        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {
            param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id + '=&'
        }
        //处理radio
        let temporary = '';
        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {
            if (temporary != document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name) {
                temporary = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name;
                param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + '=&'
            }
        }
        param = param.slice(0, param.length - 1)

        // console.log(param,document.querySelectorAll('#modify'+Num.nowPageNumber+' .main input'));
        return param;
    }

    // 更换按钮
    function updateButton(type) {
        if (type === 0) {
            //第一页
            final.style.display = 'none';
            last.style.display = 'none';
            next.style.display = 'inline-block';
        } else if (type === 1) {
            //中间页
            final.style.display = 'none';
            last.style.display = 'inline-block';
            next.style.display = 'inline-block';
        } else if (type === 2) {
            //尾页
            final.style.display = 'inline-block';
            last.style.display = 'inline-block';
            next.style.display = 'none';
        }
    }

    /*
`               ``````````````````````````````````````````````````````````````````````
    初始化定义区
`               ``````````````````````````````````````````````````````````````````````
 */

    //初始值的获取
    function getInfo(param) {
        console.log(param);
        let xmlhttp;
        if (window.XMLHttpRequest) {
            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp = new XMLHttpRequest();
        } else {
            // IE6, IE5 浏览器执行代码
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                let data = xmlhttp.responseText;
                data = JSON.parse(data);
                console.log(data);
                for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {
                    document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].value = data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id];
                }

                //radio配置
                let t = '';
                for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {
                    if (document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name != t) {
                        if (data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name] == null || data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name] == '') {
                            document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].checked = true;
                        } else {
                            document.getElementById(document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + data[document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name]).checked = true;
                        }
                        t = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name;
                    }
                }
                //
            }
        }
        xmlhttp.open("POST", "http://localhost:7893/getdata", true);
        xmlhttp.send(param);

    }

    /*
        `               ``````````````````````````````````````````````````````````````````````
         初始化执行区
        `               ``````````````````````````````````````````````````````````````````````
     */
    getParam();
    getInfo(param);
    updateButton(1);
    console.log('当前页码为:', Num.nowPageNumber);
    console.log('当前Page的所有表单信息为：', infos, radioInfos);
    console.log('所有Page的表单信息为：', allInfos, allRadioInfos);
    console.log(param);
    document.getElementById("modify" + Num.nowPageNumber).style.display = "block";
    /*
`               ``````````````````````````````````````````````````````````````````````
    Impl区
`               ``````````````````````````````````````````````````````````````````````
 */

    //上一步
    function lastPage(param) {
        if (Num.nowPageNumber != 2) updateButton(1);
        else updateButton(0);
        document.getElementById("modify" + Num.nowPageNumber).style.display = "none";
        Num.nowPageNumber--;
        document.getElementById("modify" + Num.nowPageNumber).style.display = "block";
    }

    //下一步
    function nextPage(param) {
        let xmlhttp;
        if (window.XMLHttpRequest) {
            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp = new XMLHttpRequest();
        } else {
            // IE6, IE5 浏览器执行代码
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                let data = xmlhttp.responseText;
                console.log('点击下一步时候接收到的data：', JSON.parse(data));
                data = JSON.parse(data);
                if (data.type == true) {
                    if (Num.nowPageNumber < allPageNumber - 1) {
                        updateButton(1);
                    } else {
                        updateButton(2);
                    }
                    document.getElementById("modify" + Num.nowPageNumber).style.display = "none";
                    Num.nowPageNumber++;
                    param = getParam();
                    getInfo(param);
                    document.getElementById("modify" + Num.nowPageNumber).style.display = "block";
                } else if (data.type == false) {
                    alert(data.message);
                } else {
                    alert("服务器无响应!");
                }
            }
        }
        xmlhttp.open("POST", "http://localhost:7893/total", true);
        xmlhttp.send(param);
    }

    //完成
    function finalPage(param) {
        let xmlhttp;
        if (window.XMLHttpRequest) {
            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp = new XMLHttpRequest();
        } else {
            // IE6, IE5 浏览器执行代码
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                let data = xmlhttp.responseText;
                    console.log(JSON.parse(data));
                    data = JSON.parse(data);
                    if (data.type == "1") {
                        alert("确认保存!");
                    } else if (data.type == "0"){
                        alert(data.message);
                    }else {
                        alert("保存失败!")
                }
            }
        }
        xmlhttp.open("POST", "http://localhost:7893/final", true);
        xmlhttp.send(param);
    }

    /*
`               ``````````````````````````````````````````````````````````````````````
    实现区
`               ``````````````````````````````````````````````````````````````````````
*/
    //重置
    reset.onclick = function () {
        let param = '';
        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {
            param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id + '=&'
        }
        //处理radio
        let temporary = '';
        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {
            if (temporary != document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name) {
                temporary = document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name;
                param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + '=&'
            }
        }
        param = param.slice(0, param.length - 1);
        getInfo(param);
    };

    //上一步
    last.onclick = function () {
        lastPage(param);
        console.log(param);
    };
    //下一步
    next.onclick = function () {
        let param = '';
        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input').length; i++) {
            param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].id + '=' + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .main input')[i].value + '&'
        }
        //处理radio
        for (let i = 0; i < document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input').length; i++) {
            if (document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].checked == true) {
                param = param + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].name + '=' + document.querySelectorAll('#modify' + Num.nowPageNumber + ' .radioMain input')[i].value + '&'
            }

        }
        param = param.slice(0, param.length - 1);
        console.log(param);
        nextPage(param);
        console.log(param);
    };

    //完成
    final.onclick = () => {
        let param = '';
        for (let i = 0; i < document.querySelectorAll('.main input').length; i++) {
            param = param + document.querySelectorAll('.main input')[i].id + '=' + document.querySelectorAll('.main input')[i].value + '&'
        }
        console.log(document.querySelectorAll('.radioMain input'))
        //处理radio
        for (let i = 0; i < document.querySelectorAll('.radioMain input').length; i++) {
            if (document.querySelectorAll('.radioMain input')[i].checked == true) {
                param = param + document.querySelectorAll('.radioMain input')[i].name + '=' + document.querySelectorAll('.radioMain input')[i].value + '&'
            }

        }
        param = param.slice(0, param.length - 1);
        console.log(param);
        finalPage(param);
        console.log(param);
    };
}

window.addEventListener("load", init, false);