import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.URL;  
import java.net.URLConnection;  
import java.net.HttpURLConnection;  

  
  
public class DownloadImage3 {  
  
    /** 
     * @param args 
     * @throws Exception  
     */  
     
      
    public static boolean download(String urlString, String filename)  {  
        try{
            // 构造URL  
            // 在这里是创建一个URL class的实例
            URL url = new URL(urlString); 


            // 打开连接 
            // 在你能够访问这个URL上的资源和内容之前，你必须要打开到这些资源与内容上的连接。 
            // 可以通过使用openConnection来完成这一操作。
            // openConnection并不需要参数，并且在操作成功之后，
            // 它会返回一个URLConnection class的实例。
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            System.out.println("urlConnection "+urlConnection.getContentLength()); 
            // 经过对比分析，这个返回的大小全是图片的大小，就是说返回的全是图片的内容
            // 用if判断，丢弃小于11k的，小于11k一般是获取有问题的 
            if (urlConnection.getContentLength()<11000) {
              return false;
              
            }

            System.out.println("urlConnection.getResponseCode() "+urlConnection.getResponseCode());
            if (200 == urlConnection.getResponseCode()) {
              System.out.println("执行urlConnection.getInputStream()");
              InputStream is =urlConnection.getInputStream(); 
            
              System.out.println("writing");    
              // 1K的数据缓冲  
              byte[] bs = new byte[1024];  
              // 读取到的数据长度  
              int len;  
              // 输出的文件流  
             // File sf=new File(savePath);  
             // if(!sf.exists()){  
             //     sf.mkdirs();  
             // }  
             OutputStream os = new FileOutputStream(filename);  
              // 开始读取  
              while ((len = is.read(bs)) != -1) {  
                os.write(bs, 0, len);  
              }  
              // 完毕，关闭所有链接  
              os.close();  
              is.close();  
              return true;
                    }        
            return false;

        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }   
  
} 