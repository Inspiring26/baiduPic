
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloadpic{

	public static void download(String url){
		// 创建实例
		Downloadpic pic = new Downloadpic();
		// 需要下载的url
		String photoUrl = url;
		// 截取最后的／后面的字符串作为图片名
		// 这个截取方式很好，要记住，短的字符串可以直接用substring截取，不一定都要用正则表达式
		String fileName = photoUrl.substring(photoUrl.lastIndexOf("/"));
		// 图片保存路径
		String filePath = "./jimage/";
		// 调用函数，并进行传参
		boolean flag = pic.saveUrlAs(photoUrl,filePath+fileName);
		System.out.println("Run ok!\n Get URL file "+flag);
		System.out.println(filePath);
		System.out.println(fileName);
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
			System.out.println(e+fileUrl+savePath);
			return false;
		}
	}
	
}