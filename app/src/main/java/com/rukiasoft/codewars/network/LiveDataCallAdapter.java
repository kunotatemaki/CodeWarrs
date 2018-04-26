package com.rukiasoft.codewars.network;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rukiasoft.codewars.network.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;
    private int retries;
    LiveDataCallAdapter(Type responseType, int retries) {
        this.responseType = responseType;
        this.retries = retries;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(@NonNull final Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            final AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                            ApiResponse<R> responseWrapped = new ApiResponse<>(response);
                            if(!responseWrapped.isSuccessful() && retries > 0){
                                //no successful but need to retry
                                retries--;
                                call.clone().enqueue(this);
                            }else {
                                postValue(new ApiResponse<>(response));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<R> call, @NonNull Throwable throwable) {
                            if(retries > 0){
                                //no successful but need to retry
                                retries--;
                                call.clone().enqueue(this);
                            }else {
                                postValue(new ApiResponse<R>(throwable));
                            }
                        }
                    });
                }
            }
        };
    }
}
