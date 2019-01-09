package com.base.util;

import com.base.model.Essay;
import com.base.pojo.LucenePage;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章索引
 */
@Component
public class LuceneUtil {
	// Lucene索引文件路径
    @Value("${lucene.path}")
	private String dir;

    // 定义分词器 false:最细粒度 true:智能分词模式
	static Analyzer analyzer = new IKAnalyzer(true);


    /**
	 * 添加索引
	 * @param term
	 * @param doc
	 */
	public void addDoc(Term term,Document doc) {
		try {
			File file = new File(dir);
			if (!file.exists()) {// 不存在则创建
				file.mkdirs();
			}
			// 索引库的存储目录
			Directory directory = FSDirectory.open(file);
			// 关联当前lucence版本和分值器
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			// 传入目录和分词器
			IndexWriter iwriter = new IndexWriter(directory, config);
			// 若存在,则执行更新,否则添加
			iwriter.updateDocument(term,doc);
			// 提交事务
			iwriter.commit();
			// 关闭流
			iwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除索引
	 * @param term
	 */
	public void deleteDoc(Term term) {
		File file = new File(dir);
		if (!file.exists()) {// 不存在则创建
			file.mkdirs();
		}
	    try {
	    	// 索引库的存储目录
			Directory directory = FSDirectory.open(file);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			// 传入目录和分词器
			IndexWriter iwriter = new IndexWriter(directory, config);
	    	iwriter.deleteDocuments(term);
	    	iwriter.commit();
	    	iwriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * 根据页码和分页大小获取上一页的最后一个scoredocs
	 *
	 * @param pageIndex
	 * @param pageSize
	 * @param query
	 * @param searcher
	 * @return
	 * @throws IOException
	 */
	private static ScoreDoc getLastScoreDoc(int pageIndex, int pageSize, Query query, IndexSearcher searcher)
			throws IOException {
		if (pageIndex <= 1) {// 如果当前是第一页,上一页的元素就返回空
            return null;
        }
		int num = pageSize * (pageIndex - 1);// 获取上一页的总数量
		TopDocs tds = searcher.search(query, num);//得到从第一页到上一页的所有记录
		if(tds.totalHits<=num){//如果总记录数小于等于上一页的总数量
			return null;
		}
		return tds.scoreDocs[num-1];
	}

	/**
	 * 搜索，实现高亮
	 *
	 * @param pageIndex 当前页数
	 * @param pageSize 每页的条数
	 * @param field 搜索的字段
	 * @param value 关键词
	 * @return
	 * @throws Exception
	 */
	public LucenePage search(int pageIndex, int pageSize, String field, String value) throws Exception {
		File file = new File(dir);
		if (!file.exists()) {// 不存在则创建
			file.mkdirs();
		}
        File[] tempList = file.listFiles();
		if(tempList==null||tempList.length==0){
		   throw new RuntimeException();
        }
		// 索引库的存储目录
		Directory directory = FSDirectory.open(file);
		// 读取索引库的存储目录
		DirectoryReader ireader = DirectoryReader.open(directory);
		// 搜索类
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// lucence查询解析器，用于指定查询的属性名和分词器
		QueryParser parser = new QueryParser(Version.LUCENE_47, field, analyzer);
		// 搜索
		Query query = parser.parse(value);
		// 最终被分词后添加的前缀和后缀处理器，默认是粗体<B></B>
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<font color=red>", "</font>");
		// 高亮搜索的词添加到高亮处理器中
		Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
		// 获取上一页的最后一个元素
		ScoreDoc lastSd = getLastScoreDoc(pageIndex, pageSize, query, isearcher);
		// 通过最后一个元素去搜索下一页的元素
		TopDocs tds = isearcher.searchAfter(lastSd, query, pageSize);
		ScoreDoc[] hits = tds.scoreDocs;
		LucenePage lucenePage=new LucenePage();
		List<Essay> list = new ArrayList<Essay>();
		// 遍历，输出
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			Essay essay = new Essay();
			essay.setEssayId(Integer.parseInt(hitDoc.get("essayId")));
			//出现的次数
			String highLightText = highlighter.getBestFragment(new StandardAnalyzer(Version.LUCENE_47), field,  hitDoc.get(field));
			essay.setSearchtimes(highLightText.split("<font color=red>").length-1);
			essay.setContent(highLightText);
			list.add(essay);
		}
		lucenePage.setList(list);//分页集合
		lucenePage.setTotalCount(tds.totalHits);//总记录数
		ireader.close();
		directory.close();
		return lucenePage;
	}
}