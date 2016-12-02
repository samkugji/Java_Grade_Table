var mainApp = angular.module("mainApp", ["ngRoute"]);
mainApp.controller('mainCtrl', function ($scope, $http){
	$scope.students = [];
});

mainApp.config(["$routeProvider", function($routeProvider) {
    $routeProvider.when("/addStudents", {templateUrl:"addStudents", controller:"AddStudentsCtrl"})
        .when("/viewStudents", {templateUrl:"viewStudents", controller:"ViewStudentsCtrl"})
        .when("/searchStudents", {templateUrl:"searchStudents", controller:"SearchStudentsCtrl"})
        .when("/editStudents", {templateUrl:"editStudents", controller:"EditStudentsCtrl"})
        .otherwise({redirectTo: "addStudents"});
}]);

mainApp.controller("AddStudentsCtrl", function($scope, $http) {
	// HTML Form method in View.html
});

mainApp.controller("ViewStudentsCtrl", function($scope, $http){
	console.log("viewctrl");

	 $http({
		 method:"GET",
		 url:"List",
		 headers: {'Content-Type': 'application/json'}
	 }).success(function(data) {
		 console.log("viewStudent GET success");
		 console.log(data);
		 $scope.students = data;
         
	 }).error(function() {
		 console.log("viewctrl GET fail");
	 });
	 
	 $scope.orderByMe = function(criteria) {
		 $scope.myOrderBy = criteria;
	 }
     
     $scope.removeStudent = function(index) {
//    	 ERROR: student 객체에 직접 접근은 불가 (ng-repeat 로 students객체에서 바인딩 되기 때문)
// 		 SOL: angular.forEach($scope.students, function(student, index) 로 student를 넘겨줄 수 있겠    	 
    	 
    	 $http({
		 	 method:"POST",
		 	 url:"Delete",
			 data: $.param({
				 id : $scope.students[index].id
			  	}),
			  	headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
			  
			 }).success(function() {
			 	 console.log("Delete button success");
			 	 $scope.students.splice(index, 1);
			 }).error(function() {
				 console.log("Delete button fail");
		 })
     };
     
     $scope.removeChecked = function() {
         // BUG: 2개 이상체크시 짝수번째 것들이 forEach에 안들어 옮
    	 // SOL: check를 판별하는 if문 안으로 angular 식 옮겨줌
        angular.forEach($scope.students, function(student, index) {
        
        	console.log(index, student); 
            
        	if (student.checked) {
        		$http({
        		 	 method:"POST",
        		 	 url:"Delete",
        			 data: $.param({
        				 id : student.id
        			  	}),
        			  	headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
        			 }).success(function() {
        			 	 console.log("check delete success");
                         $scope.students.splice($scope.students.indexOf(student), 1);
        			 }).error(function() {
        				 console.log("check delete fail");
        		})
             };
         });
     };
});

mainApp.controller("SearchStudentsCtrl", function($scope, $http) {
  console.log("searchctrl");

	 $http({
		 method:"GET",
		 url:"List",
		 headers: {'Content-Type': 'application/json'}
	 }).success(function(data) {
		 console.log("searchStudent GET success");
		 console.log(data);
		 $scope.students = data;
     $scope.orderByMe = function(criteria) {
         $scope.myOrderBy = criteria;
     };

     //ERROR:검색 필터 적용 후 삭제가 잘 안됨 id찾는 곳에 문제 발생
     $scope.removeStudent = function(index) {
    	 
    	 $http({
		 	 method:"POST",
		 	 url:"Delete",
			 data: $.param({
				 id : $scope.students[index].id
			  	}),
			  	headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
			  
			 }).success(function() {
			 	 console.log("Delete button success");
		    	 $scope.students.splice(index, 1);
			 }).error(function() {
				 console.log("Delete button fail");
		 })
     };
     
     $scope.removeChecked = function() {
         /*BUG: 2개 이상체크시 짝수번째 것들이 forEach에 안들어 옮*/
         angular.forEach($scope.students, function(student, index) {
        	 
           console.log(student);
             if (student.checked) {
                 $scope.students.splice($scope.students.indexOf(student), 1);
                 
               	 $http({
        		 	 method:"POST",
        		 	 url:"Delete",
        			 data: $.param({
        				 id : student.id
        			  	}),
        			  	headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
        			  
        			 }).success(function() {
        			 	 console.log("check delete success");
        			 }).error(function() {
        				 console.log("check delete fail");
        		 })
             };
         });
     };
	 }).error(function() {
		console.log("searchStudent GET fail");
	});
});

mainApp.controller("EditStudentsCtrl", function($scope, $http) {
    
	$scope.isEditable = false;
    $scope.editablerow = {};
    $scope.reset = function(){
        $scope.editablerow = {};
        $scope.isEditable = false;
    } 
	
	 $http({
		 method:"GET",
		 url:"List",
		 headers: {'Content-Type': 'application/json'}
	 }).success(function(data) {
		 console.log("EditStudentsCtrl GET success");
		 console.log(data);
		 $scope.students = data;
	 }).error(function() {
		 console.log("EditStudentsCtrl GET fail");
	 });

     
	 $scope.editStudent = function(student, index) {
		 
		 console.log("Edit button");
         $scope.editablerow.id = student.id;
         $scope.editablerow.name = student.name;
         $scope.editablerow.kor = student.kor;
         $scope.editablerow.eng = student.eng;
         $scope.editablerow.math = student.math;
         $scope.index = index;
         $scope.isEditable = true;
	  };
		 
     $scope.saveData = function() {
		 
    	 console.log("saveData function");
    	 
		 $http({
		 method:"POST",
	 	 url:"Edit",
		 data: $.param({
			 id :  $scope.editablerow.id,
			 name :  $scope.editablerow.name,
			 kor :  $scope.editablerow.kor,
			 eng :  $scope.editablerow.eng,
			 math :  $scope.editablerow.math,
		  	}),
		 headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
		 }).success(function() {
	         $scope.editablerow.sum = $scope.editablerow.kor + $scope.editablerow.eng + $scope.editablerow.math;
	         $scope.editablerow.avg = $scope.editablerow.sum / 3;
	         $scope.students[$scope.index] = angular.copy($scope.editablerow);
	         $scope.reset();
			    
		 }).error(function() {
			 console.log("Save button fail")
	 	});
     }
	
});