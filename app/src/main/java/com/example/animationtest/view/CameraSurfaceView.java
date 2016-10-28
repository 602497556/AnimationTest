package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback,
                                    Camera.AutoFocusCallback {

    private Context mContext;
    private SurfaceHolder holder;
    private Camera mCamera;
    private int mScreenWidth;
    private int mScreenHeight;

    public CameraSurfaceView(Context context) {
        this(context,null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getScreenMetrics(context);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        holder = getHolder();//获得SurfaceHolder引用
        holder.addCallback(this);
        //设置类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * 获取屏幕参数
     *
     * @param context context
     */
    private void getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(mCamera == null){
            //开启相机
            mCamera = Camera.open();
        }
        try {
            //相机画面显示在surface
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //设置参数并开始预览
        setCameraParams(mCamera,mScreenWidth,mScreenHeight);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();//停止预览
        mCamera.release();//释放相机资源
        mCamera = null;
        holder = null;
    }


    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }

    /**
     * 拍照瞬间调用
     */
    private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    /**
     * 获得没有压缩过的图片数据
     */
    private Camera.PictureCallback raw = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    /**
     * 创建jpeg图片回调数据对象
     */
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            BufferedOutputStream bos = null;
            Bitmap bitmap = null;
            try {
                String photoName = getPhotoFileName();
                bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    //照片保存路径
                    String filePath = "/sdcard/"+photoName;
                    File file = new File(filePath);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    //将图片压缩到流中
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                } else {
                    Toast.makeText(mContext,"没有检测到内存卡",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.flush();//输出
                    bos.close();//关闭
                    bitmap.recycle();//回收bitmap空间
                    mCamera.stopPreview();
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 生成照片名
     *
     * @return
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date)+".jpeg";
    }

    public Camera getmCamera(){
        return mCamera;
    }

    public void setAutoFocus(){
        mCamera.autoFocus(this);
    }

    public void takePicture(){
        //设置参数，并拍照
        setCameraParams(mCamera,mScreenWidth,mScreenHeight);
        // 当调用camera.takePiture方法后，camera关闭了预览，这时需要调用startPreview()来重新开启预览
        mCamera.takePicture(null,null,jpeg);
    }

    /**
     * 设置相机参数
     *
     * @param camera camera
     * @param width 屏幕宽
     * @param height 屏幕高
     */
    private void setCameraParams(Camera camera, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        // 获取摄像头支持的PictureSize列表
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        /**从列表中选取合适的分辨率*/
        Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
        if (null == picSize) {
            picSize = parameters.getPictureSize();
        }
        // 根据选出的PictureSize重新设置SurfaceView大小
        float w = picSize.width;
        float h = picSize.height;
        parameters.setPictureSize(picSize.width,picSize.height);
        this.setLayoutParams(new FrameLayout.LayoutParams((int) (height*(h/w)), height));

        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

        Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
        if (null != preSize) {
            parameters.setPreviewSize(preSize.width, preSize.height);
        }
        // 设置照片质量
        parameters.setJpegQuality(100);
        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            // 连续对焦模式
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        //自动对焦
        mCamera.cancelAutoFocus();
        //设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);

    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     *         h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }
        return result;
    }



}
