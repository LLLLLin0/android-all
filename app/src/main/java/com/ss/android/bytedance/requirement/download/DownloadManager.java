package com.ss.android.bytedance.requirement.download;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 2019-09-09.
 * linzhipeng.1996@bytedance.com
 */
public class DownloadManager {

    private final OkHttpClient okHttpClient;

    private static class Holder {
        static DownloadManager sInstance = new DownloadManager();
    }

    public static DownloadManager get() {
        return DownloadManager.Holder.sInstance;
    }

    private DownloadManager() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 同步执行下载
     *
     * @param url            下载连接
     * @param requestHeaders 请求的文件头信息
     * @param saveDir        储存下载文件的SDCard目录
     * @param filename       下载文件名
     * @param listener       下载监听
     * @param taskInfo       任务信息
     * @return File   返回同步下载后存储的文件    下载失败情况返回null
     */
    public synchronized File syncDownload(final String url, Map<String, String> requestHeaders,
                                          final String saveDir, final String filename,
                                          final OnDownloadListener listener,
                                          final TaskInfo taskInfo) {
        try {
            Request.Builder builder = new Request.Builder().url(url);
            Request request = builder.build();
            Response response = okHttpClient.newCall(request).execute();
            File file = new File(saveDir, filename);
            if (response != null) {
                file = responseToFile(response, file, listener, taskInfo);
            } else if (file.exists()) {
                file.delete();
            }
            return file;
        } catch (Exception e) {
            if (listener != null) {
                listener.onDownloadFailed();
            }
        }
        return null;
    }

    /**
     * 响应数据存文件
     *
     * @param response
     * @param file
     * @return
     */
    private File responseToFile(Response response, File file, OnDownloadListener listener, TaskInfo taskInfo) {
        if (response.body() == null) {
            if (listener != null) {
                listener.onDownloadSuccess(response);
            }
            return file;
        }
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        // 储存下载文件的目录
        boolean abort = false;
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            if (file.exists()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("errMsg", "file.exists()");
                } catch (JSONException e) {

                }
                file.delete();
            }
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                if (taskInfo != null && taskInfo.isCancel()) {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {

                    }
                    try {
                        if (fos != null) {
                            fos.flush();
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                    abort = true;
                    break;
                }
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                // 下载中
                if (listener != null) {
                    listener.onDownloading(progress, sum, total);
                }
            }
            fos.flush();
        } catch (Exception e) {
            //下载异常，删除文件
            file.delete();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
            if (listener != null) {
                if (abort) {
                    listener.onDownloadFailed();
                } else {
                    listener.onDownloadSuccess(response);
                }
            }
        }
        return file;
    }

    public interface TaskInfo {
        boolean isCancel();

        boolean cancel();
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(Response response);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress, long currentSize, long maxLength);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}
