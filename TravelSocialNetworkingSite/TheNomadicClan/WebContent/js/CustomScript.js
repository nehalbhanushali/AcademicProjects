/**
 * 
 */

var selectedGroup ="";

var groups ={"group1":true,"group2":false,"group3":false,"group4":false,"group5":true,"group6":false};

var buddies ={"traveller0":true,"traveller1":true,
		"traveller2":true,"traveller3":false,"traveller4":false,"traveller5":false,
		"traveller6":false,"traveller7":false,"traveller8":false,"traveller9":false};

var user = {};



$(document).ready(function () {
	getLocation();	
//alert($(window).width());
	//alert(user.group[0]);
	
	  $('[data-toggle="tooltip"]').tooltip(); 	
scrollListener();
	
	var isLoggedIn = localStorage.getItem("isloggedIn");
	if(isLoggedIn == "true"){
		
		var message = localStorage.getItem("name");
		
		


		user.name = localStorage.getItem("name");
		user.group = groups;
		user.buddies = buddies;
		
		$("li#login").css("display","none");
		$("li#notifications").css("display","inline-block");
		$("li#userMenu>a").html("Hi,"+message);
		$("#my-profile-name").html(message);
		$("li#userMenu").css("display","inline-block");
	//	$(".post-login").removeClass('hide');
		$('#myModal').modal('hide');
		
		
		
		
		var profileImg = "<img src = 'img/"+message+".jpg' class= 'img-thumbnail' alt='profile-pic'/>";
		$("#my-profile-photo").html(profileImg);
		var div = "";
		var x = document.URL;
		var parts = x.split("#");
		if(parts.length==2){
			div =parts[1];
			showMyDiv(div);
		}
	
		//login();
	}
	else{
		logout();
	}
	$(".my-left-menu a").click(function(){
		
		hideUserMenuDivs();
	});
	
	$("#userMenu>ul a").click(function(){
		
		var target = $(this).attr('href');
		var div = target.substring(1, target.length );
	
	//	alert(".content div:not('#"+div+"')");
		showMyDiv(div);
		
		   
	});
	
	
//$('input#emailSignIn').val('nehal@gmail.com');
	
//	alert($('input#emailSignIn').val());
	
//login();//to-do temp
     $("a#my-header-searchIcon-a").click(function(){
    	 
    	 
	  $("input#my-header-searchBox").toggle();
    });
//validator rules 
  
  $("#form1").validate({
	  errorElement:'div',

		//specify the validation rules
		rules:{
			emailSignIn:{
				required:true,
				laxEmail:true
			},
			password:"required",

		},

		//specify the error messages
		messages:{
			password:{
				required: "Please enter your password",
			},
			emailSignIn:{
				required: "Please enter your Email ID",
			}

		},
		submitHandler: function(form) {
		   // $(form).ajaxSubmit();
			login();
			//alert("hgvn");
		  }

	});
  
  $("#form2").validate({
	  errorElement:'div',

		//specify the validation rules
		rules:{
			fullName:"required",
			passwordSignUp:"required",
			confirmPassword:{
				required:true,
				confirmPasswordCheck:true
			},
			emailSignUp:{
				notExisting:true,
				required:true,
				laxEmail:true
			},
			
			city:"required",

		},

		//specify the error messages
		messages:{
			fullName:{
				required: "Please enter your Full Name",
			},
			
			passwordSignUp:{
				required: "Please enter a password",
			},
			
			confirmPassword:{
				required:"Please confirm your password",
				
			},
			emailSignUp:{
				required: "Please enter your Email ID",
			},
			city:{
				required: "Please choose a city",
			},
			
			laxEmail:"Please enter an Email ID",
			fullName:"Please enter your Name",
			passwordSignUp:"Please enter a password",
			
			laxEmail:"Please enter a valid email address"
		},
		submitHandler: function(form) {
		   register(); // #### e.preventdefault
		    
		  }

	});
  
  jQuery.validator.addMethod("laxEmail", function(value, element) {
	  // allow any non-whitespace characters as the host part
	  return this.optional( element ) || /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test( value );
	}, 'Please enter a valid email address.');
  
  jQuery.validator.addMethod("confirmPasswordCheck", function(value, element) {
	  // allow any non-whitespace characters as the host part
	  
	  var password = $('#passwordSignUp').val();
	  
	  return this.optional( element ) || (value == password) ;
	}, 'Passwords do not match');
  
  jQuery.validator.addMethod("notExisting", function(value, element) {
	  // allow any non-whitespace characters as the host part
	
	  return this.optional( element ) || checkifAvailable(value) ;
	}, 'Account for this Email ID exists.');

  

	  
	  $('#passwordSignUp').strengthMeter('text', {
	      container: $('#password-hierarchy-text'),
	      hierarchy: {
	          '1': ['text-danger', 'Think about a stronger password!'],
	          '5': ['text-warning', 'Weak'],
	          '10': ['text-warning', 'Strong'],
	          '15': ['text-success', 'Very Strong']
	      }
	  });
	  
	  var groupCount =1;
	  $("#travel-groups .group>div").each(function(){
		  var imageURL = "img/group"+groupCount+".jpg";
		
		
		  $(this).css("background-image","url('"+imageURL+"')");
		  groupCount++;
	  });


$('a#logoutButton').click(function(){
	
	logout();
	
});



// profile



$("#my-file").change(function(){
	
	
	
	var fReader = new FileReader();
	fReader.readAsDataURL(this.files[0]);
	fReader.onloadend = function(event){
		//event.target.result
		var fileName = $("#my-file").val();
		//alert(fileName);
		if (!(/\.(gif|jpg|jpeg|tiff|png)$/i).test(fileName)) {              
		    alert('You must select an image file only');               
		    }
		
		else{
			
			var profileImg = "<img src = '"+event.target.result+"' class= 'img-thumbnail' alt='profile-pic'/>";
			//	alert(profileImg);
				$("#my-profile-photo").html(profileImg);	
		}
		
	}
	
/*	var fileName = $("#my-file").val();
	
	alert(fileName);
	if (!(/\.(gif|jpg|jpeg|tiff|png)$/i).test(fileName)) {              
	    alert('You must select an image file only');               
	    }
	
	else{
		
		var profileImg = "<img src = '"+fileName+"' class= 'img-thumbnail' alt='profile-pic'/>";
		//	alert(profileImg);
			$("#my-profile-photo").html(profileImg);	
	}*/
});

$("#my-header-searchBox").keypress(function(e) {
    if(e.which == 13) {
    	window.location='http://www.google.com/search?q='+this.value;
    }
});



//scroller

$("a#myTopScroller").click(function() {
  $("html, body").animate({ scrollTop: 0 }, "slow");
  return false;
});
// notifications

$("#checkboxnumone").click(function(){
	

	$(".checkone").prop("checked",$(this).prop("checked"));
	var i = $(this).parent().parent().find('i').removeClass('hide');
	//alert(i);
	
	
	$("p:lt(3)").html("true");
	
	
	if(!$(this).is(":checked")){
		$(this).parent().parent().find('i').addClass('hide');
		$("p:lt(3)").html("false");
	}	
});



$("#travel-groups .group-item").click(function(){
	
	var imgName = $(this).parent().attr('id');
	selectedGroup = imgName;
	
	var url ="img/"+imgName+".jpg";
	
	var info =$(this).find("h4 a").text();
	
	
	$("#myGroupModal .modal-body h3").html(info);
	
if(user.group[selectedGroup]){
		
	$("#myGroupModal .modal-body p").html('You are a member');
	
	$("#myGroupModal .leave").removeClass('hide');
	$("#myGroupModal .join").addClass('hide');
	
	}else{
		$("#myGroupModal .modal-body p").html('Join now');
		$("#myGroupModal .join").removeClass('hide');
		$("#myGroupModal .leave").addClass('hide');
		
	}
	
	$("#myGroupModal .modal-header").css("background-image","url('"+url+"')");
});

$("#travel-groups .group>div").each(function(){
	

	
if(user.group[this.id]){
	$("#travel-groups #"+this.id+" i").removeClass('hide');	
	//	alert("You are already a member");
	}else{
		
		//alert("#travel-groups ."+selectedGroup);
		
	}
	
});


$("#myGroupModal .modal-footer .join").click(function(){

	
	if(user.group[selectedGroup]){
		
		//alert("You are already a member");
	}else{
		user.group[selectedGroup]=true;
		//alert("You are now a member");
		//alert("#travel-groups ."+selectedGroup);
		$("#travel-groups #"+selectedGroup+" i").removeClass('hide');
	}

});

$("#myGroupModal .modal-footer .leave").click(function(){

	
	
		user.group[selectedGroup]=false;
		//alert("You are now a member");
		//alert("#travel-groups ."+selectedGroup);
		$("#travel-groups #"+selectedGroup+" i").addClass('hide');
	

});







/*
$('.mark-as-read').click(function(){
	
	//int messagesUnread =getUnreadMessages();
	//alert(messagesUnread);
	
	$(".checkone").each(function(){
		if($(this).is(":checked")){
			
		
		$(this).parent().parent().find('td:nth-child(2)').css("font-weight","lighter");
		}
	});
	
});

$('.delete-selected').click(function(){
	var count = 0;
	$(".checkone").each(function(){
		if($(this).is(":checked")){
			count++;
		
		var tr =$(this).parent().parent().remove();
	
	
		}
	});
	var tableLength = $('#myNotificationsModal table tr').length;
	//alert(table);
	
	if(tableLength ==1){
		
		$(".no-notifications").removeClass('hide');
	}
	$(this).parent().parent().find('i').addClass('hide');
});
*/
$("#myNotificationsModal table td:nth-child(2)").click(function(){
	
//	$(this).css('font-weight','lighter');
	
})


for(var i = 0; i<9; i++ ){
	
	
	if(user.buddies["traveller"+i]){
		var buddy = "<li id = 'traveller"+i+"'> <img class ='img-thumbnail' src = 'img/traveller"+i+".jpg' alt='buddy' /><li>";
		
		$(".list-of-budies").append(buddy);	
		//	alert("You are already a member");
		}else{
			
			//alert("#travel-groups ."+selectedGroup);
			
		}
	
	
	
	
}




for(var i = 0; i<9; i++ ){
	
	var travellers =  "<li id = 'traveller"+i+"' data-toggle='tooltip' title='Add Friend'> <span><img class ='img-thumbnail'  src = 'img/traveller"+i+".jpg' alt='buddy' /></span><li>";
	
	$(".list-of-travellers").append(travellers);
}

$(".list-of-travellers li").each(function(){
	

	
	if(user.buddies[this.id]){
		$(this).css("opacity","0.5");	
		$(this).attr("title","Already a buddy");
		
		}else{
			
			//alert("#travel-groups ."+selectedGroup);
			
		}
		
	});


$(".list-of-travellers li").click(function(){
	
	if(user.buddies[this.id]){
			
		//	alert("You are already a member");
		}else{
			user.buddies[this.id] = true;
			
			$(this).css("opacity","0.5");	
			
		}
	
	$(".list-of-budies li").remove();
	for(var i = 0; i<9; i++ ){
		
		
		if(user.buddies["traveller"+i]){
			var buddy = "<li id = 'traveller"+i+"'> <img class ='img-thumbnail' src = 'img/traveller"+i+".jpg' alt='buddy' /><li>";
			
			$(".list-of-budies").append(buddy);	
			//	alert("You are already a member");
			}else{
				
				//alert("#travel-groups ."+selectedGroup);
				
			}
		
		
	}
	
});




$(".checkone").click(function(){
	
	$("#result"+$(this).prop("id")).html($(this).prop("checked"));
	if(!$(this).is(":checked")){
		$("#result"+$(this).prop("id")).html("false");
	}
	var count = 0;
	$(".checkone").each(function(){
		
		if($(this).is(":checked")){
			
		count++;
		
		}
	});
	//alert(count);
	if(count>0){
		
		$(this).parent().parent().parent().find('i').removeClass('hide');
	}else{
		$(this).parent().parent().parent().find('i').addClass('hide');
	}			
});



  
});
function login(){
	
	
	
	
	/*var loginDetails = {};
	
	loginDetails.userID = $('#emailSignIn').val();
	loginDetails.userPassword = $('#password').val();
	
var sendDetails = JSON.stringify(loginDetails)	;*/
	
	
	var uName = $('#emailSignIn').val();
	var uPwd = $('#password').val();
	

//alert('login details'+sendDetails);

	$.ajax({
		type:"POST",
		url:"jsp/login.jsp",
		data:{ name: uName,pwd:uPwd,actionType:"login"},
		
		success:function(data,status){
			//alert(data);
			  var str = data;
			  var dataArray =  str.split(",")
			 var resultType = dataArray[0];
		var message = dataArray[1];
			console.log(resultType);
			if(resultType.trim() == "success"){
				
				hideUserMenuDivs();
				
				window.location.href ="#home";// to do ### think about this
				
				user.name = localStorage.getItem("name");
				user.group = groups;
				user.buddies = buddies;
				
				$("li#login").css("display","none");
				$("li#notifications").css("display","inline-block");
				$("li#userMenu>a").html("Hi,"+message);
				$("#my-profile-name").html(message);
				$("li#userMenu").css("display","inline-block");
				$('#myModal').modal('hide');
				//$(".post-login").removeClass('hide');
				localStorage.setItem("isloggedIn", "true");
				
				var profileImg = "<img src = 'img/"+message+".jpg' class= 'img-thumbnail' alt='profile-pic'/>";
			//	alert(profileImg);
				$("#my-profile-photo").html(profileImg);
				localStorage.setItem("name", message);
				
				
				
			}else{
				localStorage.setItem("isloggedIn", "false");
				alert(message +"\n Try Again");
			}
		},
		error:function(){
			alert("OH, No!");
		}
	});
	
	
	
}

