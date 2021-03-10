
	

	function Userlogin(){
	$("#login-alert").hide();	
	var valid = "true";
	var mobileNoLogin = $('#mobileNoLogin').val();
	if (mobileNoLogin == ""){
		$("#login-alert").show();
		$("#login-alert").html('Please Enter Mobile Number');	
			document.getElementById("mobileNoLogin").focus();
			valid = "false";
			return false;
		}
	if(valid == "true"){
		WebViewInterface.UserLoginMethod(mobileNoLogin);
		}
	}

function exit()
{
	WebViewInterface.exitApp();
}

function rateus()
{
	WebViewInterface.RateUs();
}

function share()
{
	WebViewInterface.ShareApp();
}

function mainmenu()
{
	WebViewInterface.MainMenu();
}
	
function viewfeedback()
{
	WebViewInterface.ViewFeedbackList();
}	

function myaccount()
{
	WebViewInterface.MyAccount();
}

function addfeedback()
{
	WebViewInterface.AddFeedback();
}	

function logout()
{
	WebViewInterface.logoutMethod();
}