This is a Document Operations Service Application created using Technology Stack as 
* Java 17
* Jax-RS - Jersey Implementation
* Junit 4.12
* Maven 

Application Server : Tomcat 9.0
Pre-requisits :  
Create a directory as C://Ingenico/Uploaded 
For Junit Testing create a file in as C://Ingenico/Uploaded/test.txt

Availabe API : 
Create Profile : http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/profile
			URI : Same as above
			Method : GET
			
			Request Eg : GET http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/profile
			Response Eg : Your UserId is : 27
Docunent Operation : http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document 
Functions : 
1) Upload a  document
			URI : /upload
			Method : POST
			Request Body:
			Key                   Value                      					Type
			file				  location of the file to be uploaded			Form Data
			userId				  User Id passed during create profile			Form Data
			
			Request Eg : POST http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document/upload
			Response Eg: File uploaded to : C://Ingenico/Uploaded/27_code.docx
2) Download a document
			URI : /retrieve/{filename}
			Method : GET
			Path variable : Filename (Hint : You can get the file name after you upload the document). 
			
			Request Eg : GET http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document/retrieve/27_code.docx
			Response Eg: You can download the file and save it any any location you want. 
			
3) Delete a document : 
			URI : /delete/{filename}
			Method : GET
			Path variable : Filename (Hint : You can get the file name after you upload the document). 
			
			Request Eg : GET http://localhost:8080/UserDocumentOperationsApp/rest/v1/operations/document/delete/27_code.docx
			Response Eg: File 27_code.docx been from : C://Ingenico/Uploaded
			
			
			
	