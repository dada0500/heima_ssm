package com.itheima.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class LuceneFirst {
    @Test
    public void testLucene() throws IOException {
        // 1. 创建Directory对象,指定索引库的保存位置
        Directory directory = FSDirectory.open (new File("E:\\code\\lucene\\index").toPath ());
        // 2. 基于Directory对象创建IndexWriter对象
        IndexWriter indexWriter = new IndexWriter (directory,new IndexWriterConfig ());
        // 3. 读取磁盘上的文件，每个文件对应创建一个Document文档对象
        File dir = new File ("E:\\code\\lucene\\searchsource");
        for (File file : dir.listFiles ()) {
            // 文件名
            String fileName = file.getName ();
            // 文件路径
            String filePath = file.getPath ();
            // 文件内容
            String fileContent = FileUtils.readFileToString (file, "utf-8");
            // 文件大小
            long fileSize = FileUtils.sizeOf (file);

            // 创建Field
            // 参数1： 域的名称  参数2： 域的内容  参数3：是否存储
            TextField fieldName = new TextField ("name", fileName, Field.Store.YES);
            TextField fieldPath = new TextField ("path", filePath, Field.Store.YES);
            TextField fieldContent = new TextField ("content", fileContent, Field.Store.YES);
            TextField fieldSize = new TextField ("size", fileSize + "", Field.Store.YES);

            // 4. 向文档对象中添加域
            Document document = new Document ();
            document.add (fieldName);
            document.add (fieldPath);
            document.add (fieldContent);
            document.add (fieldSize);

            // 5. 把文档对象写入索引库
            indexWriter.addDocument (document);
        }

        // 6. 关闭IndexWriter
        indexWriter.close ();
        directory.close ();

    }
}
