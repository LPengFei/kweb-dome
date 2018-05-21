package com.cnksi.kcore.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.StringTokenizer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("restriction")
public class SystemInfo {

	private static final int CPUTIME = 30;
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;
	private static String linuxVersion = null;

	/** 系统名 */
	private String os_name;
	/** 系统架构 */
	private String os_arch;
	/** 系统版本号 */
	private String os_version;
	/** 系统IP */
	private String os_ip;
	/** 系统MAC地址 */
	private String os_mac;
	/** 系统时间 */
	private Date os_date;
	/** 系统CPU个数 */
	private Integer os_cpus;
	/** 系统用户名 */
	private String os_user_name;
	/** 用户的当前工作目录 */
	private String os_user_dir;
	/** 用户的主目录 */
	private String os_user_home;

	/** Java的运行环境版本 */
	private String java_version;
	/** java默认的临时文件路径 */
	private String java_io_tmpdir;

	/** java 平台 */
	private String sun_desktop;

	/** 文件分隔符 在 unix 系统中是＂／＂ */
	private String file_separator;
	/** 路径分隔符 在 unix 系统中是＂:＂ */
	private String path_separator;
	/** 行分隔符 在 unix 系统中是＂/n＂ */
	private String line_separator;

	/** 服务context **/
	private String server_context;
	/** 服务器名 */
	private String server_name;
	/** 服务器端口 */
	private Integer server_port;
	/** 服务器地址 */
	private String server_addr;
	/** 获得客户端电脑的名字，若失败，则返回客户端电脑的ip地址 */
	private String server_host;
	/** 服务协议 */
	private String server_protocol;

	/** 可使用内存. */
	private String totalMemory;
	/** 剩余内存. */
	private String freeMemory;
	/** 最大可使用内存. */
	private String maxMemory;

	/** 总的物理内存. */
	private String totalMemorySize;
	/** 剩余的物理内存. */
	private String freePhysicalMemorySize;
	/** 已使用的物理内存. */
	private String usedMemory;
	/** 线程总数 **/
	private int totalThread = 0;
	/** cpu使用率 **/
	private String cpuRate;

	public static SystemInfo SYSTEMINFO;

	public static SystemInfo getInstance() {
		if (SYSTEMINFO == null) {
			SYSTEMINFO = new SystemInfo();
		}
		return SYSTEMINFO;
	}

	public static SystemInfo getInstance(HttpServletRequest request) {
		if (SYSTEMINFO == null) {
			SYSTEMINFO = new SystemInfo(request);
		} else {
			SYSTEMINFO.ServerInfo(request);
		}
		return SYSTEMINFO;
	}

	private SystemInfo() {
		super();
		init();
	}

	private SystemInfo(HttpServletRequest request) {
		super();
		init();
		/**
		 * 额外信息
		 */
		ServerInfo(request);
	}

	/**
	 * 输出信息
	 */
	public void PrintInfo() {
		Properties props = System.getProperties();
		System.out.println("Java的运行环境版本：" + props.getProperty("java.version"));
		System.out.println("默认的临时文件路径：" + props.getProperty("java.io.tmpdir"));
		System.out.println("操作系统的名称：" + props.getProperty("os.name"));
		System.out.println("操作系统的构架：" + props.getProperty("os.arch"));
		System.out.println("操作系统的版本：" + props.getProperty("os.version"));
		System.out.println("文件分隔符：" + props.getProperty("file.separator")); // 在 unix 系统中是＂／＂
		System.out.println("路径分隔符：" + props.getProperty("path.separator")); // 在 unix 系统中是＂:＂
		System.out.println("行分隔符：" + props.getProperty("line.separator")); // 在 unix 系统中是＂/n＂
		System.out.println("用户的账户名称：" + props.getProperty("user.name"));
		System.out.println("用户的主目录：" + props.getProperty("user.home"));
		System.out.println("用户的当前工作目录：" + props.getProperty("user.dir"));

		System.out.println("最大可使用内存=" + getMaxMemory());
		System.out.println("可使用内存=" + getTotalMemory());
		System.out.println("剩余内存=" + getFreeMemory());

		System.out.println("总的物理内存=" + getTotalMemorySize());
		System.out.println("已使用的物理内存=" + getUsedMemory());
		System.out.println("剩余的物理内存=" + getFreePhysicalMemorySize());
		System.out.println("线程总数=" + getTotalThread());

		System.out.println("pid: " + getPid());
		System.out.println("FreeMemory: " + getFreeMemoryMB());
		System.out.println("usedMemory: " + getUsedMemoryMB());
	}

