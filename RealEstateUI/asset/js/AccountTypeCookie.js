
function AccountTypeCookie() {
    var COOKIE_NAME = "accountType";
    this.cookie = function findCookie() {
        return getCookieValue(COOKIE_NAME);
    }


    function getCookieValue(name) {
        var re = new RegExp(name + "=([^;]+)");
        var value = re.exec(document.cookie);
        return (value != null) ? decodeURI(value[1]) : null;
    }

    this.setAccountTypeCookie = function setAccountTypeCookie(accountType) {
        document.cookie = COOKIE_NAME + "=" + accountType;
    }

    this.delete = function deleteCookie() {
        document.cookie = COOKIE_NAME + "=" + getCookieValue(COOKIE_NAME) + "; expires=Thu, 18 Dec 2013 12:00:00 UTC";
    }

}