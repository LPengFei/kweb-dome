package com.cnksi.kconf.controller;

import com.cnksi.kcore.exception.KException;
import com.cnksi.sec.SecurityProp;
import com.cnksi.utils.FileUtil;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 文件上传Controller
 * <p>
 * 建议所有文件上传均通过此模块上传
 *
 * @author joe
 */
public class FileManageController extends Controller {

    private Logger logger = Logger.getLogger(FileManageController.class);

    /**
     * 默认文件上传
     * folder 可指定上传保存的文件夹
     *  rename true/false 是否重命名文件
     */
    public void uploadfile() {
        StringBuffer sb = new StringBuffer();

        List<UploadFile> uploadFiles = getFiles();

        //验证文件后缀名，是否符合白名单限制，不符合的文件直接删除
        try {
            filterUploadFileWhiteList(uploadFiles);
        } catch (Exception e) {
            logger.error(e.getMessage());
            Ret ret = Ret.create("statusCode", "300").put("message", e.getMessage());
            renderJson(ret.getData());
            return;
        }

        //上传文件保存的文件夹，baseUploadPath + folder
        String folder = getPara("folder");
        Path basePath = null;
        if (StrKit.notBlank(folder)) {
            basePath = Paths.get(JFinal.me().getConstants().getBaseUploadPath(), folder);
            if (Files.notExists(basePath)) basePath.toFile().mkdirs();
        }

        Boolean rename = getParaToBoolean("rename", false);

        for (UploadFile file : uploadFiles) {
            String filename = file.getFileName();
            File fileObj = file.getFile();

            //根据参数可重命名文件
            if (rename == true){
                filename = FileUtil.getNewFileName() + "." + FileUtil.getSuffix(filename);
                fileObj = new File(file.getFile().getParent(), filename);
                file.getFile().renameTo(fileObj);
            }

            //将当前文件转存到指定文件夹
            if (null != basePath) {
                Path npath = Paths.get(basePath.toString(), filename);
                System.err.println("npath  "+npath);
                try {
                    Files.move(Paths.get(fileObj.toURI()), npath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    logger.error("转存文件失败：" + npath.toString());
                }
            }

            sb.append(filename).append(",");
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("statusCode", "200");
        resultMap.put("message", "上传成功！");
        resultMap.put("filename", sb.toString());

        renderJson(resultMap);
    }

    /**
     * 验证文件后缀名，是否符合白名单限制，不符合的文件直接删除
     * @param uploadFiles
     */
    private void filterUploadFileWhiteList(List<UploadFile> uploadFiles) {
        String[] whitelist = SecurityProp.uploadFileWhitelistStr.split(",");
        for (Iterator<UploadFile> iterator = uploadFiles.iterator(); iterator.hasNext(); ) {
            UploadFile uploadFile = iterator.next();
            if (uploadFile == null) continue;

            //是否匹配白名单中定义的文件后缀名
            boolean nomatch = Stream.of(whitelist).noneMatch(fileSuffix -> StrKit.notBlank(fileSuffix) && uploadFile.getFileName().endsWith("." + fileSuffix.trim()));
            //未匹配，删除文件
            if (nomatch){
                uploadFile.getFile().deleteOnExit();
                iterator.remove();
                throw new KException("不能上传" + FileUtil.getSuffix(uploadFile.getFileName()) + "文件");
            }

        }
    }

    /**
     * 下载/查看文件
     */
    public void downfile() {
        String fileName = getPara("filename");
        String type = getPara("type"); //保存下载文件的文件夹(basePath下)
        //String title = getPara("title"); //文件
        File file = new File(JFinal.me().getConstants().getBaseUploadPath(), fileName);
        File file1 = null;

        if (StrKit.notBlank(type)) {
            file1 = new File(JFinal.me().getConstants().getBaseUploadPath(), type.concat(File.separator).concat(fileName));
        } else {
            file1 = new File(JFinal.me().getConstants().getBaseUploadPath(), "pictures".concat(File.separator).concat(fileName));

        }
        if (file.exists()) {
            renderFile(file);
        }else {
        	renderFile(file1);
		}
    }
}