function logout(){
	//alert()
hideUserMenuDivs();
	localStorage.setItem("isloggedIn","false");
	$("li#login").css("display","inline-block");
	$("li#userMenu").css("display","none");
	$("li#notifications").css("display","none");
	
}
function hideUserMenuDivs(){
	
	$(".content>div:not('.post-login')").removeClass('hide');	
	$(".content>div.post-login").addClass('hide');
}

function getLocation() {
	//alert('getLocation');
    if (navigator.geolocation) {
    	
        navigator.geolocation.getCurrentPosition(showPosition);
    } else { 
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}

function showPosition(position) {

    
    
    getCurrentAddress(position.coords.latitude,position.coords.longitude);
    
   // getPlacesToBe(position.coords.latitude,position.coords.longitude);
}

var scrollListener = function () {
//alert("reached end");
	var x = document.URL;
	//alert(x);
	var parts = x.split("#");
	if(parts.length==2){
		div =parts[1];
	//	alert("vvnv "+div);
		if(div=="profile"){
			
			
			var $tabs = $('#profile-menu-tabs .nav-tabs .active').text();
	
			
			if($tabs =="Trips"){
				
	//	alert($tabs);	
			
executeScrollIfinBounds();
$(window).one("scroll", function () { //unbinds itself every time it fires
	
			executeScrollIfinBounds();
			  setTimeout(scrollListener, 200); //rebinds itself after 200ms	

 
});
			}
		}
	}
};	

function showMyDiv(div){
	
	$(".content>div:not('#"+div+"')").addClass('hide');
	$(".content>div#"+div+"").removeClass('hide');
}

function goBack() {
//	alert('hash change');
  // alert(document.URL);
   
  
   
   
   var isLoggedIn = localStorage.getItem("isloggedIn");
	if(isLoggedIn == "true"){
		 var parts = document.URL.split("#");
			if(parts.length==2){
				div =parts[1];
				//alert($("#"+div).hasClass('post-login'));
				
				if($("#"+div).hasClass('post-login')){
					
					showMyDiv(div);	
					
					if(div == "profile"){
						
						scrollListener();
					}
					
					
				}else{
					
					hideUserMenuDivs();
					//showMyDiv(div);	
					 window.location.href = document.URL;
				}

			}	
		
	}
  
    //blah blah blah
   /* window.location.lasthash.pop();*/
}


function executeScrollIfinBounds()
{
     if ($(window).scrollTop() >= $(document).height() - $(window).height() - 100) {
            //Add something at the end of the page
    	 
    	 //alert("reached end");
    	 
    	 var createdGroupSet = $("ul.timeline").html();
    	// alert(createdGroupSet);
    	 $("ul.timeline").append(createdGroupSet);
    	 
        }
}
