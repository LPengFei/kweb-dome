<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<style>
    li, p, span{
        font-size: 14px;
    }
    .safe_listView{
        margin: 20px 20px 20px 0;
        padding: 20px 20px 10px 20px;
        background: #fff;
        border: 1px solid #e0e2e5;
        border-radius: 6px;
    }
    .afe_listView_title{
        font-size: 20px;
        color: #e7604a;
        text-align: center;
    }
    .safe_listView .safe_listView_title{
        color: #15cab5;
        font-size: 18px;
        margin-bottom: 20px;
        margin-top: 0;
    }
    .safe_listView_info{
        position: relative;
        display: flex;
        padding-bottom: 10px;
        margin-top: 10px;
    }
    .safe_listView_info li{
        flex: 0 0 20%;
    }
    .safe_listView_info li p:first-child{
        color: #979e9d;
    }
    .safe_listView_info li{
        color: #565656;
    }
    .safe_listView_list{
        margin-top: 20px;
        color: #738381;
    }
    .safe_listView_list li{
        border-top: 1px dashed #e0e2e5;
        padding: 10px 0;
    }
    .safe_listView_list li:first-child{
        border-top: none;
    }
    .safe_listView_list li .safe_listView_list_time{
    }
    .safe_listView_list li .safe_listView_list_time:before{
        content: '';
        position: relative;
        margin: 0 6px 0 4px;
        top: -1px;
        display: inline-block;
        width: 7px;
        height: 7px;
        border: 1px solid #15cab5;
        border-radius: 50%;
    }
    .safe_listView_audio{
        display: flex;
    }
    .safe_imgShow{
        margin-right: 8px;
        width: 100px;
        height: 75px;
        border-radius: 4px;
    }
    .safe_audioBox{
        position: relative;
        margin-right: 8px;
        width: 100px;
        height: 75px;
        background: url("/images/vidio_bg.png") no-repeat;
        background-size: 100% 100%;
        cursor: pointer;
    }
    .audio_control{
        position: absolute;
        bottom: 0;
        width: 100%;
        height: 30%;
        background: rgba(0, 0, 0, 0.1);
        padding: 0 5px;
        transition: .1s;
    }
    .audio_control .icon_audio{
        position: absolute;
        bottom: 5px;
        left: 5px;
        width: 15px;
        height: 15px;
        transition: .1s;
    }
    .icon_play .icon_audio{
        background: url("/images/icon_play.png");
        background-size: 100% 100%;
    }
    .icon_pause .icon_audio{
        background: url("/images/icon_pause.png");
        background-size: 100% 100%;
        transform: translate(40px,-30px) scale(1.5);
    }
    .icon_play .updata_time{
        display: none;
    }
    .icon_pause .updata_time{
        display: inline;
    }
    .icon_pause .audio_control{
        height: 100%;
        background: rgba(0, 0, 0, 0.3);
    }
    .audio_control cite{
        position: absolute;
        bottom: 5px;
        right: 5px;
        color: #fff;
        transition: .5s;
    }
    .icon_pause cite{
        right: 2px;
    }
    .safe_listView_digest li{
        position: relative;
        background: #f5f8fa;
        border-radius: 6px;
        padding: 8px 0 8px 30px;
        margin-top: 20px;
        display: flex;
        align-items: center;
    }
    .safe_listView_digest li:before{
        content: "";
        position: absolute;
        width: 6px;
        height: 100%;
        background: #15cab5;
        left: 0;
        top: 0;
        border-radius: 6px 0 0 6px;
    }
    .safe_listView_digest li .safe_listView_digest_time{
        color: #15cab5;
        font-size: 16px;
        margin-right: 80px;
    }
    .safe_listView_digest .safe_listView_digest_list{
        display: flex;
        align-items: center;
    }
    .safe_listView_digest .safe_listView_digest_person{
        margin-right: 80px;
    }
    .safe_listView_digest .safe_listView_digest_person span{
        color: #979e9d;
    }
    .safe_listView_item{
        background: #fdf8f3;
        padding: 5px 4px 4px 4px;
        border-radius: 6px;
        margin-bottom: 20px;
    }
    .safe_listView_item .safe_listView_subtitle{
        color: #5f6d6a;
        font-size: 16px;
        margin: 15px 0 15px 10px;
    }
    .safe_listView_item .safe_listView_subtitle a{
        padding: 6px 15px;
        background: #14CAB4;
        border-radius: 4px;
        color: #fff;
        float: right;
        margin-right: 100px;
        margin-top: -3px;
    }
    .safe_listView_item .safe_listView_subtitle a:hover{
        text-decoration: none;
    }
    .safe_listView_content{
        background: #fff;
        padding: 10px;
        border-radius: 6px;
    }
    .attachfile_icon {
        height: 13px;
    }
