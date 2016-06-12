package com.yhd.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

@SuppressLint("DrawAllocation") 
public class CutPicView extends View implements OnTouchListener {

	private Context mContext;
	private Paint mPaint;//�滭CutPicView����Ҫpain
	private Paint mRoundPaint;//�滭��ǰ���ɰ���ཻԲ��paint
	private Paint mFgroundPaint;//�滭ǰ���ɰ���paint
	private Paint mClearFgPaint;//����ɰ�����ݵ�paint
	private Paint mCutPicPaint;//�и�Բ��bitmap��pain
	
	private Bitmap mBitmap;//��������bitmap�������и�
	private Bitmap mLastBitmap;//���ڱ��洫����bitmap����������bitmap�ĸ���
	private Bitmap mFgBitmap;//���ڻ滭ǰ���ɰ���bitmap
	private Bitmap output;//���ڳн��и������ͼƬ���ݣ�Ҳ����˵������Ƿ�������Բ��bitmap
	
	
	private Point mCenterPoint;
	private Point mBitmapPoint;
	private PointF mFirstDragPoint;
	private PointF mFirstZoomPoint;
	private PointF mSecondZoomPoint;
	
	private Canvas mFgCanvas;//ǰ���ɰ�㻭��
	private Canvas canvas;//�и�Բ��bitmap�Ļ���
	private Rect src;
	private Paint paint;
	private RectF rectF;
	private Rect dst;
	private RectF mRect;
	
	private final int MODE_DRAG = 0;
	private final int MODE_ZOOM = 1;
	
	private int mCurrMode;
	private int mRadius;
	private int mRingWidth;
	private int mBitmapLastWidth;
	private int mBitmapLastHeight;
	private int mAvatorWidth;
	private float scale = 0;
	private float oriDis;
	private boolean isFinishFirstZoomed;
	private boolean isSecondePointerUp;
	
	public CutPicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		//���ð뾶Ϊ��Ļ��1/3
		mRadius = getScreenWidth()*1/5;
		mRingWidth = 4;
		mAvatorWidth = 2*mRadius;
		
