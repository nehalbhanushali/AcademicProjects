/**
 * 
 */

 
$(document).ready(function () {
	
});

function checkifAvailable(value){
	var flag = false;
	$.ajax({
		type:"POST",
		url:"jsp/login.jsp",
		async:false,
		data:{ email:value,actionType:"checkAvailability"},
		
		success:function(data,status){
	
			  var str = data;
			
			if(str.trim() == "available"){
			
			flag = true;	
		
			}
		},
		error:function(errorCode){
			
			alert(errorCode);
			
		}
	});
	return flag;
}

function register(){
	
	var name = $('#fullName').val();
	var dob = $('#dob').val();
	var city = $('#cityName').val();
	var emailId = $('#emailSignUp').val();
	var pwd = $('#passwordSignUp').val();
	


	$.ajax({
		type:"POST",
		url:"jsp/login.jsp",
		data:{ fullname: name,birthDate:dob,cityName : city, email:emailId,password:pwd,actionType:"register"},
		
		success:function(data,status){
			//alert(data);
			  var str = data;
			  var dataArray =  str.split(",")
			 var resultType = dataArray[0];
		var message = dataArray[1];
			console.log(resultType);
			if(resultType.trim() == "success"){
				
			alert("Hi "+message+". You have been successfully registered");
				$('#myModal').modal('hide');	
				
			}else{
				alert(message +"\n Try Registering Again");
			}
		},
		error:function(){
			alert("OH, No!");
		}
	});
	
}

function ajaxFunction(cityName)  
{
    
  var value = cityName.value;
    
    
    if(value.length>2){
    	
    	

	var xmlHttp;
   
	try     // Firefox, Opera 8.0+, Safari
	{
		xmlHttp=new XMLHttpRequest();
	}
	catch (e)
	{
		try  // Internet Explorer
		{
			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e)
		{
			try
			{
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e)
			{
				alert("Your browser does not support AJAX!");
				return false;
			}
		}
	}
	
	//2. Call the server-side script

	xmlHttp.open("GET", "jsp/cities.jsp?q=" + value, true); 
	
	xmlHttp.send();   

	
	//3. Check the server-data is ready
	xmlHttp.onreadystatechange=function()
	{
		if(xmlHttp.readyState==4)
		{
			//4. Manipulate the DOM
	
			var cityDiv = $(cityName).parent().find('.populateCityDiv>ul');
			
		
			var serverData = xmlHttp.responseText;
			$(cityDiv).html(serverData);
	
			
			if(cityName.id =="my-cityName" && serverData.trim().length !=0){
				$(cityDiv).css('display','block');	
				
			}
			$(cityDiv).find('li>a').click(function(){
				
				
				var selectedValue = $(this).html();
				$(cityName).val(selectedValue);
				$(cityDiv).html('');
				$(cityDiv).css('display','none');	
			}) ; 
		}
	}
    }
}

function getPlacesToBe(lat,long){
	$.support.cors = "true";
	$.ajax({
		url:"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+long +
				"&radius=500&types=food" +
				"&name=cruise&" +
				"key=AIzaSyCGGnSirW6ClO7V1BMQPHnnAgghxrAUhcc",
		
		type:"GET",
		dataType:"json",
	//	url:"https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyBxdOv7sNA6aD6NeVFxLCuONNUKpvT3DGE",
		
		success:function(data){
		//	alert(JSON.stringify(response));
		
		},
		error:function(){
			alert("OH, No!");
		}
	});
	
	
	
}
function getCurrentAddress(lat, long){
	
	//alert("bmsdbsn");
	
	console.log("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+long+
			"&key=AIzaSyBtI7AN64cEY4OHkRyaPJd_RwiuDe_pA-o");

	$.ajax({
		
	//	url:"http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+long+"&sensor=true",
		url:"https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+long
		+	"&key=AIzaSyBtI7AN64cEY4OHkRyaPJd_RwiuDe_pA-o",
		
		type:"GET",
		dataType:"json",
	//	url:"https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyBxdOv7sNA6aD6NeVFxLCuONNUKpvT3DGE",
		
		success:function(data){
		//	alert(JSON.stringify(data));
		//	alert(data.results[1].formatted_address);

/*var city = data.results[0].address_components[3].long_name;
var state =data.results[0].address_components[5].short_name;*/
var array = data.results[1].formatted_address;
var cityState = array.split(",");

$('#loc').val(cityState[1]+", "+cityState[2]);
		},
		error:function(){
			alert("OH, No!");
		}
	});
	
//	http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+long+"&sensor=true
}