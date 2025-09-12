package com.peoples.shield.risk;

import com.peoples.shield.entity.RiskZone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // Point this base path to your GitHub raw URL folder
    @GET("riskzones.json")
    Call<List<RiskZone>> getRiskZones();
}
