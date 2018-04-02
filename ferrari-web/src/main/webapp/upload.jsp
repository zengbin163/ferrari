<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>文件上传</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>

    <body>
        <form action="upload/upload" enctype="multipart/form-data" method="post">
                        图片上传: <input type="file" name="file">
               <input type="hidden" id="fileType" name="fileType" value="2">
               <input type="hidden" id="needCompress" name="needCompress" value="1">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        <br/><br/><br/><br/>
        <form action="upload/uploadFile" enctype="multipart/form-data" method="post">
                        文件上传: <input type="file" name="file">
               <input type="hidden" id="fileType" name="fileType" value="2">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        <br/><br/><br/><br/>
        <form action="upload/excelUpload" enctype="multipart/form-data" method="post">
        excel门店上传: <input type="file" name="excelFile">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        excel模板 <a href="http://139.196.239.206:8080/template/shop_templage.xls" >下载</a>
        <br/><br/><br/><br/>
        <form action="upload/accountUpload" enctype="multipart/form-data" method="post">
        excel账单上传: <input type="file" name="accountExcelFile">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        excel模板 <a href="http://139.196.239.206:8080/template/account_template.xls" >下载</a>
        <br/>
        <br/><br/><br/><br/>
        <form action="upload/orderComplainUpload" enctype="multipart/form-data" method="post">
        excel订单类投诉: <input type="file" name="orderComplainExcelFile">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        excel模板 <a href="http://139.196.239.206:8080/template/order_complain.xls" >下载</a>
        <br/>
        <br/><br/><br/><br/>
        <form action="upload/notOrderComplainUpload" enctype="multipart/form-data" method="post">
        excel非订单类投诉: <input type="file" name="notOrderComplainExcelFile">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        excel模板 <a href="http://139.196.239.206:8080/template/not_order_complain.xls" >下载</a>
        <br/>
        <br/><br/><br/><br/>
        <form action="upload/taobaoOrderUpload" enctype="multipart/form-data" method="post">
        excel订单导入: <input type="file" name="taobaoOrderExcelFile">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        excel模板 <a href="http://139.196.239.206:8080/template/order_template.xls" >下载</a>
        <br/>
        <br/>
        <br/><br/><br/><br/>
        <form action="upload/customerUpload" enctype="multipart/form-data" method="post">
        excel订单导入: <input type="file" name="customerExcelFile">
               <input type="hidden" id="sessionId" name="sessionId" value="bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=">
               <input type="submit" value="上传" />
        </form>
        excel模板 <a href="http://139.196.239.206:8080/template/crm_customer.xls" >下载</a>
        <br/>
    </body>
</html>