</style>
<h2 class="afe_listView_title">乐山公司2018第一次安全例会</h2>
<div class="navtabPage">
    <div class="safe_listView">
        <h2 class="safe_listView_title">会议基本信息</h2>
        <ul class="safe_listView_info">
            <li>
                <p>安全例会名称</p>
                <p>${record.name}</p>
            </li>
            <li>
                <p>组织者</p>
                <p>${record.organizer}</p>
            </li>
            <li>
                <p>安全例会类型</p>
                <p>${record.type}</p>
            </li>
            <li>
                <p>参会人员</p>
                <p>${record.participant}</p>
            </li>
            <li>
                <p>安全例会时间</p>
                <p><fmt:formatDate value="${record.conference_time}"  type="date" dateStyle="default"/></p>
            </li>
        </ul>
    </div>
    <div class="safe_listView">
        <h2 class="safe_listView_title">会议议题</h2>
        <ul class="safe_listView_list">
            <c:forEach items="${record.issues}" var="v" varStatus="s">
            <li>
                <span class="safe_listView_list_time">${s.count}.</span>
                <span class="safe_listView_list_detail">${v}</span>
            </li>
            </c:forEach>
        </ul>
    </div>
    <div class="safe_listView">
        <h2 class="safe_listView_title">会议录音</h2>
        <div class="safe_listView_audio">
            <c:forEach items="${record.audios}" var="audio">
                <div class="safe_audioBox icon_play">
                    <audio src="${ctx }/kconf/file/downfile?type=pictures&filename=${audio}"></audio>
                    <div class="audio_control">
                        <div class="icon_audio"></div>
                        <cite>
                            <span class="updata_time"></span>
                            <span class="audio_time"></span>
                        </cite>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="safe_listView">
        <h2 class="safe_listView_title">会议照片</h2>
        <div class="safe_listView_audio">
            <c:forEach items="${record.photos}" var="photo">
                <img class="fancy_arr safe_imgShow" src="${ctx }/kconf/file/downfile?type=pictures&filename=${photo}" alt=""
                     data-fancybox-group="conference_view_photo_${record.id}" href="${ctx }/kconf/file/downfile?type=pictures&filename=${photo}">
            </c:forEach>
        </div>
    </div>
    <div class="safe_listView">
        <h2 class="safe_listView_title">会议纪要</h2>
        <c:forEach items="${record.summarys}" var="summary" varStatus="s">
        <div class="safe_listView_item">
            <h3 class="safe_listView_subtitle">
                ${summary.content}
                <a href="${ctx}/app/conferencesummary/execute" data-tog gle="dialog" data-id="644787899163380768-execute" data-title="执行情况" data-width="1000" data-height="400" data-mask="true">执行</a>
            </h3>
            <div class="safe_listView_content">
                <ul class="safe_listView_info" style="border-bottom: 1px dashed #e0e2e5">
                    <li>
                        <p>执行人</p>
                        <p>${summary.exec_dept}<c:if test="${summary.exec_dept != null && summary.exec_person != null}">,</c:if>${summary.exec_person}</p>
                    </li>
                    <li>
                        <p>最后执行时间</p>
                        <p><fmt:formatDate value="${summary.exec_last_time}"  type="date" dateStyle="default"/></p>
                    </li>
                    <li>
                        <p>执行状态</p>
                        <p>${summary.status}</p>
                    </li>
                </ul>

                <ul class="safe_listView_digest">
                    <c:forEach items="${summary.execs}" var="exec" varStatus="s">
                        <li>
                            <span class="safe_listView_digest_person"><span>执行人：</span>${exec.exec_dept}<c:if test="${exec.exec_dept != null && exec.exec_person != null}">,</c:if>${exec.exec_person}</span>
                            <span class="safe_listView_digest_time"><span>执行时间：</span><fmt:formatDate value="${exec.exec_time}"  type="date" dateStyle="default"/></span>
                            <div class="safe_listView_digest_list">
                                <span>执行情况：</span>
                                <c:forEach items="${exec.photos}" var="photo">
                                    <img class="fancy_arr safe_imgShow" src="${ctx }/kconf/file/downfile?type=pictures&filename=${photo}" alt=""
                                         data-fancybox-group="conference_view_photo_${record.id}" href="${ctx }/kconf/file/downfile?type=pictures&filename=${photo}">
                                </c:forEach>
                                <c:forEach items="${exec.videos}" var="video">
                                    <video height="100%" class="safe_imgShow"
                                           src="${ctx }/kconf/file/downfile?type=video&filename=${video}"
                                           data-fancybox-group="conference_view_video_${r.id}"></video>
                                </c:forEach>
                                <!--
                                    如果有音频 写在这个div里面
                                    一个safe_audioBox是一个音频
                                -->
                                <c:forEach items="${exec.audios}" var="audio">
                                    <div class="safe_listView_audio">
                                        <div class="safe_audioBox icon_play">
                                            <audio src="${ctx }/kconf/file/downfile?type=pictures&filename=${audio}"></audio>
                                            <div class="audio_control">
                                                <div class="icon_audio"></div>
                                                <cite>
                                                    <span class="updata_time"></span>
                                                    <span class="audio_time"></span>
                                                </cite>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:forEach items="${exec.files}" var="file">
                                    <span style="margin-left: 5px">
                                    <img src="${ctx}/images/attachfile.png" class="attachfile_icon"><a href="${ctx }/kconf/file/downfile?type=upload&filename=${file}"></a>
                                    </span>
                                </c:forEach>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        </c:forEach>
    </div>
