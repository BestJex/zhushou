/**
 *
 * Created by shan on 2016/4/7.
 */
(function (YT) {
    if (!YT.deploy.idoc) {
        YT.deploy.idoc = {};
    }

    var common = {

        formId: "idocNewForm",

        route_callback: function (d, data) {
            console.log("idoc bind  after render call");
            //组件初始化之后
            var appList = YT.deploy.data.appList;
            YT.deploy.util.initSelect(appList, "name", "name", "appName", data.appName);


            var enums = YT.deploy.data.enums;

            YT.deploy.util.reqGet("/data/projectList",{},function(d){
                var projectList = d.data;
                YT.deploy.util.initSelect(projectList,"id","name","projectId",data.projectId);
            });


            if($("#resNewForm").length == 1) {  //资源文档

                YT.deploy.formId = "resNewForm";

                //moduleType
                YT.deploy.util.initEnumSelect("moduleType", "module", data.module);

                //是否私有
                YT.deploy.util.initEnumSelect("yesNoType", "pri", data.pri);

                // //debugger;
                YT.deploy.idoc.resDocMDE = new SimpleMDE({
                    element: document.getElementById("resDoc"),
                    insertTexts: {
                        horizontalRule: ["", "\n\n-----\n\n"],
                        image: ["![](http://", ")"],
                        link: ["[<a href='", "' target='_blank'></a>](http://)"],
                        table: ["", "\n\n| Column 1 | Column 2 | Column 3 |\n| -------- | -------- | -------- |\n| Text     | Text      | Text     |\n\n"],
                    },
                });
            }else if($("#resViewForm").length == 1) {  //资源文档
                // //debugger;
                YT.deploy.idoc.resDocMDE = new SimpleMDE({
                    toolbar: false,
                    toolbarTips: false,
                    status:false,
                });

                // YT.deploy.idoc.resDocMDE = new SimpleMDE({ // element: document.getElementById("resDoc"),
                // });
                var resDocHtmls = YT.deploy.idoc.resDocMDE.options.previewRender(data.resultData);
                // //debugger;
                $("#resDocHtml").html(resDocHtmls);
                $(".CodeMirror").hide();
            }


            //添加
            $("button[id='btnAdd']").click(function () {
                var $tr = $("#tbContent").find("tr[name='dataItem']").first().clone();
                $tr.show();
                $("#tbContent").append($tr) ;
            });

            //上移
            $(document).off("click", "a[name='itemArrowUp']");
            $(document).on("click","a[name='itemArrowUp']",function(){
                var $thisTr = $(this).parents("tr");
                var $prevTr = $thisTr.prev();
                $thisTr.after($prevTr);
                // $prevTr.before($thisTr);
            });

            //下移
            $(document).off("click", "a[name='itemArrowDown']");
            $(document).on("click","a[name='itemArrowDown']",function(){
                var $thisTr = $(this).parents("tr");
                var $nextTr = $thisTr.next();
                $nextTr.after($thisTr);
            });


            //删除
            $(document).off("click", "a[name='itemRemove']");
            $(document).on("click","a[name='itemRemove']",function(){
                if (!confirm("您确要删除吗？")) {
                    return false;
                }
                if($("#tbContent").find("tr[name='dataItem']").length == 1){
                    alert("必须保留一条记录");
                    return;
                }
                $(this).parents("tr").remove();
            });



            $("#idocNewForm").find("button[id='btnSave']").click(function () {

                var params = YT.deploy.util.getFormParams("#idocNewForm");
                $("#tbReqParam").find("tr[name='dataItem']").each(function (index, item) {
                    var code = $(item).find("input[id='code']").val();
                    // //debugger;
                    if(!code){
                        return true;
                    }
                    params["paramList[" + index + "].code"] = code;
                    params["paramList[" + index + "].name"] = $(item).find("input[id='name']").val();
                    params["paramList[" + index + "].memo"] = $(item).find("input[id='memo']").val();
                    params["paramList[" + index + "].rule"] = $(item).find("input[id='rule']").val();
                });
                
                if(params["id"]){  //update
                    YT.deploy.util.reqPost("/idocUrl/update", params, function (d) {
                        if (d.success) {
                            alert("修改成功");
                            YT.deploy.idocList.query(1);
                        } else {
                            alert("修改失败,err=" + d.message);
                        }
                    });
                    
                }else{ //save
                    YT.deploy.util.reqPost("/idocUrl/save", params, function (d) {
                        if (d.success) {
                            alert("保存成功");
                            YT.deploy.idocList.query(1);
                        } else {
                            alert("保存失败,err=" + d.message);
                        }
                    });
                }
            });


            $("button[id='btnSubDocSave']").click(function () {
                var params = {jsonDoc:$("#jsonDoc").val()};
                YT.deploy.util.reqPost("/idocUrl/submitDoc", params, function (d) {
                    if (d.success) {
                        alert("提交成功");
                        YT.deploy.idocList.query(1);
                    } else {
                        alert("提交失败,err=" + d.message);
                    }
                });

            });

            $("button[id='btnMockDataUpdate']").click(function () {
                var params = {id:$("#id").val(),mockData:$("#mockData").val()};
                YT.deploy.util.reqPost("/idocUrl/updateMockData", params, function (d) {
                    alert("修改mock数据成功");
                    YT.deploy.idocList.query(1);
                });

            });

            $("button[id='btnSubEnumSave']").click(function () {
                var params = {appName:"member",jsonEnum:$("#jsonEnum").val()};
                YT.deploy.util.reqPost("/idocUrl/submitEnum", params, function (d) {
                    if (d.success) {
                        alert("提交成功");
                        YT.deploy.idocList.query(1);
                    } else {
                        alert("提交失败,err=" + d.message);
                    }
                });

            });

            $("button[id='btnResDocSave']").click(function () {
                var params = {appName:"member",pri:$("#pri").val(),name:$("#name").val(),resDocText:YT.deploy.idoc.resDocMDE.value()};
                YT.deploy.util.reqPost("/idocUrl/saveResDoc", params, function (d) {
                    if (d.success) {
                        alert("保存成功");
                        YT.deploy.idocList.queryRes(1);
                    } else {
                        alert("保存提交失败,err=" + d.message);
                    }
                });
            });

            $("button[id='btnResDocUpdate']").click(function () {
                var params = {appName:"member",id:$("#id").val(),pri:$("#pri").val(),name:$("#name").val(),resDocText:YT.deploy.idoc.resDocMDE.value()};
                YT.deploy.util.reqPost("/idocUrl/updateResDoc", params, function (d) {
                    if (d.success) {
                        alert("修改成功");
                        YT.deploy.idocList.queryRes(1);
                    } else {
                        alert("修改失败,err=" + d.message);
                    }
                });

            });

            //修改资源文档
            $("button[id='btnUpdateResDoc']").click(function () {
                var id = $(this).attr("data");
                YT.deploy.route("/idocUrl/viewResDoc",{id:id},"/idoc/editRes.html",{tp_title:"资源文档修改"});
            });

            //删除资源文档
            $("button[id='btnDelResDoc']").click(function () {
                if (!confirm("您确认需要删除吗？")) {
                    return;
                }

                var id = $(this).attr("data");
                YT.deploy.util.reqPost("/idocUrl/deleteById", {id:id}, function (d) {
                    if (d.success) {
                        alert("删除成功");
                        YT.deploy.idocList.queryRes(1);
                    } else {
                        alert("删除失败,err=" + d.message);
                    }
                });
            });


        },
    }
    $.extend(YT.deploy, common);


    YT.deploy.idoc = {
        resDocMDE:null

    }

})(window.YunTao);
