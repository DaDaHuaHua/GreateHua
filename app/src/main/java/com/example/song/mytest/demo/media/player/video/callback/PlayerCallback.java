package com.example.song.mytest.demo.media.player.video.callback;

/**
 * Created by zz on 2017/4/6.
 * 状态回调
 */

public class PlayerCallback {

    public interface OnPrepareListener {
        /**
         * 当 prepare 完成后回调，下一步则可以调用播放器的 start() 启动播放。
         * prepare 过程 ：创建资源、建立连接、请求码流等等，
         */
        void onPrepared();
    }

    public interface OnErrorListener {
        /**
         * 播放错误回调
         * 播放错误信息见具体播放器的实现
         */
        boolean onError(int errorCode);
    }

    public interface OnCompleteListener {
        /**
         * 播放结束回调
         */
        void onComplete();
    }

    public interface OnInfoListener {
        /**
         * 在播放器启动后，播放器发生状态变化时调用该对象的 onInfo 方法，同步状态信息。
         * what 定义了消息类型，extra 是附加参数 播放信息见具体播放器的实现
         */
        void onInfo(int what, int extra);
    }

    /**
     * 已经缓冲的数据量占整个视频时长的百分比。
     * 仅在播放文件和回放时才有效
     */
    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(int percent);
    }


    public interface OnSeekCompleteListener {
        /**
         * seek 完成后回调
         * 当调用的播放器的 seekTo 方法后，会在 seek 成功后触发该回调。
         */
        void onSeekComplete();
    }
}
