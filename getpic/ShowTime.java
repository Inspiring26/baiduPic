import java.util.Date;
import java.text.SimpleDateFormat;

public class ShowTime{

	static void showTime(){
		//设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// new Date()为获取当前系统时间
		System.out.println(df.format(new Date()));

	}

}