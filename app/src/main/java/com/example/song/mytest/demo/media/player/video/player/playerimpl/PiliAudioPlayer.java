package com.example.song.mytest.demo.media.player.video.player.playerimpl;

import android.content.Context;
import android.util.Log;

import com.example.commonlibrary.util.StringUtil;
import com.example.commonlibrary.util.ToastUtil;
import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IAudioPlayer;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

/**
 * Created by zz on 2017/4/13.
 * 基于PLMediaPlayer实现音频播放器
 */

public class PiliAudioPlayer implements IAudioPlayer {
    private static final String TAG = "PiliAudioPlayerTag";
    private Context mContext;
    private PLMediaPlayer mPlayer;
    private int mAudioType = -1;
    private int mDecodeType = -1;
    private int mWakeMode = -1;
    private AVOptions mAVOptions;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnInfoListener mOnInfoListener;
    private PlayerCallback.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private PlayerCallback.OnSeekCompleteListener mOnSeekCompleteListener;

    private String mPath;

    public PiliAudioPlayer(Context context) {
        this.mContext = context;
    }

    private void buildOptions() {
        mAVOptions = new AVOptions();
        mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        mAVOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, mAudioType);
        if (mAudioType == 1) {
            mAVOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, mDecodeType);
        mAVOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
    }

    @Override
    public void init() throws IOException {
        buildOptions();
        mPlayer = new PLMediaPlayer(mContext, mAVOptions);

        if (mWakeMode != -1) {
            mPlayer.setWakeMode(mContext.getApplicationContext(), mWakeMode);
        }

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
        if (StringUtil.isNotBlank(mPath)) {
            mPlayer.setDataSource(mPath);
        }
    }

    @Override
    public void prepareAsync() {
        if (mPlayer != null) {
            mPlayer.prepareAsync();
        }
    }

    @Override
    public void reset() {
        if (mPlayer != null) {
            mPlayer.reset();
        }
    }

    @Override
    public void start() {
        if (mPlayer != null) {
            mPlayer.start();
        }
    }

    @Override
    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }
    }

    @Override
    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    @Override
    public void seekTo(long time) {
        if (mPlayer != null) {
            mPlayer.seekTo(time);
        }
    }

    @Override
    public void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    /**
     *
     * @param mode
     */
    @Override
    public void setWakeMode(int mode) {
        if (mWakeMode == -1) {
            this.mWakeMode = mode;
        } else {
            Log.e("PiliAudioPlayer", " 'setWakeMode(int)' do not support repeat invoke");
        }

    }



    @Override
    public void setPath(String path) throws IOException {
        this.mPath = path;
        if (mPlayer != null) {
            mPlayer.setDataSource(path);
        }
    }

    /**
     *
     * @param type 0:回放 1：直播
     */
    @Override
    public void setPlayerType(int type) {
        if (mAudioType == -1) {
            this.mAudioType = type;
        } else {
            Log.e("PiliAudioPlayer", " 'setPlayerType(int)' do not support repeat invoke");
        }
    }

    /**
     *
     * @param type
     */
    @Override
    public void setDecodeType(int type) {
        if (mDecodeType == -1) {
            this.mDecodeType = type;
        } else {
            Log.e("PiliAudioPlayer", " 'setDecodeType(int)' do not support repeat invoke");
        }
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
