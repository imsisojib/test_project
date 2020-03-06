package com.example.toletgo.background_threads;

import android.content.Context;
import android.os.Looper;
import android.os.Handler;

import com.example.toletgo.interfaces.ImageCompressTaskListener;
import com.example.toletgo.ui_helper.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageCompressThread implements Runnable{

    private Context mContext;
    private List<String> originalPaths = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<File> result = new ArrayList<>();
    private ImageCompressTaskListener mImageCompressTaskListener;


    public ImageCompressThread(Context context, String path, ImageCompressTaskListener compressTaskListener) {

        originalPaths.add(path);
        mContext = context;
        mImageCompressTaskListener = compressTaskListener;
    }
    public ImageCompressThread(Context context, List<String> paths, ImageCompressTaskListener compressTaskListener) {
        originalPaths = paths;
        mContext = context;
        mImageCompressTaskListener = compressTaskListener;
    }
    @Override
    public void run() {

        try {

            //Loop through all the given paths and collect the compressed file from Util.getCompressed(Context, String)
            for (String path : originalPaths) {
                File file = Util.getCompressed(mContext, path);
                //add it!
                result.add(file);
            }
            //use Handler to post the result back to the main Thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    if(mImageCompressTaskListener != null)
                        mImageCompressTaskListener.onComplete(result);
                }
            });
        }catch (final IOException ex) {
            //There was an error, report the error back through the callback
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(mImageCompressTaskListener != null)
                        mImageCompressTaskListener.onError(ex);
                }
            });
        }
    }
}

