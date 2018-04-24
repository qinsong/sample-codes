package lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;

/**
 * 专门用于提取文件内容
 *
 */
public class TikaUtil {
	
	/**
	 * 传入文件，并返回其中内容。若发生异常，返回""
	 * 文件大小现在默认20M以内，后期应做成配置的
	 * @param file 所需抽取的文件
	 * @param filesize 文件的大小
	 * @return
	 */
	public static String getContent(File file) {
		
		
		String reStr = "";
		  
        Parser parser = new AutoDetectParser();  
        InputStream is = null;  
        try {  
            Metadata metadata = new Metadata();  
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());  
            is = new FileInputStream(file);  
            ContentHandler handler = new BodyContentHandler(20*1024*1024);  
            ParseContext context = new ParseContext();  
            context.set(Parser.class,parser);  
              
            parser.parse(is,handler, metadata,context);  
 
            reStr = handler.toString();  
        } catch (Exception e) {  
            e.printStackTrace();
            return reStr;
        } finally {  
            try {  
                if(is!=null) is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return reStr;  
	}
	
}
