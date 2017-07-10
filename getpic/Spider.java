
/*
 v1.0 目前是可以实现基本的功能的，通过word输入关键词、j控制下载页面数
 v2.0 1.提升了对静态变量的使用。2.学会实现全局变量的功能。
      java中没有全局变量概念，我们可以声明一个类作为全局变量功能
 	  static表示“全局”或者“静态”的意思
 	  被static修饰的变量和方法，被类的所有实例共享。
 	  因此Spider.c = new Spider.); c.number引用，这种方式可以换为Spider.number  
  v2.1 打印标示，看看究竟是哪一步卡住了，常常是在save2 到3卡住，但是等一段时间也会通过。

*/
// 经常在save2和save4处卡住，save2处问题更严重
import java.net.URLEncoder;
import java.io.*;
import java.net.*;
import java.util.regex.*;



public class Spider{
	static String str_array[] = new String[30];
	// 在此输入爬取数量和关键字
	static int n = 20;
	static String word = "刘亦菲";

	// 记录成功下载的图片的数量
	static int number = 1;
	// 记录某个页面是否有30个图片
	static int markNumber = 0;
	


	public static void main(String[] args) {
		Downloadpic pic = new Downloadpic();
		
		// 创建文件夹
		boolean b = pic.createDir(word);

		String pageUrl = "";
		String pageContent = "";

		
		

		for (int j=1;j<=(n/10+2) ;j++ ) {
			// 获取相关数据
			pageUrl = firstPageUrl(word, j);
			pageContent = getPageContent(pageUrl);
			fromPageGetImageUrls(pageContent);

			// 判断是否该结束
			for (int i=0;i<30 ;i++) {
				if (number>n) {
				System.out.println("下载完成。");
				System.exit(0);
					
				}

			// 打印序号并下载图片
			System.out.println(Spider.number+"....");
			pic.download(decode(str_array[i]), word);
				


				
			}
		}
		
		
		

	}




	//获取指定页面的页面地址
	public static String firstPageUrl(String str,int n){
		// 标示一下以便找出是哪出了错，卡住了
		System.out.print("firstPageUrl...   ");
		String word = "";
		String url = "";
		String num = "";
		String str1 = "http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord+=&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&word=";
		String str2 = "&z=&ic=0&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&step_word=";
		String str3 = "&pn=";
		String str4 = "&rn=30";
		int temp = n*30;
		num = String.valueOf(temp);
		try{
			word = URLEncoder.encode(str, "utf-8");

		}catch(Exception e){
			e.printStackTrace();
		}
		//编写阶段可以在每个方法里保存一个sop，以观察运行效果
		//System.out.println(word);
		url = str1 + word + str2 + word + str3 + num +str4;
		//System.out.println(url);

		return url;


	}



	public static String getPageContent(String url){
		 // 打印一下以便找出错误
		System.out.print("getPageContent...   ");
		String begin_url = url;
		String line, result = "";
		try{
			URL realUrl = new URL(begin_url);
			URLConnection conn = realUrl.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while((line=reader.readLine())!=null){
				result += line; 
			}
			


		}catch(Exception e){
			e.printStackTrace();
		}

		return result;

	}


	public static void saveHtml2Txt(String pageContent){
		//java.io.Writer
		FileWriter f = null;
		String path = "Pic.txt";
		try{
			f = new FileWriter(path);
			f.write(pageContent);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	


	//从指定页面获取所有图片地址
	public static String fromPageGetImageUrls(String pageContent){
		// 打印一下以便找错错误
		System.out.print("fromPageGetImageUrls...   ");
		String question0, question1 = "";
		Pattern pattern = Pattern.compile("objURL\":\"(.+?)\",");
		Matcher matcher=pattern.matcher(pageContent);
		Spider.markNumber = 0;
		while (matcher.find()) {
			question0 = matcher.group(0);
			question1 = matcher.group(1);
			str_array[Spider.markNumber] = matcher.group(1);
			Spider.markNumber++;
			
		}
		if (Spider.markNumber<29) {
			System.out.println("单页图片不足30张："+Spider.markNumber);
			
		}


		return null;
	}




	public static String decode(String url){
		String myUrl = "";
		myUrl = url.replace("ippr", "http");
		myUrl = myUrl.replace("_z2C$q", ":");
		myUrl = myUrl.replace("AzdH3F", "/");
		myUrl = myUrl.replace("_z&e3B", "."); 
		myUrl = myUrl.toLowerCase();
		myUrl = myUrl.substring(4);
		char[] arr = myUrl.toCharArray();
		myUrl = "";
		for (char c : arr) {
			switch(c){
				case 'w': myUrl += "a";break;  
				case 'k': myUrl += "b";break;  
				case 'v': myUrl += "c";break;  
				case '1': myUrl += "d";break;  
				case 'j': myUrl += "e";break;  
				case 'u': myUrl += "f";break;  
				case '2': myUrl += "g";break;  
				case 'i': myUrl += "h";break;  
				case 't': myUrl += "i";break;  
				case '3': myUrl += "j";break;  
				case 'h': myUrl += "k";break;  
				case 's': myUrl += "l";break;  
				case '4': myUrl += "m";break;  
				case 'g': myUrl += "n";break;  
				case '5': myUrl += "o";break;  
				case 'r': myUrl += "p";break;  
				case 'q': myUrl += "q";break;  
				case '6': myUrl += "r";break;  
				case 'f': myUrl += "s";break;  
				case 'p': myUrl += "t";break;  
				case '7': myUrl += "u";break;  
				case 'e': myUrl += "v";break;  
				case 'o': myUrl += "w";break;  
				case '8': myUrl += "1";break;  
				case 'd': myUrl += "2";break;  
				case 'n': myUrl += "3";break;  
				case '9': myUrl += "4";break;  
				case 'c': myUrl += "5";break;  
				case 'm': myUrl += "6";break;  
				case '0': myUrl += "7";break;  
				case 'b': myUrl += "8";break;  
				case 'l': myUrl += "9";break;  
				case 'a': myUrl += "0";break;  
				default : myUrl += c;break;  
			}
			
		}



		return "http"+myUrl;
	}



}



