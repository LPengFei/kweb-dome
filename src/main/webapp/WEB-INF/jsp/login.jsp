<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><klookup:kv key="title" type="system_settings"/></title>
    <link rel="stylesheet" href="${ctx }/BJUI/themes/css/login1_style.css"/>
    <link rel="stylesheet" href="${ctx }/BJUI/plugins/revealjs/css/reveal.css"/>
    <link rel="stylesheet" href="${ctx }/BJUI/plugins/revealjs/css/theme/black.css"/>
    <script src="${ctx }/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx }/BJUI/js/jquery.cookie.js"></script>
    <%--<script src="${ctx }/BJUI/js/sha256.js"></script>--%>
    <%--<script src="${ctx }/BJUI/plugins/revealjs/js/reveal.js"></script>--%>
    <script src="${ctx }/js/md5.js"></script>
    <script src="${ctx }/js/RSA.js"></script>
    <style type="text/css">
        .msg-wrap {
            min-height: 10px;
        }

        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
        }

        .close-button {
            position: fixed;
            right: 10px;
            top: 10px;
            width: 40px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            border-radius: 100%;
            background: rgba(0, 0, 0, 0.2);
            color: white;
            font-size: 14px;
            cursor: pointer;
            z-index: 33;
        }

        .login-bg {
            position: absolute;
            right:0;
            top: 0;
            z-index: 32;
        }

        .reveal .slides section {
            color: white;
        }

        .login-echart-wrap {
            position: relative;
            width: 1000px;
            height: 500px;
            margin: 0 auto;

        }

        .login-echart {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0px;
            left: 0px;
        }
        .loadding {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0px;
            left: 0px;
            background: url("./images/big_load.gif") no-repeat center #fff;
            z-index: 100;
        }
        .reveal .slides > section{
            padding: 0px;
        }
    </style>
</head>
<body style="background: none">
<div class="close-button" style="display: none;">打开</div>
<div class="login-bg">
    <img src="${ctx }/images/login/bg_1_.png" id="bg_img">
    <form action="${ctx}/login" method="post" id="loginForm">
        ${token}
        <input type="hidden" id="publicKeyExponent" value="${publicKeyExponent}">
        <input type="hidden" id="publicKeyModulus" value="${publicKeyModulus}">
        <input type="hidden" name="from" value="${from}">

        <div class="login-wrap">
            <div class="login-title">用户登录<span>×</span></div>
            <div class="login-line lineTop divUsername">
                <div class="userImg"><img src="${ctx }/images/login/user1.png"></div>
                <input type="text" id="username" name="username" value="" placeholder="请输入用户名"/>
            </div>
            <div class="login-line divPassword">
                <div class="userImg"><img src="${ctx }/images/login/pwd1.png"></div>
                <input type="text" onfocus="this.type='password'" name="password" id="password" value=""
                        placeholder="请输入密码"/>
                <%--<input type="hidden" name="password" id="password">--%>
            </div>

            <%--验证码--%>
            <c:if test="${captcha}">
                <div id="captchaDiv">
                    <div class="login-yz ">
                        <div class="userImg"><img src="${ctx }/images/login/yz1.png"></div>
                        <input id="j_captcha" type="text" name="captcha" placeholder="验证码" maxlength="4"/>
                    </div>
                    <span class="login-yzm" style="width:97px; margin-left: 15px;">
                        <img id="captcha_img" alt="验证码" src="${ctx}/captcha"/>
                    </span>
                </div>
            </c:if>

            <c:if test="${rememberme}">
            <div class="login-check"><input type="checkbox" name="remember" id="remember"><label>记住用户名</label>
            </div>
            </c:if>

            <%--<c:if test="${!empty(error) }">--%>
                <div id="errorMsg" style="color:red;margin:60px 20px 5px; text-align: left;font-size:12px;">${error }</div>
            <%--</c:if>--%>
            <div class="login-btn" onclick="loginsubmit();">登录</div>
        </div>
    </form>
