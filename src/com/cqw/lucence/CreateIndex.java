package com.cqw.lucence;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class CreateIndex {
    
    /**
     * 创建索引
     * @throws IOException
     */
    private static void createIndex() throws IOException {
        // 指定analyzer，在查询时必须使用同样的analyzer
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        
        // 指定index存储位置
        File indexDir = new File("indexDir");
        Directory dir = FSDirectory.open(indexDir);
        
        // 创建index
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
        IndexWriter w = new IndexWriter(dir, config);
        addDoc(w, "001", "Lucene in Java", "An introduction of Lucene in Java.");
        addDoc(w, "002", "Lucene in IR", "An introduction od Lucene in IR.");
        addDoc(w, "003", "Hello World", "The first Program Hello World.");
        addDoc(w, "004", "Linux and Windows", "Contrast Linux OS and Windows OS.");	
        w.close(); //必须close()，非常重要，不然数据不会写入到index中
    }
    
    /**
     * 创建Doucment对象，并添加到索引中
     * @param w ： IndexWriter对象
     * @param id：document的ID
     * @param title ：document的title
     * @param content ： document的content
     * @throws IOException
     */
    private static void addDoc(IndexWriter w, String id, String title, String content) throws IOException {
        // 创建document对象
        Document doc = new Document();
        // 文档中的field, StringField中的内容不能用于检索，TextField中的内容才能用于检索
        doc.add(new StringField("id", id, Field.Store.YES));
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        w.addDocument(doc);
    }

    public static void main(String[] args) throws IOException {
        createIndex();
        System.out.println("Create Index Success!!!");
    }
}
