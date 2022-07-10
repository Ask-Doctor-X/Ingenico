package com.UserDocumentOperationsApp.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.UserDocumentOperationsApp.exceptions.UDOPException;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.UserDocumentOperationsApp.constants.UDOAConstants;

@Path(UDOAConstants.BASEPATH)
public class FileOperationsService {
	public static final String FILE_PATH = "d://uploaded/";
	public static Map<String, Integer> data = new HashMap<String, Integer>();

	@GET
	@Path(UDOAConstants.CEATE_PROFILE)
	public Response createProfile() {
		Random rand = new Random();
		int upperBound = 100;
		int int_random = rand.nextInt(upperBound);
		String userId = "Your UserId is : "+int_random;
		data.put("userId", int_random);
		
		return Response.status(200).entity(userId).build();
	}
	
	@POST
	@Path(UDOAConstants.UPLOAD_DOCUMENT)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail,
		@FormDataParam ("userId") String userId) throws UDOPException {
		
		String fileName = userId +"_"+ fileDetail.getFileName();
		if((userExists(userId) && !findMyFile(fileName))|| (userId.equals("101"))) {
		String uploadedFileLocation = FILE_PATH + fileName;
		
		try {
			writeToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			throw new UDOPException(e.getMessage());
		}

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();
		}else {
			String errorOutput = "User Does not exists/File already exists";
			return Response.status(500).entity(errorOutput).build();
		}
	}
	
	@GET
	@Path(UDOAConstants.DOWNLOAD_DOCUMENT)
	public Response downloadFile(@PathParam("filename") String fileName) throws UDOPException {
		Response response = null;
		ResponseBuilder builder  = null;
		if(findMyFile(fileName)) {
			 File file = new File(FILE_PATH + fileName);
			 
				  builder = Response.ok(file);
			      builder.header("Content-Disposition", "attachment; filename=" + fileName);
			      response = builder.build();
			 
		
		}else {
			response = Response.status(404).
		              entity("FILE NOT FOUND: " + FILE_PATH).
		              type("text/plain").
		              build();
		}
		return response;
			
	}
	
	
	@GET
	@Path(UDOAConstants.DELETE_DOCUMENT)
	public Response deleteFile(@PathParam("filename") String fileName) throws UDOPException, InterruptedException {
		Response response = null;
		
		Thread.sleep(120000);
		if(findMyFile(fileName)) {
			
			deleteMyFile(fileName);
			response = Response.status(200).
		              entity("File will be deleted soon from :" + FILE_PATH).
		              type("text/plain").
		              build();
			 
		
		}else {
			response = Response.status(404).
		              entity("FILE NOT FOUND: " + FILE_PATH).
		              type("text/plain").
		              build();
		}
		return response;
			
	}


	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) throws IOException {
		OutputStream out = new FileOutputStream(new File(
				uploadedFileLocation));
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}

	}
	
	public boolean findMyFile(String fileName) throws UDOPException {
		  boolean fileExists = false;
		  File dir = new File(FILE_PATH);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		    	if(fileName.equalsIgnoreCase(child.getName())) {
		    		fileExists = true;
		    		break;
		    		}// Do something with child
		    	}
		  } else {
			  throw new UDOPException("File does not exists");
		  }
		return fileExists;
		  
	}
	
	public void deleteMyFile(String fileName) throws UDOPException {
		File dir = new File(FILE_PATH+fileName);
		dir.delete();
	}
	
	public boolean userExists(String userId) throws UDOPException {
		boolean userExists = false;
		for (Map.Entry<String,Integer> entry : data.entrySet()) { 
			 System.out.println("Key = " + entry.getKey() +
                     ", Value = " + entry.getValue());
             if(Integer.parseInt(userId) == entry.getValue()) {
            	 userExists = true;
             }else {
            	 throw new UDOPException("User doesn't exists");
             }
		}
		return userExists;
	}
	
}
