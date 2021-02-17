/**
 扩展一个http模块
 **/

layui.define(function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var obj = {
        api:"http://localhost:8080/test/"  //根据自己项目的端口而定
    };

    //输出test接口
    exports('http', obj);
});

