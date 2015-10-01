function LoginCookie() {
    var COOKIE_NAME = "realestateUser";
    this.cookie = function findCookie() {
        return getCookieValue(COOKIE_NAME);
    }

    this.isUserPresent = function isUserPresent() {
        if (getCookieValue(COOKIE_NAME) != null) {
            return true;
        }
        else {
            return false;
        }
    }

    function getCookieValue(name) {
        var re = new RegExp(name + "=([^;]+)");
        var value = re.exec(document.cookie);
        return (value != null) ? decodeURI(value[1]) : null;
    }

    this.setUsernameCookie = function setUsernameCookie(username) {
        document.cookie = COOKIE_NAME + "=" + username;
    }

    this.delete = function deleteCookie() {
        document.cookie = COOKIE_NAME + "=" + getCookieValue(COOKIE_NAME) + "; expires=Thu, 18 Dec 2013 12:00:00 UTC";
    }

}
