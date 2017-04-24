package com.example.song.demo.media.player.player.playerimpl;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.example.commonlibrary.util.StringUtil;
import com.example.song.demo.media.player.callback.PlayerCallback;
import com.example.song.demo.media.player.player.IAudioPlayer;
import com.example.song.demo.media.player.player.IMediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

/**
 * Created by zz on 2017/4/13.
 * 基于PLMediaPlayer实现音频播放器
 */

public class PiliAudioPlayer implements IAudioPlayer {
    private Context mContext;
    private PLMediaPlayer mPlayer;
    private int mAudioType = -1;
    private int mDecodeType = -1;
    private AVOptions mAVOptions;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private PlayerCallback.OnPrepareListener mPlayerOnPrepareListener;
    private PlayerCallback.OnPrepareListener mControllerOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnInfoListener mOnInfoListener;
    private PlayerCallback.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private PlayerCallback.OnSeekCompleteListener mOnSeekCompleteListener;

    private String mPath;

    private IMediaController mMediaController;
    //当前缓冲占比
    private int mCurBufferPercent;

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
        mPlayer.setWakeMode(mContext.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

        mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(PLMediaPlayer plMediaPlayer) {
                if (mPlayerOnPrepareListener != null) {
                    mPlayerOnPrepareListener.onPrepared();
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
                    mCurBufferPercent = i;
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
            mMediaController.reset();
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

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void setPath(String path) throws IOException {
        this.mPath = path;
        if (mPlayer != null) {
            mPlayer.setDataSource(path);
        }
    }

    /**
     * @param type 0:回放 1：直播
     */
    @Override
    public void setPlayerType(int type) {
        //只能设置一次，因此用初始值判断
        if (mAudioType == -1) {
            this.mAudioType = type;
        } else {
            Log.e("PiliAudioPlayer", " 'setPlayerType(int)' do not support repeat invoke");
        }
    }

    @Override
    public int getPlayerType() {
        return mAudioType;
    }

    @Override
    public int getMediaType() {
        return 0;
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
        if (mPlayer != null) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        if (mPlayer != null) {
            return mCurBufferPercent;
        }
        return 0;
    }

    @Override
    public void setDecodeType(int type) {
        if (mDecodeType == -1) {
            this.mDecodeType = type;
        } else {
            Log.e("PiliAudioPlayer", " 'setDecodeType(int)' do not support repeat invoke");
        }
    }

    @Override
    public void setMediaController(IMediaController mediaController) {
        this.mMediaController = mediaController;
        mMediaController.onMediaControllerSet(this);
    }

    @Override
    public IMediaController getMediaController() {
        if (mMediaController == null) {
            throw new NullPointerException("getMediaController() got null , have u invoked 'setMediaController(IMediaController)' ?");
        }
        return mMediaController;
    }

    @Override
    public void setOnPrepareListener(final PlayerCallback.OnPrepareListener listener) {
        this.mPlayerOnPrepareListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(PLMediaPlayer plMediaPlayer) {
                    mPlayerOnPrepareListener.onPrepared();
                    if (mControllerOnPrepareListener != null) {
                        mControllerOnPrepareListener.onPrepared();
                    }
                }
            });
        }
    }

    @Override
    public void setControllerOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        this.mControllerOnPrepareListener = listener;
        if (mPlayer != null) {
            mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(PLMediaPlayer plMediaPlayer) {
                    if (mPlayerOnPrepareListener != null) {
                        mPlayerOnPrepareListener.onPrepared();
                    }
                    mControllerOnPrepareListener.onPrepared();
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
                    mCurBufferPercent = i;
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
