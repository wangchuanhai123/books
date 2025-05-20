package com.example.kzjzdatabase.service;

import javax.servlet.http.HttpServletResponse;

public interface DownloadService {
    String download(HttpServletResponse response,String name) throws Exception;
}
