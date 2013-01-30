package com.hz.yk.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wuzheng.yk
 *         Date: 13-1-21
 *         Time: ����3:38
 */
public class IndexFile {

    private Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);

    private IndexWriter indexWriter;

    @Before
    public void init() {
        try {
            String indexPath = "D://lucene/index";
            Directory directory = FSDirectory.open(new File(indexPath));
            indexWriter=getIndexWriter(directory);
        } catch(Exception e) {
            System.out.println("�������쳣��");
        }
    }

    /**
     * �������txt�ļ�
     * @param dirPath
     * @return
     */
    public List<File> getFileList(String dirPath) {
        File[] files=new File(dirPath).listFiles();
        List<File> fileList=new ArrayList<File>();
        if (files != null) {
            for(File file: files) {
                if(isTxtFile(file.getName())) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    /**
     * ��������
     * @throws Exception
     */
    @Test
    public void  createIndex() throws Exception{
        String dirPath = "D://lucene/test";
        List<File> fileList = getFileList(dirPath);
        Document document;
        for(File file:fileList){
            document = fileToDocument(file);
            indexWriter.addDocument(document);
            System.out.println("filename=="+document.get("filename"));
            indexWriter.commit();
        }
        closeWriter();
    }

    /**
     * �ж��Ƿ���txt�ļ�
     * @param fileName
     * @return
     */
    public boolean isTxtFile(String fileName) {
        return fileName.lastIndexOf(".txt") > 0;
    }

    /**
     * ���ļ�ת����Document����
     * @param file
     * @return
     * @throws Exception
     */
    public Document fileToDocument(File file) throws Exception {
        Document document=new Document();
        document.add(new TextField("filename", file.getName(), Field.Store.YES));
        document.add(new TextField("content", getFileContent(file), Field.Store.YES));
        document.add(new LongField("size", file.getTotalSpace(), Field.Store.YES));
        return document;
    }

    /**
     * ���indexwriter����
     * @param dir
     * @return
     * @throws Exception
     */
    public IndexWriter getIndexWriter(Directory dir) throws Exception {
        IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_40, analyzer);
        return new IndexWriter(dir, iwc);
    }

    /**
     * �ر�indexwriter����
     * @throws Exception
     */
    public void closeWriter() throws Exception {
        if(indexWriter != null) {
            indexWriter.close();
        }
    }

    /**
     * ��ȡ�ļ�����
     * @param file
     * @return
     * @throws Exception
     */
    public String getFileContent(File file) throws Exception{
        Reader reader = new InputStreamReader(new FileInputStream(file),"GBK");
        BufferedReader br = new BufferedReader(reader);
        String result ="";
        while(br.readLine() != null){
            result = result+"\n"+br.readLine();
        }
        br.close();
        reader.close();
        return result;
    }


}
