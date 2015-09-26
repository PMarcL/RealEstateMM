
var userCookie = new LoginCookie();

$(document).ready(displayUserOptions());



function displayUserOptions()
{
    loggedInDisplay();

}


function loggedInDisplay()
{
    if(userCookie.isUserPresent())
    {
        $('.login').attr("style", "display:none");
        $('.signup').attr("style", "display:none");
        $('.signout').attr("style", "display:inline-block");
    }
    else
    {
        $('.login').attr("style", "display:inline-block");
        $('.signup').attr("style", "display:inline-block");
        $('.signout').attr("style", "display:none");
    }
}

function signout()
{
    userCookie.delete();
    displayUserOptions();
}