	/**
	 * 初始化基本属性
	 */
	public void init() {
		Properties props = System.getProperties();

		this.java_version = props.getProperty("java.version");
		this.java_io_tmpdir = props.getProperty("java.io.tmpdir");
		this.os_name = props.getProperty("os.name");
		this.os_arch = props.getProperty("os.arch");
		this.os_version = props.getProperty("os.version");
		this.file_separator = props.getProperty("file.separator");
		this.path_separator = props.getProperty("path.separator");
		this.line_separator = props.getProperty("line.separator");

		this.os_user_name = props.getProperty("user.name");
		this.os_user_home = props.getProperty("user.home");
		this.os_user_dir = props.getProperty("user.dir");

		this.sun_desktop = props.getProperty("sun.desktop");

		this.os_date = new Date();
		this.os_cpus = Runtime.getRuntime().availableProcessors();

		int kb = 1024 * 1024;
		// 可使用内存
		this.totalMemory = (Runtime.getRuntime().totalMemory() / kb) + "MB";
		// 剩余内存
		this.freeMemory = (Runtime.getRuntime().freeMemory() / kb) + "MB";
		// 最大可使用内存
		this.maxMemory = (Runtime.getRuntime().maxMemory() / kb) + "MB";
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// 总的物理内存
		this.totalMemorySize = (osmxb.getTotalPhysicalMemorySize() / kb) + "MB";
		// 剩余的物理内存
		this.freePhysicalMemorySize = (osmxb.getFreePhysicalMemorySize() / kb) + "MB";
		// 已使用的物理内存
		this.usedMemory = ((osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb) + "MB";
		// 获得线程总数
		ThreadGroup parentThread;
		for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent()) {

		}
		this.totalThread = parentThread.activeCount();
		double cpuRatio = 0;
		if (os_name.toLowerCase().startsWith("windows")) {
			cpuRatio = this.getCpuRatioForWindows();
		} else {
			cpuRatio = getCpuRateForLinux();
		}
		this.cpuRate = cpuRatio + "%";

		try {
			ipMac();
		} catch (Exception e) {
			this.os_ip = "";
			this.os_mac = "";
		}
	}

