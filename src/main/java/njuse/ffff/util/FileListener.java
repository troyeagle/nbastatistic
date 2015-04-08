package njuse.ffff.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Queue;
/**
 * 文件监视器，用于监视文件发生了增加或删除
 * @author Mebleyev.G.Longinus
 *@return 返回事件类型+“;”+事件名称（即文件内容）
 */
public class FileListener {  
	String path;
	public FileListener(String path){
		this.path = path;
				
	}

	public void startNewWatch(Queue<String> eventq) throws IOException, InterruptedException{
		 WatchService watchService=FileSystems.getDefault().newWatchService();
	        Paths.get(path).register(watchService,   
	                StandardWatchEventKinds.ENTRY_CREATE,  
	                StandardWatchEventKinds.ENTRY_DELETE,
	                StandardWatchEventKinds.ENTRY_MODIFY);  
	        while(true)  
	        {  
	            WatchKey key=watchService.take();  
	            for(WatchEvent<?> event:key.pollEvents())  
	            {  
	                System.out.println(event.context()+"发生了"+event.kind()+"事件");  
	                synchronized(this){
	                	 eventq.offer(event.kind().toString()+";"+event.context().toString()) ;
	                }
	               
	            }  
	            if(!key.reset())  
	            {  
	                return;
	            }  
	        }  
	         
	}
 
}
