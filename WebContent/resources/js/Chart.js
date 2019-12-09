google.charts.load('current', {'packages':['line']});

google.charts.setOnLoadCallback(drawChart);

function getAllBatch(){
	   //make AJAX call to orders via rest service
	   $.ajax(
		{
		   type: "GET",
		   url: "/MemeSupreme/rest/tweets/getAllBatch",
		   dataType: "json",
		   success: function(data)
		{
			   //display orders
			   drawStuff(data);
		 },
		
		   error: function( xhr, ajaxOptions, thrownError)
		 {
			   alert(xhr.status);
	   		   alart(thrownError);
		 }
	   }
	   );
}
function drawChart(datas) {

    var data = new google.visualization.DataTable();
    data.addColumn('number', 'retweetsTotal');
    data.addColumn('number', 'likesTotal');
    data.addCloumn('number', 'toatlTweets');
			i = 0;
    for(data2 of datas){
    data.addRows([
      [i,data2.likesTotal]
      [i,data2.reTweetsTotal]
      [i,data2.tweetsTotal]
    ]);
    i++;
}
    var options = {
      hAxis: {
        title: 'Number'
      },
      vAxis: {
        title: 'Likes'
      }
    };

    var chart = new google.visualization.LineChart(document.getElementById('drawStuff'));

    chart.draw(data, options);
  } 