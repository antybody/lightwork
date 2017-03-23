<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.io.FileOutputStream,
			java.io.BufferedOutputStream,
			java.io.BufferedInputStream,
			java.io.FileInputStream,
			org.apache.poi.ss.util.WorkbookUtil;"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String taskid = request.getParameter("taskId");
	BufferedInputStream bis=null;
	BufferedOutputStream   bos=null;
	try{
		//获取文件名
		String fileName = request.getParameter("fileId")+".xlsx";
		//设置response的编码方式
		response.setContentType("application/x-msdownload");
		//设置附加文件名
		response.setHeader("Content-disposition","attachment; filename="+fileName);
		//String filepath = config.getServletContext().getRealPath("/excel/"+fileName);
		String filepath = "D:/44444444444444444444444444/test/"+fileName;
		//输入流
		bis = new BufferedInputStream(new FileInputStream(filepath));
		//输出流
		bos=new BufferedOutputStream(response.getOutputStream());
		if(bis!=null){
			//设置缓冲区大小
			byte[] buff = new byte[2048];
			int bytesRead;
			while(-1 != (bytesRead = bis.read(buff,0,buff.length))){
				bos.write(buff,0,bytesRead);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
	//关闭输入流和输出流
		if (bis != null)bis.close();
		if (bos != null)bos.close();
	}
	out.clear();
	out=pageContext.pushBody();
%>