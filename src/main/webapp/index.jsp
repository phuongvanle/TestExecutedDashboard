<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
  <head>
	<title>Quality Dashboard</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.4/lodash.js"></script>
    
    <script type="text/javascript">
    
   		var app = angular.module('myApp', []);    

		google.charts.load('current', {'packages':['corechart']});
		
		app.controller('PieChart',function($scope, $http){
			
			// Function draw pie chart 
			google.charts.setOnLoadCallback(drawPieChart);
			function drawPieChart() {
				var data = new google.visualization.DataTable(); 
				data.addColumn('string', 'Status');
				data.addColumn('number', 'Quantity');
				data.addRows($scope.Excution);
	
				var options = {
					title: 'Test Execution',
					is3D: true,
				};
	
		        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d')); 
		        chart.draw(data, options);
	      	}
			
			// Get content from JSON url
			$scope.Excution = [];
			$scope.Get = function(){
				$http({
				method : "GET",
				url : "http://20.203.139.12:8080/TestExecutedDashboard/pieChartData/SuitProject001"
			}).then(function mySuccess(response) {
				
				$scope.Excution = [
									["Passed",response.data[0].quantity],
									["Failed",response.data[1].quantity],
									["Error", response.data[2].quantity]												
								];
				console.log($scope.Excution);
				drawPieChart();
			});		
				}
			$scope.Get();
		});	
	
		
		
		//--------------------------------------------
		
		
		app.controller('AreaChart',function($scope, $http){
			
			// Function draw Area chart 
			google.charts.setOnLoadCallback(drawAreaChart);
			function drawAreaChart() {
				var data = new google.visualization.DataTable(); 
				data.addColumn('string', 'date');
				data.addColumn('number', 'Passed');
				data.addColumn('number', 'Failed');
				data.addRows($scope.Excution);
	
				var options_stacked = {
		        		title: 'APA',
		                isStacked: true,
		                height: 500,
		                legend: {position: 'top', maxLines: 3},
		                vAxis: {minValue: 0, format: '0'},
		                hAxis: {slantedText: true, slantedTextAngle: 90},
		              };
	
		        var chart = new google.visualization.AreaChart(document.getElementById('chart_div')); 
		        chart.draw(data, options_stacked);
	      	}
			
			// Get content from JSON url
			$scope.Excution = [];
			

			
			$scope.Get = function(){
				$http({
				method : "GET",
				url : "http://20.203.139.12:8080/TestExecutedDashboard/areaChartData/SuitProject001" 
			}).then(function mySuccess(response) {
				
				
				var content = response.data;
								

				var result = _(content).groupBy('date').transform(function(result, current) {
				    result.push([
				        new Date(current[0].date).toLocaleDateString(),
				        current[0].quanlity,
				        current[1].quanlity
				    ]);
				}, []).value();
				
				$scope.Excution = result;
				console.log(result);
				
				drawAreaChart();
			});		
				}
			
			$scope.Get();
		});	
		
  </script>
<body>
<div ng-app="myApp">
<table>
      <tr>
        <td><div ng-controller="PieChart" id="piechart_3d" style="border: 1px solid #ccc; width: 620px; height: 500px;" ></div></td>
        <td><div ng-controller="AreaChart" id="chart_div" style="border: 1px solid #ccc; width: 620px; height: 500px;" ></div></td>
      </tr>
      
      <tr>
       <td>
        <div ng-controller="PieChart">
		<table  border="1">
            <tr ng-repeat="row in Excution">
                        <td ng-repeat="column in row">{{column}}</td>
            </tr>
        </table>
        </div>
    </table></div>   
</body>
  
</html>
