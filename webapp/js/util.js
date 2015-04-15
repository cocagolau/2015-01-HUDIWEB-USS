/**
 * Created by park on 15. 4. 1..
 */

var req = function (method, url, data) {
    return {
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        transformRequest: function (obj) {
            var str = [];
            for (var p in obj)
                str.push(encodeURIComponent(p) + "="
                + encodeURIComponent(obj[p]));
            return str.join("&");
        },
        data: data
    }
};

function getGetUrlParsed(obj){
    var str = "";
    for (var key in obj) {
        if (str != "") {
            str += "&";
        }
        str += key + "=" + encodeURIComponent(obj[key]);
    }
    return str;
}

function error(message) {
    app.findScope('alert').alert(message);
}

Date.prototype.toJSON = function () {
    return this.getFullYear() + "." + this.getMonth() + "." + this.getDate() + "." + this.getHours() + "." + this.getMinutes();
};

Array.prototype.contains = function (obj) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == obj)
            return true;
    }
    return false;
};

