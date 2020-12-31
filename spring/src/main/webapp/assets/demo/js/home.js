var app = {
    method: {
        init: function () {
            app.$('title').innerText = 'This is home page.';
            app.method.way1('/demo/data/list');
            app.method.way3('/demo/data/list');
            app.method.way4('/demo/data/list');
        },
        way1: function(url) {
            fetch(url)
                .then(function (response) {
                    return response.json();
                })
                .then(function (data) {
                    console.log(data);
                }).catch(function (e) {
                    console.log(e);
                });
        },
        way2: function() {
        },
        way3: function(url) {
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'text',
                success: function (data) {
                    console.log(data);
                },
                error: function (e) {
                    console.log(e);
                }
            });
        },
        way4: function(url) {
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', url, true);    //准备向服务器的ajaxtext1.ashx发送get请求
            //监听onreadystatechange事件
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4) {
                    if (xmlhttp.status === 200) {
                        console.log(xmlhttp.responseText);  //responseText属性为服务器返回的文本
                    } else {
                        console.log('AJAX服务器返回错误！');
                    }
                }
            }
            xmlhttp.send();     //开始发送请求，之前的代码都是做准备
        }
    },
    $: function (id) {
        return document.getElementById(id);
    }
};