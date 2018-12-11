package com.telecorp.teledev.lukaapp.Service.ServiceMobile;

import com.telecorp.teledev.lukaapp.Model.CheckUserRequest;
import com.telecorp.teledev.lukaapp.Model.CheckUserResult;
import com.telecorp.teledev.lukaapp.Model.NotificationRequest;
import com.telecorp.teledev.lukaapp.Model.NotificationResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CheckUserServiceMobile {
       @POST("/lukaservice/ServiceSHermes.svc/GetMCheckUser")
//    @POST("/ServiceSHermes.svc/Login")
        Call<CheckUserResult> checkuser(@Body CheckUserRequest checkUserRequest);

        public interface OnNetworkCallbackListener{
                public void onResponse(CheckUserResult checkUserResult, Retrofit retrofit);
                public void onBodyError(ResponseBody responseBodyError);
                public void onBodyErrorIsNull();
                public void onFailure(Throwable t);

        }

    @POST("/lukaservice/ServiceSHermes.svc/UpdateMSUser")
//    @POST("/ServiceSHermes.svc/UpdateMSUser")
    Call<CheckUserResult> updateuser(@Body CheckUserRequest checkUserRequest);

    public interface OnNetworkCallbackListenerUpdate{
        public void onResponse(CheckUserResult checkUserResult, Retrofit retrofit);
        public void onBodyError(ResponseBody responseBodyError);
        public void onBodyErrorIsNull();
        public void onFailure(Throwable t);

    }




}
