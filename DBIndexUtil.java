package lucene;

import java.util.List;
import java.util.Map;

import system.util.ProjectPath;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import edu.uci.ics.crawler4j.examples.custom.DBUtil;

/**
 * @ TODO 为避免数据量过大，需进行分页处理;
 * 专门用于处理数据库中类的索引
 * 由于数据库中的表与IndexItem的对应关系不一致，需要统一对每张索引的表进行手动对应
 *
 */
public class DBIndexUtil {
	
	/**
	 * 根据主健生成单条记录的索引,随后关闭IndexWriter录
	 * @param indexDir 索引目录
	 * @param id 主键id
	 * @return
	 */
	public boolean indexMysqlNewsFocusById(String indexDir, String id) {
		boolean flag = false;
		String sql = "select * from news_focus where id="+id;
		List<Map<String,Object>> list = DBUtil.queryFromMySql(sql);
		
		for (Map<String, Object> map : list) {
			IndexItem indexItem = changeNewsFocusMapToIndexItem(map);
			flag = index(indexDir,indexItem);
		}
		
		Indexer indexer = new Indexer(ProjectPath.getIndexPath());
		indexer.close();
		
		return flag;
	}
	
	/**
	 * 对mysql-cy-news_focus建立索引，随后关闭IndexWriter
	 * @param indexDir
	 * @return
	 */
	public boolean indexMysqlNewsFocus(String indexDir){
		boolean flag = false;
		String sql = "select * from news_focus where isdelete='0'";
		List<Map<String,Object>> list = DBUtil.queryFromMySql(sql);
		
		for (Map<String, Object> map : list) {
			IndexItem indexItem = changeNewsFocusMapToIndexItem(map);
			flag = index(indexDir,indexItem);
		}
		
		Indexer indexer = new Indexer(ProjectPath.getIndexPath());
		indexer.close();
		
		return flag;
	}
	
	/**
	 * 对mongodb-nutch-webpage建立索引，随后关闭IndexWriter
	 * @param indexDir 索引目录
	 * @return
	 */
	public boolean indexMongoDB(String indexDir){
		boolean flag = false;
		DBCollection dbc = DBUtil.getDBCollection("nutch", "webpage");
		DBCursor cursor = dbc.find(new BasicDBObject("contenttype","text/html"));
		while(cursor.hasNext()){
			DBObject dbo = cursor.next();
			Map map = dbo.toMap();
			IndexItem indexItem = changeWebPageMapToIndexItem(map);
			flag = index(indexDir,indexItem);
		}
		
		Indexer indexer = new Indexer(ProjectPath.getIndexPath());
		indexer.close();
		return flag;
	}
	
	private boolean index(String indexDir,IndexItem indexItem ){
		Indexer indexer = new Indexer(indexDir);
		return indexer.create(indexItem);
	}
	
	private IndexItem changeNewsFocusMapToIndexItem(Map<String,Object> map){
		IndexItem indexItem = new IndexItem();
		
		indexItem.setAbscontent(returnStr(map.get("introduce")));
		indexItem.setAdddate(returnStr(map.get("adddate")));
		indexItem.setContent(returnStr(map.get("content")));
		indexItem.setFilepath("");
		indexItem.setId("mysql_newsfocus_"+returnStr(map.get("id")));
		indexItem.setSrcdb("cy");
		indexItem.setSrcid(returnStr(map.get("id")));
		indexItem.setSrctab("news_focus");
		indexItem.setSrctype("mysql");
		indexItem.setTitle(returnStr(map.get("name")));
		
		return indexItem;
	}
	
	private IndexItem changeWebPageMapToIndexItem(Map map){
		
		IndexItem indexItem = new IndexItem();
		
		indexItem.setAbscontent(null);
		indexItem.setAdddate(returnStr(map.get("addtime")));
		indexItem.setContent(returnStr(map.get("text")));
		indexItem.setFilepath(null);
		indexItem.setId("mongodb_nutch_"+returnStr(map.get("url")));
		indexItem.setSrcdb("nutch");
		indexItem.setSrcid(returnStr(map.get("_id")));
		indexItem.setSrctab("webpage");
		indexItem.setSrctype("mongodb");
		indexItem.setTitle(returnStr(map.get("anchor")));
		
		return indexItem;
	}
	
	private String returnStr(Object obj){
		return obj!=null?obj.toString():"";
	}

}
