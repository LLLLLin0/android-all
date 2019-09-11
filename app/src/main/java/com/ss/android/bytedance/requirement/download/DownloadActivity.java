package com.ss.android.bytedance.requirement.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ss.android.bytedance.R;
import com.ss.android.bytedance.R2;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created on 2019-09-09.
 * linzhipeng.1996@bytedance.com
 */
public class DownloadActivity extends AppCompatActivity {

    private static final String TAG = "DownloadActivity";

    @BindView(R2.id.edit)
    EditText mEditText;
    @BindView(R2.id.progress)
    ProgressBar mProgressBar;
    @BindView(R2.id.img)
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedance_download_activity);
        ButterKnife.bind(this);
        mProgressBar.setMax(100);
    }

    @OnClick(R2.id.download)
    void send() {
        final String s = mEditText.getText().toString();
        Toast.makeText(this, "send" + s, Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = DownloadManager.get().syncDownload(
                        s, null, getFilesDir().getAbsolutePath(), "123.jpg", new DownloadManager.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(Response response) {
                                Log.i(TAG, "onDownloadSuccess:" + response);
                            }

                            @Override
                            public void onDownloading(int progress, long currentSize, long maxLength) {
                                Log.i(TAG, "onDownloading: pro" + progress + "cus" + currentSize + " max" + maxLength);
                                runOnUiThread(() -> {
                                    mProgressBar.setProgress(progress);
                                });
                            }

                            @Override
                            public void onDownloadFailed() {
                                Log.i(TAG, "onDownloadFailed: ");
                            }
                        }
                        , new DownloadManager.TaskInfo() {
                            @Override
                            public boolean isCancel() {
                                return false;
                            }

                            @Override
                            public boolean cancel() {
                                return false;
                            }
                        });

                Log.i(TAG, "file:" + file);
                runOnUiThread(() -> {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    img.setImageBitmap(bitmap);
                });
            }
        }).start();
    }


}