		mPaint = new Paint();
		mRoundPaint = new Paint();
		mFgroundPaint = new Paint();
		mClearFgPaint = new Paint();
		mCutPicPaint = new Paint();
		
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true); 
		mPaint.setARGB(255, 255 ,225, 255);
		mPaint.setStrokeWidth(mRingWidth);
		
		mRoundPaint.setStrokeWidth(mRadius);  
        mRoundPaint.setAntiAlias(true);
		mRoundPaint.setARGB(255, 0, 0, 0);
		
		mFgroundPaint.setAntiAlias(true);
		mFgroundPaint.setARGB(185, 0 ,0, 0); 
		//����ǰ���ɰ��Ļ���XfermodeģʽΪXOR��Ҳ�������ģʽ���Խ�����ͼ���ཻ�Ĳ���������ʣ��һ�����ſն����ɰ��
		mFgroundPaint.setXfermode(new PorterDuffXfermode(Mode.XOR));
		//�����и�Բ��ͷ��Ļ���Xfermode�����ģʽ���Խ�����ͼ���ཻ�Ĳ����и���������ƿ�ͼ
		mCutPicPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		
		mCenterPoint = new Point();
		mBitmapPoint = new Point();
		mFirstDragPoint = new PointF();
		mFirstZoomPoint = new PointF();
		mSecondZoomPoint = new PointF();
		
		 //���mFgCanvas�е�����
		mClearFgPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
       // mClearFgPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));
        
		initCutPicParams();
		
		setOnTouchListener(this);
		
	}

	private void initCutPicParams() {
		float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        float offsetX = mCenterPoint.x - mBitmapPoint.x-mRadius;
        float offsetY = mCenterPoint.y - mBitmapPoint.y-mRadius;
        
        top = offsetY;
        bottom = offsetY+mAvatorWidth;
        left = offsetX;
        right = offsetX+mAvatorWidth;
                
        dst_left = 0;
        dst_top = 0;
        dst_right = mAvatorWidth;
        dst_bottom = mAvatorWidth;
        
        output = Bitmap.createBitmap(mAvatorWidth,
        		mAvatorWidth, Config.ARGB_8888);
        canvas = new Canvas(output);
         
        paint = new Paint();
        src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        rectF = new RectF(dst);

        paint.setAntiAlias(true);
         
        paint.setColor(0xFFFFFFFF);
        
	}

	/**
	 * �����и�ͼƬ��λ�ã������λ������bitmap������ı�
	 */
	private void setCutPicPosition() {
        src.set(mCenterPoint.x - mBitmapPoint.x-mRadius,
        		mCenterPoint.y - mBitmapPoint.y-mRadius,
        		mCenterPoint.x - mBitmapPoint.x-mRadius+mAvatorWidth,
        		mCenterPoint.y - mBitmapPoint.y-mRadius+mAvatorWidth);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//��֤����ı���ֻ����һ�γ�ʼ������ΪonDraw�����ﲻ�ʺϽ��г�ʼ������������Ὺ��ܴ����Դ��
		if(mFgBitmap == null) {
			
			mLastBitmap = mBitmap;
			mBitmapLastWidth = mBitmap.getWidth();
			mBitmapLastHeight = mBitmap.getHeight();
			mFgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);  
			mFgCanvas = new  Canvas(mFgBitmap);
			mRect = new RectF(0, 0, getWidth(), getHeight());
			mCenterPoint.set(getWidth()/2, getHeight()/2);
			
		}
		setCutPicPosition();
		//���ǰ���ɰ��
		mFgCanvas.drawPaint(mClearFgPaint);
        
		if(mBitmap != null) {
			if(!isFinishFirstZoomed) {
				scale = getHeight()/mBitmap.getHeight();
				mBitmap = zoomImg(mLastBitmap, (int) (scale*mBitmap.getWidth()), getHeight());
				mBitmapPoint.set((getWidth()-mBitmap.getWidth())/2, 0);
				isFinishFirstZoomed = true;
			}
			//�滭��������bitmap
			canvas.drawBitmap(mBitmap, mBitmapPoint.x, mBitmapPoint.y, mPaint);
		}
		
        //����Բ��  
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint);
        //�����ཻԲ  
        mFgCanvas.drawCircle(mCenterPoint.x,mCenterPoint.y, mRadius, mRoundPaint);  
        //���ư�͸������
        mFgCanvas.drawRect(mRect, mFgroundPaint) ;
        //��ǰ���ɰ�㻭�ڱ�CutPicView��canvas��
        canvas.drawBitmap(mFgBitmap, null, mRect, mPaint);
	}

	//������ͼƬ�϶������ŵ��߼��������߼����׽��ͣ��ڴ�Ҳ���������ע���ˣ�����Ȥ�Ŀ����о�һ�¡�
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(mBitmap != null) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			//��ָ����
			case MotionEvent.ACTION_DOWN:
				//��Ϊ��ָ�϶�ģʽ
				mCurrMode = MODE_DRAG;
				mFirstDragPoint.set(event.getX(), event.getY());
				mFirstZoomPoint.set(event.getX(), event.getY());
				break;
			//˫ָ����
			case MotionEvent.ACTION_POINTER_DOWN:
				//��Ϊ˫ָ�϶�ģʽ
				mCurrMode = MODE_ZOOM;
				mSecondZoomPoint.set(event.getX(), event.getY());
				oriDis = distance(event); 
				break;
			case MotionEvent.ACTION_MOVE:
				//�жϵ�ǰģʽ
				if(mCurrMode == MODE_DRAG) {
					if(isSecondePointerUp) {
						mFirstDragPoint.set(event.getX(), event.getY());
						isSecondePointerUp = false;
					}
					mBitmapPoint.set(mBitmapPoint.x+(int) (event.getX()-mFirstDragPoint.x),
							mBitmapPoint.y+(int) (event.getY()-mFirstDragPoint.y));
					mFirstDragPoint.set(event.getX(), event.getY());
				} else if(mCurrMode == MODE_ZOOM) {
					 float newDist = distance(event);  
		                if (newDist > 10f) {  
		                    float scale = newDist / oriDis;
		                    int x = (int) (mBitmapPoint.x+(mBitmap.getWidth()-scale*mBitmapLastWidth)/2);
		                    int y = (int) (mBitmapPoint.y+(mBitmap.getHeight()-scale*mBitmapLastHeight)/2);
		                    mBitmap = zoomImg(mLastBitmap, (int)(scale*mBitmapLastWidth), (int)(scale*mBitmapLastHeight));
		                    mBitmapPoint.set(x, y);
		                }  
				}
				
				break;
			case MotionEvent.ACTION_UP:
				break;
			case MotionEvent.ACTION_POINTER_UP:				
				mBitmapLastWidth = mBitmap.getWidth();
				mBitmapLastHeight = mBitmap.getHeight();
				mCurrMode = MODE_DRAG;
				isSecondePointerUp = true;
				break;
			default:
				break;
			}
			requestLayout();
			invalidate();
		}
		
		return true;
	} 
	/**
	 * ������Ҫ�и��bitmap
	 * @param bitmap
	 */
	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}
	
	/**
     * ת��ͼƬ��Բ��
     * @param bitmap ����Bitmap����
     * @return
     */
    public Bitmap toRoundBitmap() {
       
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(mBitmap, src, dst, paint);
        return output;
    }
	/**
	 * ����������bitmap
	 * @param bm ԭbitmap
	 * @param newWidth �µĿ��
	 * @param newHeight �µĸ߶�
	 * @return ���ź��bitmap
	 */
	private Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
		if(newHeight==0 || newWidth == 0) {
			return bm;
		}
	   float scale = 0;
	   //���ͼƬ�Ŀ��
	   int width = bm.getWidth();
	   int height = bm.getHeight();
	   //�������ű��������жϸ߶�
	   if(newHeight > 0) {
		   scale = ((float) newHeight) / height;
		   newWidth = (int) (scale*width);
		   
	   } else {
		   scale = ((float) newWidth) / width;
		   newHeight = (int) (scale*height);
	   }
	   //ȡ����Ҫ���ŵ�matrix����
	  
	   Matrix matrix = new Matrix();
	   matrix.postScale(scale, scale);
	   // �õ��µ�ͼƬ
	   Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	   return newbm;
	}
	
    /**
     *  ��������������֮��ľ���   
     * @param event
     * @return
     */
    private float distance(MotionEvent event) {  
        float x = event.getX(0) - event.getX(1);  
        float y = event.getY(0) - event.getY(1);  
        return (float) Math.sqrt(x * x + y * y);  
    }  

    /**
     * �����Ļ���
     * @return
     */
    private int getScreenWidth() {
    	WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    	DisplayMetrics m = new DisplayMetrics();
    	wm.getDefaultDisplay().getMetrics(m);
    	return m.widthPixels;
    }
	
}
