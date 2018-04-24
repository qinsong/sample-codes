package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 提供对单个IndexItem对象的索引操作
 * 当更改索引目录时，应先调用close()方法对 writer进行关闭
 *
 */
public class Indexer {
	 private static IndexWriter writer;
	 
	 public Indexer(String indexDir) {
		try {
			Directory dir = FSDirectory.open(new File(indexDir));
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, new IKAnalyzer(true));
			
			if(writer==null){
				writer = new IndexWriter(dir,iwc);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    /**
	 * 创建索引，如果已经存在，而更新
	 */
	 public boolean create(IndexItem indexItem){
    	try {
	        writer.deleteDocuments(new Term(IndexItem.ID, indexItem.getId()));
			writer.addDocument(indexItemToDoc(indexItem));
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * 删除索引
	 * @param indexItem
	 * @return
	 */
	 public boolean delete(IndexItem indexItem){
    	try {
			writer.deleteDocuments(new Term(IndexItem.ID, indexItem.getId()));
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
    }
    
    /**
     * 更新索引
     * @param indexItem
     * @return
     */
	 public boolean update(IndexItem indexItem){
    	try {
		    writer.updateDocument(new Term(IndexItem.ID, indexItem.getId()), indexItemToDoc(indexItem));
		    
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
    	
    }
    
	/**
	 * 关闭IndexWriter
     */
	 public void close(){
	    try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 将IndexItem转换为doc
     * doc的字段值不能为null,当为null时，全转为""
     * @param indexItem
     * @return
     */
    @SuppressWarnings("deprecation")
	private Document indexItemToDoc(IndexItem indexItem){
    	Document doc = new Document();
		 
	    doc.add(new Field(IndexItem.ID, indexItem.getId()==null?"":indexItem.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field(IndexItem.TITLE, indexItem.getTitle()==null?"":indexItem.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field(IndexItem.ABSTRACT, indexItem.getAbscontent()==null?"":indexItem.getAbscontent(), Field.Store.YES, Field.Index.ANALYZED));
	    doc.add(new Field(IndexItem.CONTENT, indexItem.getContent()==null?"":indexItem.getContent(), Field.Store.YES, Field.Index.ANALYZED));
	    doc.add(new Field(IndexItem.SRC_TYPE, indexItem.getSrctype()==null?"":indexItem.getSrctype(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	    doc.add(new Field(IndexItem.SRC_DB, indexItem.getSrcdb()==null?"":indexItem.getSrcdb(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	    doc.add(new Field(IndexItem.SRC_TAB, indexItem.getSrctab()==null?"":indexItem.getSrctab(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	    doc.add(new Field(IndexItem.SRC_ID, indexItem.getSrcid()==null?"":indexItem.getSrcid(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	    doc.add(new Field(IndexItem.FILEPATH, indexItem.getFilepath()==null?"":indexItem.getFilepath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	    doc.add(new Field(IndexItem.ADDDATE, indexItem.getAdddate()==null?"":indexItem.getAdddate(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	    
	    return doc;
    }
}
