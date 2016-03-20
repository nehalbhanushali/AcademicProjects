/**
 * 
 */



// tooltip code


$(document).ready(function () {
//	 alert('hi all');	
	

	
	 $("a.tooltips").hide(); //## to do remove
	 
	 $("input#my-header-searchBox").hide();
     $("a#my-header-searchIcon-a").click(function(){
	  $("input#my-header-searchBox").toggle();
    });
  
  $('.my-hideToSignIn').hide();
  
  var signUp = false;
  
  $('#signUpLink').click(function(){
	  signUp = true;
	 $('.my-hideToSignUp').hide();
	 $('.my-hideToSignIn').show();
	 $('#password').val("");
	 $('#password-hierarchy-text').html("");
	 $("a.tooltips").hide();
	 
  });
  $('#signInLink').click(function(){
	  signUp = false;
		 $('.my-hideToSignIn').hide();
		 $('.my-hideToSignUp').show();
		 $('#password').val("");
		 $('#password-hierarchy-text').html("");
		
		 $("a.tooltips").hide();
		 
	  });
  


  
//validator rules later
  
  
  
  $("#form1").validate({
	  errorElement:'div',

		//specify the validation rules
		rules:{
			fullName:"required",
			password:{
				required:true
				
			},
			confirmPassword:{
				required:true
				
			},
			email:{
				required:true,
				laxEmail:true
			}

		},

		//specify the error messages
		messages:{
			fullName:"Please enter your name",
			password:"Please enter a password",
			confirmPassword:"Please confirm your password",
			email:"Please enter a valid email address"
		}

	});
  
  jQuery.validator.addMethod("laxEmail", function(value, element) {
	  // allow any non-whitespace characters as the host part
	  return this.optional( element ) || /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test( value );
	}, 'Please enter a valid email address.');
  


  

	  
	  $('#password').strengthMeter('text', {
	      container: $('#password-hierarchy-text'),
	      hierarchy: {
	          '0': ['text-danger', 'Think about a stronger password!'],
	          '5': ['text-warning', 'Weak'],
	          '10': ['text-warning', 'Strong'],
	          '15': ['text-success', 'Very Strong']
	      }
	  });	  



 
	

  
  
})

