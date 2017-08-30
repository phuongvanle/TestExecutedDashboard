<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Execution Dashboard</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="http://underscorejs.org/underscore-min.js"></script>

<script type="text/javascript">
	google.charts.load("current", {packages:["corechart"]});
	google.charts.setOnLoadCallback(drawPieChart);
	google.charts.setOnLoadCallback(drawAreaChart);

	// Draw Pie Chart
  	function drawPieChart() { 
	  
	//Connect API get JSON content
  	var input = (function () {
    $.ajax({
        'async': false,
        'global': false,
        'url': "http://localhost:8080/TestExecutedDashboard/pieChartData/SuitProject001", 
        'dataType': "json",
        'success': function (data) {
            input = data;
	            
			//Create Table below Pie Chart
			var tr;
			tr = $('<tr/>');

			tr.append("<td>" + input[0].quantity + "</td>");
			tr.append("<td>" + input[1].quantity + "</td>");
			tr.append("<td>" + input[2].quantity + "</td>");
			$('#tableID').append(tr);
			} 
	    });
	    return input;
  	})();
	var output = input.map(function(item) { return [item.status, item.quantity]; });
	output.unshift(["Status", "Quantity"]);
	 
    var data = google.visualization.arrayToDataTable(output);
    var options = {
		title: 'Test Execution',
		is3D: true,
    };

    var Pchart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
    Pchart.draw(data, options);
    } 
	
    // Draw stacked chart
    function drawAreaChart() {
    	
    	//Connect API get JSON content
      	var input = (function () {
        
        $.ajax({
            'async': false,
            'global': false,
            'url': "http://20.203.139.12:8080/TestExecutedDashboard/areaChartData/SuitProject001", 
            'dataType': "json",
            'success': function (data) {
            	input = data;
    			} 
    	    });
    	    return input;
      	})(); 
   
      	var header = _.chain(input).pluck("status").sort().uniq(true).value();
      	header.unshift("Status");

      	var rows = _.chain(input)
      	.groupBy(function(item) { return new Date(item.date).toLocaleDateString() })
      	.map(function(group, key) { 
      	    var result = [key];
      	    _.each(group, function(item) { 
      	        result[_.indexOf(header, item.status)] = parseInt(item.quanlity); 
      	    });
      	    return result; 
      	})
      	.value();

      	var jsonData = [header].concat(rows);
    	
        var data = google.visualization.arrayToDataTable(jsonData);

        var options_stacked = {
        		title: 'APA',
                isStacked: true,
                height: 300,
                legend: {position: 'top', maxLines: 3},
                vAxis: {minValue: 0, format: '0'}
                
              };
        
        var Achart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        Achart.draw(data, options_stacked);
      }

</script>
</head>
<body>
	<table>
      <tr>
        <td><div id="piechart_3d" style="border: 1px solid #ccc; width: 620px; height: 500px;" ></div></td>
        <td><div id="chart_div" style="border: 1px solid #ccc; width: 620px; height: 500px;" ></div></td>
      </tr>
      
      <tr>
       <td>
		<table ID="tableID" style="border-collapse: collapse; background-color: #D7D7D7;" border="1">
				<tr>
					<th>Passed</th>
					<th>Failed</th>
					<th>Error</th>
				</tr>
		</table>     
    </table>
</body>
</html>