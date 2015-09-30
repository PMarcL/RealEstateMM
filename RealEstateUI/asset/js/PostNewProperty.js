
function postNewProperty()
{
	 var formData = {propertyType:"",properTyAddress:"",propertyPrice:"",propertyOwner:"",propertyStatus:""};

    $.ajax({
        url : "localhost:8080/addProperty",
        type: "POST",
        contentType: "application/json",
        data : formData,
        dataType:"json",
        success: function()
        {
            alert("yay");
        },
        error: function (httpRequest, textStatus, errorThrown)
        {
            alert(errorThrown + " fuck");
        }
    });
}