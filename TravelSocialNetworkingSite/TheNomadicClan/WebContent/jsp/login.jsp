<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>


<%
		
		
		try
		{

			
			
			ArrayList<String> userDetails = new ArrayList<String>();
			
			userDetails.add("nehal@gmail.com,nehal123,Nehal");
			userDetails.add("kareena@gmail.com,kapoor123,Kareena");
			
			String actionType = request.getParameter("actionType");
			
			ArrayList<String> additionalDetails = new ArrayList<String>();
			
			additionalDetails.add("01-13-1991,Boston");
			additionalDetails.add("02-20-1983,Mumbai");
			
			
		
			
			
			
			if(actionType.equals("login")){
				
				
		
			String userName = request.getParameter("name");
			String userPassword = request.getParameter("pwd");
			
			String pswd = "";
			
			if (userName == null)
				throw new RuntimeException("No user name was specified");
				
			userName = userName.trim();
			
			if (userName.equals(""))
				throw new RuntimeException("User name cannot be blank");

			if (userPassword == null)
				throw new RuntimeException("No password was specified");
				
			userPassword = userPassword.trim();
			
			if (userPassword.equals(""))
            throw new RuntimeException("Password cannot be blank");
            
			
			String msg = "";
            // Validate user password
            
            if(userDetails.isEmpty()){
            	msg = "Failure,No Data in database";
            	
            }
            for(String u : userDetails){
            	
            	String[] userD = u.split(","); 
            	
            	String userNameinRec = userD[0];
            	
            	String passwordinRec = userD[1];
            	
            	String nameinRec = userD[2];
            	
            	if(userNameinRec.equals(userName)){
            		
            		if(passwordinRec.equals(userPassword)){
            			msg= "success,"+nameinRec;
            			
            		}else{
            			
            			msg = "failure,Password Incorrect";
            		}
            	break;	
            		
            	}
            	else{
            		
            		msg = "failure,No records found for this email ID";
            	}
            }
          
          
            out.println(msg);
            				
			}else if(actionType.equals("register")){
				
				String fullname=request.getParameter("fullname");
				String birthDate=request.getParameter("birthDate");
			    String cityName = request.getParameter("cityName");
			    String email= request.getParameter("email");
			    String password =request.getParameter("password");
			    
			    
			    userDetails.add(email+","+password+","+fullname);
			    additionalDetails.add(birthDate+","+cityName);
			    
			    
			    
			    out.println("success,"+fullname);
	
			}else if (actionType.equals("checkAvailability")){
				String email=request.getParameter("email");
				
				String msg = "";
				int count = 0;
				String userNameinRec="";
				 for(String u : userDetails){
		            	
		            	String[] userD = u.split(","); 
		            	
		            	userNameinRec = userD[0];
		            
		            	
		            	if(userNameinRec.equals(email)){
		            		count++;
		            		
		            		break;
		            	}
				
				
		     	}
				 
				 if(count==0){
					 
					 msg = "available"; 
				 }else{
					 msg = "unavailable";
				 }
				 
				 out.println(msg);
			}
			
		}
		catch (RuntimeException e)
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("<H3><U>ERROR OCCURRED</U></H3>");
			buffer.append("<PRE>\n");
			buffer.append(e.getMessage());
			buffer.append("\n");
			buffer.append("</PRE>");
			buffer.append("<P>");
			buffer.append("Click ");
			buffer.append("<A HREF='");
			buffer.append("index.html'");
			buffer.append(">here</A>");
			buffer.append(" to try again.");
			
			out.println(buffer.toString());
		}
		catch (Exception e)
		{
         out.println("Could not connect to the users database.<P>");
         out.println("The error message was");
         out.println("<PRE>");
         out.println(e.getMessage());
         out.println("</PRE>");
      }
%>