
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;

public class Downloadpic{

	public static void download(String url, String fileDir){
		
		// 需要下载的url
		String photoUrl = url;
		String filePath = fileDir;

		// 调用函数，并进行传参
		String fileName = Spider.word+Count.number+".jpg";
		boolean flag = saveUrlAs(photoUrl,filePath+"/"+fileName);
		System.out.println("成功下载： "+flag);
		if (flag) {
			Count.number++;
			
		}
		System.out.println("");

	}

	public static boolean saveUrlAs(String fileUrl, String savePath){
		try{
			// 将网络资源地址传给url
			URL url = new URL(fileUrl);
			// 此为练习获得网络资源的固定格式用法，
			// 以便后面的in变量获得url截取网络资源的输入流
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			// 此处也可以用BufferedInputStream与BuffereOutputStream
			// 需要保存的路径
			DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
			// 将参数savePath，即将截取的图片的存储在本地的地址赋值给out输出流所指定的地址
			byte [] buffer = new byte[4096];
			int count = 0;
			while((count = in.read(buffer))>0){
				// 将输入流以字节的形式读取并写入buffer中
				out.write(buffer,0,count);
			}
			// 后面三行为关闭输入输出流以及网络资源的固定格式
			out.close();
			in.close();
			connection.disconnect();
			return true;

		}catch(Exception e){
			System.out.println("e: "+e);
			System.out.println("fileUrl: "+fileUrl);
			System.out.println("savePath: "+savePath);
			return false;
		}
	}

	// 创建目录的方法，，这里说的方法是着类中的方法，不是指怎么做
	public static boolean createDir(String DirName){
		File dir = new File(DirName);
		// 判断目录存在性，用的是File类的方法exists()
		if (dir.exists()) {
			System.out.println("创建目录失败，目录已存在。");
			return false;
		}
		// 创建目录和检查目录存在性，用的都是File类下的方法，它们的返回值都是布尔值
		if (dir.mkdirs()) {
			System.out.println("创建目录成功。");
			return true;	
		}else{
			System.out.println("创建目录失败。");
			return false;	

		}
	}
	
}











