# sample-codes
背景描述：文件夹内代码是2015年实现网站爬虫及内容搜索时所写，为首次完整接触爬虫和搜索引擎时间段，比较有代表意义。
代码功能简介：
	IndexItem索引对象，数据、文件等信息都是转换为该对象后再建立索引；
	Indexer为创建索引的工具类，目的是将IndexItem对象转换为索引；
	DBIndexUtil,FileIndexUtil为对数据库（mysql,mongodb）和文件创建索引提供的工具类；
	Searcher为对索引进行查询；
	TikaUtil为文件内容抽取工具类。
