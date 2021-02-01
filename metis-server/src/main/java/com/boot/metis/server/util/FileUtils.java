package com.boot.metis.server.util;

import com.boot.metis.server.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class FileUtils {

    /**
     * 向客户端下载文件,弹出下载框.
     *
     * @param file(需要下载的文件)
     */
    public static void downloadFile(HttpServletResponse response, File file) {
        OutputStream out = null;
        InputStream in = null;
        // 获得文件名
        String fileName = null;
        try {
            fileName = URLEncoder.encode(file.getName(), "UTF-8");
            out = response.getOutputStream();
            response.reset();
            in = new FileInputStream(file.getPath());
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", file.length() + "");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            int data = 0;
            while (-1 != (data = in.read())) {
                out.write(data);
            }
        }catch (IOException e) {
            log.error("exportFile error " + e.getMessage());
        }finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("exportFile close InputStream error " + e.getMessage());
                }
            }
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("exportFile close OutputStream error " + e.getMessage());
                }
            }
        }
    }
}