</div>

<script>
    init_time()
    $('.safe_listView_audio').delegate('.safe_audioBox','click', function(){
      var audio = $(this).find('audio').get(0)
      var otherAudio = $(this).siblings().find('audio')
      if(audio.paused){
        audio.play()
        $(this).removeClass('icon_play').addClass('icon_pause')
        if(otherAudio.length){
          // 其他播放暂停
          otherAudio.get(0).pause()
          // 播放时间都归0
          otherAudio.get(0).currentTime=0
          $(this).siblings().removeClass('icon_pause').addClass('icon_play')
        }
        audio.addEventListener('ended',audioEnded,false);
        audio.addEventListener('timeupdate',audioTime,false)
      }else{
        audio.pause()
        $(this).removeClass('icon_pause').addClass('icon_play')
      }
    })
    // 初始化音频时间
    function init_time() {
      $(".safe_audioBox").each(function(idx,item) {
        $(item).find('audio').on("loadedmetadata",function () {
          $(this).siblings('.audio_control').find('.audio_time').text(transTime($(this).get(0).duration))
        });
      })
    }
    // 播放完成后
    function audioEnded() {
        $(this).parent('.safe_audioBox').removeClass('icon_pause').addClass('icon_play')
    }
    // 更新播放时间
    function audioTime() {
      $(this).siblings('.audio_control').find('.updata_time').text(transTime($(this).get(0).currentTime)+'/')
    }
    //转换音频时长显示
    function transTime(time) {
      var duration = parseInt(time);
      var minute = parseInt(duration/60);
      var sec = duration%60+'';
      var isM0 = ':';
      if(minute == 0){
        minute = '00';
      }else if(minute < 10 ){
        minute = '0'+minute;
      }
      if(sec.length == 1){
        sec = '0'+sec;
      }
      return minute+isM0+sec
    }
</script>