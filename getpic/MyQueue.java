import java.util.LinkedList;
import java.util.Queue;

public class MyQueue{
	public static void main(String[] args) {
		Queue <String> queue = new LinkedList <String>();
		queue.offer("1");
		queue.offer("2");
		queue.offer("3");

		System.out.println("queue.size() "+queue.size());

		// for (String string : queue ) {
		// 	System.out.println("queue.size() "+queue.size());
		// 	System.out.println(string);
			
		// }

		while(queue.size()>0){
			System.out.println("queue.size() "+queue.size());
			System.out.println(queue.remove());

		}

	}
}