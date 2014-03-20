package logic;

public class Test {
	
	public static void main(String[] args) {
		String tid = "2014-12-01 12:30:00.0";
		tid = tid.replaceAll("-", "");
		tid = tid.replace(":", "");
		tid=tid.replaceAll("\\s+", "");
		tid = tid.replace(".", "");
		long tidInt = Long.parseLong(tid);
		System.out.println(tidInt);
	}

}
