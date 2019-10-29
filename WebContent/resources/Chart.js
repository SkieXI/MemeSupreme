google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(getWeatherData);

function getWeatherData(){
	   //make AJAX call to orders via rest service
	   $.ajax(
		{
		   type: "GET",
		   url: "/MemeSupreme/rest/tweets/getalldata",
		   dataType: "json",
		   success: function(data)
		{
			   //display orders
			   screenName(data);
			   tweet(data);
			   retweetCount(data);
			   likes(data);
			   language(data);
		 },
		
		   error: function( xhr, ajaxOptions, thrownError)
		 {
			   alert(xhr.status);
	   		   alart(thrownError);
		 }
	   }
	   );
}
function drawTempC(data) {

    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'retweetCount');
			i = 0;
    for(data2 of data){
    data.addRows([
      [i,data2.tempC]
    ]);
    i++;
}
    var options = {
      hAxis: {
        title: 'Amount of Retweets'
      },
      vAxis: {
        title: 'Retweets'
      }
    };

    var chart = new google.visualization.LineChart(document.getElementById('chartRetweets_div'));

    chart.draw(data, options);
  }
    function drawTempF(datas) {

        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'likes');
    			i = 0;
        for(data2 of datas){
        data.addRows([
          [i,data2.tempF]
        ]);
        i++;
    }
        var options = {
          hAxis: {
            title: 'Amount of likes'
          },
          vAxis: {
            title: 'likes'
          }
        };

        var chart = new google.visualization.LineChart(document.getElementById('chartLikes_div'));

        chart.draw(data, options);
      }
    
    function drawHumidity(datas) {

        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'language');
    			i = 0;
        for(data2 of datas){
        data.addRows([
          [i,data2.humidity]
        ]);
        i++;
    }
        var options = {
          hAxis: {
            title: 'Language Distribution'
          },
          vAxis: {
            title: 'language'
          }
        };

        var chart = new google.visualization.LineChart(document.getElementById('chartLang_div'));

        chart.draw(data, options);
      }