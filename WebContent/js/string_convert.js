var myvar = "<!DOCTYPE HTML>"+
"<html>"+
"<head>"+
"<title>"+
"    Pet Health Insurance Application Form"+
"     </title>"+
"     <link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css'>"+
" <script src='https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js'></script>"+
"  <script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>"+
"     <style>"+
"         .blue-box{"+
"             "+
"             background-color:azure;"+
"         } "+
"         .title{"+
"             background-color:aliceblue ;"+
"         }"+
"     </style>"+
"     </head>"+
"     <body>"+
"     <div class = 'wrapper'>"+
"<header>"+
"         <div class = 'col-sm-offset-4 col-sm-4'><img src = 'images/header_logo.jpg' alt ='header'/></div>"+
"    "+
"         </header> "+
"         <div class ='container-fluid'>"+
"             <form action ='part5_petform' method ='post'>"+
"            <div class ='col-sm-12 blue-box'>Ccc-laim Form PLEASE INCLUDE YOUR PET'S MEDICAL RECORDS TO HELP EXPEDITE PROCESSING</div> "+
"             <div class ='col-sm-12 '>"+
"            <div class ='title'> General Information Please fill out this form completely. Incomplete forms will delay processing.</div>"+
"             <div class ='col-sm-6'>"+
"                 <div class ='title2'> Your Information"+
"                 </div>"+
"<table><tr><td>Name:</td><td><input type = 'text' name = 'owner-name'/></td></tr>"+

"<tr><td>Address:</td><td><input type = 'text' name = 'address'/></td></tr>"+
"<tr><td>reed:</td><td><input type = 'text' name = 'city-state-zip'/></td></tr> "+
"     <tr><td>Phone:</td><td><input type = 'text' name = 'phone'/></td></tr>"+
"     <tr><td>Email:</td><td> <input type = 'email' name = 'email'/>  </td></tr>"+
"                 </table>  "+
"                 "+
"                 </div> "+
"                 <div class ='col-sm-6'>"+
"                  <div class ='title2'> Pet Information"+
"                 </div>"+
"<table>"+
"     <tr><td>Account number:</td><td><input type = 'text' name = 'account-number'/></td></tr>"+
"     <tr><td>Name:</td><td><input type = 'text' name = 'pet-name'/></td></tr>"+
"<tr><td>Breed:</td><td><input type = 'text' name = 'breed'/></td></tr> "+
"     <tr><td>Age:</td><td><input type = 'number' name = 'age'/></td></tr>"+
"     <tr><td>Gender:</td><td> <input type = 'radio' name = 'pet-gender' value = 'male'/> Male <input type = 'radio' name = 'pet-gender' value = 'female'/> Female  </td></tr>"+
"                 </table>  "+
"                 </div>"+
"                 "+
"             </div> "+
"                 "+
"                 <!--sec 1 ends-->"+
"             <div class ='col-sm-12'>"+
"               <div class ='title col-sm-6'> Diagnosis/Symptom Information"+

"                 </div>"+

"               <div class ='title col-sm-6'> HELP US! By providing the Story of Occurrence/Diagnosis, you will help"+
"us avoid delays in processing your claim"+
"                 </div>"+
"                <div class ='col-sm-12'>"+
"                    <div>Story of Occurrence/Diagnosis - Please describe this incident, including dates, details and symptoms leading                           up to :"+
"                    </div>"+
"                <div><textarea name =story' cols='50' rows='5'></textarea></div>";


var myvar2 = "<div class ='col-sm-12 '>"+
"            <input type ='submit' class ='btn btn-info' value = 'SUBMIT'/>"+
"           </div>"+
"               "+
"        </form>"+
"        </div><!--container ends-->"+
"        </div><!--wrapper ends-->"+
"    </body>"+
"</html>";