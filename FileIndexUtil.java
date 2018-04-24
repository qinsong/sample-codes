package lucene;

import java.io.File;
import java.util.Date;

import system.util.DateTimeUtil;

/**
 * 提供根据文件创建索引的通用方法，其中将文件转换成IndexItem时，将文件的绝对路径作为id
 *
 */
public class FileIndexUtil {

	public static final String FILE = "file";
	
	private Indexer indexer;
	
	public FileIndexUtil(Indexer indexer){
		this.indexer = indexer;
	}
	
	/**
	 * 根据路径创建索引，包括文件夹或文件
	 * @param path
	 * @return
	 */
	public boolean createIndex(String path){
		boolean flag = false;
		
		File file = new File(path);
		
		if(file.isDirectory()){
			flag = createIndexForDirectory(file);
		}
		
		if(file.isFile()){
			flag = createIndexForFile(file);
		}
		
		indexer.close();
		
		return flag;
	}
	
	/**
	 * 根据路径删除索引
	 * @param path
	 */
	public boolean deleteIndex(String path){
		boolean flag = false;
		
		File file = new File(path);
		
		if(file.isDirectory()){
			flag = deleteIndexForDirectory(file);
		}
		
		if(file.isFile()){
			flag = deleteIndexForFile(file);
		}
		
		indexer.close();
		
		return flag;
	}
	
	/**
	 * 对目录级别进行更新
	 * @param orginPath
	 * @param currentPath
	 * @return
	 */
	public boolean updateIndex(String orginPath , String currentPath ){
		boolean flag = false;
		
		flag = deleteIndex(orginPath);
		flag = createIndex(currentPath);
		
		return flag;
	}
	
	/**
	 * 对目录创建索引
	 * @param ir
	 * @return
	 */
	private boolean createIndexForDirectory(File file){
		boolean flag = false;
		File[] files = file.listFiles();
		for (File f : files) {
			if(f.isDirectory()){
				flag = createIndexForDirectory(f);
			}else{
				flag = createIndexForFile(f);
			}
		}
		return flag;
	}
	
	/**
	 * 对单个文件创建索引
	 * @param filePath
	 * @return
	 */
	private boolean createIndexForFile(File file){
		IndexItem indexItem = fileToIndexItem(file);
		return indexer.create(indexItem);
	}
	
	/**
	 * 根据目录删除索引
	 * @param file
	 * @return
	 */
	private boolean deleteIndexForDirectory(File file){
		boolean flag = false;
		File[] files = file.listFiles();
		for (File f : files) {
			if(f.isDirectory()){
				flag = deleteIndexForDirectory(f);
			}else{
				flag = deleteIndexForFile(f);
			}
		}
		return flag;
	}
	
	/**
	 * 根据文件删除索引
	 */
	private boolean deleteIndexForFile(File file){
		return indexer.delete(fileToIndexItem(file));
	}
	
	/**
	 * 将文件信息转换成IndexItem
	 * @param file
	 * @return
	 */
	private IndexItem fileToIndexItem(File file){
		
		IndexItem item = new IndexItem();
		
		item.setId(file.getAbsolutePath());
		item.setAbscontent(null);
		item.setTitle(file.getName());
		item.setContent(TikaUtil.getContent(file));
		item.setFilepath(file.getAbsolutePath());
		item.setSrctype(FILE);
		item.setSrcdb(null);
		item.setSrctab(null);
		item.setSrcid(null);
		item.setAdddate(DateTimeUtil.getDateTime(new Date(file.lastModified())));
		
		return item;
	}
	
}
