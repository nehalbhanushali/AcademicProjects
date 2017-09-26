/**
 * 
 */


var app = angular.module('myApp', []);
app.controller('notificationController', function($scope) {
    $scope.count = 3;
   
    $scope.markRowAsRead = function(rowID){
    	//alert(rowID);
    	var fontweight =$("#"+rowID).css("font-weight");
    	//alert(fontweight);
       if(Number(fontweight) == 100){
    		
    	}else{

			$scope.count = $scope.count -1;
			$("#"+rowID).css('font-weight','lighter');
    	}
    	
    	
    	
    };
  
    $scope.markAsRead = function(){
    	
    	//int messagesUnread =getUnreadMessages();
    	//alert(messagesUnread);
    	var tableLength = $('#myNotificationsModal table tr').length;
    	
    	$(".checkone").each(function(){
    		if($(this).is(":checked")){
    			var fontweight =$(this).parent().parent().find('td:nth-child(2)').css("font-weight");
    		//	alert(Number(fontweight) == 100);
    			if(Number(fontweight) == 100){
    		
    	}else{

			$scope.count = $scope.count -1;
		$(this).parent().parent().find('td:nth-child(2)').css("font-weight","lighter");
    	}
    		}
    	});
    	
    };
    $scope.deleteSelected = function(){
    	var count = 0;
    	$(".checkone").each(function(){
    		if($(this).is(":checked")){
    			count++;
    			//$scope.count = $scope.count -1;
    		
    		
    		var fontweight =$(this).parent().parent().find('td:nth-child(2)').css("font-weight");
    			//alert(Number(fontweight) == 100);
    			if(Number(fontweight) == 100){
    				
    			}else{
    				$scope.count = $scope.count -1;	
    			}
    			var tr =$(this).parent().parent().remove();
    		}
    	});
    	var tableLength = $('#myNotificationsModal table tr').length;
    	//alert(table);

    	if(tableLength ==1){
    		
    		$(".no-notifications").removeClass('hide');
    	}
    	$("#myNotificationsModal table th").find('i').addClass('hide');
    	};
    
});





