function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
function getBearerAuth() {
    var token = getCookie("Authorization");
    token = token.substring(7,token.length);
    return token;
}
