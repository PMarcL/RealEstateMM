
$(document).ready(function(){
    getSellerMessages();
});

function getSellerMessages() {
    $.ajax({
        url: "http://localhost:8080/message/unread/" + new TokenCookie().cookie(),
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            var sellerMessagesJson = JSON.parse(httpResponse.responseText);
            console.log(sellerMessagesJson);

        },
        error: function (data) {
            console.log(data.responseText);
        }
    });
}


