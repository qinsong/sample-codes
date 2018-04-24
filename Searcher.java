package lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 *
 * 提供对索引的查询功能
 *
 */
public class Searcher {
	  
	private IndexSearcher searcher;
    private QueryParser contentQueryParser;
	 
	@SuppressWarnings("deprecation")
	public Searcher(String indexDir) throws IOException {
	    
        searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(indexDir))));
        IKAnalyzer analyzer = new IKAnalyzer(true);
	 
	    String[] fields = {IndexItem.TITLE,IndexItem.ABSTRACT,IndexItem.CONTENT}; 
	    
	    contentQueryParser = new MultiFieldQueryParser(Version.LUCENE_44, fields, analyzer);
	}
	
	/**
	* 实现分页查询
	* ScoreDoc实例化时需两个参数，两个参数值为list最后一条记录中docid,score对值 的value值
	* @param queryString 被查询的字符串
	* @param pageSize 返回的记录条数
	* @param pageNumber 页数
	*/
	public Map<String,Object> findByPage(String queryString, int pageSize,int pageNumber) throws ParseException, IOException {
	    
		Map<String,Object> reMap = new HashMap<String,Object>();
		
	    Query query = contentQueryParser.parse(queryString);
	    
	    //获取最后一个ScoreDoc
	    ScoreDoc afterDoc = null;
	    if(pageNumber>1){
	    	int num = pageSize * (pageNumber-1);
	    	afterDoc = searcher.search(query,num).scoreDocs[num-1];
	    }
	    
	    TopDocs queryResult = searcher.searchAfter(afterDoc,query, pageSize);    
	    ScoreDoc[] queryResults = queryResult.scoreDocs;
	    
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    
	    for (ScoreDoc scoreDoc : queryResults) {
	    	int docID = scoreDoc.doc;
	    	float score = scoreDoc.score;
	    	
	    	Document document = searcher.doc(docID) ;
	    	
	    	Map<String,Object> map = documentToMap(document);
	    	map.put("docid",docID);
	    	map.put("score",score);
	    	
	    	list.add(map);
		}
	    
	    reMap.put("total",queryResult.totalHits);
	    reMap.put("rows",list);
	    
	    return reMap;
	}
	 
	/**
	 * 将Document转化成Map
	 * @param document
	 * @return
	 */
	private static Map<String,Object> documentToMap(Document document){
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<IndexableField> fields = document.getFields();
		for (IndexableField indexableField : fields) {
			map.put(indexableField.name(), document.get(indexableField.name()));
		}
		
		return map;
	}
	
}
