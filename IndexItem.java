package lucene;

public class IndexItem {

	private String id;
	private String title;
	private String abscontent;
	private String content;
	
	private String srctype;
	private String srcdb;
	private String srctab;
	private String srcid;
	
	private String filepath;
	
	private String adddate;
	
	/**
	 * 索引id
	 */
	public static final String ID = "id";
	/**
	 * 索引标题
	 */
	public static final String TITLE = "title";
	/**
	 * 摘要
	 */
	public static final String ABSTRACT = "abscontent";
	/**
	 * 索引内容
	 */
	public static final String CONTENT = "content";
	/**
	 * 来自文件还是数据库
	 */
	public static final String SRC_TYPE = "srctype";
	/**
	 * 来源的数据库
	 */
	public static final String SRC_DB = "srcdb";
	/**
	 * 来源的所属表
	 */
	public static final String SRC_TAB = "srctab";
	/**
	 * 来源所属表中的哪条记录
	 */
	public static final String SRC_ID = "srcid";
	/**
	 * 文件路径
	 */
	public static final String FILEPATH = "filepath";
	/**
	 * 源记录的生成时间
	 */
	public static final String ADDDATE = "adddate";
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbscontent() {
		return abscontent;
	}

	public void setAbscontent(String abscontent) {
		this.abscontent = abscontent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSrctype() {
		return srctype;
	}

	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}

	public String getSrcdb() {
		return srcdb;
	}

	public void setSrcdb(String srcdb) {
		this.srcdb = srcdb;
	}

	public String getSrctab() {
		return srctab;
	}

	public void setSrctab(String srctab) {
		this.srctab = srctab;
	}

	public String getSrcid() {
		return srcid;
	}

	public void setSrcid(String srcid) {
		this.srcid = srcid;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	
}

