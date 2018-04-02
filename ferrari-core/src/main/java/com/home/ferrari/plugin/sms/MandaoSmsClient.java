package com.home.ferrari.plugin.sms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MandaoSmsClient {
	
	public static final String TZ_SN = "SDK-BJR-010-00608";
	public static final String TZ_PW = "07b[-e3c8-[";
	public static final String YX_SN = "SDK-BJR-010-00609";
	public static final String YX_PW = "34b4-d0$5-4";

	private String serviceURL = "http://sdk.entinfo.cn:8061/webservice.asmx";
	private String sn = "";
	private String password = "";
	private String pwd = "";

	public MandaoSmsClient(String sn, String password)
			throws UnsupportedEncodingException {
		this.sn = sn;
		this.password = password;
		this.pwd = this.getMD5(sn + password);
	}

	public String getMD5(String sourceStr) throws UnsupportedEncodingException {
		String resultStr = "";
		try {
			byte[] temp = sourceStr.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			// resultStr = new String(md5.digest());
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'A', 'B', 'C', 'D', 'E', 'F' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}


	public String mdgetSninfo() {
		String result = "";
		String soapAction = "http://entinfo.cn/mdgetSninfo";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdgetSninfo xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "<mobile>" + "" + "</mobile>";
		xml += "<content>" + "" + "</content>";
		xml += "<ext>" + "" + "</ext>";
		xml += "<stime>" + "" + "</stime>";
		xml += "<rrid>" + "" + "</rrid>";
		xml += "<msgfmt>" + "" + "</msgfmt>";
		xml += "</mdgetSninfo>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		URL url;
		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdgetSninfoResult>(.*)</mdgetSninfoResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			try {
				if (bout!=null) {
					bout.close();
				}
				if(out!=null){
					out.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(in!=null){
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 方法名称：mdgxsend 
	 * 功    能：发送个性短信 
	 * 参    数：mobile,content,ext,stime,rrid,msgfmt(手机号，内容，扩展码，定时时间，唯一标识，内容编码)
	 * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
	 */
	public String mdgxsend(String mobile, String content, String ext, String stime,
			String rrid, String msgfmt) {
		String result = "";
		String soapAction = "http://entinfo.cn/mdgxsend";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdgxsend xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<ext>" + ext + "</ext>";
		xml += "<stime>" + stime + "</stime>";
		xml += "<rrid>" + rrid + "</rrid>";
		xml += "<msgfmt>" + msgfmt + "</msgfmt>";
		xml += "</mdgxsend>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdgxsendResult>(.*)</mdgxsendResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			try {
				if (bout!=null) {
					bout.close();
				}
				if(out!=null){
					out.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(in!=null){
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 方法名称：mdsmssend
	 * 功    能：发送短信 
	 * 参    数：mobile,content,ext,stime,rrid,msgfmt(手机号，内容，扩展码，定时时间，唯一标识，内容编码)
	 * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
	 */
	public String send(String mobile, String content, String ext, String stime,
			String rrid,String msgfmt) {
		String result = "";
		String soapAction = "http://entinfo.cn/mdsmssend";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdsmssend  xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<ext>" + ext + "</ext>";
		xml += "<stime>" + stime + "</stime>";
		xml += "<rrid>" + rrid + "</rrid>";
		xml += "<msgfmt>" + msgfmt + "</msgfmt>";
		xml += "</mdsmssend>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdsmssendResult>(.*)</mdsmssendResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			try {
				if (bout!=null) {
					bout.close();
				}
				if(out!=null){
					out.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(in!=null){
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 方法名称：getBalance 
	 * 功    能：获取余额 
	 * 参    数：无 
	 * 返 回 值：余额（String）
	 */
	public String getBalance() {
		String serviceURL2 = "http://sdk.entinfo.cn:8060/webservice.asmx?op=balance";
		String result = "";
		String soapAction = "http://tempuri.org/balance";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<balance xmlns=\"http://tempuri.org/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "</balance>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		URL url;
		try {
			url = new URL(serviceURL2);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			OutputStream out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<balanceResult>(.*)</balanceResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			in.close();
			return new String(result.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException{
		String sn = YX_SN;
		String pwd = YX_PW;
		MandaoSmsClient client = new MandaoSmsClient(sn, pwd);
		
		String content = java.net.URLEncoder.encode("@某某门店，接口测试【xacl】",  "utf-8");  
		String result_mt = client.send("18867102687", content, "", "", "", "");
		System.out.println(result_mt);
		/**
		String result_gxmt = client.mdgxsend("18867102687", "接口测试2[星奥车联]", "", "", "", "");
		System.out.print(result_gxmt);
		**/
		System.out.println(client.getBalance());
	}
}