</div>
<!-- 展示的容器 -->
<%--
<div class="reveal" style="width: 100%; padding: 0px; display: none">
    <div class="slides" style="width: 100%; height: 100%; padding: 0px; overflow:hidden;">
        <section style="background: #090B1A;">
            <iframe src="${ctx}/BJUI/plugins/echartmap/riskmap.html" style="width: 100%; height: 100%; max-height: 100%; max-width: 100%; overflow: hidden;"></iframe>
        </section>
        <section>
            <h4>业界已经有多如牛毛的图表库了！</h4>
            <div class="fragment" style="text-align:right;">
                <hr/>
                Why <strong style="color:#9acd32">ECharts</strong> ?
            </div>
            <div class="login-echart-wrap">
                <div class="login-echart"></div>
                <div class="loadding"></div>
            </div>

        </section>
        <section>
            <h4 style="color:#9acd32">深度数据互动可视化</h4>
            <p>
                <small>
                    <br/>打破单纯的视觉呈现，拥有<strong>互动图形用户界面（GUI）</strong>的数据可视化。数据呈现<strong>不仅是诉说</strong>，而是允许用户对所呈现数据进行<strong>挖掘、整合</strong>，让可视化成为<strong>辅助人们进行视觉化思考</strong>的方式。
                </small>
            </p>
            <br/>
            <div class="fragment" style="text-align:right;">
                <hr>
                <small>让我们看看<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>为此目标做了什么？<br/>
                    <small>* 下面的内容建议全屏浏览（F11切换）</small>
                </small>
            </div>
            <div class="login-echart-wrap">
                <div class="login-echart"></div>
                <div class="loadding"></div>
            </div>
        </section>
        <section>
            <h4>[ 拖拽重计算 ] 整合你所关心的数据</h4>
            <p>
                <small>图表数据的默认分类不总是满足每一个人的需求。
                    <br/>就像如下的浏览器占比，我想知道IE所占的总比例，是默默的心算还是拿根笔出来？
                </small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/"
                        target="_blank">ECharts</a>，试试把你关心的数据图形<strong>拖拽到一起</strong>！
                </small>
            </p>
            <div class="main" optionKey="calculable1">
                <img src="${ctx}/BJUI/plugins/revealjs/test2.png"
                        style="margin:0;background:rgba(0,0,0,0);border-width: 0;box-shadow: 0 0 0px rgba(0, 0, 0, 0);"/>
            </div>

        </section>
        <section>
            <h4>[ 拖拽重计算 ] 剔除畸形数据</h4>
            <p>
                <small>不可避免的有些时候会有些畸形数据存在，就像如下的销售数据：
                    <br/>双11的辉煌后你看到了这样统计数据，你得忍受这无多大指导意义统计图表一个月甚至更长时间？
                </small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/"
                        target="_blank">ECharts</a>，试试把畸形数据<strong>拖拽出来</strong>！
                    <br/>恩，剔除畸形数据后你好像还惊讶的发现了一个不太乐观的趋势？
                </small>
            </p>
            <div class="main" optionKey="calculable2">
                <img src="${ctx}/BJUI/plugins/revealjs/test3.png"
                        style="margin:0;background:rgba(0,0,0,0);border-width: 0;box-shadow: 0 0 0px rgba(0, 0, 0, 0);"/>
            </div>
        </section>
        <section>
            <h4>[ 数据视图 ] 满足用户对数据的需求</h4>
            <p>
                <small>如果你所呈现的数据足够让用户所关心，那么他们将不满足于查看可视化的图表：
                    <br/>浪费你服务器上宝贵的磁盘空间去异步生成数据文件？再大动干戈的开发文件下载的轮询请求？
                </small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>，或许你只需给予一个“,”分隔的数据文本他们就懂了，试试<strong>点击这个图标</strong>
                    <br/>你甚至可以打开数据视图的<strong>编辑功能</strong>，跟拖拽重计算相比，这可是批量的数据修改！
                </small>
            </p>
            <div class="main" optionKey="dataView"></div>
        </section>
        <section>
            <section>
                <h4>[ 动态类型切换 ] 尝试不同类型的图表展现</h4>
                <p>
                    <small>很多图表类型本身所表现的能力是相似的，但由于数据差异、表现需求和个人喜好的不同导致最终图表所呈现的张力又大不一样，比如折线图和柱状图的选择总是让人头疼？是否使用堆积也是一个艰难的决定？是放弃这个尝试还是重复的写上大段代码？
                    </small>
                </p>
                <p class="fragment">
                    <small>这是<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>，我们提供了动态类型切换功能，让用户随心所欲的切换图表类型和堆积平铺状态。
                        <br/>试试把柱状图<strong>切换</strong>成折线图，或者切换堆积平铺状态，你会对这份数据有更多的解读。
                    </small>
                </p>
                <div class="main" optionKey="magicType"></div>
            </section>
            <section>
                <h4>[ 动态类型切换 ] 尝试不同类型的图表展现</h4>
                <p>
                    <small>很多图表类型本身所表现的能力是相似的，但由于数据差异、表现需求和个人喜好的不同导致最终图表所呈现的张力又大不一样，是放弃这个尝试还是重复的写上大段代码？
                    </small>
                </p>
                <p>
                    <small>这是<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>，我们提供了动态类型切换功能。不同的图形表达去展现同样的数据，或许你会对这份数据有更多的解读。
                    </small>
                </p>
                <div class="main" optionKey="magicType2"></div>
            </section>
            <section>
                <h4>[ 动态类型切换 ] 尝试不同类型的图表展现</h4>
                <p>
                    <small>很多图表类型本身所表现的能力是相似的，但由于数据差异、表现需求和个人喜好的不同导致最终图表所呈现的张力又大不一样，是放弃这个尝试还是重复的写上大段代码？
                    </small>
                </p>
                <p>
                    <small>这是<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>，我们提供了动态类型切换功能。不同的图形表达去展现同样的数据，或许你会对这份数据有更多的解读。
                    </small>
                </p>
                <div class="main" optionKey="legendSelected"></div>
            </section>
        </section>
        <section>
            <h4>[ 值域漫游 ] 聚焦到你所关心的数值上</h4>
            <p>
                <small>基于坐标的图表（如地图、散点图）通过色彩变化表现数值的大小能直观形象的展示数据分布：
                    <br/>但如何聚焦到我所关心的数值上？比如我只想查看top 10%的地域有哪些？又找笔了？
                </small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/"
                        target="_blank">ECharts</a>，我们创造了称为值域漫游的功能，尝试<strong>上下拖拽</strong>左下角的那个控件，甚至单纯的鼠标移动时你就发现了神奇的事情！他表达的意义并不需要过多的解析，看起来就像是理所当然的！<br/>
                </small>
            </p>
            <div class="main" optionKey="dataRange1"></div>
            <h4>[ 值域漫游 ] 聚焦到你所关心的数值上</h4>
            <p>
                <small>基于坐标的图表（如地图、散点图）通过色彩变化表现数值的大小能直观形象的展示数据分布：
                    <br/>但如何聚焦到我所关心的数值上？比如我只想查看top 10%的地域有哪些？又找笔了？
                </small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/"
                        target="_blank">ECharts</a>，我们创造了称为值域漫游的功能，尝试<strong>上下拖拽</strong>左下角的那个控件，甚至单纯的鼠标移动时你就发现了神奇的事情！他表达的意义并不需要过多的解析，看起来就像是理所当然的！<br/>
                </small>
            </p>
            <div class="main" optionKey="dataRange1"></div>

        </section>
        <section>
            <h4>[ 数据区域缩放 ] 聚焦到你所关心的数据上</h4>
            <p>
                <small>显示空间总是有限的，显示一大段时间跨度的数据是常见的需求：
                    <br/>密密麻麻的全放出来？提供一个日历选择器让用户频繁的选择切换？
                </small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>，我们提供了数据区域缩放功能，带全局数值影子的刻度条加上三个可<strong>拖拽</strong>的手柄让你轻松完成数据区域浏览，你甚至可以启用更加直观的<strong>框选</strong>放大和<strong>后退</strong>
                    <br/><strong>拖拽</strong>和<strong>框选</strong>这两种交互会自动同步的！你或许已经发现了，随动的还有<strong>极值点</strong>和<strong>平均线</strong>的自动标注。<br/>
                </small>
            </p>
            <div class="main" optionKey="dataZoom1"></div>
        </section>

        <section>
            <h4>[ 多图联动 ] 更友好的关联数据分析</h4>
            <p>
                <small>多系列数据在同一个直角系内同时展现有时候会产生混乱，但他们又存在极强的关联意义不可分离？</small>
            </p>
            <p class="fragment">
                <small>这是<a href="http://ecomfe.github.io/echarts/" target="_blank">ECharts</a>，我们提供了多图联动的能力（connect），能做的可不仅仅是鼠标划过的详情显示。
                </small>
            </p>
            <div class="main" optionKey="multiCharts" style='height:210px;padding-bottom:0;border-bottom-width:0'></div>
            <div id="mcMain2" class="main2" style='height:140px;padding:1px 10px;border-width:0 1px;'></div>
            <div id="mcMain3" class="main2" style='height:100px;padding-top:1px;border-top-width:0'></div>
        </section>
    </div>
