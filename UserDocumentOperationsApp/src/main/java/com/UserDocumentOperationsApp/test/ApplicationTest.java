package com.UserDocumentOperationsApp.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.UserDocumentOperationsApp.exceptions.UDOPException;

public class ApplicationTest {
	public static final String fileLocation = "C://Ingenico/Uploaded/test.txt";
	public String userIdForTest = "101";
	
	@Test
	public void createProfileTest() throws UDOPException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet downloadFile = new HttpGet("http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/profile");
		HttpResponse response = null;
		try {
			response = httpClient.execute(downloadFile);
		} catch (IOException e) {
			throw new UDOPException(e.getMessage());
		}
		StatusLine status = response.getStatusLine();
				
		assertEquals("Checking API status code : ", status.getStatusCode(), 200);
	}
	
	@Test
	public void uploadTest() throws UDOPException {
		
		HttpResponse response;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost uploadFile = new HttpPost("http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document/upload");
		StringBody userId = new StringBody(userIdForTest, ContentType.TEXT_PLAIN);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		
		File file = new File(fileLocation);
		try {
			builder.addBinaryBody(
			    "file",
			    new FileInputStream(file),
			    ContentType.MULTIPART_FORM_DATA,
			    file.getName()
			);
		} catch (FileNotFoundException e) {
			throw new UDOPException(e.getMessage());
		}
		builder.addPart("userId", userId);
		uploadFile.setEntity(builder.build());
		try {
			response = httpClient.execute(uploadFile);
		} catch (ClientProtocolException e) {
			throw new UDOPException(e.getMessage());
		} catch (IOException e) {
			throw new UDOPException(e.getMessage());
		}
		StatusLine status = response.getStatusLine();
		
		
		assertEquals("Checking API status code : ", status.getStatusCode(), 200);
	}
	
	@Test
	public void downloadTest() throws UDOPException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet downloadFile = new HttpGet("http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document/retrieve/"+userIdForTest+"_test.txt");
		HttpResponse response = null;
		try {
			response = httpClient.execute(downloadFile);
		} catch (IOException e) {
			throw new UDOPException(e.getMessage());
		}
		StatusLine status = response.getStatusLine();
		
		assertEquals("Checking API status code : ", status.getStatusCode(), 200);
	}
	
	@Test
	public void deleteTest() throws UDOPException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet downloadFile = new HttpGet("http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document/delete/"+userIdForTest+"_test.txt");
		HttpResponse response;
		try {
			response = httpClient.execute(downloadFile);
		} catch (IOException e) {
			throw new UDOPException(e.getMessage());
		}
		StatusLine status = response.getStatusLine();
		
		assertEquals("Checking API status code : ", status.getStatusCode(), 200);
	}
	
}
