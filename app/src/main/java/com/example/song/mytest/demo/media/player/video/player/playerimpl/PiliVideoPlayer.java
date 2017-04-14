package com.example.song.mytest.demo.media.player.video.player.playerimpl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.song.R;
import com.example.song.mytest.demo.media.player.video.MediaController;
import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IVideoPlayer;
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

    /***
     * 监听播放器的 prepare 过程
     * prepare 过程主要包括：创建资源、建立连接、请求码流等等
     * 当 prepare 完成后， 会回调onPrepare 。可以调用播放器的 start() 启动播放
     */
    private PlayerCallback.OnPrepareListener mOnPrepareListener;

    /***
     * 监听播放结束的消息
     * 如果是播放文件，则是播放到文件结束后产生回调
     * 如果是在线视频，则会在读取到码流的EOF信息后产生回调，回调前会先播放完已缓冲的数据
     * 如果播放过程中产生onError，并且没有处理的话，最后也会回调本接口
     * 如果播放前设置了 setLooping(true)，则播放结束后会自动重新开始，不会回调本接口
     */
    private PlayerCallback.OnCompleteListener mOnCompleteListener;

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
    private PlayerCallback.OnErrorListener mOnErrorListener;

    private PlayerCallback.OnInfoListener mOnInfoListener;
    private PlayerCallback.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private PlayerCallback.OnSeekCompleteListener mOnSeekCompleteListener;

    //播放类型 0回放 1直播
    private int mVideoType;
    //解码类型 0软解码 1硬解码 2自动
    private int mDecodeType;
    //播放器配置
    private AVOptions mAvOptions;

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
        mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(PLMediaPlayer plMediaPlayer) {
                if (mOnPrepareListener != null) {
                    mOnPrepareListener.onPrepared();
                }
            }
        });
        mPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                if (mOnErrorListener != null) {
                    return mOnErrorListener.onError(i);
                }
                return false;
            }
        });
        mPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(PLMediaPlayer plMediaPlayer) {
                if (mOnCompleteListener != null) {
                    mOnCompleteListener.onComplete();
                }
            }
        });
        mPlayer.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (mOnInfoListener != null) {
                    mOnInfoListener.onInfo(i, i1);
                }
                return true;
            }
        });
        mPlayer.setOnBufferingUpdateListener(new PLMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int i) {
                if (mOnBufferingUpdateListener != null) {
                    mOnBufferingUpdateListener.onBufferingUpdate(i);
                }
            }
        });
        mPlayer.setOnSeekCompleteListener(new PLMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
                if (mOnSeekCompleteListener != null) {
                    mOnSeekCompleteListener.onSeekComplete();
                }
            }
        });
        MediaController mediaController = new MediaController(mContext, false, mVideoType == 1);
        mPlayer.setMediaController(mediaController);

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
    public void release() {
        if (mPlayer != null) {
            mPlayer.stopPlayback();
            mPlayer.releaseSurfactexture();
        }
    }

    @Override
    public void setVolume(float l, float r) {
        mPlayer.setVolume(l, r);
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
        mPlayer.setVideoPath(path);
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
    public void setDecodeType(int type) {
        this.mDecodeType = type;
    }


    @Override
    public void setOnPrepareListener(final PlayerCallback.OnPrepareListener listener) {
        this.mOnPrepareListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(PLMediaPlayer plMediaPlayer) {
                    listener.onPrepared();
                }
            });
        }
    }

    @Override
    public void setOnErrorListener(final PlayerCallback.OnErrorListener listener) {
        this.mOnErrorListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                    return listener.onError(i);
                }
            });
        }
    }

    @Override
    public void setOnCompleteListener(final PlayerCallback.OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(PLMediaPlayer plMediaPlayer) {
                    listener.onComplete();
                }
            });
        }
    }

    @Override
    public void setOnInfoListener(PlayerCallback.OnInfoListener listener) {
        this.mOnInfoListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                    mOnInfoListener.onInfo(i, i1);
                    return true;
                }
            });
        }
    }

    @Override
    public void setOnBufferingUpdateListener(PlayerCallback.OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnBufferingUpdateListener(new PLMediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int i) {
                    mOnBufferingUpdateListener.onBufferingUpdate(i);
                }
            });
        }
    }


    @Override
    public void setOnSeekCompleteListener(PlayerCallback.OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnSeekCompleteListener(new PLMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
                    mOnSeekCompleteListener.onSeekComplete();
                }
            });
        }
    }


}
