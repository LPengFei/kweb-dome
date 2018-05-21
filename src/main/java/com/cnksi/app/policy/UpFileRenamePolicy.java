package com.cnksi.app.policy;

import com.cnksi.utils.FileUtil;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.io.File;

/**
 * Created by Joey on 2016/5/13.
 * 上传文件名重命名规则
 */
public class UpFileRenamePolicy extends DefaultFileRenamePolicy {
    /**
     * 获取文件后缀
     *
     * @param @param  fileName
     * @param @return 设定文件
     * @return String 返回类型
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
    }

    /**
     * 将上传的文件重命名（路径不变）
     *
     * @param f
     * @return
     */
    public File rename(File f) {
        // 获取webRoot目录
        String webRoot = PathKit.getWebRootPath();
        // 用户设置的默认上传目录
        // {webRoot}/{baseUploadPath}/yyyyMMddHHmmssssss.txt
        StringBuilder newFileName = new StringBuilder(webRoot)
                .append(File.separator)
                .append(JFinal.me().getConstants().getBaseUploadPath())
                .append(File.separator)
                .append(FileUtil.getNewFileName())
                .append(getFileExt(f.getName()));

        File dest = new File(newFileName.toString());
        // 创建上层目录
        File dir = dest.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dest;
    }


}
