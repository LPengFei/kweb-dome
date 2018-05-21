package com.cnksi.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

public class FileUtil {

    /**
     * 判断上传文件
     */

    private final static String[] fileTypes = {"jpg", "png", "gif"};

    /**
     * 判断上传图片格式是否正确 如果返回为true 文件格式正确
     *
     * @return
     */
    public static boolean checkImgFileType(String fileType) {
        if (fileType == null) {
            return false;
        }
        String value = fileType.toLowerCase();
        for (String type : fileTypes) {
            if (type.equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断上传的excel 格式是否正确 true 正确 flase 错误
     */

    public static boolean checkExcelFileType(String fileType) {
        if (fileType == null) {
            return false;
        }
        String value = fileType.toLowerCase();

        if ("xls".equals(value)) {
            return true;
        }
        if ("xlsx".equals(value)) {
            return true;
        }
        return false;
    }

    public static void download(HttpServletRequest request,
                                HttpServletResponse response, String path, String contentType,
                                String realName) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        long fileLength = new File(path).length();
        response.setContentType(contentType);
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(realName.getBytes("gbk"), "ISO8859-1"));
//		response.setHeader("Content-disposition", "attachment; filename="
//				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(fileLength));
        bis = new BufferedInputStream(new FileInputStream(path));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    public static void download(HttpServletResponse response, File file, String fileName) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        long fileLength = file.length();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=".concat(new String(fileName.getBytes("gbk"), "ISO8859-1")));
        response.setHeader("Content-Length", String.valueOf(fileLength));
        bis = new BufferedInputStream(new FileInputStream(file));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 获取文件扩展名 txt,doc等
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        String suffix = "";
        int index = fileName.lastIndexOf(".");
        if (index > -1 && fileName.length() > index) {
            suffix = fileName.substring(index + 1);
        }
        return suffix;
    }

    /**
     * 文件重命名，保留源文件的父目录和扩展名<br/>
     * 重命名成功返回新文件，否则返回空
     *
     * @param file
     * @param newFilename
     * @return
     */
    public static File renameTo(File file, String newFilename) {
        try {
            if (file == null) return null;
            File parent = file.getParentFile();
            String suffix = getSuffix(file.getName());
            newFilename += "." + suffix;
            File newFile = new File(parent, newFilename);
            boolean flag = file.renameTo(newFile);
            if (flag) return newFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeStream(Object o) {
        if (o == null) return;

        try {
            if (o instanceof InputStream) {
                ((InputStream) o).close();
            } else if (o instanceof OutputStream) {
                ((OutputStream) o).close();
            }
        } catch (IOException e) {
        }
    }


    /**
     * 以系统时间返回照相图片文件名称  不包含文件后缀
     *
     * @return yyyyMMddHHmmssSSSwei
     */
    public static String getNewFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA);
        String uuidStr = UUID.randomUUID().toString().replace("-", "");
        if (uuidStr.length() > 3) {
            uuidStr = uuidStr.substring(0, 3);
        }
        return String.valueOf(formatter.format(new Date()) + uuidStr);
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除文件不存在的文件名
     *
     * @param files
     * @param type  video/pictures
     */
    public static List<String> deleteNotExitList(List<String> files, String type) {

        List<String> exits = new ArrayList<>();
        for (String file : files) {
          String newFile =  deleteNotExit(file,type);
          if(newFile!=null){
              exits.add(newFile);
          }
        }

        return exits;
    }

    /**
     * 删除文件不存在的文件名
     *
     * @param file
     * @param type video/pictures
     */
    public static String deleteNotExit(String file, String type) {
        if (StrKit.isBlank(file)) {
            return null;
        }
        String fullPath = "";
        if(StrKit.notBlank(type)){
            fullPath = PropKit.get(IConstans.UPLOAD_FOLDER).concat(File.separator).concat(type);
        }else{
            fullPath = PropKit.get(IConstans.UPLOAD_FOLDER);
        }
       System.err.println(fullPath);
       String  refile = fullPath.concat(File.separator).concat(file);
        File f = new File(refile);
        if (!f.exists()) {
            return null;
        }else{
            file = showServicePath(f,type);
        }

        return file;
    }

   /* *
     * 上传文件
     *
     * @param uf
     * @param type
     /*
    public static String setUploadPath(UploadFile uf, String type) {
        String uploadPath = "";
        if (StrKit.notBlank(type)) {
            uploadPath = PropKit.get(IConstans.UPLOAD_FOLDER).concat(File.separator).concat(uf.getFileName());

        } else {
            uploadPath = PropKit.get(IConstans.UPLOAD_FOLDER).concat(File.separator).concat(type).concat(File.separator).concat(uf.getFileName());

        }
        File upFile = new File(uploadPath);

        if (!upFile.getParentFile().exists()) {

            upFile.getParentFile().mkdirs();

        }

        uf.getFile().renameTo(upFile);
        return upFile.getAbsolutePath();
    }*/

    public static String  showServicePath(File file,String type){
        //配置服务器图片存放地址
        String fullPath = "";
        if (StrKit.notBlank(type)) {
             fullPath = PropKit.get("synchronizingServerPath").concat(type).concat("/");

        }else{
            fullPath = PropKit.get("synchronizingServerPath");
        }

        //先判断文件夹是否存在,不存在就创建文件夹
        File newFile  = new File(fullPath);
        if(!newFile.exists()){
            newFile.mkdirs();
        }

        String fileRealPath = fullPath.concat(file.getName());
        /*File fileReal = new File(fileRealPath);
        if(!fileReal.exists()){
            fileRealPath = null;

        }*/
        return fileRealPath;

    }

}
