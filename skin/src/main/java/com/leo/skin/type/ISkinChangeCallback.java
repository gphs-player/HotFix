package com.leo.skin.type;

/**
 * <p>Date:2020-04-04.12:32</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public interface ISkinChangeCallback {
    void onStart();
    void onError(Exception e);
    void onComplete();
    DefaultSkinChangeCallback DEFAULT = new DefaultSkinChangeCallback();

    class DefaultSkinChangeCallback implements ISkinChangeCallback{

        @Override
        public void onStart() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
