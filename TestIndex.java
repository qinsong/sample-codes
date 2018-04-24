package lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import system.youzhi.bean.User;


public class TestIndex {
	
	@Test
	public void testClass(){
		User user = new User();
		Object s = user;
		System.out.println(s.getClass().getName());
	}
	
	@Test
	public void testCreateIndex(){
		
		DBIndexUtil dbu = new DBIndexUtil();
		
		dbu.indexMysqlNewsFocus("D:/lucene");
		dbu.indexMongoDB("D:/lucene");
		
		Indexer indexer = new Indexer("D:/lucene");
		FileIndexUtil fiu = new FileIndexUtil(indexer);
		fiu.createIndex("D:\\Apache\\uploadStuff\\crawldata\\file");
		
		indexer.close();
		
	}
	
	@Test 
	public void testQueryIndexPage(){
		try {
			Searcher searcher = new Searcher("D:/lucene");
//			List<Map<String,Object>> list =searcher.findByPage("专利", 10 , 2);
//			for (Map<String, Object> map : list) {
//				System.out.println("docid~~~"+map.get("docid").toString()+"~~~~socre:"+map.get("score"));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testAnalyzer(){
		String str = "公路局正在治理解放大道路面积水问题 欢迎光临test how tried given tests fucking are you adfdfds的博客。";  
//		List<String> lists = getWords(str, new StandardAnalyzer(Version.LUCENE_44));  
		List<String> lists = getWords(str, new IKAnalyzer(true)); 
		for (String s : lists) {  
		    System.out.println(s);  
		}  
	}
	
	public List<String> getWords(String str,Analyzer analyzer){  
	    List<String> result = new ArrayList<String>();  
	    TokenStream stream = null;  
	    try {  
	        stream = analyzer.tokenStream("content", new StringReader(str));  
	        CharTermAttribute attr = stream.addAttribute(CharTermAttribute.class);  
	        stream.reset();  
	        while(stream.incrementToken()){  
	            result.add(attr.toString());  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(stream != null){  
	            try {  
	                stream.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    return result;  
	}  

}
