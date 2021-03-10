
$(document).ready(function(){
	
	//var style_code = WebViewInterface.getStyleDetails();
	var style_code = 5;
	
	if(style_code == 1)
	{
		$('head').append('<link rel="stylesheet" type="text/css" href="css/style1.css">');
	}
	
	if(style_code == 2)
	{
		$('head').append('<link rel="stylesheet" type="text/css" href="css/style2.css">');
	}
	
	if(style_code == 3)
	{
		$('head').append('<link rel="stylesheet" type="text/css" href="css/style3.css">');
	}
	
	if(style_code == 4)
	{
		$('head').append('<link rel="stylesheet" type="text/css" href="css/style4.css">');
	}
	
	if(style_code == 5)
	{
		$('head').append('<link rel="stylesheet" type="text/css" href="css/style5.css">');
	}
	
});