	public int getPid() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName(); // format: "pid@hostname"
		System.out.println("name:" + name);
		try {
			return Integer.parseInt(name.substring(0, name.indexOf('@')));
		} catch (Exception e) {
			return -1;
		}
	}

	public String getPidHostName() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		return runtime.getName(); // format: "pid@hostname"
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576; // 1024 * 1024 = 1048576;
	}

	public long getFreeMemoryMB() {
		return Runtime.getRuntime().freeMemory() / 1048576; // 1024 * 1024 = 1048576;
	}

	private static double getCpuRateForLinux() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		try {
			System.out.println("Get usage rate of CUP , linux version: " + linuxVersion);
			Process process = Runtime.getRuntime().exec("top -b -n 1");
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			brStat = new BufferedReader(isr);
			if (linuxVersion.equals("2.4")) {
				brStat.readLine();
				brStat.readLine();
				brStat.readLine();
				brStat.readLine();
				tokenStat = new StringTokenizer(brStat.readLine());
				tokenStat.nextToken();
				tokenStat.nextToken();
				String user = tokenStat.nextToken();
				tokenStat.nextToken();
				String system = tokenStat.nextToken();
				tokenStat.nextToken();
				String nice = tokenStat.nextToken();
				System.out.println(user + " , " + system + " , " + nice);
				user = user.substring(0, user.indexOf("%"));
				system = system.substring(0, system.indexOf("%"));
				nice = nice.substring(0, nice.indexOf("%"));
				float userUsage = new Float(user).floatValue();
				float systemUsage = new Float(system).floatValue();
				float niceUsage = new Float(nice).floatValue();
				return (userUsage + systemUsage + niceUsage) / 100;
			} else {
				brStat.readLine();
				brStat.readLine();
				tokenStat = new StringTokenizer(brStat.readLine());
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				String cpuUsage = tokenStat.nextToken();
				System.out.println("CPU idle : " + cpuUsage);
				Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
				return (1 - usage.floatValue() / 100);
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			freeResource(is, isr, brStat);
			return 1;
		} finally {
			freeResource(is, isr, brStat);
		}
	}

	private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
		try {
			if (is != null)
				is.close();
			if (isr != null)
				isr.close();
			if (br != null)
				br.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	/**
	 * 获得CPU使用率.
	 *
	 * @return 返回cpu使用率
	 * @author GuoHuang
	 */
	private double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(PERCENT * (busytime) / (busytime + idletime)).doubleValue();
			} else {
				return 0.0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * 读取CPU信息.
	 * 
	 * @param proc
	 * @return
	 * @author GuoHuang
	 */
	private long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = line.substring(capidx, cmdidx - 1).trim();
				String cmd = line.substring(cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = line.substring(kmtidx, rocidx - 1).trim();
				String s2 = line.substring(umtidx, wocidx - 1).trim();
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0)
						idletime += Long.valueOf(s1).longValue();
					if (s2.length() > 0)
						idletime += Long.valueOf(s2).longValue();
					continue;
				}
				if (s1.length() > 0)
					kneltime += Long.valueOf(s1).longValue();
				if (s2.length() > 0)
					usertime += Long.valueOf(s2).longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取ip和mac地址
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	private void ipMac() throws Exception {
		InetAddress address = InetAddress.getLocalHost();
		NetworkInterface ni = NetworkInterface.getByInetAddress(address);
		ni.getInetAddresses().nextElement().getAddress();
		byte[] mac = ni.getHardwareAddress();
		String sIP = address.getHostAddress();
		String sMAC = "";
		Formatter formatter = new Formatter();
		for (int i = 0; i < mac.length; i++) {
			sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString();

		}
		SystemInfo.this.os_ip = sIP;
		SystemInfo.this.os_mac = sMAC;
	}

	/**
	 * 获取服务器信息
	 * 
	 * @param request
	 */
	public void ServerInfo(HttpServletRequest request) {
		this.server_name = request.getServerName();
		this.server_port = request.getServerPort();
		this.server_addr = request.getRemoteAddr();
		this.server_host = request.getRemoteHost();
		this.server_protocol = request.getProtocol();
		this.server_context = request.getContextPath();
	}

	public String getOs_name() {
		return os_name;
	}

	public String getOs_arch() {
		return os_arch;
	}

	public String getOs_version() {
		return os_version;
	}

	public String getOs_ip() {
		return os_ip;
	}

	public String getOs_mac() {
		return os_mac;
	}

	public Date getOs_date() {
		return os_date;
	}

	public Integer getOs_cpus() {
		return os_cpus;
	}

	public String getOs_user_name() {
		return os_user_name;
	}

	public String getOs_user_dir() {
		return os_user_dir;
	}

	public String getOs_user_home() {
		return os_user_home;
	}

	public String getJava_version() {
		return java_version;
	}

	public String getJava_io_tmpdir() {
		return java_io_tmpdir;
	}

	public String getSun_desktop() {
		return sun_desktop;
	}

	public String getFile_separator() {
		return file_separator;
	}

	public String getPath_separator() {
		return path_separator;
	}

	public String getLine_separator() {
		return line_separator;
	}

	public String getServer_context() {
		return server_context;
	}

	public String getServer_name() {
		return server_name;
	}

	public Integer getServer_port() {
		return server_port;
	}

	public String getServer_addr() {
		return server_addr;
	}

	public String getServer_host() {
		return server_host;
	}

	public String getServer_protocol() {
		return server_protocol;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}

	public String getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}

	public String getTotalMemorySize() {
		return totalMemorySize;
	}

	public void setTotalMemorySize(String totalMemorySize) {
		this.totalMemorySize = totalMemorySize;
	}

	public String getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(String freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public String getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}

	public String getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public String getCpuRate() {
		return cpuRate;
	}

}
