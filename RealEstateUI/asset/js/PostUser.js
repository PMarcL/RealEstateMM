

function postUser() {
    var formData =JSON.stringify( {
        "pseudonym": "testuser",
        "firstName": "John",
        "lastName": "Bartholomew",
        "email": "johnb@example.com",
        "phoneNumber": "418-666-6666"
    });

    $.ajax({
        url: "http://localhost:8080/user",
        type: "POST",
        contentType: "application/json",
        data: formData,
        dataType: "json",
        success: function (data, status, httpResponse) {
            alert(httpResponse.getResponseHeader('content-type'));
        },
        error: function (httpRequest) {
            alert(httpRequest.responseText + " fuck");
        }
    });
}
