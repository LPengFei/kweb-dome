<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>

<div id="fine-uploader" style="position:relative; display:block; width:960px;background:#f3f8fc;overflow: auto;"></div>
<script type="text/template" id="qq-template">
    <div class="qq-uploader-selector qq-uploader qq-gallery" qq-drop-area-text="将文件拖拽至此区域">
        <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
            <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
        </div>
        <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
            <span class="qq-upload-drop-area-text-selector"></span>
        </div>
        <div class="qq-upload-button-selector qq-upload-button">
            <div>上传文件</div>
        </div>
        <span class="qq-drop-processing-selector qq-drop-processing">
            <span>Processing dropped files...</span>
            <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
        </span>
        <ul class="qq-upload-list-selector qq-upload-list" role="region" aria-live="polite" aria-relevant="additions removals">
            <li style="min-width: auto;">
                <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                <div class="qq-progress-bar-container-selector qq-progress-bar-container">
                    <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                </div>
                <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                <div class="qq-thumbnail-wrapper">
                    <img class="qq-thumbnail-selector" qq-max-size="120" qq-server-scale>
                </div>
                <button type="button" class="qq-upload-cancel-selector qq-upload-cancel">X</button>
                <button type="button" class="qq-upload-retry-selector qq-upload-retry">
                    <span class="qq-btn qq-retry-icon" aria-label="Retry"></span>
                    重试
                </button>

                <div class="qq-file-info">
                    <div class="qq-file-name">
                        <span class="qq-upload-file-selector qq-upload-file"></span>
                        <span class="qq-edit-filename-icon-selector qq-btn qq-edit-filename-icon" aria-label="Edit filename"></span>
                    </div>
                    <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                    <span class="qq-upload-size-selector qq-upload-size"></span>
                    <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">
                        <span class="qq-btn qq-delete-icon" aria-label="Delete"></span>
                    </button>
                    <button type="button" class="qq-btn qq-upload-pause-selector qq-upload-pause">
                        <span class="qq-btn qq-pause-icon" aria-label="Pause"></span>
                    </button>
                    <button type="button" class="qq-btn qq-upload-continue-selector qq-upload-continue">
                        <span class="qq-btn qq-continue-icon" aria-label="Continue"></span>
                    </button>
                </div>
            </li>
        </ul>

        <dialog class="qq-alert-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">关闭</button>
            </div>
        </dialog>

        <dialog class="qq-confirm-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">取消</button>
                <button type="button" class="qq-ok-button-selector">确认</button>
            </div>
        </dialog>

        <dialog class="qq-prompt-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <input type="text">
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">取消</button>
                <button type="button" class="qq-ok-button-selector">确认</button>
            </div>
        </dialog>
    </div>
</script>

<script>

    $('#fine-uploader').fineUploader({
        //调试，上线关闭
        //debug: true,
        template: 'qq-template',
        request: {
            //上传处理函数
            endpoint: "${ctx }/kconf/file/uploadfile?folder=pictures"
        },
        //是否支持多文件同时上传
        multiple: ${f.file_multi},
        //是否自动提交
        autoUpload: true,
        session: {
            endpoint: '${ctx }/kconf/file/downfile?type=pictures',

        },
        deleteFile: {
            enabled: true,
            endpoint: '${ctx }/kconf/file/deletefile?type=pictures',
            method: 'POST',
            forceConfirm: true,
            confirmMessage: '确定要删除文件 {filename} 吗？',
            deletingFailedText: '删除失败！'
        },
        validation: {
            //单次上传限制数量
            itemLimit: 5,
            allowedExtensions: ['pdf','avi','doc','docx','png','jpg','gif'],
            sizeLimit: $('#file_size_limit').val() // 50 kB = 50 * 1024 bytes,
        },
        thumbnails: {
            placeholders: {
                notAvailablePath: "${ctx }/BJUI/jquery.fine-uploader/placeholders/not_available-generic.png",
                waitingPath: "${ctx }/BJUI/jquery.fine-uploader/placeholders/waiting-generic.png"
            }
        },
        text: {
            failUpload: '上传失败',
            waitingForResponse: "处理中..."
        },
        messages: {
            typeError: "{file} 无效的扩展名.支持的扩展名: {extensions}.",
            sizeError: "{file} 文件太大，最大支持的文件大小 {sizeLimit}.",
            minSizeError: "{file} 太小，最小文件大小为：{minSizeLimit}.",
            emptyError: "{file} 文件为空，请重新选择",
            noFilesError: "文件不存在",
            tooManyItemsError: "Too many items ({netItems}) would be uploaded.  Item limit is {itemLimit}.",
            maxHeightImageError: "Image is too tall.",
            maxWidthImageError: "Image is too wide.",
            minHeightImageError: "Image is not tall enough.",
            minWidthImageError: "Image is not wide enough.",
            retryFailTooManyItems: "Retry failed - you have reached your file limit.",
            onLeave: "The files are being uploaded, if you leave now the upload will be canceled.",
            unsupportedBrowserIos8Safari: "Unrecoverable error - this browser does not permit file uploading of any kind due to serious bugs in iOS8 Safari.  Please use iOS8 Chrome until Apple fixes these issues."
        }
    }).on({                                                       //注册监听事件
        "complete": function(event, id, fileName, responseJSON) { //完成
            if(responseJSON.success) {
                get_uploadName()
            }
        },
        "cancel": function(event, id,fileName){                   //取消
            //console.log(fileName);
        },
        "delete": function(event, id,fileName){                   //删除文件,根据需求去删除已上传到服务器上的图片

            setTimeout(function(){
                get_uploadName()
            },1)
        },
        "submit": function(event, id, fileName) {                 //选择文件后
            //console.log(event, id, fileName);
        },
        "error": function(event, id, fileName, reason) {          //出错,这里还有些其他事件，我暂时不用就没实践，大家可以自己去看api
            //console.log(event, id, fileName, reason);
        }
    });
    get_uploadName();

    function get_uploadName(){
        var name_string = [];
        $(".qq-file-info .qq-upload-file").each(function(){
            name_string.push($(this).text())
        });
        $("#uploader-name").val(name_string.join(","))
        console.log(name_string.join(","))
    }
</script>