</div>
--%>

</body>
<script src="${ctx }/BJUI/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/js/login-echart.js"></script>
<script type="text/javascript">
    /*Reveal.initialize({
        // 是否在右下角展示控制条
        controls: true,
        // 是否显示演示的进度条
        progress: true,

        // 是否显示当前幻灯片的页数
        slideNumber: 'c/t',

        // 是否将每个幻灯片改变加入到浏览器的历史记录中去
        history: false,

        // 是否启用键盘快捷键来导航
        keyboard: true,

        // 是否启用幻灯片的概览模式,开启后，可以使用ESC键查看幻灯片概览
        overview: true,

        // 是否将幻灯片垂直居中
        center: true,

        // 是否在触屏设备上启用触摸导航
        touch: true,

        // 是否循环演示
        loop: false,

        // 是否将演示的方向变成 RTL
        rtl: false,

        // 全局开启和关闭碎片
        fragments: true,

        // 标识演示文稿是否在嵌入模式中运行，即包含在屏幕的有限部分中的
        embedded: false,

        // 标识当问号键被点击的时候是否应该显示一个帮助的覆盖层
        help: true,

        //  两个幻灯片之间自动切换的时间间隔（毫秒），当设置成 0 的时候则禁止自动切换，该值可以被幻灯片上的 ` data-autoslide` 属性覆盖
        autoSlide: 0,

        // 当遇到用户输入的时候停止切换
        autoSlideStoppable: true,

        // 是否启用通过鼠标滚轮来导航幻灯片
        mouseWheel: true,

        //  是否在移动设备上隐藏地址栏
        hideAddressBar: true,

        // 是否在一个弹出的 iframe 中打开幻灯片中的链接
        previewLinks: false,

        // 切换过渡效果
        transition: 'default', // none/fade/slide/convex/concave/zoom

        // 过渡速度
        transitionSpeed: 'default', // default/fast/slow

        // 全屏幻灯片背景的过渡效果
        backgroundTransition: 'default', // none/fade/slide/convex/concave/zoom

        // 除当前可见的之外的幻灯片数量
        viewDistance: 3,

        // 视差背景图片
        parallaxBackgroundImage: '', // e.g. "'https://s3.amazonaws.com/hakim-static/reveal-js/reveal-parallax-1.jpg'"

        // 视差背景尺寸
        parallaxBackgroundSize: '', // CSS syntax, e.g. "2100px 900px"

        // 移动视差背景（水平和垂直）滑动变化的数量, 例如100
        parallaxBackgroundHorizontal: '',
        parallaxBackgroundVertical: ''
    });*/

