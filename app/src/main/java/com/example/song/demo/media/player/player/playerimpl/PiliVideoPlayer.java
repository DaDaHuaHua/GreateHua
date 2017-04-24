package com.example.song.demo.media.player.player.playerimpl;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.song.R;
import com.example.song.demo.media.player.callback.PlayerCallback;
import com.example.song.demo.media.player.player.IMediaController;
import com.example.song.demo.media.player.player.IVideoPlayer;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

/**
 * Created by zz on 2017/3/31.
 * 基于PiliVideoTextureView 实现的播放器
 */

public class PiliVideoPlayer implements IVideoPlayer {

    private static String TAG = "PiliVideoPlayerTAG";
    private PLVideoTextureView mPlayer;
    private Context mContext;

    //播放类型 0回放 1直播
    private int mVideoType;
    //解码类型 0软解码 1硬解码 2自动
    private int mDecodeType;
    //播放器配置
    private AVOptions mAvOptions;
    //MediaController
    private IMediaController mMediaController;

    private PlayerCallback.OnPrepareListener mPlayerOnPrepareListener;
    private PlayerCallback.OnPrepareListener mControllerOnPrepareListener;

    /**
     * 播放器显示比例
     * PLVideoTextureView.ASPECT_RATIO_ORIGIN           原始尺寸
     * PLVideoTextureView.ASPECT_RATIO_FIT_PARENT       适应屏幕
     * PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT     全屏铺满
     * PLVideoTextureView.ASPECT_RATIO_16_9             16:9
     * PLVideoTextureView.ASPECT_RATIO_4_3              4:3
     */
    private int mCurrentRatio;


    public PiliVideoPlayer(Context context) {
        this.mContext = context;
        mPlayer = (PLVideoTextureView) ((Activity) context).getLayoutInflater().inflate(R.layout.plvideo_texture_view, null);
    }

    @Override
    public void init() {
        buildOptions();
    }

