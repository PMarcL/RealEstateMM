function TokenCookie() {
    var COOKIE_NAME = "token";
    this.cookie = function findCookie() {
        return getCookieValue(COOKIE_NAME);
    }


    function getCookieValue(name) {
        var re = new RegExp(name + "=([^;]+)");
        var value = re.exec(document.cookie);
        return (value != null) ? decodeURI(value[1]) : null;
    }

    this.setTokenCookie = function setTokenCookie(token) {
        document.cookie = COOKIE_NAME + "=" + token;
    }

    this.delete = function deleteCookie() {
        document.cookie = COOKIE_NAME + "=" + getCookieValue(COOKIE_NAME) + "; expires=Thu, 18 Dec 2013 12:00:00 UTC";
    }

}
