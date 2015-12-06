$(document).ready(function () {
    getSellerMessages();
});

function getSellerMessages() {
    $.ajax({
        url: "http://localhost:8080/message/usermessages/" + new TokenCookie().cookie(),
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            var sellerMessagesJson = JSON.parse(httpResponse.responseText);
            displaySellerMessages(sellerMessagesJson);
            readMessages();

        }
    });
}


function displaySellerMessages(messagesJson) {
    var buffer = "";
    $.each(messagesJson, function () {
        var unreadClass = "title";
        if(this.unread){
            unreadClass += "-unread";
        }
        buffer = buffer
            + "<li class='messageslist-item collection-item'>"
            + "<div class='messageContainer'>"
            + "<div class=" + unreadClass+">" + 'From: ' + this.senderPseudonym + "</div>"
            + "<div class='content'>" + this.message.split('Contact information:')[0] + "</div>"
            + "<div class='contact-info'>" + this.message.split('Contact information:')[1] + "</div>"
            + "</div>"
            + "</li>";
        $('.message-list').html(buffer);
    });
}


function readMessages(){
    $.ajax({
        url: "http://localhost:8080/message/readall/" + new TokenCookie().cookie(),
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            console.log("messagesAreRead");
        }
    });
}