//    var COOKIE_NAME = 'sys__username';

    $(function () {
        /*$(".slide-number").bind('DOMNodeInserted', function (e) {
            switch (e.target.innerHTML) {
                case '1':
                    console.log("0")
                    break;
                case '2':
                    $($("section")[1]).find(".loadding").show();
                    setTimeout(function () {
                        $($("section")[1]).find(".loadding").hide();
                        var chart0 = echarts.init($($("section")[1]).find(".login-echart")[0]);
                        chart0.setOption(option0);
                    }, 1000);
                    break;
                case '3':
                    $($("section")[2]).find(".loadding").show();
                    setTimeout(function () {
                        $($("section")[2]).find(".loadding").hide();
                        var chart = echarts.init($($("section")[2]).find(".login-echart")[0]);
                        chart.setOption(option1);
                    }, 1000);
                    break;
                case '4':
                    console.log("0")
                    break;
                case '5':
                    console.log("0")
                    break;
            }


        });

//        $(".login-bg").css({"right": -$(window).width()},{"top": -$(window).height()});
        //控制显示
        $(".close-button").toggle(function () {
            $(".login-bg").stop(true).animate({right: 0, top: 0}, 500, function () {
                $(".close-button").text("关闭")
            });

        }, function () {
            $(".login-bg").stop(true).animate({right: -$(window).width(), top: -$(window).height()}, 500, function () {
                $(".close-button").text("打开")
            });
        })*/

        //获取链接中的错误信息
        try {
            var url  = decodeURI(location.href);
            if (url.indexOf('msg=') >= 0){
                var errorMsg = url.substring(url.indexOf('msg=')+'msg='.length);
                if (errorMsg.indexOf("&") > -1){
                    errorMsg = errorMsg.substring(0, errorMsg.indexOf("&"));
                }
                errorMsg = decodeURIComponent(errorMsg);
                if (errorMsg){
                    errorMsg = errorMsg.replace(/\+/g, " ");
                }
                $("#errorMsg").text(errorMsg);
            }
        } catch (e) {
        }

//        if ($.cookie(COOKIE_NAME)) {
//            $("#username").val($.cookie(COOKIE_NAME));
//            $("#remember").attr('checked', true);
//            $("#username").focus();
//        }

        $('#bg_img').css('height', function () {
            return $(window).height()
        });
        $('.login-wrap').css('margin-top', function () {
            return -parseInt($('.login-wrap').css('height')) / 2 - 20 + 'px'
        });

        
        $("input").keydown(function (e) {
            if(e.which=='13'){
                loginsubmit();
            }
        });

        $("#captcha_img").click(function(){
            $("#captcha_img").attr("src", "${ctx}/captcha?t="+genTimestamp());
        });

    });

    function loginsubmit() {
        var issubmit = true;

        if ($.trim($("#username").val()).length == 0) {
            $(".divUsername").css('border', '1px #ff0000 solid');
            $("#username").focus();
            issubmit = false;
            console.debug("username is null")
            return;
        }

        if ($.trim($("#password").val()).length == 0) {
            $(".divPassword1").css('border', '1px #ff0000 solid');
            $("#password1").focus();
            issubmit = false;
            console.debug("password is null")
            return;
        }

        if (!issubmit) {
            return;
        }

//      $("#password").val(md5($.trim($("#password1").val())));
//        var $remember = $("#remember");
//        if ($remember.attr('checked')) {
//            $.cookie(COOKIE_NAME, $("#username").val(), {path: '/', expires: 15});
//        } else {
//            $.cookie(COOKIE_NAME, null, {path: '/'});  //删除cookie
//        }

        //加密用户名，密码
        encodeUserNameAndPwd();

        $("#loginForm").submit();

    }

    /**
     * 加密用户名，密码
     */
    function encodeUserNameAndPwd(){
        var publicExponent = $("#publicKeyExponent").val();
        var publicModulus = $("#publicKeyModulus").val();
        RSAUtils.setMaxDigits(200);
        var rsaKey = new RSAUtils.getKeyPair(publicExponent, "", publicModulus);

        //加密用户名、密码
        $("[name=username]").val(function (i, val) {
            val = md5(val) + "-" + val;
            return RSAUtils.encryptedString(rsaKey, val.split("").reverse().join(""));
        });
        $("[name=password]").val(function (i, val) {
            val = md5(val) + "-" + val;
            return RSAUtils.encryptedString(rsaKey, val.split("").reverse().join(""));
        });
    }


    function genTimestamp() {
        var time = new Date();
        return time.getTime();
    }
</script>
</html>