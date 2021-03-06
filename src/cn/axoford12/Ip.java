package cn.axoford12;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidMethodNameException;

public class Ip {
	protected String IPDomain;
	protected String correctIp;

	// Construction.
	public Ip() {
		try {
			// set IPs
			this.IPDomain = this.getDomainIP();
			this.correctIp = this.getMyIP();
		} catch (InvalidMethodNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getDomainIP() throws InvalidMethodNameException {
		String re = HttpRequest.getReqter().send("POST", "https://dnsapi.cn/Record.Info", "login_token="
				+ Config.c.login_token + "&record_id=" + Config.c.record_id + "&domain=" + Config.c.domain);
		Pattern p = Pattern.compile("value>(.+?)</value");

		Matcher m = p.matcher(re);
		m.find();
		try {
			return m.group(1);
		} catch (Exception e) {
			// No match found.
			System.out.println("No network connection ,Please check Your network connection(s)");
		}
		return "";
	}

	private String getMyIP() throws InvalidMethodNameException {
		String re = HttpRequest.getReqter().send("GET", "http://1212.ip138.com/ic.asp", "");
		Pattern p = Pattern.compile("\\[(.+?)\\]");
		Matcher m = p.matcher(re);
		m.find();
		String result = "";
		try {
			result = m.group(1);
		} catch (Exception e) {

			System.out.println("No network connection ,Please check Your network connection(s)");
		}
		return result;
	}

	public Boolean isChanged() {
		Boolean result = false;
		if (!(this.correctIp.equals(this.IPDomain))) {
			result = true;
		}
		return result;
	}
}
