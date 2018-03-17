package com.uurobot.yxwlib.cameraEncode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.uurobot.yxwlib.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CameraActivity_MediaRecoder extends Activity implements Callback {
	private RenderScript rs;
	private ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic;
	private Type.Builder yuvType, rgbaType;
	private Allocation in, out;
	String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/video.mp4";
	SurfaceView surfaceView_send;
	SurfaceView surfaceView_rev;
	private SurfaceHolder holder_sendHolder;
	private SurfaceHolder holder_revHolder;
	private Camera mCamera;
	private String TAG = "CameraActivity";
	UDPServer udpServer ;
	LocalServer localServer;
	LocalClient localClient;
	ImageView img_show ;
	private MediaRecorder mediaRecorder ;
	private Handler handler = new Handler(Looper.getMainLooper());






	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		img_show = findViewById(R.id.img_show);
		surfaceView_send = (SurfaceView) findViewById(R.id.camera_surface_send);
		surfaceView_rev = (SurfaceView) findViewById(R.id.camera_surface_rev);
		holder_sendHolder = surfaceView_send.getHolder();
		holder_revHolder = surfaceView_rev.getHolder();
		holder_sendHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//
		holder_revHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//
		holder_sendHolder.addCallback(this);
		rs = RenderScript.create(this);
		VideoView videoView = new VideoView(this);



		holder_revHolder.addCallback(new Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

			}
		});
		surfaceView_rev.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		udpServer = new UDPServer();
		new Thread(udpServer).start();

		 localServer = new LocalServer(new LocalServer.IReceiver() {
			 @Override
			 public void onReceiver(final byte[] data, final int len) {
				 Log.e(TAG,"===onReceiver==read====="+len + "   0="+data[0] + "  1="+data[1] );
//				 if (yuvType == null)
//				 {
//					 yuvType = new Type.Builder(rs, Element.U8(rs)).setX(data.length);
//					 in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);
//
//					 rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).setX(width).setY(height);
//					 out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);
//				 }
//
//				 in.copyFrom(data);
//				 yuvToRgbIntrinsic.setInput(in);
//				 yuvToRgbIntrinsic.forEach(out);
//				 final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//				 out.copyTo(bitmap);

				 final Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
				 if (bitmap != null ){
					 Log.e(TAG, "onReceiver: "+bitmap.getWidth()  + "    "+bitmap.getHeight());
					 Canvas canvas = holder_revHolder.lockCanvas();
					 canvas.drawBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),null);
					 holder_revHolder.unlockCanvasAndPost(canvas);
					 handler.post(new Runnable() {
						 @Override
						 public void run() {
							 img_show.setImageBitmap(bitmap);
						 }
					 });
				 }

			 }
		 });
		 localClient = new LocalClient();
		new Thread(localServer).start();
		new Thread(localClient).start();
	}
	UDPClientThread udpClientThread;
	public void rev(View v){
//		if (udpClientThread == null) {
//			udpClientThread = new UDPClientThread(new UDPClientThread.IRev() {
//				@Override
//				public void onData(final byte[] data) {
//					Log.e(TAG,"rev========="+data.length);
//				}
//			});
//			udpClientThread.start();
//		}
		localClient.sendData(new byte[]{1,2});

	}



	private long prevTime ;
	private synchronized void addVideoData(byte[] data) {
//		videoUtil.videostart(data);
		//Log.e(TAG, "addVideoData");
//		udpServer.send(data);

		byte[] cmdData = new byte[1 + 4];
		cmdData[0] = (byte) 0xA0; // cmd

		int length = data.length;
		cmdData[1] = (byte) (length & 0xFF);// len
		cmdData[2] = (byte) ((length >> 8) );
		cmdData[3] = (byte) ((length >> 16) );
		cmdData[4] = (byte) ((length >> 24) );


		long l = System.currentTimeMillis();
		if (l-prevTime > 100){
			prevTime = l ;
			Log.e("ff"+TAG, "addVideoData: send data===="+data.length);
			Log.e("ff"+TAG,"1="+cmdData[1]+"    2="+cmdData[2]+"   3="+cmdData[3]+"   4="+cmdData[4]);
			byte[] packageData = DataUtil.concat(cmdData, data);
			localClient.sendData(packageData);
			//localClient.sendData(data);
		}

	}

	protected boolean initCamera(SurfaceHolder holder) {
		try {
			this.mCamera = Camera.open(0);
			this.mCamera.setPreviewDisplay(holder);
			return true;
		} catch (Exception ioe) {
			ioe.printStackTrace(System.out);
			if (mCamera != null) {
				mCamera.release();
				mCamera = null;
			}
			Log.e(TAG, "initCamera: "+ ioe);
			return false;
		}
	}
	 int width = 320;
	 int height = 240;
	protected void openCamera() {
		if (mCamera != null) try {
			Camera.Parameters parameters = mCamera.getParameters();
			// parameters.setPreviewSize(640, 480);// ����Ԥ����Ƭ�Ĵ�С
			parameters.setPictureSize(640, 480);
			parameters.setPreviewFrameRate(30);// ����ÿ��30֡

			parameters.setPreviewSize(320, 240); //�����С���ܸġ���������������������������������������������������������

			List<Integer> frameRates = parameters.getSupportedPreviewFrameRates();
			for (int i : frameRates) {
				Log.d(TAG, "֧�ֵ�Ԥ��֡��:" + i);
			}
			// ʵ���Զ��Խ�
			List<String> focusModes = parameters.getSupportedFocusModes();
			if (focusModes.contains("continuous-video")) {
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			if (focusModes.contains("continuous-picture")) {
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			}
			if (focusModes.contains("torch")) {
				parameters.setFocusMode(Camera.Parameters.FLASH_MODE_TORCH);
			}
			parameters.setPictureFormat(ImageFormat.NV21);//������Ƭ�ĸ�ʽ��ֻ֧��JPEG��
//
//				 parameters.setPreviewFormat(ImageFormat.NV21);//(֧��NV21��YV12)
//				parameters.setPictureFormat(PixelFormat.JPEG); // Sets the image
//																// format for
//				// picture �趨��Ƭ��ʽΪJPEG��Ĭ��ΪNV21
			parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP); // Sets

			parameters.setJpegQuality(100);// ������Ƭ������
			mCamera.setDisplayOrientation(0);
			mCamera.setParameters(parameters);

			mCamera.setPreviewCallback(new Camera.PreviewCallback() {

				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					YuvImage image = new YuvImage(data, ImageFormat.NV21, width, height, null);
					ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
					if (!image.compressToJpeg(new Rect(0, 0, width, height), 100, os)) {
						return;
					}
					byte[] tmp = os.toByteArray();

					addVideoData(tmp);
				}
			});
			mCamera.startPreview();
			mCamera.cancelAutoFocus();
			// �����Զ��Խ�
			mCamera.autoFocus(new Camera.AutoFocusCallback() {
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					// Logger.e(TAG, "onAutoFocus_success��" + success);
					if (success) {
						camera.cancelAutoFocus();
						mCamera.autoFocus(this);
					}
				}
			});
			Log.e(TAG, "��ʼ������ͷ�ɹ�");
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "��ʼ������ͷʧ��");
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		openCamera();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initCamera(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}




}
