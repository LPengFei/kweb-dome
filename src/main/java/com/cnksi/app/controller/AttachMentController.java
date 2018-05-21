package com.cnksi.app.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cnksi.app.AppConfig;
import com.cnksi.utils.IConstans;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * Created by Joey on 2017/2/27.
 */
public class AttachMentController extends Controller {

	public void view() {
		keepPara();
		String type = getPara("type");
		Objects.requireNonNull(type, "缺少附件类型！");
		String files = getPara("files");
		AttachmentType at = AttachmentType.valueOf(type);
		if (AttachmentType.video.equals(at)) {
			redirect("/" + AppConfig.appid + "attachment/download?type=video&file=" + files);
			return;
		}
		Objects.requireNonNull(files, "缺少附件信息！");
		List<String> pathList = new ArrayList<>();
		for (String file : files.split(",")) {
			pathList.add(type.concat("/").concat(file));
		}
		setAttr("pathList", pathList);
		render("view_pic.jsp");
	}

	/**
	 * 下载文件 file 为相对路径
	 *
	 */
	public void download() {
		String file = getPara("file");
		String 	fullPath = PropKit.get(IConstans.UPLOAD_FOLDER);
		String  refile = fullPath.concat(File.separator).concat(file);

		System.out.println("文件绝对路径：" + refile);
		if (refile!=null) {
			File f = new File(refile);
			renderFile(f);
		} else {
			// todo error page
			renderText("文件不存在");
		}
	}

	public enum AttachmentType {
		pictures, video;
	}
}
