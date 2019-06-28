package com.hyphenate.easeui.chat.interfaces;

public interface OnCallBackListener<T, K> {
    void onSuccess(T t);

    void onError(K k, String error);

}