    /**
     * 配置播放参数
     */
    private void buildOptions() {
        mAvOptions = new AVOptions();
        // 解码方式:
        // codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
        // codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
        // codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
        mAvOptions.setInteger(AVOptions.KEY_MEDIACODEC, mDecodeType);
        // 准备超时时间，包括创建资源、建立连接、请求码流等，单位是 ms
        mAvOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 30 * 1000);
        // 读取视频流超时时间，单位是 ms
        // 默认值是：10 * 1000
        mAvOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 30 * 1000);
        // 播放前最大探测流的字节数，单位是 byte
        // 默认值是：128 * 1024
        mAvOptions.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // 当前播放的是否为在线直播，如果是，则底层会有一些播放优化
        // 默认值是：0
        mAvOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, mVideoType);
        if (mVideoType == 1) {
            mAvOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        // 是否自动启动播放，如果设置为 1，则在调用 `prepareAsync` 或者 `setVideoPath` 之后自动启动播放，无需调用 `start()`
        // 默认值是：1
        mAvOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
        mPlayer.setAVOptions(mAvOptions);
    }


    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void stop() {
        mPlayer.stopPlayback();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public void seekTo(long time) {
        mPlayer.seekTo(time);
    }

    @Override
    public void reset() {
        mPlayer.stopPlayback();
        mMediaController.reset();
    }

    @Override
    public void release() {
        if (mPlayer != null) {
            mPlayer.stopPlayback();
            mPlayer.releaseSurfactexture();
        }
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void setDisplayOrientation(int degree) {
        mPlayer.setDisplayOrientation(degree);
    }

    @Override
    public void setDisplayAspectRatio(int ratio) {
        this.mCurrentRatio = ratio;
        mPlayer.setDisplayAspectRatio(ratio);
    }

    @Override
    public int getDisplayAspectRatio() {
        return mCurrentRatio;
    }

    @Override
    public void setBufferingView(View v) {
        mPlayer.setBufferingIndicator(v);
    }

    @Override
    public void setCoverView(View v) {
        mPlayer.setCoverView(v);
    }


    @Override
    public View getPlayerLayout() {
        return mPlayer;
    }

    @Override
    public void setPath(String path) {
        if (mPlayer != null) {
            mPlayer.setVideoPath(path);
        }
    }

    @Override
    public void setPlayerType(int type) {
        if (mVideoType != type) {
            this.mVideoType = type;
            if (mAvOptions != null) {
                mAvOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, mVideoType);
                if (mVideoType == 1) {
                    mAvOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
                }
                mPlayer.setAVOptions(mAvOptions);
            }
        }
    }

    @Override
    public int getPlayerType() {
        return mVideoType;
    }

    @Override
    public int getMediaType() {
        return 1;
    }

    @Override
    public long getDuration() {
        if (mPlayer != null) {
            return mPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public long getCurrentPosition() {
        if(mPlayer != null ){
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return mPlayer.getBufferPercentage();
    }

    @Override
    public void setDecodeType(int type) {
        this.mDecodeType = type;
    }

    @Override
    public void setMediaController(com.example.song.demo.media.player.player.IMediaController mediaController) {
        this.mMediaController = mediaController;
        mMediaController.onMediaControllerSet(this);
    }

    @Override
    public IMediaController getMediaController() {
        if(mMediaController == null ){
            throw  new NullPointerException("getMediaController() got null , have u invoked 'setMediaController(IMediaController)' ?");
        }
        return mMediaController;
    }

    /***
     * 监听播放器的 prepare 过程 ，提供给ZMVideoPlayer设置并监听piliVideoPlayer的prepare过程
     *
     * prepare 过程主要包括：创建资源、建立连接、请求码流等等
     * 当 prepare 完成后， 会回调onPrepare 。可以调用播放器的 start() 启动播放
     */
    @Override
    public void setOnPrepareListener( PlayerCallback.OnPrepareListener listener) {
        this.mPlayerOnPrepareListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(PLMediaPlayer plMediaPlayer) {
                    mPlayerOnPrepareListener.onPrepared();
                    if(mControllerOnPrepareListener != null){
                        mControllerOnPrepareListener.onPrepared();
                    }
                }
            });
        }
    }

    /***
     * 监听播放器的 prepare 过程 ， 提供给MediaController监听piliVideoPlayer的prepare过程
     *
     *  同{@link #setOnPrepareListener }
     */
    @Override
    public void setControllerOnPrepareListener( PlayerCallback.OnPrepareListener controllerOnPrepareListener) {
        this.mControllerOnPrepareListener = controllerOnPrepareListener;
        if(mPlayer != null ){
            mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(PLMediaPlayer plMediaPlayer) {
                    if(mPlayerOnPrepareListener!= null){
                        mPlayerOnPrepareListener.onPrepared();
                    }
                    mControllerOnPrepareListener.onPrepared();
                }
            });
        }
    }

    /***
     * 监听播放器的错误消息
     * 返回值决定了该错误是否已经被处理，如果返回 false，则代表没有被处理，下一步则会触发 onCompletion 消息。
     errorCode	                  value	             描述
     MEDIA_ERROR_UNKNOWN	         -1	        未知错误
     ERROR_CODE_INVALID_URI	         -2	        无效的 URL
     ERROR_CODE_IO_ERROR	         -5	        网络异常
     ERROR_CODE_STREAM_DISCONNECTED	-11	        与服务器连接断开
     ERROR_CODE_EMPTY_PLAYLIST	-541478725	    空的播放列表
     ERROR_CODE_404_NOT_FOUND	-875574520	    播放资源不存在
     ERROR_CODE_CONNECTION_REFUSED	-111	    服务器拒绝连接
     ERROR_CODE_CONNECTION_TIMEOUT	-110	    连接超时
     ERROR_CODE_UNAUTHORIZED	-825242872	    未授权，播放一个禁播的流
     ERROR_CODE_PREPARE_TIMEOUT	-2001	        播放器准备超时
     ERROR_CODE_READ_FRAME_TIMEOUT	-2002	    读取数据超时
     ERROR_CODE_HW_DECODE_FAILURE	-2003	    硬解码失败
     */
    @Override
    public void setOnErrorListener(final PlayerCallback.OnErrorListener listener) {
        if (mPlayer != null) {
            mPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                    return listener.onError(i);
                }
            });
        }
    }


    /**
     * 如果是播放文件，则是播放到文件结束后产生回调
     * 如果是在线视频，则会在读取到码流的EOF信息后产生回调，回调前会先播放完已缓冲的数据
     * 如果播放过程中产生onError，并且没有处理的话，最后也会回调本接口
     * 如果播放前设置了 setLooping(true)，则播放结束后会自动重新开始，不会回调本接口
     */
    @Override
    public void setOnCompleteListener(final PlayerCallback.OnCompleteListener listener) {
        if (mPlayer != null) {
            mPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(PLMediaPlayer plMediaPlayer) {
                    listener.onComplete();
                }
            });
        }
    }

    /**
     * what             	            value                描述
     * MEDIA_INFO_UNKNOWN	                1	                未知消息
     * MEDIA_INFO_VIDEO_RENDERING_START	3	                第一帧视频已成功渲染
     * MEDIA_INFO_BUFFERING_START     	701	                开始缓冲
     * MEDIA_INFO_BUFFERING_END	        702	                停止缓冲
     * MEDIA_INFO_VIDEO_ROTATION_CHANGED	10001	            获取到视频的播放角度
     * MEDIA_INFO_AUDIO_RENDERING_START	10002	            第一帧音频已成功播放
     * MEDIA_INFO_VIDEO_GOP_TIME	        10003	            获取视频的I帧间隔
     * MEDIA_INFO_SWITCHING_SW_DECODE	    802	                硬解失败，自动切换软解
     *
     * @param listener listener
     */
    @Override
    public void setOnInfoListener(final PlayerCallback.OnInfoListener listener) {
        if (mPlayer != null) {
            mPlayer.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                    listener.onInfo(i, i1);
                    return true;
                }
            });
        }
    }

    /**
     * 已经缓冲的数据量占整个视频时长的百分比。
     * 仅在播放文件和回放时才有效
     */
    @Override
    public void setOnBufferingUpdateListener(final PlayerCallback.OnBufferingUpdateListener listener) {
        if (mPlayer != null) {
            mPlayer.setOnBufferingUpdateListener(new PLMediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int i) {
                    listener.onBufferingUpdate(i);
                }
            });
        }
    }

    /**
     * seek 完成后回调
     * 当调用的播放器的 seekTo 方法后，会在 seek 成功后触发该回调。
     */
    @Override
    public void setOnSeekCompleteListener(final PlayerCallback.OnSeekCompleteListener listener) {
        if (mPlayer != null) {
            mPlayer.setOnSeekCompleteListener(new PLMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
                    listener.onSeekComplete();
                }
            });
        }
    }


}
