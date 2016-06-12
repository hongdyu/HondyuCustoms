/**
 * Created by ThinkPad User on 2016/2/25.
 */
//声明ajax地址
var urlAjax="http://192.168.1.117:8090/020/01/list"

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
//缓存js 也就是webstorge