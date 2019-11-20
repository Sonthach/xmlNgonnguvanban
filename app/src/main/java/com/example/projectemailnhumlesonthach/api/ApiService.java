package com.example.projectemailnhumlesonthach.api;

import com.example.projectemailnhumlesonthach.notifications.MyResponse;
import com.example.projectemailnhumlesonthach.notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA4hYi9ss:APA91bExhiF0QLx_VbmDkJBTIy71cRpHvRt-L8rwF0qj5F1oNAxR6Jj1_7bAwk4awg6B9Sf8biP4MW8kuIS4YAKUZeo-s3jBcn66hjmDWayVd0VWMZAoENt31_ZgHaHrYA9T8z4JMFcA"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
