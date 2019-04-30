package com.thkmon.lucene;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexManager {

	public static void main(String[] args) {
		IndexManager indexMng = new IndexManager();
		indexMng.doIndex();
	}
	
	
	/**
	 * 색인하기
	 */
	public void doIndex() {
		
		// 색인 결과파일 폴더 지정
		String indexDir = "C:\\index";
		
		// 색인할 txt 파일들이 있는 대상 폴더 지정
		String dataDir = "C:\\개인폴더\\개발작업\\개인사이트제작";
		
		Directory dir = null;
		IndexWriterConfig config = null;
		IndexWriter indexWriter = null;
		
		try {
			dir = FSDirectory.open(new File(indexDir).toPath());
			config = new IndexWriterConfig(new StandardAnalyzer());
			indexWriter = new IndexWriter(dir, config);
			
			File[] fileArr = new File(dataDir).listFiles();
			int fileCount = fileArr.length;
			
			File file = null;
			for (int i=0; i<fileCount; i++) {
				file = fileArr[i];
				
				if (file.isDirectory()) {
					continue;
				}
				
				if (file.isHidden()) {
					continue;
				}
				
				if (!file.exists()) {
					continue;
				}
				
				if (!file.canRead()) {
					continue;
				}
				
				if (file.getName().toLowerCase().endsWith(".txt")) {
					indexFile(indexWriter, file);
				}
			}
			
			System.out.println(indexWriter.numRamDocs());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (indexWriter != null) {
					indexWriter.close();
				}
			} catch (Exception e) {
				indexWriter = null;
			}
			
			try {
				if (dir != null) {
					dir.close();
				}
			} catch (Exception e) {
				dir = null;
			}
		}
	}

	
	public void indexFile(IndexWriter indexWriter, File file) throws Exception {
		Document doc = getDocument(file);
		indexWriter.addDocument(doc);
	}
	
	
	private Document getDocument(File file) throws Exception {
		String fileContent = FileReadUtil.readFileToString(file, "MS949", " ");
		
		Document doc = new Document();
		
		doc.add(new TextField("contents", String.valueOf(fileContent), Field.Store.YES));
		doc.add(new TextField("filename", String.valueOf(file.getName()), Field.Store.YES));
		doc.add(new TextField("fullpath", String.valueOf(file.getCanonicalPath()), Field.Store.YES));
		
		System.out.println("canonicalPath : " + file.getCanonicalPath());
		System.out.println("filename : " + file.getName());
		System.out.println("fileContent : " + fileContent);
		System.out.println("====================");
		
		return doc;
	}
}